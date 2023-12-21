package be.bstorm.formation.airport.bll.services.implementations;

import be.bstorm.formation.airport.bll.services.PlaneService;
import be.bstorm.formation.airport.dal.models.PlaneEntity;
import be.bstorm.formation.airport.dal.repositories.OwnerRepository;
import be.bstorm.formation.airport.dal.repositories.PlaneRepository;
import be.bstorm.formation.airport.dal.repositories.PlaneTypeRepository;
import be.bstorm.formation.airport.dal.repositories.SpecificationBuilder;
import be.bstorm.formation.airport.pl.models.forms.PlaneForm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static be.bstorm.formation.airport.dal.repositories.SpecificationBuilder.specificationBuilder;

@Service
public class PlaneServiceImpl implements PlaneService {

    private final PlaneRepository planeRepository;
    private final OwnerRepository ownerRepository;
    private final PlaneTypeRepository planeTypeRepository;

    public PlaneServiceImpl(PlaneRepository planeRepository,
                            OwnerRepository ownerRepository,
                            PlaneTypeRepository planeTypeRepository) {
        this.planeRepository = planeRepository;
        this.ownerRepository = ownerRepository;
        this.planeTypeRepository = planeTypeRepository;
    }

    @Override
    public Page<PlaneEntity> getAll(Pageable pageable) {
        return planeRepository.findAll(pageable);
    }

    @Override
    public Optional<PlaneEntity> getById(String id) {
        return planeRepository.findById(id);
    }

    @Override
    public void save(PlaneForm form) {
        if (form == null)
            throw new IllegalArgumentException("Form can't be null");

        PlaneEntity entity = new PlaneEntity();
        entity.setNumIma(form.numIma());
        entity.setOwnerEntities(new ArrayList<>(ownerRepository.findAllById(form.ownersId())));
        entity.setPlaneTypeEntity(planeTypeRepository.findById(form.planeTypeId()).orElseThrow(() -> new EntityNotFoundException("Plane type not found")));
        planeRepository.save(entity);
    }

    @Override
    public void update(PlaneForm form, String id) {
        if (form == null)
            throw new IllegalArgumentException("Form can't be null");

        PlaneEntity entity = planeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Plane not found"));
        entity.setNumIma(form.numIma());
        entity.setOwnerEntities(new ArrayList<>(ownerRepository.findAllById(form.ownersId())));
        entity.setPlaneTypeEntity(planeTypeRepository.findById(entity.getPlaneTypeEntity().getId()).orElseThrow(() -> new EntityNotFoundException("Plane type not found")));
        planeRepository.save(entity);
    }

    @Override
    public List<PlaneEntity> search(String numIma, String ownerName) {
        return planeRepository.findAll(specificationBuilder(numIma, ownerName));
    }

    @Override
    public void deleteById(String id) {
        planeRepository.deleteById(id);
    }

}

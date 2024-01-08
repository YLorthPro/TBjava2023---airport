package be.bstorm.formation.airport.bll.services.implementations;

import be.bstorm.formation.airport.bll.services.MachinistService;
import be.bstorm.formation.airport.dal.models.MachinistEntity;
import be.bstorm.formation.airport.dal.repositories.MachinistRepository;
import be.bstorm.formation.airport.dal.repositories.PlaneTypeRepository;
import be.bstorm.formation.airport.pl.models.forms.MachinistForm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class MachinistServiceImpl implements MachinistService {

    private final MachinistRepository machinistRepository;
    private final PlaneTypeRepository planeTypeRepository;

    public MachinistServiceImpl(MachinistRepository machinistRepository,
                                PlaneTypeRepository planeTypeRepository) {
        this.machinistRepository = machinistRepository;
        this.planeTypeRepository = planeTypeRepository;
    }

    @Override
    public Page<MachinistEntity> getAll(Pageable pageable) {
        return machinistRepository.findAll(pageable);
    }

    @Override
    public Optional<MachinistEntity> getById(Long id) {
        return machinistRepository.findById(id);
    }

    @Override
    public void save(MachinistForm form) {
        if(form == null)
            throw new IllegalArgumentException("form can't be null");

        MachinistEntity entity = new MachinistEntity();
        entity.setName(form.name());
        entity.setAddress(form.address());
        entity.setPhone(form.phone());
        entity.setPlaneTypeEntities(new ArrayList<>(planeTypeRepository.findAllById(form.planeTypeId())));
        machinistRepository.save(entity);
    }


    @Override
    public void deleteById(Long id) {
        machinistRepository.deleteById(id);
    }

    @Override
    public void update(MachinistForm form, Long id) {

        if(form == null)
            throw new IllegalArgumentException("form can't be null");

        MachinistEntity entity = machinistRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Machinist not found"));
        entity.setName(form.name());
        entity.setAddress(form.address());
        entity.setPhone(form.phone());
        entity.setPlaneTypeEntities(new ArrayList<>(planeTypeRepository.findAllById(form.planeTypeId())));
        machinistRepository.save(entity);
    }


}

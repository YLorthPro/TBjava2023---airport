package be.bstorm.formation.airport.bll.services.implementations;

import be.bstorm.formation.airport.bll.services.OwnerService;
import be.bstorm.formation.airport.dal.models.OwnerEntity;
import be.bstorm.formation.airport.dal.repositories.OwnerRepository;
import be.bstorm.formation.airport.pl.models.forms.OwnerForm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public List<OwnerEntity> getAll() {
        return ownerRepository.findAll();
    }

    @Override
    public Optional<OwnerEntity> getById(Long id) {
        return ownerRepository.findById(id);
    }

    @Override
    public void save(OwnerForm form) {

        if(form == null)
            throw new IllegalArgumentException("Form can't be null");

        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setName(form.name());
        ownerEntity.setAddress(form.address());
        ownerEntity.setPhone(form.phone());
        ownerRepository.save(ownerEntity);
    }

    @Override
    public void update(OwnerForm form, Long id) {
        if (form == null)
            throw new IllegalArgumentException("Form can't be null");

        OwnerEntity entity = ownerRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Owner not found"));
        entity.setName(form.name());
        entity.setAddress(form.address());
        entity.setPhone(form.phone());
        ownerRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        ownerRepository.deleteById(id);
    }
}

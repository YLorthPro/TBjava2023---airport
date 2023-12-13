package be.bstorm.formation.airport.bll.services;

import be.bstorm.formation.airport.dal.models.OwnerEntity;
import be.bstorm.formation.airport.pl.models.forms.OwnerForm;

import java.util.List;
import java.util.Optional;

public interface OwnerService {
    List<OwnerEntity> getAll();
    void save(OwnerForm form);
    Optional<OwnerEntity> getById(Long id);
    void deleteById(Long id);
    void update (OwnerForm form, Long id);
}

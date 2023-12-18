package be.bstorm.formation.airport.bll.services;

import be.bstorm.formation.airport.dal.models.OwnerEntity;
import be.bstorm.formation.airport.pl.models.forms.OwnerForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OwnerService {
    Page<OwnerEntity> getAll(Pageable pageable);
    void save(OwnerForm form);
    Optional<OwnerEntity> getById(Long id);
    void deleteById(Long id);
    void update (OwnerForm form, Long id);
}

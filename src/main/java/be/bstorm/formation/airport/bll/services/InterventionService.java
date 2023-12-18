package be.bstorm.formation.airport.bll.services;

import be.bstorm.formation.airport.dal.models.InterventionEntity;
import be.bstorm.formation.airport.pl.models.forms.InterventionForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface InterventionService {
    Page<InterventionEntity> getAll(Pageable pageable);
    void save(InterventionForm form);
    Optional<InterventionEntity> getById(Long id);
    void deleteById(Long id);
    void update (InterventionForm form, Long id);
}

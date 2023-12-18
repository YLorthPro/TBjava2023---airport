package be.bstorm.formation.airport.bll.services;

import be.bstorm.formation.airport.dal.models.PlaneTypeEntity;
import be.bstorm.formation.airport.pl.models.forms.PlaneTypeForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PlaneTypeService {
    Page<PlaneTypeEntity> getAll(Pageable pageable);
    void save(PlaneTypeForm form);
    Optional<PlaneTypeEntity> getById(Long id);
    void deleteById(Long id);
    void update (PlaneTypeForm form, Long id);
}

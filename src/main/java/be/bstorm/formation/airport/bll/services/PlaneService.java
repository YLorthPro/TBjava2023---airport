package be.bstorm.formation.airport.bll.services;

import be.bstorm.formation.airport.dal.models.PlaneEntity;
import be.bstorm.formation.airport.pl.models.forms.PlaneForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PlaneService {
    Page<PlaneEntity> getAll(Pageable pageable);
    void save(PlaneForm form);
    Optional<PlaneEntity> getById(String id);
    void deleteById(String id);
    void update (PlaneForm form, String id);
}

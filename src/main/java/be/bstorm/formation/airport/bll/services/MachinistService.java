package be.bstorm.formation.airport.bll.services;

import be.bstorm.formation.airport.dal.models.MachinistEntity;
import be.bstorm.formation.airport.pl.models.forms.MachinistForm;

import java.util.List;
import java.util.Optional;

public interface MachinistService {
    List<MachinistEntity> getAll();
    void save(MachinistForm form);
    Optional<MachinistEntity> getById(Long id);
    void deleteById(Long id);
    void update (MachinistForm form, Long id);
}

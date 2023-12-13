package be.bstorm.formation.airport.bll.services;

import be.bstorm.formation.airport.dal.models.PilotEntity;
import be.bstorm.formation.airport.pl.models.forms.PilotForm;

import java.util.List;
import java.util.Optional;

public interface PilotService {
    List<PilotEntity> getAll();
    void save(PilotForm form);
    Optional<PilotEntity> getById(Long id);
    void deleteById(Long id);
    void update (PilotForm form, Long id);
}
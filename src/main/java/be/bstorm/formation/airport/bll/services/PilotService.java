package be.bstorm.formation.airport.bll.services;

import be.bstorm.formation.airport.bll.models.FlightResume;
import be.bstorm.formation.airport.dal.models.PilotEntity;
import be.bstorm.formation.airport.pl.models.forms.PilotForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PilotService {
    Page<PilotEntity> getAll(Pageable pageable);
    void save(PilotForm form);
    Optional<PilotEntity> getById(Long id);
    void deleteById(Long id);
    void update (PilotForm form, Long id);
    void addFlight(Long planeTypeId, Long pilotId);
    FlightResume getResume(Long pilotId);
    void updateResume(Long pilotId, Long planeTypeId, int flightNumber);
}

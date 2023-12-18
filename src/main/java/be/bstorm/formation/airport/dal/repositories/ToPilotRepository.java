package be.bstorm.formation.airport.dal.repositories;

import be.bstorm.formation.airport.dal.models.PilotEntity;
import be.bstorm.formation.airport.dal.models.PlaneTypeEntity;
import be.bstorm.formation.airport.dal.models.ToPilotEntity;
import be.bstorm.formation.airport.dal.models.ToPilotEntityCompositeKey;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ToPilotRepository extends JpaRepository<ToPilotEntity, ToPilotEntityCompositeKey>, JpaSpecificationExecutor<ToPilotEntity> {
}
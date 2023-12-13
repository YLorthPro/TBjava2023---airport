package be.bstorm.formation.airport.dal.repositories;

import be.bstorm.formation.airport.dal.models.PilotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PilotRepository extends JpaRepository<PilotEntity, Long> {
}
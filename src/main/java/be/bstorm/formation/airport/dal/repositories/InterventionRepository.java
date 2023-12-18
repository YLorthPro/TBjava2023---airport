package be.bstorm.formation.airport.dal.repositories;

import be.bstorm.formation.airport.dal.models.InterventionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InterventionRepository extends JpaRepository<InterventionEntity, Long>, JpaSpecificationExecutor<InterventionEntity> {
}
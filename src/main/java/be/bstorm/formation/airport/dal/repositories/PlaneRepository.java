package be.bstorm.formation.airport.dal.repositories;

import be.bstorm.formation.airport.dal.models.PlaneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlaneRepository extends JpaRepository<PlaneEntity, String>, JpaSpecificationExecutor<PlaneEntity> {
}
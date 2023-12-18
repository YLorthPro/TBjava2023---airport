package be.bstorm.formation.airport.dal.repositories;

import be.bstorm.formation.airport.dal.models.PlaneTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlaneTypeRepository extends JpaRepository<PlaneTypeEntity, Long>, JpaSpecificationExecutor<PlaneTypeEntity> {
}
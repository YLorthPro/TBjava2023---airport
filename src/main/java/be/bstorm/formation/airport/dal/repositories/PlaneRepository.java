package be.bstorm.formation.airport.dal.repositories;

import be.bstorm.formation.airport.dal.models.PlaneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaneRepository extends JpaRepository<PlaneEntity, String> {
}
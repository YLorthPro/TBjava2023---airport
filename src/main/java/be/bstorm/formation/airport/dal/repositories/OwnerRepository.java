package be.bstorm.formation.airport.dal.repositories;

import be.bstorm.formation.airport.dal.models.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<OwnerEntity, Long> {
}
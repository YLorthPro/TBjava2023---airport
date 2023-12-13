package be.bstorm.formation.airport.dal.repositories;

import be.bstorm.formation.airport.dal.models.MachinistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachinistRepository extends JpaRepository<MachinistEntity, Long> {
}
package be.bstorm.formation.airport.dal.repositories;

import be.bstorm.formation.airport.dal.models.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditEntityRepository extends JpaRepository<AuditEntity, Long> {
}
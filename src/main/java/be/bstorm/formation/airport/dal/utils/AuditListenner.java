package be.bstorm.formation.airport.dal.utils;

import be.bstorm.formation.airport.dal.models.AuditEntity;
import be.bstorm.formation.airport.dal.models.InterventionEntity;
import be.bstorm.formation.airport.dal.repositories.AuditEntityRepository;
import jakarta.persistence.PrePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuditListenner {

    private static AuditEntityRepository auditEntityRepository;

    public AuditListenner() {}

    @Autowired
    public void setAuditEntityRepository(AuditEntityRepository auditEntityRepository) {
        AuditListenner.auditEntityRepository = auditEntityRepository;
    }

    @PrePersist
    public void atCreate(InterventionEntity interventionEntity){
        AuditEntity entity = new AuditEntity();
        entity.setTime(LocalDateTime.now());
        entity.setPlane(interventionEntity.getPlane().getNumIma());
        auditEntityRepository.save(entity);
    }
}

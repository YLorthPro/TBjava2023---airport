package be.bstorm.formation.airport.pl.models.dto;

import be.bstorm.formation.airport.dal.models.InterventionEntity;

import java.time.LocalDate;

public record Intervention(
        long id,
        String object,
        LocalDate date,
        double duration,
        Machinist verifier,
        Machinist repairman,
        Plane plane){
    public static Intervention fromBll(InterventionEntity entity){
        return new Intervention(
                entity.getId(),
                entity.getObject(),
                entity.getDate(),
                entity.getDuration(),
                Machinist.fromBll(entity.getVerifier()),
                Machinist.fromBll(entity.getRepairman()),
                Plane.fromBll(entity.getPlaneEntity())
        );
    }
}
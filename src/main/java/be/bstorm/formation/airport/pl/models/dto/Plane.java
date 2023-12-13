package be.bstorm.formation.airport.pl.models.dto;

import be.bstorm.formation.airport.dal.models.PlaneEntity;

import java.util.List;

public record Plane(
        String numIma,
        List<Owner> owner,
        PlaneType type

) {
    public static Plane fromBll(PlaneEntity entity){
        return new Plane(
                entity.getNumIma(),
                entity.getOwnerEntities().stream().map(Owner::fromBll).toList(),
                PlaneType.fromBll(entity.getPlaneTypeEntity()));
    }
}

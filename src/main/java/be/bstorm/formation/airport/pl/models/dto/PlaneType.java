package be.bstorm.formation.airport.pl.models.dto;

import be.bstorm.formation.airport.dal.models.PlaneTypeEntity;

public record PlaneType(
        Long id,
        String name,
        String builder,
        int power,
        int seatsNumber
) {
    public static PlaneType fromBll(PlaneTypeEntity entity){
        return new PlaneType(
                entity.getId(),
                entity.getName(),
                entity.getBuilder(),
                entity.getPower(),
                entity.getSeatsNumber()
        );
    }
}

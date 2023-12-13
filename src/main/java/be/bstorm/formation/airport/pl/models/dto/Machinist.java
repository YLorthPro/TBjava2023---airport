package be.bstorm.formation.airport.pl.models.dto;

import be.bstorm.formation.airport.dal.models.MachinistEntity;

import java.util.Set;
import java.util.stream.Collectors;

public record Machinist(
        long id,
        String name,
        String address,
        String phone,
        Set<PlaneType> planeTypes){
    public static Machinist fromBll(MachinistEntity entity){
        return new Machinist(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getPlaneTypeEntities().stream().map(PlaneType::fromBll).collect(Collectors.toSet())
        );
    }
}
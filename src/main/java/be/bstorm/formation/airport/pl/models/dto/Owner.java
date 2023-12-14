package be.bstorm.formation.airport.pl.models.dto;


import be.bstorm.formation.airport.dal.models.OwnerEntity;
import be.bstorm.formation.airport.dal.models.PlaneEntity;

import java.util.Set;
import java.util.stream.Collectors;

public record Owner(
        Long id,
        String name,
        String address,
        String phone,
        Set<String> planes
) {
    public static Owner fromBll(OwnerEntity entity){
        return new Owner(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getPlaneEntities().stream()
                        .map(PlaneEntity::getNumIma)
                        .collect(Collectors.toSet())
        );
    }
}

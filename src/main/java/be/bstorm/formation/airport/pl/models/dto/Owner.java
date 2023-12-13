package be.bstorm.formation.airport.pl.models.dto;


import be.bstorm.formation.airport.dal.models.OwnerEntity;

import java.util.Set;
import java.util.stream.Collectors;

public record Owner(
        Long id,
        String name,
        String address,
        String phone,
        Set<Plane> planes
) {
    public static Owner fromBll(OwnerEntity entity){
        return new Owner(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getPlaneEntities().stream()
                        .map(Plane::fromBll)
                        .collect(Collectors.toSet())
        );
    }
}

package be.bstorm.formation.airport.pl.models.dto;

import be.bstorm.formation.airport.dal.models.PilotEntity;
import be.bstorm.formation.airport.dal.models.ToPilotEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record Pilot(
        long id,
        String name,
        String address,
        String phone,
        int licenseNumber,
        List<PlaneType> planeTypes,
        int flightsNumber){
    public static Pilot fromBll(PilotEntity entity){
        return new Pilot(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getLicenseNumber(),
                entity.getToPilotEntityList().stream()
                        .map(toPilotEntity -> PlaneType.fromBll(toPilotEntity.getPlaneType()))
                        .toList(),
                entity.getToPilotEntityList().stream()
                        .mapToInt(ToPilotEntity::getFlightsNumber)
                        .sum()
        );
    }
}
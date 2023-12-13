package be.bstorm.formation.airport.pl.models.forms;

import be.bstorm.formation.airport.pl.models.dto.PlaneType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record PilotForm(
        @NotBlank
        String name,
        @NotBlank
        String address,
        @NotBlank
        @Size(min = 9, max = 13)
        String phone,
        @Positive
        int licenseNumber,
        @Size(min = 1)
        Set<Long> planeTypes){
}
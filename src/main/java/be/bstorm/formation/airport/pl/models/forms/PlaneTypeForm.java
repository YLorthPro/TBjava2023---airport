package be.bstorm.formation.airport.pl.models.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record PlaneTypeForm(
        @NotBlank
        String name,
        @NotBlank
        String builder,
        @Positive
        int power,
        @Positive
        int seatsNumber
) {
}

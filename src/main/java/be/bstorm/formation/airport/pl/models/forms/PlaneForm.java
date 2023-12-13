package be.bstorm.formation.airport.pl.models.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PlaneForm(
        @NotBlank
        String numIma,
        @Size(min = 1)
        List<Long> ownersId,
        @Positive
        long planeTypeId
) {
}

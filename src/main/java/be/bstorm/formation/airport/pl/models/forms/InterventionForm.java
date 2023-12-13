package be.bstorm.formation.airport.pl.models.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record InterventionForm(
        @NotBlank
        String object,
        @NotNull
        @PastOrPresent
        LocalDate date,
        @Positive
        double duration,
        @NotNull
        @Positive
        long verifierId,
        @NotNull
        @Positive
        long repairmanId,
        @NotBlank
        String planeId){
}
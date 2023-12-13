package be.bstorm.formation.airport.pl.models.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record MachinistForm(
        @NotBlank
        String name,
        @NotBlank
        String address,
        @NotBlank
        @Size(min = 9, max = 13)
        String phone,
        @Size(min = 1)
        List<Long> planeTypeId){
}
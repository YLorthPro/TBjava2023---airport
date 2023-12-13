package be.bstorm.formation.airport.pl.models.forms;


import be.bstorm.formation.airport.dal.models.OwnerEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record OwnerForm(
        @NotBlank
        String name,
        @NotBlank
        String address,
        @NotBlank
        @Size(min = 9, max = 13)
        String phone,
        List<String> planesId
) {
}

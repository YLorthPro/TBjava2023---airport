package be.bstorm.formation.airport.pl.models.dto;

import java.time.LocalDateTime;

public record Error(
        String message,
        LocalDateTime requestMadeAt,
        String URI
) {
}

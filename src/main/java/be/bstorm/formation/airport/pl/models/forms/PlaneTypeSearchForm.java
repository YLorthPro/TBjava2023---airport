package be.bstorm.formation.airport.pl.models.forms;

public record PlaneTypeSearchForm(
        String name,
        String builder,
        int power,
        int seatsNumber
) {
}

package be.bstorm.formation.airport.pl.models.forms;


import java.time.LocalDate;

public record InterventionSearchForm(
        String object,
        LocalDate date,
        int duration,
        long verifierId,
        long repairmanId,
        String planeId){
}
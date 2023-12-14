package be.bstorm.formation.airport.bll.models;

import be.bstorm.formation.airport.pl.models.dto.Pilot;
import be.bstorm.formation.airport.pl.models.dto.PlaneType;

import java.util.HashMap;

public record FlightResume (
        Pilot pilot,
        HashMap<PlaneType, Integer> resume
){

}

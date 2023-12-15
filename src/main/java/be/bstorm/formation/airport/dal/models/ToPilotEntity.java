package be.bstorm.formation.airport.dal.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Piloter")
public class ToPilotEntity {
/*
 @EmbeddedId est utilisé pour spécifier le champ qui est une clé primaire composite,
 c'est-à-dire une clé primaire dont les champs sont définis dans une autre classe embarquée.*/
    @EmbeddedId
    private ToPilotEntityCompositeKey id;

    @Column(name = "nb_vols", columnDefinition = "INT DEFAULT 0 CHECK (nb_vols >= 0)")
    private int flightsNumber;

    /*
     @MapsId est utilisé pour indiquer que ce champ fait partie de la clé primaire
     et qu'il est mappé sur une autre entité.
     L'argument "pilotId" indique que ce champ est mappé sur le champ "pilotId"
     dans l'identifiant enraciné (ToPilotEntityCompositeKey).*/
    @MapsId("pilotId")
    @ManyToOne
    @JoinColumn(name = "pilot_id")
    private PilotEntity pilot;

    @MapsId("planeTypeId")
    @ManyToOne
    @JoinColumn(name = "plane_type_id")
    private PlaneTypeEntity planeType;


}

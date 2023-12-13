package be.bstorm.formation.airport.dal.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Piloter")
public class ToPilotEntity {
    @EmbeddedId
    private ToPilotEntityCompositeKey id;

    @Column(name = "nb_vols", columnDefinition = "INT DEFAULT 0 CHECK (nb_vols >= 0)")
    private int flightsNumber;

    @MapsId("pilotId")
    @ManyToOne
    @JoinColumn(name = "pilot_id")
    private PilotEntity pilot;

    @MapsId("planeTypeId")
    @ManyToOne
    @JoinColumn(name = "plane_type_id")
    private PlaneTypeEntity planeType;


}

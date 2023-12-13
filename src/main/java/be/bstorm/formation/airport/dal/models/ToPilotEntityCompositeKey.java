package be.bstorm.formation.airport.dal.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ToPilotEntityCompositeKey implements Serializable {
    @Column(name = "pilot_id")
    private Long pilotId;

    @Column(name = "plane_type_id")
    private Long planeTypeId;
}

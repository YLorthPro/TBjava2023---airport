package be.bstorm.formation.airport.dal.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

/* Utilise l'annotation @Embeddable pour indiquer à JPA
 que cette classe est destinée à être incorporée par des entités propriétaires.
 */
@Embeddable
@Data
public class ToPilotEntityCompositeKey implements Serializable {
    @Column(name = "pilot_id")
    private Long pilotId;

    @Column(name = "plane_type_id")
    private Long planeTypeId;

    public ToPilotEntityCompositeKey() {
    }

    public ToPilotEntityCompositeKey(PilotEntity pilot, PlaneTypeEntity planeType) {
        this.pilotId = pilot.getId();
        this.planeTypeId = planeType.getId();
    }
}

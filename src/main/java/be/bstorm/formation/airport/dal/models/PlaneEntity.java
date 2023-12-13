package be.bstorm.formation.airport.dal.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "Avion")
public class PlaneEntity {
    @Id
    private String numIma;

    @ManyToMany(mappedBy = "planeEntities")
    private Set<OwnerEntity> ownerEntities = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "intervention_id")
    private InterventionEntity interventionEntity;

    @ManyToOne
    @JoinColumn(name = "plane_type_id")
    private PlaneTypeEntity planeTypeEntity;

}

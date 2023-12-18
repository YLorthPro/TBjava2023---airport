package be.bstorm.formation.airport.dal.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Avion")
public class PlaneEntity {
    @Id
    private String numIma;

    @ManyToMany
    @JoinTable(name = "a",
            joinColumns = @JoinColumn(name = "num_ima"),
            inverseJoinColumns = @JoinColumn(name = "owner_id"))
    private List<OwnerEntity> ownerEntities = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "plane_type_id")
    private PlaneTypeEntity planeTypeEntity;

    @OneToMany(orphanRemoval = true, mappedBy = "plane")
    private List<InterventionEntity> interventionEntities = new ArrayList<>();

}

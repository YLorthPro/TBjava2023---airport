package be.bstorm.formation.airport.dal.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Mecano")
public class MachinistEntity extends PersonEntity{
    @ManyToMany
    @JoinTable(name = "est_habilite",
            joinColumns = @JoinColumn(name = "machinist_id"),
            inverseJoinColumns = @JoinColumn(name = "planeType_id"))
    private List<PlaneTypeEntity> planeTypeEntities = new ArrayList<>();

    public boolean isQualifiedFor(PlaneEntity plane) {
        return planeTypeEntities.contains(plane.getPlaneTypeEntity());
    }

}

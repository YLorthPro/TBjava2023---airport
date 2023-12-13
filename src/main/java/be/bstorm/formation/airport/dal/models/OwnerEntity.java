package be.bstorm.formation.airport.dal.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "Proprio")
public class OwnerEntity extends PersonEntity{
    @ManyToMany
    @JoinTable(name = "a",
            joinColumns = @JoinColumn(name = "owner_id"),
            inverseJoinColumns = @JoinColumn(name = "num_ima"))
    private Set<PlaneEntity> planeEntities = new LinkedHashSet<>();

}

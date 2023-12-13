package be.bstorm.formation.airport.dal.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "Mecano")
public class MachinistEntity extends PersonEntity{
    @ManyToMany
    @JoinTable(name = "est_habilite",
            joinColumns = @JoinColumn(name = "machinist_id"),
            inverseJoinColumns = @JoinColumn(name = "planeType_id"))
    private Set<PlaneTypeEntity> planeTypeEntities = new LinkedHashSet<>();

}

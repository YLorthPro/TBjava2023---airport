package be.bstorm.formation.airport.dal.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "Mecano")
public class MachinistEntity extends PersonEntity{
    @ManyToMany
    @JoinTable(name = "est_habilite",
            joinColumns = @JoinColumn(name = "machinist_id"),
            inverseJoinColumns = @JoinColumn(name = "planeType_id"))
    private List<PlaneTypeEntity> planeTypeEntities = new ArrayList<>();

}

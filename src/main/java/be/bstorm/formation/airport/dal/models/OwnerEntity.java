package be.bstorm.formation.airport.dal.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "Proprio")
public class OwnerEntity extends PersonEntity{
    @ManyToMany(mappedBy = "ownerEntities")
    private List<PlaneEntity> planeEntities = new ArrayList<>();

}

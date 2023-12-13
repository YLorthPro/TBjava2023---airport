package be.bstorm.formation.airport.dal.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "Pilote")
public class PilotEntity extends PersonEntity{
    @Column(name = "num_brevet", nullable = false)
    private int licenseNumber;

    @OneToMany(mappedBy = "pilot")
    private List<ToPilotEntity> planeTypes;
}

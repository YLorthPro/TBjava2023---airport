package be.bstorm.formation.airport.dal.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "type_avion")
public class PlaneTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "modele_id")
    private long id;
    @Column(name = "nom", nullable = false)
    private String name;
    @Column(name = "constructeur", nullable = false)
    private String builder;
    @Column(name = "puissance", nullable = false, columnDefinition = "INT CHECK (puissance > 0)")
    private int power;
    @Column(name = "nb_places", nullable = false, columnDefinition = "INT CHECK (nb_places > 0)")
    private int seatsNumber;


    public void setPower(int power) {
        if(power<=0)
            throw new IllegalArgumentException("Power must be positive");
        this.power = power;
    }

    public void setSeatsNumber(int seatsNumber) {
        if(seatsNumber<=0)
            throw new IllegalArgumentException("Seats number must be positive");
        this.seatsNumber = seatsNumber;
    }

    @ManyToMany(mappedBy = "planeTypeEntities")
    private List<MachinistEntity> machinistEntities = new ArrayList<>();

    @OneToMany(mappedBy = "planeType")
    private List<ToPilotEntity> toPilots;

}

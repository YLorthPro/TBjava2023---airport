package be.bstorm.formation.airport.dal.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "Intervention")
public class InterventionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "num_unique")
    private long id;
    @Column(name = "objet", nullable = false)
    private String object;
    @Column(nullable = false, columnDefinition = "DATE CHECK (date <= CURRENT_DATE)")
    private LocalDate date;
    @Column(name = "duree", nullable = false, columnDefinition = "INT CHECK (duree > 0)")
    private int duration;


    public void setDate(LocalDate date) {
        if(date.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date can't be in the future");

        this.date = date;
    }

    public void setDuration(int duration) {
        if(duration <= 0)
            throw new IllegalArgumentException("Duration must be positive");
        this.duration = duration;
    }

    @ManyToOne
    @JoinColumn(name = "verifier_id")
    private MachinistEntity verifier;

    @ManyToOne
    @JoinColumn(name = "repairman_id")
    private MachinistEntity repairman;

    @ManyToOne
    @JoinColumn(name = "plane_id")
    private PlaneEntity plane;
}

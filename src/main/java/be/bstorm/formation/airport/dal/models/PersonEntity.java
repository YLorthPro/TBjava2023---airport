package be.bstorm.formation.airport.dal.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Personne")
public abstract class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "personneId")
    private long id;
    @Column(name = "nom", nullable = false)
    private String name;
    @Column(name = "adresse", nullable = false)
    private String address;
    @Column(name = "tel", nullable = false)
    private String phone;
}

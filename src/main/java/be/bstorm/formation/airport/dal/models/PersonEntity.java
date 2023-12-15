package be.bstorm.formation.airport.dal.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
/*
 L'annotation Inheritance est utilisée pour définir la stratégie d'héritage à utiliser pour une hiérarchie de classe.
 InheritanceType.JOINED est une stratégie qui mappe chaque classe concrète à sa propre table.
 Les colonnes qui sont héritées sont incluses dans chaque table.
 */
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

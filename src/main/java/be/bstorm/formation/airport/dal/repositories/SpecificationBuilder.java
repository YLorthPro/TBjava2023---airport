package be.bstorm.formation.airport.dal.repositories;

import be.bstorm.formation.airport.dal.models.InterventionEntity;
import be.bstorm.formation.airport.dal.models.PlaneEntity;
import be.bstorm.formation.airport.dal.models.PlaneTypeEntity;
import be.bstorm.formation.airport.dal.models.ToPilotEntity;
import be.bstorm.formation.airport.pl.models.forms.InterventionSearchForm;
import be.bstorm.formation.airport.pl.models.forms.PlaneTypeSearchForm;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder {
    public static Specification<PlaneEntity> specificationBuilder(String numIma, String ownerName){
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if(numIma != null && !numIma.isEmpty())
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("numIma")), "%" + numIma + "%"));

            if(ownerName != null && !ownerName.isEmpty())
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("ownerEntities").get("name")), "%" + ownerName.toLowerCase() + "%"));


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<InterventionEntity> specificationBuilder(InterventionSearchForm form) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            //cb.lower -> résultats colonne en miniscule
            if (form.object() != null && !form.object().isEmpty())
                predicates.add(cb.like(cb.lower(root.get("object")), "%" + form.object().toLowerCase() + "%"));

            if (form.date() != null)
                predicates.add(cb.equal(root.get("date"), form.date()));

            if (form.duration() > 0)
                predicates.add(cb.equal(root.get("duration"), form.duration()));

            if (form.verifierId() > 0)
                predicates.add(cb.equal(root.get("verifier").get("id"), form.verifierId()));

            if (form.repairmanId() > 0)
                predicates.add(cb.equal(root.get("repairman").get("id"), form.repairmanId()));

            if (form.planeId() != null)
                predicates.add(cb.like(cb.lower(root.get("plane").get("id")), "%" + form.planeId().toLowerCase() + "%"));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<PlaneTypeEntity> specificationBuilder(PlaneTypeSearchForm form){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (form.name() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + form.name().toLowerCase() + "%"));
            }

            if (form.builder() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("builder")), "%" + form.builder().toLowerCase() + "%"));
            }

            if (form.power() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("power"), form.power()));
            }

            if (form.seatsNumber() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("seatsNumber"), form.seatsNumber()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<ToPilotEntity> specificationBuilder(Long pilotId, Long planeTypeId){

        return (root, query, cb)->{
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("pilote_id"), pilotId));

            if(planeTypeId != null)
                predicates.add(cb.equal(root.get("plane_type_id"),planeTypeId));

            return cb.and(predicates.toArray(new Predicate[0]));
        };

    }
}

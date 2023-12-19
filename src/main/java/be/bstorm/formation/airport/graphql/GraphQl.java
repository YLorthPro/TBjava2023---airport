package be.bstorm.formation.airport.graphql;

import be.bstorm.formation.airport.dal.models.InterventionEntity;
import be.bstorm.formation.airport.dal.models.MachinistEntity;
import be.bstorm.formation.airport.dal.models.PlaneEntity;
import be.bstorm.formation.airport.dal.models.PlaneTypeEntity;
import be.bstorm.formation.airport.dal.repositories.InterventionRepository;
import be.bstorm.formation.airport.dal.repositories.PlaneRepository;
import be.bstorm.formation.airport.dal.repositories.PlaneTypeRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GraphQl {

    private final PlaneRepository planeRepository;
    private final PlaneTypeRepository planeTypeRepository;
    private final InterventionRepository interventionRepository;

    public GraphQl(PlaneRepository planeRepository,
                   PlaneTypeRepository planeTypeRepository,
                   InterventionRepository interventionRepository) {
        this.planeRepository = planeRepository;
        this.planeTypeRepository = planeTypeRepository;
        this.interventionRepository = interventionRepository;
    }

    /* Annotation indiquant que cette méthode est un "Query Resolver" dans GraphQL.
     Cette méthode est capable de répondre à une requête GraphQL
     demandant des informations sur un type d'avion spécifique, identifié par son ID.*/
    @QueryMapping
    public PlaneTypeEntity planeTypeById(@Argument Long id) {
        return planeTypeRepository.findById(id).orElse(null);
    }

    /* Annotation indiquant que cette méthode est un "Query Resolver" dans GraphQL.
     Cette méthode est capable de répondre à une requête GraphQL
     demandant des informations sur un avion spécifique, identifié par son numéro d'immatriculation (numIma).*/
    @QueryMapping
    public PlaneEntity planeById(@Argument String numIma) {
        return planeRepository.findById(numIma).orElse(null);
    }

    /* Annotation indiquant que cette méthode est un "Query Resolver" dans GraphQL.
    C'est-à-dire qu'elle est capable de répondre à une requête GraphQL demandant des données sur des avions.*/
    @QueryMapping
    public List<PlaneEntity> planes(){
        return planeRepository.findAll();
    }

    /*     Annotation indiquant que cette méthode est un "Type Resolver" dans GraphQL pour le type "Plane".
     Cette méthode est appelée par GraphQL lorsqu'il a besoin d'informations complémentaires sur une entité d'avion
     (par exemple, le type de l'avion qui est une autre entité).*/
    @SchemaMapping
    public PlaneTypeEntity getPlaneTypeEntity(PlaneEntity planeEntity) {
        return planeEntity.getPlaneTypeEntity();
    }

    @QueryMapping
    public List<InterventionEntity> interventions(){
        return interventionRepository.findAll();
    }

    @QueryMapping
    public InterventionEntity interventionById(@Argument Long id){
        return interventionRepository.findById(id).get();
    }

    @SchemaMapping
    public List<InterventionEntity> getInterventionsByPlane(PlaneEntity planeEntity) {
        return planeEntity.getInterventionEntities();
    }
    @SchemaMapping
    public MachinistEntity getRepairmanByIntervention(InterventionEntity interventionEntity) {
        return interventionEntity.getRepairman();
    }
}

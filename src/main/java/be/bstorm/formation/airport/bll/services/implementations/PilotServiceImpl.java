package be.bstorm.formation.airport.bll.services.implementations;

import be.bstorm.formation.airport.bll.models.FlightResume;
import be.bstorm.formation.airport.bll.services.PilotService;
import be.bstorm.formation.airport.dal.models.PilotEntity;
import be.bstorm.formation.airport.dal.models.PlaneTypeEntity;
import be.bstorm.formation.airport.dal.models.ToPilotEntity;
import be.bstorm.formation.airport.dal.models.ToPilotEntityCompositeKey;
import be.bstorm.formation.airport.dal.repositories.ToPilotRepository;
import be.bstorm.formation.airport.dal.repositories.PilotRepository;
import be.bstorm.formation.airport.dal.repositories.PlaneTypeRepository;
import be.bstorm.formation.airport.pl.models.dto.Pilot;
import be.bstorm.formation.airport.pl.models.dto.PlaneType;
import be.bstorm.formation.airport.pl.models.forms.PilotForm;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class PilotServiceImpl implements PilotService {

    private final PilotRepository pilotRepository;
    private final PlaneTypeRepository planeTypeRepository;
    private final ToPilotRepository toPilotRepository;

    public PilotServiceImpl(PilotRepository pilotRepository,
                            PlaneTypeRepository planeTypeRepository,
                            ToPilotRepository toPilotRepository) {
        this.pilotRepository = pilotRepository;
        this.planeTypeRepository = planeTypeRepository;
        this.toPilotRepository = toPilotRepository;
    }


    @Override
    public Page<PilotEntity> getAll(Pageable pageable) {
        return pilotRepository.findAll(pageable);
    }


    @Override
    public Optional<PilotEntity> getById(Long id) {
        return pilotRepository.findById(id);
    }

    @Override
    public void save(PilotForm form) {
        if(form == null)
            throw new IllegalArgumentException("Form can't be null");

        PilotEntity pilotEntity = new PilotEntity();
        pilotEntity.setName(form.name());
        pilotEntity.setAddress(form.address());
        pilotEntity.setPhone(form.phone());
        pilotEntity.setLicenseNumber(form.licenseNumber());
        /*
        la méthode findAllById récupère tous les types d'avions dans `planeTypeRepository`
        correspondant aux ID fournis par `form.planeTypes()`.

        Le résultat est ensuite transformé en un Stream pour pouvoir manipuler les données en utilisant Java Stream API.

        La méthode flatMap, elle s'applique à chaque élément du Stream et remplace chaque élément par le contenu de ce Stream.
        Elle est généralement utilisée pour aplatir une structure de données complexe. Dans ce cas,
        nous remplaçons chaque type d'avion par sa liste de pilotes (`pt.getToPilots().stream()`).

        Enfin, la méthode distinct filtre le Stream pour s'assurer que tous les pilotes sont uniques, éliminant ainsi les doublons.
        Le résultat est un Stream de pilotes uniques pour les types d'avions spécifiés.
        */
        pilotEntity.setToPilotEntityList(planeTypeRepository.findAllById(form.planeTypes()).stream()
                .flatMap(pt->pt.getToPilots().stream().distinct())
                .toList()
        );
        pilotRepository.save(pilotEntity);
    }

    @Override
    public void deleteById(Long id) {
        pilotRepository.deleteById(id);
    }

    @Override
    public void update(PilotForm form, Long id) {
        if(form == null)
            throw new IllegalArgumentException("Form can't be null");

        PilotEntity pilotEntity = pilotRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Pilot not found"));
        pilotEntity.setName(form.name());
        pilotEntity.setAddress(form.address());
        pilotEntity.setPhone(form.phone());
        pilotEntity.setLicenseNumber(form.licenseNumber());
        pilotEntity.setToPilotEntityList(planeTypeRepository.findAllById(form.planeTypes()).stream()
                .flatMap(pt->pt.getToPilots().stream().distinct())
                .toList()
        );
        pilotRepository.save(pilotEntity);
    }

    @Override
    public void addFlight(Long planeTypeId, Long pilotId) {
        PlaneTypeEntity planeTypeEntity = planeTypeRepository.findById(planeTypeId).orElseThrow(()->new EntityNotFoundException("Plane type not found"));
        PilotEntity pilotEntity = pilotRepository.findById(pilotId).orElseThrow(()-> new EntityNotFoundException("Pilot not found"));

        // Tente de rechercher une entité "ToPilotEntity" existante dans la base de données en utilisant les entités "pilotEntity" et "planeTypeEntity" respectivement comme clé de pilote et type d'avion
        ToPilotEntity toPilotEntity = toPilotRepository.findOne(specificationBuilder(pilotEntity.getId(), planeTypeEntity.getId()))
                // Si aucune entité correspondante n'est trouvée, exécute la méthode orElseGet() qui crée une nouvelle entité
                .orElseGet(()->{
                    // Création d'une nouvelle instance de ToPilotEntity
                    ToPilotEntity toPilot = new ToPilotEntity();

                    // Définition de pilotEntity comme pilote de l'entité toPilot
                    toPilot.setPilot(pilotEntity);

                    // Définition de planeTypeEntity comme type d'avion de l'entité toPilot
                    toPilot.setPlaneType(planeTypeEntity);

                    // Création d'une nouvelle clé composite avec pilotEntity et planeTypeEntity,
                    // puis assignation de cette clé comme ID de l'entité toPilot
                    ToPilotEntityCompositeKey key = new ToPilotEntityCompositeKey(pilotEntity,planeTypeEntity);
                    toPilot.setId(key);

                    // Initialisation du nombre de vols à 0
                    toPilot.setFlightsNumber(0);

                    // Sauvegarde de l'entité toPilot nouvellement créée dans la base de données,
                    // puis retourne cette entité en cas d'absence de l'entité recherchée initialement
                    return toPilotRepository.save(toPilot);
                });

        toPilotEntity.setFlightsNumber(toPilotEntity.getFlightsNumber()+1);

        toPilotRepository.save(toPilotEntity);
    }

    @Override
    public FlightResume getResume(Long pilotId) {
        PilotEntity pilot = pilotRepository.findById(pilotId).orElseThrow(() -> new EntityNotFoundException("Pilot not found"));

        HashMap<PlaneType, Integer> resume = new HashMap<>();
        toPilotRepository.findAll(specificationBuilder(pilotId, null))
                .forEach(toPilotEntity -> resume.put(PlaneType.fromBll(toPilotEntity.getPlaneType()), toPilotEntity.getFlightsNumber()));
        return new FlightResume(Pilot.fromBll(pilot), resume);
    }

    @Override
    public void updateResume(Long pilotId, Long planeTypeId, int flightNumber) {
        ToPilotEntity toPilotEntity = toPilotRepository.findOne(specificationBuilder(pilotId,planeTypeId)).orElseThrow(()->new EntityNotFoundException("ToPilot not found"));
        toPilotEntity.setFlightsNumber(flightNumber);
        toPilotRepository.save(toPilotEntity);
    }

    private Specification<ToPilotEntity> specificationBuilder(Long pilotId, Long planeTypeId){

        return (root, query, cb)->{
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("pilote_id"), pilotId));

            if(planeTypeId != null)
                predicates.add(cb.equal(root.get("plane_type_id"),planeTypeId));

            return cb.and(predicates.toArray(new Predicate[0]));
        };

    }
}

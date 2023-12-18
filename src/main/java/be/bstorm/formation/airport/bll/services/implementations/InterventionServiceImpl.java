package be.bstorm.formation.airport.bll.services.implementations;

import be.bstorm.formation.airport.bll.services.InterventionService;
import be.bstorm.formation.airport.dal.models.InterventionEntity;
import be.bstorm.formation.airport.dal.repositories.InterventionRepository;
import be.bstorm.formation.airport.dal.repositories.MachinistRepository;
import be.bstorm.formation.airport.dal.repositories.PlaneRepository;
import be.bstorm.formation.airport.pl.models.forms.InterventionForm;
import be.bstorm.formation.airport.pl.models.forms.InterventionSearchForm;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InterventionServiceImpl implements InterventionService {

    private final InterventionRepository interventionRepository;
    private final PlaneRepository planeRepository;
    private final MachinistRepository machinistRepository;

    public InterventionServiceImpl(InterventionRepository interventionRepository,
                                   PlaneRepository planeRepository,
                                   MachinistRepository machinistRepository) {
        this.interventionRepository = interventionRepository;
        this.planeRepository = planeRepository;
        this.machinistRepository = machinistRepository;
    }

    @Override
    public Page<InterventionEntity> getAll(Pageable pageable) {
        return interventionRepository.findAll(pageable);
    }

    @Override
    public Optional<InterventionEntity> getById(Long id) {
        return interventionRepository.findById(id);
    }

    @Override
    public void save(InterventionForm form) {

        if(form == null)
            throw new IllegalArgumentException("Form can't be null");

        if (form.repairmanId()== form.verifierId())
            throw new IllegalArgumentException("Verifier can't be the repairman");

        if(!machinistRepository.findById(form.repairmanId()).orElseThrow(() -> new EntityNotFoundException("Repairman not found")).isQualifiedFor(planeRepository.findById(form.planeId()).orElseThrow(() -> new EntityNotFoundException("Plane not found"))))
            throw new IllegalArgumentException("Repaiman has no abilities for this plane");

        InterventionEntity entity = new InterventionEntity();
        entity.setDate(form.date());
        entity.setDuration(form.duration());
        entity.setObject(form.object());
        entity.setPlane(planeRepository.findById(form.planeId()).orElseThrow(()->new EntityNotFoundException("Plane not found")));
        entity.setVerifier(machinistRepository.findById(form.verifierId()).orElseThrow(()->new EntityNotFoundException("Verifier not found")));
        entity.setRepairman(machinistRepository.findById(form.repairmanId()).orElseThrow(()->new EntityNotFoundException("Repairman not found")));
        interventionRepository.save(entity);
    }

    @Override
    public void update(InterventionForm form, Long id) {
        if (form == null)
            throw new IllegalArgumentException("Form can't be null");

        if (form.repairmanId() == form.verifierId())
            throw new IllegalArgumentException("Verifier can't be the repairman");

        if(!machinistRepository.findById(form.repairmanId()).orElseThrow(()->new EntityNotFoundException("Repairman not found")).getPlaneTypeEntities().contains(planeRepository.findById(form.planeId()).orElseThrow(()->new EntityNotFoundException("Plane not found")).getPlaneTypeEntity()))
            throw new IllegalArgumentException("Repaiman has no abilities for this plane");

        InterventionEntity entity = interventionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Intervention not found"));

        entity.setDate(form.date());
        entity.setDuration(form.duration());
        entity.setObject(form.object());
        entity.setPlane(planeRepository.findById(form.planeId())
                .orElseThrow(() -> new EntityNotFoundException("Plane not found")));
        entity.setVerifier(machinistRepository.findById(form.verifierId())
                .orElseThrow(() -> new EntityNotFoundException("Verifier not found")));
        entity.setRepairman(machinistRepository.findById(form.repairmanId())
                .orElseThrow(() -> new EntityNotFoundException("Repairman not found")));

        interventionRepository.save(entity);
    }

    @Override
    public List<InterventionEntity> search(InterventionSearchForm form) {
        return interventionRepository.findAll(specificationBuilder(form));
    }

    @Override
    public void deleteById(Long id) {
        interventionRepository.deleteById(id);
    }

    private Specification<InterventionEntity> specificationBuilder(InterventionSearchForm form) {
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
}

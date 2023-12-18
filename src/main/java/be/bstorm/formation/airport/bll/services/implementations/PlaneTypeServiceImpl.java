package be.bstorm.formation.airport.bll.services.implementations;

import be.bstorm.formation.airport.bll.services.PlaneTypeService;
import be.bstorm.formation.airport.dal.models.PlaneTypeEntity;
import be.bstorm.formation.airport.dal.repositories.PlaneTypeRepository;
import be.bstorm.formation.airport.pl.models.forms.PlaneTypeForm;
import be.bstorm.formation.airport.pl.models.forms.PlaneTypeSearchForm;
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
public class PlaneTypeServiceImpl implements PlaneTypeService {

    private final PlaneTypeRepository planeTypeRepository;

    public PlaneTypeServiceImpl(PlaneTypeRepository planeTypeRepository) {
        this.planeTypeRepository = planeTypeRepository;
    }


    @Override
    public Page<PlaneTypeEntity> getAll(Pageable pageable) {
        return planeTypeRepository.findAll(pageable);
    }


    @Override
    public Optional<PlaneTypeEntity> getById(Long id) {
        return planeTypeRepository.findById(id);
    }

    @Override
    public void save(PlaneTypeForm form) {

        if(form == null)
            throw new IllegalArgumentException("Form can't be null");

        PlaneTypeEntity planeTypeEntity = new PlaneTypeEntity();
        planeTypeEntity.setName(form.name());
        planeTypeEntity.setBuilder(form.builder());
        planeTypeEntity.setPower(form.power());
        planeTypeEntity.setSeatsNumber(form.seatsNumber());

        planeTypeRepository.save(planeTypeEntity);
    }

    @Override
    public void deleteById(Long id) {
        planeTypeRepository.deleteById(id);
    }

    @Override
    public void update(PlaneTypeForm form, Long id) {
        if(form == null)
            throw new IllegalArgumentException("Form can't be null");

        PlaneTypeEntity planeTypeEntity = planeTypeRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Entity not found"));
        planeTypeEntity.setName(form.name());
        planeTypeEntity.setBuilder(form.builder());
        planeTypeEntity.setPower(form.power());
        planeTypeEntity.setSeatsNumber(form.seatsNumber());

        planeTypeRepository.save(planeTypeEntity);
    }

    @Override
    public List<PlaneTypeEntity> search(PlaneTypeSearchForm form) {
        return planeTypeRepository.findAll(specificationBuilder(form));
    }

    private Specification<PlaneTypeEntity> specificationBuilder(PlaneTypeSearchForm form){
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
}

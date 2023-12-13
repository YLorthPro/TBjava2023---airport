package be.bstorm.formation.airport.bll.services.implementations;

import be.bstorm.formation.airport.bll.services.PilotService;
import be.bstorm.formation.airport.dal.models.PilotEntity;
import be.bstorm.formation.airport.dal.repositories.PilotRepository;
import be.bstorm.formation.airport.dal.repositories.PlaneTypeRepository;
import be.bstorm.formation.airport.pl.models.forms.PilotForm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PilotServiceImpl implements PilotService {

    private final PilotRepository pilotRepository;
    private final PlaneTypeRepository planeTypeRepository;

    public PilotServiceImpl(PilotRepository pilotRepository,
                            PlaneTypeRepository planeTypeRepository) {
        this.pilotRepository = pilotRepository;
        this.planeTypeRepository = planeTypeRepository;
    }


    @Override
    public List<PilotEntity> getAll() {
        return pilotRepository.findAll();
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
        pilotEntity.setToPilotEntityList(planeTypeRepository.findAllById(form.planeTypes()).stream()
                .flatMap(pt->pt.getPilots().stream())
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
                .flatMap(pt->pt.getPilots().stream())
                .toList()
        );
        pilotRepository.save(pilotEntity);
    }
}

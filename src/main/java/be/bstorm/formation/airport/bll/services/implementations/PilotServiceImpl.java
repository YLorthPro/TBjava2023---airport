package be.bstorm.formation.airport.bll.services.implementations;

import be.bstorm.formation.airport.bll.models.FlightResume;
import be.bstorm.formation.airport.bll.services.PilotService;
import be.bstorm.formation.airport.dal.models.PilotEntity;
import be.bstorm.formation.airport.dal.models.PlaneTypeEntity;
import be.bstorm.formation.airport.dal.models.ToPilotEntity;
import be.bstorm.formation.airport.dal.repositories.ToPilotRepository;
import be.bstorm.formation.airport.dal.repositories.PilotRepository;
import be.bstorm.formation.airport.dal.repositories.PlaneTypeRepository;
import be.bstorm.formation.airport.pl.models.dto.Pilot;
import be.bstorm.formation.airport.pl.models.dto.PlaneType;
import be.bstorm.formation.airport.pl.models.forms.PilotForm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

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

        ToPilotEntity toPilotEntity = toPilotRepository.findByPilotAndPlaneType(pilotEntity,planeTypeEntity).orElseThrow(()->new EntityNotFoundException("Resume not found"));

        toPilotEntity.setFlightsNumber(toPilotEntity.getFlightsNumber()+1);

        toPilotRepository.save(toPilotEntity);
    }

    @Override
    public FlightResume getResume(Long pilotId) {
        PilotEntity pilot = pilotRepository.findById(pilotId).orElseThrow(() -> new EntityNotFoundException("Pilot not found"));
        HashMap<PlaneType, Integer> resume = new HashMap<>();
        toPilotRepository.findByPilot(pilot)
                .forEach(toPilotEntity -> resume.put(PlaneType.fromBll(toPilotEntity.getPlaneType()), toPilotEntity.getFlightsNumber()));
        return new FlightResume(Pilot.fromBll(pilot), resume);
    }

    @Override
    public void updateResume(Long pilotId, Long planeTypeId, int flightNumber) {
        ToPilotEntity toPilotEntity = toPilotRepository.findByPilotAndPlaneType(pilotRepository.findById(pilotId).orElseThrow(() -> new EntityNotFoundException("Pilot not found")), planeTypeRepository.findById(planeTypeId).orElseThrow(() -> new EntityNotFoundException("Plane type not found"))).orElseThrow(() -> new EntityNotFoundException("Resume not found"));
        toPilotEntity.setFlightsNumber(flightNumber);
        toPilotRepository.save(toPilotEntity);
    }
}

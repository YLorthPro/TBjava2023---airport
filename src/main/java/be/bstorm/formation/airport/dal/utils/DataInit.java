package be.bstorm.formation.airport.dal.utils;

import be.bstorm.formation.airport.dal.models.*;
import be.bstorm.formation.airport.dal.repositories.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.github.javafaker.Faker;

import java.util.*;

@Component

public class DataInit implements InitializingBean {

    private final PlaneTypeRepository planeTypeRepository;
    private final OwnerRepository ownerRepository;
    private final PilotRepository pilotRepository;
    private final PlaneRepository planeRepository;
    private final MachinistRepository machinistRepository;
    private final ToPilotRepository toPilotRepository;

    @Value("${api.data-init}")
    private boolean insertion;

    public DataInit(PlaneTypeRepository planeTypeRepository,
                    OwnerRepository ownerRepository,
                    PilotRepository pilotRepository,
                    PlaneRepository planeRepository,
                    MachinistRepository machinistRepository,
                    ToPilotRepository toPilotRepository) {
        this.planeTypeRepository = planeTypeRepository;
        this.ownerRepository = ownerRepository;
        this.pilotRepository = pilotRepository;
        this.planeRepository = planeRepository;
        this.machinistRepository = machinistRepository;
        this.toPilotRepository = toPilotRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(insertion){
            Faker faker = new Faker();
            for (int i = 0; i < 50; i++) {
                PlaneTypeEntity planeType = new PlaneTypeEntity();
                planeType.setName(faker.lorem().word());
                planeType.setBuilder(faker.company().name());
                planeType.setPower(faker.number().numberBetween(100, 1000));
                planeType.setSeatsNumber(faker.number().numberBetween(2, 200));
                planeTypeRepository.save(planeType);
            }
            List<OwnerEntity> owners = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                OwnerEntity owner = new OwnerEntity();
                owner.setName(faker.name().fullName());
                owner.setAddress(faker.address().fullAddress());
                owner.setPhone(faker.phoneNumber().phoneNumber());
                owners.add(owner);
                ownerRepository.save(owner);
            }
            for (int i = 0; i < 50; i++) {
                PilotEntity pilot = new PilotEntity();
                pilot.setName(faker.name().fullName());
                pilot.setAddress(faker.address().fullAddress());
                pilot.setPhone(faker.phoneNumber().phoneNumber());
                pilot.setLicenseNumber(faker.number().numberBetween(10000, 99999));
                pilotRepository.save(pilot);
            }
            for (int i = 0; i < 50; i++) {
                PlaneEntity plane = new PlaneEntity();
                plane.setNumIma(faker.lorem().word());
                plane.setPlaneTypeEntity(planeTypeRepository.findById((long) faker.number().numberBetween(1, 50)).orElse(null));
                int a = faker.number().numberBetween(0, owners.size());
                plane.setOwnerEntities(List.of(owners.get(a)));
                owners.remove(a);
                plane.setInterventionEntities(new ArrayList<>());
                planeRepository.save(plane);
            }
            for (int i = 0; i < 50; i++) {
                MachinistEntity machinist = new MachinistEntity();
                machinist.setName(faker.name().fullName());
                machinist.setAddress(faker.address().fullAddress());
                machinist.setPhone(faker.phoneNumber().phoneNumber());

                machinist.getPlaneTypeEntities().add(planeTypeRepository.findById((long) faker.number().numberBetween(1, 50)).orElse(null));
                machinistRepository.save(machinist);
            }
            for (int i = 0; i < 50; i++) {
                ToPilotEntity toPilot = new ToPilotEntity();
                ToPilotEntityCompositeKey toPilotEntityCompositeKey = new ToPilotEntityCompositeKey();

                PilotEntity pilot = pilotRepository.findById((long) faker.number().numberBetween(1, 50)).orElse(null);
                PlaneTypeEntity planeType = planeTypeRepository.findById((long) faker.number().numberBetween(1, 50)).orElse(null);

                if (pilot != null) {
                    toPilotEntityCompositeKey.setPilotId(pilot.getId());
                } else {
                    continue;
                }

                if (planeType != null) {
                    toPilotEntityCompositeKey.setPlaneTypeId(planeType.getId());
                } else {
                    continue;
                }

                toPilot.setId(toPilotEntityCompositeKey);
                toPilot.setFlightsNumber(faker.number().numberBetween(0, 500));

                toPilotRepository.save(toPilot);
            }
        }
    }
}

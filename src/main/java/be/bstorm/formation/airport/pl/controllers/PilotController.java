package be.bstorm.formation.airport.pl.controllers;

import be.bstorm.formation.airport.bll.models.FlightResume;
import be.bstorm.formation.airport.bll.services.PilotService;
import be.bstorm.formation.airport.pl.models.dto.Pilot;
import be.bstorm.formation.airport.pl.models.forms.PilotForm;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pilots")
public class PilotController {

    private final PilotService pilotService;

    public PilotController(PilotService pilotService) {
        this.pilotService = pilotService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Pilot>> getAll(Pageable pageable) {
        return ResponseEntity.ok(pilotService.getAll(pageable).stream().map(Pilot::fromBll).toList());
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<Pilot> getById(@PathVariable Long id) {
        return ResponseEntity.ok(Pilot.fromBll(pilotService.getById(id).orElseThrow(()->new EntityNotFoundException("Pilot not found"))));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid PilotForm form) {
        pilotService.save(form);
    }

    @PutMapping("/{id:[0-9]+}")
    public void update(@RequestBody @Valid PilotForm form, @PathVariable Long id) {
        pilotService.update(form, id);
    }

    @DeleteMapping("/{id:[0-9]+}")
    public void deleteById(@PathVariable Long id) {
        pilotService.deleteById(id);
    }

    @GetMapping("/{id:[0-9]+}/resume")
    public ResponseEntity<FlightResume> getResume(@PathVariable Long id) {
        return ResponseEntity.ok(pilotService.getResume(id));
    }

    @PostMapping("/{pilotId:[0-9]+}/add-flight/{planeTypeId:[0-9]+}")
    public void addFlight(@PathVariable Long pilotId, @PathVariable Long planeTypeId) {
        pilotService.addFlight(planeTypeId, pilotId);
    }

    @PutMapping("/{pilotId:[0-9]+}/update-resume/{planeTypeId:[0-9]+}")
    public void updateResume(@PathVariable Long pilotId, @PathVariable Long planeTypeId, @RequestParam int flightNumber) {
        pilotService.updateResume(pilotId, planeTypeId, flightNumber);
    }
}

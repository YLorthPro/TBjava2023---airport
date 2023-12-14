package be.bstorm.formation.airport.pl.controllers;

import be.bstorm.formation.airport.bll.services.InterventionService;
import be.bstorm.formation.airport.pl.models.dto.Intervention;
import be.bstorm.formation.airport.pl.models.forms.InterventionForm;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interventions")
public class InterventionController {

    private final InterventionService interventionService;

    public InterventionController(InterventionService interventionService) {
        this.interventionService = interventionService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Intervention>> getAllInterventions() {
        return ResponseEntity.ok(interventionService.getAll().stream()
                .map(Intervention::fromBll)
                .toList());
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<Intervention> getInterventionById(@PathVariable Long id) {
        return ResponseEntity.ok(Intervention.fromBll(interventionService.getById(id).orElseThrow(()->new EntityNotFoundException("Intervention not found"))));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createIntervention(@RequestBody @Valid InterventionForm form) {
        interventionService.save(form);
    }

    @PutMapping("/{id:[0-9]+}")
    public void updateIntervention(@RequestBody @Valid InterventionForm form, @PathVariable Long id) {
        interventionService.update(form, id);
    }

    @DeleteMapping("/{id:[0-9]+}")
    public void deleteIntervention(@PathVariable Long id) {
        interventionService.deleteById(id);
    }
}

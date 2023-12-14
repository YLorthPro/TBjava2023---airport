package be.bstorm.formation.airport.pl.controllers;

import be.bstorm.formation.airport.bll.services.MachinistService;
import be.bstorm.formation.airport.pl.models.dto.Machinist;
import be.bstorm.formation.airport.pl.models.forms.MachinistForm;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/machinist")
public class MachinistController {
    private final MachinistService machinistService;

    public MachinistController(MachinistService machinistService) {
        this.machinistService = machinistService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Machinist>> getAll() {
        return ResponseEntity.ok(machinistService.getAll().stream()
                .map(Machinist::fromBll)
                .toList());
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<Machinist> getById(@PathVariable Long id) {
        return ResponseEntity.ok(Machinist.fromBll(machinistService.getById(id).orElseThrow(()->new EntityNotFoundException("machinist not found"))));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid MachinistForm form) {
        machinistService.save(form);
    }

    @PutMapping("/{id:[0-9]+}")
    public void update(@RequestBody @Valid MachinistForm form, @PathVariable Long id) {
        machinistService.update(form, id);
    }

    @DeleteMapping("/{id:[0-9]+}")
    public void delete(@PathVariable Long id) {
        machinistService.deleteById(id);
    }
}

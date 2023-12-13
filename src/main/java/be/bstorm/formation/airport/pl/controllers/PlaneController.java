package be.bstorm.formation.airport.pl.controllers;

import be.bstorm.formation.airport.bll.services.PlaneService;
import be.bstorm.formation.airport.pl.models.dto.Plane;
import be.bstorm.formation.airport.pl.models.forms.PlaneForm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planes")
public class PlaneController {

    private final PlaneService planeService;

    public PlaneController(PlaneService planeService) {
        this.planeService = planeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Plane>> getAll() {
        return ResponseEntity.ok(planeService.getAll().stream()
                .map(Plane::fromBll)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plane> getOne(@PathVariable String id){
        return ResponseEntity.ok(planeService.getById(id)
                .map(Plane::fromBll)
                .orElseThrow(() -> new EntityNotFoundException("Plane not found")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody PlaneForm form) {
        planeService.save(form);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody PlaneForm form, @PathVariable String id) {
        planeService.update(form, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        planeService.deleteById(id);
    }
}


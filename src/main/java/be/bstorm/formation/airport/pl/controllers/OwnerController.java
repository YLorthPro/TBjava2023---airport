package be.bstorm.formation.airport.pl.controllers;

import be.bstorm.formation.airport.bll.services.OwnerService;
import be.bstorm.formation.airport.pl.models.dto.Owner;
import be.bstorm.formation.airport.pl.models.dto.Owner;
import be.bstorm.formation.airport.pl.models.forms.OwnerForm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public ResponseEntity<List<Owner>> getAll() {
        return ResponseEntity.ok(ownerService.getAll().stream()
                .map(Owner::fromBll)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Owner> getById(@PathVariable Long id) {
        return ResponseEntity.ok(Owner.fromBll(ownerService.getById(id).orElseThrow(()->new EntityNotFoundException("owner not found"))));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody OwnerForm form) {
        ownerService.save(form);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody OwnerForm form, @PathVariable Long id) {
        ownerService.update(form, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ownerService.deleteById(id);
    }
}

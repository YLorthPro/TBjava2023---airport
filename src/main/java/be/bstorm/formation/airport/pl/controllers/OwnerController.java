package be.bstorm.formation.airport.pl.controllers;

import be.bstorm.formation.airport.bll.services.OwnerService;
import be.bstorm.formation.airport.pl.models.dto.Owner;
import be.bstorm.formation.airport.pl.models.dto.Owner;
import be.bstorm.formation.airport.pl.models.forms.OwnerForm;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/all")
    public ResponseEntity<List<Owner>> getAll(Pageable pageable) {
        return ResponseEntity.ok(ownerService.getAll(pageable).stream()
                .map(Owner::fromBll)
                .toList());
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<Owner> getById(@PathVariable Long id) {
        return ResponseEntity.ok(Owner.fromBll(ownerService.getById(id).orElseThrow(()->new EntityNotFoundException("owner not found"))));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid OwnerForm form) {
        ownerService.save(form);
    }

    @PutMapping("/{id:[0-9]+}")
    public void update(@RequestBody @Valid OwnerForm form, @PathVariable Long id) {
        ownerService.update(form, id);
    }

    @DeleteMapping("/{id:[0-9]+}")
    public void delete(@PathVariable Long id) {
        ownerService.deleteById(id);
    }
}

package be.bstorm.formation.airport.pl.controllers;

import be.bstorm.formation.airport.bll.services.PlaneTypeService;
import be.bstorm.formation.airport.pl.models.dto.PlaneType;
import be.bstorm.formation.airport.pl.models.forms.PlaneTypeForm;
import be.bstorm.formation.airport.pl.models.forms.PlaneTypeSearchForm;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planeTypes")
public class PlaneTypeController {

    private final PlaneTypeService planeTypeService;

    public PlaneTypeController(PlaneTypeService planeTypeService) {
        this.planeTypeService = planeTypeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlaneType>> getAll(Pageable pageable) {
        return ResponseEntity.ok(planeTypeService.getAll(pageable).stream().map(PlaneType::fromBll).toList());
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<PlaneType> getById(@PathVariable Long id) {
        return ResponseEntity.ok(PlaneType.fromBll(planeTypeService.getById(id).orElseThrow(()->new EntityNotFoundException("Plane type not found"))));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid PlaneTypeForm form) {
        planeTypeService.save(form);
    }

    @PutMapping("/{id:[0-9]+}")
    public void update(@RequestBody @Valid PlaneTypeForm form, @PathVariable Long id) {
        planeTypeService.update(form, id);
    }

    @DeleteMapping("/{id:[0-9]+}")
    public void deleteById(@PathVariable Long id) {
        planeTypeService.deleteById(id);
    }

    @PostMapping("/search")
    public ResponseEntity<List<PlaneType>> search(@RequestBody PlaneTypeSearchForm form){
        return ResponseEntity.ok(planeTypeService.search(form).stream().map(PlaneType::fromBll).toList());
    }

}

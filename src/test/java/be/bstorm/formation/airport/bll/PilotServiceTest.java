package be.bstorm.formation.airport.bll;

import be.bstorm.formation.airport.bll.services.implementations.PilotServiceImpl;
import be.bstorm.formation.airport.dal.models.OwnerEntity;
import be.bstorm.formation.airport.dal.models.PilotEntity;
import be.bstorm.formation.airport.dal.models.PlaneTypeEntity;
import be.bstorm.formation.airport.dal.repositories.PilotRepository;
import be.bstorm.formation.airport.dal.repositories.PlaneTypeRepository;
import be.bstorm.formation.airport.pl.models.forms.PilotForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PilotServiceTest {
    @Mock
    private PilotRepository pilotRepository;

    @Mock
    private PlaneTypeRepository planeTypeRepository;

    @InjectMocks
    private PilotServiceImpl pilotService;

    @Test
    public void testGetAll() {

        PilotEntity pilotEntity = new PilotEntity();
        List<PilotEntity> pilotEntityList = List.of(pilotEntity);
        Pageable pageable = PageRequest.of(0,5);
        Page<PilotEntity> page = new PageImpl<>(pilotEntityList);
        when(pilotRepository.findAll(pageable)).thenReturn(page);


        Page<PilotEntity> result = pilotService.getAll(pageable);


        assertEquals(1, result.getContent().size());
        assertEquals(pilotEntity, result.getContent().get(0));
    }

    @Test
    public void testGetById(){

        PilotEntity pilotEntity = new PilotEntity();
        when(pilotRepository.findById(anyLong())).thenReturn(Optional.of(pilotEntity));

        Optional<PilotEntity> result = pilotService.getById(1L);

        assertEquals(Optional.of(pilotEntity), result);

    }

    @Test
    public void testGetByIdNotFound(){

        when(pilotRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<PilotEntity> result = pilotService.getById(1L);

        assertTrue(result.isEmpty());

    }

    @Test
    public void testSave(){

        PlaneTypeEntity planeType1 = new PlaneTypeEntity();
        planeType1.setToPilots(new ArrayList<>());

        PlaneTypeEntity planeType2 = new PlaneTypeEntity();
        planeType2.setToPilots(new ArrayList<>());

        List<PlaneTypeEntity> planeTypeEntities = Arrays.asList(planeType1, planeType2);

        PilotForm form = new PilotForm("name", "address", "phone",25, Set.of(1L,2L));

        when(planeTypeRepository.findAllById(form.planeTypes())).thenReturn(planeTypeEntities);

        pilotService.save(form);

        verify(pilotRepository).save(any(PilotEntity.class));
    }

    @Test
    public void testSaveNullForm(){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->pilotService.save(null));

        assertEquals("Form can't be null", exception.getMessage());

    }

    @Test
    public void testUpdate() {

        PlaneTypeEntity planeType1 = new PlaneTypeEntity();
        planeType1.setToPilots(new ArrayList<>());

        PlaneTypeEntity planeType2 = new PlaneTypeEntity();
        planeType2.setToPilots(new ArrayList<>());

        List<PlaneTypeEntity> planeTypeEntities = Arrays.asList(planeType1, planeType2);

        PilotForm form = new PilotForm("name", "address", "phone",25, Set.of(1L,2L));
        when(planeTypeRepository.findAllById(form.planeTypes())).thenReturn(planeTypeEntities);

        PilotEntity pilotEntity = new PilotEntity();
        when(pilotRepository.findById(anyLong())).thenReturn(Optional.of(pilotEntity));

        pilotService.update(form, 1L);

        verify(pilotRepository).save(pilotEntity);
    }

    @Test
    public void testUpdateNullForm(){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->pilotService.update(null, 1L));

        assertEquals("Form can't be null", exception.getMessage());

    }

    @Test
    public void testDeleteById() {

        pilotService.deleteById(1L);

        verify(pilotRepository).deleteById(1L);
    }
    
}

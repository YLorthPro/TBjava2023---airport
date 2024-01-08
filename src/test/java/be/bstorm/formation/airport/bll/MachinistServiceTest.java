package be.bstorm.formation.airport.bll;

import be.bstorm.formation.airport.bll.services.implementations.MachinistServiceImpl;
import be.bstorm.formation.airport.dal.models.MachinistEntity;
import be.bstorm.formation.airport.dal.models.PlaneTypeEntity;
import be.bstorm.formation.airport.dal.repositories.MachinistRepository;
import be.bstorm.formation.airport.dal.repositories.PlaneTypeRepository;
import be.bstorm.formation.airport.pl.models.forms.MachinistForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MachinistServiceTest {
    @Mock
    private MachinistRepository machinistRepository;

    @Mock
    private PlaneTypeRepository planeTypeRepository;

    @InjectMocks
    private MachinistServiceImpl machinistService;

    @Test
    public void testGetAll() {

        MachinistEntity machinistEntity = new MachinistEntity();
        List<MachinistEntity> machinistEntityList = List.of(machinistEntity);
        Pageable pageable = PageRequest.of(0,5);
        Page<MachinistEntity> page = new PageImpl<>(machinistEntityList);
        when(machinistRepository.findAll(pageable)).thenReturn(page);


        Page<MachinistEntity> result = machinistService.getAll(pageable);


        assertEquals(1, result.getContent().size());
        assertEquals(machinistEntity, result.getContent().get(0));
    }

    @Test
    public void testGetById(){

        MachinistEntity machinistEntity = new MachinistEntity();
        when(machinistRepository.findById(anyLong())).thenReturn(Optional.of(machinistEntity));

        Optional<MachinistEntity> result = machinistService.getById(1L);

        assertEquals(Optional.of(machinistEntity), result);

    }

    @Test
    public void testGetByIdNotFound(){

        when(machinistRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<MachinistEntity> result = machinistService.getById(1L);

        assertTrue(result.isEmpty());

    }

    @Test
    public void testSave(){

        MachinistForm form = new MachinistForm("name", "address", "phone", Arrays.asList(1L, 2L));
        List<PlaneTypeEntity> planeTypeEntities = Arrays.asList(new PlaneTypeEntity(), new PlaneTypeEntity());
        when(planeTypeRepository.findAllById(form.planeTypeId())).thenReturn(planeTypeEntities);

        machinistService.save(form);

        verify(machinistRepository).save(any(MachinistEntity.class));
    }

    @Test
    public void testSaveNullForm(){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->machinistService.save(null));

        assertEquals("form can't be null", exception.getMessage());

    }

    @Test
    public void testUpdate() {

        MachinistForm form = new MachinistForm("name", "address", "phone", Arrays.asList(1L, 2L));
        List<PlaneTypeEntity> planeTypeEntities = Arrays.asList(new PlaneTypeEntity(), new PlaneTypeEntity());
        when(planeTypeRepository.findAllById(form.planeTypeId())).thenReturn(planeTypeEntities);
        MachinistEntity machinistEntity = new MachinistEntity();
        when(machinistRepository.findById(anyLong())).thenReturn(Optional.of(machinistEntity));

        machinistService.update(form, 1L);

        verify(machinistRepository).save(machinistEntity);
    }

    @Test
    public void testUpdateNullForm(){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->machinistService.update(null, 1L));

        assertEquals("form can't be null", exception.getMessage());

    }

    @Test
    public void testDeleteById() {

        machinistService.deleteById(1L);

        verify(machinistRepository).deleteById(1L);
    }


}

package be.bstorm.formation.airport.bll;

import be.bstorm.formation.airport.bll.services.implementations.OwnerServiceImpl;
import be.bstorm.formation.airport.dal.models.OwnerEntity;
import be.bstorm.formation.airport.dal.repositories.OwnerRepository;
import be.bstorm.formation.airport.pl.models.forms.OwnerForm;
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
public class OwnerServiceTest {
    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerServiceImpl ownerService;

    @Test
    public void testGetAll() {

        OwnerEntity ownerEntity = new OwnerEntity();
        List<OwnerEntity> ownerEntityList = List.of(ownerEntity);
        Pageable pageable = PageRequest.of(0,5);
        Page<OwnerEntity> page = new PageImpl<>(ownerEntityList);
        when(ownerRepository.findAll(pageable)).thenReturn(page);


        Page<OwnerEntity> result = ownerService.getAll(pageable);


        assertEquals(1, result.getContent().size());
        assertEquals(ownerEntity, result.getContent().get(0));
    }

    @Test
    public void testGetById(){

        OwnerEntity ownerEntity = new OwnerEntity();
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(ownerEntity));

        Optional<OwnerEntity> result = ownerService.getById(1L);

        assertEquals(Optional.of(ownerEntity), result);

    }

    @Test
    public void testGetByIdNotFound(){

        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<OwnerEntity> result = ownerService.getById(1L);

        assertTrue(result.isEmpty());

    }

    @Test
    public void testSave(){

        OwnerForm form = new OwnerForm("name", "address", "phone", Arrays.asList("Ima1", "Ima2"));

        ownerService.save(form);

        verify(ownerRepository).save(any(OwnerEntity.class));
    }

    @Test
    public void testSaveNullForm(){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->ownerService.save(null));

        assertEquals("Form can't be null", exception.getMessage());

    }

    @Test
    public void testUpdate() {

        OwnerForm form = new OwnerForm("name", "address", "phone", Arrays.asList("Ima1", "Ima2"));
        OwnerEntity ownerEntity = new OwnerEntity();
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(ownerEntity));

        ownerService.update(form, 1L);

        verify(ownerRepository).save(ownerEntity);
    }

    @Test
    public void testUpdateNullForm(){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->ownerService.update(null, 1L));

        assertEquals("Form can't be null", exception.getMessage());

    }

    @Test
    public void testDeleteById() {

        ownerService.deleteById(1L);

        verify(ownerRepository).deleteById(1L);
    }

}

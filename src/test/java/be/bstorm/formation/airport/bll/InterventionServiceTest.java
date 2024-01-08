package be.bstorm.formation.airport.bll;

import be.bstorm.formation.airport.bll.services.implementations.InterventionServiceImpl;
import be.bstorm.formation.airport.dal.models.InterventionEntity;
import be.bstorm.formation.airport.dal.models.MachinistEntity;
import be.bstorm.formation.airport.dal.models.PlaneEntity;
import be.bstorm.formation.airport.dal.models.PlaneTypeEntity;
import be.bstorm.formation.airport.dal.repositories.InterventionRepository;
import be.bstorm.formation.airport.dal.repositories.MachinistRepository;
import be.bstorm.formation.airport.dal.repositories.PlaneRepository;
import be.bstorm.formation.airport.pl.models.forms.InterventionForm;
import be.bstorm.formation.airport.pl.models.forms.InterventionSearchForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InterventionServiceTest {

    @InjectMocks
    private InterventionServiceImpl interventionService;

    @Mock
    private InterventionRepository interventionRepository;

    @Mock
    private MachinistRepository machinistRepository;

    @Mock
    private PlaneRepository planeRepository;

    @Test
    public void testGetAll() {
        // Given = Arrange
        InterventionEntity intervention = new InterventionEntity();
        List<InterventionEntity> interventions = Collections.singletonList(intervention);
        Page<InterventionEntity> interventionPage = new PageImpl<>(interventions);

        // When = Act
        when(interventionRepository.findAll(Pageable.unpaged())).thenReturn(interventionPage);

        // Then = Assert
        Page<InterventionEntity> result = interventionService.getAll(Pageable.unpaged());
        assertEquals(interventionPage, result);
    }

    @Test
    public void testGetById() {
        // Given
        Long givenId = 1L;
        InterventionEntity intervention = new InterventionEntity();
        when(interventionRepository.findById(givenId)).thenReturn(Optional.of(intervention));

        // When
        Optional<InterventionEntity> result = interventionService.getById(givenId);

        // Then
        assertEquals(Optional.of(intervention), result);
    }

    @Test
    public void testGetByIdNotFound() {
        // Given
        Long givenId = 1L;
        when(interventionRepository.findById(givenId)).thenReturn(Optional.empty());

        // When
        Optional<InterventionEntity> result = interventionService.getById(givenId);

        // Then
        assertTrue(result.isEmpty());

    }

    @Test
    public void testSave_ValidForm() {
        // Form
        InterventionForm form = new InterventionForm("test", LocalDate.now(), 10, 1L, 2L, "LKKDP");

        // PlaneType
        PlaneTypeEntity planeType = new PlaneTypeEntity();

        // Repairman with the required Plane Type
        MachinistEntity repairman = new MachinistEntity();
        repairman.setPlaneTypeEntities(Collections.singletonList(planeType));
        when(machinistRepository.findById(1L)).thenReturn(Optional.of(repairman));

        // Verifier with the required Plane Type
        MachinistEntity verifier = new MachinistEntity();
        verifier.setPlaneTypeEntities(Collections.singletonList(planeType));
        when(machinistRepository.findById(2L)).thenReturn(Optional.of(verifier));

        // Plane with the required Plane Type
        PlaneEntity plane = new PlaneEntity();
        plane.setPlaneTypeEntity(planeType);
        when(planeRepository.findById("LKKDP")).thenReturn(Optional.of(plane));

        // Mocked Intervention Entity
        InterventionEntity intervention = new InterventionEntity();
        when(interventionRepository.save(any())).thenReturn(intervention);


        interventionService.save(form);

        verify(interventionRepository, times(1)).save(any());
    }

    @Test
    public void testSave_NullForm() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->interventionService.save(null));
        assertEquals("Form can't be null", exception.getMessage());
    }

    @Test
    public void testSave_VerifierIsRepairman() throws Exception {

            InterventionForm form = new InterventionForm("test", LocalDate.now(),10,1L,1L,"LKKDP");

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,()->interventionService.save(form));

            assertEquals("Verifier can't be the repairman", exception.getMessage());
    }

    @Test
    public void testSave_RepairmanNotQualified() {
        // Given
        PlaneTypeEntity planeType = new PlaneTypeEntity();
        PlaneEntity plane = new PlaneEntity();
        plane.setPlaneTypeEntity(planeType);
        MachinistEntity machinist = new MachinistEntity();
        machinist.setPlaneTypeEntities(List.of());

        when(machinistRepository.findById(anyLong())).thenReturn(Optional.of(machinist));
        when(planeRepository.findById(anyString())).thenReturn(Optional.empty());
        InterventionForm form = new InterventionForm("Test", LocalDate.now(), 10, 1L, 2L, "TEST");
        when(planeRepository.findById(anyString())).thenReturn(Optional.of(plane));
        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,()->interventionService.save(form));


        // Then
        verify(machinistRepository, times(1)).findById(anyLong());
        verify(planeRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(interventionRepository, machinistRepository, planeRepository);
        assertEquals("Repaiman has no abilities for this plane", exception.getMessage());
    }

    @Test
    public void testUpdate_ValidForm() {
        // Form
        InterventionForm form = new InterventionForm("test", LocalDate.now(), 10, 1L, 2L, "LKKDP");

        // PlaneType
        PlaneTypeEntity planeType = new PlaneTypeEntity();

        // Repairman with the required Plane Type
        MachinistEntity repairman = new MachinistEntity();
        repairman.setPlaneTypeEntities(Collections.singletonList(planeType));
        when(machinistRepository.findById(1L)).thenReturn(Optional.of(repairman));

        // Verifier with the required Plane Type
        MachinistEntity verifier = new MachinistEntity();
        verifier.setPlaneTypeEntities(Collections.singletonList(planeType));
        when(machinistRepository.findById(2L)).thenReturn(Optional.of(verifier));

        // Plane with the required Plane Type
        PlaneEntity plane = new PlaneEntity();
        plane.setPlaneTypeEntity(planeType);
        when(planeRepository.findById("LKKDP")).thenReturn(Optional.of(plane));

        // Mocked Intervention Entity
        InterventionEntity intervention = new InterventionEntity();
        when(interventionRepository.findById(1L)).thenReturn(Optional.of(intervention));
        when(interventionRepository.save(any())).thenReturn(intervention);

        interventionService.update(form, 1L);

        verify(interventionRepository, times(1)).findById(any());
        verify(interventionRepository, times(1)).save(any());
    }

    @Test
    public void testUpdate_NullForm() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> interventionService.update(null, 1L));
        assertEquals("Form can't be null", exception.getMessage());
    }

    @Test
    public void testUpdate_VerifierIsRepairman() throws Exception {

        InterventionForm form = new InterventionForm("test", LocalDate.now(),10,1L,1L,"LKKDP");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,()->interventionService.update(form, 1L));

        assertEquals("Verifier can't be the repairman", exception.getMessage());
    }

    @Test
    public void testUpdate_RepairmanNotQualified() {

        // Given
        PlaneTypeEntity planeType = new PlaneTypeEntity();
        PlaneEntity plane = new PlaneEntity();
        plane.setPlaneTypeEntity(planeType);
        MachinistEntity machinist = new MachinistEntity();
        machinist.setPlaneTypeEntities(List.of());  // Empty list

        when(machinistRepository.findById(anyLong())).thenReturn(Optional.of(machinist));
        InterventionForm form = new InterventionForm("Test", LocalDate.now(), 10, 1L, 2L, "TEST");
        when(planeRepository.findById(anyString())).thenReturn(Optional.of(plane));

        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> interventionService.update(form,1L));

        // Then
        verify(machinistRepository, times(1)).findById(anyLong());
        verify(planeRepository, times(1)).findById(anyString());

        verifyNoMoreInteractions(interventionRepository, machinistRepository, planeRepository);
        assertEquals("Repaiman has no abilities for this plane", exception.getMessage());

    }

    @Test
    public void testSearch() {
        InterventionSearchForm form = new InterventionSearchForm("",LocalDate.now(),0,1L,2L,"");
        List<InterventionEntity> interventions = new ArrayList<>();

        List<InterventionEntity> result = interventionService.search(form);

        assertEquals(interventions, result);
    }

    @Test
    public void testDeleteById() {
        Long givenId = 1L;

        interventionService.deleteById(givenId);

        verify(interventionRepository, times(1)).deleteById(givenId);
    }

}

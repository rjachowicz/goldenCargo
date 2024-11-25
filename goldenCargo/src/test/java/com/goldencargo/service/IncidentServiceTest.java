package com.goldencargo.service;

import com.goldencargo.model.data.Status;
import com.goldencargo.model.entities.Incident;
import com.goldencargo.repository.IncidentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IncidentServiceTest {

    @Mock
    private IncidentRepository incidentRepository;

    @InjectMocks
    private IncidentService incidentService;

    private Incident incident;

    @BeforeEach
    public void setUp() {
        incident = new Incident();
        incident.setIncidentId(1L);
        incident.setIncidentType("Test Incident");
        incident.setDate(new Date());
        incident.setDescription("Test description");
        incident.setStatus(Status.NEW);
        incident.setCreatedAt(new Date());
        incident.setUpdatedAt(new Date());
    }

    @Test
    public void testGetAllIncidents() {
        List<Incident> incidents = Collections.singletonList(incident);
        when(incidentRepository.findAll()).thenReturn(incidents);

        List<Incident> result = incidentService.getAllIncidents();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(incident.getIncidentType(), result.getFirst().getIncidentType());
        verify(incidentRepository, times(1)).findAll();
    }

    @Test
    public void testGetIncidentById() {
        when(incidentRepository.findById(1L)).thenReturn(Optional.of(incident));

        Optional<Incident> result = incidentService.getIncidentById(1L);

        assertTrue(result.isPresent());
        assertEquals(incident.getIncidentType(), result.get().getIncidentType());
        verify(incidentRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateIncident() {
        when(incidentRepository.save(incident)).thenReturn(incident);

        Incident result = incidentService.createIncident(incident);

        assertNotNull(result);
        assertEquals(incident.getIncidentType(), result.getIncidentType());
        verify(incidentRepository, times(1)).save(incident);
    }

    @Test
    public void testUpdateIncident() {
        when(incidentRepository.findById(1L)).thenReturn(Optional.of(incident));
        when(incidentRepository.save(incident)).thenReturn(incident);

        Optional<Incident> result = incidentService.updateIncident(1L, incident);

        assertTrue(result.isPresent());
        assertEquals(incident.getIncidentType(), result.get().getIncidentType());
        verify(incidentRepository, times(1)).findById(1L);
        verify(incidentRepository, times(1)).save(incident);
    }

    @Test
    public void testDeleteIncident() {
        when(incidentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(incidentRepository).deleteById(1L);

        boolean result = incidentService.deleteIncident(1L);

        assertTrue(result);
        verify(incidentRepository, times(1)).existsById(1L);
        verify(incidentRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteIncident_NotFound() {
        when(incidentRepository.existsById(1L)).thenReturn(false);

        boolean result = incidentService.deleteIncident(1L);

        assertFalse(result);
        verify(incidentRepository, times(1)).existsById(1L);
        verify(incidentRepository, never()).deleteById(1L);
    }
}

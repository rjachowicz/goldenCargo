package com.goldencargo.service;

import com.goldencargo.model.entities.DriverReport;
import com.goldencargo.repository.DriverReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DriverReportServiceTest {

    @Mock
    private DriverReportRepository driverReportRepository;

    @InjectMocks
    private DriverReportService driverReportService;

    private DriverReport driverReport;

    @BeforeEach
    public void setUp() {
        driverReport = new DriverReport();
        driverReport.setDriverReportId(1L);
        driverReport.setDate(new Date());
        driverReport.setContent("Test report content");
        driverReport.setCreatedAt(new Date());
        driverReport.setUpdatedAt(new Date());
    }

    @Test
    public void testGetAllDriverReports() {
        List<DriverReport> driverReports = Collections.singletonList(driverReport);
        when(driverReportRepository.findAll()).thenReturn(driverReports);

        List<DriverReport> result = driverReportService.getAllDriverReports();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(driverReport.getContent(), result.getFirst().getContent());
        verify(driverReportRepository, times(1)).findAll();
    }

    @Test
    public void testGetDriverReportById() {
        when(driverReportRepository.findById(1L)).thenReturn(Optional.of(driverReport));

        Optional<DriverReport> result = driverReportService.getDriverReportById(1L);

        assertTrue(result.isPresent());
        assertEquals(driverReport.getContent(), result.get().getContent());
        verify(driverReportRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateDriverReport() {
        when(driverReportRepository.save(driverReport)).thenReturn(driverReport);

        DriverReport result = driverReportService.createDriverReport(driverReport);

        assertNotNull(result);
        assertEquals(driverReport.getContent(), result.getContent());
        verify(driverReportRepository, times(1)).save(driverReport);
    }

    @Test
    public void testUpdateDriverReport() {
        when(driverReportRepository.findById(1L)).thenReturn(Optional.of(driverReport));
        when(driverReportRepository.save(driverReport)).thenReturn(driverReport);

        Optional<DriverReport> result = driverReportService.updateDriverReport(1L, driverReport);

        assertTrue(result.isPresent());
        assertEquals(driverReport.getContent(), result.get().getContent());
        verify(driverReportRepository, times(1)).findById(1L);
        verify(driverReportRepository, times(1)).save(driverReport);
    }

    @Test
    public void testDeleteDriverReport() {
        when(driverReportRepository.existsById(1L)).thenReturn(true);
        doNothing().when(driverReportRepository).deleteById(1L);

        boolean result = driverReportService.deleteDriverReport(1L);

        assertTrue(result);
        verify(driverReportRepository, times(1)).existsById(1L);
        verify(driverReportRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteDriverReport_NotFound() {
        when(driverReportRepository.existsById(1L)).thenReturn(false);

        boolean result = driverReportService.deleteDriverReport(1L);

        assertFalse(result);
        verify(driverReportRepository, times(1)).existsById(1L);
        verify(driverReportRepository, never()).deleteById(1L);
    }
}
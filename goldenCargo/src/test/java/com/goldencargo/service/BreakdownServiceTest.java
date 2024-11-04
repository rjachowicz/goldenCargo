package com.goldencargo.service;

import com.goldencargo.model.entities.Breakdown;
import com.goldencargo.repository.BreakdownRepository;
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
public class BreakdownServiceTest {

    @Mock
    private BreakdownRepository breakdownRepository;

    @InjectMocks
    private BreakdownService breakdownService;

    private Breakdown breakdown;

    @BeforeEach
    public void setUp() {
        breakdown = new Breakdown();
        breakdown.setBreakdownId(1L);
        breakdown.setDescription("Test description");
        breakdown.setRepairCost(500.0);
        breakdown.setRepairDate(new Date());
        breakdown.setCreatedAt(new Date());
        breakdown.setUpdatedAt(new Date());
    }

    @Test
    public void testGetAllBreakdowns() {
        List<Breakdown> breakdowns = Collections.singletonList(breakdown);
        when(breakdownRepository.findAll()).thenReturn(breakdowns);

        List<Breakdown> result = breakdownService.getAllBreakdowns();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(breakdown.getDescription(), result.getFirst().getDescription());
        verify(breakdownRepository, times(1)).findAll();
    }

    @Test
    public void testGetBreakdownById() {
        when(breakdownRepository.findById(1L)).thenReturn(Optional.of(breakdown));

        Optional<Breakdown> result = breakdownService.getBreakdownById(1L);

        assertTrue(result.isPresent());
        assertEquals(breakdown.getDescription(), result.get().getDescription());
        verify(breakdownRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateBreakdown() {
        when(breakdownRepository.save(breakdown)).thenReturn(breakdown);

        Breakdown result = breakdownService.createBreakdown(breakdown);

        assertNotNull(result);
        assertEquals(breakdown.getDescription(), result.getDescription());
        verify(breakdownRepository, times(1)).save(breakdown);
    }

    @Test
    public void testUpdateBreakdown() {
        when(breakdownRepository.findById(1L)).thenReturn(Optional.of(breakdown));
        when(breakdownRepository.save(breakdown)).thenReturn(breakdown);

        Optional<Breakdown> result = breakdownService.updateBreakdown(1L, breakdown);

        assertTrue(result.isPresent());
        assertEquals(breakdown.getDescription(), result.get().getDescription());
        verify(breakdownRepository, times(1)).findById(1L);
        verify(breakdownRepository, times(1)).save(breakdown);
    }

    @Test
    public void testDeleteBreakdown() {
        when(breakdownRepository.existsById(1L)).thenReturn(true);
        doNothing().when(breakdownRepository).deleteById(1L);

        boolean result = breakdownService.deleteBreakdown(1L);

        assertTrue(result);
        verify(breakdownRepository, times(1)).existsById(1L);
        verify(breakdownRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteBreakdown_NotFound() {
        when(breakdownRepository.existsById(1L)).thenReturn(false);

        boolean result = breakdownService.deleteBreakdown(1L);

        assertFalse(result);
        verify(breakdownRepository, times(1)).existsById(1L);
        verify(breakdownRepository, never()).deleteById(1L);
    }
}

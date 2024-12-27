package com.goldencargo.service;

import com.goldencargo.model.entities.Damage;
import com.goldencargo.repository.DamageRepository;
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
public class DamageServiceTest {

    @Mock
    private DamageRepository damageRepository;

    @InjectMocks
    private DamageService damageService;

    private Damage damage;

    @BeforeEach
    public void setUp() {
        damage = new Damage();
        damage.setDamageId(1L);
        damage.setDescription("Test damage");
        damage.setRepairCost(500.0);
        damage.setInsuranceClaim(true);
        damage.setClaimNumber("CLM-12345");
        damage.setCreatedAt(new Date());
        damage.setUpdatedAt(new Date());
    }

    @Test
    public void testGetAllDamages() {
        List<Damage> damages = Collections.singletonList(damage);
        when(damageRepository.findAll()).thenReturn(damages);

        List<Damage> result = damageService.getAllDamages();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(damage.getDescription(), result.getFirst().getDescription());
        verify(damageRepository, times(1)).findAll();
    }

    @Test
    public void testGetDamageById() {
        when(damageRepository.findById(1L)).thenReturn(Optional.of(damage));

        Optional<Damage> result = damageService.getDamageById(1L);

        assertTrue(result.isPresent());
        assertEquals(damage.getDescription(), result.get().getDescription());
        verify(damageRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateDamage() {
        when(damageRepository.save(damage)).thenReturn(damage);

        Damage result = damageService.createDamage(damage);

        assertNotNull(result);
        assertEquals(damage.getDescription(), result.getDescription());
        verify(damageRepository, times(1)).save(damage);
    }

    @Test
    public void testUpdateDamage() {
        when(damageRepository.findById(1L)).thenReturn(Optional.of(damage));
        when(damageRepository.save(damage)).thenReturn(damage);

        Optional<Damage> result = damageService.updateDamage(1L, damage);

        assertTrue(result.isPresent());
        assertEquals(damage.getDescription(), result.get().getDescription());
        verify(damageRepository, times(1)).findById(1L);
        verify(damageRepository, times(1)).save(damage);
    }

    @Test
    public void testDeleteDamage() {
        when(damageRepository.existsById(1L)).thenReturn(true);
        doNothing().when(damageRepository).deleteById(1L);

        boolean result = damageService.deleteDamage(1L);

        assertTrue(result);
        verify(damageRepository, times(1)).existsById(1L);
        verify(damageRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteDamage_NotFound() {
        when(damageRepository.existsById(1L)).thenReturn(false);

        boolean result = damageService.deleteDamage(1L);

        assertFalse(result);
        verify(damageRepository, times(1)).existsById(1L);
        verify(damageRepository, never()).deleteById(1L);
    }
}
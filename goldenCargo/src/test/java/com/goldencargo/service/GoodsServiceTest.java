package com.goldencargo.service;

import com.goldencargo.model.entities.Goods;
import com.goldencargo.repository.GoodsRepository;
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
public class GoodsServiceTest {

    @Mock
    private GoodsRepository goodsRepository;

    @InjectMocks
    private GoodsService goodsService;

    private Goods goods;

    @BeforeEach
    public void setUp() {
        goods = new Goods();
        goods.setGoodsId(1L);
        goods.setName("Sample Goods");
        goods.setDescription("Description of sample goods");
        goods.setWeight(20.5);
        goods.setDimensions("50x30x20");
        goods.setSpecialHandlingInstructions("Handle with care");
        goods.setCreatedAt(new Date());
        goods.setUpdatedAt(new Date());
    }

    @Test
    public void testGetAllGoods() {
        List<Goods> goodsList = Collections.singletonList(goods);
        when(goodsRepository.findAll()).thenReturn(goodsList);

        List<Goods> result = goodsService.getAllGoods();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(goods.getName(), result.getFirst().getName());
        verify(goodsRepository, times(1)).findAll();
    }

    @Test
    public void testGetGoodsById() {
        when(goodsRepository.findById(1L)).thenReturn(Optional.of(goods));

        Optional<Goods> result = goodsService.getGoodsById(1L);

        assertTrue(result.isPresent());
        assertEquals(goods.getName(), result.get().getName());
        verify(goodsRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateGoods() {
        when(goodsRepository.save(goods)).thenReturn(goods);

        Goods result = goodsService.createGoods(goods);

        assertNotNull(result);
        assertEquals(goods.getName(), result.getName());
        verify(goodsRepository, times(1)).save(goods);
    }

    @Test
    public void testUpdateGoods() {
        when(goodsRepository.findById(1L)).thenReturn(Optional.of(goods));
        when(goodsRepository.save(goods)).thenReturn(goods);

        Optional<Goods> result = goodsService.updateGoods(1L, goods);

        assertTrue(result.isPresent());
        assertEquals(goods.getName(), result.get().getName());
        verify(goodsRepository, times(1)).findById(1L);
        verify(goodsRepository, times(1)).save(goods);
    }

    @Test
    public void testDeleteGoods() {
        when(goodsRepository.existsById(1L)).thenReturn(true);
        doNothing().when(goodsRepository).deleteById(1L);

        boolean result = goodsService.deleteGoods(1L);

        assertTrue(result);
        verify(goodsRepository, times(1)).existsById(1L);
        verify(goodsRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteGoods_NotFound() {
        when(goodsRepository.existsById(1L)).thenReturn(false);

        boolean result = goodsService.deleteGoods(1L);

        assertFalse(result);
        verify(goodsRepository, times(1)).existsById(1L);
        verify(goodsRepository, never()).deleteById(1L);
    }
}

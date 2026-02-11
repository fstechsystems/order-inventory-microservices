package com.company.supply.inventory.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.company.supply.inventory.entity.InventoryBatch;
import com.company.supply.inventory.factory.InventoryStrategyFactory;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

    @Mock
    InventoryStrategyFactory factory;

    @Mock
    InventoryStrategy strategy;

    @InjectMocks
    InventoryService service;

    @Test
    void shouldReturnBatches() {
        when(factory.getStrategy("EXPIRY")).thenReturn(strategy);
        when(strategy.getBatches(1001L)).thenReturn(List.of(new InventoryBatch()));

        assertEquals(1, service.getInventory(1001L).size());
    }
}
package com.company.supply.inventory.factory;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.company.supply.inventory.service.InventoryStrategy;

@Component
public class InventoryStrategyFactory {

    private final Map<String, InventoryStrategy> strategies;

    public InventoryStrategyFactory(List<InventoryStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(s -> "EXPIRY", Function.identity()));
    }

    public InventoryStrategy getStrategy(String type) {
        return strategies.getOrDefault(type, strategies.get("EXPIRY"));
    }
}
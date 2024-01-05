package de.hannezhd.dungeoncrusher.utils;

import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class MobHealthBuilder {

    private final Map<EntityType, Double> initialHealthMap;

    public MobHealthBuilder() {
        // Hier kannst du die initialen Lebenspunkte für verschiedene Mobs festlegen
        this.initialHealthMap = new HashMap<>();
        this.initialHealthMap.put(EntityType.FROG, 50.0);
        this.initialHealthMap.put(EntityType.RABBIT,200.0);
        this.initialHealthMap.put(EntityType.CHICKEN,350.0);
        this.initialHealthMap.put(EntityType.CAT, 500.0);
        this.initialHealthMap.put(EntityType.FOX, 650.0);
        this.initialHealthMap.put(EntityType.WOLF, 800.0);
        this.initialHealthMap.put(EntityType.SHEEP, 950.0);
        this.initialHealthMap.put(EntityType.PIG, 1100.0);
        this.initialHealthMap.put(EntityType.COW, 1250.0);
        this.initialHealthMap.put(EntityType.SKELETON, 1000000.0);
        this.initialHealthMap.put(EntityType.IRON_GOLEM, 2000000.0);
    }

    public void applyAdjustments(LivingEntity entity) {
        EntityType entityType = entity.getType();
        if (initialHealthMap.containsKey(entityType)) {
            double initialHealth = initialHealthMap.get(entityType);
            setHealth(entity, initialHealth);
        }
    }

    private void setHealth(LivingEntity entity, double health) {
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        entity.setHealth(health);
    }
}

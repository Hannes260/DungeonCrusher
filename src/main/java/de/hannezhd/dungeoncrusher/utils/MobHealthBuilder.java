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
        this.initialHealthMap.put(EntityType.FROG, 24.0);
        this.initialHealthMap.put(EntityType.RABBIT,39.0);
        this.initialHealthMap.put(EntityType.CHICKEN,54.0);
        this.initialHealthMap.put(EntityType.CAT, 69.0);
        this.initialHealthMap.put(EntityType.FOX, 84.0);
        this.initialHealthMap.put(EntityType.WOLF, 99.0);
        this.initialHealthMap.put(EntityType.SHEEP, 114.0);
        this.initialHealthMap.put(EntityType.PIG, 129.0);
        this.initialHealthMap.put(EntityType.COW, 144.0);
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

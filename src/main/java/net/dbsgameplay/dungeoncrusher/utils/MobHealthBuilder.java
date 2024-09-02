package net.dbsgameplay.dungeoncrusher.utils;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;

public class MobHealthBuilder {

    private final Map<EntityType, Double> initialHealthMap;

    public MobHealthBuilder() {
        // Hier kannst du die initialen Lebenspunkte fÃ¼r verschiedene Mobs festlegen
        this.initialHealthMap = new HashMap<>();
        this.initialHealthMap.put(EntityType.SHEEP, 24.0);
        this.initialHealthMap.put(EntityType.PIG, 39.0);
        this.initialHealthMap.put(EntityType.COW, 54.0);
        this.initialHealthMap.put(EntityType.HORSE, 69.0);
        this.initialHealthMap.put(EntityType.DONKEY, 84.0);
        this.initialHealthMap.put(EntityType.CAMEL, 99.0);
        this.initialHealthMap.put(EntityType.FROG, 114.0);
        this.initialHealthMap.put(EntityType.GOAT, 129.0);
        this.initialHealthMap.put(EntityType.LLAMA, 144.0);
        this.initialHealthMap.put(EntityType.MOOSHROOM, 159.0);
        this.initialHealthMap.put(EntityType.MULE, 174.0);
        this.initialHealthMap.put(EntityType.SNIFFER, 189.0);
        this.initialHealthMap.put(EntityType.PANDA, 204.0);
        this.initialHealthMap.put(EntityType.TURTLE, 219.0);
        this.initialHealthMap.put(EntityType.OCELOT, 234.0);
        this.initialHealthMap.put(EntityType.AXOLOTL, 249.0);
        this.initialHealthMap.put(EntityType.FOX, 264.0);
        this.initialHealthMap.put(EntityType.CAT, 279.0);
        this.initialHealthMap.put(EntityType.CHICKEN, 294.0);
        this.initialHealthMap.put(EntityType.VILLAGER, 309.0);
        this.initialHealthMap.put(EntityType.RABBIT, 324.0);
        this.initialHealthMap.put(EntityType.SILVERFISH, 339.0);
        this.initialHealthMap.put(EntityType.VINDICATOR, 354.0);
        this.initialHealthMap.put(EntityType.POLAR_BEAR, 369.0);
        this.initialHealthMap.put(EntityType.ZOMBIE_HORSE, 384.0);
        this.initialHealthMap.put(EntityType.WOLF, 399.0);
        this.initialHealthMap.put(EntityType.ZOMBIE_VILLAGER, 414.0);
        this.initialHealthMap.put(EntityType.SNOW_GOLEM, 429.0);
        this.initialHealthMap.put(EntityType.SKELETON, 444.0);
        this.initialHealthMap.put(EntityType.DROWNED, 459.0);
        this.initialHealthMap.put(EntityType.HUSK, 474.0);
        this.initialHealthMap.put(EntityType.SPIDER, 489.0);
        this.initialHealthMap.put(EntityType.ZOMBIE, 504.0);
        this.initialHealthMap.put(EntityType.STRAY, 519.0);
        this.initialHealthMap.put(EntityType.CREEPER, 534.0);
        this.initialHealthMap.put(EntityType.CAVE_SPIDER, 549.0);
        this.initialHealthMap.put(EntityType.ENDERMITE, 564.0);
        this.initialHealthMap.put(EntityType.STRIDER, 579.0);
        this.initialHealthMap.put(EntityType.BLAZE, 594.0);
        this.initialHealthMap.put(EntityType.SKELETON_HORSE, 609.0);
        this.initialHealthMap.put(EntityType.WITCH, 624.0);
        this.initialHealthMap.put(EntityType.SLIME, 639.0);
        this.initialHealthMap.put(EntityType.MAGMA_CUBE, 654.0);
        this.initialHealthMap.put(EntityType.ENDERMAN, 669.0);
        this.initialHealthMap.put(EntityType.PIGLIN, 684.0);
        this.initialHealthMap.put(EntityType.ZOMBIFIED_PIGLIN, 699.0);
        this.initialHealthMap.put(EntityType.PIGLIN_BRUTE, 714.0);
        this.initialHealthMap.put(EntityType.PILLAGER, 729.0);
        this.initialHealthMap.put(EntityType.HOGLIN, 744.0);
        this.initialHealthMap.put(EntityType.EVOKER, 759.0);
        this.initialHealthMap.put(EntityType.GHAST, 774.0);
        this.initialHealthMap.put(EntityType.WITHER_SKELETON, 789.0);
        this.initialHealthMap.put(EntityType.ZOGLIN, 792.0);
        this.initialHealthMap.put(EntityType.RAVAGER, 819.0);
        this.initialHealthMap.put(EntityType.IRON_GOLEM, 834.0);
        this.initialHealthMap.put(EntityType.WARDEN, 849.0);
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
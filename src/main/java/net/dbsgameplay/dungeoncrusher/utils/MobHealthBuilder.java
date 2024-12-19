package net.dbsgameplay.dungeoncrusher.utils;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.utils.configs.HealthConfigManager;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class MobHealthBuilder {

    private final HealthConfigManager healthConfigManager;
    public MobHealthBuilder() {
        this.healthConfigManager = new HealthConfigManager(DungeonCrusher.getInstance());
    }

    public void setMobHealth(Entity entity) {
        if (entity instanceof LivingEntity) {
            String mobName = entity.getType().toString();
            double health = healthConfigManager.getMobHealth(mobName);

            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
            livingEntity.setHealth(health);
        }
    }
}
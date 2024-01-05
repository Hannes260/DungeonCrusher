package de.hannezhd.dungeoncrusher.listener;

import de.hannezhd.dungeoncrusher.utils.MobHealthBuilder;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class MobDamageListener implements Listener {

    private final MobHealthBuilder healthBuilder;

    public MobDamageListener(MobHealthBuilder healthBuilder) {
        this.healthBuilder = healthBuilder;
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            healthBuilder.applyAdjustments(entity);
            updateHealthBar(entity);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            updateHealthBar(entity);
        }
    }

    private void updateHealthBar(LivingEntity entity) {
        double maxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        double currentHealth = entity.getHealth();
        int hearts = (int) Math.ceil(currentHealth);

        String mobName = entity.getType().name();
        String healthDisplay = String.format("§a%s §8|||||§c%d/%d§8|||||", mobName, hearts, (int) maxHealth);
        entity.setCustomName(healthDisplay);
        entity.setCustomNameVisible(true);
    }
}

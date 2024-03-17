package net.dbsgameplay.dungeoncrusher.listener;

import net.dbsgameplay.dungeoncrusher.utils.MobHealthBuilder;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.HashMap;
import java.util.Map;

public class MobDamageListener implements Listener {

    private final Map<LivingEntity, String> mobNames = new HashMap<>();
    private final MobHealthBuilder healthBuilder;

    public MobDamageListener(MobHealthBuilder healthBuilder) {
        this.healthBuilder = healthBuilder;
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity && isAllowedEntity(entity)) {
            LivingEntity livingEntity = (LivingEntity) entity;
            healthBuilder.applyAdjustments(livingEntity);

            String mobName = livingEntity.getType().name();
            mobNames.put(livingEntity, mobName);

            // Hier können Sie andere Anpassungen vornehmen, z.B. die Healthbar anwenden
            updateHealthBar(livingEntity, mobName);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            String mobName = mobNames.get(livingEntity);
            if (mobName != null) {
                updateHealthBar(livingEntity, mobName);
            }
        }
    }

    private void updateHealthBar(LivingEntity entity, String mobName) {
        double maxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        double currentHealth = entity.getHealth();
        int hearts = (int) Math.ceil(currentHealth);

        String healthDisplay = String.format("§a%s §8|||||§c%d/%d§8|||||", mobName, hearts, (int) maxHealth);
        entity.setCustomName(healthDisplay);
        entity.setCustomNameVisible(true);
    }

    private boolean isAllowedEntity(Entity entity) {
        if (entity instanceof ArmorStand || entity instanceof ItemFrame) {
            return false; // Armorstand nicht erlauben
        }

        return true;
    }
}

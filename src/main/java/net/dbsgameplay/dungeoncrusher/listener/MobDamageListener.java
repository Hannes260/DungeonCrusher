package net.dbsgameplay.dungeoncrusher.listener;

import net.dbsgameplay.dungeoncrusher.utils.MobHealthBuilder;
import org.bukkit.attribute.Attribute;
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
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            healthBuilder.applyAdjustments(entity);

            String mobName = entity.getType().name();
            mobNames.put(entity, mobName);

            // Hier können Sie andere Anpassungen vornehmen, z.B. die Healthbar anwenden
            updateHealthBar(entity, mobName);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            String mobName = mobNames.get(entity);
            if (mobName != null) {
                updateHealthBar(entity, mobName);
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
}

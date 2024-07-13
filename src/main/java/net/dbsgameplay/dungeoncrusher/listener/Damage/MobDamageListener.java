package net.dbsgameplay.dungeoncrusher.listener.Damage;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.utils.MobHealthBuilder;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

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
            updateHealthBar(livingEntity, mobName);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity && event.getDamager() instanceof Player) {
            final LivingEntity livingEntity = (LivingEntity) entity;
            String mobName = mobNames.get(livingEntity);
            if (mobName != null) {
                updateHealthBar(livingEntity, mobName);
                new BukkitRunnable() {
                    int count = 0;

                    @Override
                    public void run() {
                        count++;
                        if (count >= 2) {
                            cancel();
                            return;
                        }
                        updateHealthBar(livingEntity, mobName);
                    }
                }.runTaskTimer(DungeonCrusher.getInstance(), 5L, 10L);
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
        return !(entity instanceof ArmorStand || entity instanceof ItemFrame);
    }
}

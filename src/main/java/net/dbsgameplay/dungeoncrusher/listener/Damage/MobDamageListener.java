package net.dbsgameplay.dungeoncrusher.listener.Damage;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.MobHealthBuilder;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class MobDamageListener implements Listener {

    private final Map<LivingEntity, String> mobNames = new HashMap<>();
    private final MobHealthBuilder healthBuilder;
    MYSQLManager mysqlManager;

    public MobDamageListener(MobHealthBuilder healthBuilder, MYSQLManager mysqlManager) {
        this.healthBuilder = healthBuilder;
        this.mysqlManager = mysqlManager;

    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity && isAllowedEntity(entity)) {
            LivingEntity livingEntity = (LivingEntity) entity;
            healthBuilder.setMobHealth(livingEntity);

            String mobName = livingEntity.getType().name();
            mobNames.put(livingEntity, mobName);
            updateHealthBar(livingEntity, mobName);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity) {
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
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof LivingEntity) {
            entity.setCustomName(null);  // Entfernt den Namen vor dem Tod
            entity.setCustomNameVisible(false);  // Macht den Namen unsichtbar
        }
    }
    private void updateHealthBar(LivingEntity entity, String mobName) {
        double maxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        double currentHealth = entity.getHealth();

        // Berechnung der prozentualen Gesundheit
        int percentage = (int) ((currentHealth / maxHealth) * 100);

        // Anzahl der aktiven Striche links und rechts
        int activeBars = percentage / 10; // Jeder 10% entspricht 1 Strich
        int leftBars = activeBars / 2;    // Hälfte der Striche links
        int rightBars = activeBars - leftBars; // Rest der Striche rechts

        // Gesundheitsanzeige mit links und rechts 5 Strichen aufbauen
        StringBuilder leftHealthBar = new StringBuilder("§e");
        for (int i = 0; i < 5; i++) {
            if (i < leftBars) {
                leftHealthBar.append("|"); // Aktive Striche links
            } else {
                leftHealthBar.append("§7|"); // Ausgegraute Striche links
            }
        }

        StringBuilder rightHealthBar = new StringBuilder("§e");
        for (int i = 0; i < 5; i++) {
            if (i < rightBars) {
                rightHealthBar.append("|"); // Aktive Striche rechts
            } else {
                rightHealthBar.append("§7|"); // Ausgegraute Striche rechts
            }
        }

        // Anzeige mit Mob-Namen, Gesundheitszahlen und dem symmetrischen Balken
        String healthDisplay = String.format("§a%s %s §c%d/%d %s", mobName, leftHealthBar, (int) currentHealth, (int) maxHealth, rightHealthBar);
        entity.setCustomName(healthDisplay);
        entity.setCustomNameVisible(true);
    }
    private boolean isAllowedEntity(Entity entity) {
        return !(entity instanceof ArmorStand || entity instanceof ItemFrame);
    }
}

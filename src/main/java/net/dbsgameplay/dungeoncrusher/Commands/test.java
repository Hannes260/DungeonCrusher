package net.dbsgameplay.dungeoncrusher.Commands;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

public class test implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
                Sheep sheep = (Sheep) player.getWorld().spawnEntity(player.getLocation(), EntityType.SHEEP);
                sheep.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.4);
                sheep.getAttribute(Attribute.GENERIC_WATER_MOVEMENT_EFFICIENCY).setBaseValue(0.3);
            new BukkitRunnable() {
                @Override
                public void run() {
                    sheep.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(120);
                    sheep.setHealth(120); // Setze die aktuelle Gesundheit auf das Maximum
                    updateHealthBar(sheep, "§aSchaf §cLvl.1");
                }
            }.runTaskLater(DungeonCrusher.getInstance(), 10L); // 10 Ticks Verzögerung
                followPlayer(sheep, player);
                applyDamageToNearbyPlayers(sheep);
                return true;
        }
        return false;
    }

    private void followPlayer(Sheep sheep, Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (sheep.isDead() || !player.isOnline()) {
                    this.cancel();
                    return;
                }

                if (sheep.getLocation().distance(player.getLocation()) > 50) {
                    sheep.setTarget(null); // Stoppe die Verfolgung
                    return;
                }

                sheep.setTarget(player);
                sheep.getPathfinder().moveTo(player);
            }
        }.runTaskTimer(DungeonCrusher.getInstance(), 0L, 5L);
    }
    private void applyDamageToNearbyPlayers(Sheep sheep) {
        new BukkitRunnable() {

            @Override
            public void run() {
                if (sheep.isDead()) {
                    this.cancel();
                    return;
                }
                for (Entity entity : sheep.getNearbyEntities(2,2,2)){
                    if (entity instanceof Player) {
                        Player nearbyPlayer = (Player) entity;
                        nearbyPlayer.damage(3.5);
                    }
                }
            }
        }.runTaskTimer(DungeonCrusher.getInstance(), 0L, 20L);
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
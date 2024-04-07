package net.dbsgameplay.dungeoncrusher.listener;

import net.dbsgameplay.dungeoncrusher.utils.LocationConfigManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class DungeonListener implements Listener {

    private final Random random = new Random();
    private final LocationConfigManager locationConfigManager;

    public DungeonListener(LocationConfigManager locationConfigManager) {
        this.locationConfigManager = locationConfigManager;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        for (Map.Entry<String, List<String>> entry : getDungeonsAndSavezones().entrySet()) {
            String dungeonName = entry.getKey();
            if (isInDungeon(event.getPlayer().getLocation(), dungeonName)) {
                // Zufällig entscheiden, ob ein Mob spawnen soll
                if (random.nextDouble() <= getMobSpawnChance(dungeonName)) {
                    spawnRandomMob(dungeonName, event.getPlayer().getLocation());
                }
                break;
            }
        }
    }

    private boolean isInDungeon(Location playerLocation, String dungeonName) {
        Location dungeonPos1 = locationConfigManager.getDungeonPos1(dungeonName);
        Location dungeonPos2 = locationConfigManager.getDungeonPos2(dungeonName);

        if (dungeonPos1 != null && dungeonPos2 != null) {
            double minX = Math.min(dungeonPos1.getX(), dungeonPos2.getX());
            double minY = Math.min(dungeonPos1.getY(), dungeonPos2.getY());
            double minZ = Math.min(dungeonPos1.getZ(), dungeonPos2.getZ());
            double maxX = Math.max(dungeonPos1.getX(), dungeonPos2.getX());
            double maxY = Math.max(dungeonPos1.getY(), dungeonPos2.getY());
            double maxZ = Math.max(dungeonPos1.getZ(), dungeonPos2.getZ());

            return playerLocation.getX() >= minX && playerLocation.getX() <= maxX &&
                    playerLocation.getY() >= minY && playerLocation.getY() <= maxY &&
                    playerLocation.getZ() >= minZ && playerLocation.getZ() <= maxZ &&
                    playerLocation.getWorld().getName().equals(dungeonPos1.getWorld().getName());
        }
        return false;
    }

    private double getMobSpawnChance(String dungeonName) {
        return locationConfigManager.getConfiguration().getDouble(dungeonName + ".spawnChance", 0.0);
    }
    private void spawnRandomMob(String dungeonName, Location playerLocation) {
        List<String> mobTypes = locationConfigManager.getConfiguration().getStringList(dungeonName + ".mobTypes");

        if (!mobTypes.isEmpty()) {
            Location dungeonPos1 = locationConfigManager.getDungeonPos1(dungeonName);
            Location dungeonPos2 = locationConfigManager.getDungeonPos2(dungeonName);
            double radius = 20.0;
            double angle = random.nextDouble() * 2 * Math.PI;
            double x = playerLocation.getX() + radius * Math.cos(angle);
            double z = playerLocation.getZ() + radius * Math.sin(angle);
            double y = playerLocation.getWorld().getHighestBlockYAt((int) x, (int) z);
            int maxAttempts = 50;

            for (int attempt = 0; attempt < maxAttempts; attempt++) {
                if (isSafeSpawnLocation(playerLocation.getWorld(), x, y, z) &&
                        countEntitiesInDungeon(playerLocation.getWorld(), dungeonPos1, dungeonPos2) < 50) {
                    if (!isSafeSpawnLocation(playerLocation.getWorld(), x, y - 1, z)) {
                        y = playerLocation.getWorld().getHighestBlockYAt((int) x, (int) z) + 1;
                    }

                    Location spawnLocation = new Location(playerLocation.getWorld(), x, y, z);
                    String randomMobType = mobTypes.get(random.nextInt(mobTypes.size()));

                    LivingEntity mob = (LivingEntity) playerLocation.getWorld().spawnEntity(spawnLocation, EntityType.valueOf(randomMobType.toUpperCase()));
                    break;
                }
            }
        }
    }


    private boolean isSafeSpawnLocation(World world, double x, double y, double z) {
        Location checkLocation = new Location(world, x, y, z);
        Material blockType = checkLocation.getBlock().getType();

        // Überprüfen, ob der Block an der Spawn-Position ein Grasblock ist
        return blockType.equals(Material.GRASS_BLOCK) || blockType.equals(Material.SAND) || blockType.equals(Material.MYCELIUM)||blockType.equals(Material.ICE) ||
                blockType.equals(Material.COARSE_DIRT);
    }

    private int countEntitiesInDungeon(World world, Location dungeonPos1, Location dungeonPos2) {
        int count = 0;

        for (LivingEntity entity : world.getLivingEntities()) {
            Location entityLocation = entity.getLocation();

            if (isInDungeon(entityLocation, dungeonPos1, dungeonPos2)) {
                count++;
            }
        }

        return count;
    }

    private boolean isInDungeon(Location location, Location pos1, Location pos2) {
        double minX = Math.min(pos1.getX(), pos2.getX());
        double minY = Math.min(pos1.getY(), pos2.getY());
        double minZ = Math.min(pos1.getZ(), pos2.getZ());
        double maxX = Math.max(pos1.getX(), pos2.getX());
        double maxY = Math.max(pos1.getY(), pos2.getY());
        double maxZ = Math.max(pos1.getZ(), pos2.getZ());

        return location.getX() >= minX && location.getX() <= maxX &&
                location.getY() >= minY && location.getY() <= maxY &&
                location.getZ() >= minZ && location.getZ() <= maxZ &&
                location.getWorld().getName().equals(pos1.getWorld().getName());
    }

    private Map<String, List<String>> getDungeonsAndSavezones() {
        return locationConfigManager.getDungeonsAndSavezones();
    }
}

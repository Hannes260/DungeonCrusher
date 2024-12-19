package net.dbsgameplay.dungeoncrusher.listener;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.utils.configs.LocationConfigManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.*;

public class DungeonListener implements Listener {

    private final Random random = new Random();
    private static LocationConfigManager locationConfigManager;
    private final Map<String, Set<UUID>> dungeonPlayerMap = new HashMap<>();
    private final Map<String, BukkitRunnable> dungeonCleanupTasks = new HashMap<>();

    public DungeonListener(LocationConfigManager locationConfigManager) {
        this.locationConfigManager = locationConfigManager;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        updatePlayerLocation(event.getPlayer().getUniqueId(), event.getPlayer().getLocation());
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

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();
        removePlayer(playerId);
        // Starte oder aktualisiere die Aufräum-Aufgabe für diesen Dungeon
        updateCleanupTask(playerId);
    }

    private void updatePlayerLocation(UUID playerId, Location playerLocation) {
        // Prüfen, ob der Spieler in einem Dungeon ist und ggf. zur Liste hinzufügen
        for (String dungeonName : getDungeonsAndSavezones().keySet()) {
            if (isInDungeon(playerLocation, dungeonName)) {
                dungeonPlayerMap.computeIfAbsent(dungeonName, k -> new HashSet<>()).add(playerId);
                updateCleanupTask(playerId); // Aufräum-Aufgabe starten oder aktualisieren
                return; // Sobald der Spieler einem Dungeon hinzugefügt wurde, abbrechen
            }
        }
        // Wenn der Spieler in keinem Dungeon ist, aus allen Listen entfernen
        for (Set<UUID> playerSet : dungeonPlayerMap.values()) {
            playerSet.remove(playerId);
        }
        // Nachdem der Spieler entfernt wurde, überprüfen, ob keine Spieler mehr im Dungeon sind
        for (String dungeonName : dungeonPlayerMap.keySet()) {
            if (dungeonPlayerMap.get(dungeonName).isEmpty()) {
                // Starte oder aktualisiere die Aufräum-Aufgabe für diesen Dungeon
                updateCleanupTask(dungeonName, 20); // 20 Sekunden Verzögerung
                break; // Nur den ersten leeren Dungeon entfernen
            }
        }
    }

    private void removePlayer(UUID playerId) {
        // Spieler aus allen Dungeon-Listen entfernen
        for (Set<UUID> playerSet : dungeonPlayerMap.values()) {
            playerSet.remove(playerId);
        }
        // Nachdem der Spieler entfernt wurde, überprüfen, ob keine Spieler mehr im Dungeon sind
        for (String dungeonName : dungeonPlayerMap.keySet()) {
            if (dungeonPlayerMap.get(dungeonName).isEmpty()) {
                // Starte oder aktualisiere die Aufräum-Aufgabe für diesen Dungeon
                updateCleanupTask(dungeonName, 20); // 20 Sekunden Verzögerung
                break; // Nur den ersten leeren Dungeon entfernen
            }
        }
    }

    private void updateCleanupTask(UUID playerId) {
        for (String dungeonName : dungeonPlayerMap.keySet()) {
            if (dungeonPlayerMap.get(dungeonName).contains(playerId)) {
                updateCleanupTask(dungeonName, 20); // 20 Sekunden Verzögerung
                return;
            }
        }
    }

    private void updateCleanupTask(String dungeonName, int delaySeconds) {
        // Wenn bereits eine Aufgabe läuft, stoppe sie
        BukkitRunnable task = dungeonCleanupTasks.get(dungeonName);
        if (task != null) {
            task.cancel();
        }
        // Erstelle eine neue Aufgabe, um die Mobs zu entfernen
        task = new BukkitRunnable() {
            @Override
            public void run() {
                // Überprüfen, ob wirklich keine Spieler mehr im Dungeon sind
                if (dungeonPlayerMap.containsKey(dungeonName) && dungeonPlayerMap.get(dungeonName).isEmpty()) {
                    killAllMobsInDungeon(locationConfigManager.getDungeonPos1(dungeonName));
                    dungeonPlayerMap.remove(dungeonName);
                    dungeonCleanupTasks.remove(dungeonName);
                }
            }
        };
        // Starte die Aufgabe mit Verzögerung
        task.runTaskLater(DungeonCrusher.getInstance(), delaySeconds * 20L); // 20L = 20 Ticks (1 Sekunde = 20 Ticks)
        dungeonCleanupTasks.put(dungeonName, task);
    }

    private void killAllMobsInDungeon(Location playerLocation) {
        String currentDungeon = getCurrentDungeon(playerLocation);
        if (currentDungeon != null) {
            List<String> mobTypes = locationConfigManager.getConfiguration().getStringList(currentDungeon + ".mobTypes");
            World world = playerLocation.getWorld();
            Location pos1 = locationConfigManager.getDungeonPos1(currentDungeon);
            Location pos2 = locationConfigManager.getDungeonPos2(currentDungeon);
            for (Entity entity : world.getEntities()) {
                if (entity instanceof LivingEntity && isInDungeon(entity.getLocation(), pos1, pos2)) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    if (mobTypes.contains(livingEntity.getType().name().toLowerCase())) {
                        livingEntity.remove();
                    }
                }
            }
        }
    }

    private double getMobSpawnChance(String dungeonName) {
        return locationConfigManager.getConfiguration().getDouble(dungeonName + ".spawnChance", 0.0);
    }

    private void spawnRandomMob(String dungeonName, Location playerLocation) {
        List<String> mobTypes = locationConfigManager.getConfiguration().getStringList(dungeonName + ".mobTypes");
        int maxMobCount = locationConfigManager.getConfiguration().getInt(dungeonName + ".mobCount", 0);

        if (!mobTypes.isEmpty()) {
            int maxAttempts = 50;

            Location pos1 = locationConfigManager.getDungeonPos1(dungeonName);
            Location pos2 = locationConfigManager.getDungeonPos2(dungeonName);

            int entitiesInDungeon = countEntitiesInDungeon(playerLocation.getWorld(), pos1, pos2, mobTypes);

            if (entitiesInDungeon >= maxMobCount) {
                return;
            }

            for (int attempt = 0; attempt < maxAttempts; attempt++) {
                double x = randomDoubleInRange(Math.min(pos1.getX(), pos2.getX()), Math.max(pos1.getX(), pos2.getX()));
                double z = randomDoubleInRange(Math.min(pos1.getZ(), pos2.getZ()), Math.max(pos1.getZ(), pos2.getZ()));
                double y = randomDoubleInRange(Math.min(pos1.getY(), pos2.getY()), Math.max(pos1.getY(), pos2.getY()));
                Location spawnLocation = new Location(playerLocation.getWorld(), x, y, z);

                // Überprüfen, ob die Spawn-Position sicher ist
                if (isSafeSpawnLocation(spawnLocation)) {
                    String randomMobType = mobTypes.get(random.nextInt(mobTypes.size()));
                    LivingEntity mob = (LivingEntity) playerLocation.getWorld().spawnEntity(spawnLocation, EntityType.valueOf(randomMobType.toUpperCase()));

                    entitiesInDungeon++;
                    if (entitiesInDungeon >= maxMobCount) {
                        break;
                    }
                }
            }
        }
    }

    private boolean isSafeSpawnLocation(Location location) {
        World world = location.getWorld();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        Material blockType = new Location(world, x, y - 1, z).getBlock().getType();
        Material blockAboveType = new Location(world, x, y, z).getBlock().getType();
        Material blockTwoAboveType = new Location(world, x, y + 1, z).getBlock().getType();

        // Überprüfen, ob der Block an der Spawn-Position begehbar ist und der Bereich darüber frei ist
        boolean isSafe = (blockType == Material.GRASS_BLOCK || blockType == Material.DIRT || blockType == Material.MOSS_BLOCK || blockType == Material.STONE ||blockType == Material.SAND);
        return isSafe && blockAboveType == Material.AIR && blockTwoAboveType == Material.AIR;
    }

    private int countEntitiesInDungeon(World world, Location dungeonPos1, Location dungeonPos2, List<String> mobTypes) {
        int count = 0;

        for (LivingEntity entity : world.getLivingEntities()) {
            Location entityLocation = entity.getLocation();

            if (isInDungeon(entityLocation, dungeonPos1, dungeonPos2) && mobTypes.contains(entity.getType().name().toLowerCase())) {
                count++;
            }
        }

        return count;
    }

    private static boolean isInDungeon(Location location, String dungeonName) {
        Location dungeonPos1 = locationConfigManager.getDungeonPos1(dungeonName);
        Location dungeonPos2 = locationConfigManager.getDungeonPos2(dungeonName);

        if (dungeonPos1 != null && dungeonPos2 != null) {
            double minX = Math.min(dungeonPos1.getX(), dungeonPos2.getX());
            double minY = Math.min(dungeonPos1.getY(), dungeonPos2.getY());
            double minZ = Math.min(dungeonPos1.getZ(), dungeonPos2.getZ());
            double maxX = Math.max(dungeonPos1.getX(), dungeonPos2.getX());
            double maxY = Math.max(dungeonPos1.getY(), dungeonPos2.getY());
            double maxZ = Math.max(dungeonPos1.getZ(), dungeonPos2.getZ());

            return location.getX() >= minX && location.getX() <= maxX &&
                    location.getY() >= minY && location.getY() <= maxY &&
                    location.getZ() >= minZ && location.getZ() <= maxZ &&
                    location.getWorld().getName().equals(dungeonPos1.getWorld().getName());
        }
        return false;
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

    private static Map<String, List<String>> getDungeonsAndSavezones() {
        return locationConfigManager.getDungeonsAndSavezones();
    }

    private double randomDoubleInRange(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    public static String getCurrentDungeon(Location playerLocation) {
        for (Map.Entry<String, List<String>> entry : getDungeonsAndSavezones().entrySet()) {
            String dungeonName = entry.getKey();
            if (isInDungeon(playerLocation, dungeonName)) {
                return dungeonName;
            }
        }
        return null;
    }
}



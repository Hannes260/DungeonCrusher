package net.dbsgameplay.dungeoncrusher.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class DungeonManager {

    private final LocationConfigManager locationConfigManager;
    private final Random random = new Random();

    public DungeonManager(LocationConfigManager locationConfigManager) {
        this.locationConfigManager = locationConfigManager;
    }

    public boolean isInDungeon(Player player, String dungeonName) {
        Location playerLocation = player.getLocation();
        String path = dungeonName;

        if (locationConfigManager.isLocationSet(path + ".pos1") && locationConfigManager.isLocationSet(path + ".pos2")) {
            Location pos1 = locationConfigManager.getDungeonPos1(dungeonName);
            Location pos2 = locationConfigManager.getDungeonPos2(dungeonName);

            if (pos1 != null && pos2 != null) {
                double minX = Math.min(pos1.getX(), pos2.getX());
                double minY = Math.min(pos1.getY(), pos2.getY());
                double minZ = Math.min(pos1.getZ(), pos2.getZ());
                double maxX = Math.max(pos1.getX(), pos2.getX());
                double maxY = Math.max(pos1.getY(), pos2.getY());
                double maxZ = Math.max(pos1.getZ(), pos2.getZ());

                return playerLocation.getX() >= minX && playerLocation.getX() <= maxX &&
                        playerLocation.getY() >= minY && playerLocation.getY() <= maxY &&
                        playerLocation.getZ() >= minZ && playerLocation.getZ() <= maxZ &&
                        playerLocation.getWorld().getName().equals(pos1.getWorld().getName());
            }
        }

        return false;
    }
}

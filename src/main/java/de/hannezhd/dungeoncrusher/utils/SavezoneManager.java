package de.hannezhd.dungeoncrusher.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.List;
import java.util.Map;

public class SavezoneManager {

    private final LocationConfigManager locationConfigManager;

    public SavezoneManager(LocationConfigManager locationConfigManager) {
        this.locationConfigManager = locationConfigManager;
    }
    public boolean isInSavezone(Player player, String dungeonName, String savezoneName) {
        Location playerLocation = player.getLocation();
        String path = dungeonName + "." + savezoneName;

        if (locationConfigManager.isLocationSet(path + ".pos1") && locationConfigManager.isLocationSet(path + ".pos2")) {
            Location pos1 = locationConfigManager.getSavezonePos1(dungeonName, savezoneName);
            Location pos2 = locationConfigManager.getSavezonePos2(dungeonName, savezoneName);
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
    public boolean isInAnySavezone(Player player) {
        Map<String, List<String>> dungeonsAndSavezones = locationConfigManager.getDungeonsAndSavezones();

        for (Map.Entry<String, List<String>> entry : dungeonsAndSavezones.entrySet()) {
            String dungeonName = entry.getKey();
            List<String> savezoneNames = entry.getValue();

            for (String savezoneName : savezoneNames) {
                if (isInSavezone(player, dungeonName, savezoneName)) {
                    return true;
                }
            }
        }
        return false;
    }
}

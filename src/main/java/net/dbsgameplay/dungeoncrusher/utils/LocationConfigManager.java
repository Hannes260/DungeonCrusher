package net.dbsgameplay.dungeoncrusher.utils;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class LocationConfigManager {

    private final JavaPlugin plugin;
    private File configFile;
    private FileConfiguration config;

    public LocationConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "locations.yml");
        if (!configFile.exists()) {
            plugin.saveResource("locations.yml", false);
        }

        this.config = YamlConfiguration.loadConfiguration(configFile);
    }
    public Map<String, List<String>> getDungeonsAndSavezones() {
        Map<String, List<String>> dungeonsAndSavezones = new HashMap<>();

        Set<String> dungeonNames = config.getKeys(false);
        for (String dungeonName : dungeonNames) {
            ConfigurationSection dungeonSection = config.getConfigurationSection(dungeonName);
            if (dungeonSection != null) {
                List<String> savezoneNames = new ArrayList<>();
                Set<String> keys = dungeonSection.getKeys(false);

                for (String key : keys) {
                    // Überprüfe, ob es sich um eine Savezone handelt (nicht um eine Position)
                    if (config.isConfigurationSection(dungeonName + "." + key) && !key.startsWith("pos")) {
                        savezoneNames.add(key);
                    }
                }

                dungeonsAndSavezones.put(dungeonName, savezoneNames);
            }
        }

        return dungeonsAndSavezones;
    }
    public void deleteDungeon(String dungeonName) {
        if (config.isConfigurationSection(dungeonName)) {
            ConfigurationSection dungeonSection = config.getConfigurationSection(dungeonName);

            // Lösche den Dungeon
            config.set(dungeonName, null);

            // Lösche alle zugehörigen Savezones
            for (String savezoneName : dungeonSection.getKeys(false)) {
                if (!savezoneName.startsWith("pos")) {
                    String savezonePath = dungeonName + "." + savezoneName;
                    config.set(savezonePath, null);
                }
            }

            saveConfig();
        }
    }
    public void deleteSavezone(String dungeonName, String savezoneName) {
        String path = dungeonName + "." + savezoneName;
        if (config.isConfigurationSection(path)) {
            config.set(path, null);
            saveConfig();
        }
    }
    public Location getSpawn() {
        if (isLocationSet("spawn")) {
            double x = config.getDouble("spawn.x");
            double y = config.getDouble("spawn.y");
            double z = config.getDouble("spawn.z");
            float yaw = (float) config.getDouble("spawn.yaw");
            float pitch = (float) config.getDouble("spawn.pitch");
            String worldName = config.getString("spawn.world");
            return new Location(plugin.getServer().getWorld(worldName), x, y, z,yaw,pitch);
        }

        return null;
    }
    public void setSpawn(Location location) {
        config.set("spawn.x", location.getX());
        config.set("spawn.y", location.getY());
        config.set("spawn.z", location.getZ());
        config.set("spawn.yaw", (float) location.getYaw());
        config.set("spawn.pitch", (float) location.getPitch());
        config.set("spawn.world", location.getWorld().getName());
        saveConfig();
    }
    public Location getLocation(String locationName) {
        double x = config.getDouble(locationName + ".x");
        double y = config.getDouble(locationName + ".y");
        double z = config.getDouble(locationName + ".z");
        String world = config.getString(locationName + ".world");

        return null;
    }

    public void saveLocation(String dungeonName, Location location, int markierungsschritt) {
        String position = "pos" + markierungsschritt;

        config.set(dungeonName + "." + position + ".x", location.getX());
        config.set(dungeonName + "." + position + ".y", location.getY());
        config.set(dungeonName + "." + position + ".z", location.getZ());
        config.set(dungeonName + "." + position + ".world", location.getWorld().getName());
        saveConfig();
    }
    public Location getSpawnpoint(String dungeonName) {
        if (isLocationSet(dungeonName + "." + "spawnpoint")) {
            double x = config.getDouble(dungeonName + "." + "spawnpoint" + ".x");
            double y = config.getDouble(dungeonName + "." + "spawnpoint" + ".y");
            double z = config.getDouble(dungeonName + "." + "spawnpoint" + ".z");
            float yaw = (float) config.getDouble(dungeonName + "." + "spawnpoint" + ".yaw");
            float pitch = (float) config.getDouble(dungeonName + "." + "spawnpoint" + ".pitch");
            String worldName = config.getString(dungeonName + "." + "spawnpoint" + ".world");
            return new Location(plugin.getServer().getWorld(worldName), x, y, z,yaw,pitch);
        }
        return null;
    }
    public void saveSpawnpoint(String dungeonName, Location location) {
            config.set(dungeonName + "." + "spawnpoint" + ".x", location.getX());
            config.set(dungeonName + "." + "spawnpoint" + ".y", location.getY());
            config.set(dungeonName + "." + "spawnpoint" + ".z", location.getZ());
            config.set(dungeonName + "." + "spawnpoint" + ".yaw", (float) location.getYaw());
            config.set(dungeonName + "." + "spawnpoint" + ".pitch", (float) location.getPitch());
            config.set(dungeonName + "." + "spawnpoint" + ".world", location.getWorld().getName());
            saveConfig();

    }
    public Integer saveKills(String dungeonName, Integer Kills) {
        config.set(dungeonName + "." + "kills", Kills);
        saveConfig();
        return Kills;
    }
    public Integer getKills(String dungeonName) {
        Integer kills = (Integer) config.get(dungeonName + "." + "kills");
        return kills;
    }

    public void saveSavezone(String dungeonName, String savezoneName, Location location, int i) {
        String position = "pos" + i;

        config.set(dungeonName + "." + savezoneName + "." + position + ".x", location.getX());
        config.set(dungeonName + "." + savezoneName + "." + position + ".y", location.getY());
        config.set(dungeonName + "." + savezoneName + "." + position + ".z", location.getZ());
        config.set(dungeonName + "." + savezoneName + "." + position + ".world", location.getWorld().getName());
        saveConfig();
    }
    public Location getSavezonePos1(String dungeonName, String savezoneName) {
        if (isLocationSet(dungeonName + "." + savezoneName + ".pos1")) {
            double x = config.getDouble(dungeonName + "." + savezoneName + ".pos1.x");
            double y = config.getDouble(dungeonName + "." + savezoneName + ".pos1.y");
            double z = config.getDouble(dungeonName + "." + savezoneName + ".pos1.z");
            String worldName = config.getString(dungeonName + "." + savezoneName + ".pos1.world");
            return new Location(plugin.getServer().getWorld(worldName), x, y, z);
        }
        return null;
    }
    public Location getSavezonePos2(String dungeonName, String savezoneName) {
        if (isLocationSet(dungeonName + "." + savezoneName + ".pos2")) {
            double x = config.getDouble(dungeonName + "." + savezoneName + ".pos2.x");
            double y = config.getDouble(dungeonName + "." + savezoneName + ".pos2.y");
            double z = config.getDouble(dungeonName + "." + savezoneName + ".pos2.z");
            String worldName = config.getString(dungeonName + "." + savezoneName + ".pos2.world");
            return new Location(plugin.getServer().getWorld(worldName), x, y, z);
        }
        return null;
    }
    public boolean isLocationSet(String path) {
        return config.contains(path + ".x");
    }
    public FileConfiguration getConfiguration() {
        return config;
    }
    public Location getDungeonPos1(String dungeonName) {
        if (isLocationSet(dungeonName + ".pos1")) {
            double x = config.getDouble(dungeonName + ".pos1.x");
            double y = config.getDouble(dungeonName + ".pos1.y");
            double z = config.getDouble(dungeonName + ".pos1.z");
            String worldName = config.getString(dungeonName + ".pos1.world");
            return new Location(plugin.getServer().getWorld(worldName), x, y, z);
        }
        return null;
    }

    public Location getDungeonPos2(String dungeonName) {
        if (isLocationSet(dungeonName + ".pos2")) {
            double x = config.getDouble(dungeonName + ".pos2.x");
            double y = config.getDouble(dungeonName + ".pos2.y");
            double z = config.getDouble(dungeonName + ".pos2.z");
            String worldName = config.getString(dungeonName + ".pos2.world");
            return new Location(plugin.getServer().getWorld(worldName), x, y, z);
        }
        return null;
    }
    public void setMobTypes(String dungeonName, String[] mobTypes) {
        List<String> mobTypesList = Arrays.asList(mobTypes);
        config.set(dungeonName + ".mobTypes", mobTypesList);
        saveConfig();
    }
    private List<String> getMobTypesForDungeon(String dungeonName) {
        return config.getStringList(dungeonName + ".mobTypes");
    }
    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

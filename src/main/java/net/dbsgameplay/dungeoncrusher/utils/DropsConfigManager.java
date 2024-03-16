package net.dbsgameplay.dungeoncrusher.utils;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DropsConfigManager {

    private final JavaPlugin plugin;
    private final File configFile;
    private FileConfiguration config;

    public DropsConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "drops.yml");
        if (!configFile.exists()) {
            plugin.saveResource("drops.yml", false);
        }

        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    public Map<String, Double> loadMobDrops(String mobName) {
        Map<String, Double> mobDrops = new HashMap<>();
        if (config.contains("mobs." + mobName)) {
            ConfigurationSection mobSection = config.getConfigurationSection("mobs." + mobName);
            for (String key : mobSection.getKeys(false)) {
                if (key.equalsIgnoreCase("money")) {
                    // Wenn das Material "money" ist, füge es direkt hinzu
                    mobDrops.put(key, mobSection.getDouble(key + ".money_chance"));
                } else {
                    // Andernfalls füge das normale Material hinzu
                    mobDrops.put(key, mobSection.getDouble(key));
                }
            }
        }
        return mobDrops;
    }

    public double[] loadMoneyDropRange(String mobName) {
        ConfigurationSection mobSection = config.getConfigurationSection("mobs." + mobName + ".money");
        if (mobSection != null) {
            double minAmount = mobSection.getDouble("minAmount", 0.0);
            double maxAmount = mobSection.getDouble("maxAmount", 0.0);
            return new double[]{minAmount, maxAmount};
        }
        return new double[]{0.0, 0.0}; // Standardbereich, wenn nicht konfiguriert
    }

    public double loadMoneyDropChance(String mobName) {
        return config.getDouble("mobs." + mobName + ".money.money_chance", 0.0);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

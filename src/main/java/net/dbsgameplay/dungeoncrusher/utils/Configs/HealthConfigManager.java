package net.dbsgameplay.dungeoncrusher.utils.Configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class HealthConfigManager {
    private final JavaPlugin plugin;
    private final File configFile;
    private FileConfiguration config;

    public HealthConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "mobhealth.yml");
        if (!configFile.exists()) {
            plugin.saveResource("mobhealth.yml", false);
        }

        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    public double getMobHealth(String mobName) {
        return config.getDouble("mobs." + mobName + ".health", 20.0); // Standardwert 20.0, wenn nicht konfiguriert
    }

    public void setMobHealth(String mobName, double health) {
        config.set("mobs." + mobName + ".health", health);
        saveConfig();
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

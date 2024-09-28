package net.dbsgameplay.dungeoncrusher.utils.Configs;

import net.dbsgameplay.dungeoncrusher.utils.ErfolgeBuilders;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ErfolgeConfigManager {
    private final JavaPlugin plugin;
    private final File configFile;
    private static FileConfiguration config;

    public ErfolgeConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "erfolge.yml");
        if (!configFile.exists()) {
            plugin.saveResource("erfolge.yml", false);
        }

        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    public static void loadMap() {
        List<String> mobList = ErfolgeBuilders.moblist;

        if (config.contains("erfolge.")) {
            if (config.contains("erfolge." + "mobs")) {
                List<String> list = config.getStringList("erfolge." + "mobs");
                for (String s : list) {
                    mobList.add(s);
                }
            }
        }
    }
}

package net.dbsgameplay.dungeoncrusher.utils.Configs;

import it.unimi.dsi.fastutil.Hash;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public class MinibossConfigManager {

    private final JavaPlugin plugin;
    private final File configFile;
    private static FileConfiguration config;

    public MinibossConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "miniboss.yml");
        if (!configFile.exists()) {
            plugin.saveResource("miniboss.yml", false);
        }

        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    public static HashMap<String, HashMap<String, HashMap<String, Double>>> miniboss_data_Hashmap = new HashMap<>();

    public static void loadConfig() {
        for (String dungeon : config.getConfigurationSection("dungeons").getKeys(false)) {
            HashMap<String, HashMap<String, Double>> hashmap1 = new HashMap<>();
            for (String boss : config.getConfigurationSection("dungeons." + dungeon).getKeys(false)) {
                HashMap<String, Double> hashmap2 = new HashMap<>();
                ConfigurationSection section = config.getConfigurationSection("dungeons." + dungeon + "." + boss);
                for (String lvl : section.getKeys(false)) {
                    hashmap2.put(lvl, config.getDouble("dungeons." + dungeon + "." + boss + "." + lvl));
                }
                hashmap1.put(boss, hashmap2);
            }
            miniboss_data_Hashmap.put(dungeon, hashmap1);
        }
    }
}
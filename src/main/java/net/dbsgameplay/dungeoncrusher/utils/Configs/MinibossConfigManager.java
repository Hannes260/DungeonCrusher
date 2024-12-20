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

    public static HashMap<String, HashMap<String, HashMap<String, Integer>>> miniboss_data_Hashmap = new HashMap<>();

    public static void loadConfig() {
        for (String dungeon1 : config.getConfigurationSection("dungeons.").getKeys(false)) {
            HashMap<String, HashMap<String, Integer>> hashmap1= new HashMap<>();
            HashMap<String, Integer> hashmap2 = new HashMap<>();
            ConfigurationSection section = config.getConfigurationSection("dungeons." + dungeon1);
            for (String boss : section.getKeys(false)) {
                ConfigurationSection section1 = config.getConfigurationSection("dungeons." + dungeon1 + "." + boss);
                for (String lvl1 : section1.getKeys(false)) {
                    hashmap2.put(lvl1, config.getInt("dungeons." + dungeon1 + "." + boss + "." + lvl1));
                }
                hashmap1.put(boss, hashmap2);
            }
            miniboss_data_Hashmap.put(dungeon1, hashmap1);
        }
    }
}

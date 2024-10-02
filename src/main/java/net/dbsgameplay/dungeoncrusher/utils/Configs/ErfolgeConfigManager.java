package net.dbsgameplay.dungeoncrusher.utils.Configs;

import net.dbsgameplay.dungeoncrusher.enums.MobNameTranslator;
import net.dbsgameplay.dungeoncrusher.utils.ErfolgeBuilders;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
        List<String> titlesList = new ArrayList<>();
        List<String> enNamesList = new ArrayList<>();
        HashMap titlesHashmap = ErfolgeBuilders.titlesHashmap;

        if (config.contains("erfolge.")) {
            if (config.contains("erfolge." + "mobs")) {
                List<String> list = config.getStringList("erfolge." + "mobs");
                for (String s : list) {
                    mobList.add(s);
                }
            }
            if (config.contains("erfolge." + "killAmount")) {
                ErfolgeBuilders.killAmount = config.getInt("erfolge." + "killAmount");
            }
            if (config.contains("erfolge." + "rewardAmount")) {
                ErfolgeBuilders.rewardAmount = config.getInt("erfolge." + "rewardAmount");
            }
            if (config.contains("erfolge." + "titles")) {
                List<String> list = config.getStringList("erfolge." + "titles");
                for (String s : list) {
                    titlesList.add(s);
                }

                for (String mob : mobList) {
                    for (int i = 1; i != 21; i++) {
                        String index = "Erfolg_" + mob + "_" + i;
                        String title = mob + titlesList.get(i-1);

                        titlesHashmap.put(index, title);
                    }
                }
            }
            if (config.contains("erfolge." + "enMobs")) {
                List<String> list = config.getStringList("erfolge." + "enMobs");
                for (String s : list) {
                    enNamesList.add(s);
                }

                for (int i = 0; i != mobList.size(); i++) {
                    MobNameTranslator.translatorMap.put(enNamesList.get(i), mobList.get(i));
                }
            }
        }
    }
}

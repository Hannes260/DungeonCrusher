package net.dbsgameplay.dungeoncrusher.utils.Configs;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardConfigManager {
    private final JavaPlugin plugin;
    private final File configFile;
    private static FileConfiguration config;

    public RewardConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "rewards.yml");
        if (!configFile.exists()) {
            plugin.saveResource("rewards.yml", false);
        }

        this.config = YamlConfiguration.loadConfiguration(configFile);
    }
    public static Map<String, Map<String, Object>> loadRewards() {
        Map<String, Map<String, Object>> rewards = new HashMap<>();
        if (config.contains("rewards")) {
            ConfigurationSection rewardsSection = config.getConfigurationSection("rewards");
            for (String key : rewardsSection.getKeys(false)) {
                ConfigurationSection rewardDataSection = rewardsSection.getConfigurationSection(key);
                if (rewardDataSection != null) {
                    Map<String, Object> rewardData = new HashMap<>();
                    List<Map<String, Object>> itemsList = new ArrayList<>();
                    if (rewardDataSection.contains("items")) {
                        ConfigurationSection itemsSection = rewardDataSection.getConfigurationSection("items");
                        for (String itemKey : itemsSection.getKeys(false)) {
                            ConfigurationSection itemDataSection = itemsSection.getConfigurationSection(itemKey);
                            if (itemDataSection != null) {
                                Map<String, Object> itemData = new HashMap<>();
                                itemData.put("material", itemDataSection.getString("material"));
                                itemData.put("minAmount", itemDataSection.getInt("minAmount"));
                                itemData.put("maxAmount", itemDataSection.getInt("maxAmount"));
                                itemsList.add(itemData);
                            }
                        }
                    }
                    rewardData.put("items", itemsList);
                    if (rewardDataSection.contains("money")) {
                        rewardData.put("money", true);
                        rewardData.put("minAmount", rewardDataSection.getDouble("money.minAmount"));
                        rewardData.put("maxAmount", rewardDataSection.getDouble("money.maxAmount"));
                    }
                    rewardData.put("chance", rewardDataSection.getDouble("chance"));
                    rewards.put(key, rewardData);
                }
            }
        }
        return rewards;
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

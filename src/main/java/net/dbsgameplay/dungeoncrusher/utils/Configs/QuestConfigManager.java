package net.dbsgameplay.dungeoncrusher.utils.Configs;

import it.unimi.dsi.fastutil.Hash;
import net.dbsgameplay.dungeoncrusher.utils.quests.Daily;
import org.bukkit.Bukkit;
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

public class QuestConfigManager {
    private final JavaPlugin plugin;
    private final File configFile;
    private static FileConfiguration config;
    public static HashMap<String, Map<String, Object>> dailyRewardItemList = Daily.dailyRewardItemList;
    public static HashMap<String, Integer> dailyRewardMoneyList = Daily.dailyRewardMoneyList;

    public static HashMap<String, Integer> dailyMoveQuestList = Daily.dailyMoveQuestList;
    public static HashMap<String, Integer> dailyPlayQuestList = Daily.dailyPlayQuestList;
    public static HashMap<String, Integer> dailyKillQuestList = Daily.dailyKillQuestList;
    public static HashMap<String, Integer> dailyGetQuestList = Daily.dailyGetQuestList;

    public QuestConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "quest.yml");
        if (!configFile.exists()) {
            plugin.saveResource("quest.yml", false);
        }

        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    public static void loadMap() {
        if (config.contains("quest.")) {
            //Daily
            if (config.contains("quest." + "daily.")) {
                //Move Kategorie
                if (config.contains("quest." + "daily." + "move.")) {
                    ConfigurationSection section = config.getConfigurationSection("quest." + "daily." + "move.");
                    for (String s : section.getKeys(false)) {
                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "daily." + "move." + s + ".");
                        for (String s2 : keysSection.getKeys(false)) {
                            int quest = config.getInt("quest." + "daily." + "move." + s + "." + "quest");
                            if (config.contains("quest." + "daily." + "move." + s + "." + "money")) {
                                int money = config.getInt("quest." + "daily." + "move." + s + "." + "money");
                                dailyRewardMoneyList.put(s, money);
                            }
                            //Material, anzahl
                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "daily." + "move." + s + "." + "items.");
                            Map<String, Object> itemData = new HashMap<>();
                            for (String s3 : keysRewardSection.getKeys(false)) {
                                if (config.contains("quest." + "daily." + "move." + s + "." + "items." + s3)) {
                                    int amount = config.getInt("quest." + "daily." + "move." + s + "." + "items." + s3);
                                    //s ist die questid
                                    itemData.put(s3, amount);
                                }
                            }
                            dailyRewardItemList.put(s, itemData);
                            dailyMoveQuestList.put(s, quest);
                        }
                    }
                }
                //Play Kategorie
                if (config.contains("quest." + "daily." + "play.")) {
                    ConfigurationSection section = config.getConfigurationSection("quest." + "daily." + "play.");
                    for (String s : section.getKeys(false)) {
                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "daily." + "play." + s + ".");
                        for (String s2 : keysSection.getKeys(false)) {
                            int quest = config.getInt("quest." + "daily." + "play." + s + "." + "quest");
                            if (config.contains("quest." + "daily." + "play." + s + "." + "money")) {
                                int money = config.getInt("quest." + "daily." + "play." + s + "." + "money");
                                dailyRewardMoneyList.put(s, money);
                            }
                            //Material, anzahl
                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "daily." + "play." + s + "." + "items.");
                            Map<String, Object> itemData = new HashMap<>();
                            for (String s3 : keysRewardSection.getKeys(false)) {
                                if (config.contains("quest." + "daily." + "play." + s + "." + "items." + s3)) {
                                    int amount = config.getInt("quest." + "daily." + "play." + s + "." + "items." + s3);
                                    //s ist die questid
                                    itemData.put(s3, amount);
                                }
                            }
                            dailyRewardItemList.put(s, itemData);
                            dailyPlayQuestList.put(s, quest);
                        }
                    }
                }
                //Kill Kategorie
                if (config.contains("quest." + "daily." + "kill.")) {
                    ConfigurationSection section = config.getConfigurationSection("quest." + "daily." + "kill.");
                    for (String s : section.getKeys(false)) {
                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "daily." + "kill." + s + ".");
                        for (String s2 : keysSection.getKeys(false)) {
                            int quest = config.getInt("quest." + "daily." + "kill." + s + "." + "quest");
                            if (config.contains("quest." + "daily." + "kill." + s + "." + "money")) {
                                int money = config.getInt("quest." + "kill." + "play." + s + "." + "money");
                                dailyRewardMoneyList.put(s, money);
                            }
                            //Material, anzahl
                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "daily." + "kill." + s + "." + "items.");
                            Map<String, Object> itemData = new HashMap<>();
                            for (String s3 : keysRewardSection.getKeys(false)) {
                                if (config.contains("quest." + "daily." + "kill." + s + "." + "items." + s3)) {
                                    int amount = config.getInt("quest." + "daily." + "kill." + s + "." + "items." + s3);
                                    //s ist die questid
                                    itemData.put(s3, amount);
                                }
                            }
                            dailyRewardItemList.put(s, itemData);
                            dailyKillQuestList.put(s, quest);
                        }
                    }
                }
                //Get Kategorie
                if (config.contains("quest." + "daily." + "get.")) {
                    ConfigurationSection section = config.getConfigurationSection("quest." + "daily." + "get.");
                    for (String s : section.getKeys(false)) {
                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "daily." + "get." + s + ".");
                        for (String s2 : keysSection.getKeys(false)) {
                            int quest = config.getInt("quest." + "daily." + "get." + s + "." + "quest");
                            if (config.contains("quest." + "daily." + "get." + s + "." + "money")) {
                                int money = config.getInt("quest." + "get." + "play." + s + "." + "money");
                                dailyRewardMoneyList.put(s, money);
                            }
                            //Material, anzahl
                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "daily." + "get." + s + "." + "items.");
                            Map<String, Object> itemData = new HashMap<>();
                            for (String s3 : keysRewardSection.getKeys(false)) {
                                if (config.contains("quest." + "daily." + "get." + s + "." + "items." + s3)) {
                                    int amount = config.getInt("quest." + "daily." + "get." + s + "." + "items." + s3);
                                    //s ist die questid
                                    itemData.put(s3, amount);
                                }
                            }
                            dailyRewardItemList.put(s, itemData);
                            dailyGetQuestList.put(s, quest);
                        }
                    }
                }
            }
        }
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

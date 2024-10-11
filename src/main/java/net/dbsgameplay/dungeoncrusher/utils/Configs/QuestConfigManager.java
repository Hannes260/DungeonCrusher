package net.dbsgameplay.dungeoncrusher.utils.Configs;

import net.dbsgameplay.dungeoncrusher.utils.quests.Daily;
import net.dbsgameplay.dungeoncrusher.utils.quests.Monthly;
import net.dbsgameplay.dungeoncrusher.utils.quests.Weekly;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QuestConfigManager {
    private final JavaPlugin plugin;
    private final File configFile;
    private static FileConfiguration config;

    public static HashMap<String, Map<String, Object>> dailyRewardItemList = Daily.RewardItemList;
    public static HashMap<String, Integer> dailyRewardMoneyList = Daily.RewardMoneyList;

    public static HashMap<String, Integer> dailyMoveQuestList = Daily.MoveQuestList;
    public static HashMap<String, Integer> dailyPlayQuestList = Daily.PlayQuestList;
    public static HashMap<String, Integer> dailyKillQuestList = Daily.KillQuestList;
    public static HashMap<String, Integer> dailyGetQuestList = Daily.GetQuestList;
    public static HashMap<String, Integer> dailyDamageQuestList = Daily.DamageQuestList;
    public static HashMap<String, Integer> dailyEatlQuestList = Daily.EatQuestList;
    public static HashMap<String, Integer> dailyDrinkQuestList = Daily.DrinkQuestList;

    public static HashMap<String, Map<String, Object>> weeklyRewardItemList = Weekly.RewardItemList;
    public static HashMap<String, Integer> weeklyRewardMoneyList = Weekly.RewardMoneyList;

    public static HashMap<String, Integer> weeklyMoveQuestList = Weekly.MoveQuestList;
    public static HashMap<String, Integer> weeklyPlayQuestList = Weekly.PlayQuestList;
    public static HashMap<String, Integer> weeklyKillQuestList = Weekly.KillQuestList;
    public static HashMap<String, Integer> weeklyGetQuestList = Weekly.GetQuestList;
    public static HashMap<String, Integer> weeklyDamageQuestList = Weekly.DamageQuestList;
    public static HashMap<String, Integer> weeklyEatlQuestList = Weekly.EatQuestList;
    public static HashMap<String, Integer> weeklyDrinkQuestList = Weekly.DrinkQuestList;

    public static HashMap<String, Map<String, Object>> monthlyRewardItemList = Monthly.RewardItemList;
    public static HashMap<String, Integer> monthlyRewardMoneyList = Monthly.RewardMoneyList;

    public static HashMap<String, Integer> monthlyMoveQuestList = Monthly.MoveQuestList;
    public static HashMap<String, Integer> monthlyPlayQuestList = Monthly.PlayQuestList;
    public static HashMap<String, Integer> monthlyKillQuestList = Monthly.KillQuestList;
    public static HashMap<String, Integer> monthlyGetQuestList = Monthly.GetQuestList;
    public static HashMap<String, Integer> monthlyDamageQuestList = Monthly.DamageQuestList;
    public static HashMap<String, Integer> monthlyEatlQuestList = Monthly.EatQuestList;
    public static HashMap<String, Integer> monthlyDrinkQuestList = Monthly.DrinkQuestList;

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

            String[] questTypes = {"daily.", "weekly.", "monthly."};

            for (String sQuestType : questTypes) {
                String rawQuestType = sQuestType.replace(".", "");

                if (config.contains("quest." + "weekly.")) {
                    //Move Kategorie
                    if (config.contains("quest." + sQuestType + "move.")) {
                        ConfigurationSection section = config.getConfigurationSection("quest." + sQuestType + "move.");
                        for (String s : section.getKeys(false)) {
                            ConfigurationSection keysSection = config.getConfigurationSection("quest." + sQuestType + "move." + s + ".");
                            for (String s2 : keysSection.getKeys(false)) {
                                int quest = config.getInt("quest." + sQuestType + "move." + s + "." + "quest");
                                if (config.contains("quest." + sQuestType + "move." + s + "." + "money")) {
                                    int money = config.getInt("quest." + sQuestType + "move." + s + "." + "money");

                                    switch (rawQuestType) {
                                        case "daily" :
                                            dailyRewardMoneyList.put(s, money);
                                            break;
                                        case "weekly" :
                                            weeklyRewardMoneyList.put(s, money);
                                            break;
                                        case "monthly" :
                                            monthlyRewardMoneyList.put(s, money);
                                            break;
                                    }
                                }
                                //Material, anzahl
                                ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + sQuestType + "move." + s + "." + "items.");
                                Map<String, Object> itemData = new HashMap<>();
                                for (String s3 : keysRewardSection.getKeys(false)) {
                                    if (config.contains("quest." + sQuestType + "move." + s + "." + "items." + s3)) {
                                        int amount = config.getInt("quest." + sQuestType + "move." + s + "." + "items." + s3);
                                        //s ist die questid
                                        itemData.put(s3, amount);
                                    }
                                }
                                switch (rawQuestType) {
                                    case "daily" :
                                        dailyRewardItemList.put(s, itemData);
                                        dailyMoveQuestList.put(s, quest);
                                        break;
                                    case "weekly" :
                                        weeklyRewardItemList.put(s, itemData);
                                        weeklyMoveQuestList.put(s, quest);
                                        break;
                                    case "monthly" :
                                        monthlyRewardItemList.put(s, itemData);
                                        monthlyMoveQuestList.put(s, quest);
                                        break;
                                }
                            }
                        }
                    }
                    //Play Kategorie
                    if (config.contains("quest." + sQuestType + "play.")) {
                        ConfigurationSection section = config.getConfigurationSection("quest." + sQuestType + "play.");
                        for (String s : section.getKeys(false)) {
                            ConfigurationSection keysSection = config.getConfigurationSection("quest." + sQuestType + "play." + s + ".");
                            for (String s2 : keysSection.getKeys(false)) {
                                int quest = config.getInt("quest." + sQuestType + "play." + s + "." + "quest");
                                if (config.contains("quest." + sQuestType + "play." + s + "." + "money")) {
                                    int money = config.getInt("quest." + sQuestType + "play." + s + "." + "money");

                                    switch (rawQuestType) {
                                        case "daily" :
                                            dailyRewardMoneyList.put(s, money);
                                            break;
                                        case "weekly" :
                                            weeklyRewardMoneyList.put(s, money);
                                            break;
                                        case "monthly" :
                                            monthlyRewardMoneyList.put(s, money);
                                            break;
                                    }
                                }
                                //Material, anzahl
                                ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + sQuestType + "play." + s + "." + "items.");
                                Map<String, Object> itemData = new HashMap<>();
                                for (String s3 : keysRewardSection.getKeys(false)) {
                                    if (config.contains("quest." + sQuestType + "play." + s + "." + "items." + s3)) {
                                        int amount = config.getInt("quest." + sQuestType + "play." + s + "." + "items." + s3);
                                        //s ist die questid
                                        itemData.put(s3, amount);
                                    }
                                }
                                switch (rawQuestType) {
                                    case "daily" :
                                        dailyRewardItemList.put(s, itemData);
                                        dailyPlayQuestList.put(s, quest);
                                        break;
                                    case "weekly" :
                                        weeklyRewardItemList.put(s, itemData);
                                        weeklyPlayQuestList.put(s, quest);
                                        break;
                                    case "monthly" :
                                        monthlyRewardItemList.put(s, itemData);
                                        monthlyPlayQuestList.put(s, quest);
                                        break;
                                }
                            }
                        }
                    }
                    //Kill Kategorie
                    if (config.contains("quest." + sQuestType + "kill.")) {
                        ConfigurationSection section = config.getConfigurationSection("quest." + sQuestType + "kill.");
                        for (String s : section.getKeys(false)) {
                            ConfigurationSection keysSection = config.getConfigurationSection("quest." + sQuestType + "kill." + s + ".");
                            for (String s2 : keysSection.getKeys(false)) {
                                int quest = config.getInt("quest." + sQuestType + "kill." + s + "." + "quest");
                                if (config.contains("quest." + sQuestType + "kill." + s + "." + "money")) {
                                    int money = config.getInt("quest." + sQuestType + "kill." + s + "." + "money");

                                    switch (rawQuestType) {
                                        case "daily" :
                                            dailyRewardMoneyList.put(s, money);
                                            break;
                                        case "weekly" :
                                            weeklyRewardMoneyList.put(s, money);
                                            break;
                                        case "monthly" :
                                            monthlyRewardMoneyList.put(s, money);
                                            break;
                                    }
                                }
                                //Material, anzahl
                                ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + sQuestType + "kill." + s + "." + "items.");
                                Map<String, Object> itemData = new HashMap<>();
                                for (String s3 : keysRewardSection.getKeys(false)) {
                                    if (config.contains("quest." + sQuestType + "kill." + s + "." + "items." + s3)) {
                                        int amount = config.getInt("quest." + sQuestType + "kill." + s + "." + "items." + s3);
                                        //s ist die questid
                                        itemData.put(s3, amount);
                                    }
                                }
                                switch (rawQuestType) {
                                    case "daily" :
                                        dailyRewardItemList.put(s, itemData);
                                        dailyKillQuestList.put(s, quest);
                                        break;
                                    case "weekly" :
                                        weeklyRewardItemList.put(s, itemData);
                                        weeklyKillQuestList.put(s, quest);
                                        break;
                                    case "monthly" :
                                        monthlyRewardItemList.put(s, itemData);
                                        monthlyKillQuestList.put(s, quest);
                                        break;
                                }
                            }
                        }
                    }
                    //Get Kategorie
                    if (config.contains("quest." + sQuestType + "get.")) {
                        ConfigurationSection section = config.getConfigurationSection("quest." + sQuestType + "get.");
                        for (String s : section.getKeys(false)) {
                            ConfigurationSection keysSection = config.getConfigurationSection("quest." + sQuestType + "get." + s + ".");
                            for (String s2 : keysSection.getKeys(false)) {
                                int quest = config.getInt("quest." + sQuestType + "get." + s + "." + "quest");
                                if (config.contains("quest." + sQuestType + "get." + s + "." + "money")) {
                                    int money = config.getInt("quest." + sQuestType + "get." + s + "." + "money");

                                    switch (rawQuestType) {
                                        case "daily" :
                                            dailyRewardMoneyList.put(s, money);
                                            break;
                                        case "weekly" :
                                            weeklyRewardMoneyList.put(s, money);
                                            break;
                                        case "monthly" :
                                            monthlyRewardMoneyList.put(s, money);
                                            break;
                                    }
                                }
                                //Material, anzahl
                                ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + sQuestType + "get." + s + "." + "items.");
                                Map<String, Object> itemData = new HashMap<>();
                                for (String s3 : keysRewardSection.getKeys(false)) {
                                    if (config.contains("quest." + sQuestType + "get." + s + "." + "items." + s3)) {
                                        int amount = config.getInt("quest." + sQuestType + "get." + s + "." + "items." + s3);
                                        //s ist die questid
                                        itemData.put(s3, amount);
                                    }
                                }
                                switch (rawQuestType) {
                                    case "daily" :
                                        dailyRewardItemList.put(s, itemData);
                                        dailyGetQuestList.put(s, quest);
                                        break;
                                    case "weekly" :
                                        weeklyRewardItemList.put(s, itemData);
                                        weeklyGetQuestList.put(s, quest);
                                        break;
                                    case "monthly" :
                                        monthlyRewardItemList.put(s, itemData);
                                        monthlyGetQuestList.put(s, quest);
                                        break;
                                }
                            }
                        }
                    }
                    //Damage Kategorie
                    if (config.contains("quest." + sQuestType + "damage.")) {
                        ConfigurationSection section = config.getConfigurationSection("quest." + sQuestType + "damage.");
                        for (String s : section.getKeys(false)) {
                            ConfigurationSection keysSection = config.getConfigurationSection("quest." + sQuestType + "damage." + s + ".");
                            for (String s2 : keysSection.getKeys(false)) {
                                int quest = config.getInt("quest." + sQuestType + "damage." + s + "." + "quest");
                                if (config.contains("quest." + sQuestType + "damage." + s + "." + "money")) {
                                    int money = config.getInt("quest." + sQuestType + "damage." + s + "." + "money");

                                    switch (rawQuestType) {
                                        case "daily" :
                                            dailyRewardMoneyList.put(s, money);
                                            break;
                                        case "weekly" :
                                            weeklyRewardMoneyList.put(s, money);
                                            break;
                                        case "monthly" :
                                            monthlyRewardMoneyList.put(s, money);
                                            break;
                                    }
                                }
                                //Material, anzahl
                                ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + sQuestType + "damage." + s + "." + "items.");
                                Map<String, Object> itemData = new HashMap<>();
                                for (String s3 : keysRewardSection.getKeys(false)) {
                                    if (config.contains("quest." + sQuestType + "damage." + s + "." + "items." + s3)) {
                                        int amount = config.getInt("quest." + sQuestType + "damage." + s + "." + "items." + s3);
                                        //s ist die questid
                                        itemData.put(s3, amount);
                                    }
                                }
                                switch (rawQuestType) {
                                    case "daily" :
                                        dailyRewardItemList.put(s, itemData);
                                        dailyDamageQuestList.put(s, quest);
                                        break;
                                    case "weekly" :
                                        weeklyRewardItemList.put(s, itemData);
                                        weeklyDamageQuestList.put(s, quest);
                                        break;
                                    case "monthly" :
                                        monthlyRewardItemList.put(s, itemData);
                                        monthlyDamageQuestList.put(s, quest);
                                        break;
                                }
                            }
                        }
                    }
                    //Eat Kategorie
                    if (config.contains("quest." + sQuestType + "eat.")) {
                        ConfigurationSection section = config.getConfigurationSection("quest." + sQuestType + "eat.");
                        for (String s : section.getKeys(false)) {
                            ConfigurationSection keysSection = config.getConfigurationSection("quest." + sQuestType + "eat." + s + ".");
                            for (String s2 : keysSection.getKeys(false)) {
                                int quest = config.getInt("quest." + sQuestType + "eat." + s + "." + "quest");
                                if (config.contains("quest." + sQuestType + "eat." + s + "." + "money")) {
                                    int money = config.getInt("quest." + sQuestType + "eat." + s + "." + "money");

                                    switch (rawQuestType) {
                                        case "daily" :
                                            dailyRewardMoneyList.put(s, money);
                                            break;
                                        case "weekly" :
                                            weeklyRewardMoneyList.put(s, money);
                                            break;
                                        case "monthly" :
                                            monthlyRewardMoneyList.put(s, money);
                                            break;
                                    }
                                }
                                //Material, anzahl
                                ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + sQuestType + "eat." + s + "." + "items.");
                                Map<String, Object> itemData = new HashMap<>();
                                for (String s3 : keysRewardSection.getKeys(false)) {
                                    if (config.contains("quest." + sQuestType + "eat." + s + "." + "items." + s3)) {
                                        int amount = config.getInt("quest." + sQuestType + "eat." + s + "." + "items." + s3);
                                        //s ist die questid
                                        itemData.put(s3, amount);
                                    }
                                }
                                switch (rawQuestType) {
                                    case "daily" :
                                        dailyRewardItemList.put(s, itemData);
                                        dailyEatlQuestList.put(s, quest);
                                        break;
                                    case "weekly" :
                                        weeklyRewardItemList.put(s, itemData);
                                        weeklyEatlQuestList.put(s, quest);
                                        break;
                                    case "monthly" :
                                        monthlyRewardItemList.put(s, itemData);
                                        monthlyEatlQuestList.put(s, quest);
                                        break;
                                }
                            }
                        }
                    }
                    //Drink Kategorie
                    if (config.contains("quest." + sQuestType + "drink.")) {
                        ConfigurationSection section = config.getConfigurationSection("quest." + sQuestType + "drink.");
                        for (String s : section.getKeys(false)) {
                            ConfigurationSection keysSection = config.getConfigurationSection("quest." + sQuestType + "drink." + s + ".");
                            for (String s2 : keysSection.getKeys(false)) {
                                int quest = config.getInt("quest." + sQuestType + "drink." + s + "." + "quest");
                                if (config.contains("quest." + sQuestType + "drink." + s + "." + "money")) {
                                    int money = config.getInt("quest." + sQuestType + "drink." + s + "." + "money");

                                    switch (rawQuestType) {
                                        case "daily" :
                                            dailyRewardMoneyList.put(s, money);
                                            break;
                                        case "weekly" :
                                            weeklyRewardMoneyList.put(s, money);
                                            break;
                                        case "monthly" :
                                            monthlyRewardMoneyList.put(s, money);
                                            break;
                                    }
                                }
                                //Material, anzahl
                                ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + sQuestType + "drink." + s + "." + "items.");
                                Map<String, Object> itemData = new HashMap<>();
                                for (String s3 : keysRewardSection.getKeys(false)) {
                                    if (config.contains("quest." + sQuestType + "drink." + s + "." + "items." + s3)) {
                                        int amount = config.getInt("quest." + sQuestType + "drink." + s + "." + "items." + s3);
                                        //s ist die questid
                                        itemData.put(s3, amount);
                                    }
                                }
                                switch (rawQuestType) {
                                    case "daily" :
                                        dailyRewardItemList.put(s, itemData);
                                        dailyDrinkQuestList.put(s, quest);
                                        break;
                                    case "weekly" :
                                        weeklyRewardItemList.put(s, itemData);
                                        weeklyDrinkQuestList.put(s, quest);
                                        break;
                                    case "monthly" :
                                        monthlyRewardItemList.put(s, itemData);
                                        monthlyDrinkQuestList.put(s, quest);
                                        break;
                                }
                            }
                        }
                    }
                }
            }
//            //Daily
//            if (config.contains("quest." + "daily.")) {
//                //Move Kategorie
//                if (config.contains("quest." + "daily." + "move.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "daily." + "move.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "daily." + "move." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "daily." + "move." + s + "." + "quest");
//                            if (config.contains("quest." + "daily." + "move." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "daily." + "move." + s + "." + "money");
//                                dailyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "daily." + "move." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "daily." + "move." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "daily." + "move." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            dailyRewardItemList.put(s, itemData);
//                            dailyMoveQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Play Kategorie
//                if (config.contains("quest." + "daily." + "play.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "daily." + "play.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "daily." + "play." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "daily." + "play." + s + "." + "quest");
//                            if (config.contains("quest." + "daily." + "play." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "daily." + "play." + s + "." + "money");
//                                dailyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "daily." + "play." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "daily." + "play." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "daily." + "play." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            dailyRewardItemList.put(s, itemData);
//                            dailyPlayQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Kill Kategorie
//                if (config.contains("quest." + "daily." + "kill.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "daily." + "kill.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "daily." + "kill." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "daily." + "kill." + s + "." + "quest");
//                            if (config.contains("quest." + "daily." + "kill." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "daily." + "kill." + s + "." + "money");
//                                dailyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "daily." + "kill." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "daily." + "kill." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "daily." + "kill." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            dailyRewardItemList.put(s, itemData);
//                            dailyKillQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Get Kategorie
//                if (config.contains("quest." + "daily." + "get.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "daily." + "get.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "daily." + "get." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "daily." + "get." + s + "." + "quest");
//                            if (config.contains("quest." + "daily." + "get." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "daily." + "get." + s + "." + "money");
//                                dailyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "daily." + "get." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "daily." + "get." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "daily." + "get." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            dailyRewardItemList.put(s, itemData);
//                            dailyGetQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Damage Kategorie
//                if (config.contains("quest." + "daily." + "damage.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "daily." + "damage.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "daily." + "damage." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "daily." + "damage." + s + "." + "quest");
//                            if (config.contains("quest." + "daily." + "damage." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "daily." + "damage." + s + "." + "money");
//                                dailyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "daily." + "damage." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "daily." + "damage." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "daily." + "damage." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            dailyRewardItemList.put(s, itemData);
//                            dailydamageQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Eat Kategorie
//                if (config.contains("quest." + "daily." + "eat.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "daily." + "eat.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "daily." + "eat." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "daily." + "eat." + s + "." + "quest");
//                            if (config.contains("quest." + "daily." + "eat." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "daily." + "eat." + s + "." + "money");
//                                dailyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "daily." + "eat." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "daily." + "eat." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "daily." + "eat." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            dailyRewardItemList.put(s, itemData);
//                            dailyeatlQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Drink Kategorie
//                if (config.contains("quest." + "daily." + "drink.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "daily." + "drink.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "daily." + "drink." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "daily." + "drink." + s + "." + "quest");
//                            if (config.contains("quest." + "daily." + "drink." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "daily." + "drink." + s + "." + "money");
//                                dailyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "daily." + "drink." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "daily." + "drink." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "daily." + "drink." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            dailyRewardItemList.put(s, itemData);
//                            dailydrinkQuestList.put(s, quest);
//                        }
//                    }
//                }
//            }
//            //Weekly
//            if (config.contains("quest." + "weekly.")) {
//                //Move Kategorie
//                if (config.contains("quest." + "weekly." + "move.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "weekly." + "move.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "weekly." + "move." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "weekly." + "move." + s + "." + "quest");
//                            if (config.contains("quest." + "weekly." + "move." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "weekly." + "move." + s + "." + "money");
//                                weeklyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "weekly." + "move." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "weekly." + "move." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "weekly." + "move." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            weeklyRewardItemList.put(s, itemData);
//                            weeklyMoveQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Play Kategorie
//                if (config.contains("quest." + "weekly." + "play.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "weekly." + "play.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "weekly." + "play." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "weekly." + "play." + s + "." + "quest");
//                            if (config.contains("quest." + "weekly." + "play." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "weekly." + "play." + s + "." + "money");
//                                weeklyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "weekly." + "play." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "weekly." + "play." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "weekly." + "play." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            weeklyRewardItemList.put(s, itemData);
//                            weeklyPlayQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Kill Kategorie
//                if (config.contains("quest." + "weekly." + "kill.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "weekly." + "kill.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "weekly." + "kill." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "weekly." + "kill." + s + "." + "quest");
//                            if (config.contains("quest." + "weekly." + "kill." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "weekly." + "kill." + s + "." + "money");
//                                weeklyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "weekly." + "kill." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "weekly." + "kill." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "weekly." + "kill." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            weeklyRewardItemList.put(s, itemData);
//                            weeklyKillQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Get Kategorie
//                if (config.contains("quest." + "weekly." + "get.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "weekly." + "get.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "weekly." + "get." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "weekly." + "get." + s + "." + "quest");
//                            if (config.contains("quest." + "weekly." + "get." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "weekly." + "get." + s + "." + "money");
//                                weeklyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "weekly." + "get." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "weekly." + "get." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "weekly." + "get." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            weeklyRewardItemList.put(s, itemData);
//                            weeklyGetQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Damage Kategorie
//                if (config.contains("quest." + "weekly." + "damage.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "weekly." + "damage.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "weekly." + "damage." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "weekly." + "damage." + s + "." + "quest");
//                            if (config.contains("quest." + "weekly." + "damage." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "weekly." + "damage." + s + "." + "money");
//                                weeklyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "weekly." + "damage." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "weekly." + "damage." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "weekly." + "damage." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            weeklyRewardItemList.put(s, itemData);
//                            weeklydamageQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Eat Kategorie
//                if (config.contains("quest." + "weekly." + "eat.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "weekly." + "eat.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "weekly." + "eat." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "weekly." + "eat." + s + "." + "quest");
//                            if (config.contains("quest." + "weekly." + "eat." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "weekly." + "eat." + s + "." + "money");
//                                weeklyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "weekly." + "eat." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "weekly." + "eat." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "weekly." + "eat." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            weeklyRewardItemList.put(s, itemData);
//                            weeklyeatlQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Drink Kategorie
//                if (config.contains("quest." + "weekly." + "drink.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "weekly." + "drink.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "weekly." + "drink." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "weekly." + "drink." + s + "." + "quest");
//                            if (config.contains("quest." + "weekly." + "drink." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "weekly." + "drink." + s + "." + "money");
//                                weeklyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "weekly." + "drink." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "weekly." + "drink." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "weekly." + "drink." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            weeklyRewardItemList.put(s, itemData);
//                            weeklydrinkQuestList.put(s, quest);
//                        }
//                    }
//                }
//            }
//            //Monthly
//            if (config.contains("quest." + "weekly.")) {
//                //Move Kategorie
//                if (config.contains("quest." + "weekly." + "move.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "weekly." + "move.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "weekly." + "move." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "weekly." + "move." + s + "." + "quest");
//                            if (config.contains("quest." + "weekly." + "move." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "weekly." + "move." + s + "." + "money");
//                                weeklyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "weekly." + "move." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "weekly." + "move." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "weekly." + "move." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            weeklyRewardItemList.put(s, itemData);
//                            weeklyMoveQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Play Kategorie
//                if (config.contains("quest." + "weekly." + "play.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "weekly." + "play.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "weekly." + "play." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "weekly." + "play." + s + "." + "quest");
//                            if (config.contains("quest." + "weekly." + "play." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "weekly." + "play." + s + "." + "money");
//                                weeklyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "weekly." + "play." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "weekly." + "play." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "weekly." + "play." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            weeklyRewardItemList.put(s, itemData);
//                            weeklyPlayQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Kill Kategorie
//                if (config.contains("quest." + "weekly." + "kill.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "weekly." + "kill.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "weekly." + "kill." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "weekly." + "kill." + s + "." + "quest");
//                            if (config.contains("quest." + "weekly." + "kill." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "weekly." + "kill." + s + "." + "money");
//                                weeklyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "weekly." + "kill." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "weekly." + "kill." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "weekly." + "kill." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            weeklyRewardItemList.put(s, itemData);
//                            weeklyKillQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Get Kategorie
//                if (config.contains("quest." + "weekly." + "get.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "weekly." + "get.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "weekly." + "get." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "weekly." + "get." + s + "." + "quest");
//                            if (config.contains("quest." + "weekly." + "get." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "weekly." + "get." + s + "." + "money");
//                                weeklyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "weekly." + "get." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "weekly." + "get." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "weekly." + "get." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            weeklyRewardItemList.put(s, itemData);
//                            weeklyGetQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Damage Kategorie
//                if (config.contains("quest." + "weekly." + "damage.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "weekly." + "damage.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "weekly." + "damage." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "weekly." + "damage." + s + "." + "quest");
//                            if (config.contains("quest." + "weekly." + "damage." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "weekly." + "damage." + s + "." + "money");
//                                weeklyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "weekly." + "damage." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "weekly." + "damage." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "weekly." + "damage." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            weeklyRewardItemList.put(s, itemData);
//                            weeklydamageQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Eat Kategorie
//                if (config.contains("quest." + "weekly." + "eat.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "weekly." + "eat.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "weekly." + "eat." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "weekly." + "eat." + s + "." + "quest");
//                            if (config.contains("quest." + "weekly." + "eat." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "weekly." + "eat." + s + "." + "money");
//                                weeklyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "weekly." + "eat." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "weekly." + "eat." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "weekly." + "eat." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            weeklyRewardItemList.put(s, itemData);
//                            weeklyeatlQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Drink Kategorie
//                if (config.contains("quest." + "weekly." + "drink.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "weekly." + "drink.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "weekly." + "drink." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "weekly." + "drink." + s + "." + "quest");
//                            if (config.contains("quest." + "weekly." + "drink." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "weekly." + "drink." + s + "." + "money");
//                                weeklyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "weekly." + "drink." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "weekly." + "drink." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "weekly." + "drink." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            weeklyRewardItemList.put(s, itemData);
//                            weeklydrinkQuestList.put(s, quest);
//                        }
//                    }
//                }
//            }
//            //Weekly
//            if (config.contains("quest." + sQuestType)) {
//                //Move Kategorie
//                if (config.contains("quest." + sQuestType + "move.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "weekly." + "move.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "weekly." + "move." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "weekly." + "move." + s + "." + "quest");
//                            if (config.contains("quest." + "weekly." + "move." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "weekly." + "move." + s + "." + "money");
//                                weeklyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "weekly." + "move." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "weekly." + "move." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "weekly." + "move." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            weeklyRewardItemList.put(s, itemData);
//                            weeklyMoveQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Play Kategorie
//                if (config.contains("quest." + "weekly." + "play.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "weekly." + "play.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "weekly." + "play." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "weekly." + "play." + s + "." + "quest");
//                            if (config.contains("quest." + "weekly." + "play." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "weekly." + "play." + s + "." + "money");
//                                weeklyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "weekly." + "play." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "weekly." + "play." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "weekly." + "play." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            weeklyRewardItemList.put(s, itemData);
//                            weeklyPlayQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Kill Kategorie
//                if (config.contains("quest." + "weekly." + "kill.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "weekly." + "kill.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "weekly." + "kill." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "weekly." + "kill." + s + "." + "quest");
//                            if (config.contains("quest." + "weekly." + "kill." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "kill." + "play." + s + "." + "money");
//                                weeklyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "weekly." + "kill." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "weekly." + "kill." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "weekly." + "kill." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            weeklyRewardItemList.put(s, itemData);
//                            weeklyKillQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Get Kategorie
//                if (config.contains("quest." + "weekly." + "get.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "weekly." + "get.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "weekly." + "get." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "weekly." + "get." + s + "." + "quest");
//                            if (config.contains("quest." + "weekly." + "get." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "get." + "play." + s + "." + "money");
//                                weeklyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "weekly." + "get." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "weekly." + "get." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "weekly." + "get." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            weeklyRewardItemList.put(s, itemData);
//                            weeklyGetQuestList.put(s, quest);
//                        }
//                    }
//                }
//            }
//            //Monthly
//            if (config.contains("quest." + "monthly.")) {
//                //Move Kategorie
//                if (config.contains("quest." + "monthly." + "move.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "monthly." + "move.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "monthly." + "move." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "monthly." + "move." + s + "." + "quest");
//                            if (config.contains("quest." + "monthly." + "move." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "monthly." + "move." + s + "." + "money");
//                                monthlyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "monthly." + "move." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "monthly." + "move." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "monthly." + "move." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            monthlyRewardItemList.put(s, itemData);
//                            monthlyMoveQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Play Kategorie
//                if (config.contains("quest." + "monthly." + "play.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "monthly." + "play.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "monthly." + "play." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "monthly." + "play." + s + "." + "quest");
//                            if (config.contains("quest." + "monthly." + "play." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "monthly." + "play." + s + "." + "money");
//                                monthlyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "monthly." + "play." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "monthly." + "play." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "monthly." + "play." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            monthlyRewardItemList.put(s, itemData);
//                            monthlyPlayQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Kill Kategorie
//                if (config.contains("quest." + "monthly." + "kill.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "monthly." + "kill.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "monthly." + "kill." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "monthly." + "kill." + s + "." + "quest");
//                            if (config.contains("quest." + "monthly." + "kill." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "kill." + "play." + s + "." + "money");
//                                monthlyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "monthly." + "kill." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "monthly." + "kill." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "monthly." + "kill." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            monthlyRewardItemList.put(s, itemData);
//                            monthlyKillQuestList.put(s, quest);
//                        }
//                    }
//                }
//                //Get Kategorie
//                if (config.contains("quest." + "monthly." + "get.")) {
//                    ConfigurationSection section = config.getConfigurationSection("quest." + "monthly." + "get.");
//                    for (String s : section.getKeys(false)) {
//                        ConfigurationSection keysSection = config.getConfigurationSection("quest." + "monthly." + "get." + s + ".");
//                        for (String s2 : keysSection.getKeys(false)) {
//                            int quest = config.getInt("quest." + "monthly." + "get." + s + "." + "quest");
//                            if (config.contains("quest." + "monthly." + "get." + s + "." + "money")) {
//                                int money = config.getInt("quest." + "get." + "play." + s + "." + "money");
//                                monthlyRewardMoneyList.put(s, money);
//                            }
//                            //Material, anzahl
//                            ConfigurationSection keysRewardSection = config.getConfigurationSection("quest." + "monthly." + "get." + s + "." + "items.");
//                            Map<String, Object> itemData = new HashMap<>();
//                            for (String s3 : keysRewardSection.getKeys(false)) {
//                                if (config.contains("quest." + "monthly." + "get." + s + "." + "items." + s3)) {
//                                    int amount = config.getInt("quest." + "monthly." + "get." + s + "." + "items." + s3);
//                                    //s ist die questid
//                                    itemData.put(s3, amount);
//                                }
//                            }
//                            monthlyRewardItemList.put(s, itemData);
//                            monthlyGetQuestList.put(s, quest);
//                        }
//                    }
//                }
//            }
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

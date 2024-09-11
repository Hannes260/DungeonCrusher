package net.dbsgameplay.dungeoncrusher.utils.quests;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.Shop.FoodCategory;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.QuestBuilder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.*;

public class Daily {
    public static HashMap<String, Map<String, Object>> dailyRewardItemList = new HashMap<>();
    public static HashMap<String, Integer> dailyRewardMoneyList = new HashMap<>();

    public static HashMap<String, Integer> dailyMoveQuestList = new HashMap<>();
    public static HashMap<String, Integer> dailyPlayQuestList = new HashMap<>();
    public static HashMap<String, Integer> dailyKillQuestList = new HashMap<>();
    public static HashMap<String, Integer> dailyGetQuestList = new HashMap<>();

    public static List<String> dailyQuests = new ArrayList<>();

    public static MYSQLManager mysqlManager;
    public static DungeonCrusher dungeonCrusher;

    public Daily(MYSQLManager mysqlManager, DungeonCrusher dungeonCrusher) {
        this.mysqlManager = mysqlManager;
        this.dungeonCrusher = dungeonCrusher;
    }

    public static void loadDaily() {
        for (String s : dailyMoveQuestList.keySet()) {
            dailyQuests.add(s);
        }
        for (String s : dailyGetQuestList.keySet()) {
            dailyQuests.add(s);
        }
        for (String s : dailyKillQuestList.keySet()) {
            dailyQuests.add(s);
        }
        for (String s : dailyPlayQuestList.keySet()) {
            dailyQuests.add(s);
        }

        Bukkit.getLogger().info(dailyRewardMoneyList.toString());
        Bukkit.getLogger().info(dailyRewardMoneyList.toString());
    }

    public static void checkForDailyOrginQuest() {
        //Daily
        if (mysqlManager.getOrginQuest("daily1") == null) {
            Random random = new Random();
            int rdmNum = random.nextInt(0, dailyQuests.size());
            String key = dailyQuests.get(rdmNum);
            mysqlManager.updateOrginQuest("daily1", key);
        }
        if (mysqlManager.getOrginQuest("daily2") == null) {
            Random random = new Random();
            int rdmNum = random.nextInt(0, dailyQuests.size());
            String key = dailyQuests.get(rdmNum);
            if (key == mysqlManager.getOrginQuest("daily1")) {
                while (key == mysqlManager.getOrginQuest("daily1")) {
                    rdmNum = random.nextInt(0, dailyQuests.size());
                    key = dailyQuests.get(rdmNum);
                    Bukkit.getLogger().info("1");
                }
                mysqlManager.updateOrginQuest("daily2", key);
            }else {
                mysqlManager.updateOrginQuest("daily2", key);
            }
        }
        if (mysqlManager.getOrginQuest("daily3") == null) {
            Random random = new Random();
            int rdmNum = random.nextInt(0, dailyQuests.size());
            String key = dailyQuests.get(rdmNum);
            if (key == mysqlManager.getOrginQuest("daily1") || key == mysqlManager.getOrginQuest("daily2")) {
                while (key == mysqlManager.getOrginQuest("daily1") || key == mysqlManager.getOrginQuest("daily2")) {
                    rdmNum = random.nextInt(0, dailyQuests.size());
                    key = dailyQuests.get(rdmNum);
                    Bukkit.getLogger().info("1");
                }
                mysqlManager.updateOrginQuest("daily3", key);
            }else {
                mysqlManager.updateOrginQuest("daily3", key);
            }
        }
    }

    public static void checkForDailyOrginQuestUpdate() {
        //Daily
        Date nowdaily = new Date(System.currentTimeMillis());
        SimpleDateFormat formatdaily = new SimpleDateFormat("HH:mm:ss");

        if (formatdaily.format(nowdaily).equalsIgnoreCase("00:00:01")) {
            //Daily
            if (mysqlManager.getOrginQuest("daily1") == null) {
                Random random = new Random();
                int rdmNum = random.nextInt(0, dailyQuests.size());
                String key = dailyQuests.get(rdmNum);
                mysqlManager.updateOrginQuest("daily1", key);
            }
            if (mysqlManager.getOrginQuest("daily2") == null) {
                Random random = new Random();
                int rdmNum = random.nextInt(0, dailyQuests.size());
                String key = dailyQuests.get(rdmNum);
                if (key == mysqlManager.getOrginQuest("daily1")) {
                    while (key == mysqlManager.getOrginQuest("daily1")) {
                        rdmNum = random.nextInt(0, dailyQuests.size());
                        key = dailyQuests.get(rdmNum);
                        Bukkit.getLogger().info("1");
                    }
                    mysqlManager.updateOrginQuest("daily2", key);
                }else {
                    mysqlManager.updateOrginQuest("daily2", key);
                }
            }
            if (mysqlManager.getOrginQuest("daily3") == null) {
                Random random = new Random();
                int rdmNum = random.nextInt(0, dailyQuests.size());
                String key = dailyQuests.get(rdmNum);
                if (key == mysqlManager.getOrginQuest("daily1") || key == mysqlManager.getOrginQuest("daily2")) {
                    while (key == mysqlManager.getOrginQuest("daily1") || key == mysqlManager.getOrginQuest("daily2")) {
                        rdmNum = random.nextInt(0, dailyQuests.size());
                        key = dailyQuests.get(rdmNum);
                        Bukkit.getLogger().info("1");
                    }
                    mysqlManager.updateOrginQuest("daily3", key);
                }else {
                    mysqlManager.updateOrginQuest("daily3", key);
                }
            }
            for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()) {
                mysqlManager.updatePlayerQuest( "daily1",false, p.getUniqueId().toString());
                mysqlManager.updatePlayerQuest( "daily2",false, p.getUniqueId().toString());
                mysqlManager.updatePlayerQuest( "daily3",false, p.getUniqueId().toString());
                dungeonCrusher.getConfig().set("quest." + p.getUniqueId().toString() + "." + "daily1", 0);
                dungeonCrusher.getConfig().set("quest." + p.getUniqueId().toString() + "." + "daily2", 0);
                dungeonCrusher.getConfig().set("quest." + p.getUniqueId().toString() + "." + "daily3", 0);
                dungeonCrusher.saveConfig();
            }
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                mysqlManager.updatePlayerQuest( "daily1",false, p.getUniqueId().toString());
                mysqlManager.updatePlayerQuest( "daily2",false, p.getUniqueId().toString());
                mysqlManager.updatePlayerQuest( "daily3",false, p.getUniqueId().toString());
                dungeonCrusher.getConfig().set("quest." + p.getUniqueId().toString() + "." + "daily1", 0);
                dungeonCrusher.getConfig().set("quest." + p.getUniqueId().toString() + "." + "daily2", 0);
                dungeonCrusher.getConfig().set("quest." + p.getUniqueId().toString() + "." + "daily3", 0);
                dungeonCrusher.saveConfig();
            }

        }
    }

    public static String getQuestTitle(String questID) {
        String title = null;
        for (String s : dailyPlayQuestList.keySet()) {
            if (s.equals(questID)) {
                title = "Spiele " + dailyPlayQuestList.get(questID) + " Minuten.";
            }
        }
        for (String s : dailyKillQuestList.keySet()) {
            if (s.equals(questID)) {
                title = "Töte " + dailyKillQuestList.get(questID) + " Kreaturen.";
            }
        }
        for (String s : dailyMoveQuestList.keySet()) {
            if (s.equals(questID)) {
                title = "Lege " + dailyMoveQuestList.get(questID) + " Meter zurück.";
            }
        }
        for (String s : dailyGetQuestList.keySet()) {
            if (s.equals(questID)) {
                title = "Sammle " + dailyGetQuestList.get(questID) + " Materialien.";
            }
        }
        return title;
    }

    public static boolean isDailyDone(int num, Player p){
        if (mysqlManager.getPlayerQuest("daily" + num, p.getUniqueId().toString()) == true) {
            return true;
        }
        return false;
    }

    public static void doDailyQuest(Player p, HashMap<String, Integer> questMap) {
        if (QuestBuilder.isTutorialDone(p)) {
            FileConfiguration cfg = dungeonCrusher.getConfig();
            String dailyQuest1 = mysqlManager.getOrginQuest("daily1");
            String dailyQuest2 = mysqlManager.getOrginQuest("daily2");
            String dailyQuest3 = mysqlManager.getOrginQuest("daily3");
            FoodCategory foodCategory = new FoodCategory(mysqlManager);

            for (String s : questMap.keySet()) {
                if (s.equals(dailyQuest1)) {
                    if (!Daily.isDailyDone(1, p)) {
                        String path = "quest." + p.getUniqueId().toString() + "daily1";
                        if (cfg.contains(path)) {
                            if (cfg.getInt(path) == questMap.get(s)) {
                                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
                                QuestBuilder.openRewardmenü(p, s, Daily.dailyRewardItemList);
                                mysqlManager.updatePlayerQuest("daily1", true, p.getUniqueId().toString());
                                if (Daily.dailyRewardMoneyList.get(s) != null) {
                                    foodCategory.addMoney(p, Daily.dailyRewardMoneyList.get(s));
                                    p.sendMessage(" §7[§a+§7] §6" + Daily.dailyRewardMoneyList.get(s) + "€");
                                }
                            } else {
                                cfg.set(path, cfg.getInt(path) + 1);
                                dungeonCrusher.saveConfig();
                            }
                        } else {
                            cfg.set(path, cfg.getInt(path) + 1);
                            dungeonCrusher.saveConfig();
                        }
                    }
                } else if (s.equals(dailyQuest2)) {
                    if (!Daily.isDailyDone(2, p)) {
                        String path = "quest." + p.getUniqueId().toString() + "daily2";
                        if (cfg.contains(path)) {
                            if (cfg.getInt(path) == questMap.get(s)) {
                                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
                                QuestBuilder.openRewardmenü(p, s, Daily.dailyRewardItemList);
                                mysqlManager.updatePlayerQuest("daily2", true, p.getUniqueId().toString());
                                if (Daily.dailyRewardMoneyList.get(s) != null) {
                                    foodCategory.addMoney(p, Daily.dailyRewardMoneyList.get(s));
                                    p.sendMessage(" §7[§a+§7] §6" + Daily.dailyRewardMoneyList.get(s) + "€");
                                }
                            } else {
                                cfg.set(path, cfg.getInt(path) + 1);
                                dungeonCrusher.saveConfig();
                            }
                        } else {
                            cfg.set(path, cfg.getInt(path) + 1);
                            dungeonCrusher.saveConfig();
                        }
                    }
                } else if (s.equals(dailyQuest3)) {
                    if (!Daily.isDailyDone(3, p)) {
                        String path = "quest." + p.getUniqueId().toString() + "daily3";
                        if (cfg.contains(path)) {
                            if (cfg.getInt(path) == questMap.get(s)) {
                                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
                                QuestBuilder.openRewardmenü(p, s, Daily.dailyRewardItemList);
                                mysqlManager.updatePlayerQuest("daily3", true, p.getUniqueId().toString());
                                if (Daily.dailyRewardMoneyList.get(s) != null) {
                                    foodCategory.addMoney(p, Daily.dailyRewardMoneyList.get(s));
                                    p.sendMessage(" §7[§a+§7] §6" + Daily.dailyRewardMoneyList.get(s) + "€");
                                }
                            } else {
                                cfg.set(path, cfg.getInt(path) + 1);
                                dungeonCrusher.saveConfig();
                            }
                        } else {
                            cfg.set(path, cfg.getInt(path) + 1);
                            dungeonCrusher.saveConfig();
                        }
                    }
                }
            }
        }
    }
}

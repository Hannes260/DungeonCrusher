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

public class Monthly {
    public static HashMap<String, Map<String, Object>> RewardItemList = new HashMap<>();
    public static HashMap<String, Integer> RewardMoneyList = new HashMap<>();

    public static HashMap<String, Integer> MoveQuestList = new HashMap<>();
    public static HashMap<String, Integer> PlayQuestList = new HashMap<>();
    public static HashMap<String, Integer> KillQuestList = new HashMap<>();
    public static HashMap<String, Integer> GetQuestList = new HashMap<>();

    public static List<String> Quests = new ArrayList<>();

    public static MYSQLManager mysqlManager;
    public static DungeonCrusher dungeonCrusher;

    public Monthly(MYSQLManager mysqlManager, DungeonCrusher dungeonCrusher) {
        this.mysqlManager = mysqlManager;
        this.dungeonCrusher = dungeonCrusher;
    }

    public static void load() {
        for (String s : MoveQuestList.keySet()) {
            Quests.add(s);
        }
        for (String s : GetQuestList.keySet()) {
            Quests.add(s);
        }
        for (String s : KillQuestList.keySet()) {
            Quests.add(s);
        }
        for (String s : PlayQuestList.keySet()) {
            Quests.add(s);
        }
    }

    public static void checkForOrginQuest() {
        //
        if (mysqlManager.getOrginQuest("monthly1") == null) {
            Random random = new Random();
            int rdmNum = random.nextInt(0, Quests.size());
            String key = Quests.get(rdmNum);
            mysqlManager.updateOrginQuest("monthly1", key);
        }
        if (mysqlManager.getOrginQuest("monthly2") == null) {
            Random random = new Random();
            int rdmNum = random.nextInt(0, Quests.size());
            String key = Quests.get(rdmNum);
            if (key == mysqlManager.getOrginQuest("monthly1")) {
                while (key == mysqlManager.getOrginQuest("monthly1")) {
                    rdmNum = random.nextInt(0, Quests.size());
                    key = Quests.get(rdmNum);
                }
                mysqlManager.updateOrginQuest("monthly2", key);
            }else {
                mysqlManager.updateOrginQuest("monthly2", key);
            }
        }
        if (mysqlManager.getOrginQuest("monthly3") == null) {
            Random random = new Random();
            int rdmNum = random.nextInt(0, Quests.size());
            String key = Quests.get(rdmNum);
            if (key == mysqlManager.getOrginQuest("monthly1") || key == mysqlManager.getOrginQuest("monthly2")) {
                while (key == mysqlManager.getOrginQuest("monthly1") || key == mysqlManager.getOrginQuest("monthly2")) {
                    rdmNum = random.nextInt(0, Quests.size());
                    key = Quests.get(rdmNum);
                }
                mysqlManager.updateOrginQuest("monthly3", key);
            }else {
                mysqlManager.updateOrginQuest("monthly3", key);
            }
        }
    }

    public static void checkForOrginQuestUpdate() {
        //
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("WW");

        if (format.format(now).equalsIgnoreCase("01")) {
            //
            if (mysqlManager.getOrginQuest("monthly1") == null) {
                Random random = new Random();
                int rdmNum = random.nextInt(0, Quests.size());
                String key = Quests.get(rdmNum);
                mysqlManager.updateOrginQuest("monthly1", key);
            }
            if (mysqlManager.getOrginQuest("monthly2") == null) {
                Random random = new Random();
                int rdmNum = random.nextInt(0, Quests.size());
                String key = Quests.get(rdmNum);
                if (key == mysqlManager.getOrginQuest("monthly1")) {
                    while (key == mysqlManager.getOrginQuest("monthly1")) {
                        rdmNum = random.nextInt(0, Quests.size());
                        key = Quests.get(rdmNum);
                    }
                    mysqlManager.updateOrginQuest("monthly2", key);
                } else {
                    mysqlManager.updateOrginQuest("monthly2", key);
                }
            }
            if (mysqlManager.getOrginQuest("monthly3") == null) {
                Random random = new Random();
                int rdmNum = random.nextInt(0, Quests.size());
                String key = Quests.get(rdmNum);
                if (key == mysqlManager.getOrginQuest("monthly1") || key == mysqlManager.getOrginQuest("monthly2")) {
                    while (key == mysqlManager.getOrginQuest("monthly1") || key == mysqlManager.getOrginQuest("monthly2")) {
                        rdmNum = random.nextInt(0, Quests.size());
                        key = Quests.get(rdmNum);
                    }
                    mysqlManager.updateOrginQuest("monthly3", key);
                } else {
                    mysqlManager.updateOrginQuest("monthly3", key);
                }
            }
            for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()) {
                mysqlManager.updatePlayerQuest("monthly1", false, p.getUniqueId().toString());
                mysqlManager.updatePlayerQuest("monthly2", false, p.getUniqueId().toString());
                mysqlManager.updatePlayerQuest("monthly3", false, p.getUniqueId().toString());
                dungeonCrusher.getConfig().set("quest." + p.getUniqueId().toString(), null);
                dungeonCrusher.saveConfig();
            }
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                mysqlManager.updatePlayerQuest("monthly1", false, p.getUniqueId().toString());
                mysqlManager.updatePlayerQuest("monthly2", false, p.getUniqueId().toString());
                mysqlManager.updatePlayerQuest("monthly3", false, p.getUniqueId().toString());
                dungeonCrusher.getConfig().set("quest." + p.getUniqueId().toString(), null);
                dungeonCrusher.saveConfig();
            }
        }
    }

    public static String getQuestTitle(String questID) {
        String title = null;

        for (String s : PlayQuestList.keySet()) {
            if (s.equals(questID)) {
                title = "Spiele " + PlayQuestList.get(questID) + " Minuten.";
            }
        }
        for (String s : KillQuestList.keySet()) {
            if (s.equals(questID)) {
                title = "Töte " + KillQuestList.get(questID) + " Kreaturen.";
            }
        }
        for (String s : MoveQuestList.keySet()) {
            if (s.equals(questID)) {
                title = "Lege " + MoveQuestList.get(questID) + " Meter zurück.";
            }
        }
        for (String s : GetQuestList.keySet()) {
            if (s.equals(questID)) {
                title = "Sammle " + GetQuestList.get(questID) + " Materialien.";
            }
        }
        return title;
    }

    public static boolean isDone(int num, Player p){
        if (mysqlManager.getPlayerQuest("monthly" + num, p.getUniqueId().toString()) == true) {
            return true;
        }
        return false;
    }

    public static void doQuest(Player p, HashMap<String, Integer> questMap) {
        if (QuestBuilder.isTutorialDone(p)) {
            FileConfiguration cfg = dungeonCrusher.getConfig();
            String Quest1 = mysqlManager.getOrginQuest("monthly1");
            String Quest2 = mysqlManager.getOrginQuest("monthly2");
            String Quest3 = mysqlManager.getOrginQuest("monthly3");
            FoodCategory foodCategory = new FoodCategory(mysqlManager);

            for (String s : questMap.keySet()) {
                if (s.equals(Quest1)) {
                    if (!Monthly.isDone(1, p)) {
                        String path = "quest." + p.getUniqueId().toString() + "." + "monthly1";
                        if (cfg.contains(path)) {
                            if (cfg.getInt(path) == questMap.get(s)) {
                                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
                                QuestBuilder.openRewardmenü(p, s, Monthly.RewardItemList);
                                mysqlManager.updatePlayerQuest("monthly1", true, p.getUniqueId().toString());
                                if (Monthly.RewardMoneyList.get(s) != null) {
                                    foodCategory.addMoney(p, Monthly.RewardMoneyList.get(s));
                                    p.sendMessage(" §7[§a+§7] §6" + Monthly.RewardMoneyList.get(s) + "€");
                                }
                                dungeonCrusher.getConfig().set("quest." + p.getUniqueId().toString(), null);
                                dungeonCrusher.saveConfig();
                            } else {
                                cfg.set(path, cfg.getInt(path) + 1);
                                dungeonCrusher.saveConfig();
                            }
                        } else {
                            cfg.set(path, cfg.getInt(path) + 1);
                            dungeonCrusher.saveConfig();
                        }
                    }
                } else if (s.equals(Quest2)) {
                    if (!Monthly.isDone(2, p)) {
                        String path = "quest." + p.getUniqueId().toString() + "." + "monthly2";
                        if (cfg.contains(path)) {
                            if (cfg.getInt(path) == questMap.get(s)) {
                                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
                                QuestBuilder.openRewardmenü(p, s, Monthly.RewardItemList);
                                mysqlManager.updatePlayerQuest("monthly2", true, p.getUniqueId().toString());
                                if (Monthly.RewardMoneyList.get(s) != null) {
                                    foodCategory.addMoney(p, Monthly.RewardMoneyList.get(s));
                                    p.sendMessage(" §7[§a+§7] §6" + Monthly.RewardMoneyList.get(s) + "€");
                                }
                                dungeonCrusher.getConfig().set("quest." + p.getUniqueId().toString(), null);
                                dungeonCrusher.saveConfig();
                            } else {
                                cfg.set(path, cfg.getInt(path) + 1);
                                dungeonCrusher.saveConfig();
                            }
                        } else {
                            cfg.set(path, cfg.getInt(path) + 1);
                            dungeonCrusher.saveConfig();
                        }
                    }
                } else if (s.equals(Quest3)) {
                    if (!Monthly.isDone(3, p)) {
                        String path = "quest." + p.getUniqueId().toString() + "." + "monthly3";
                        if (cfg.contains(path)) {
                            if (cfg.getInt(path) == questMap.get(s)) {
                                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
                                QuestBuilder.openRewardmenü(p, s, Monthly.RewardItemList);
                                mysqlManager.updatePlayerQuest("monthly3", true, p.getUniqueId().toString());
                                if (Monthly.RewardMoneyList.get(s) != null) {
                                    foodCategory.addMoney(p, Monthly.RewardMoneyList.get(s));
                                    p.sendMessage(" §7[§a+§7] §6" + Monthly.RewardMoneyList.get(s) + "€");
                                }
                                dungeonCrusher.getConfig().set("quest." + p.getUniqueId().toString(), null);
                                dungeonCrusher.saveConfig();
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

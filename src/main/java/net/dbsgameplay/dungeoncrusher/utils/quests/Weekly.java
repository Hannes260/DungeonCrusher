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

public class Weekly {
    public static HashMap<String, Map<String, Object>> RewardItemList = new HashMap<>();
    public static HashMap<String, Integer> RewardMoneyList = new HashMap<>();

    public static HashMap<String, Integer> MoveQuestList = new HashMap<>();
    public static HashMap<String, Integer> PlayQuestList = new HashMap<>();
    public static HashMap<String, Integer> KillQuestList = new HashMap<>();
    public static HashMap<String, Integer> GetQuestList = new HashMap<>();

    public static List<String> Quests = new ArrayList<>();

    public static MYSQLManager mysqlManager;
    public static DungeonCrusher dungeonCrusher;

    public Weekly(MYSQLManager mysqlManager, DungeonCrusher dungeonCrusher) {
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
        Bukkit.getLogger().info(Quests.toString());
        if (mysqlManager.getOrginQuest("weekly1") == null) {
            Random random = new Random();
            int rdmNum = random.nextInt(0, Quests.size());
            String key = Quests.get(rdmNum);
            mysqlManager.updateOrginQuest("weekly1", key);
        }
        if (mysqlManager.getOrginQuest("weekly2") == null) {
            Random random = new Random();
            int rdmNum = random.nextInt(0, Quests.size());
            String key = Quests.get(rdmNum);
            if (key == mysqlManager.getOrginQuest("weekly1")) {
                while (key == mysqlManager.getOrginQuest("weekly1")) {
                    rdmNum = random.nextInt(0, Quests.size());
                    key = Quests.get(rdmNum);
                }
                mysqlManager.updateOrginQuest("weekly2", key);
            }else {
                mysqlManager.updateOrginQuest("weekly2", key);
            }
        }
        if (mysqlManager.getOrginQuest("weekly3") == null) {
            Random random = new Random();
            int rdmNum = random.nextInt(0, Quests.size());
            String key = Quests.get(rdmNum);
            if (key == mysqlManager.getOrginQuest("weekly1") || key == mysqlManager.getOrginQuest("weekly2")) {
                while (key == mysqlManager.getOrginQuest("weekly1") || key == mysqlManager.getOrginQuest("weekly2")) {
                    rdmNum = random.nextInt(0, Quests.size());
                    key = Quests.get(rdmNum);
                }
                mysqlManager.updateOrginQuest("weekly3", key);
            }else {
                mysqlManager.updateOrginQuest("weekly3", key);
            }
        }
    }

    public static void checkForOrginQuestUpdate() {
        //
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("FF");

        if (format.format(now).equalsIgnoreCase("01")) {
            //
            if (mysqlManager.getOrginQuest("weekly1") == null) {
                Random random = new Random();
                int rdmNum = random.nextInt(0, Quests.size());
                String key = Quests.get(rdmNum);
                mysqlManager.updateOrginQuest("weekly1", key);
            }
            if (mysqlManager.getOrginQuest("weekly2") == null) {
                Random random = new Random();
                int rdmNum = random.nextInt(0, Quests.size());
                String key = Quests.get(rdmNum);
                if (key == mysqlManager.getOrginQuest("weekly1")) {
                    while (key == mysqlManager.getOrginQuest("weekly1")) {
                        rdmNum = random.nextInt(0, Quests.size());
                        key = Quests.get(rdmNum);
                    }
                    mysqlManager.updateOrginQuest("weekly2", key);
                }else {
                    mysqlManager.updateOrginQuest("weekly2", key);
                }
            }
            if (mysqlManager.getOrginQuest("weekly3") == null) {
                Random random = new Random();
                int rdmNum = random.nextInt(0, Quests.size());
                String key = Quests.get(rdmNum);
                if (key == mysqlManager.getOrginQuest("weekly1") || key == mysqlManager.getOrginQuest("weekly2")) {
                    while (key == mysqlManager.getOrginQuest("weekly1") || key == mysqlManager.getOrginQuest("weekly2")) {
                        rdmNum = random.nextInt(0, Quests.size());
                        key = Quests.get(rdmNum);
                    }
                    mysqlManager.updateOrginQuest("weekly3", key);
                }else {
                    mysqlManager.updateOrginQuest("weekly3", key);
                }
            }
            for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()) {
                mysqlManager.updatePlayerQuest( "weekly1",false, p.getUniqueId().toString());
                mysqlManager.updatePlayerQuest( "weekly2",false, p.getUniqueId().toString());
                mysqlManager.updatePlayerQuest( "weekly3",false, p.getUniqueId().toString());
                mysqlManager.updatePlayerTempQuest("weekly3", p.getUniqueId().toString(), 0);
                mysqlManager.updatePlayerTempQuest("weekly2", p.getUniqueId().toString(), 0);
                mysqlManager.updatePlayerTempQuest("weekly1", p.getUniqueId().toString(), 0);
            }
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                mysqlManager.updatePlayerQuest( "weekly1",false, p.getUniqueId().toString());
                mysqlManager.updatePlayerQuest( "weekly2",false, p.getUniqueId().toString());
                mysqlManager.updatePlayerQuest( "weekly3",false, p.getUniqueId().toString());
                mysqlManager.updatePlayerTempQuest("weekly3", p.getUniqueId().toString(), 0);
                mysqlManager.updatePlayerTempQuest("weekly2", p.getUniqueId().toString(), 0);
                mysqlManager.updatePlayerTempQuest("weekly1", p.getUniqueId().toString(), 0);
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
        if (mysqlManager.getPlayerQuest("weekly" + num, p.getUniqueId().toString()) == true) {
            return true;
        }
        return false;
    }

    public static void doQuest(Player p, HashMap<String, Integer> questMap) {
        if (QuestBuilder.isTutorialDone(p)) {
            FileConfiguration cfg = dungeonCrusher.getConfig();
            String Quest1 = mysqlManager.getOrginQuest("weekly1");
            String Quest2 = mysqlManager.getOrginQuest("weekly2");
            String Quest3 = mysqlManager.getOrginQuest("weekly3");
            FoodCategory foodCategory = new FoodCategory(mysqlManager);

            try {
                mysqlManager.getPlayerTempQuest("weekly1", p.getUniqueId().toString());
                mysqlManager.getPlayerTempQuest("weekly2", p.getUniqueId().toString());
                mysqlManager.getPlayerTempQuest("weekly3", p.getUniqueId().toString());
            }catch (Exception e) {
                mysqlManager.updatePlayerTempQuest("weekly1", p.getUniqueId().toString(), 0);
                mysqlManager.updatePlayerTempQuest("weekly2", p.getUniqueId().toString(), 0);
                mysqlManager.updatePlayerTempQuest("weekly3", p.getUniqueId().toString(), 0);
            }

            for (String s : questMap.keySet()) {
                if (s.equals(Quest1)) {
                    if (!Weekly.isDone(1, p)) {
                            if (mysqlManager.getPlayerTempQuest("weekly1", p.getUniqueId().toString()) == questMap.get(s)) {
                                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
                                QuestBuilder.openRewardmenü(p, s, Weekly.RewardItemList);
                                mysqlManager.updatePlayerQuest("weekly1", true, p.getUniqueId().toString());
                                if (Weekly.RewardMoneyList.get(s) != null) {
                                    foodCategory.addMoney(p, Weekly.RewardMoneyList.get(s));
                                    p.sendMessage(" §7[§a+§7] §6" + Weekly.RewardMoneyList.get(s) + "€");
                                }
                            } else {
                                mysqlManager.updatePlayerTempQuest("weekly1", p.getUniqueId().toString(), mysqlManager.getPlayerTempQuest("weekly1", p.getUniqueId().toString())+1);
                            }
                    }
                } else if (s.equals(Quest2)) {
                    if (!Weekly.isDone(2, p)) {
                            if (mysqlManager.getPlayerTempQuest("weekly2", p.getUniqueId().toString()) == questMap.get(s)) {
                                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
                                QuestBuilder.openRewardmenü(p, s, Weekly.RewardItemList);
                                mysqlManager.updatePlayerQuest("weekly2", true, p.getUniqueId().toString());
                                if (Weekly.RewardMoneyList.get(s) != null) {
                                    foodCategory.addMoney(p, Weekly.RewardMoneyList.get(s));
                                    p.sendMessage(" §7[§a+§7] §6" + Weekly.RewardMoneyList.get(s) + "€");
                                }
                            } else {
                                mysqlManager.updatePlayerTempQuest("weekly2", p.getUniqueId().toString(), mysqlManager.getPlayerTempQuest("weekly2", p.getUniqueId().toString())+1);

                            }
                    }
                } else if (s.equals(Quest3)) {
                    if (!Weekly.isDone(3, p)) {
                            if (mysqlManager.getPlayerTempQuest("weekly3", p.getUniqueId().toString()) == questMap.get(s)) {
                                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
                                QuestBuilder.openRewardmenü(p, s, Weekly.RewardItemList);
                                mysqlManager.updatePlayerQuest("weekly3", true, p.getUniqueId().toString());
                                if (Weekly.RewardMoneyList.get(s) != null) {
                                    foodCategory.addMoney(p, Weekly.RewardMoneyList.get(s));
                                    p.sendMessage(" §7[§a+§7] §6" + Weekly.RewardMoneyList.get(s) + "€");
                                }
                            } else {
                                mysqlManager.updatePlayerTempQuest("weekly3", p.getUniqueId().toString(), mysqlManager.getPlayerTempQuest("weekly3", p.getUniqueId().toString())+1);

                            }
                    }
                }
            }
        }
    }
}
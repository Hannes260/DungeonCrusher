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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Daily {
    public static HashMap<String, Map<String, Object>> RewardItemList = new HashMap<>();
    public static HashMap<String, Integer> RewardMoneyList = new HashMap<>();

    public static HashMap<String, Integer> MoveQuestList = new HashMap<>();
    public static HashMap<String, Integer> PlayQuestList = new HashMap<>();
    public static HashMap<String, Integer> KillQuestList = new HashMap<>();
    public static HashMap<String, Integer> GetQuestList = new HashMap<>();

    public static List<String> Quests = new ArrayList<>();

    public static MYSQLManager mysqlManager;
    public static DungeonCrusher dungeonCrusher;

    public Daily(MYSQLManager mysqlManager, DungeonCrusher dungeonCrusher) {
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
        if (mysqlManager.getOrginQuest("daily1") == null) {
            Random random = new Random();
            int rdmNum = random.nextInt(0, Quests.size());
            String key = Quests.get(rdmNum);
            mysqlManager.updateOrginQuest("daily1", key);
        }
        if (mysqlManager.getOrginQuest("daily2") == null) {
            Random random = new Random();
            int rdmNum = random.nextInt(0, Quests.size());
            String key = Quests.get(rdmNum);
            if (key == mysqlManager.getOrginQuest("daily1")) {
                while (key == mysqlManager.getOrginQuest("daily1")) {
                    rdmNum = random.nextInt(0, Quests.size());
                    key = Quests.get(rdmNum);
                }
                mysqlManager.updateOrginQuest("daily2", key);
            }else {
                mysqlManager.updateOrginQuest("daily2", key);
            }
        }
        if (mysqlManager.getOrginQuest("daily3") == null) {
            Random random = new Random();
            int rdmNum = random.nextInt(0, Quests.size());
            String key = Quests.get(rdmNum);
            if (key == mysqlManager.getOrginQuest("daily1") || key == mysqlManager.getOrginQuest("daily2")) {
                while (key == mysqlManager.getOrginQuest("daily1") || key == mysqlManager.getOrginQuest("daily2")) {
                    rdmNum = random.nextInt(0, Quests.size());
                    key = Quests.get(rdmNum);
                }
                mysqlManager.updateOrginQuest("daily3", key);
            }else {
                mysqlManager.updateOrginQuest("daily3", key);
            }
        }
    }

    public static void checkForOrginQuestUpdate() {
        //
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("FF");

        if (format.format(now).equalsIgnoreCase("01")) {
            //
            if (mysqlManager.getOrginQuest("daily1") == null) {
                Random random = new Random();
                int rdmNum = random.nextInt(0, Quests.size());
                String key = Quests.get(rdmNum);
                mysqlManager.updateOrginQuest("daily1", key);
            }
            if (mysqlManager.getOrginQuest("daily2") == null) {
                Random random = new Random();
                int rdmNum = random.nextInt(0, Quests.size());
                String key = Quests.get(rdmNum);
                if (key == mysqlManager.getOrginQuest("daily1")) {
                    while (key == mysqlManager.getOrginQuest("daily1")) {
                        rdmNum = random.nextInt(0, Quests.size());
                        key = Quests.get(rdmNum);
                    }
                    mysqlManager.updateOrginQuest("daily2", key);
                }else {
                    mysqlManager.updateOrginQuest("daily2", key);
                }
            }
            if (mysqlManager.getOrginQuest("daily3") == null) {
                Random random = new Random();
                int rdmNum = random.nextInt(0, Quests.size());
                String key = Quests.get(rdmNum);
                if (key == mysqlManager.getOrginQuest("daily1") || key == mysqlManager.getOrginQuest("daily2")) {
                    while (key == mysqlManager.getOrginQuest("daily1") || key == mysqlManager.getOrginQuest("daily2")) {
                        rdmNum = random.nextInt(0, Quests.size());
                        key = Quests.get(rdmNum);
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
                mysqlManager.updatePlayerTempQuest("daily3", p.getUniqueId().toString(), 0);
                mysqlManager.updatePlayerTempQuest("daily2", p.getUniqueId().toString(), 0);
                mysqlManager.updatePlayerTempQuest("daily1", p.getUniqueId().toString(), 0);
            }
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                mysqlManager.updatePlayerQuest( "daily1",false, p.getUniqueId().toString());
                mysqlManager.updatePlayerQuest( "daily2",false, p.getUniqueId().toString());
                mysqlManager.updatePlayerQuest( "daily3",false, p.getUniqueId().toString());
                mysqlManager.updatePlayerTempQuest("daily3", p.getUniqueId().toString(), 0);
                mysqlManager.updatePlayerTempQuest("daily2", p.getUniqueId().toString(), 0);
                mysqlManager.updatePlayerTempQuest("daily1", p.getUniqueId().toString(), 0);
            }

        }
    }

    public static String getQuestTitle(String questID) {
        String title = null;
        if (PlayQuestList.containsKey(questID)) {
            title = "Spiele " + PlayQuestList.get(questID) + " Minuten.";
        }
        if (KillQuestList.containsKey(questID)) {
            title = "Töte " + KillQuestList.get(questID) + " Kreaturen.";
        }
        if (MoveQuestList.containsKey(questID)) {
            title = "Lege " + MoveQuestList.get(questID) + " Meter zurück.";
        }
        if (GetQuestList.containsKey(questID)) {
            title = "Sammle " + GetQuestList.get(questID) + " Materialien.";
        }
        return title;
    }

    public static int getQuestAim(String questID) {
        int title = 0;
        if (PlayQuestList.containsKey(questID)) {
            title = PlayQuestList.get(questID);
        }
        if (KillQuestList.containsKey(questID)) {
            title = KillQuestList.get(questID);
        }
        if (MoveQuestList.containsKey(questID)) {
            title = MoveQuestList.get(questID);
        }
        if (GetQuestList.containsKey(questID)) {
            title = GetQuestList.get(questID);
        }
        return title;
    }

    public static boolean isDone(int num, Player p){
        if (mysqlManager.getPlayerQuest("daily" + num, p.getUniqueId().toString()) == true) {
            return true;
        }
        return false;
    }

    public static void doQuest(Player p, HashMap<String, Integer> questMap) {
        if (QuestBuilder.isTutorialDone(p)) {
            FileConfiguration cfg = dungeonCrusher.getConfig();
            String Quest1 = mysqlManager.getOrginQuest("daily1");
            String Quest2 = mysqlManager.getOrginQuest("daily2");
            String Quest3 = mysqlManager.getOrginQuest("daily3");
            FoodCategory foodCategory = new FoodCategory(mysqlManager);

            try {
                mysqlManager.getPlayerTempQuest("daily1", p.getUniqueId().toString());
                mysqlManager.getPlayerTempQuest("daily2", p.getUniqueId().toString());
                mysqlManager.getPlayerTempQuest("daily3", p.getUniqueId().toString());
            }catch (Exception e) {
                mysqlManager.updatePlayerTempQuest("daily1", p.getUniqueId().toString(), 0);
                mysqlManager.updatePlayerTempQuest("daily2", p.getUniqueId().toString(), 0);
                mysqlManager.updatePlayerTempQuest("daily3", p.getUniqueId().toString(), 0);
            }

            for (String s : questMap.keySet()) {
                if (s.equals(Quest1)) {
                    if (!Daily.isDone(1, p)) {
                            if (mysqlManager.getPlayerTempQuest("daily1", p.getUniqueId().toString()) == questMap.get(s)) {
                                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
                                QuestBuilder.unclaimedQuestRewards.put(p.getUniqueId().toString(), Daily.RewardItemList);
                                mysqlManager.updatePlayerQuest("daily1", true, p.getUniqueId().toString());
                                if (Daily.RewardMoneyList.get(s) != null) {
                                    foodCategory.addMoney(p, Daily.RewardMoneyList.get(s));
                                    p.sendMessage(" §7[§a+§7] §6" + Daily.RewardMoneyList.get(s) + "€");
                                }
                            } else {
                                mysqlManager.updatePlayerTempQuest("daily1", p.getUniqueId().toString(), mysqlManager.getPlayerTempQuest("daily1", p.getUniqueId().toString())+1);
                            }
                    }
                } else if (s.equals(Quest2)) {
                    if (!Daily.isDone(2, p)) {
                            if (mysqlManager.getPlayerTempQuest("daily2", p.getUniqueId().toString()) == questMap.get(s)) {
                                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
                                QuestBuilder.unclaimedQuestRewards.put(p.getUniqueId().toString(), Daily.RewardItemList);
                                mysqlManager.updatePlayerQuest("daily2", true, p.getUniqueId().toString());
                                if (Daily.RewardMoneyList.get(s) != null) {
                                    foodCategory.addMoney(p, Daily.RewardMoneyList.get(s));
                                    p.sendMessage(" §7[§a+§7] §6" + Daily.RewardMoneyList.get(s) + "€");
                                }
                            } else {
                                mysqlManager.updatePlayerTempQuest("daily2", p.getUniqueId().toString(), mysqlManager.getPlayerTempQuest("daily2", p.getUniqueId().toString())+1);
                            }
                    }
                } else if (s.equals(Quest3)) {
                    if (!Daily.isDone(3, p)) {
                            if (mysqlManager.getPlayerTempQuest("daily3", p.getUniqueId().toString()) == questMap.get(s)) {
                                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
                                QuestBuilder.unclaimedQuestRewards.put(p.getUniqueId().toString(), Daily.RewardItemList);
                                mysqlManager.updatePlayerQuest("daily3", true, p.getUniqueId().toString());
                                if (Daily.RewardMoneyList.get(s) != null) {
                                    foodCategory.addMoney(p, Daily.RewardMoneyList.get(s));
                                    p.sendMessage(" §7[§a+§7] §6" + Daily.RewardMoneyList.get(s) + "€");
                                }
                            } else {
                                mysqlManager.updatePlayerTempQuest("daily3", p.getUniqueId().toString(), mysqlManager.getPlayerTempQuest("daily3", p.getUniqueId().toString())+1);
                            }
                    }
                }
            }
        }
    }
}

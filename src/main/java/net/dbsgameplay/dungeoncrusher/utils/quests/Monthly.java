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
        Quests.addAll(MoveQuestList.keySet());
        Quests.addAll(GetQuestList.keySet());
        Quests.addAll(KillQuestList.keySet());
        Quests.addAll(PlayQuestList.keySet());
    }

    public static void checkForOrginQuest() {
        String k1 = null;
        String k2 = null;
        String k3 = null;
        
        if (mysqlManager.getOrginQuest("monthly1") == null) {
            Bukkit.getLogger().info("Monthly1 is missing... fixing...");
            Random random = new Random();
            int rdmNum = random.nextInt(0, Quests.size());
            String key = Quests.get(rdmNum);
            k1 = Monthly.getQuestKategorie(key);
            mysqlManager.updateOrginQuest("monthly1", key);
        }
        if (mysqlManager.getOrginQuest("monthly2") == null) {
            Bukkit.getLogger().info("Monthly2 is missing... fixing...");
            Random random = new Random();
            int rdmNum = random.nextInt(0, Quests.size());
            String key = Quests.get(rdmNum);
            k2 = Monthly.getQuestKategorie(key);
            if (key.equalsIgnoreCase(mysqlManager.getOrginQuest("monthly1")) || k1.equalsIgnoreCase(k2)) {
                while (key.equalsIgnoreCase(mysqlManager.getOrginQuest("monthly1")) || k1.equalsIgnoreCase(k2)) {
                    rdmNum = random.nextInt(0, Quests.size());
                    key = Quests.get(rdmNum);
                    k2 = Monthly.getQuestKategorie(key);
                }
                mysqlManager.updateOrginQuest("monthly2", key);
            }else {
                mysqlManager.updateOrginQuest("monthly2", key);
            }
        }
        if (mysqlManager.getOrginQuest("monthly3") == null) {
            Bukkit.getLogger().info("Monthly3 is missing... fixing...");
            Random random = new Random();
            int rdmNum = random.nextInt(0, Quests.size());
            String key = Quests.get(rdmNum);
            k3 = Monthly.getQuestKategorie(key);
            if ((key.equalsIgnoreCase(mysqlManager.getOrginQuest("monthly1")) || k3.equalsIgnoreCase(k1)) || (key.equalsIgnoreCase(mysqlManager.getOrginQuest("monthly2")) || k3.equalsIgnoreCase(k2))) {
                while ((key.equalsIgnoreCase(mysqlManager.getOrginQuest("monthly1")) || k3.equalsIgnoreCase(k1)) || (key.equalsIgnoreCase(mysqlManager.getOrginQuest("monthly2")) || k3.equalsIgnoreCase(k2))) {
                    rdmNum = random.nextInt(0, Quests.size());
                    key = Quests.get(rdmNum);
                    k3 = Monthly.getQuestKategorie(key);
                }
                mysqlManager.updateOrginQuest("monthly3", key);
            }else {
                mysqlManager.updateOrginQuest("monthly3", key);
            }
        }
    }

    public static void checkForOrginQuestUpdate() {
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("dd:HH");

        if (format.format(now).equalsIgnoreCase("01:00")) {
            String k1 = null;
            String k2 = null;
            String k3 = null;

            if (mysqlManager.getOrginQuest("monthly1") != null) {
                Bukkit.getLogger().info("Monthly1 is updating...");
                Random random = new Random();
                int rdmNum = random.nextInt(0, Quests.size());
                String key = Quests.get(rdmNum);
                k1 = Monthly.getQuestKategorie(key);
                mysqlManager.updateOrginQuest("monthly1", key);
            }
            if (mysqlManager.getOrginQuest("monthly2") != null) {
                Bukkit.getLogger().info("Monthly2 is updating...");
                Random random = new Random();
                int rdmNum = random.nextInt(0, Quests.size());
                String key = Quests.get(rdmNum);
                k2 = Monthly.getQuestKategorie(key);
                if (key.equalsIgnoreCase(mysqlManager.getOrginQuest("monthly1")) || k1.equalsIgnoreCase(k2)) {
                    while (key.equalsIgnoreCase(mysqlManager.getOrginQuest("monthly1")) || k1.equalsIgnoreCase(k2)) {
                        rdmNum = random.nextInt(0, Quests.size());
                        key = Quests.get(rdmNum);
                        k2 = Monthly.getQuestKategorie(key);
                    }
                    mysqlManager.updateOrginQuest("monthly2", key);
                }else {
                    mysqlManager.updateOrginQuest("monthly2", key);
                }
            }
            if (mysqlManager.getOrginQuest("monthly3") != null) {
                Bukkit.getLogger().info("Monthly3 is updating...");
                Random random = new Random();
                int rdmNum = random.nextInt(0, Quests.size());
                String key = Quests.get(rdmNum);
                k3 = Monthly.getQuestKategorie(key);
                if ((key.equalsIgnoreCase(mysqlManager.getOrginQuest("monthly1")) || k3.equalsIgnoreCase(k1)) || (key.equalsIgnoreCase(mysqlManager.getOrginQuest("monthly2")) || k3.equalsIgnoreCase(k2))) {
                    while ((key.equalsIgnoreCase(mysqlManager.getOrginQuest("monthly1")) || k3.equalsIgnoreCase(k1)) || (key.equalsIgnoreCase(mysqlManager.getOrginQuest("monthly2")) || k3.equalsIgnoreCase(k2))) {
                        rdmNum = random.nextInt(0, Quests.size());
                        key = Quests.get(rdmNum);
                        k3 = Monthly.getQuestKategorie(key);
                    }
                    mysqlManager.updateOrginQuest("monthly3", key);
                }else {
                    mysqlManager.updateOrginQuest("monthly3", key);
                }
            }
            Bukkit.getLogger().info("Reseting Monthly PlayerTempQuests...");
            for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()) {
                mysqlManager.updatePlayerQuest("monthly1", false, p.getUniqueId().toString());
                mysqlManager.updatePlayerQuest("monthly2", false, p.getUniqueId().toString());
                mysqlManager.updatePlayerQuest("monthly3", false, p.getUniqueId().toString());
                mysqlManager.updatePlayerTempQuest("monthly3", p.getUniqueId().toString(), 0);
                mysqlManager.updatePlayerTempQuest("monthly2", p.getUniqueId().toString(), 0);
                mysqlManager.updatePlayerTempQuest("monthly1", p.getUniqueId().toString(), 0);
            }
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                mysqlManager.updatePlayerQuest("monthly1", false, p.getUniqueId().toString());
                mysqlManager.updatePlayerQuest("monthly2", false, p.getUniqueId().toString());
                mysqlManager.updatePlayerQuest("monthly3", false, p.getUniqueId().toString());
                mysqlManager.updatePlayerTempQuest("monthly3", p.getUniqueId().toString(), 0);
                mysqlManager.updatePlayerTempQuest("monthly2", p.getUniqueId().toString(), 0);
                mysqlManager.updatePlayerTempQuest("monthly1", p.getUniqueId().toString(), 0);
            }
        }
    }

    public static String getQuestKategorie(String questID) {
        String kategorie = null;
        if (PlayQuestList.containsKey(questID)) {
            kategorie = "Play";
            return kategorie;
        }
        if (KillQuestList.containsKey(questID)) {
            kategorie = "Kill";
            return kategorie;
        }
        if (MoveQuestList.containsKey(questID)) {
            kategorie = "Move";
            return kategorie;
        }
        if (GetQuestList.containsKey(questID)) {
            kategorie = "Get";
            return kategorie;
        }

        return kategorie;
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
        return mysqlManager.getPlayerQuest("monthly" + num, p.getUniqueId().toString());
    }

    public static void doQuest(Player p, HashMap<String, Integer> questMap) {
        if (QuestBuilder.isTutorialDone(p)) {
            FileConfiguration cfg = dungeonCrusher.getConfig();
            String Quest1 = mysqlManager.getOrginQuest("monthly1");
            String Quest2 = mysqlManager.getOrginQuest("monthly2");
            String Quest3 = mysqlManager.getOrginQuest("monthly3");
            FoodCategory foodCategory = new FoodCategory(mysqlManager);

            try {
                mysqlManager.getPlayerTempQuest("monthly1", p.getUniqueId().toString());
                mysqlManager.getPlayerTempQuest("monthly2", p.getUniqueId().toString());
                mysqlManager.getPlayerTempQuest("monthly3", p.getUniqueId().toString());
            }catch (Exception e) {
                Bukkit.getLogger().info("Monthly temps reseting... ");
                mysqlManager.updatePlayerTempQuest("monthly1", p.getUniqueId().toString(), 0);
                mysqlManager.updatePlayerTempQuest("monthly2", p.getUniqueId().toString(), 0);
                mysqlManager.updatePlayerTempQuest("monthly3", p.getUniqueId().toString(), 0);
            }

            for (String s : questMap.keySet()) {
                if (s.equals(Quest1)) {
                    handle(1, "monthly1", p, questMap, s, foodCategory);
                } else if (s.equals(Quest2)) {
                    handle(2, "monthly2", p, questMap, s, foodCategory);
                } else if (s.equals(Quest3)) {
                    handle(3, "monthly3", p, questMap, s, foodCategory);
                }
            }
        }
    }

    public static void handle(int num, String questType, Player p, HashMap<String, Integer> questMap, String s, FoodCategory foodCategory) {
        if (!Monthly.isDone(num, p)) {
            if (mysqlManager.getPlayerTempQuest(questType, p.getUniqueId().toString()) == questMap.get(s)) {
                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);

                mysqlManager.updatePlayerQuest(questType, true, p.getUniqueId().toString());
                mysqlManager.updatePlayerTempQuest(questType, p.getUniqueId().toString(), 0);

                p.sendActionBar("§6Du hast eine Quest abgeschlossen hol dir deine Belohnung §d/quest");

                if (Monthly.RewardMoneyList.get(s) != null) {
                    foodCategory.addMoney(p, Monthly.RewardMoneyList.get(s));
                    p.sendMessage(" §7[§a+§7] §6" + Monthly.RewardMoneyList.get(s) + "€");
                }

                for (int i = 0; i!= 10; i++) {
                    if (!QuestBuilder.unclaimedQuestRewards.containsKey(p.getUniqueId().toString()+i)) {
                        QuestBuilder.unclaimedQuestRewards.put(p.getUniqueId().toString()+i, RewardItemList.get(mysqlManager.getOrginQuest(questType)));
                        break;
                    }
                }
            } else {
                mysqlManager.updatePlayerTempQuest(questType, p.getUniqueId().toString(), mysqlManager.getPlayerTempQuest(questType, p.getUniqueId().toString())+1);
            }
        }
    }
}

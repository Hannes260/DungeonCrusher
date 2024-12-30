package net.dbsgameplay.dungeoncrusher.utils.quests;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.Shop.FoodCategory;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Weekly {
    public static HashMap<String, Map<String, Object>> RewardItemList = new HashMap<>();
    public static HashMap<String, Integer> RewardMoneyList = new HashMap<>();

    public static HashMap<String, Integer> MoveQuestList = new HashMap<>();
    public static HashMap<String, Integer> PlayQuestList = new HashMap<>();
    public static HashMap<String, Integer> KillQuestList = new HashMap<>();
    public static HashMap<String, Integer> GetQuestList = new HashMap<>();
    public static HashMap<String, Integer> DamageQuestList = new HashMap<>();
    public static HashMap<String, Integer> EatQuestList = new HashMap<>();
    public static HashMap<String, Integer> DrinkQuestList = new HashMap<>();

    public static List<String> Quests = new ArrayList<>();

    public static MYSQLManager mysqlManager;
    public static DungeonCrusher dungeonCrusher;

    public Weekly(MYSQLManager mysqlManager, DungeonCrusher dungeonCrusher) {
        this.mysqlManager = mysqlManager;
        this.dungeonCrusher = dungeonCrusher;
    }

    public static void load() {
        Quests.addAll(MoveQuestList.keySet());
        Quests.addAll(GetQuestList.keySet());
        Quests.addAll(KillQuestList.keySet());
        Quests.addAll(PlayQuestList.keySet());
        Quests.addAll(DamageQuestList.keySet());
        Quests.addAll(EatQuestList.keySet());
        Quests.addAll(DrinkQuestList.keySet());
    }

    public static void checkForOrginQuest() {
        String k1 = null;
        String k2 = null;
        String k3;
        
        if (mysqlManager.getOrginQuest("weekly1") == null) {
            Bukkit.getLogger().info("Weekly1 is missing... fixing...");
            Random random = new Random();
            int rdmNum = random.nextInt(0, Quests.size());
            String key = Quests.get(rdmNum);
            k1 = Weekly.getQuestKategorie(key);
            mysqlManager.updateOrginQuest("weekly1", key);
        }
        if (mysqlManager.getOrginQuest("weekly2") == null) {
            Bukkit.getLogger().info("Weekly2 is missing... fixing...");
            Random random = new Random();
            int rdmNum = random.nextInt(0, Quests.size());
            String key = Quests.get(rdmNum);
            k2 = Weekly.getQuestKategorie(key);
            if (key.equalsIgnoreCase(mysqlManager.getOrginQuest("weekly1")) || k1.equalsIgnoreCase(k2)) {
                while (key.equalsIgnoreCase(mysqlManager.getOrginQuest("weekly1")) || k1.equalsIgnoreCase(k2)) {
                    rdmNum = random.nextInt(0, Quests.size());
                    key = Quests.get(rdmNum);
                    k2 = Weekly.getQuestKategorie(key);
                }
                mysqlManager.updateOrginQuest("weekly2", key);
            }else {
                mysqlManager.updateOrginQuest("weekly2", key);
            }
        }
        if (mysqlManager.getOrginQuest("weekly3") == null) {
            Bukkit.getLogger().info("Weekly3 is missing... fixing...");
            Random random = new Random();
            int rdmNum = random.nextInt(0, Quests.size());
            String key = Quests.get(rdmNum);
            k3 = Weekly.getQuestKategorie(key);
            if ((key.equalsIgnoreCase(mysqlManager.getOrginQuest("weekly1")) || k3.equalsIgnoreCase(k1)) || (key.equalsIgnoreCase(mysqlManager.getOrginQuest("weekly2")) || k3.equalsIgnoreCase(k2))) {
                while ((key.equalsIgnoreCase(mysqlManager.getOrginQuest("weekly1")) || k3.equalsIgnoreCase(k1)) || (key.equalsIgnoreCase(mysqlManager.getOrginQuest("weekly2")) || k3.equalsIgnoreCase(k2))) {
                    rdmNum = random.nextInt(0, Quests.size());
                    key = Quests.get(rdmNum);
                    k3 = Weekly.getQuestKategorie(key);
                }
                mysqlManager.updateOrginQuest("weekly3", key);
            }else {
                mysqlManager.updateOrginQuest("weekly3", key);
            }
        }
    }

    public static void checkForOrginQuestUpdate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String time = now.format(formatter);

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("EEEE");
        LocalDateTime now2 = LocalDateTime.now();
        String time2 = now2.format(formatter2);


        if (time2.equalsIgnoreCase("Monday")) {
            if (time.substring(0, time.length() - 3).equalsIgnoreCase("00:01") && (Integer.parseInt(time.substring(6)) >= 1 && Integer.parseInt(time.substring(6)) <= 10)) {
                String k1 = null;
                String k2 = null;
                String k3;

                if (mysqlManager.getOrginQuest("weekly1") != null) {
                    Bukkit.getLogger().info("Weekly1 is updating...");
                    Random random = new Random();
                    int rdmNum = random.nextInt(0, Quests.size());
                    String key = Quests.get(rdmNum);
                    k1 = Weekly.getQuestKategorie(key);
                    mysqlManager.updateOrginQuest("weekly1", key);
                }
                if (mysqlManager.getOrginQuest("weekly2") != null) {
                    Bukkit.getLogger().info("Weekly2 is updating...");
                    Random random = new Random();
                    int rdmNum = random.nextInt(0, Quests.size());
                    String key = Quests.get(rdmNum);
                    k2 = Weekly.getQuestKategorie(key);
                    if (key.equalsIgnoreCase(mysqlManager.getOrginQuest("weekly1")) || k1.equalsIgnoreCase(k2)) {
                        while (key.equalsIgnoreCase(mysqlManager.getOrginQuest("weekly1")) || k1.equalsIgnoreCase(k2)) {
                            rdmNum = random.nextInt(0, Quests.size());
                            key = Quests.get(rdmNum);
                            k2 = Weekly.getQuestKategorie(key);
                        }
                        mysqlManager.updateOrginQuest("weekly2", key);
                    }else {
                        mysqlManager.updateOrginQuest("weekly2", key);
                    }
                }
                if (mysqlManager.getOrginQuest("weekly3") != null) {
                    Bukkit.getLogger().info("Weekly3 is updating...");
                    Random random = new Random();
                    int rdmNum = random.nextInt(0, Quests.size());
                    String key = Quests.get(rdmNum);
                    k3 = Weekly.getQuestKategorie(key);
                    if ((key.equalsIgnoreCase(mysqlManager.getOrginQuest("weekly1")) || k3.equalsIgnoreCase(k1)) || (key.equalsIgnoreCase(mysqlManager.getOrginQuest("weekly2")) || k3.equalsIgnoreCase(k2))) {
                        while ((key.equalsIgnoreCase(mysqlManager.getOrginQuest("weekly1")) || k3.equalsIgnoreCase(k1)) || (key.equalsIgnoreCase(mysqlManager.getOrginQuest("weekly2")) || k3.equalsIgnoreCase(k2))) {
                            rdmNum = random.nextInt(0, Quests.size());
                            key = Quests.get(rdmNum);
                            k3 = Weekly.getQuestKategorie(key);
                        }
                        mysqlManager.updateOrginQuest("weekly3", key);
                    }else {
                        mysqlManager.updateOrginQuest("weekly3", key);
                    }
                }
                Bukkit.getLogger().info("Reseting Weekly PlayerTempQuests...");
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
        if (DamageQuestList.containsKey(questID)) {
            kategorie = "Damage";
            return kategorie;
        }
        if (EatQuestList.containsKey(questID)) {
            kategorie = "Eat";
            return kategorie;
        }
        if (DrinkQuestList.containsKey(questID)) {
            kategorie = "Drink";
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
        if (DamageQuestList.containsKey(questID)) {
            title = "Verursache " + DamageQuestList.get(questID) + " Schaden.";
        }
        if (EatQuestList.containsKey(questID)) {
            title = "Esse " + EatQuestList.get(questID) + " Lebensmittel.";
        }
        if (DrinkQuestList.containsKey(questID)) {
            title = "Trinke " + DrinkQuestList.get(questID) + " Tränke.";
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
        if (DamageQuestList.containsKey(questID)) {
            title = DamageQuestList.get(questID);
        }
        if (EatQuestList.containsKey(questID)) {
            title = EatQuestList.get(questID);
        }
        if (DrinkQuestList.containsKey(questID)) {
            title = DrinkQuestList.get(questID);
        }
        return title;
    }

    public static boolean isDone(int num, Player p){
        return mysqlManager.getPlayerQuest("weekly" + num, p.getUniqueId().toString());
    }

    public static void doQuest(Player p, HashMap<String, Integer> questMap, int progress) {
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
                Bukkit.getLogger().info("Weekly temps reseting... ");
                mysqlManager.updatePlayerTempQuest("weekly1", p.getUniqueId().toString(), 0);
                mysqlManager.updatePlayerTempQuest("weekly2", p.getUniqueId().toString(), 0);
                mysqlManager.updatePlayerTempQuest("weekly3", p.getUniqueId().toString(), 0);
            }

            for (String s : questMap.keySet()) {
                if (s.equals(Quest1)) {
                    handle(1, "weekly1", p, questMap, s, foodCategory, progress);
                } else if (s.equals(Quest2)) {
                    handle(2, "weekly2", p, questMap, s, foodCategory, progress);
                } else if (s.equals(Quest3)) {
                    handle(3, "weekly3", p, questMap, s, foodCategory, progress);
                }
            }
        }
    }

    public static void handle(int num, String questType, Player p, HashMap<String, Integer> questMap, String s, FoodCategory foodCategory, int progress) {
        if (!Weekly.isDone(num, p)) {
            mysqlManager.updatePlayerTempQuest(questType, p.getUniqueId().toString(), mysqlManager.getPlayerTempQuest(questType, p.getUniqueId().toString())+ progress);

            if (mysqlManager.getPlayerTempQuest(questType, p.getUniqueId().toString()) >= questMap.get(s)) {
                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);

                mysqlManager.updatePlayerQuest(questType, true, p.getUniqueId().toString());
                mysqlManager.updatePlayerTempQuest(questType, p.getUniqueId().toString(), 0);

                p.sendActionBar("§6Du hast eine Quest abgeschlossen hol dir deine Belohnung §d/quest");

                if (Weekly.RewardMoneyList.get(s) != null) {
                    foodCategory.addMoney(p, Weekly.RewardMoneyList.get(s));
                    p.sendMessage("»Quests §7[§a+§7] §6" + Weekly.RewardMoneyList.get(s) + "€");
                }

                for (int i = 0; i!= 10; i++) {
                    if (!QuestBuilder.unclaimedQuestRewards.containsKey(p.getUniqueId().toString()+i)) {
                        QuestBuilder.unclaimedQuestRewards.put(p.getUniqueId().toString()+i, RewardItemList.get(mysqlManager.getOrginQuest(questType)));
                        break;
                    }
                }
            }
        }
    }
}

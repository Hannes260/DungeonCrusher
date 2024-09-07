package net.dbsgameplay.dungeoncrusher.utils;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.Shop.FoodCategory;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;
import java.util.*;

public class QuestBuilder {

    public static MYSQLManager mysqlManager;

    public QuestBuilder(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.mysqlManager = mysqlManager;
    }
    private LocationConfigManager locationConfigManager;
    private static DungeonCrusher dungeonCrusher;

    public static Inventory questMenu = Bukkit.createInventory(null, 54, "§7Questmenü");

    public static HashMap<String, String> tutorialQuestMap = new HashMap<>();
    public static HashMap<String, String> dailyQuestMap = new HashMap<>();
    public static HashMap<String, String> dailyQuestRewardMap = new HashMap<>();

    public static BossBar bossBar = Bukkit.createBossBar(tutorialQuestMap.get("t3"), BarColor.BLUE, BarStyle.SOLID);


    public static Inventory getQuestmenü() {
        return questMenu;
    }

    public static void fillQuestmenü(Player player) {
        //Daily
        ItemStack dailyTitle = new ItemStack(Material.CLOCK);
        ItemMeta dailyTitleMeta = dailyTitle.getItemMeta();
        dailyTitleMeta.setDisplayName("§dDaily Quests");
        dailyTitle.setItemMeta(dailyTitleMeta);
        questMenu.setItem(11, dailyTitle);

        ItemStack dailyStack1 = new ItemStack(Material.NAME_TAG);
        ItemMeta dailylMeta1 = dailyStack1.getItemMeta();
        dailylMeta1.setItemName(ChatColor.GOLD + dailyQuestMap.get(mysqlManager.getOrginQuest("daily")));
        ArrayList<String> dailyList1 = new ArrayList<>();
        if (mysqlManager.getPlayerDailyQuest(1,player.getUniqueId().toString()) == null) {
            mysqlManager.updatePlayerDailyQuest(1,false, player.getUniqueId().toString());
        }
        if (mysqlManager.getPlayerDailyQuest(1,player.getUniqueId().toString())) {
            dailyList1.add("§aAbgeschlossen!");
            dailylMeta1.setEnchantmentGlintOverride(true);
        } else {
            dailylMeta1.setEnchantmentGlintOverride(false);
        }
        dailylMeta1.setLore(dailyList1);
        dailyStack1.setItemMeta(dailylMeta1);
        questMenu.setItem(11, dailyStack1);
    }

    public static void checkForOrginQuest() {
        //Daily
        if (mysqlManager.getOrginQuest("daily1") == null) {
            Random random = new Random();
            int rdmNum = random.nextInt(0, dailyQuestMap.size());
            String[] keyArray = dailyQuestMap.keySet().toArray(new String[0]);
            String key = keyArray[rdmNum];
            mysqlManager.updateOrginQuest("daily1", key);

        }
        if (mysqlManager.getOrginQuest("daily2") == null) {
            Random random = new Random();
            int rdmNum = random.nextInt(0, dailyQuestMap.size());
            String[] keyArray = dailyQuestMap.keySet().toArray(new String[0]);
            String key = keyArray[rdmNum];
            if (key == mysqlManager.getOrginQuest("daily1")) {
                while (key == mysqlManager.getOrginQuest("daily1")) {
                    rdmNum = random.nextInt(0, dailyQuestMap.size());
                    key = keyArray[rdmNum];
                }
                mysqlManager.updateOrginQuest("daily2", key);
            }
        }
        if (mysqlManager.getOrginQuest("daily3") == null) {
            Random random = new Random();
            int rdmNum = random.nextInt(0, dailyQuestMap.size());
            String[] keyArray = dailyQuestMap.keySet().toArray(new String[0]);
            String key = keyArray[rdmNum];
            if (key == mysqlManager.getOrginQuest("daily1") || key == mysqlManager.getOrginQuest("daily2")) {
                while (key == mysqlManager.getOrginQuest("daily1") || key == mysqlManager.getOrginQuest("daily2")) {
                    rdmNum = random.nextInt(0, dailyQuestMap.size());
                    key = keyArray[rdmNum];
                }
                mysqlManager.updateOrginQuest("daily3", key);
            }
        }
    }

    public static void checkForOrginQuestUpdate() {
        //Daily
        Date nowdaily = new Date(System.currentTimeMillis());
        SimpleDateFormat formatdaily = new SimpleDateFormat("HH:mm:ss");

        if (formatdaily.format(nowdaily).equalsIgnoreCase("00:00:01")) {
            if (mysqlManager.getOrginQuest("daily1") == null) {
                Random random = new Random();
                int rdmNum = random.nextInt(0, dailyQuestMap.size());
                String[] keyArray = dailyQuestMap.keySet().toArray(new String[0]);
                String key = keyArray[rdmNum];
                mysqlManager.updateOrginQuest("daily1", key);

            }
            if (mysqlManager.getOrginQuest("daily2") == null) {
                Random random = new Random();
                int rdmNum = random.nextInt(0, dailyQuestMap.size());
                String[] keyArray = dailyQuestMap.keySet().toArray(new String[0]);
                String key = keyArray[rdmNum];
                if (key == mysqlManager.getOrginQuest("daily1")) {
                    while (key == mysqlManager.getOrginQuest("daily1")) {
                        rdmNum = random.nextInt(0, dailyQuestMap.size());
                        key = keyArray[rdmNum];
                    }
                    mysqlManager.updateOrginQuest("daily2", key);
                }
            }
            if (mysqlManager.getOrginQuest("daily3") == null) {
                Random random = new Random();
                int rdmNum = random.nextInt(0, dailyQuestMap.size());
                String[] keyArray = dailyQuestMap.keySet().toArray(new String[0]);
                String key = keyArray[rdmNum];
                if (key == mysqlManager.getOrginQuest("daily1") || key == mysqlManager.getOrginQuest("daily2")) {
                    while (key == mysqlManager.getOrginQuest("daily1") || key == mysqlManager.getOrginQuest("daily2")) {
                        rdmNum = random.nextInt(0, dailyQuestMap.size());
                        key = keyArray[rdmNum];
                    }
                    mysqlManager.updateOrginQuest("daily3", key);
                }
            }
            for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()) {
                mysqlManager.updatePlayerDailyQuest( 1,false, p.getUniqueId().toString());
                mysqlManager.updatePlayerDailyQuest( 2,false, p.getUniqueId().toString());
                mysqlManager.updatePlayerDailyQuest( 3,false, p.getUniqueId().toString());
                dungeonCrusher.getConfig().set("quest." + p.getUniqueId().toString() + "." + "daily1", 0);
                dungeonCrusher.getConfig().set("quest." + p.getUniqueId().toString() + "." + "daily2", 0);
                dungeonCrusher.getConfig().set("quest." + p.getUniqueId().toString() + "." + "daily3", 0);
                dungeonCrusher.saveConfig();
            }
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                mysqlManager.updatePlayerDailyQuest( 1,false, p.getUniqueId().toString());
                mysqlManager.updatePlayerDailyQuest( 2,false, p.getUniqueId().toString());
                mysqlManager.updatePlayerDailyQuest( 3,false, p.getUniqueId().toString());
                dungeonCrusher.getConfig().set("quest." + p.getUniqueId().toString() + "." + "daily1", 0);
                dungeonCrusher.getConfig().set("quest." + p.getUniqueId().toString() + "." + "daily2", 0);
                dungeonCrusher.getConfig().set("quest." + p.getUniqueId().toString() + "." + "daily3", 0);
                dungeonCrusher.saveConfig();
            }

        }
    }

    public static void checkIfDailyIsDone(String questType, String quest, Player p, int aim) {
        if (mysqlManager.getOrginQuest(questType) != null && mysqlManager.getOrginQuest(questType).equalsIgnoreCase(quest) && mysqlManager.getTutorialQuest(p.getUniqueId().toString()).equalsIgnoreCase("t0") && mysqlManager.getPlayerDailyQuest(1,p.getUniqueId().toString()) == false) {
            FileConfiguration cfg = DungeonCrusher.getInstance().getConfig();
            if (cfg.contains("quest." + p.getUniqueId().toString() + "." + questType)) {
                cfg.set("quest." + p.getUniqueId().toString() + "." + questType, cfg.getInt("quest." + p.getUniqueId().toString() + "." + questType)+1);
                DungeonCrusher.getInstance().saveConfig();
            }else {
                cfg.set("quest." + p.getUniqueId().toString() + "." + questType, 1);
                DungeonCrusher.getInstance().saveConfig();
            }

            if (cfg.getInt("quest." + p.getUniqueId().toString() + "." + questType) == aim) {
                cfg.set("quest." + p.getUniqueId().toString() + "." + questType, 0);
                DungeonCrusher.getInstance().saveConfig();
                mysqlManager.updatePlayerDailyQuest(1, true, p.getUniqueId().toString());

                Random rdm = new Random();
                FoodCategory foodCategory = new FoodCategory(mysqlManager);
                foodCategory.addMoney(p, rdm.nextInt(70,100));
                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
                p.sendActionBar("§6Du hast die daily abgeschlossen!");
            }
        }
    }
}

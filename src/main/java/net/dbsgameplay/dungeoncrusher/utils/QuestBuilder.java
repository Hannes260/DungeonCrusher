package net.dbsgameplay.dungeoncrusher.utils;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.Shop.FoodCategory;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
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
    final public static BossBar bossBar = Bukkit.createBossBar(tutorialQuestMap.get("t3"), BarColor.BLUE, BarStyle.SOLID);


    public static Inventory getQuestmenü() {
        return questMenu;
    }

    public static void fillQuestmenü(Player player) {
        //Daily
        ItemStack dailyStack = new ItemStack(Material.CLOCK);
        ItemMeta dailylMeta = dailyStack.getItemMeta();
        dailylMeta.setItemName("§6Daily");
        ArrayList<String> dailyList = new ArrayList<>();
        dailyList.add(dailyQuestMap.get(mysqlManager.getOrginQuest("daily")));


        if (mysqlManager.getPlayerWeeklyQuest(player.getUniqueId().toString()) == null) {
            mysqlManager.updatePlayerWeeklyQuest(false, player.getUniqueId().toString());
        }

        if (mysqlManager.getPlayerWeeklyQuest(player.getUniqueId().toString())) {
            dailyList.add("§aAbgeschlossen!");
            dailylMeta.setEnchantmentGlintOverride(true);
        } else {
            dailylMeta.setEnchantmentGlintOverride(false);
        }
        dailylMeta.setLore(dailyList);
        dailyStack.setItemMeta(dailylMeta);
        questMenu.setItem(11, dailyStack);
    }

    public static void checkForOrginQuest() {
        if (mysqlManager.getOrginQuest("daily") == null) {
            Random random = new Random();
            int rdmNum = random.nextInt(0, dailyQuestMap.size());
            String[] keyArray = dailyQuestMap.keySet().toArray(new String[0]);
            String key = keyArray[rdmNum];
            mysqlManager.updateOrginQuest("daily", key);

        }
    }

    public static void checkForOrginQuestUpdate() {
        Date nowdaily = new Date(System.currentTimeMillis());
        SimpleDateFormat formatdaily = new SimpleDateFormat("HH:mm:ss");

        if (formatdaily.format(nowdaily).equalsIgnoreCase("00:00:01")) {
            Random random = new Random();
            int rdmNum = random.nextInt(0, QuestBuilder.dailyQuestMap.size());
            String[] keyArray = QuestBuilder.dailyQuestMap.keySet().toArray(new String[0]);
            String key = keyArray[rdmNum]; // Access the element at index 9
            mysqlManager.updateOrginQuest("daily", key);
            for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()) {
                mysqlManager.updatePlayerWeeklyQuest( false, p.getUniqueId().toString());
            }
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                mysqlManager.updatePlayerWeeklyQuest( false, p.getUniqueId().toString());
            }
            dungeonCrusher.getConfig().set("quest.", null);
        }
    }

    public static void checkIfWeeklyIsDone(String questType, String quest, Player p, int aim) {
        if (mysqlManager.getOrginQuest(questType) != null && mysqlManager.getOrginQuest(questType).equalsIgnoreCase(quest) && mysqlManager.getTutorialQuest(p.getUniqueId().toString()).equalsIgnoreCase("t0") && mysqlManager.getPlayerWeeklyQuest(p.getUniqueId().toString()) == false) {
            FileConfiguration cfg = DungeonCrusher.getInstance().getConfig();
            if (cfg.contains("quest." + p.getUniqueId().toString() + "." + questType)) {
                cfg.set("quest." + p.getUniqueId().toString() + "." + questType, cfg.getInt("quest." + p.getUniqueId().toString() + "." + questType)+1);
                DungeonCrusher.getInstance().saveConfig();
            }else {
                cfg.set("quest." + p.getUniqueId().toString() + "." + questType, 1);
                DungeonCrusher.getInstance().saveConfig();
            }

            if (cfg.getInt("quest." + p.getUniqueId().toString() + "." + questType) == aim) {
                cfg.set("quest." + p.getUniqueId().toString() + "." + questType, null);
                DungeonCrusher.getInstance().saveConfig();
                mysqlManager.updatePlayerWeeklyQuest( true, p.getUniqueId().toString());

                Random rdm = new Random();
                FoodCategory foodCategory = new FoodCategory(mysqlManager);
                foodCategory.addMoney(p, rdm.nextInt(70,100));
                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
            }
        }
    }
}

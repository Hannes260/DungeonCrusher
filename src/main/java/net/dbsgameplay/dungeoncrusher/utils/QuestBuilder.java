package net.dbsgameplay.dungeoncrusher.utils;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
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
    public static HashMap<String, String> dailyQuestBMap = new HashMap<>();
    final public static BossBar bossBar = Bukkit.createBossBar(tutorialQuestMap.get("t3"), BarColor.BLUE, BarStyle.SOLID);
    public static String[] dailyQuests = (String[]) tutorialQuestMap.values().toArray();

    public static Inventory getQuestmenü() {
        return questMenu;
    }

    public static void fillQuestmenü(Player player) {
        Date nowdaily = new Date(System.currentTimeMillis());
        SimpleDateFormat formatdaily = new SimpleDateFormat("HH:mm:ss");

        if (formatdaily.format(nowdaily).equalsIgnoreCase("00:00:01")) {
            Random random = new Random();
            int rdmNum = random.nextInt(0, dailyQuests.length + 1);
            String questKey = dailyQuests[rdmNum];
            mysqlManager.updateOrginQuest("daily", questKey);
            mysqlManager.updatePlayerQuest("daily", false, player.getUniqueId().toString());
            dungeonCrusher.getConfig().set("quest." + player.getUniqueId().toString() + "." + "daily", null);
        }

        //Daily
        ItemStack dailyStack = new ItemStack(Material.CLOCK);
        ItemMeta dailylMeta = dailyStack.getItemMeta();
        dailylMeta.setItemName("§6Daily");
        ArrayList<String> dailyList = new ArrayList<>();
        dailyList.add(dailyQuestMap.get(mysqlManager.getOrginQuest("daily")));
        dailylMeta.setLore(dailyList);
        if (mysqlManager.getPlayerQuest("daily", player.getUniqueId().toString())) {
            dailylMeta.setEnchantmentGlintOverride(true);
        } else {
            dailylMeta.setEnchantmentGlintOverride(false);
        }
        dailyStack.setItemMeta(dailylMeta);
        questMenu.setItem(11, dailyStack);
    }
}

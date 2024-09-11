package net.dbsgameplay.dungeoncrusher.utils;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.QuestConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.quests.Daily;
import org.bukkit.*;
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
    public static Inventory rewardMenu = Bukkit.createInventory(null, 27, "§7Rewardmenü - §6Pick 2!");

    public static HashMap<String, String> tutorialQuestMap = new HashMap<>();
    public static List<String> dailyQuestList = new ArrayList<>();

    public static BossBar bossBar = Bukkit.createBossBar(tutorialQuestMap.get("t3"), BarColor.BLUE, BarStyle.SOLID);


    public static Inventory getQuestmenü() {
        return questMenu;
    }

    public static void fillQuestmenü(Player player) {
        //Daily1
        ItemStack dailyTitle = new ItemStack(Material.CLOCK);
        ItemMeta dailyTitleMeta = dailyTitle.getItemMeta();
        dailyTitleMeta.setDisplayName("§dDaily Quests");
        dailyTitle.setItemMeta(dailyTitleMeta);
        questMenu.setItem(11, dailyTitle);

        ItemStack dailyStack1 = new ItemStack(Material.NAME_TAG);
        ItemMeta dailylMeta1 = dailyStack1.getItemMeta();
        dailylMeta1.setItemName(ChatColor.GOLD + Daily.getQuestTitle(mysqlManager.getOrginQuest("daily1")));
        ArrayList<String> dailyList1 = new ArrayList<>();
        if (mysqlManager.getPlayerQuest("daily1",player.getUniqueId().toString()) == null) {
            mysqlManager.updatePlayerQuest("daily1",false, player.getUniqueId().toString());
        }
        if (mysqlManager.getPlayerQuest("daily1",player.getUniqueId().toString())) {
            dailyList1.add("§aAbgeschlossen!");
            dailylMeta1.setEnchantmentGlintOverride(true);
        } else {
            dailylMeta1.setEnchantmentGlintOverride(false);
        }
        dailylMeta1.setLore(dailyList1);
        dailyStack1.setItemMeta(dailylMeta1);
        questMenu.setItem(20, dailyStack1);

        //Daily2
        ItemStack dailyStack2 = new ItemStack(Material.NAME_TAG);
        ItemMeta dailylMeta2 = dailyStack2.getItemMeta();
        dailylMeta2.setItemName(ChatColor.GOLD + Daily.getQuestTitle(mysqlManager.getOrginQuest("daily2")));
        ArrayList<String> dailyList2 = new ArrayList<>();
        if (mysqlManager.getPlayerQuest("daily2",player.getUniqueId().toString()) == null) {
            mysqlManager.updatePlayerQuest("daily2",false, player.getUniqueId().toString());
        }
        if (mysqlManager.getPlayerQuest("daily2",player.getUniqueId().toString())) {
            dailyList2.add("§aAbgeschlossen!");
            dailylMeta2.setEnchantmentGlintOverride(true);
        } else {
            dailylMeta2.setEnchantmentGlintOverride(false);
        }
        dailylMeta2.setLore(dailyList2);
        dailyStack2.setItemMeta(dailylMeta2);
        questMenu.setItem(29, dailyStack2);

        //Daily3
        ItemStack dailyStack3 = new ItemStack(Material.NAME_TAG);
        ItemMeta dailylMeta3 = dailyStack3.getItemMeta();
        dailylMeta3.setItemName(ChatColor.GOLD + Daily.getQuestTitle(mysqlManager.getOrginQuest("daily3")));
        ArrayList<String> dailyList3 = new ArrayList<>();
        if (mysqlManager.getPlayerQuest("daily3",player.getUniqueId().toString()) == null) {
            mysqlManager.updatePlayerQuest("daily3",false, player.getUniqueId().toString());
        }
        if (mysqlManager.getPlayerQuest("daily3",player.getUniqueId().toString())) {
            dailyList3.add("§aAbgeschlossen!");
            dailylMeta3.setEnchantmentGlintOverride(true);
        } else {
            dailylMeta3.setEnchantmentGlintOverride(false);
        }
        dailylMeta3.setLore(dailyList3);
        dailyStack3.setItemMeta(dailylMeta3);
        questMenu.setItem(38, dailyStack3);
    }

    public static void openRewardmenü(Player p, String questID, HashMap<String, Map<String, Object>> rewardList) {
        ItemStack rawCopper = new ItemStack(Material.RAW_COPPER);
        ItemMeta rawCopperMeta = rawCopper.getItemMeta();
        rawCopperMeta.setDisplayName("§dAnzahl: " + rewardList.get(questID).get("rohkupfer"));
        rawCopper.setItemMeta(rawCopperMeta);
        rewardMenu.setItem(0, rawCopper);

        ItemStack copper = new ItemStack(Material.COPPER_INGOT);
        ItemMeta copperMeta = copper.getItemMeta();
        copperMeta.setDisplayName("§dAnzahl: " + rewardList.get(questID).get("kupfer"));
        copper.setItemMeta(copperMeta);
        rewardMenu.setItem(1, copper);

        ItemStack rawGold = new ItemStack(Material.RAW_GOLD);
        ItemMeta rawGoldMeta = rawGold.getItemMeta();
        rawGoldMeta.setDisplayName("§dAnzahl: " + rewardList.get(questID).get("rohgold"));
        rawGold.setItemMeta(rawGoldMeta);
        rewardMenu.setItem(9, rawGold);

        ItemStack gold = new ItemStack(Material.GOLD_INGOT);
        ItemMeta goldMeta = gold.getItemMeta();
        goldMeta.setDisplayName("§dAnzahl: " + rewardList.get(questID).get("gold"));
        gold.setItemMeta(goldMeta);
        rewardMenu.setItem(10, gold);

        ItemStack rawIron = new ItemStack(Material.RAW_IRON);
        ItemMeta rawIronMeta = rawIron.getItemMeta();
        rawIronMeta.setDisplayName("§dAnzahl: " + rewardList.get(questID).get("roheisen"));
        rawIron.setItemMeta(rawIronMeta);
        rewardMenu.setItem(18, rawIron);

        ItemStack iron = new ItemStack(Material.IRON_INGOT);
        ItemMeta ironMeta = iron.getItemMeta();
        ironMeta.setDisplayName("§dAnzahl: " + rewardList.get(questID).get("eisen"));
        iron.setItemMeta(ironMeta);
        rewardMenu.setItem(19, iron);

        ItemStack coal = new ItemStack(Material.COAL);
        ItemMeta coalMeta = coal.getItemMeta();
        coalMeta.setDisplayName("§dAnzahl: " + rewardList.get(questID).get("kohle"));
        coal.setItemMeta(coalMeta);
        rewardMenu.setItem(13, coal);

        ItemStack stone = new ItemStack(Material.STONE);
        ItemMeta stoneMeta = stone.getItemMeta();
        stoneMeta.setDisplayName("§dAnzahl: " + rewardList.get(questID).get("stein"));
        stone.setItemMeta(stoneMeta);
        rewardMenu.setItem(7, stone);

        ItemStack cobbleStone = new ItemStack(Material.COBBLESTONE);
        ItemMeta cobbleStoneMeta = cobbleStone.getItemMeta();
        cobbleStoneMeta.setDisplayName("§dAnzahl: " + rewardList.get(questID).get("bruchstein"));
        cobbleStone.setItemMeta(coalMeta);
        rewardMenu.setItem(8, cobbleStone);

        ItemStack diamand = new ItemStack(Material.DIAMOND);
        ItemMeta diamandMeta = diamand.getItemMeta();
        diamandMeta.setDisplayName("§dAnzahl: " + rewardList.get(questID).get("diamant"));
        diamand.setItemMeta(diamandMeta);
        rewardMenu.setItem(16, diamand);

        ItemStack dimondOre = new ItemStack(Material.DIAMOND_ORE);
        ItemMeta dimondOreMeta = dimondOre.getItemMeta();
        dimondOreMeta.setDisplayName("§dAnzahl: " + rewardList.get(questID).get("diamanterz"));
        dimondOre.setItemMeta(dimondOreMeta);
        rewardMenu.setItem(17, dimondOre);

        ItemStack netherite = new ItemStack(Material.NETHERITE_INGOT);
        ItemMeta netheriteMeta = netherite.getItemMeta();
        netheriteMeta.setDisplayName("§dAnzahl: " + rewardList.get(questID).get("netherite"));
        netherite.setItemMeta(netheriteMeta);
        rewardMenu.setItem(25, netherite);

        ItemStack netheriteScrap = new ItemStack(Material.NETHERITE_SCRAP);
        ItemMeta netheriteScrapMeta = netheriteScrap.getItemMeta();
        netheriteScrapMeta.setDisplayName("§dAnzahl: " + rewardList.get(questID).get("antikerschrott"));
        netheriteScrap.setItemMeta(netheriteScrapMeta);
        rewardMenu.setItem(26, netheriteScrap);

        p.openInventory(rewardMenu);
    }

    public static boolean isTutorialDone(Player p) {
        if (mysqlManager.getTutorialQuest(p.getUniqueId().toString()).equals("t0")) {
            return true;
        }
        return false;
    }
}

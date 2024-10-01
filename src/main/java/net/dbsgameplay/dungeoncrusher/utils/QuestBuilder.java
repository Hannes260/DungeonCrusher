package net.dbsgameplay.dungeoncrusher.utils;

import it.unimi.dsi.fastutil.Hash;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.quests.Daily;
import net.dbsgameplay.dungeoncrusher.utils.quests.Monthly;
import net.dbsgameplay.dungeoncrusher.utils.quests.Weekly;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
    public static Inventory rewardMenu = Bukkit.createInventory(null, 27, "§7Rewardmenü - §6Such dir 2 aus!");
    public static HashMap<String, Map<String, Object>> unclaimedQuestRewards = new HashMap<>();


    public static HashMap<String, String> tutorialQuestMap = new HashMap<>();
    public static List<String> dailyQuestList = new ArrayList<>();

    public static BossBar questBar_t4 = Bukkit.createBossBar(tutorialQuestMap.get("t4"), BarColor.BLUE, BarStyle.SOLID);
    public static BossBar questBar_t3 = Bukkit.createBossBar(tutorialQuestMap.get("t3"), BarColor.BLUE, BarStyle.SOLID);
    public static BossBar questBar_t2 = Bukkit.createBossBar(tutorialQuestMap.get("t2"), BarColor.BLUE, BarStyle.SOLID);
    public static BossBar questBar_t1 = Bukkit.createBossBar(tutorialQuestMap.get("t1"), BarColor.BLUE, BarStyle.SOLID);

    public static Inventory getQuestmenü() {
        return questMenu;
    }

    public static void fillQuestmenü(Player player) {
        //Daily1
        ItemStack dailyTitle = new ItemStack(Material.CLOCK);
        ItemMeta dailyTitleMeta = dailyTitle.getItemMeta();
        dailyTitleMeta.setDisplayName("§dDaily Quests");
        dailyTitleMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_STORED_ENCHANTS);
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

            dailyList1.add(loadProgressBar(player, "daily1"));
            dailylMeta1.setEnchantmentGlintOverride(false);
        }
        dailylMeta1.setLore(dailyList1);
        dailylMeta1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_STORED_ENCHANTS);
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
            dailyList2.add(loadProgressBar(player, "daily2"));
            dailylMeta2.setEnchantmentGlintOverride(false);
        }
        dailylMeta2.setLore(dailyList2);
        dailylMeta2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_STORED_ENCHANTS);
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
            dailyList3.add(loadProgressBar(player, "daily3"));
            dailylMeta3.setEnchantmentGlintOverride(false);
        }
        dailylMeta3.setLore(dailyList3);
        dailylMeta3.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_STORED_ENCHANTS);
        dailyStack3.setItemMeta(dailylMeta3);
        questMenu.setItem(38, dailyStack3);

        //Weekly1
        ItemStack weeklyTitle = new ItemStack(Material.CLOCK);
        ItemMeta weeklyTitleMeta = weeklyTitle.getItemMeta();
        weeklyTitleMeta.setDisplayName("§dWeekly Quests");
        weeklyTitle.setItemMeta(weeklyTitleMeta);
        questMenu.setItem(13, weeklyTitle);

        ItemStack weeklyStack1 = new ItemStack(Material.NAME_TAG);
        ItemMeta weeklylMeta1 = weeklyStack1.getItemMeta();
        weeklylMeta1.setItemName(ChatColor.GOLD + Weekly.getQuestTitle(mysqlManager.getOrginQuest("weekly1")));
        ArrayList<String> weeklyList1 = new ArrayList<>();
        if (mysqlManager.getPlayerQuest("weekly1",player.getUniqueId().toString()) == null) {
            mysqlManager.updatePlayerQuest("weekly1",false, player.getUniqueId().toString());
        }
        if (mysqlManager.getPlayerQuest("weekly1",player.getUniqueId().toString())) {
            weeklyList1.add("§aAbgeschlossen!");
            weeklylMeta1.setEnchantmentGlintOverride(true);
        } else {
            weeklyList1.add(loadProgressBar(player, "weekly1"));
            weeklylMeta1.setEnchantmentGlintOverride(false);
        }
        weeklylMeta1.setLore(weeklyList1);
        weeklylMeta1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_STORED_ENCHANTS);
        weeklyStack1.setItemMeta(weeklylMeta1);
        questMenu.setItem(22, weeklyStack1);

        //Weekly2
        ItemStack weeklyStack2 = new ItemStack(Material.NAME_TAG);
        ItemMeta weeklylMeta2 = weeklyStack2.getItemMeta();
        weeklylMeta2.setItemName(ChatColor.GOLD + Weekly.getQuestTitle(mysqlManager.getOrginQuest("weekly2")));
        ArrayList<String> weeklyList2 = new ArrayList<>();
        if (mysqlManager.getPlayerQuest("weekly2",player.getUniqueId().toString()) == null) {
            mysqlManager.updatePlayerQuest("weekly2",false, player.getUniqueId().toString());
        }
        if (mysqlManager.getPlayerQuest("weekly2",player.getUniqueId().toString())) {
            weeklyList2.add("§aAbgeschlossen!");
            weeklylMeta2.setEnchantmentGlintOverride(true);
        } else {
            weeklyList2.add(loadProgressBar(player, "weekly2"));
            weeklylMeta2.setEnchantmentGlintOverride(false);
        }
        weeklylMeta2.setLore(weeklyList2);
        weeklylMeta2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_STORED_ENCHANTS);
        weeklyStack2.setItemMeta(weeklylMeta2);
        questMenu.setItem(31, weeklyStack2);

        //Weekly3
        ItemStack weeklyStack3 = new ItemStack(Material.NAME_TAG);
        ItemMeta weeklylMeta3 = weeklyStack3.getItemMeta();
        weeklylMeta3.setItemName(ChatColor.GOLD + Weekly.getQuestTitle(mysqlManager.getOrginQuest("weekly3")));
        ArrayList<String> weeklyList3 = new ArrayList<>();
        if (mysqlManager.getPlayerQuest("weekly3",player.getUniqueId().toString()) == null) {
            mysqlManager.updatePlayerQuest("weekly3",false, player.getUniqueId().toString());
        }
        if (mysqlManager.getPlayerQuest("weekly3",player.getUniqueId().toString())) {
            weeklyList3.add("§aAbgeschlossen!");
            weeklylMeta3.setEnchantmentGlintOverride(true);
        } else {
            weeklyList3.add(loadProgressBar(player, "weekly3"));
            weeklylMeta3.setEnchantmentGlintOverride(false);
        }
        weeklylMeta3.setLore(weeklyList3);
        weeklylMeta3.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_STORED_ENCHANTS);
        weeklyStack3.setItemMeta(weeklylMeta3);
        questMenu.setItem(40, weeklyStack3);

        //Monthly1
        ItemStack monthlyTitle = new ItemStack(Material.CLOCK);
        ItemMeta monthlyTitleMeta = monthlyTitle.getItemMeta();
        monthlyTitleMeta.setDisplayName("§dMonthly Quests");
        monthlyTitle.setItemMeta(monthlyTitleMeta);
        questMenu.setItem(15, monthlyTitle);

        ItemStack monthlyStack1 = new ItemStack(Material.NAME_TAG);
        ItemMeta monthlylMeta1 = monthlyStack1.getItemMeta();
        monthlylMeta1.setItemName(ChatColor.GOLD + Monthly.getQuestTitle(mysqlManager.getOrginQuest("monthly1")));
        ArrayList<String> monthlyList1 = new ArrayList<>();
        if (mysqlManager.getPlayerQuest("monthly1",player.getUniqueId().toString()) == null) {
            mysqlManager.updatePlayerQuest("monthly1",false, player.getUniqueId().toString());
        }
        if (mysqlManager.getPlayerQuest("monthly1",player.getUniqueId().toString())) {
            monthlyList1.add("§aAbgeschlossen!");
            monthlylMeta1.setEnchantmentGlintOverride(true);
        } else {
            monthlyList1.add(loadProgressBar(player, "monthly1"));
            monthlylMeta1.setEnchantmentGlintOverride(false);
        }
        monthlylMeta1.setLore(monthlyList1);
        monthlylMeta1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_STORED_ENCHANTS);
        monthlyStack1.setItemMeta(monthlylMeta1);
        questMenu.setItem(24, monthlyStack1);

        //monthly2
        ItemStack monthlyStack2 = new ItemStack(Material.NAME_TAG);
        ItemMeta monthlylMeta2 = monthlyStack2.getItemMeta();
        monthlylMeta2.setItemName(ChatColor.GOLD + Monthly.getQuestTitle(mysqlManager.getOrginQuest("monthly2")));
        ArrayList<String> monthlyList2 = new ArrayList<>();
        if (mysqlManager.getPlayerQuest("monthly2",player.getUniqueId().toString()) == null) {
            mysqlManager.updatePlayerQuest("monthly2",false, player.getUniqueId().toString());
        }
        if (mysqlManager.getPlayerQuest("monthly2",player.getUniqueId().toString())) {
            monthlyList2.add("§aAbgeschlossen!");
            monthlylMeta2.setEnchantmentGlintOverride(true);
        } else {
            monthlyList2.add(loadProgressBar(player, "monthly2"));
            monthlylMeta2.setEnchantmentGlintOverride(false);
        }
        monthlylMeta2.setLore(monthlyList2);
        monthlylMeta2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_STORED_ENCHANTS);
        monthlyStack2.setItemMeta(monthlylMeta2);
        questMenu.setItem(33, monthlyStack2);

        //Monthly3
        ItemStack monthlyStack3 = new ItemStack(Material.NAME_TAG);
        ItemMeta monthlylMeta3 = monthlyStack3.getItemMeta();
        monthlylMeta3.setItemName(ChatColor.GOLD + Monthly.getQuestTitle(mysqlManager.getOrginQuest("monthly3")));
        ArrayList<String> monthlyList3 = new ArrayList<>();
        if (mysqlManager.getPlayerQuest("monthly3",player.getUniqueId().toString()) == null) {
            mysqlManager.updatePlayerQuest("monthly3",false, player.getUniqueId().toString());
        }
        if (mysqlManager.getPlayerQuest("monthly3",player.getUniqueId().toString())) {
            monthlyList3.add("§aAbgeschlossen!");
            monthlylMeta3.setEnchantmentGlintOverride(true);
        } else {
            monthlyList3.add(loadProgressBar(player, "monthly3"));
            monthlylMeta3.setEnchantmentGlintOverride(false);
        }
        monthlylMeta3.setLore(monthlyList3);
        monthlylMeta3.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_STORED_ENCHANTS);
        monthlyStack3.setItemMeta(monthlylMeta3);
        questMenu.setItem(42, monthlyStack3);
    }

    public static void openRewardmenü(Player p, Map<String, Object> rewardList) {
        ItemStack rawCopper = new ItemStack(Material.RAW_COPPER);
        ItemMeta rawCopperMeta = rawCopper.getItemMeta();
        if (rewardList.get("rohkupfer") == null) {
            rawCopperMeta.setDisplayName("§dAnzahl: 0");
        }else {
            rawCopperMeta.setDisplayName("§dAnzahl: " + rewardList.get("rohkupfer"));
        }
        rawCopper.setItemMeta(rawCopperMeta);
        rewardMenu.setItem(0, rawCopper);

        ItemStack copper = new ItemStack(Material.COPPER_INGOT);
        ItemMeta copperMeta = copper.getItemMeta();
        if (rewardList.get("kupferbarren") == null) {
            copperMeta.setDisplayName("§dAnzahl: 0");
        }else {
            copperMeta.setDisplayName("§dAnzahl: " + rewardList.get("kupferbarren"));
        }
        copper.setItemMeta(copperMeta);
        rewardMenu.setItem(1, copper);

        ItemStack rawGold = new ItemStack(Material.RAW_GOLD);
        ItemMeta rawGoldMeta = rawGold.getItemMeta();
        if (rewardList.get("rohgold") == null) {
            rawGoldMeta.setDisplayName("§dAnzahl: 0");
        }else {
            rawGoldMeta.setDisplayName("§dAnzahl: " + rewardList.get("rohgold"));
        }
        rawGold.setItemMeta(rawGoldMeta);
        rewardMenu.setItem(9, rawGold);

        ItemStack gold = new ItemStack(Material.GOLD_INGOT);
        ItemMeta goldMeta = gold.getItemMeta();
        if (rewardList.get("goldbarren") == null) {
            goldMeta.setDisplayName("§dAnzahl: 0");
        }else {
            goldMeta.setDisplayName("§dAnzahl: " + rewardList.get("goldbarren"));
        }
        gold.setItemMeta(goldMeta);
        rewardMenu.setItem(10, gold);

        ItemStack rawIron = new ItemStack(Material.RAW_IRON);
        ItemMeta rawIronMeta = rawIron.getItemMeta();
        if (rewardList.get("roheisen") == null) {
            rawIronMeta.setDisplayName("§dAnzahl: 0");
        }else {
            rawIronMeta.setDisplayName("§dAnzahl: " + rewardList.get("roheisen"));
        }
        rawIron.setItemMeta(rawIronMeta);
        rewardMenu.setItem(18, rawIron);

        ItemStack iron = new ItemStack(Material.IRON_INGOT);
        ItemMeta ironMeta = iron.getItemMeta();
        if (rewardList.get("eisenbarren") == null) {
            ironMeta.setDisplayName("§dAnzahl: 0");
        }else {
            ironMeta.setDisplayName("§dAnzahl: " + rewardList.get("eisenbarren"));
        }
        iron.setItemMeta(ironMeta);
        rewardMenu.setItem(19, iron);

        ItemStack coal = new ItemStack(Material.COAL);
        ItemMeta coalMeta = coal.getItemMeta();
        if (rewardList.get("kohle") == null) {
            coalMeta.setDisplayName("§dAnzahl: 0");
        }else {
            coalMeta.setDisplayName("§dAnzahl: " + rewardList.get("kohle"));
        }
        coal.setItemMeta(coalMeta);
        rewardMenu.setItem(13, coal);

        ItemStack stone = new ItemStack(Material.STONE);
        ItemMeta stoneMeta = stone.getItemMeta();
        if (rewardList.get("stein") == null) {
            stoneMeta.setDisplayName("§dAnzahl: 0");
        }else {
            stoneMeta.setDisplayName("§dAnzahl: " + rewardList.get("stein"));
        }
        stone.setItemMeta(stoneMeta);
        rewardMenu.setItem(7, stone);

        ItemStack cobbleStone = new ItemStack(Material.COBBLESTONE);
        ItemMeta cobbleStoneMeta = cobbleStone.getItemMeta();
        if (rewardList.get("bruchstein") == null) {
            cobbleStoneMeta.setDisplayName("§dAnzahl: 0");
        }else {
            cobbleStoneMeta.setDisplayName("§dAnzahl: " + rewardList.get("bruchstein"));
        }
        cobbleStone.setItemMeta(coalMeta);
        rewardMenu.setItem(8, cobbleStone);

        ItemStack diamand = new ItemStack(Material.DIAMOND);
        ItemMeta diamandMeta = diamand.getItemMeta();
        if (rewardList.get("diamant") == null) {
            diamandMeta.setDisplayName("§dAnzahl: 0");
        }else {
            diamandMeta.setDisplayName("§dAnzahl: " + rewardList.get("diamant"));
        }
        diamand.setItemMeta(diamandMeta);
        rewardMenu.setItem(16, diamand);

        ItemStack dimondOre = new ItemStack(Material.DIAMOND_ORE);
        ItemMeta dimondOreMeta = dimondOre.getItemMeta();
        if (rewardList.get("diamanterz") == null) {
            dimondOreMeta.setDisplayName("§dAnzahl: 0");
        }else {
            dimondOreMeta.setDisplayName("§dAnzahl: " + rewardList.get("diamanterz"));
        }
        dimondOre.setItemMeta(dimondOreMeta);
        rewardMenu.setItem(17, dimondOre);

        ItemStack netherite = new ItemStack(Material.NETHERITE_INGOT);
        ItemMeta netheriteMeta = netherite.getItemMeta();
        if (rewardList.get("netherite") == null) {
            netheriteMeta.setDisplayName("§dAnzahl: 0");
        }else {
            netheriteMeta.setDisplayName("§dAnzahl: " + rewardList.get("netheritebarren"));
        }
        netherite.setItemMeta(netheriteMeta);
        rewardMenu.setItem(25, netherite);

        ItemStack netheriteScrap = new ItemStack(Material.NETHERITE_SCRAP);
        ItemMeta netheriteScrapMeta = netheriteScrap.getItemMeta();
        if (rewardList.get("antikerschrott") == null) {
            netheriteScrapMeta.setDisplayName("§dAnzahl: 0");
        }else {
            netheriteScrapMeta.setDisplayName("§dAnzahl: " + rewardList.get("netheriteplatten"));
        }
        netheriteScrap.setItemMeta(netheriteScrapMeta);
        rewardMenu.setItem(26, netheriteScrap);

        p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
        p.openInventory(rewardMenu);
    }

    public static boolean isTutorialDone(Player p) {
        if (mysqlManager.getTutorialQuest(p.getUniqueId().toString()).equals("t0")) {
            return true;
        }
        return false;
    }

    public static String loadProgressBar(Player p, String questType) {
        int percentage = 0;

        if (questType.startsWith("d")) {
            percentage = mysqlManager.getPlayerTempQuest(questType, p.getUniqueId().toString()) * 100 / (Daily.getQuestAim(mysqlManager.getOrginQuest(questType)));
            if (Daily.getQuestKategorie(mysqlManager.getOrginQuest(questType)).equalsIgnoreCase("Play")) {
                percentage = mysqlManager.getPlayerTempQuest(questType, p.getUniqueId().toString()) * 100 / (Daily.getQuestAim(mysqlManager.getOrginQuest(questType))*60);
            }
        } else if (questType.startsWith("w")) {
            percentage = mysqlManager.getPlayerTempQuest(questType, p.getUniqueId().toString()) * 100 / (Weekly.getQuestAim(mysqlManager.getOrginQuest(questType)));
            if (Weekly.getQuestKategorie(mysqlManager.getOrginQuest(questType)).equalsIgnoreCase("Play")) {
                percentage = mysqlManager.getPlayerTempQuest(questType, p.getUniqueId().toString()) * 100 / (Weekly.getQuestAim(mysqlManager.getOrginQuest(questType))*60);
            }
        } else if (questType.startsWith("m")) {
            percentage = mysqlManager.getPlayerTempQuest(questType, p.getUniqueId().toString()) * 100 / (Monthly.getQuestAim(mysqlManager.getOrginQuest(questType)));
            if (Monthly.getQuestKategorie(mysqlManager.getOrginQuest(questType)).equalsIgnoreCase("Play")) {
                percentage = mysqlManager.getPlayerTempQuest(questType, p.getUniqueId().toString()) * 100 / (Monthly.getQuestAim(mysqlManager.getOrginQuest(questType))*60);
            }
        }

        String progress = null;

        if (percentage <= 20) {
            progress = percentage + "% □□□□□"; //■□□□□
        }
        if (percentage >= 20) {
            progress = percentage + "% ■□□□□";
        }
        if (percentage >= 40) {
            progress = percentage + "% ■■□□□";
        }
        if (percentage >= 60) {
            progress = percentage + "% ■■■□□";
        }
        if (percentage >= 80) {
            progress = percentage + "% ■■■■□";
        }

        return progress;
    }
}

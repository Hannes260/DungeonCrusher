package net.dbsgameplay.dungeoncrusher.utils;

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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.List;

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

    public static BossBar questBar_t4 = Bukkit.createBossBar("§6Verbessere dein Schwert auf Lvl. 2.", BarColor.BLUE, BarStyle.SOLID);
    public static BossBar questBar_t3 = Bukkit.createBossBar("§6Kaufe dir Essen im Teleporter.", BarColor.BLUE, BarStyle.SOLID);
    public static BossBar questBar_t2 = Bukkit.createBossBar("§6Trinke einen Stärketrank.", BarColor.BLUE, BarStyle.SOLID);
    public static BossBar questBar_t1 = Bukkit.createBossBar("§6Erreiche Ebene 2.", BarColor.BLUE, BarStyle.SOLID);

    public static Inventory getQuestmenü() {
        return questMenu;
    }

    public static void fillQuestmenü(Player player) {
        //Daily
        ItemStack dailyTitle = new ItemStack(Material.CLOCK);
        ItemMeta dailyTitleMeta = dailyTitle.getItemMeta();
        dailyTitleMeta.setDisplayName("§dDaily Quests");
        dailyTitleMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_STORED_ENCHANTS);
        dailyTitle.setItemMeta(dailyTitleMeta);
        questMenu.setItem(11, dailyTitle);

        for (int i = 1; i != 4; i++) {
            int[] slots = {20, 29, 38};

            ItemStack Stack = new ItemStack(Material.NAME_TAG);
            ItemMeta Meta = Stack.getItemMeta();
            Meta.setItemName(ChatColor.GOLD + Daily.getQuestTitle(mysqlManager.getOrginQuest("daily" + i)));
            ArrayList<String> List = new ArrayList<>();
            if (mysqlManager.getPlayerQuest("daily" + i ,player.getUniqueId().toString()) == null) {
                mysqlManager.updatePlayerQuest("daily" + i,false, player.getUniqueId().toString());
            }
            if (mysqlManager.getPlayerQuest("daily" + i, player.getUniqueId().toString())) {
                List.add("§aAbgeschlossen!");
                Meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
            } else {
                List.add(loadProgressBar(player, "daily" + i));
                List.add("§8");
                List.addAll(getRewardlist(player, Daily.RewardItemList.get(mysqlManager.getOrginQuest("daily" + i))));
                Meta.removeEnchant(Enchantment.KNOCKBACK);
            }

            Meta.setLore(List);
            Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_STORED_ENCHANTS);
            Stack.setItemMeta(Meta);
            questMenu.setItem(slots[i-1], Stack);
        }

        //Weekly
        ItemStack weeklyTitle = new ItemStack(Material.CLOCK);
        ItemMeta weeklyTitleMeta = weeklyTitle.getItemMeta();
        weeklyTitleMeta.setDisplayName("§dWeekly Quests");
        weeklyTitle.setItemMeta(weeklyTitleMeta);
        questMenu.setItem(13, weeklyTitle);

        for (int i = 1; i != 4; i++) {
            int[] slots = {22, 31, 40};

            ItemStack Stack = new ItemStack(Material.NAME_TAG);
            ItemMeta Meta = Stack.getItemMeta();
            Meta.setItemName(ChatColor.GOLD + Weekly.getQuestTitle(mysqlManager.getOrginQuest("weekly" + i)));
            ArrayList<String> List = new ArrayList<>();
            if (mysqlManager.getPlayerQuest("weekly" + i ,player.getUniqueId().toString()) == null) {
                mysqlManager.updatePlayerQuest("weekly" + i,false, player.getUniqueId().toString());
            }
            if (mysqlManager.getPlayerQuest("weekly" + i, player.getUniqueId().toString())) {
                List.add("§aAbgeschlossen!");
                Meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
            } else {
                List.add(loadProgressBar(player, "weekly" + i));
                List.add("§8");
                List.addAll(getRewardlist(player, Weekly.RewardItemList.get(mysqlManager.getOrginQuest("weekly" + i))));
                Meta.removeEnchant(Enchantment.KNOCKBACK);
            }
            Meta.setLore(List);
            Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_STORED_ENCHANTS);
            Stack.setItemMeta(Meta);
            questMenu.setItem(slots[i-1], Stack);
        }

        //Monthly
        ItemStack monthlyTitle = new ItemStack(Material.CLOCK);
        ItemMeta monthlyTitleMeta = monthlyTitle.getItemMeta();
        monthlyTitleMeta.setDisplayName("§dMonthly Quests");
        monthlyTitle.setItemMeta(monthlyTitleMeta);
        questMenu.setItem(15, monthlyTitle);

        for (int i = 1; i != 4; i++) {
            int[] slots = {24, 33, 42};

            ItemStack Stack = new ItemStack(Material.NAME_TAG);
            ItemMeta Meta = Stack.getItemMeta();
            Meta.setItemName(ChatColor.GOLD + Monthly.getQuestTitle(mysqlManager.getOrginQuest("monthly" + i)));
            ArrayList<String> List = new ArrayList<>();
            if (mysqlManager.getPlayerQuest("monthly" + i ,player.getUniqueId().toString()) == null) {
                mysqlManager.updatePlayerQuest("monthly" + i,false, player.getUniqueId().toString());
            }
            if (mysqlManager.getPlayerQuest("monthly" + i, player.getUniqueId().toString())) {
                List.add("§aAbgeschlossen!");
                Meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
            } else {
                List.add(loadProgressBar(player, "monthly" + i));
                List.add("§8");
                List.addAll(getRewardlist(player, Monthly.RewardItemList.get(mysqlManager.getOrginQuest("monthly" + i))));
                Meta.removeEnchant(Enchantment.KNOCKBACK);
            }
            Meta.setLore(List);
            Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_STORED_ENCHANTS);
            Stack.setItemMeta(Meta);
            questMenu.setItem(slots[i-1], Stack);
        }
    }

    public static void openRewardmenü(Player p, @NotNull Map<String, Object> rewardList) {
        ItemStack rawCopper = new ItemStack(Material.RAW_COPPER);
        ItemMeta rawCopperMeta = rawCopper.getItemMeta();

        if (rewardList == null) {
            for (int i = 0; i != 10; i++) {
                if (QuestBuilder.unclaimedQuestRewards.containsKey(p.getUniqueId().toString()+i)) {
                    QuestBuilder.openRewardmenü(p, unclaimedQuestRewards.get(p.getUniqueId().toString()+i));
                    QuestBuilder.unclaimedQuestRewards.remove(p.getUniqueId().toString()+i);
                }
            }
        }

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
        if (rewardList.get("netheriteplatten") == null) {
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
        return mysqlManager.getTutorialQuest(p.getUniqueId().toString()).equals("t0");
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

    public static ArrayList<String> getRewardlist(Player p, @NotNull Map<String, Object> rewardList) {
        ArrayList<String> list = new ArrayList<>();

        list.add("§6Rewards:");

        String[] materialList = {"rohkupfer", "kupferbarren", "rohgold", "goldbarren", "roheisen", "eisenbarren", "kohle", "stein", "bruchstein", "diamant", "diamanterz", "netheritebarren", "netheriteplatten"};

        for (String s : materialList) {
            if (rewardList.get(s) != null) {
                if (!rewardList.get(s).toString().equals("0")) {
                    p.sendMessage(rewardList.get(s).toString());
                    list.add("§8" + s + ": " + rewardList.get(s));
                }
            }
        }

        return list;
    }
}

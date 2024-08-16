package net.dbsgameplay.dungeoncrusher.enums.Shop;

import net.dbsgameplay.dungeoncrusher.Commands.interfaces.ShopCategory;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
import net.dbsgameplay.dungeoncrusher.utils.TexturedHeads;
import net.dbsgameplay.dungeoncrusher.utils.shops.ShopManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import java.util.*;

public class ExchangeCategory implements ShopCategory {

    private final MYSQLManager mysqlManager;
    private final ScoreboardBuilder scoreboardBuilder;
    private final Map<Integer, ExchangeCategory.ShopItem> items;
    public ExchangeCategory(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
        this.scoreboardBuilder = new ScoreboardBuilder(DungeonCrusher.getInstance());

        this.items = new HashMap<>();
        items.put(1, new ExchangeCategory.ShopItem("20x Kupferbarren", Material.COPPER_INGOT, 20, Arrays.asList("§7Benötigt: §6100x Rohkupfer", "§7Gibt: §620x Kupferbarren")));
        items.put(2, new ExchangeCategory.ShopItem("200x Kupferbarren", Material.COPPER_INGOT, 100, Arrays.asList("§7Benötigt: §61000x Rohkupfer", "§7Gibt: §6200x Kupferbarren")));
        items.put(6, new ExchangeCategory.ShopItem("100x Rohkupfer", Material.RAW_COPPER, 100, Arrays.asList("§7Benötigt: §620x Kupferbarren", "§7Gibt: §6100x Rohkupfer")));
        items.put(7, new ExchangeCategory.ShopItem("1000x Rohkupfer", Material.RAW_COPPER, 1000, Arrays.asList("§7Benötigt: §6200x Kupferbarren", "§7Gibt: §61000x Rohkupfer")));

        items.put(10, new ExchangeCategory.ShopItem("100x Bruchstein", Material.COBBLESTONE, 20, Arrays.asList("§7Benötigt: §620x stein", "§7Gibt: §6100x Bruchstein")));
        items.put(11, new ExchangeCategory.ShopItem("1000x Bruchstein", Material.COBBLESTONE, 1000, Arrays.asList("§7Benötigt: §6200x stein", "§7Gibt: §61000x Bruchstein")));
        items.put(15, new ExchangeCategory.ShopItem("20x Stein", Material.STONE, 20, Arrays.asList("§7Benötigt: §6100x bruchstein", "§7Gibt: 20x Stein")));
        items.put(16, new ExchangeCategory.ShopItem("200x Stein", Material.STONE, 200, Arrays.asList("§7Benötigt: §61000x bruchstein", "§7Gibt: 200x Stein")));

        items.put(19, new ExchangeCategory.ShopItem("20x Eisenbarren", Material.IRON_INGOT, 20, Arrays.asList("§7Benötigt: §6100x Roheisen", "§7Gibt: §620x Eisenbarren")));
        items.put(20, new ExchangeCategory.ShopItem("200x Eisenbarren", Material.IRON_INGOT, 200, Arrays.asList("§7Benötigt: §61000x Roheisen", "§7Gibt: §6200x Eisenbarren")));
        items.put(24, new ExchangeCategory.ShopItem("100x Roheisen", Material.RAW_IRON, 100, Arrays.asList("§7Benötigt: §620x Eisenbarren", "§7Gibt: §6100x Roheisen")));
        items.put(25, new ExchangeCategory.ShopItem("1000x Roheisen", Material.RAW_IRON, 1000, Arrays.asList("§7Benötigt: §6200x Eisenbarren", "§7Gibt: §61000x Roheisen")));

        items.put(28, new ExchangeCategory.ShopItem("20x Goldbarren", Material.GOLD_INGOT, 20, Arrays.asList("§7Benötigt: §6100x Rohgold", "§7Gibt: §620x Goldbarren")));
        items.put(29, new ExchangeCategory.ShopItem("200x Goldbarren", Material.GOLD_INGOT, 200, Arrays.asList("§7Benötigt: §61000x Rohgold", "§7Gibt: §6200x Goldbarren")));
        items.put(33, new ExchangeCategory.ShopItem("100x Rohgold", Material.RAW_GOLD, 100, Arrays.asList("§7Benötigt: §620x Goldbarren", "§7Gibt: §6100x Rohgold")));
        items.put(34, new ExchangeCategory.ShopItem("1000x Rohgold", Material.RAW_GOLD, 1000, Arrays.asList("§7Benötigt: §6200x Goldbarren", "§7Gibt: §61000x Rohgold")));

        items.put(37, new ExchangeCategory.ShopItem("20x Diamant", Material.DIAMOND, 20, Arrays.asList("§7Benötigt: §6100x Diamanterz", "§7Gibt: §620x Diamant")));
        items.put(38, new ExchangeCategory.ShopItem("200x Diamant", Material.DIAMOND, 200, Arrays.asList("§7Benötigt: §61000x Diamanterz", "§7Gibt: §6200x Diamant")));
        items.put(42, new ExchangeCategory.ShopItem("100x Diamanterz", Material.DIAMOND_ORE, 100, Arrays.asList("§7Benötigt: §620x Diamant", "§7Gibt: §6100x Diamanterz")));
        items.put(43, new ExchangeCategory.ShopItem("1000x Diamanterz", Material.DIAMOND_ORE, 1000, Arrays.asList("§7Benötigt: §6200x Diamant", "§7Gibt: §61000x Diamanterz")));

        items.put(46, new ExchangeCategory.ShopItem("20x Netheritbarren", Material.NETHERITE_INGOT, 20, Arrays.asList("§7Benötigt: §6100x Netheritplatten", "§7Gibt: §620x Netheritbarren")));
        items.put(47, new ExchangeCategory.ShopItem("200x Netheritbarren", Material.NETHERITE_INGOT, 200, Arrays.asList("§7Benötigt: §61000x Netheritplatten", "§7Gibt: §6200x Netheritbarren")));
        items.put(51, new ExchangeCategory.ShopItem("100x Netheritplatten", Material.NETHERITE_SCRAP, 100, Arrays.asList("§7Benötigt: §620x Netheritbarren", "§7Gibt: §6100x Netheritplatten")));
        items.put(52, new ExchangeCategory.ShopItem("1000x Netheritplatten", Material.NETHERITE_SCRAP, 1000, Arrays.asList("§7Benötigt: §6200x Netheritbarren", "§7Gibt: §61000x Netheritplatten")));

        items.put(22, new ExchangeCategory.ShopItem("100x Kohle", Material.COAL, 100, Arrays.asList("§7Benötigt: §6100€", "§7Gibt: §6100x Kohle")));
        items.put(31, new ExchangeCategory.ShopItem("100€", Material.PAPER, 100, Arrays.asList("§7Benötigt: §6100x Kohle", "§7Gibt: §6100€")));
    }

@Override
public void openMenu(Player player) {
    Inventory inv = Bukkit.createInventory(null, 9 * 6, "§7➢ Eintausch");

    // Populate inventory with items from the HashMap
    for (int slot : items.keySet()) {
        ExchangeCategory.ShopItem shopItem = items.get(slot);
        ItemStack itemStack = new ItemStack(shopItem.material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(shopItem.displayName);
        meta.setLore(shopItem.lore);
        itemStack.setItemMeta(meta);

        inv.setItem(slot, itemStack);
    }

    addBackButton(player, inv);
    player.openInventory(inv);
}

@Override
public void handleItemClick(Player player, ItemStack clickedItem) {
    if (clickedItem == null || clickedItem.getType() == Material.AIR) {
        return;
    }

    ItemMeta clickedMeta = clickedItem.getItemMeta();
    if (clickedMeta == null) {
        return;
    }

    String clickedDisplayName = clickedMeta.getDisplayName();

    for (int slot : items.keySet()) {
        ExchangeCategory.ShopItem shopItem = items.get(slot);
        if (shopItem.displayName.equals(clickedDisplayName)) {
            //tauschen logic
            exchangeItems(player, shopItem);
            return;
        }
    }

    if ("§7➢ Zurück".equals(clickedDisplayName)) {
        ShopManager.openMainShopMenu(player, mysqlManager);
    }
}

@Override
public void handleShiftClick(Player player, ItemStack clickedItem) {

}
    private void exchangeItems(Player player, ShopItem shopItem) {
        String playerUUID = player.getUniqueId().toString();
        int requiredAmount = 0;
        int currentAmount = 0;
        String requiredItem = "";

        // Bestimmen des benötigten Gegenstands und der Menge basierend auf der Anzeigebeschreibung
        switch (shopItem.displayName) {
            case "20x Kupferbarren":
                requiredItem = "raw_copper";
                requiredAmount = 100;
                break;
            case "200x Kupferbarren":
                requiredItem = "raw_copper";
                requiredAmount = 1000;
                break;
            case "100x Rohkupfer":
                requiredItem = "copper_ingot";
                requiredAmount = 20;
                break;
            case "1000x Rohkupfer":
                requiredItem = "copper_ingot";
                requiredAmount = 200;
                break;
            case "100x Bruchstein":
                requiredItem = "stone";
                requiredAmount = 20;
                break;
            case "1000x Bruchstein":
                requiredItem = "stone";
                requiredAmount = 200;
                break;
            case "20x Stein":
                requiredItem = "cobblestone";
                requiredAmount = 100;
                break;
            case "200x Stein":
                requiredItem = "cobblestone";
                requiredAmount = 1000;
                break;
            case "20x Eisenbarren":
                requiredItem = "raw_iron";
                requiredAmount = 100;
                break;
            case "200x Eisenbarren":
                requiredItem = "raw_iron";
                requiredAmount = 1000;
                break;
            case "100x Roheisen":
                requiredItem = "iron_ingot";
                requiredAmount = 20;
                break;
            case "1000x Roheisen":
                requiredItem = "iron_ingot";
                requiredAmount = 200;
                break;
            case "20x Goldbarren":
                requiredItem = "raw_gold";
                requiredAmount = 100;
                break;
            case "200x Goldbarren":
                requiredItem = "raw_gold";
                requiredAmount = 1000;
                break;
            case "100x Rohgold":
                requiredItem = "gold_ingot";
                requiredAmount = 20;
                break;
            case "1000x Rohgold":
                requiredItem = "gold_ingot";
                requiredAmount = 200;
                break;
            case "20x Diamant":
                requiredItem = "diamond_ore";
                requiredAmount = 100;
                break;
            case "200x Diamant":
                requiredItem = "diamond_ore";
                requiredAmount = 1000;
                break;
            case "100x Diamanterz":
                requiredItem = "diamond";
                requiredAmount = 20;
                break;
            case "1000x Diamanterz":
                requiredItem = "diamond";
                requiredAmount = 200;
                break;
            case "20x Netheritbarren":
                requiredItem = "netherite_scraps";
                requiredAmount = 100;
                break;
            case "200x Netheritbarren":
                requiredItem = "netherite_scraps";
                requiredAmount = 1000;
                break;
            case "100x Netheritplatten":
                requiredItem = "netherite_ingot";
                requiredAmount = 20;
                break;
            case "1000x Netheritplatten":
                requiredItem = "netherite_ingot";
                requiredAmount = 200;
                break;
            case "100x Kohle":
                handleCoalExchange(player);
                return;
            case "100€":
                handleEuroExchange(player);
                return;
            default:
                return;
        }

        // Check if player has enough raw material
        currentAmount = mysqlManager.getItemAmount(playerUUID, requiredItem);
        if (currentAmount >= requiredAmount) {
            // Deduct required material
            mysqlManager.updateItemAmount(playerUUID, requiredItem, currentAmount - requiredAmount);

            // Add new items
            int newAmount = mysqlManager.getItemAmount(playerUUID, shopItem.material.name().toLowerCase());
            mysqlManager.updateItemAmount(playerUUID, shopItem.material.name().toLowerCase(), newAmount + shopItem.amount);
            updateItems(player);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f,1.0f);
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.exchanged", "%item_name%", shopItem.displayName));

        } else {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.exchangedfailed", "%required_amount%", String.valueOf(requiredAmount), "%required_item%", requiredItem.replace("_", " ")));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_DEATH, 1.0f,1.0f);
        }
    }

    private void handleCoalExchange(Player player) {
        String playerUUID = player.getUniqueId().toString();
        double requiredMoney = 100;

        // Check if player has enough coal to sell
        int currentAmountCoal = mysqlManager.getItemAmount(playerUUID, "coal");
            // Check if player has enough money to buy coal
            if (removeMoney(player, requiredMoney)) {
                // Add 100 units of coal to player's inventory
                mysqlManager.updateItemAmount(playerUUID, "coal", currentAmountCoal + 100);
                updateItems(player);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f,1.0f);
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.changedmoneytocoal", "", ""));
            } else {
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughmoneyforchangecoal", "", ""));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_DEATH, 1.0f,1.0f);
            }
    }

    private void handleEuroExchange(Player player) {
        String playerUUID = player.getUniqueId().toString();
        double requiredCoal = 100;
        double givenMoney = 100;

        // Check if player has enough coal to exchange
        int currentAmountCoal = mysqlManager.getItemAmount(playerUUID, "coal");
        if (currentAmountCoal >= 100) {
            // Exchange coal for money
            if (removeItem(playerUUID, "coal", requiredCoal)) {
                addMoney(player, givenMoney);
                updateItems(player);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f,1.0f);
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.changedcoal", "", ""));
            } else {
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughcoaltochange", "", ""));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_DEATH, 1.0f,1.0f);
            }
        } else {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughcoaltochange", "", ""));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_DEATH, 1.0f,1.0f);
        }
    }

    // Method to remove items from player's inventory
    private boolean removeItem(String playerUUID, String item, double amount) {
        int currentAmount = mysqlManager.getItemAmount(playerUUID, item);
        if (currentAmount >= amount) {
            mysqlManager.updateItemAmount(playerUUID, item, currentAmount - (int) amount);
            return true;
        }
        return false;
    }

    private boolean removeMoney(Player p, double amount) {
        double currentMoney = 0;
        String currentMoneyString = mysqlManager.getBalance(p.getUniqueId().toString());
        currentMoneyString = currentMoneyString.replace(",", "");
        currentMoney = Double.parseDouble(currentMoneyString);

        double newMoney = currentMoney - amount;
        if (newMoney < 0) {
            return false;
        }

        String formattedMoney = String.format(Locale.ENGLISH, "%,.2f", newMoney);
        mysqlManager.updateBalance(p.getUniqueId().toString(), formattedMoney);
        scoreboardBuilder.updateMoney(p);

        return true;
    }
    private void addMoney(Player player, double amount) {
        String playerName = player.getUniqueId().toString();
        double currentMoney;
        try {
            String currentMoneyString = mysqlManager.getBalance(playerName);
            currentMoneyString = currentMoneyString.replace(",", "");
            currentMoney = Double.parseDouble(currentMoneyString);
        } catch (NumberFormatException e) {
            return;
        }

        double newMoney = currentMoney + amount;
        String formattedMoney = String.format(Locale.ENGLISH, "%,.2f", newMoney);
        mysqlManager.updateBalance(playerName, formattedMoney);
        scoreboardBuilder.updateMoney(player);
    }
    private void addBackButton(Player player, Inventory inventory) {
        PlayerProfile backheadprofile = TexturedHeads.getProfile("https://textures.minecraft.net/texture/f7aacad193e2226971ed95302dba433438be4644fbab5ebf818054061667fbe2");
        ItemStack backhead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headmeta = (SkullMeta) backhead.getItemMeta();
        headmeta.setOwnerProfile(backheadprofile);
        headmeta.setDisplayName("§7➢ Zurück");
        backhead.setItemMeta(headmeta);
        inventory.setItem(45, backhead);
    }
    private static class ShopItem {
        private final String displayName;
        private final Material material;
        private final int amount;
        private final List<String> lore;

        private ShopItem(String displayName, Material material, int amount, List<String> lore) {
            this.displayName = displayName;
            this.material = material;
            this.amount = amount;
            this.lore = lore;
        }
    }

    private void updateItems(Player player) {
        ItemStack rawcopper = new ItemStack(Material.RAW_COPPER);
        ItemMeta rawcoppermeta = rawcopper.getItemMeta();
        rawcoppermeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "raw_copper"));
        rawcoppermeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        rawcoppermeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        rawcopper.setItemMeta(rawcoppermeta);
        player.getInventory().setItem(9, rawcopper);

        ItemStack copperingot = new ItemStack(Material.COPPER_INGOT);
        ItemMeta copperingotmeta = copperingot.getItemMeta();
        copperingotmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "copper_ingot"));
        copperingotmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        copperingotmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        copperingot.setItemMeta(copperingotmeta);
        player.getInventory().setItem(10, copperingot);

        ItemStack rawgold = new ItemStack(Material.RAW_GOLD);
        ItemMeta rawgoldmeta = rawgold.getItemMeta();
        rawgoldmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "raw_gold"));
        rawgoldmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        rawgoldmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        rawgold.setItemMeta(rawgoldmeta);
        player.getInventory().setItem(18, rawgold);

        ItemStack goldingot = new ItemStack(Material.GOLD_INGOT);
        ItemMeta goldingotmeta = goldingot.getItemMeta();
        goldingotmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "gold_ingot"));
        goldingotmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        goldingotmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        goldingot.setItemMeta(goldingotmeta);
        player.getInventory().setItem(19, goldingot);

        ItemStack rawiron = new ItemStack(Material.RAW_IRON);
        ItemMeta rawironmeta = rawiron.getItemMeta();
        rawironmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "raw_iron"));
        rawironmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        rawironmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        rawiron.setItemMeta(rawironmeta);
        player.getInventory().setItem(27, rawiron);

        ItemStack ironingot = new ItemStack(Material.IRON_INGOT);
        ItemMeta ironingotmeta = ironingot.getItemMeta();
        ironingotmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "iron_ingot"));
        ironingotmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        ironingotmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ironingot.setItemMeta(ironingotmeta);
        player.getInventory().setItem(28, ironingot);

        ItemStack coal = new ItemStack(Material.COAL);
        ItemMeta coalmeta = coal.getItemMeta();
        coalmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "coal"));
        coalmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        coalmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        coal.setItemMeta(coalmeta);
        player.getInventory().setItem(22, coal);

        ItemStack cobblestone = new ItemStack(Material.COBBLESTONE);
        ItemMeta cobblestonemeta = cobblestone.getItemMeta();
        cobblestonemeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "cobblestone"));
        cobblestonemeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        cobblestonemeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        cobblestone.setItemMeta(cobblestonemeta);
        player.getInventory().setItem(17, cobblestone);

        ItemStack stone = new ItemStack(Material.STONE);
        ItemMeta stonemeta = stone.getItemMeta();
        stonemeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "stone"));
        stonemeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        stonemeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        stone.setItemMeta(stonemeta);
        player.getInventory().setItem(16, stone);

        ItemStack diamondore = new ItemStack(Material.DIAMOND_ORE);
        ItemMeta diamondoremeta = diamondore.getItemMeta();
        diamondoremeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "diamond_ore"));
        diamondoremeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        diamondoremeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        diamondore.setItemMeta(diamondoremeta);
        player.getInventory().setItem(26, diamondore);

        ItemStack diamond = new ItemStack(Material.DIAMOND);
        ItemMeta diamondmeta = diamond.getItemMeta();
        diamondmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "diamond"));
        diamondmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        diamondmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        diamond.setItemMeta(diamondmeta);
        player.getInventory().setItem(25, diamond);

        ItemStack netherite = new ItemStack(Material.NETHERITE_INGOT);
        ItemMeta netheritemeta = netherite.getItemMeta();
        netheritemeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "netherite_ingot"));
        netheritemeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        netheritemeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        netherite.setItemMeta(netheritemeta);
        player.getInventory().setItem(34, netherite);

        ItemStack netheritescrap = new ItemStack(Material.NETHERITE_SCRAP);
        ItemMeta netheritescrapmeta = netheritescrap.getItemMeta();
        netheritescrapmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "netherite_scrap"));
        netheritescrapmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        netheritescrapmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        netheritescrap.setItemMeta(netheritescrapmeta);
        player.getInventory().setItem(35, netheritescrap);
    }
}

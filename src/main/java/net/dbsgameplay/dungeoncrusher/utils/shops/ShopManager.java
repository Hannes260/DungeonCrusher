package net.dbsgameplay.dungeoncrusher.utils.shops;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.Commands.interfaces.ShopCategory;
import net.dbsgameplay.dungeoncrusher.enums.Shop.ExchangeCategory;
import net.dbsgameplay.dungeoncrusher.enums.Shop.FoodCategory;
import net.dbsgameplay.dungeoncrusher.enums.Shop.PotionCategory;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class ShopManager { ;
    MYSQLManager mysqlManager;
    public ShopManager(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
        initializeCategories(mysqlManager);
    }

    private static Map<String, ShopCategory> categories = new HashMap<>();
    private void initializeCategories(MYSQLManager mysqlManager) {
        categories.put("§7➢ essen", new FoodCategory(mysqlManager));
        categories.put("§7➢ eintausch", new ExchangeCategory(mysqlManager));
        categories.put("§7➢ tränke", new PotionCategory(mysqlManager));
    }

    public static void openMainShopMenu(Player player, MYSQLManager mysqlManager) {
        String DisplayName = "§f<shift:-8>%oraxen_shop%";
        DisplayName = PlaceholderAPI.setPlaceholders(player, DisplayName);

        Inventory inv = Bukkit.createInventory(null, 9*6, DisplayName);

        ItemStack foodhead = new ItemStack(Material.PAPER);
        ItemMeta foodmeta = foodhead.getItemMeta();
        foodmeta.setCustomModelData(100);
        foodmeta.setDisplayName("§7➢ Essen");
        foodhead.setItemMeta(foodmeta);

        ItemStack potionhead = new ItemStack(Material.PAPER);
        ItemMeta  potionmeta = potionhead.getItemMeta();
        potionmeta.setCustomModelData(100);
        potionmeta.setDisplayName("§7➢ Tränke");
        potionhead.setItemMeta(potionmeta);

        ItemStack exchangeItem = new ItemStack(Material.PAPER);
        ItemMeta exchangeMeta = exchangeItem.getItemMeta();
        exchangeMeta.setCustomModelData(100);
        exchangeMeta.setDisplayName("§7➢ Eintausch");
        exchangeItem.setItemMeta(exchangeMeta);

        addCloseButton(player, inv);
        int[] potionslots = {15,16,17,24,25,26,33,34,35};
        for (int slot : potionslots) {
            inv.setItem(slot, potionhead);
        }
        int[] exchangeslots = {12,13,14,21,22,23,30,31,32};
        for (int slot: exchangeslots) {
            inv.setItem(slot, exchangeItem);
        }
        int[] foodslots = {9,10,11,18,19,20,27,28,29};
        for (int slot : foodslots) {
            inv.setItem(slot, foodhead);
        }
        showBottomRightItem(player, inv, mysqlManager);
        player.openInventory(inv);
    }

    private static void showBottomRightItem(Player player, Inventory inventory, MYSQLManager mysqlManager) {
        String currentmoney = mysqlManager.getBalance(player.getUniqueId().toString());
        ItemStack playerItem = new ItemStack(Material.PAPER);
        ItemMeta playerMeta = playerItem.getItemMeta();
        playerMeta.setCustomModelData(100);
        playerMeta.setDisplayName("§9Dein Geld: §a" + currentmoney + "§9€");
        playerItem.setItemMeta(playerMeta);
        inventory.setItem(53, playerItem);
    }

    public static void addCloseButton(Player player, Inventory inventory) {
        ItemStack closeItem = new ItemStack(Material.PAPER);
        ItemMeta closeMeta = closeItem.getItemMeta();
        closeMeta.setCustomModelData(100);
        closeMeta.setDisplayName("§cZurück");
        closeItem.setItemMeta(closeMeta);

        inventory.setItem(45, closeItem);
    }

    public static ShopCategory getCategory(String name) {
        name = name.trim().toLowerCase();
        ShopCategory category = categories.get(name);
        if (category == null) {
        } else {
        }
        return category;
    }

}

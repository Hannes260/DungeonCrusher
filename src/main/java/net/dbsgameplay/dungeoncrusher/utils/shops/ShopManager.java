package net.dbsgameplay.dungeoncrusher.utils.shops;

import net.dbsgameplay.dungeoncrusher.Commands.interfaces.ShopCategory;
import net.dbsgameplay.dungeoncrusher.enums.Shop.ExchangeCategory;
import net.dbsgameplay.dungeoncrusher.enums.Shop.FoodCategory;
import net.dbsgameplay.dungeoncrusher.objects.PlayerHead;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.TexturedHeads;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

import java.util.HashMap;
import java.util.Map;

public class ShopManager {

    MYSQLManager mysqlManager;
    public ShopManager(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
        initializeCategories(mysqlManager);
    }

    private static Map<String, ShopCategory> categories = new HashMap<>();
    private void initializeCategories(MYSQLManager mysqlManager) {
        categories.put("§7➢ essen", new FoodCategory(mysqlManager));
        categories.put("§7➢ eintausch", new ExchangeCategory(mysqlManager));
    }

    public static void openMainShopMenu(Player player, MYSQLManager mysqlManager){
        Inventory inv = Bukkit.createInventory(null, 9*6, "Shop");
        PlayerProfile foodprofile = TexturedHeads.getProfile("https://textures.minecraft.net/texture/1591b61529d25a7ecd6bec00948e6fe155e3007f2d7fe559f3a83c6f808e434d");
        ItemStack foodhead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta foodmeta = (SkullMeta) foodhead.getItemMeta();
        foodmeta.setOwnerProfile(foodprofile);
        foodmeta.setDisplayName("§7➢ Essen");
        foodhead.setItemMeta(foodmeta);

        PlayerProfile potionprofile = TexturedHeads.getProfile("https://textures.minecraft.net/texture/a7cc07fbdbfc44b30625d3ab09c7441970e8a571ede29d75be1284f7c8953a9f");
        ItemStack potionhead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta potionmeta = (SkullMeta) potionhead.getItemMeta();
        potionmeta.setOwnerProfile(potionprofile);
        potionmeta.setDisplayName("§7➢ Tränke");
        potionhead.setItemMeta(potionmeta);
        ItemStack exchangeItem = new ItemStack(Material.EMERALD);
        ItemMeta exchangeMeta = exchangeItem.getItemMeta();
        exchangeMeta.setDisplayName("§7➢ Eintausch");
        exchangeItem.setItemMeta(exchangeMeta);

        addCloseButton(player, inv);
        inv.setItem(24, potionhead);
        inv.setItem(22, exchangeItem);
        inv.setItem(20, foodhead);
        showBottomRightItem(player, inv, mysqlManager);
        player.openInventory(inv);
    }

    private static void showBottomRightItem(Player player, Inventory inventory, MYSQLManager mysqlManager) {
        String currentmoney = mysqlManager.getBalance(player.getUniqueId().toString());
        inventory.setItem(53, (new PlayerHead(player.getName(), "§9Dein Geld: §a" + currentmoney + "§9€", new String[0])).getItemStack());
    }

    public static void addCloseButton(Player player, Inventory inventory) {
        ItemStack closeItem = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = closeItem.getItemMeta();
        closeMeta.setDisplayName("§cSchließen");
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

package net.dbsgameplay.dungeoncrusher.utils.shops;

import net.dbsgameplay.dungeoncrusher.Commands.interfaces.ShopCategory;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.FoodCategory;
import net.dbsgameplay.dungeoncrusher.objects.PlayerHead;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
import net.dbsgameplay.dungeoncrusher.utils.TexturedHeads;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
    private static final Map<String, ShopCategory> categories = new HashMap<>();
    static {

        // Initialisierung der categories Map
        categories.put("Essen", new FoodCategory());
        // Weitere Kategorien hinzufügen, falls vorhanden
    }
    public static void openMainShopMenu(Player player){
        Inventory inv = Bukkit.createInventory(null, 9*6, "Shop");

        ItemStack category1 = new ItemStack(Material.DIAMOND);
        ItemMeta meta1 = category1.getItemMeta();
        meta1.setDisplayName("Essen");
        category1.setItemMeta(meta1);

        addCloseButton(player, inv);
        inv.setItem(20, category1);
        player.openInventory(inv);
    }

    //    private static void showBottomRightItem (Player player, Inventory inventory, MYSQLManager mysqlManager) {
    //      String currentmoney = mysqlManager.getBalance(player.getUniqueId().toString());
    //      inventory.setItem(53, (new PlayerHead(player.getName(), "§9Dein Geld: §a" + currentmoney + "§9€", new String[0])).getItemStack());
    // }
    public static void addCloseButton(Player player, Inventory inventory) {
        ItemStack closeItem = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = closeItem.getItemMeta();
        closeMeta.setDisplayName("§cSchließen");
        closeItem.setItemMeta(closeMeta);

        // Platziere das Schließ-Item unten rechts im Inventar
        inventory.setItem(45, closeItem);
    }
    public static ShopCategory getCategory(String name) {
        return categories.get(name);
    }
}

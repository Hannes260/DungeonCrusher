package net.dbsgameplay.dungeoncrusher.utils.upgrades;

import net.dbsgameplay.dungeoncrusher.Commands.interfaces.ShopCategory;
import net.dbsgameplay.dungeoncrusher.objects.PlayerHead;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class UpgradeManager {

    MYSQLManager mysqlManager;
    public UpgradeManager(MYSQLManager mysqlManager){
        this.mysqlManager = mysqlManager;
        initializeCategories(mysqlManager);
    }

    private static Map<String, ShopCategory> categories = new HashMap<>();
    private void initializeCategories(MYSQLManager mysqlManager) {

    }
    public static void openMainMenu(Player player, MYSQLManager mysqlManager) {
        Inventory inv = Bukkit.createInventory(null, 9*6, "Upgrade");
        ItemStack sworditem = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta swordmeta = sworditem.getItemMeta();
        swordmeta.setDisplayName("§7➢ Schwert");
        sworditem.setItemMeta(swordmeta);


        inv.setItem(20, sworditem);
        addCloseButton(player, inv);
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
        return categories.get(name);
    }
}

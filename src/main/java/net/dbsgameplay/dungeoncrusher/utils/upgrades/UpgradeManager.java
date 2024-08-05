package net.dbsgameplay.dungeoncrusher.utils.upgrades;

import net.dbsgameplay.dungeoncrusher.Commands.interfaces.UpgradeCategory;
import net.dbsgameplay.dungeoncrusher.enums.Upgrades.SwordCategory;
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

    private static Map<String, UpgradeCategory> categories = new HashMap<>();

    private void initializeCategories(MYSQLManager mysqlManager) {
        categories.put("§7➢ schwert", new SwordCategory(mysqlManager));
    }

    public static void openMainMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9 * 6, "Upgrade");
        ItemStack swordItem = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta swordMeta = swordItem.getItemMeta();
        swordMeta.setDisplayName("§7➢ Schwert");
        swordItem.setItemMeta(swordMeta);

        inv.setItem(20, swordItem);
        addCloseButton(player, inv);
        player.openInventory(inv);
    }

    public static void addCloseButton(Player player, Inventory inventory) {
        ItemStack closeItem = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = closeItem.getItemMeta();
        closeMeta.setDisplayName("§cSchließen");
        closeItem.setItemMeta(closeMeta);

        inventory.setItem(45, closeItem);
    }

    public static UpgradeCategory getCategory(String name) {
        name = name.trim().toLowerCase();
        Bukkit.getLogger().info("Getting category for name: " + name);
        UpgradeCategory category = categories.get(name);
        if (category == null) {
            Bukkit.getLogger().info("No category found for name: " + name);
        } else {
            Bukkit.getLogger().info("Found category: " + category);
        }
        return category;
    }
}

package net.dbsgameplay.dungeoncrusher.utils.upgrades;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.interfaces.UpgradeCategory;
import net.dbsgameplay.dungeoncrusher.enums.Upgrades.ArmorCategory;
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

    private static Map<String, UpgradeCategory> upgradecategories = new HashMap<>();
    private void initializeCategories(MYSQLManager mysqlManager) {
        upgradecategories.put("§7➢ schwert", new SwordCategory(mysqlManager));
        upgradecategories.put("§7➢ rüstung", new ArmorCategory(mysqlManager));
    }

    public static void openMainMenu(Player player) {
        String DisplayName = "§f<shift:-8>%nexo_upgrade%";
        DisplayName = PlaceholderAPI.setPlaceholders(player, DisplayName);
        Inventory inv = Bukkit.createInventory(null, 9 * 6, DisplayName);

        ItemStack Sword = new ItemStack(Material.PAPER);
        ItemMeta Swordmeta = Sword.getItemMeta();
        Swordmeta.setCustomModelData(100);
        Swordmeta.setDisplayName("§7➢ Schwert");
        Sword.setItemMeta(Swordmeta);


        ItemStack Armor = new ItemStack(Material.PAPER);
        ItemMeta Armormeta = Sword.getItemMeta();
        Armormeta.setCustomModelData(100);
        Armormeta.setDisplayName("§7➢ Rüstung");
        Armor.setItemMeta(Armormeta);

        int[] swordlots = {10,11,12,19,20,21,28,29,30};
        for (int slot : swordlots) {
            inv.setItem(slot, Sword);
        }
        int[] armorlots = {14,15,16,23,24,25,32,33,34};
        for (int slot : armorlots) {
            inv.setItem(slot, Armor);
        }
        addCloseButton(player, inv);
        player.openInventory(inv);
    }

    public static void addCloseButton(Player player, Inventory inventory) {
        ItemStack closeItem = new ItemStack(Material.PAPER);
        ItemMeta closeMeta = closeItem.getItemMeta();
        closeMeta.setCustomModelData(100);
        closeMeta.setDisplayName("§cZurück");
        closeItem.setItemMeta(closeMeta);

        inventory.setItem(45, closeItem);
    }
    public static UpgradeCategory getCategory(String displayName) {
        // Durchlaufe alle Kategorien und vergleiche den Display-Namen ohne Formatierung
        for (Map.Entry<String, UpgradeCategory> entry : upgradecategories.entrySet()) {
            String categoryName = entry.getKey();
            if (displayName.equalsIgnoreCase(categoryName)) {
                return entry.getValue();
            }
        }
        return null;
    }
}

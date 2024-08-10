package net.dbsgameplay.dungeoncrusher.utils.upgrades;

import net.dbsgameplay.dungeoncrusher.Commands.interfaces.UpgradeCategory;
import net.dbsgameplay.dungeoncrusher.enums.Upgrades.SwordCategory;
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

public class UpgradeManager {

    MYSQLManager mysqlManager;

    public UpgradeManager(MYSQLManager mysqlManager){
        this.mysqlManager = mysqlManager;
        initializeCategories(mysqlManager);
    }

    private static Map<String, UpgradeCategory> upgradecategories = new HashMap<>();
    private void initializeCategories(MYSQLManager mysqlManager) {
        upgradecategories.put("§7➢ schwert", new SwordCategory(mysqlManager));
    }

    public static void openMainMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9 * 6, "Upgrade");

        PlayerProfile foodprofile = TexturedHeads.getProfile("https://textures.minecraft.net/texture/77334cddfab45d75ad28e1a47bf8cf5017d2f0982f6737da22d4972952510661");
        ItemStack foodhead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta foodmeta = (SkullMeta) foodhead.getItemMeta();
        foodmeta.setOwnerProfile(foodprofile);
        foodmeta.setDisplayName("§7➢ Schwert");
        foodhead.setItemMeta(foodmeta);
        inv.setItem(21, foodhead);
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

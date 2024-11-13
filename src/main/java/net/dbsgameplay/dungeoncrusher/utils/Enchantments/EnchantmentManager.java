package net.dbsgameplay.dungeoncrusher.utils.Enchantments;


import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.enums.Enchantments.SwordEnchantments;
import net.dbsgameplay.dungeoncrusher.interfaces.EnchantmentCategory;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class EnchantmentManager {
    MYSQLManager mysqlManager;
    public EnchantmentManager(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
        initializeCategories(mysqlManager);
    }

    private static Map<String, EnchantmentCategory> categories = new HashMap<>();
    private void initializeCategories(MYSQLManager mysqlManager) {
        categories.put("§7Schwert", new SwordEnchantments(mysqlManager));
    }
    public static void openMainShopMenu(Player player, MYSQLManager mysqlManager) {
        String DisplayName = "§f<shift:-8>%oraxen_enchantment%";
        DisplayName = PlaceholderAPI.setPlaceholders(player, DisplayName);

        Inventory inv = Bukkit.createInventory(null, 9*6, DisplayName);

        ItemStack forschen = new ItemStack(Material.PAPER);
        ItemMeta forschenmeta = forschen.getItemMeta();
        forschenmeta.setCustomModelData(100);
        forschenmeta.setDisplayName("§7➢ Forscher");
        forschen.setItemMeta(forschenmeta);

        ItemStack verzaubern = new ItemStack(Material.PAPER);
        ItemMeta verzaubernmeta = verzaubern.getItemMeta();
        verzaubernmeta.setCustomModelData(100);
        verzaubernmeta.setDisplayName("§7➢ Verzaubern");
        verzaubern.setItemMeta(verzaubernmeta);

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

    public static EnchantmentCategory getCategory(String name) {
        name = name.trim().toLowerCase();
        EnchantmentCategory category = categories.get(name);
        if (category == null) {
        } else {
        }
        return category;
    }

}

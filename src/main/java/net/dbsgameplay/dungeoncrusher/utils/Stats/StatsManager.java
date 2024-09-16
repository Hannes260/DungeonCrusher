package net.dbsgameplay.dungeoncrusher.utils.Stats;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.Commands.interfaces.StatsCategory;
import net.dbsgameplay.dungeoncrusher.enums.Stats.KillsCategory;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class StatsManager {
    MYSQLManager mysqlManager;
    public StatsManager(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
        initializeCategories(mysqlManager);
    }
    private static Map<String, StatsCategory> categories = new HashMap<>();
    private void initializeCategories(MYSQLManager mysqlManager) {
        categories.put("§7➢ kills", new KillsCategory(mysqlManager));
    }

    public static void openMainShopMenu(Player player) {
        String DisplayName = "§f<shift:-8>%oraxen_stats%";
        DisplayName = PlaceholderAPI.setPlaceholders(player, DisplayName);

        Inventory inv = Bukkit.createInventory(null, 9*6, DisplayName);

        ItemStack foodhead = new ItemStack(Material.PAPER);
        ItemMeta foodmeta = foodhead.getItemMeta();
        foodmeta.setCustomModelData(100);
        foodmeta.setDisplayName("§7➢ Kills");
        foodhead.setItemMeta(foodmeta);

        addCloseButton(player, inv);

        int[] killslots = {9,10,11,18,19,20,27,28,29};
        for (int slot : killslots) {
            inv.setItem(slot, foodhead);
        }
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
    public static StatsCategory getCategory(String name) {
        name = name.trim().toLowerCase();
        StatsCategory category = categories.get(name);
        if (category == null) {
        } else {
        }
        return category;
    }
}

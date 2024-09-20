package net.dbsgameplay.dungeoncrusher.utils.Stats;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.Commands.interfaces.StatsCategory;
import net.dbsgameplay.dungeoncrusher.enums.Stats.GeneralStatsCategory;
import net.dbsgameplay.dungeoncrusher.enums.Stats.KillsCategory;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        categories.put("§7➢ stats", new GeneralStatsCategory(mysqlManager));
    }

    public static void openMainShopMenu(Player player) {
        String DisplayName = "§f<shift:-8>%oraxen_stats_gui%";
        DisplayName = PlaceholderAPI.setPlaceholders(player, DisplayName);

        Inventory inv = Bukkit.createInventory(null, 9*6, DisplayName);

        ItemStack killsItem = new ItemStack(Material.PAPER);
        ItemMeta killsMeta = killsItem.getItemMeta();
        killsMeta.setCustomModelData(100);
        killsMeta.setDisplayName("§7➢ Kills");
        killsItem.setItemMeta(killsMeta);

        addCloseButton(player, inv);

        int[] killslots = {10,11,12,19,20,21,28,29,30};
        for (int slot : killslots) {
            inv.setItem(slot, killsItem);
        }

        ItemStack statsItem = new ItemStack(Material.PAPER);
        ItemMeta statsMeta = statsItem.getItemMeta();
        statsMeta.setCustomModelData(100);
        statsMeta.setDisplayName("§7➢ Stats");
        statsItem.setItemMeta(statsMeta);

        addCloseButton(player, inv);

        int[] statslots = {14,15,16,23,24,25,32,33,34};
        for (int slot : statslots) {
            inv.setItem(slot, statsItem);
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

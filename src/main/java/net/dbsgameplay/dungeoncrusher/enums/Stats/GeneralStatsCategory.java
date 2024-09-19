package net.dbsgameplay.dungeoncrusher.enums.Stats;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.Commands.interfaces.StatsCategory;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class GeneralStatsCategory implements StatsCategory {
    private final MYSQLManager mysqlManager;

    public GeneralStatsCategory(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
    }

    @Override
    public void openMenu(Player player) {
        String DisplayName = "%oraxen_generalstats_gui%";
        DisplayName = PlaceholderAPI.setPlaceholders(player, DisplayName);

        Inventory inv = Bukkit.createInventory(null, 9 * 6, DisplayName);
        ItemStack playTimeItem = new ItemStack(Material.CLOCK);
        ItemMeta playTimeMeta = playTimeItem.getItemMeta();
        playTimeMeta.setDisplayName("§7➢ Spielzeit");
        playTimeMeta.setLore(Collections.singletonList(String.valueOf(player.getStatistic(Statistic.PLAY_ONE_MINUTE))));
        playTimeItem.setItemMeta(playTimeMeta);
        inv.setItem(0, playTimeItem);

        ItemStack damageItem = new ItemStack(Material.GOLDEN_SWORD);
        ItemMeta damageMeta = damageItem.getItemMeta();
        damageMeta.setDisplayName("§7➢ Ausgerichteter Schaden");
        damageMeta.setLore(Collections.singletonList(String.valueOf(player.getStatistic(Statistic.DAMAGE_DEALT))));
        damageItem.setItemMeta(damageMeta);
        inv.setItem(1, damageItem);
    }

    @Override
    public void handleItemClick(Player player, ItemStack clickedItem) {

    }
}

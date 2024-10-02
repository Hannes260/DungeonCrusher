package net.dbsgameplay.dungeoncrusher.listener.Stats;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.interfaces.StatsCategory;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.Stats.GeneralStatsCategory;
import net.dbsgameplay.dungeoncrusher.enums.Stats.KillsCategory;
import net.dbsgameplay.dungeoncrusher.listener.Navigator.NavigatorListener;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Stats.StatsManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class StatsListener implements Listener {
    private final LocationConfigManager locationConfigManager;
    private final MYSQLManager mysqlManager;
    private final DungeonCrusher dungeonCrusher;

    public StatsListener(DungeonCrusher dungeonCrusher, LocationConfigManager locationConfigManager, MYSQLManager mysqlManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.locationConfigManager = locationConfigManager;
        this.mysqlManager = mysqlManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedInventory == null || clickedItem == null || clickedItem.getType().isAir()) {
            return;
        }

        // Verhindere Hotkey-Aktionen mit der Nummerntaste
        if (event.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD || event.getClick() == ClickType.NUMBER_KEY) {
            event.setCancelled(true);
            player.updateInventory(); // Verhindert visuelle Bugs
            return;
        }

        String displayName = "§f<shift:-8>%oraxen_stats_gui%";
        displayName = PlaceholderAPI.setPlaceholders(player, displayName);
        String title = event.getView().getTitle();

        String displayNameKills = "§f<shift:-8>%oraxen_kills_gui%";
        displayNameKills = PlaceholderAPI.setPlaceholders(player, displayNameKills);
        String displayNameGeneralStats = "§f<shift:-8>%oraxen_general_stats_gui%";
        displayNameGeneralStats = PlaceholderAPI.setPlaceholders(player, displayNameGeneralStats);

        // Main Stats Inventory
        if (title.equalsIgnoreCase(displayName)) {
            event.setCancelled(true);

            // Klick auf andere Kategorien wie "Kills"
            String categoryName = clickedItem.getItemMeta().getDisplayName().toLowerCase();
            StatsCategory category = StatsManager.getCategory(categoryName);
            if (category != null) {
                category.openMenu(player);
                player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1.0f, 1.0f);
            } else if ("§cZurück".equals(clickedItem.getItemMeta().getDisplayName())) {
                NavigatorListener navigatorListener = new NavigatorListener(dungeonCrusher, locationConfigManager, mysqlManager);
                navigatorListener.openNavigator(player);
                player.playSound(player.getLocation(), Sound.BLOCK_BARREL_CLOSE, 1.0f, 1.0f);
            }

        } else if (title.equalsIgnoreCase(displayNameKills)) {
            event.setCancelled(true);
            KillsCategory killsCategory = new KillsCategory(mysqlManager);
            killsCategory.handleItemClick(player, clickedItem);
        } else if (title.equalsIgnoreCase(displayNameGeneralStats)) {
            event.setCancelled(true);
            GeneralStatsCategory generalStatsCategory = new GeneralStatsCategory(mysqlManager);
            generalStatsCategory.handleItemClick(player, clickedItem);
        }
    }
}

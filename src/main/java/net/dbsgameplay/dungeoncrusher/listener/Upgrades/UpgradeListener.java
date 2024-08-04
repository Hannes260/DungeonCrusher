package net.dbsgameplay.dungeoncrusher.listener.Upgrades;

import net.dbsgameplay.dungeoncrusher.Commands.interfaces.UpgradeCategory;
import net.dbsgameplay.dungeoncrusher.utils.upgrades.UpgradeManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class UpgradeListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedInventory == null || clickedItem == null || clickedItem.getType().isAir()) {
            return;
        }
        String title = event.getView().getTitle();
        if (title.equals("Upgrade")) {
            event.setCancelled(true);
            String categoryName = clickedItem.getItemMeta().getDisplayName().toLowerCase();
            UpgradeCategory category = UpgradeManager.getCategory(categoryName);
            System.out.println("Clicked Item Display Name: " + categoryName);
            System.out.println("Category: " + category);
            if (category != null) {
                category.openMenu(player);
                player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1.0f, 1.0f);
            } else if (clickedItem.getItemMeta().getDisplayName().equals("§cSchließen")) {
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.BLOCK_BARREL_CLOSE, 1.0f, 1.0f);
            }
        } else {
            UpgradeCategory category = UpgradeManager.getCategory(title.toLowerCase());
            if (category != null) {
                event.setCancelled(true);
                if (event.getClick().isShiftClick() && event.getClick().isShiftClick()) {
                    // Handle shift-click action
                } else {
                    category.handleItemClick(player, clickedItem);
                }
            }
        }
    }
}

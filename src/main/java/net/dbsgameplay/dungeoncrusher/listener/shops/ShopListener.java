package net.dbsgameplay.dungeoncrusher.listener.shops;

import net.dbsgameplay.dungeoncrusher.Commands.interfaces.ShopCategory;
import net.dbsgameplay.dungeoncrusher.utils.shops.ShopManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedInventory == null || clickedItem == null || clickedItem.getType().isAir()) {
            return;
        }

        String title = event.getView().getTitle();
        if (title.equals("Shop")) {
            event.setCancelled(true);
            String categoryName = clickedItem.getItemMeta().getDisplayName().toLowerCase();
            ShopCategory category = ShopManager.getCategory(categoryName);
            if (category != null) {
                category.openMenu(player);
                player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1.0f, 1.0f);
            } else if (clickedItem.getItemMeta().getDisplayName().equals("§cSchließen")) {
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.BLOCK_BARREL_CLOSE, 1.0f, 1.0f);
            }
        } else {
            ShopCategory category = ShopManager.getCategory(title.toLowerCase());
            if (category != null) {
                event.setCancelled(true);
                if (event.getClick().isShiftClick() && event.getClick().isLeftClick()) {
                    category.handleShiftClick(player, clickedItem); // Shift + Linksklick
                } else {
                    category.handleItemClick(player, clickedItem); // Normaler Linksklick
                }
            }
        }
    }
}

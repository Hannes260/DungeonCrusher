package net.dbsgameplay.dungeoncrusher.listener.shops;

import net.dbsgameplay.dungeoncrusher.Commands.interfaces.ShopCategory;
import net.dbsgameplay.dungeoncrusher.utils.shops.ShopManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedInventory == null || clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        String title = event.getView().getTitle();
        if (title.equals("Shop")) {
            event.setCancelled(true);
            ShopCategory category = ShopManager.getCategory(clickedItem.getItemMeta().getDisplayName());
            if (category != null) {
                category.openMenu(player);
            }
        } else {
            ShopCategory category = ShopManager.getCategory(title);
            if (category != null) {
                event.setCancelled(true);
                category.handleItemClick(player, clickedItem);
            }
        }
    }
}

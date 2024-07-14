package net.dbsgameplay.dungeoncrusher.listener.shops;

import net.dbsgameplay.dungeoncrusher.Commands.interfaces.ShopCategory;
import net.dbsgameplay.dungeoncrusher.utils.shops.ShopManager;
import org.bukkit.Material;
import org.bukkit.Sound;
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
                    category.handleShiftClick(player, clickedItem); // Shift + Left Click
                } else {
                    category.handleItemClick(player, clickedItem); // Normal Left Click
                }
            } else {
                handleExchangeClick(player, clickedItem); // Handle exchange items
            }
        }
    }

    private void handleExchangeClick(Player player, ItemStack clickedItem) {
        ItemMeta meta = clickedItem.getItemMeta();
        if (meta == null) return;

        String displayName = meta.getDisplayName();
        switch (displayName) {
            case "20x Kupferbarren":
                exchangeItems(player, Material.COPPER_INGOT, 20, Material.RAW_COPPER, 100);
                break;
            case "200x Kupferbarren":
                exchangeItems(player, Material.COPPER_INGOT, 200, Material.RAW_COPPER, 1000);
                break;
            // Add more cases for other materials based on your list
        }
    }

    private void exchangeItems(Player player, Material from, int fromAmount, Material to, int toAmount) {
        Inventory playerInventory = player.getInventory();
        if (playerInventory.containsAtLeast(new ItemStack(from), fromAmount)) {
            playerInventory.removeItem(new ItemStack(from, fromAmount));
            playerInventory.addItem(new ItemStack(to, toAmount));
            player.sendMessage("Exchanged " + fromAmount + " " + from + " for " + toAmount + " " + to + ".");
        } else {
            player.sendMessage("You do not have enough " + from + " to exchange.");
        }
    }
}

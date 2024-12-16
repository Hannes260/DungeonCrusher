package net.dbsgameplay.dungeoncrusher.listener.Enchantments;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EnchantmentListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedInventory == null || clickedItem == null || clickedItem.getType().isAir()) {
            return;
        }

        if (clickedItem == null || clickedItem.getItemMeta() == null || clickedItem.getItemMeta().getDisplayName() == null) {
            return; // Kein gültiges Item, also nichts tun
        }
        String title = event.getView().getTitle();

        String DisplayName = "§f<shift:-8>%oraxen_enchantment%";
        DisplayName = PlaceholderAPI.setPlaceholders(player, DisplayName);
        if (DisplayName.equalsIgnoreCase(title)) {
            event.setCancelled(true);
        }
    }
}

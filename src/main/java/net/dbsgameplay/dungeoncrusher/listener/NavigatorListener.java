package net.dbsgameplay.dungeoncrusher.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class NavigatorListener implements Listener {
    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        HashMap<Integer, ItemStack> integerItemStackHashMap = new HashMap<>();
        Player player = event.getPlayer();
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("§e§lTeleporter")) {
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();
        }
    }
}

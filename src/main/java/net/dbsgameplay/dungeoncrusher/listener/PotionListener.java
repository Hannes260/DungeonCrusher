package net.dbsgameplay.dungeoncrusher.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class PotionListener implements Listener {
    @EventHandler
    public void onPlayerDrink(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item.getType() == Material.GLASS_BOTTLE) {
            player.getInventory().removeItem(item);
        }
    }

}

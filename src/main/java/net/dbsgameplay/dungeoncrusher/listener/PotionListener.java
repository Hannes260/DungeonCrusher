package net.dbsgameplay.dungeoncrusher.listener;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class PotionListener implements Listener {
    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        final Player player = e.getPlayer();

        if (e.getItem().getType() == Material.POTION) {
            Bukkit.getServer().getScheduler().runTaskLater(DungeonCrusher.getInstance(), new Runnable() {
                @Override
                public void run() {
                    // Entferne die leere Flasche aus dem Inventar
                    for (ItemStack item : player.getInventory().getContents()) {
                        if (item != null && item.getType() == Material.GLASS_BOTTLE) {
                            player.getInventory().remove(item);
                            break; // Nur eine leere Flasche entfernen
                        }
                    }
                }
            }, 1L);
        }
    }
}

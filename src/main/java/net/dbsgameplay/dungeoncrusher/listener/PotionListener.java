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
            Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(DungeonCrusher.getPlugin(), new Runnable() {
                public void run() {
                    player.setItemInHand(new ItemStack(Material.AIR));
                }
            }, 1L);
        }
    }
}

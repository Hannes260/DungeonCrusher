package net.dbsgameplay.dungeoncrusher.listener;

import net.dbsgameplay.dungeoncrusher.utils.ConfigManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MobkillListener implements Listener {
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getKiller() instanceof Player) {
            Player player = entity.getKiller();

            Inventory playerInventory = player.getInventory();
            if (playerInventory.firstEmpty() != -1) {
                for (ItemStack item : event.getDrops()) {
                    player.getInventory().addItem(item);
                }
                event.getDrops().clear();
            } else {
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.inventoryfull","",""));
            }
        }
    }
}

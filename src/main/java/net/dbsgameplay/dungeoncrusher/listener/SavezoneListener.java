package net.dbsgameplay.dungeoncrusher.listener;

import net.dbsgameplay.dungeoncrusher.utils.SavezoneManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;


public class SavezoneListener implements Listener {

    private final SavezoneManager savezoneManager;

    public SavezoneListener(SavezoneManager savezoneManager) {
        this.savezoneManager = savezoneManager;
    }
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (savezoneManager.isInAnySavezone(player)) {
                event.setCancelled(true);
            }
        }
    }
}

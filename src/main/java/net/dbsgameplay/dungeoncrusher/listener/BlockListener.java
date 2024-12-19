package net.dbsgameplay.dungeoncrusher.listener;

import net.dbsgameplay.dungeoncrusher.utils.manager.MarkingsManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockListener implements Listener {
    private final MarkingsManager markierungsManager;

    public BlockListener(MarkingsManager markierungsManager) {
        this.markierungsManager = markierungsManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (markierungsManager.isMarkierungsModusAktiv(player) && event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Location clickedLocation = event.getClickedBlock().getLocation();

            markierungsManager.handleMarkierungSetzen(player, clickedLocation);
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (markierungsManager.isMarkierungsModusAktiv(player)) {
            event.setCancelled(true);
        }
    }
}

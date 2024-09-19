package net.dbsgameplay.dungeoncrusher.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public class ChunkListener implements Listener {
    private final JavaPlugin plugin;
    private final Set<EntityType> mobTypesToKill;

    public ChunkListener(JavaPlugin plugin, Set<EntityType> mobTypesToKill) {
        this.plugin = plugin;
        this.mobTypesToKill = mobTypesToKill;
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        World world = event.getWorld();
        if (world != null) {
            for (Entity entity : event.getChunk().getEntities()) {
                if (!(entity instanceof Player)) {
                    if (mobTypesToKill.contains(entity.getType())) {
                        entity.remove();
                    }
                }
            }
        }
    }
}

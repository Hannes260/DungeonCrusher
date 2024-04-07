package net.dbsgameplay.dungeoncrusher.listener;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.LocationConfigManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
    public final DungeonCrusher dungeonCrusher;
    private MYSQLManager mysqlManager;
    private LocationConfigManager locationConfigManager;

    public DeathListener(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager, LocationConfigManager locationConfigManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.mysqlManager = mysqlManager;
        this.locationConfigManager = locationConfigManager;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        int currentdeaths = mysqlManager.getDeaths(player.getUniqueId().toString());
        mysqlManager.updateDeaths(player.getUniqueId().toString(), currentdeaths + 1);
        Location spawnLocation = locationConfigManager.getSpawn();
        player.teleport(spawnLocation);
    }

}

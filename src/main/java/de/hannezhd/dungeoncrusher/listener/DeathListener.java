package de.hannezhd.dungeoncrusher.listener;

import de.hannezhd.dungeoncrusher.DungeonCrusher;
import de.hannezhd.dungeoncrusher.sql.MYSQLManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
    public final DungeonCrusher dungeonCrusher;
    private MYSQLManager mysqlManager;

    public DeathListener(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.mysqlManager = mysqlManager;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        String player = String.valueOf(event.getEntity().getPlayer().getUniqueId());
        int currentdeaths = mysqlManager.getDeaths(player);
        mysqlManager.updateDeaths(player, currentdeaths + 1);
    }

}

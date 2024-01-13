package de.hannezhd.dungeoncrusher.listener;

import de.hannezhd.dungeoncrusher.DungeonCrusher;
import de.hannezhd.dungeoncrusher.sql.MYSQLManager;
import de.hannezhd.dungeoncrusher.utils.ScoreboardBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillListener implements Listener {
    public final DungeonCrusher dungeonCrusher;
    private MYSQLManager mysqlManager;
    private ScoreboardBuilder scoreboardBuilder;

    public KillListener(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.scoreboardBuilder = new ScoreboardBuilder(dungeonCrusher);
        this.mysqlManager = mysqlManager;
    }
    @EventHandler
    public void onentitydeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            String player = String.valueOf(event.getEntity().getKiller().getPlayer().getUniqueId());
            int currentKills = Integer.parseInt(mysqlManager.getKills(player));
            mysqlManager.updateKills(player, String.valueOf(currentKills + 1));
            scoreboardBuilder.updateKills(event.getEntity().getKiller().getPlayer());

        }
    }
}

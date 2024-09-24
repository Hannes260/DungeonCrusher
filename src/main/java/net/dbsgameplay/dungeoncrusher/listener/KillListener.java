package net.dbsgameplay.dungeoncrusher.listener; // Korrekter Paketname entsprechend dem Verzeichnis der Klasse

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
import org.bukkit.entity.EntityType;
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
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            String playeruuid = String.valueOf(event.getEntity().getKiller().getPlayer().getUniqueId());
            int currentKills = Integer.parseInt(mysqlManager.getKills(playeruuid));
            mysqlManager.updateKills(playeruuid, String.valueOf(currentKills + 1));
            scoreboardBuilder.updateKills(event.getEntity().getKiller().getPlayer());
            scoreboardBuilder.updateDungeonKills(event.getEntity().getKiller().getPlayer());

            for (EntityType entity : EntityType.values()) {
                String name = MobNameTranslator.translateToGerman(entity.getName());
                if (event.getEntityType.getName().equalsIgnoreCase(name)) {
                    mysqlManager.updateMobKillsForPlayer(playeruuid, name, 1);
                    int currentKillCount = mysqlManager.getPlayerMobKills(p.getUniqueId().toString(), name);
                    for (int i = 0; i != 22) {
                        if (currentKillCount == i*150) {
                            ErfolgeBuilders.reward(i*100, p);
                            break;
                        }
                    }
                break;
                }
            }
        }
    }
}

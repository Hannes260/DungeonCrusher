package net.dbsgameplay.dungeoncrusher.listener.Damage;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.MobNameTranslator;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MobkillListener implements Listener {
    private final MYSQLManager mysqlManager;
    private final DungeonCrusher dungeonCrusher;
    private ScoreboardBuilder scoreboardBuilder;

    public MobkillListener(MYSQLManager mysqlManager, DungeonCrusher dungeonCrusher) {
        this.mysqlManager = mysqlManager;
        this.scoreboardBuilder = new ScoreboardBuilder(dungeonCrusher);
        this.dungeonCrusher = dungeonCrusher;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

        LivingEntity entity = event.getEntity();
        if (entity.getKiller() instanceof Player) {
            Player player = entity.getKiller();

                for (ItemStack item : event.getDrops()) {
                    player.getInventory().addItem(item);
                }
                event.getDrops().clear();
                if (hasRequiredKills(player, "dungeon9")) {
                    mysqlManager.updateDungeonCount(String.valueOf(player.getUniqueId()), 9);
                    scoreboardBuilder.updateDungeon(player);
                }else if (hasRequiredKills(player, "dungeon8")) {
                    mysqlManager.updateDungeonCount(String.valueOf(player.getUniqueId()), 8);
                    scoreboardBuilder.updateDungeon(player);
                }else if (hasRequiredKills(player, "dungeon7")) {
                    mysqlManager.updateDungeonCount(String.valueOf(player.getUniqueId()), 7);
                    scoreboardBuilder.updateDungeon(player);
                }else if (hasRequiredKills(player, "dungeon6")) {
                    mysqlManager.updateDungeonCount(String.valueOf(player.getUniqueId()), 6);
                    scoreboardBuilder.updateDungeon(player);
                }else if (hasRequiredKills(player, "dungeon5")) {
                    mysqlManager.updateDungeonCount(String.valueOf(player.getUniqueId()), 5);
                    scoreboardBuilder.updateDungeon(player);
                }else if (hasRequiredKills(player, "dungeon4")) {
                    mysqlManager.updateDungeonCount(String.valueOf(player.getUniqueId()), 4);
                    scoreboardBuilder.updateDungeon(player);
                }else if (hasRequiredKills(player, "dungeon3")) {
                    mysqlManager.updateDungeonCount(String.valueOf(player.getUniqueId()), 3);
                    scoreboardBuilder.updateDungeon(player);
                } else if (hasRequiredKills(player, "dungeon2")) {
                    mysqlManager.updateDungeonCount(String.valueOf(player.getUniqueId()), 2);
                    scoreboardBuilder.updateDungeon(player);
                } else if (hasRequiredKills(player, "dungeon1")) {
                    mysqlManager.updateDungeonCount(String.valueOf(player.getUniqueId()), 1);
                    scoreboardBuilder.updateDungeon(player);
                }
        }
    }
    private boolean hasRequiredKills(Player player, String currentDungeonName) {
        LocationConfigManager locationConfigManager = new LocationConfigManager(DungeonCrusher.getInstance());
        // Prüfe, ob es sich um Dungeon 1 handelt
        if ("dungeon1".equals(currentDungeonName)) {
            // Dungeon 1 ist immer freigeschaltet
            return true;
        }
        // Hole den Namen des vorherigen Dungeons
        String previousDungeonName = locationConfigManager.getPreviousDungeon(currentDungeonName);
        if (previousDungeonName == null) {
            return false; // Kein vorheriger Dungeon gefunden
        }

        // Hole den Mob-Typ für den vorherigen Dungeon
        String previousDungeonMobType = String.valueOf(locationConfigManager.getMobTypesForDungeon(previousDungeonName));
        String germanMobType = MobNameTranslator.translateToGerman(previousDungeonMobType);

        // Hole die Kills des Spielers für den Mob-Typ des vorherigen Dungeons
        int kills = mysqlManager.getPlayerMobKills(String.valueOf(player.getUniqueId()), germanMobType);

        // Hole die erforderlichen Kills für den aktuellen Dungeon
        Integer requiredKills = locationConfigManager.getKills(currentDungeonName);
        if (requiredKills == null) {
            requiredKills = 0;
        }

        return kills >= requiredKills;
    }
}

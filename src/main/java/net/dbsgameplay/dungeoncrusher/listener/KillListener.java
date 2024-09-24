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


            if (event.getEntityType().equals(EntityType.SHEEP)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Schaf", 1);
                int currentKillCount = mysqlManager.getPlayerMobKills(p.getUniqueId().toString(), "Schaf");
                    for (int i = 0; i != 22) {
                        if (currentKillCount == i*150) {
                            ErfolgeBuilders.reward(i*100, p);
                        }
                    }
            }
            if (event.getEntityType().equals(EntityType.PIG)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Schweine", 1);
            }
            if (event.getEntityType().equals(EntityType.COW)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Kuh", 1);
            }
            if (event.getEntityType().equals(EntityType.HORSE)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Pferd", 1);
            }
            if (event.getEntityType().equals(EntityType.DONKEY)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Esel", 1);
            }
            if (event.getEntityType().equals(EntityType.CAMEL)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Kamel", 1);
            }
            if (event.getEntityType().equals(EntityType.VILLAGER)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Dorfbewohner", 1);
            }
            if (event.getEntityType().equals(EntityType.GOAT)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Ziegen", 1);
            }
            if (event.getEntityType().equals(EntityType.LLAMA)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Lama", 1);
            }
            if (event.getEntityType().equals(EntityType.MOOSHROOM)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Mooshroom", 1);
            }
            if (event.getEntityType().equals(EntityType.MULE)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Maultier", 1);
            }
            if (event.getEntityType().equals(EntityType.SNIFFER)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Schnüffler", 1);
            }
            if (event.getEntityType().equals(EntityType.PANDA)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Panda", 1);
            }
            if (event.getEntityType().equals(EntityType.TURTLE)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Schildkröten", 1);
            }
            if (event.getEntityType().equals(EntityType.OCELOT)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Ozelot", 1);
            }
            if (event.getEntityType().equals(EntityType.AXOLOTL)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Axolotl", 1);
            }
            if (event.getEntityType().equals(EntityType.FOX)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Fuchs", 1);
            }
            if (event.getEntityType().equals(EntityType.CAT)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Katzen    ", 1);
            }
            if (event.getEntityType().equals(EntityType.CHICKEN)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Huhn", 1);
            }
            if (event.getEntityType().equals(EntityType.FROG)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Frosch", 1);
            }
            if (event.getEntityType().equals(EntityType.RABBIT)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Kaninchen", 1);
            }
            if (event.getEntityType().equals(EntityType.SILVERFISH)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Silberfisch", 1);
            }
            if (event.getEntityType().equals(EntityType.VINDICATOR)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Diener", 1);
            }
            if (event.getEntityType().equals(EntityType.POLAR_BEAR)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Eisbären", 1);
            }
            if (event.getEntityType().equals(EntityType.ZOMBIE_HORSE)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Zombiepferd", 1);
            }
            if (event.getEntityType().equals(EntityType.WOLF)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Wolf", 1);
            }
            if (event.getEntityType().equals(EntityType.ZOMBIE_VILLAGER)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Zombiedorfbewohner", 1);
            }
            if (event.getEntityType().equals(EntityType.SNOW_GOLEM)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Schneegolem", 1);
            }
            if (event.getEntityType().equals(EntityType.SKELETON)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Skelett", 1);
            }
            if (event.getEntityType().equals(EntityType.DROWNED)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Ertrunkenen", 1);
            }
            if (event.getEntityType().equals(EntityType.HUSK)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Wüstenzombie", 1);
            }
            if (event.getEntityType().equals(EntityType.SPIDER)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Spinnen", 1);
            }
            if (event.getEntityType().equals(EntityType.ZOMBIE)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Zombie", 1);
            }
            if (event.getEntityType().equals(EntityType.STRAY)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Eiswanderer", 1);
            }
            if (event.getEntityType().equals(EntityType.CREEPER)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Creeper", 1);
            }
            if (event.getEntityType().equals(EntityType.CAVE_SPIDER)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Höhlenspinnen", 1);
            }
            if (event.getEntityType().equals(EntityType.ENDERMITE)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Endermiten", 1);
            }
            if (event.getEntityType().equals(EntityType.STRIDER)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Schreiter", 1);
            }
            if (event.getEntityType().equals(EntityType.BLAZE)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Lohen", 1);
            }
            if (event.getEntityType().equals(EntityType.SKELETON_HORSE)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Skelettpferde", 1);
            }
            if (event.getEntityType().equals(EntityType.WITCH)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Hexen", 1);
            }
            if (event.getEntityType().equals(EntityType.SLIME)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Schleim", 1);
            }
            if (event.getEntityType().equals(EntityType.MAGMA_CUBE)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Magmawürfel", 1);
            }
            if (event.getEntityType().equals(EntityType.ENDERMAN)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Enderman", 1);
            }
            if (event.getEntityType().equals(EntityType.PIGLIN)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Piglin", 1);
            }
            if (event.getEntityType().equals(EntityType.ZOMBIFIED_PIGLIN)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Zombifizierter_Piglin", 1);
            }
            if (event.getEntityType().equals(EntityType.PIGLIN_BRUTE)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Piglin_Barbaren", 1);
            }
            if (event.getEntityType().equals(EntityType.PILLAGER)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Plünderer", 1);
            }
            if (event.getEntityType().equals(EntityType.HOGLIN)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Hoglin", 1);
            }
            if (event.getEntityType().equals(EntityType.EVOKER)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Magier", 1);
            }
            if (event.getEntityType().equals(EntityType.GHAST)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Ghast", 1);
            }
            if (event.getEntityType().equals(EntityType.WITHER_SKELETON)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Wither_Skelett", 1);
            }
            if (event.getEntityType().equals(EntityType.ZOGLIN)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Zoglin", 1);
            }
            if (event.getEntityType().equals(EntityType.RAVAGER)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Verwüster", 1);
            }
            if (event.getEntityType().equals(EntityType.IRON_GOLEM)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Eisengolem", 1);
            }
            if (event.getEntityType().equals(EntityType.WARDEN)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Wärter", 1);
            }
            if (event.getEntityType().equals(EntityType.BOGGED)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Sumpfskelett", 1);
            }
            if (event.getEntityType().equals(EntityType.BREEZE)) {
                mysqlManager.updateMobKillsForPlayer(playeruuid, "Böe", 1);
            }
            if (event.getEntityType().equals(EntityType.ARMADILLO)) {
                    mysqlManager.updateMobKillsForPlayer(playeruuid, "Gürteltier", 1);
            }
        }
    }
}

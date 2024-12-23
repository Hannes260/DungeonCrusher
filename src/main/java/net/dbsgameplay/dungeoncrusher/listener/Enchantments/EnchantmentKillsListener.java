package net.dbsgameplay.dungeoncrusher.listener.Enchantments;

import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EnchantmentKillsListener implements Listener {
    private final MYSQLManager mysqlManager;

    public EnchantmentKillsListener(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        // Überprüfen, ob der Mörder ein Spieler ist und das getötete Entity ein Pferd ist
        if (!(event.getEntity().getKiller() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity().getKiller();
        if (event.getEntityType() == EntityType.HORSE) {
            // Hole die Anzahl der Pferde-Kills des Spielers aus der Datenbank
            String playerUUID = player.getUniqueId().toString();
            int currentKills = mysqlManager.getPlayerMobKills(playerUUID, "Pferd");  // Hole die Anzahl der Pferde-Kills

            // Wenn der Spieler weniger als 100 Kills hat, wird kein Drop vergeben
            if (currentKills >= 100) {
                // Chance auf einen Drop von 1:350
                double random = Math.random();
                if (random < 1.0 / 350.0) {
                    boolean hasWindklinge = mysqlManager.getFoundEnchantment(playerUUID, "windklinge");
                    if (!hasWindklinge) {
                        mysqlManager.updateFoundEnchantments(playerUUID, "windklinge", true);
                        player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.foundwindklinge","",""));
                    }
                }
            }
        }
    }
}

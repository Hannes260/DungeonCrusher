package de.hannezhd.dungeoncrusher.listener;

import de.hannezhd.dungeoncrusher.DungeonCrusher;
import de.hannezhd.dungeoncrusher.sql.MYSQLManager;
import de.hannezhd.dungeoncrusher.utils.ConfigManager;
import de.hannezhd.dungeoncrusher.utils.ScoreboardBuilder;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class CustomDropListener implements Listener {
    MYSQLManager mysqlManager;
    ScoreboardBuilder scoreboardBuilder;
    private final Random random;
    public CustomDropListener(DungeonCrusher dungeonCrusher,MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
        this.scoreboardBuilder = new ScoreboardBuilder(dungeonCrusher);
        this.random = new Random();
    }
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        List<ItemStack> drops = event.getDrops();
        Player player = event.getEntity().getKiller();
        if (event.getEntityType() == EntityType.ZOMBIE) {
            double random = Math.random();
            if (random < 0.3) {
                drops.add(new ItemStack(Material.DIAMOND,1));
            }
        } else if (event.getEntityType() == EntityType.FROG) {
            double random = Math.random();
            if (random < 0.05) {
                drops.add(new ItemStack(Material.COPPER_INGOT));
            }else if (random < 0.25) {
                drops.add(new ItemStack(Material.RAW_COPPER));
            } else if (random < 0.4) {
                double giveMoney = Math.round((random * 9.99 + 1) * 100.0) / 100.0;
                double newMoney;
                String currentMoney = mysqlManager.getBalance(player.getUniqueId().toString());
                currentMoney = currentMoney.replace(",", "");
                double money = Double.parseDouble(currentMoney);
                newMoney = money + giveMoney;
                String formattedMoney = String.format(Locale.ENGLISH, "%,.2f", newMoney);
                mysqlManager.updateBalance(String.valueOf(player.getUniqueId()), formattedMoney);
                scoreboardBuilder.updateMoney(player);
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.killedmobmoney", "%money%", String.valueOf(giveMoney)));
        } else if (random < 0.65) {
                drops.add(new ItemStack(Material.COAL));
            }
        }
    }
}

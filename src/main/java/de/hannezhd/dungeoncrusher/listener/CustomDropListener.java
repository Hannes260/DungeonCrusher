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
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class CustomDropListener implements Listener {
    MYSQLManager mysqlManager;
    ScoreboardBuilder scoreboardBuilder;
    public CustomDropListener(DungeonCrusher dungeonCrusher,MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
        this.scoreboardBuilder = new ScoreboardBuilder(dungeonCrusher);
    }
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        List<ItemStack> drops = event.getDrops();
        Player player = event.getEntity().getKiller();
        String playerUUID = player.getUniqueId().toString();
        if (event.getEntityType() == EntityType.ZOMBIE) {
            double random = Math.random();
            if (random < 0.3) {
                ItemStack diamond = new ItemStack(Material.DIAMOND, 1);

                int currentdiamond = mysqlManager.getItemAmount(playerUUID,"diamond");
                mysqlManager.updateItemAmount(playerUUID, diamond.getType().toString(), currentdiamond + diamond.getAmount());

                player.sendMessage(ConfigManager.getConfigMessage("message.additem","%item%",diamond.getType().toString()));
            }
        } else if (event.getEntityType() == EntityType.FROG) {
            double random = Math.random();
            if (random < 0.05) {
                ItemStack copperingot = new ItemStack(Material.COPPER_INGOT,1);

                int currentcopperingot = mysqlManager.getItemAmount(playerUUID, "copper_ingot");
                mysqlManager.updateItemAmount(playerUUID, copperingot.getType().toString(), currentcopperingot + copperingot.getAmount());

                ItemMeta copperingotmeta = copperingot.getItemMeta();
                copperingotmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(playerUUID, "copper_ingot"));
                copperingot.setItemMeta(copperingotmeta);
                player.getInventory().setItem(10, copperingot);

                player.sendMessage(ConfigManager.getConfigMessage("message.additem","%item%",copperingot.getType().toString()));
            }else if (random < 0.25) {
                ItemStack rawcopper = new ItemStack(Material.RAW_COPPER,1);

                int currentrawcopper = mysqlManager.getItemAmount(playerUUID, "raw_copper");
                mysqlManager.updateItemAmount(playerUUID, rawcopper.getType().toString(), currentrawcopper + rawcopper.getAmount());

                ItemStack rawcopperitem = new ItemStack(Material.RAW_COPPER);
                ItemMeta rawcoppermeta = rawcopperitem.getItemMeta();
                rawcoppermeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(playerUUID, "raw_copper"));
                rawcopperitem.setItemMeta(rawcoppermeta);
                player.getInventory().setItem(9, rawcopperitem);

                player.sendMessage(ConfigManager.getConfigMessage("message.additem","%item%", rawcopper.getType().toString()));
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

                player.sendMessage(ConfigManager.getConfigMessage("message.addmobkilledmoney", "%money%", String.valueOf(giveMoney)));
        } else if (random < 0.65) {
                ItemStack coal = new ItemStack(Material.COAL,1);

                int currentcoal = mysqlManager.getItemAmount(playerUUID, "coal");
                mysqlManager.updateItemAmount(playerUUID, coal.getType().toString(), currentcoal + coal.getAmount());

                ItemMeta coalmeta = coal.getItemMeta();
                coalmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "coal"));
                coal.setItemMeta(coalmeta);
                player.getInventory().setItem(22, coal);

                player.sendMessage(ConfigManager.getConfigMessage("message.additem","%item%",coal.getType().toString()));
            }
        }
    }
}

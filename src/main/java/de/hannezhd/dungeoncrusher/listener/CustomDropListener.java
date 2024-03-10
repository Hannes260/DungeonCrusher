package de.hannezhd.dungeoncrusher.listener;

import de.hannezhd.dungeoncrusher.DungeonCrusher;
import de.hannezhd.dungeoncrusher.sql.MYSQLManager;
import de.hannezhd.dungeoncrusher.utils.ConfigManager;
import de.hannezhd.dungeoncrusher.utils.ScoreboardBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Locale;

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
        if (player != null) {
            String playerUUID = player.getUniqueId().toString();
            if (event.getEntityType() == EntityType.FROG) {
                double random = Math.random();
                if (random < 0.10) {
                    ItemStack copperingot = new ItemStack(Material.COPPER_INGOT, 1);

                    int currentcopperingot = mysqlManager.getItemAmount(playerUUID, "copper_ingot");
                    mysqlManager.updateItemAmount(playerUUID, copperingot.getType().toString(), currentcopperingot + copperingot.getAmount());

                    ItemMeta copperingotmeta = copperingot.getItemMeta();
                    copperingotmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(playerUUID, "copper_ingot"));
                    copperingotmeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
                    copperingotmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    copperingot.setItemMeta(copperingotmeta);
                    player.getInventory().setItem(10, copperingot);

                    player.sendMessage(ConfigManager.getConfigMessage("message.additem", "%item%", copperingot.getType().toString()));
                } else if (random < 0.25) {
                    ItemStack rawcopper = new ItemStack(Material.RAW_COPPER, 1);

                    int currentrawcopper = mysqlManager.getItemAmount(playerUUID, "raw_copper");
                    mysqlManager.updateItemAmount(playerUUID, rawcopper.getType().toString(), currentrawcopper + rawcopper.getAmount());

                    ItemMeta rawcoppermeta = rawcopper.getItemMeta();
                    rawcoppermeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(playerUUID, "raw_copper"));
                    rawcoppermeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
                    rawcoppermeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    rawcopper.setItemMeta(rawcoppermeta);
                    player.getInventory().setItem(9, rawcopper);

                    player.sendMessage(ConfigManager.getConfigMessage("message.additem", "%item%", rawcopper.getType().toString()));
                } else if (random < 0.4) {
                 giveMoney(player, 1.0, 10.0);
                } else if (random < 0.60) {
                    giveItem(player, Material.COAL, 22, "coal");
                }
            }
        }
    }
    private void giveItem(Player player, Material material, Integer slot, String item) {
        ItemStack items = new ItemStack(material, 1);
        String playerUUID = player.getUniqueId().toString();
        int currentitem = mysqlManager.getItemAmount(playerUUID, item);
        mysqlManager.updateItemAmount(playerUUID, items.getType().toString(), currentitem + items.getAmount());

        ItemMeta itemsmeta = items.getItemMeta();
        itemsmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), item));
        itemsmeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
        itemsmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        items.setItemMeta(itemsmeta);
        player.getInventory().setItem(slot, items);
        player.sendMessage(ConfigManager.getConfigMessage("message.additem", "%item%", items.getType().toString()));
    }
    private void giveMoney(Player player, double minAmount, double maxAmount){
        double random = Math.random();
        double range = maxAmount - minAmount;
        double giveMoney = Math.round((random * range + minAmount) * 100.0) / 100.0;
        double newMoney;
        String currentMoney = mysqlManager.getBalance(player.getUniqueId().toString());
        currentMoney = currentMoney.replace(",", "");
        double money = Double.parseDouble(currentMoney);
        newMoney = money + giveMoney;
        String formattedMoney = String.format(Locale.ENGLISH, "%,.2f", newMoney);
        mysqlManager.updateBalance(String.valueOf(player.getUniqueId()), formattedMoney);
        scoreboardBuilder.updateMoney(player);

        player.sendMessage(ConfigManager.getConfigMessage("message.addmobkilledmoney", "%money%", String.valueOf(giveMoney)));
    }

}

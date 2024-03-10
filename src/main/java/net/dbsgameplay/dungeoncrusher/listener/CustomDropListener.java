package net.dbsgameplay.dungeoncrusher.listener;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
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
    private final ConfigManager configManager;
    public CustomDropListener(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager,ConfigManager configManager) {
        this.mysqlManager = mysqlManager;
        this.scoreboardBuilder = new ScoreboardBuilder(dungeonCrusher);
        this.configManager = configManager;
    }
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Player player = entity.getKiller();
        String entityName = entity.getCustomName();

        if (entityName == null || entityName.isEmpty()) {
            entityName = entity.getType().name();
        } else {
            entityName = extractMobName(entityName);
        }

        String mobNameLowerCase = entityName.toLowerCase();
        ConfigurationSection mobSection = configManager.getCustomDropsConfig().getConfigurationSection("customDrops.mobs." + mobNameLowerCase);
        System.out.println(mobNameLowerCase);
        System.out.println(mobSection);
        if (mobSection != null) {
            for (String dropName : mobSection.getKeys(false)) {
                ConfigurationSection dropSection = mobSection.getConfigurationSection(dropName);
                if (dropSection != null) {
                    double chance = dropSection.getDouble("chance");
                    if (Math.random() < chance) {
                        if (dropSection.isConfigurationSection("money")) {
                            ConfigurationSection moneySection = dropSection.getConfigurationSection("money");
                            double minAmount = moneySection.getDouble("minAmount");
                            double maxAmount = moneySection.getDouble("maxAmount");
                            giveMoney(player, minAmount, maxAmount);
                        } else {
                            String materialName = dropSection.getString("material");
                            Material material = Material.getMaterial(materialName);
                            int amount = dropSection.getInt("amount");
                            // Hier können Sie die Informationen für den Drop weiterverarbeiten
                            // z.B. Spieler das Item geben, Nachrichten senden usw.
                        }
                    }
                }
            }
        }
    }
        private void giveItem (Player player, Material material, Integer slot, String item){
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
        private void giveMoney (Player player,double minAmount, double maxAmount){
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
    private String extractMobName(String entityName) {
        // Entferne Leerzeichen und Farbcodes aus dem Mobs-Namen
        String cleanName = entityName.trim().replaceAll("§.", "");
        String[] parts = cleanName.split(" ", 2);
        if (parts.length > 0) {
            return parts[0];
        } else {
            return cleanName;
        }
    }
}
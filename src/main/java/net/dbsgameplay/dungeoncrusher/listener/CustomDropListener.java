package net.dbsgameplay.dungeoncrusher.listener;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.DropsConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
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
import java.util.Map;

public class CustomDropListener implements Listener {
    MYSQLManager mysqlManager;
    ScoreboardBuilder scoreboardBuilder;
    private final DropsConfigManager dropsConfigManager;

    public CustomDropListener(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager,DropsConfigManager dropsConfigManager) {
        this.mysqlManager = mysqlManager;
        this.scoreboardBuilder = new ScoreboardBuilder(dungeonCrusher);
        this.dropsConfigManager = dropsConfigManager;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!(event.getEntity().getKiller() instanceof Player)) {
            return;
        }
        List<ItemStack> drops = event.getDrops();
        drops.clear();
        Player player = (Player) event.getEntity().getKiller();
        EntityType entityType = event.getEntityType();
        String mobName = entityType.name();

        // Laden der Drops aus dem DropsConfigManager
        Map<String, Double> mobDrops = dropsConfigManager.loadMobDrops(mobName);

        // Verarbeiten der geladenen Drops
        for (Map.Entry<String, Double> entry : mobDrops.entrySet()) {
            String materialName = entry.getKey();
            double dropChance = entry.getValue();
            double random = Math.random();
            System.out.println(mobDrops);
            if (random < dropChance) {
                if (materialName.equalsIgnoreCase("money")) {
                    // Geld-Drop
                    double[] moneyDropRange = dropsConfigManager.loadMoneyDropRange(mobName);
                    double minAmount = moneyDropRange[0];
                    double maxAmount = moneyDropRange[1];
                    double moneyDropChance = dropsConfigManager.loadMoneyDropChance(mobName);
                    if (minAmount > 0 && maxAmount > 0 && random < moneyDropChance) {
                        giveMoney(player, minAmount, maxAmount);
                    }
                } else {
                    // Item drop
                    giveItem(player, Material.valueOf(materialName), getItemSlot(Material.valueOf(materialName)), materialName);
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

    private void giveMoney(Player player, double minAmount, double maxAmount) {
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
    private String getitemname(Material material) {
        switch (material) {
            case COBBLESTONE:
                 return "cobblestone";
            case STONE:
                return "stone";
            case RAW_COPPER:
                return "raw_copper";
            case COPPER_INGOT:
                return "copper_ingot";
            case RAW_IRON:
                return "raw_iron";
            case IRON_INGOT:
                return "iron_ingot";
            case RAW_GOLD:
                return "raw_gold";
            case GOLD_INGOT:
                return "gold_ingot";
            case DIAMOND:
                return "diamond";
            case DIAMOND_ORE:
                return "diamond_ore";
            case NETHERITE_SCRAP:
                return "netherite_scrap";
            case NETHERITE_INGOT:
                return "netherite_ingot";
            case COAL:
                return "coal";
        }
        return null;
    }
    private int getItemSlot(Material material) {
        switch (material) {
            case COBBLESTONE:
                return 17;
            case STONE:
                return 16;
            case RAW_COPPER:
                return 9;
            case COPPER_INGOT:
                return 10;
            case RAW_IRON:
                return 27;
            case IRON_INGOT:
                return 28;
            case RAW_GOLD:
                return 18;
            case GOLD_INGOT:
                return 19;
            case DIAMOND:
                return 25;
            case DIAMOND_ORE:
                return 26;
            case NETHERITE_SCRAP:
                return 35;
            case NETHERITE_INGOT:
                return 34;
            case COAL:
                return  22;
        }
        return 0;
    }
}
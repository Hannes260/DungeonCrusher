package net.dbsgameplay.dungeoncrusher.listener.Damage;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.Upgrades.SwordCategory;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.DropsConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Manager.HologramManager;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
import net.dbsgameplay.dungeoncrusher.utils.quests.Daily;
import net.dbsgameplay.dungeoncrusher.utils.quests.Monthly;
import net.dbsgameplay.dungeoncrusher.utils.quests.Weekly;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CustomDropListener implements Listener {
    MYSQLManager mysqlManager;
    ScoreboardBuilder scoreboardBuilder;
    DungeonCrusher dungeonCrusher;
    private final DropsConfigManager dropsConfigManager;
    public CustomDropListener(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager, DropsConfigManager dropsConfigManager) {
        this.mysqlManager = mysqlManager;
        this.scoreboardBuilder = new ScoreboardBuilder(dungeonCrusher);
        this.dropsConfigManager = dropsConfigManager;
        this.dungeonCrusher = dungeonCrusher;
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

        // Laden der Geld-Drops
        double moneyDropChance = dropsConfigManager.loadMoneyDropChance(mobName);
        double random = Math.random();
        if (random < moneyDropChance) {
            double[] moneyDropRange = dropsConfigManager.loadMoneyDropRange(mobName);
            double minMoneyAmount = moneyDropRange[0];
            double maxMoneyAmount = moneyDropRange[1];
            if (minMoneyAmount > 0 && maxMoneyAmount > 0) {
                giveMoney(player, minMoneyAmount, maxMoneyAmount, event);
            }
        }

        // Laden der Gegenstands-Drops
        Map<String, Double> itemDrops = dropsConfigManager.loadMobItemDrops(mobName);
        for (Map.Entry<String, Double> entry : itemDrops.entrySet()) {
            String itemName = entry.getKey();
            double dropChance = entry.getValue();
            random = Math.random();
            if (random < dropChance) {
                giveItem(player, Material.valueOf(itemName), getItemSlot(Material.valueOf(itemName)), itemName, event);
            }
        }
    }
    private void giveItem(Player player, Material material, Integer slot, String item, EntityDeathEvent event) {
        // Async Task für MySQL und Berechnungen
        Bukkit.getScheduler().runTaskAsynchronously(dungeonCrusher, () -> {
            int[] itemDropRange = dropsConfigManager.loadItemDropRange(event.getEntityType().name(), material.name());
            int minAmount = itemDropRange[0];
            int maxAmount = itemDropRange[1];

            int amountToDrop = (int) (Math.random() * (maxAmount - minAmount + 1)) + minAmount;
            if (amountToDrop <= 0) return; // Wenn die Menge <= 0 ist, wird kein Gegenstand fallen gelassen

            String playerUUID = player.getUniqueId().toString();
            int currentItem = mysqlManager.getItemAmount(playerUUID, item);
            mysqlManager.updateItemAmount(playerUUID, material.name(), currentItem + amountToDrop);
            // Quests nach dem Hinzufügen der Items abschließen
            Daily.doQuest(player, Daily.GetQuestList);
            Weekly.doQuest(player, Weekly.GetQuestList);
            Monthly.doQuest(player, Monthly.GetQuestList);// Anzahl aktualisieren
            SwordCategory swordCategory = new SwordCategory(mysqlManager);
            int currentLevel = mysqlManager.getSwordLevel(player.getUniqueId().toString());
            if (swordCategory.hasEnoughResourcesForVisuals(player, currentLevel)) {
                new BukkitRunnable() {
                int timeLeft = 3 * 20; // 20 Ticks pro Sekunde

                @Override
                public void run() {
                    if (timeLeft > 0) {
                        // Sende die Nachricht über der Hotbar direkt mit der Player-Methode
                        player.sendActionBar("§aDu kannst dein Schwert upgraden benutze §6[/upgrade]!");
                        timeLeft -= 20; // Reduziere die verbleibende Zeit um 1 Sekunde (20 Ticks)
                    } else {
                        // Stoppe das Wiederholen, wenn die Zeit abgelaufen ist
                        this.cancel();
                    }
                }
            }.runTaskTimer(DungeonCrusher.getInstance(), 0L, 20L);
            }
            // Sync Task für Inventaroperationen und Hologramme
            Bukkit.getScheduler().runTask(dungeonCrusher, () -> {
                ItemStack items = new ItemStack(material, 1);
                ItemMeta itemMeta = items.getItemMeta();
                itemMeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(playerUUID, item));
                itemMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                items.setItemMeta(itemMeta);
                player.getInventory().setItem(slot, items);

                String itemName = translateMaterialName(material);
                player.sendMessage(ConfigManager.getConfigMessage("message.additem", "%item%", itemName, "%amount%", String.valueOf(amountToDrop)));

                Location hologramLocation = event.getEntity().getLocation();
                HologramManager.spawnItemHologram(hologramLocation, itemName);
            });
        });
    }
    public static String translateMaterialName(Material material) {
        switch (material) {
            case COBBLESTONE:
                return "Bruchstein";
            case STONE:
                return "Stein";
            case RAW_COPPER:
                return "RohKupfer";
            case COPPER_INGOT:
                return "Kupferbarren";
            case RAW_IRON:
                return "Roheisen";
            case IRON_INGOT:
                return "Eisenbarren";
            case RAW_GOLD:
                return "RohGold";
            case GOLD_INGOT:
                return "Goldbarren";
            case DIAMOND:
                return "Diamant";
            case DIAMOND_ORE:
                return "Diamanterz";
            case NETHERITE_SCRAP:
                return "Netherite-Schrott";
            case NETHERITE_INGOT:
                return "Netheritebarren";
            case COAL:
                return "Kohle";
            default:
                return material.name();
        }
    }
    private void giveMoney(Player player, double minAmount, double maxAmount, EntityDeathEvent event) {
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
        Location hologramLocation = event.getEntity().getLocation(); // Position des getöteten Mobs
        HologramManager.spawnItemHologram(hologramLocation, String.valueOf(giveMoney) + " €");
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
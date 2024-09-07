package net.dbsgameplay.dungeoncrusher.enums.Shop;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.Commands.interfaces.ShopCategory;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.QuestBuilder;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
import net.dbsgameplay.dungeoncrusher.utils.shops.ShopManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class PotionCategory implements ShopCategory {
    private final MYSQLManager mysqlManager;
    private final ScoreboardBuilder scoreboardBuilder;
    private final Map<Integer, PotionCategory.ShopItem> items;

    public PotionCategory(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
        this.scoreboardBuilder = new ScoreboardBuilder(DungeonCrusher.getInstance());
        this.items = new HashMap<>();

        // Speed 1 Trank
        List<String> speed1lore = Arrays.asList("§7Anzahl: §61", "§7Preis: §6160€", "§7Dauer: §61:30min");
        PotionEffect speed1Effect = new PotionEffect(PotionEffectType.SPEED, 5 * 60 * 20, 0);
        items.put(11, new PotionCategory.ShopItem("Schnelligkeit I", Material.POTION, 160, speed1lore, speed1Effect));

        // Speed 2 Trank
        List<String> speed2lore = Arrays.asList("§7Anzahl: §61", "§7Preis: §6200€", "§7Dauer: §63:00min");
        PotionEffect speedEffect = new PotionEffect(PotionEffectType.SPEED, 3 * 60 * 20, 1); // 3 Minuten Dauer, Stärke 1
        items.put(29, new PotionCategory.ShopItem("Schnelligkeit II", Material.POTION, 200, speed2lore, speedEffect));

        // Stärke 1 Trank
        List<String> strength1lore = Arrays.asList("§7Anzahl: §61", "§7Preis: §6290€", "§7Dauer: §63:00min");
        PotionEffect strength1Effect = new PotionEffect(PotionEffectType.STRENGTH, 3 * 60 * 20, 0); // 1 Minute 3 Sekunden Dauer, Stärke 2
        items.put(13, new PotionCategory.ShopItem("Stärke I", Material.POTION, 290, strength1lore, strength1Effect));

        // Stärke 2 Trank
        List<String> strength2lore = Arrays.asList("§7Anzahl: §61", "§7Preis: §6500€", "§7Dauer: §61:30min");
        PotionEffect strengthEffect = new PotionEffect(PotionEffectType.STRENGTH, 1800, 1); // 1 Minute 3 Sekunden Dauer, Stärke 2
        items.put(31, new PotionCategory.ShopItem("Stärke II", Material.POTION, 500, strength2lore, strengthEffect));

        // Resistance 1 Trank
        List<String> resistancelore = Arrays.asList("§7Anzahl: §61", "§7Preis: §6780€", "§7Dauer: §65:00min");
        PotionEffect resistanceEffect = new PotionEffect(PotionEffectType.RESISTANCE, 5 * 60 * 20, 0);
        items.put(15, new PotionCategory.ShopItem("Resistanz I", Material.POTION, 780, resistancelore, resistanceEffect));

        // Resistance 2 Trank
        List<String> resistance2lore = Arrays.asList("§7Anzahl: §61", "§7Preis: §6950€", "§7Dauer: §63:00min");
        PotionEffect resistance2Effect = new PotionEffect(PotionEffectType.RESISTANCE, 3 * 60 * 20, 1);
        items.put(33, new PotionCategory.ShopItem("Resistanz II", Material.POTION, 950, resistance2lore, resistance2Effect));
    }

    @Override
    public void openMenu(Player player) {
        String displayNamePotion = "§f<shift:-8>%oraxen_potion_gui%";
        displayNamePotion = PlaceholderAPI.setPlaceholders(player, displayNamePotion);

        Inventory inv = Bukkit.createInventory(null, 9 * 6, displayNamePotion);

        for (int slot : items.keySet()) {
            PotionCategory.ShopItem shopItem = items.get(slot);
            ItemStack itemStack = new ItemStack(shopItem.material);
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(shopItem.displayName);
            meta.setLore(shopItem.lore);
            itemStack.setItemMeta(meta);

            inv.setItem(slot, itemStack);
        }

        addBackButton(player, inv);
        player.openInventory(inv);
    }

    @Override
    public void handleItemClick(Player player, ItemStack clickedItem) {
        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        ItemMeta clickedMeta = clickedItem.getItemMeta();
        if (clickedMeta == null) {
            return;
        }

        String clickedDisplayName = clickedMeta.getDisplayName();

        for (int slot : items.keySet()) {
            PotionCategory.ShopItem shopItem = items.get(slot);
            if (shopItem.displayName.equals(clickedDisplayName)) {
                buyItem(player, shopItem.material, 1, shopItem.price, clickedDisplayName);
                return;
            }
        }

        if ("§7➢ Zurück".equals(clickedDisplayName)) {
            ShopManager.openMainShopMenu(player, mysqlManager);
        }
    }

    @Override
    public void handleShiftClick(Player player, ItemStack clickedItem) {
    }

    private void buyItem(Player p, Material material, int amount, double pricePerUnit, String displayName) {
        double totalPrice = amount * pricePerUnit;
        if (removeMoney(p, totalPrice)) {
            for (int i = 0; i < amount; i++) {
                PotionCategory.ShopItem shopItem = findShopItemByDisplayName(displayName);
                if (shopItem == null) {
                    p.sendMessage(ConfigManager.getPrefix() + "Unbekannter Trank.");
                    return;
                }

                // Erstelle den Trank
                ItemStack potion = new ItemStack(Material.POTION);
                PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
                if (potionMeta != null) {
                    // Entferne alle vorhandenen Effekte und füge den gewünschten Effekt hinzu
                    potionMeta.clearCustomEffects();
                    potionMeta.addCustomEffect(shopItem.getEffect(), true);


                    // Setze den Namen und die Lore des Tranks
                    potion.setItemMeta(potionMeta);

                    // Füge den Trank zum Inventar des Spielers hinzu
                    Inventory playerInventory = p.getInventory();
                    Map<Integer, ItemStack> leftOverItems = playerInventory.addItem(potion);

                    //QuestCheck
                    QuestBuilder.checkIfDailyIsDone("daily", "d5", p, 3);

                    if (!leftOverItems.isEmpty()) {
                        p.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.inventoryfull", "", ""));
                        addMoney(p, totalPrice);
                        return;
                    }
                } else {
                    p.sendMessage(ConfigManager.getPrefix() + "Fehler beim Erstellen des Trankes.");
                    return;
                }
            }
            p.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.buyshop", "%amount%", String.valueOf(amount), "%material%", material.toString()));
        } else {
            p.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughmoney", "%price%", String.valueOf(totalPrice)));
        }
    }

    private void addBackButton(Player player, Inventory inventory) {
        ItemStack backhead = new ItemStack(Material.PAPER);
        ItemMeta headmeta = backhead.getItemMeta();
        headmeta.setDisplayName("§7➢ Zurück");
        headmeta.setCustomModelData(100);
        backhead.setItemMeta(headmeta);
        inventory.setItem(45, backhead);
    }

    private boolean removeMoney(Player p, double amount) {
        double currentMoney;
        try {
            String currentMoneyString = mysqlManager.getBalance(p.getUniqueId().toString());
            currentMoneyString = currentMoneyString.replace(",", "");
            currentMoney = Double.parseDouble(currentMoneyString);
        } catch (NumberFormatException e) {
            return false;
        }

        double newMoney = currentMoney - amount;
        if (newMoney < 0) {
            return false;
        }

        String formattedMoney = String.format(Locale.ENGLISH, "%,.2f", newMoney);
        mysqlManager.updateBalance(p.getUniqueId().toString(), formattedMoney);
        scoreboardBuilder.updateMoney(p);

        return true;
    }

    private void addMoney(Player player, double amount) {
        String playerName = player.getUniqueId().toString();
        double currentMoney;
        try {
            String currentMoneyString = mysqlManager.getBalance(playerName);
            currentMoneyString = currentMoneyString.replace(",", "");
            currentMoney = Double.parseDouble(currentMoneyString);
        } catch (NumberFormatException e) {
            return;
        }

        double newMoney = currentMoney + amount;
        String formattedMoney = String.format(Locale.ENGLISH, "%,.2f", newMoney);
        mysqlManager.updateBalance(playerName, formattedMoney);
        scoreboardBuilder.updateMoney(player);
    }

    private PotionCategory.ShopItem findShopItemByDisplayName(String displayName) {
        for (PotionCategory.ShopItem shopItem : items.values()) {
            if (shopItem.displayName.equals(displayName)) {
                return shopItem;
            }
        }
        return null;
    }

    private static class ShopItem {
        private final String displayName;
        private final Material material;
        private final double price;
        private final List<String> lore;
        private final PotionEffect effect;  // Hinzugefügt: Trankeffekt

        private ShopItem(String displayName, Material material, double price, List<String> lore, PotionEffect effect) {
            this.displayName = displayName;
            this.material = material;
            this.price = price;
            this.lore = lore;
            this.effect = effect;  // Setze den Effekt
        }

        public PotionEffect getEffect() {
            return effect;
        }
    }
}

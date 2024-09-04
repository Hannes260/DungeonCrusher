package net.dbsgameplay.dungeoncrusher.enums.Shop;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.Commands.interfaces.ShopCategory;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.QuestBuilder;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
import net.dbsgameplay.dungeoncrusher.utils.TexturedHeads;
import net.dbsgameplay.dungeoncrusher.utils.shops.ShopManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

import java.util.*;

public class FoodCategory implements ShopCategory {
    private final MYSQLManager mysqlManager;
    private final ScoreboardBuilder scoreboardBuilder;
    private final Map<Integer, ShopItem> items;

    public FoodCategory(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;

        this.scoreboardBuilder = new ScoreboardBuilder(DungeonCrusher.getInstance());

        this.items = new HashMap<>();
        items.put(42, new ShopItem("Apfel", Material.APPLE, 25, Arrays.asList("§7Anzahl: §61  §7Preis: §625€", "§7Anzahl: §664  §7Preis: §61600€")));
        items.put(2, new ShopItem("Brot", Material.BREAD, 50, Arrays.asList("§7Anzahl: §61  §7Preis: §650€", "§7Anzahl: §664  §7Preis: §63200€")));
        items.put(6, new ShopItem("Gebackene Kartoffel", Material.BAKED_POTATO, 35, Arrays.asList("§7Anzahl: §61  §7Preis: §635€", "§7Anzahl: §664  §7Preis: §62240€")));
        items.put(22, new ShopItem("Verzauberter Gold Apfel", Material.ENCHANTED_GOLDEN_APPLE, 1000, Arrays.asList("§7Anzahl: §61  §7Preis: §61000€", "§7Anzahl: §664  §7Preis: §664000€")));
        items.put(38, new ShopItem("Goldener Apfel", Material.GOLDEN_APPLE, 250, Arrays.asList("§7Anzahl: §61  §7Preis: §6250€", "§7Anzahl: §664  §7Preis: §616000€")));
    }

    @Override
    public void openMenu(Player player) {
        // Setze den Titel für das Inventar mit dem Oraxen-Placeholder
        String displayNameFood = "§f<shift:-8>%oraxen_food_gui%";
        displayNameFood = PlaceholderAPI.setPlaceholders(player, displayNameFood);

        Inventory inv = Bukkit.createInventory(null, 9 * 6, displayNameFood);

        // Fülle das Inventar mit Items aus der HashMap
        for (int slot : items.keySet()) {
            ShopItem shopItem = items.get(slot);
            ItemStack itemStack = new ItemStack(shopItem.material);
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(shopItem.displayName);
            meta.setLore(shopItem.lore);
            itemStack.setItemMeta(meta);

            inv.setItem(slot, itemStack); // Setze das Item in den spezifischen Slot
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
            ShopItem shopItem = items.get(slot);
            if (shopItem.displayName.equals(clickedDisplayName)) {
                buyItem(player, shopItem.material, 1, shopItem.price);
                return;
            }
        }

        if ("§7➢ Zurück".equals(clickedDisplayName)) {
            ShopManager.openMainShopMenu(player, mysqlManager);
        }
    }

    @Override
    public void handleShiftClick(Player player, ItemStack clickedItem) {
        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        ItemMeta clickedMeta = clickedItem.getItemMeta();
        if (clickedMeta == null) {
            return;
        }

        String clickedDisplayName = clickedMeta.getDisplayName();

        for (int slot : items.keySet()) {
            ShopItem shopItem = items.get(slot);
            if (shopItem.displayName.equals(clickedDisplayName)) {
                buyItem(player, shopItem.material, 64, shopItem.price);
                return;
            }
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

    private void buyItem(Player p, Material material, int amount, double pricePerUnit) {
        double totalPrice = amount*pricePerUnit;
        if (removeMoney(p, totalPrice)) {
            ItemStack item = new ItemStack(material, amount);
            Inventory playerInventory = p.getInventory();
            HashMap<Integer, ItemStack> leftOverItems = playerInventory.addItem(item);
            if (leftOverItems.isEmpty()) {
                p.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.buyshop", "%amount%", String.valueOf(amount), "%material%", material.toString()));

                if (mysqlManager.getTutorialQuest(p.getUniqueId().toString()).equalsIgnoreCase("t2")) {
                    mysqlManager.updateTutorialQuest(p.getUniqueId().toString(), "t1");
                    BossBar bossBar1 = QuestBuilder.bossBar;
                    bossBar1.setTitle(QuestBuilder.tutorialQuestMap.get("t1"));
                    bossBar1.addPlayer(p);

                    if (mysqlManager.getOrginQuest("daily").equalsIgnoreCase("d6")) {
                        FileConfiguration cfg = DungeonCrusher.getInstance().getConfig();

                        if (cfg.contains("quest." + p.getUniqueId().toString() + "." + "daily")) {
                            cfg.set("quest." + p.getUniqueId().toString() + "." + "daily", cfg.getInt("quest." + p.getUniqueId().toString() + "." + "daily")+1);
                        }else {
                            cfg.set("quest." + p.getUniqueId().toString() + "." + "daily", 1);
                        }

                        if (cfg.getInt("quest." + p.getUniqueId().toString() + "." + "daily") == 20) {
                            cfg.set("quest." + p.getUniqueId().toString() + "." + "daily", null);
                            mysqlManager.updatePlayerQuest("daily", true, p.getUniqueId().toString());

                            Random rdm = new Random();
                            mysqlManager.updateBalance(p.getUniqueId().toString(), mysqlManager.getBalance(p.getUniqueId().toString() + rdm.nextInt(90, 151)));
                        }
                    }
                }
            } else {
                p.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.inventoryfull", "", ""));
                addMoney(p, totalPrice);
            }
        } else {
            p.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughmoney", "%price%", String.valueOf(totalPrice)));
        }
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

    private static class ShopItem {
        private final String displayName;
        private final Material material;
        private final double price;
        private final List<String> lore;

        private ShopItem(String displayName, Material material, double price, List<String> lore) {
            this.displayName = displayName;
            this.material = material;
            this.price = price;
            this.lore = lore;
        }
    }
}
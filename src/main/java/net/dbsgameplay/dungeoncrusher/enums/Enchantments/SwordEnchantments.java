package net.dbsgameplay.dungeoncrusher.enums.Enchantments;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.Upgrades.SwordCategory;
import net.dbsgameplay.dungeoncrusher.interfaces.EnchantmentCategory;
import net.dbsgameplay.dungeoncrusher.interfaces.UpgradeCategory;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class SwordEnchantments implements EnchantmentCategory {
    private final MYSQLManager mysqlManager;
    private final ScoreboardBuilder scoreboardBuilder;
    private final Map<Integer, EnchantmentItem> items;

    public SwordEnchantments(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;

        this.scoreboardBuilder = new ScoreboardBuilder(DungeonCrusher.getInstance());

        this.items = new HashMap<>();
        items.put(0, new EnchantmentItem("§7§kFesselschlag", Material.BOOK, 500, Arrays.asList(""), "fesselschlag","Dein Gegner wird verlangsamt", "wenn du ihn schlägst."));
        items.put(9, new EnchantmentItem("§7§kWindklinge", Material.BOOK, 500, Arrays.asList(""), "windklinge", "Erhöht deine Geschwindigkeit", "wenn du das Schwert in der Hand hältst."));
        items.put(18, new EnchantmentItem("§7§kGifthieb", Material.BOOK, 500, Arrays.asList(""), "gifthieb","Test", "Test"));
        items.put(27, new EnchantmentItem("§7§kSeelenentzug", Material.BOOK, 500, Arrays.asList(""), "seelenentzug","Test", "Test"));
        items.put(36, new EnchantmentItem("§7§kWutentbrannt", Material.BOOK, 500, Arrays.asList(""), "wutentbrannt","Test", "Test"));
    }

    private static final Map<String, Map<Integer, Map<String, Integer>>> enchantmentRequirements = new HashMap<>();

    static {
        // Anforderungen für Fesselschlag: Level -> Mob-Typ -> Anzahl Kills
        Map<Integer, Map<String, Integer>> fesselschlag = new HashMap<>();
        fesselschlag.put(1, Map.of("Schaf", 50, "Schweine", 25));
        fesselschlag.put(2, Map.of("Schaf", 90, "Schweine", 45));
        fesselschlag.put(3, Map.of("Schaf", 180, "Schweine", 90, "Kuh", 35));
        enchantmentRequirements.put("fesselschlag", fesselschlag);

        Map<Integer, Map<String, Integer>> windklinge = new HashMap<>();
        windklinge.put(1, Map.of("Schwein", 75, "Kuh", 40, "Pferd", 15));
        windklinge.put(2, Map.of("Schwein", 135, "Kuh", 70, "Pferd", 30));
        windklinge.put(3, Map.of("Schwein", 270, "Kuh", 140, "Pferd", 60));
        windklinge.put(4, Map.of("Schwein", 590, "Kuh", 305, "Pferd", 130, "Esel", 40));
        enchantmentRequirements.put("windklinge", windklinge);
    }
    private static final Map<String, Map<Integer, EnchantmentCost>> enchantmentCosts = new HashMap<>();

    static {
        // Define costs for Fesselschlag
        Map<Integer, EnchantmentCost> fesselschlagCosts = new HashMap<>();
        fesselschlagCosts.put(1, new EnchantmentCost(12000, 500));
        fesselschlagCosts.put(2, new EnchantmentCost(21000, 900));
        fesselschlagCosts.put(3, new EnchantmentCost(46000, 1800));
        enchantmentCosts.put("fesselschlag", fesselschlagCosts);

        // Define costs for Windklinge
        Map<Integer, EnchantmentCost> windklingeCosts = new HashMap<>();
        windklingeCosts.put(1, new EnchantmentCost(15000, 800));
        windklingeCosts.put(2, new EnchantmentCost(27000, 1450));
        windklingeCosts.put(3, new EnchantmentCost(54000, 2900));
        windklingeCosts.put(4, new EnchantmentCost(118000, 6400));
        enchantmentCosts.put("windklinge", windklingeCosts);
    }
    @Override
    public void openMenu(Player player) {
        String displayName = "§f<shift:-8>%nexo_enchantment%";
        displayName = PlaceholderAPI.setPlaceholders(player, displayName);
        Inventory inv = Bukkit.createInventory(null, 9 * 6, displayName);
        String playerUUID = player.getUniqueId().toString();

        for (Map.Entry<Integer, EnchantmentItem> entry : items.entrySet()) {
            int slot = entry.getKey();
            EnchantmentItem enchantmentItem = entry.getValue();

            // ItemStack erstellen und Meta setzen
            ItemStack itemStack = new ItemStack(enchantmentItem.material);
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(enchantmentItem.displayName);
            meta.setCustomModelData(enchantmentItem.modeldata);
            itemStack.setItemMeta(meta);

            // Setze das Item in das Inventar
            inv.setItem(slot, itemStack);

            // Zeige das Verzauberungs-Item dynamisch an
            showEnchantmentItem(player, inv, playerUUID, enchantmentItem.getEnchantmentName(), slot, enchantmentItem.description, enchantmentItem.description2);
        }
        player.openInventory(inv);
    }

    private void showEnchantmentItem(Player player, Inventory inv, String playerUUID, String enchantmentName, int slot, String discription, String discription2) {
        boolean hasEnchantment = mysqlManager.getFoundEnchantment(playerUUID, enchantmentName);
        int level = mysqlManager.getlevelEnchantment(playerUUID, enchantmentName);
        int erforschtlvl = mysqlManager.geterforchtEnchantmentlvl(playerUUID, enchantmentName);
        boolean equipedenchantment = mysqlManager.getEquipedEnchantment(playerUUID, enchantmentName);

        if (equipedenchantment) {
            // Wenn das Enchantment ausgerüstet ist, zeige das verzauberte Buch
            ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§e§n" + capitalize(enchantmentName) + " §r§e(lvl " + level + "§e/5)");

            List<String> lore = new ArrayList<>();
            lore.add("§6" + discription);
            lore.add("§6" + discription2);
            lore.add("§4");

            if (level < 5) {
                lore.add("§eErforschen:");
                Map<String, Integer> requirements = getKillRequirements(enchantmentName, erforschtlvl + 1);
                for (String mobType : requirements.keySet()) {
                    int requiredKills = requirements.get(mobType);
                    int currentKills = getPlayerKills(playerUUID, mobType);
                    lore.add("§7 - " + mobType + ": §f" + currentKills + "§7 / §e" + requiredKills);
                }
            } else {
                lore.add("§aMaximales Level Erreicht!");
            }
            lore.add("§eLinksklick §a>> Herstellen");
            lore.add("§eRechtsklick §a>> Ausrüsten/Ablegen");

            // PersistentData hinzufügen
            NamespacedKey key = new NamespacedKey(DungeonCrusher.getInstance(), "enchantment");
            PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
            dataContainer.set(key, PersistentDataType.STRING, enchantmentName);

            meta.setLore(lore);
            item.setItemMeta(meta);
            inv.setItem(slot, item);
        }else if (hasEnchantment) {
            ItemStack item = new ItemStack(Material.BOOK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§e§n" + capitalize(enchantmentName) + " §r§e(lvl " + level + "§e/5)");

            List<String> lore = new ArrayList<>();
            lore.add("§6" + discription);
            lore.add("§6" + discription2);
            lore.add("§4");

            if (level < 5) {
                lore.add("§eErforschen:");
                Map<String, Integer> requirements = getKillRequirements(enchantmentName, erforschtlvl + 1);
                for (String mobType : requirements.keySet()) {
                    int requiredKills = requirements.get(mobType);
                    int currentKills = getPlayerKills(playerUUID, mobType);
                    lore.add("§7 - " + mobType + ": §f" + currentKills + "§7 / §e" + requiredKills);
                }
            } else {
                lore.add("§aMaximales Level Erreicht!");
            }
            lore.add("§eLinksklick §a>> Herstellen");
            lore.add("§eRechtsklick §a>> Ausrüsten/Ablegen");

            // PersistentData hinzufügen
            NamespacedKey key = new NamespacedKey(DungeonCrusher.getInstance(), "enchantment");
            PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
            dataContainer.set(key, PersistentDataType.STRING, enchantmentName);

            meta.setLore(lore);
            item.setItemMeta(meta);
            inv.setItem(slot, item);
        }
    }
    @Override
    public void handleItemClick(Player player, ItemStack clickedItem, ClickType clickType) {
        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        ItemMeta clickedMeta = clickedItem.getItemMeta();
        if (clickedMeta == null) {
            return;
        }

        NamespacedKey key = new NamespacedKey(DungeonCrusher.getInstance(), "enchantment");
        PersistentDataContainer dataContainer = clickedMeta.getPersistentDataContainer();

        String enchantmentName = null;
        if (dataContainer.has(key, PersistentDataType.STRING)) {
            enchantmentName = dataContainer.get(key, PersistentDataType.STRING);
        }

        // Unterschiedliche Aktionen basierend auf dem Klicktyp
        if (clickType == ClickType.LEFT) {
            handleLeftClick(player, enchantmentName, clickedItem);
        } else if (clickType == ClickType.RIGHT) {
            handleRightClick(player, clickedItem);
        }
    }

    private void handleLeftClick(Player player, String enchantmentName, ItemStack clickedItem) {
        String playerUUID = player.getUniqueId().toString();
        int level = mysqlManager.getlevelEnchantment(player.getUniqueId().toString(), enchantmentName);

        boolean hasResourcesforUpgrade = checkRecourcesforUpgrade(player, level + 1, enchantmentName);
        if (!hasResourcesforUpgrade) {
            return;
        }
        // Deduct the required resources
        EnchantmentCost cost = getEnchantmentCost(enchantmentName, level + 1);
        System.out.println("1. " + cost);
        if (cost != null) {
            // Update player's balance
            double currentBalance = Double.parseDouble(mysqlManager.getBalance(playerUUID).replace(",", ""));
            double newMoney = currentBalance - cost.money;
            String formattedMoney = String.format(Locale.ENGLISH, "%,.2f", newMoney);
            mysqlManager.updateBalance(playerUUID, formattedMoney);

            // Update player's raw copper amount
            int currentRawCopper = mysqlManager.getItemAmount(playerUUID, "raw_copper");
            mysqlManager.updateItemAmount(playerUUID, "raw_copper", currentRawCopper - cost.rawCopper);

            // Increase the enchantment level
            mysqlManager.updatelevelEnchantment(playerUUID, enchantmentName, level + 1);
            player.updateInventory();
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.enchantmentupgraded", "%enchantmentname%", enchantmentName));
            scoreboardBuilder.updateMoney(player);
            SwordCategory swordCategory = new SwordCategory(mysqlManager);
            swordCategory.setPlayerInventoryItems(player);
        } else {
            player.sendMessage(ConfigManager.getPrefix() + "§cFehler: Kosten für das Enchantment nicht gefunden.");
        }
    }

    // Funktion für Rechtsklick
    private void handleRightClick(Player player, ItemStack clickedItem) {
        // Sicherstellen, dass das geklickte Item ein Buch (normal oder verzaubert) ist
        if (clickedItem != null && (clickedItem.getType() == Material.BOOK || clickedItem.getType() == Material.ENCHANTED_BOOK)) {
            String enchantmentName = getEnchantmentNameFromItem(clickedItem);
            boolean equipedenchantment = mysqlManager.getEquipedEnchantment(player.getUniqueId().toString(), enchantmentName);

            if (enchantmentName != null) {
                // Überprüfen, ob der Spieler bereits maximal 3 ausgerüstete Verzauberungen hat
                int equippedEnchantmentsCount = mysqlManager.getEquipeedEnchantmentsCount(player.getUniqueId().toString());
                if (equippedEnchantmentsCount >= 3 && !equipedenchantment) {
                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.tomanyenchantmentsequiped","",""));
                    return;  // Stoppt den Vorgang, wenn der Spieler bereits 3 Verzauberungen ausgerüstet hat
                }

                // Wenn es ein verzaubertes Buch ist
                if (clickedItem.getType() == Material.ENCHANTED_BOOK) {
                    // Erstelle ein neues normales Buch
                    ItemStack normalBook = new ItemStack(Material.BOOK);

                    // Hole das Meta des verzauberten Buches
                    ItemMeta enchantedMeta = clickedItem.getItemMeta();
                    if (enchantedMeta != null) {
                        // Kopiere den ItemMeta des verzauberten Buches, einschließlich Name und Lore
                        ItemMeta normalMeta = normalBook.getItemMeta();
                        if (normalMeta != null) {
                            normalMeta.setDisplayName(enchantedMeta.getDisplayName());  // Name bleibt gleich
                            normalMeta.setLore(enchantedMeta.getLore());  // Lore bleibt gleich

                            // Entferne die Verzauberung aus den persistenten Daten
                            NamespacedKey key = new NamespacedKey(DungeonCrusher.getInstance(), "enchantment");
                            PersistentDataContainer dataContainer = normalMeta.getPersistentDataContainer();
                            dataContainer.set(key, PersistentDataType.STRING, enchantmentName);

                            // Setze den Meta-Tag für das normale Buch
                            normalBook.setItemMeta(normalMeta);

                            // Hole das Inventar des Spielers und ersetze das verzauberte Buch
                            Inventory inv = player.getOpenInventory().getTopInventory();
                            if (inv != null) {
                                int slot = inv.first(clickedItem);  // Finde den Slot des geklickten Items
                                if (slot != -1) {
                                    inv.setItem(slot, normalBook);  // Ersetze das verzauberte Buch durch das normale Buch
                                    player.updateInventory();  // Stelle sicher, dass das Inventar sofort aktualisiert wird
                                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.enchantmentunequiped", "",""));
                                }
                            }

                            // Aktualisiere den Equip-Status auf false, da das Buch nicht mehr ausgerüstet ist
                            mysqlManager.updateEquipedEnchantments(player.getUniqueId().toString(), enchantmentName, false);
                        }
                    }
                }
                // Wenn es ein normales Buch ist
                else if (clickedItem.getType() == Material.BOOK) {
                    // Erstelle ein neues verzaubertes Buch
                    ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);

                    // Hole das Meta des normalen Buches
                    ItemMeta normalMeta = clickedItem.getItemMeta();
                    if (normalMeta != null) {
                        // Kopiere den ItemMeta des normalen Buches, einschließlich Name und Lore
                        ItemMeta enchantedMeta = enchantedBook.getItemMeta();
                        if (enchantedMeta != null) {
                            enchantedMeta.setDisplayName(normalMeta.getDisplayName());  // Name bleibt gleich
                            enchantedMeta.setLore(normalMeta.getLore());  // Lore bleibt gleich

                            // Füge die Verzauberung zum verzauberten Buch hinzu
                            NamespacedKey key = new NamespacedKey(DungeonCrusher.getInstance(), "enchantment");
                            PersistentDataContainer dataContainer = enchantedMeta.getPersistentDataContainer();
                            dataContainer.set(key, PersistentDataType.STRING, enchantmentName);

                            // Setze den Meta-Tag für das verzauberte Buch
                            enchantedBook.setItemMeta(enchantedMeta);
                            // Hole das Inventar des Spielers und ersetze das normale Buch
                            Inventory inv = player.getOpenInventory().getTopInventory();
                            if (inv != null) {
                                int slot = inv.first(clickedItem);  // Finde den Slot des geklickten Items
                                if (slot != -1) {
                                    inv.setItem(slot, enchantedBook);  // Ersetze das normale Buch durch das verzauberte Buch
                                    player.updateInventory();  // Stelle sicher, dass das Inventar sofort aktualisiert wird
                                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.enchantmentequiped","",""));
                                }
                            }

                            // Aktualisiere den Equip-Status auf true, da das Buch ausgerüstet ist
                            mysqlManager.updateEquipedEnchantments(player.getUniqueId().toString(), enchantmentName, true);
                        }
                    }
                }
            } else {
                player.sendMessage("§cFehler bitte einem Admin melden!");
            }
        }
    }

    private String getEnchantmentNameFromItem(ItemStack item) {
        if (item.hasItemMeta()) {
            NamespacedKey key = new NamespacedKey(DungeonCrusher.getInstance(), "enchantment");
            PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();

            if (dataContainer.has(key, PersistentDataType.STRING)) {
                return dataContainer.get(key, PersistentDataType.STRING);
            }
        }
        return null;
    }

    private boolean checkRecourcesforUpgrade(Player player, int level, String enchantmentName) {
        String playerUUID = player.getUniqueId().toString();
        EnchantmentCost cost = getEnchantmentCost(enchantmentName, level);
        if (cost == null) {
            player.sendMessage(ConfigManager.getPrefix() + "§cFehler: Kosten für das Enchantment nicht gefunden.");
            return false;
        }

        double currentGeld = Double.parseDouble(mysqlManager.getBalance(playerUUID).replace(",", ""));
        if (currentGeld < cost.money) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughmoneyforupgrade", "", ""));
            return false;
        }

        int currentRawCopper = mysqlManager.getItemAmount(playerUUID, "raw_copper");
        if (currentRawCopper < cost.rawCopper) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.enchantmentnotenoughrawcopper", "", ""));
            return false;
        }

        return true;
    }

    private EnchantmentCost getEnchantmentCost(String enchantmentName, int level) {
        Map<Integer, EnchantmentCost> costs = enchantmentCosts.get(enchantmentName);
        if (costs != null) {
            return costs.get(level);
        }
        return null;
    }

    private Map<String, Integer> getKillRequirements(String enchantment, int level) {
        if (enchantmentRequirements.containsKey(enchantment)) {
            return enchantmentRequirements.get(enchantment).getOrDefault(level, new HashMap<>());
        }
        return new HashMap<>();
    }

    private int getPlayerKills(String playerUUID, String mobType) {
        return mysqlManager.getPlayerMobKills(playerUUID, mobType);
    }

    private String capitalize(String str) {
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }

    public boolean checkKillsforErforschen(Player player, int level, String enchantmentName) {
        String playerUUID = player.getUniqueId().toString();
        Map<String, Integer> requirements = getKillRequirements(enchantmentName, level + 1);
        for (String mobType : requirements.keySet()) {
            int requiredKills = requirements.get(mobType);
            int currentKills = getPlayerKills(playerUUID, mobType);
            if (currentKills < requiredKills) {
                return false;
            }
        }

        return true;
    }

    private static class EnchantmentItem {
        private final String displayName;
        private final Material material;
        private final int modeldata;
        private final List<String> lore;
        private final String enchantmentName;
        private final String description;
        private final String description2;// Neuer Parameter

        private EnchantmentItem(String displayName, Material material, int ModelData, List<String> lore, String enchantmentName, String description, String description2) {
            this.displayName = displayName;
            this.material = material;
            this.modeldata = ModelData;
            this.lore = lore;
            this.enchantmentName = enchantmentName;
            this.description = description;
            this.description2 = description2;// Initialisierung
        }
        public String getEnchantmentName() {
            return enchantmentName;
    }
    }

    private static class EnchantmentCost {
        private final double money;
        private final int rawCopper;

        public EnchantmentCost(double money, int rawCopper) {
            this.money = money;
            this.rawCopper = rawCopper;
        }
    }

}
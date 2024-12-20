package net.dbsgameplay.dungeoncrusher.enums.Enchantments;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.interfaces.EnchantmentCategory;
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
        items.put(9, new EnchantmentItem("§7§kWindklinge", Material.BOOK, 500, Arrays.asList(""), "windklinge","Test", "Test"));
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

    }

    @Override
    public void openMenu(Player player) {
        String displayName = "§f<shift:-8>%oraxen_enchantment%";
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
        int level = mysqlManager.getlevelEnchantment(player.getUniqueId().toString(), enchantmentName);

        boolean hasResourcesforUpgrade = checkRecourcesforUpgrade(player, level, enchantmentName);
        if (!hasResourcesforUpgrade) {
            return;
        }

        player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.enchantmentupgraded", "%enchantmentname%" , enchantmentName));
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
        double requiredGeld = RequiredMaterialForFesselschlag(level);
        String balanceString = mysqlManager.getBalance(playerUUID).replace(",", ""); // Komma entfernen
        double currentGeld = Double.parseDouble(balanceString);
        int nextlevel = level + 1;

        if (currentGeld < requiredGeld) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughmoneyforupgrade","",""));
            return false;
        }

        int currentRawCopper = mysqlManager.getItemAmount(playerUUID, "raw_copper");
        int requiredRawCopper = RequiredMaterialForFesselschlag(level);

        if (currentRawCopper < requiredRawCopper) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.enchantmentnotenoughrawcopper","",""));
            return false;
        }

        return true;
    }

    private double RequiredMoneyForFesselschlag(int level) {
        if (level < 2) {
            return 12000;
        }else if (level < 3) {
            return 21000;
        }else {
            return 46000;
        }
    }

    private int RequiredMaterialForFesselschlag(int level) {
        if (level < 2) {
            return 500;
        } else if (level < 3) {
            return 900;
        } else  {
            return 1800;
        }
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
}
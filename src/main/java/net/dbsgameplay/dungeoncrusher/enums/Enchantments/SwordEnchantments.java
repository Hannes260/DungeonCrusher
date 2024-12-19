package net.dbsgameplay.dungeoncrusher.enums.Enchantments;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.Upgrades.SwordCategory;
import net.dbsgameplay.dungeoncrusher.interfaces.EnchantmentCategory;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
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
        items.put(0, new EnchantmentItem("§7§kFesselschlag", Material.BOOK, 500, Arrays.asList(""), "fesselschlag"));
        items.put(9, new EnchantmentItem("§7§kWindklinge", Material.BOOK, 500, Arrays.asList(""), "windklinge"));
        items.put(18, new EnchantmentItem("§7§kGifthieb", Material.BOOK, 500, Arrays.asList(""), "gifthieb"));
        items.put(27, new EnchantmentItem("§7§kSeelenentzug", Material.BOOK, 500, Arrays.asList(""), "seelenentzug"));
        items.put(36, new EnchantmentItem("§7§kWutentbrannt", Material.BOOK, 500, Arrays.asList(""), "wutentbrannt"));
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
        String DisplayName = "§f<shift:-8>%oraxen_enchantment%";
        DisplayName = PlaceholderAPI.setPlaceholders(player, DisplayName);
        Inventory inv = Bukkit.createInventory(null, 9 * 6, DisplayName);
        String playerUUID = player.getUniqueId().toString();

        for (int slot : items.keySet()) {
            SwordEnchantments.EnchantmentItem shopItem = items.get(slot);
            ItemStack itemStack = new ItemStack(shopItem.material);
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(shopItem.displayName);
            meta.setCustomModelData(500);
            itemStack.setItemMeta(meta);
            inv.setItem(slot, itemStack);

        }
        showEnchantmentItem(player, inv, playerUUID, "fesselschlag", 0, "Dein Gegner wird verlangsamt", "wenn du ihn schlägst.");
        player.openInventory(inv);
    }
    private void showEnchantmentItem(Player player, Inventory inv, String playerUUID, String enchantmentName, int slot, String discription, String discription2) {
        boolean hasEnchantment = mysqlManager.getFoundEnchantment(playerUUID, enchantmentName);
        int level = mysqlManager.getlevelEnchantment(playerUUID, enchantmentName);

        if (hasEnchantment) {
            ItemStack item = new ItemStack(Material.BOOK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§e§n" + capitalize(enchantmentName) + " §r§e(lvl " + level + "§e/5)");

            List<String> lore = new ArrayList<>();
            lore.add("§6" + discription);
            lore.add("§6" + discription2);
            lore.add("§4");

            if (level < 5) {
                lore.add("§eErforschen:");
                Map<String, Integer> requirements = getKillRequirements(enchantmentName, level + 1);
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
    public void handleItemClick(Player player, ItemStack clickedItem) {
        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            player.sendMessage("§cUngültiges Item.");
            return;
        }

        ItemMeta clickedMeta = clickedItem.getItemMeta();
        if (clickedMeta == null) {
            player.sendMessage("§cKein ItemMeta gefunden.");
            return;
        }

        NamespacedKey key = new NamespacedKey(DungeonCrusher.getInstance(), "enchantment");
        PersistentDataContainer dataContainer = clickedMeta.getPersistentDataContainer();

        if (dataContainer.has(key, PersistentDataType.STRING)) {
            String enchantmentName = dataContainer.get(key, PersistentDataType.STRING);
            if (enchantmentName != null) {
                player.sendMessage("§aEnchantment verarbeitet: " + enchantmentName);
                // Deine Logik hier
            } else {
                player.sendMessage("§cKein Enchantment zugeordnet.");
            }
        } else {
            player.sendMessage("§cKein Enchantment-Tag gefunden.");
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
        private final String enchantmentName; // Neuer Parameter

        private EnchantmentItem(String displayName, Material material, int ModelData, List<String> lore, String enchantmentName) {
            this.displayName = displayName;
            this.material = material;
            this.modeldata = ModelData;
            this.lore = lore;
            this.enchantmentName = enchantmentName; // Initialisierung
        }
        public String getEnchantmentName() {
            return enchantmentName;
    }
    }
}
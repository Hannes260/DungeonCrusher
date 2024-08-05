package net.dbsgameplay.dungeoncrusher.enums.Upgrades;

import net.dbsgameplay.dungeoncrusher.Commands.interfaces.UpgradeCategory;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.listener.Joinlistener;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
import net.dbsgameplay.dungeoncrusher.utils.TexturedHeads;
import net.dbsgameplay.dungeoncrusher.utils.shops.ShopManager;
import net.dbsgameplay.dungeoncrusher.utils.upgrades.UpgradeManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

import java.util.*;

public class SwordCategory implements UpgradeCategory {
    private final MYSQLManager mysqlManager;
    private final ScoreboardBuilder scoreboardBuilder;
    private final Map<Integer, ShopItem> items;

    public SwordCategory(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
        this.scoreboardBuilder = new ScoreboardBuilder(DungeonCrusher.getInstance());

        this.items = new HashMap<>();
        items.put(20, new ShopItem("§7➢ Schwert Upgrade", Material.DIAMOND_SWORD, 25, Arrays.asList("")));
    }

    @Override
    public void openMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9 * 6, "§7➢ Schwert");

        for (int slot : items.keySet()) {
            ShopItem shopItem = items.get(slot);
            ItemStack itemStack = new ItemStack(shopItem.material);
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(shopItem.displayName);

            // Details zu benötigten Materialien und aktuelle Menge hinzufügen
            String uuid = player.getUniqueId().toString();
            int currentLevel = mysqlManager.getSwordLevel(uuid);
            double requiredGeld = calculateRequiredGeld(currentLevel);
            String balanceString = mysqlManager.getBalance(uuid).replace(",", ""); // Komma entfernen
            double currentMoney = Double.parseDouble(balanceString);
            String[] materialTypes = getMaterialTypes(currentLevel);
            int requiredMaterial1 = calculateRequiredMaterial1(currentLevel);
            int requiredMaterial2 = calculateRequiredMaterial2(currentLevel);

            int currentMaterial1Amount = mysqlManager.getItemAmount(uuid, materialTypes[0]);
            int currentMaterial2Amount = mysqlManager.getItemAmount(uuid, materialTypes[1]);

            List<String> lore = new ArrayList<>();
            lore.add("§7Erforderliches Geld: §6" + requiredGeld);
            lore.add("§7Material 1 (" + getMaterialDisplayName(materialTypes[0]) + "): " + requiredMaterial1 + " (" + currentMaterial1Amount + ")");
            lore.add("§7Material 2 (" + getMaterialDisplayName(materialTypes[1]) + "): " + requiredMaterial2 + " (" + currentMaterial2Amount + ")");
            meta.setLore(lore);

            //Visuelle Anezeige
            boolean hasResources = checkResourcesForUpgrade(player, currentLevel);
            if (hasResources) {
                meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

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
        if ("§7➢ Zurück".equals(clickedDisplayName)) {
            UpgradeManager.openMainMenu(player);
            return;
        }

        String uuid = player.getUniqueId().toString();
        int currentLevel = mysqlManager.getSwordLevel(uuid);

        boolean hasResources = checkResourcesForUpgrade(player, currentLevel);
        if (!hasResources) {
            return;
        }

        upgradeSword(player, currentLevel);

        updatePlayerResources(player, currentLevel);

        mysqlManager.updateSwordLevel(uuid, currentLevel + 1);

        player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.upgradesword", "%currentlevel%", String.valueOf(currentLevel + 1)));

        openMenu(player);
    }

    private boolean checkResourcesForUpgrade(Player player, int currentLevel) {
        String uuid = player.getUniqueId().toString();

        // Erforderliches Geld und Materialien berechnen
        double requiredGeld = calculateRequiredGeld(currentLevel);
        String[] materialTypes = getMaterialTypes(currentLevel);
        int requiredMaterial1 = calculateRequiredMaterial1(currentLevel);
        int requiredMaterial2 = calculateRequiredMaterial2(currentLevel);

        // Aktuelles Geld des Spielers abrufen und in eine Zahl umwandeln
        String balanceString = mysqlManager.getBalance(uuid).replace(",", ""); // Komma entfernen
        double currentGeld = Double.parseDouble(balanceString);

        // Überprüfen, ob der Spieler genügend Geld hat
        if (currentGeld < requiredGeld) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughmoneyforupgrade","",""));
            return false;
        }

        // Materialmengen des Spielers abrufen
        int currentMaterial1Amount = mysqlManager.getItemAmount(uuid, materialTypes[0]);
        int currentMaterial2Amount = mysqlManager.getItemAmount(uuid, materialTypes[1]);

        // Überprüfen, ob der Spieler genügend Materialien hat
        if (currentMaterial1Amount < requiredMaterial1) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughmaterialforupgrade", "%materialtypes%", materialTypes[0]));
            return false;
        }

        if (currentMaterial2Amount < requiredMaterial2) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughmaterialforupgrade", "%materialtypes%", materialTypes[1]));
            return false;
        }

        return true;
    }

    private void updatePlayerResources(Player player, int currentLevel) {
        String uuid = player.getUniqueId().toString();

        // Berechnung des erforderlichen Geldes und Materialien
        double requiredGeld = calculateRequiredGeld(currentLevel);
        String[] materialTypes = getMaterialTypes(currentLevel);
        int requiredMaterial1 = calculateRequiredMaterial1(currentLevel);
        int requiredMaterial2 = calculateRequiredMaterial2(currentLevel);

        // Aktuelles Geld abrufen und in eine Zahl umwandeln
        String balanceString = mysqlManager.getBalance(uuid).replace(",", ""); // Komma entfernen
        double currentMoney = Double.parseDouble(balanceString);

        // Überprüfen, ob der Spieler genug Geld hat
        if (currentMoney < requiredGeld) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughmoneyforupgrade","",""));
            return;
        }


        double newMoney = currentMoney - requiredGeld;

        // Geld aktualisieren und formatieren
        String formattedMoney = String.format(Locale.ENGLISH, "%,.2f", newMoney);
        mysqlManager.updateBalance(uuid, formattedMoney);

        // Materialmengen abrufen und überprüfen
        int currentMaterial1Amount = mysqlManager.getItemAmount(uuid, materialTypes[0]);
        int currentMaterial2Amount = mysqlManager.getItemAmount(uuid, materialTypes[1]);

        if (currentMaterial1Amount < requiredMaterial1 || currentMaterial2Amount < requiredMaterial2) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughmaterialsforupgrade","",""));
            return;
        }

        // Berechnung der neuen Materialmengen
        int newMaterial1Amount = currentMaterial1Amount - requiredMaterial1;
        int newMaterial2Amount = currentMaterial2Amount - requiredMaterial2;

        // Materialmengen in der Datenbank aktualisieren
        mysqlManager.updateItemAmount(uuid, materialTypes[0], newMaterial1Amount);
        mysqlManager.updateItemAmount(uuid, materialTypes[1], newMaterial2Amount);

        // Spieler informieren
        player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.upgradesuccesfull"));
        scoreboardBuilder.updateMoney(player);
        setPlayerInventoryItems(player);
    }

    private void upgradeSword(Player player, int currentLevel) {
        int newLevel = currentLevel + 1;
        double newAttackDamage = calculateAttackDamage(newLevel);
        Material newMaterial = getSwordMaterial(newLevel);

        ItemStack sword = new ItemStack(newMaterial);
        ItemMeta meta = sword.getItemMeta();
        if (meta == null) {
            return; // Exit if meta is null
        }
        meta.setDisplayName("§7<<Schwert Lv. " + newLevel + "§7>>");

        // Angriffsschaden-Attribut hinzufügen mit NamespacedKey
        NamespacedKey key = new NamespacedKey(DungeonCrusher.getInstance(), "generic.attackDamage");
        AttributeModifier modifier = new AttributeModifier(key, newAttackDamage, AttributeModifier.Operation.ADD_NUMBER);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);

        // Lore hinzufügen
        List<String> lore = new ArrayList<>();
        meta.setLore(lore);

        sword.setItemMeta(meta);

        // Schwert im Inventar des Spielers aktualisieren
        player.getInventory().setItem(0, sword);

        // Aktualisiere das gesamte Inventar, um sicherzustellen, dass die Änderungen angezeigt werden
        player.updateInventory();
    }


    private double calculateRequiredGeld(int level) {
        return 365 * level; // 365 * aktuelles Level
    }

    private int calculateRequiredMaterial1(int level) {
        if (level < 45) {
            return 10 + (5 * level); // Rohkupfer
        } else if (level < 85) {
            return 230 + (5 * (level - 45)); // Bruchstein
        } else {
            return 430 + (5 * (level - 85)); // Roheisen
        }
    }

    private int calculateRequiredMaterial2(int level) {
        if (level < 45) {
            return 6 + (5 * level); // Kupferbarren
        } else if (level < 85) {
            return 242 + (5 * (level - 45)); // Stein
        } else {
            return 462 + (5 * (level - 85)); // Eisenbarren
        }
    }

    private int calculateAttackDamage(int level) {
        if (level < 45) {
            return 4 + level; // Holzschwert
        } else if (level < 85) {
            return 49 + (level - 45); // Steinschwert
        } else {
            return 89 + (level - 85); // Eisenschwert
        }
    }

    private Material getSwordMaterial(int level) {
        if (level < 46) {
            return Material.WOODEN_SWORD;
        } else if (level < 86) {
            return Material.STONE_SWORD;
        } else {
            return Material.IRON_SWORD;
        }
    }

    private String[] getMaterialTypes(int level) {
        if (level < 45) {
            return new String[]{"raw_copper", "copper_ingot"};
        } else if (level < 85) {
            return new String[]{"cobblestone", "stone"};
        } else {
            return new String[]{"raw_iron", "iron_ingot"};
        }
    }

    private String getMaterialDisplayName(String materialType) {
        switch (materialType) {
            case "raw_copper":
                return "Rohkupfer";
            case "copper_ingot":
                return "Kupferbarren";
            case "cobblestone":
                return "Bruchstein";
            case "stone":
                return "Stein";
            case "raw_iron":
                return "Roheisen";
            case "iron_ingot":
                return "Eisenbarren";
            default:
                return "Unbekanntes Material";
        }
    }

    private void addBackButton(Player player, Inventory inventory) {
        PlayerProfile backheadprofile = TexturedHeads.getProfile("https://textures.minecraft.net/texture/f7aacad193e2226971ed95302dba433438be4644fbab5ebf818054061667fbe2");
        ItemStack backhead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headmeta = (SkullMeta) backhead.getItemMeta();
        headmeta.setOwnerProfile(backheadprofile);
        headmeta.setDisplayName("§7➢ Zurück");
        backhead.setItemMeta(headmeta);
        inventory.setItem(45, backhead);
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
    public void setPlayerInventoryItems(Player player) {
        // Define items
        ItemStack[] items = new ItemStack[13];
        String[] materialNames = {
                "RAW_COPPER", "COPPER_INGOT", "RAW_GOLD", "GOLD_INGOT",
                "RAW_IRON", "IRON_INGOT", "COAL", "COBBLESTONE",
                "STONE", "DIAMOND_ORE", "DIAMOND", "NETHERITE_INGOT",
                "NETHERITE_SCRAP"
        };
        Material[] materials = {
                Material.RAW_COPPER, Material.COPPER_INGOT, Material.RAW_GOLD, Material.GOLD_INGOT,
                Material.RAW_IRON, Material.IRON_INGOT, Material.COAL, Material.COBBLESTONE,
                Material.STONE, Material.DIAMOND_ORE, Material.DIAMOND, Material.NETHERITE_INGOT,
                Material.NETHERITE_SCRAP
        };

        // Set items
        for (int i = 0; i < items.length; i++) {
            items[i] = new ItemStack(materials[i]);
            ItemMeta itemMeta = items[i].getItemMeta();
            String itemName = materialNames[i].toLowerCase();
            itemMeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), itemName));
            itemMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            items[i].setItemMeta(itemMeta);
        }

        // Set inventory slots
        int[] slots = {9, 10, 18, 19, 27, 28, 22, 17, 16, 26, 25, 34, 35};
        for (int i = 0; i < items.length; i++) {
            player.getInventory().setItem(slots[i], items[i]);
        }
    }
}

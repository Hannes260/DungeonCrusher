package net.dbsgameplay.dungeoncrusher.enums.Upgrades;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.interfaces.UpgradeCategory;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
import net.dbsgameplay.dungeoncrusher.utils.TexturedHeads;
import net.dbsgameplay.dungeoncrusher.utils.upgrades.UpgradeManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
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
    private final Map<Integer, ShopItem> upgradeitems;
    public SwordCategory(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
        this.scoreboardBuilder = new ScoreboardBuilder(DungeonCrusher.getInstance());
        this.upgradeitems = new HashMap<>();
        upgradeitems.put(10, new ShopItem("§7➢ Schwert Upgrade", Material.PAPER, 25, Arrays.asList("")));
        upgradeitems.put(11, new ShopItem("§7➢ Schwert Upgrade", Material.PAPER, 25, Arrays.asList("")));
        upgradeitems.put(12, new ShopItem("§7➢ Schwert Upgrade", Material.PAPER, 25, Arrays.asList("")));
        upgradeitems.put(19, new ShopItem("§7➢ Schwert Upgrade", Material.PAPER, 25, Arrays.asList("")));
        upgradeitems.put(20, new ShopItem("§7➢ Schwert Upgrade", Material.PAPER, 25, Arrays.asList("")));
        upgradeitems.put(21, new ShopItem("§7➢ Schwert Upgrade", Material.PAPER, 25, Arrays.asList("")));
        upgradeitems.put(29, new ShopItem("§7➢ Schwert Upgrade", Material.PAPER, 25, Arrays.asList("")));
        upgradeitems.put(29, new ShopItem("§7➢ Schwert Upgrade", Material.PAPER, 25, Arrays.asList("")));
        upgradeitems.put(30, new ShopItem("§7➢ Schwert Upgrade", Material.PAPER, 25, Arrays.asList("")));
    }

    @Override
    public void openMenu(Player player) {

        String DisplayName = "§f<shift:-8>%oraxen_upgrade_sword%";
        DisplayName = PlaceholderAPI.setPlaceholders(player, DisplayName);
        Inventory inv = Bukkit.createInventory(null, 9 * 6, DisplayName);

        for (int slot : upgradeitems.keySet()) {
            ShopItem shopItem = upgradeitems.get(slot);
            ItemStack itemStack = new ItemStack(shopItem.material);
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(shopItem.displayName);
            meta.setCustomModelData(100);

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
            lore.add("§7Erforderliches Geld: §6" + currentMoney + " §7von " +requiredGeld);
            lore.add("§7Material 1 (" + getMaterialDisplayName(materialTypes[0]) + "): §6" + currentMaterial1Amount + " §7von " + requiredMaterial1);
            lore.add("§7Material 2 (" + getMaterialDisplayName(materialTypes[1]) + "): §6" + currentMaterial2Amount + " §7von " + requiredMaterial2);
            meta.setLore(lore);

            //Visuelle Anezeige
            boolean hasResources = hasEnoughResourcesForVisuals(player, currentLevel);
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
        for (int slot : upgradeitems.keySet()) {
            SwordCategory.ShopItem shopItem = upgradeitems.get(slot);
            if (shopItem.displayName.equals(clickedDisplayName)) {
                upgradeSword(player, currentLevel);
                player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_PLACE, 1.0f, 1.0f);
                updatePlayerResources(player, currentLevel);
                mysqlManager.updateSwordLevel(uuid, currentLevel + 1);
                scoreboardBuilder.updateSwordLevel(player);
                int nextlevel = currentLevel + 1;
                String playerName = player.getName();
                String message = "\nHat das schwert geupgradet auf Level " + nextlevel;
                String fullmessage = "Name: " + playerName + message;
                DungeonCrusher.getInstance().sendToDiscord(fullmessage, 65280);
                openMenu(player);
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.upgradesword", "%currentlevel%", String.valueOf(currentLevel + 1)));
                return;
            }
        }
    }


    public boolean hasEnoughResourcesForVisuals(Player player, int currentLevel) {
        String uuid = player.getUniqueId().toString();

        // Erforderliches Geld und Materialien berechnen
        double requiredGeld = calculateRequiredGeld(currentLevel);
        String[] materialTypes = getMaterialTypes(currentLevel);
        int requiredMaterial1 = calculateRequiredMaterial1(currentLevel);
        int requiredMaterial2 = calculateRequiredMaterial2(currentLevel);

        // Aktuelles Geld des Spielers abrufen und in eine Zahl umwandeln
        String balanceString = mysqlManager.getBalance(uuid).replace(",", ""); // Komma entfernen
        double currentGeld = Double.parseDouble(balanceString);

        // Materialmengen des Spielers abrufen
        int currentMaterial1Amount = mysqlManager.getItemAmount(uuid, materialTypes[0]);
        int currentMaterial2Amount = mysqlManager.getItemAmount(uuid, materialTypes[1]);

        // Überprüfen, ob der Spieler genügend Geld und Materialien hat
        return currentGeld >= requiredGeld && currentMaterial1Amount >= requiredMaterial1 && currentMaterial2Amount >= requiredMaterial2;
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
        int nextlevel = currentLevel + 1;
        // Überprüfen, ob der Spieler genügend Geld hat
        if (currentGeld < requiredGeld) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughmoneyforupgrade","",""));
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1.0f, 1.0f);
            String playerName = player.getName();
            String message = "Konnte das schwert nicht auf Level " + nextlevel + "upgraden! \nzu wenig Geld";
            String fullmessage = "Name: " + playerName +"\n Derzeitiges Level: " + currentLevel + "  \n"+ message ;
            DungeonCrusher.getInstance().sendToDiscord(fullmessage, 16711680);
            return false;
        }

        // Materialmengen des Spielers abrufen
        int currentMaterial1Amount = mysqlManager.getItemAmount(uuid, materialTypes[0]);
        int currentMaterial2Amount = mysqlManager.getItemAmount(uuid, materialTypes[1]);

        // Überprüfen, ob der Spieler genügend Materialien hat
        if (currentMaterial1Amount < requiredMaterial1) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughmaterialforupgrade", "%materialtypes%", getMaterialDisplayName(materialTypes[0])));
            String playerName = player.getName();
            String message = "Konnte das schwert nicht auf Level " + nextlevel + "\n" + " upgraden wegen zu wenig Materialien!";
            String fullmessage = "Name: " + playerName +"\n Derzeitiges Level: " + currentLevel + "  \n"+ message ;
            DungeonCrusher.getInstance().sendToDiscord(fullmessage, 16711680);
            return false;
        }

        if (currentMaterial2Amount < requiredMaterial2) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughmaterialforupgrade", "%materialtypes%", getMaterialDisplayName(materialTypes[1])));

            String playerName = player.getName();
            String message = " \nKonnte das schwert nicht auf Level " + nextlevel + "\n" + " upgraden wegen zu wenig Materialien!";
            String fullmessage = "Name: " + playerName +"\n Derzeitiges Level: " + currentLevel + "  \n"+ message ;
            DungeonCrusher.getInstance().sendToDiscord(fullmessage, 16711680);
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
        meta.setDisplayName("§7<<§6Schwert §aLv. " + newLevel + "§7>>");
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.setLore(Collections.singletonList("Attack Damage: " + newAttackDamage));
        meta.setUnbreakable(true);

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
        } else if (level < 130) {
            return 430 + (5 * (level - 85)); // Roheisen
        } else if (level < 175) {
            return 630 + (5 * (level - 130)); // Goldnugget
        } else if (level < 215) {
            return 830 + (5 * (level - 175)); // Diamant
        } else {
            return 1030 + (5 * (level - 215)); // Netheritplatten
        }
    }

    private int calculateRequiredMaterial2(int level) {
        if (level < 45) {
            return 6 + (5 * level); // Kupferbarren
        } else if (level < 85) {
            return 242 + (5 * (level - 45)); // Stein
        } else if (level < 130) {
            return 462 + (5 * (level - 85)); // Eisenbarren
        } else if (level < 175) {
            return 682 + (5 * (level - 130)); // Goldbarren
        } else if (level < 215) {
            return 902 + (5 * (level - 175)); // Diamantblock
        } else {
            return 1122 + (5 * (level - 215)); // Netheritbarren
        }
    }

    private int calculateAttackDamage(int level) {
        if (level < 45) {
            return 4 + level; // Holzschwert
        } else if (level < 85) {
            return 49 + (level - 45); // Steinschwert
        } else if (level < 130) {
            return 89 + (level - 85); // Eisenschwert
        } else if (level < 175) {
            return 129 + (level - 130); // Goldschwert
        } else if (level < 215) {
            return 169 + (level - 175); // Diamantschwert
        } else {
            return 209 + (level - 215); // Netheritschwert
        }
    }

    private Material getSwordMaterial(int level) {
        if (level < 46) {
            return Material.WOODEN_SWORD;
        } else if (level < 86) {
            return Material.STONE_SWORD;
        } else if (level < 131) {
            return Material.IRON_SWORD;
        } else if (level < 176) {
            return Material.GOLDEN_SWORD;
        } else if (level < 216) {
            return Material.DIAMOND_SWORD;
        } else
            return Material.NETHERITE_SWORD;
    }

    private String[] getMaterialTypes(int level) {
        if (level < 45) {
            return new String[]{"raw_copper", "copper_ingot"};
        } else if (level < 85) {
            return new String[]{"cobblestone", "stone"};
        } else if (level < 130) {
            return new String[]{"raw_iron", "iron_ingot"};
        } else if (level < 175) {
            return new String[]{"raw_gold", "gold_ingot"};
        } else if (level < 215) {
            return new String[]{"diamond", "diamond_ore"};
        } else {
            return new String[]{"netherite_scrap", "netherite_ingot"};
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
            case "raw_gold":
                return "RohGold";
            case "gold_ingot":
                return "Goldbarren";
            case "diamond":
                return "Diamant";
            case "diamond_block":
                return "Diamantblock";
            case "netherite_scrap":
                return "Netheritschrott";
            case "netherite_ingot":
                return "Netheritbarren";
            default:
                return "Unbekanntes Material";
        }
    }

    private void addBackButton(Player player, Inventory inventory) {
        ItemStack backhead = new ItemStack(Material.PAPER);
        ItemMeta headmeta =  backhead.getItemMeta();
        headmeta.setDisplayName("§7➢ Zurück");
        headmeta.setCustomModelData(100);
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

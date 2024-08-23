package net.dbsgameplay.dungeoncrusher.enums.Upgrades;

import net.dbsgameplay.dungeoncrusher.Commands.interfaces.UpgradeCategory;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
import net.dbsgameplay.dungeoncrusher.utils.TexturedHeads;
import net.dbsgameplay.dungeoncrusher.utils.upgrades.UpgradeManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

import java.util.*;

public class ArmorCategory implements UpgradeCategory {
    private final MYSQLManager mysqlManager;
    private final ScoreboardBuilder scoreboardBuilder;
    private final Map<Integer, ShopItem> upgradeItems;
    public ArmorCategory(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
        this.scoreboardBuilder = new ScoreboardBuilder(DungeonCrusher.getInstance());

        this.upgradeItems = new HashMap<>();
        upgradeItems.put(20, new ShopItem("§7➢ Rüstung Upgrade", Material.DIAMOND_CHESTPLATE, 25, Arrays.asList("")));
    }

    @Override
    public void openMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9 * 6, "§7➢ Rüstung");

        for (int slot : upgradeItems.keySet()) {
            ShopItem shopItem = upgradeItems.get(slot);
            ItemStack itemStack = new ItemStack(shopItem.material);
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(shopItem.displayName);

            // Details zu benötigten Materialien und aktueller Menge hinzufügen
            String uuid = player.getUniqueId().toString();
            int currentLevel = mysqlManager.getArmorLvl(uuid);
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

            // Visuelle Anzeige
            boolean hasResources = hasEnoughResourcesForVisuals(player, currentLevel);
            if (hasResources) {
                meta.addEnchant(Enchantment.PROTECTION, 1, true);
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
        int currentLevel = mysqlManager.getArmorLvl(uuid);

        boolean hasResources = checkResourcesForUpgrade(player, currentLevel);
        if (!hasResources) {
            return;
        }
        for (int slot : upgradeItems.keySet()) {
            ArmorCategory.ShopItem shopItem = upgradeItems.get(slot);
            if (shopItem.displayName.equals(clickedDisplayName)) {
                upgradeArmor(player, currentLevel);
                updatePlayerResources(player, currentLevel);
                scoreboardBuilder.updateMoney(player);
                mysqlManager.updateArmorLvl(uuid, currentLevel + 1);
                scoreboardBuilder.updateArmorLevel(player);
                int nextLevel = currentLevel + 1;
                String playerName = player.getName();
                String message = "\nHat die Rüstung geupgradet auf Level " + nextLevel;
                String fullMessage = "Name: " + playerName + message;
                DungeonCrusher.getInstance().sendToDiscord(fullMessage, 65280);
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.upgradearmor", "%currentlevel%", String.valueOf(currentLevel + 1)));
                return;
            }
        }
        openMenu(player);
    }

    private boolean hasEnoughResourcesForVisuals(Player player, int currentLevel) {
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
        int nextLevel = currentLevel + 1;

        // Überprüfen, ob der Spieler genügend Geld hat
        if (currentGeld < requiredGeld) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughmoneyforupgrade", "", ""));
            String playerName = player.getName();
            String message = "Konnte die Rüstung nicht auf Level " + nextLevel + " upgraden! \nZu wenig Geld";
            String fullMessage = "Name: " + playerName + "\n Derzeitiges Level: " + currentLevel + "  \n" + message;
            DungeonCrusher.getInstance().sendToDiscord(fullMessage, 16711680);
            return false;
        }

        // Materialmengen des Spielers abrufen
        int currentMaterial1Amount = mysqlManager.getItemAmount(uuid, materialTypes[0]);
        int currentMaterial2Amount = mysqlManager.getItemAmount(uuid, materialTypes[1]);

        // Überprüfen, ob der Spieler genügend Materialien hat
        if (currentMaterial1Amount < requiredMaterial1) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughmaterialforupgrade", "%materialtypes%", getMaterialDisplayName(materialTypes[0])));
            String playerName = player.getName();
            String message = "Konnte die Rüstung nicht auf Level " + nextLevel + "\n" + " upgraden wegen zu wenig Materialien!";
            String fullMessage = "Name: " + playerName + "\n Derzeitiges Level: " + currentLevel + "  \n" + message;
            DungeonCrusher.getInstance().sendToDiscord(fullMessage, 16711680);
            return false;
        }

        if (currentMaterial2Amount < requiredMaterial2) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughmaterialforupgrade", "%materialtypes%", getMaterialDisplayName(materialTypes[1])));

            String playerName = player.getName();
            String message = " \nKonnte die Rüstung nicht auf Level " + nextLevel + "\n" + " upgraden wegen zu wenig Materialien!";
            String fullMessage = "Name: " + playerName + "\n Derzeitiges Level: " + currentLevel + "  \n" + message;
            DungeonCrusher.getInstance().sendToDiscord(fullMessage, 16711680);
            return false;
        }

        return true;
    }

    private void updatePlayerResources(Player player, int currentLevel) {
        String uuid = player.getUniqueId().toString();

        // Benötigte Ressourcen berechnen
        double requiredGeld = calculateRequiredGeld(currentLevel);
        String[] materialTypes = getMaterialTypes(currentLevel);
        int requiredMaterial1 = calculateRequiredMaterial1(currentLevel);
        int requiredMaterial2 = calculateRequiredMaterial2(currentLevel);


        // Spielerressourcen aktualisieren
        String balanceString = mysqlManager.getBalance(uuid).replace(",", ""); // Komma entfernen
        double currentMoney = Double.parseDouble(balanceString);
        double newMoney = currentMoney - requiredGeld;

        // Geld aktualisieren und formatieren
        String formattedMoney = String.format(Locale.ENGLISH, "%,.2f", newMoney);
        mysqlManager.updateBalance(uuid, formattedMoney);
        mysqlManager.updateItemAmount(uuid, materialTypes[0], mysqlManager.getItemAmount(uuid, materialTypes[0]) - requiredMaterial1);
        mysqlManager.updateItemAmount(uuid, materialTypes[1], mysqlManager.getItemAmount(uuid, materialTypes[1]) - requiredMaterial2);
    }

    private void upgradeArmor(Player player, int currentLevel) {
        ItemStack item = null;
        int armorSlot = -1;
        double armorValue = 0;

        // Bestimmen Sie, welches Rüstungsteil basierend auf dem aktuellen Level aktualisiert werden soll
        if (currentLevel >= 0 && currentLevel < 10) {
            item = new ItemStack(Material.LEATHER_HELMET);
            armorSlot = EquipmentSlot.HEAD.ordinal();
            armorValue = 1.0;
        } else if (currentLevel >= 10 && currentLevel < 20) {
            item = new ItemStack(Material.LEATHER_CHESTPLATE);
            armorSlot = EquipmentSlot.CHEST.ordinal();
            armorValue = 3.0;
        } else if (currentLevel >= 20 && currentLevel < 30) {
            item = new ItemStack(Material.LEATHER_LEGGINGS);
            armorSlot = EquipmentSlot.LEGS.ordinal();
            armorValue = 2.0;
        } else if (currentLevel >= 30 && currentLevel < 45) {
            item = new ItemStack(Material.LEATHER_BOOTS);
            armorSlot = EquipmentSlot.FEET.ordinal();
            armorValue = 1.0;
        } else if (currentLevel >= 45 && currentLevel < 55) {
            item = new ItemStack(Material.CHAINMAIL_HELMET);
            armorSlot = EquipmentSlot.HEAD.ordinal();
            armorValue = 1.0;
        } else if (currentLevel >= 55 && currentLevel < 65) {
            item = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
            armorSlot = EquipmentSlot.CHEST.ordinal();
            armorValue = 3.0;
        } else if (currentLevel >= 65 && currentLevel < 75) {
            item = new ItemStack(Material.CHAINMAIL_LEGGINGS);
            armorSlot = EquipmentSlot.LEGS.ordinal();
            armorValue = 2.0;
        } else if (currentLevel >= 75 && currentLevel < 85) {
            item = new ItemStack(Material.CHAINMAIL_BOOTS);
            armorSlot = EquipmentSlot.FEET.ordinal();
            armorValue = 1.0;
        }else if (currentLevel >= 85 && currentLevel < 95) {
            item = new ItemStack(Material.IRON_HELMET);
            armorSlot = EquipmentSlot.HEAD.ordinal();
            armorValue = 2.0;
        }else if (currentLevel >= 95 && currentLevel < 105) {
            item = new ItemStack(Material.IRON_CHESTPLATE);
            armorSlot = EquipmentSlot.CHEST.ordinal();
            armorValue = 2.0;
        }else if (currentLevel >= 105 && currentLevel < 115) {
            item = new ItemStack(Material.IRON_LEGGINGS);
            armorSlot = EquipmentSlot.LEGS.ordinal();
            armorValue = 2.0;
        }else if (currentLevel >= 116 && currentLevel < 130) {
            item = new ItemStack(Material.IRON_BOOTS);
            armorSlot = EquipmentSlot.FEET.ordinal();
            armorValue = 2.0;
        }else if (currentLevel >= 131 && currentLevel < 140) {
            item = new ItemStack(Material.GOLDEN_HELMET);
            armorSlot = EquipmentSlot.HEAD.ordinal();
            armorValue = 2.0;
        }else if (currentLevel >= 141 && currentLevel < 150) {
            item = new ItemStack(Material.GOLDEN_CHESTPLATE);
            armorSlot = EquipmentSlot.LEGS.ordinal();
            armorValue = 2.0;
        } else if (currentLevel >= 151 && currentLevel < 160) {
            item = new ItemStack(Material.GOLDEN_LEGGINGS);
            armorSlot = EquipmentSlot.LEGS.ordinal();
            armorValue = 2.0;
        } else if (currentLevel >= 161 && currentLevel < 175) {
            item = new ItemStack(Material.GOLDEN_BOOTS);
            armorSlot = EquipmentSlot.FEET.ordinal();
            armorValue = 2.0;
        } else if (currentLevel >= 176 && currentLevel < 185) {
            item = new ItemStack(Material.DIAMOND_HELMET);
            armorSlot = EquipmentSlot.HEAD.ordinal();
            armorValue = 2.0;
        } else if (currentLevel >= 186 && currentLevel < 195) {
            item = new ItemStack(Material.DIAMOND_CHESTPLATE);
            armorSlot = EquipmentSlot.CHEST.ordinal();
            armorValue = 2.0;
        } else if (currentLevel >= 196 && currentLevel < 205) {
            item = new ItemStack(Material.DIAMOND_LEGGINGS);
            armorSlot = EquipmentSlot.LEGS.ordinal();
            armorValue = 2.0;
        }else if (currentLevel >= 206 && currentLevel < 215) {
            item = new ItemStack(Material.DIAMOND_BOOTS);
            armorSlot = EquipmentSlot.FEET.ordinal();
            armorValue = 2.0;
        } else if (currentLevel >= 216 && currentLevel < 230) {
            item = new ItemStack(Material.NETHERITE_HELMET);
            armorSlot = EquipmentSlot.HEAD.ordinal();
            armorValue = 2.0;
        } else if (currentLevel >= 231 && currentLevel < 245) {
            item = new ItemStack(Material.NETHERITE_CHESTPLATE);
            armorSlot = EquipmentSlot.CHEST.ordinal();
            armorValue = 2.0;
        } else if (currentLevel >= 246 && currentLevel < 260) {
            item = new ItemStack(Material.NETHERITE_LEGGINGS);
            armorSlot = EquipmentSlot.LEGS.ordinal();
            armorValue = 2.0;
        } else if (currentLevel >= 261 && currentLevel < 280) {
            item = new ItemStack(Material.NETHERITE_BOOTS);
            armorSlot = EquipmentSlot.FEET.ordinal();
            armorValue = 2.0;
        }

        if (item != null && armorSlot != -1) {
            // AttributeModifier für zusätzliche Rüstung hinzufügen
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                List<AttributeModifier> modifiers = new ArrayList<>();
                // Berechne die zusätzliche Rüstung basierend auf dem Level
                int extraArmor = (currentLevel / 2) * 1; // Alle zwei Level ein zusätzlicher Rüstungs-Punkt
                int newLevel = currentLevel + 1;
                // Füge den Rüstungs-Modifier hinzu
                NamespacedKey key = new NamespacedKey(DungeonCrusher.getInstance(), "generic.armor");
                modifiers.add(new AttributeModifier(key, armorValue + extraArmor, AttributeModifier.Operation.ADD_NUMBER));
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifiers.get(0));

                // Bei ungeraden Levels zusätzliche Lebenspunkte vergeben
                if (currentLevel % 2 != 0) {
                    double healthBonus = 1.0; // Lebenspunkte, die der Spieler zusätzlich bekommt
                    player.setMaxHealth(player.getMaxHealth() + healthBonus); // Max. Lebenspunkte erhöhen
                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.addhealth", "",""));
                }

                meta.setDisplayName("§7<<Rüstungs Lv. " + newLevel + "§7>>");
                meta.setUnbreakable(true);

                item.setItemMeta(meta);
            }

            // Das Rüstungsteil direkt ausrüsten
            player.getEquipment().setItem(EquipmentSlot.values()[armorSlot], item);
            player.updateInventory();
        }

    }
    private double calculateRequiredGeld(int currentLevel) {
        return currentLevel * 365;
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
            return 6 + (5 * level);
        } else if (level < 85) {
            return 242 + (5 * (level - 45));
        } else if (level < 130) {
            return 462 + (5 * (level - 85));
        } else if (level < 175) {
            return 682 + (5 * (level - 130));
        } else if (level < 215) {
            return 902 + (5 * (level - 175));
        } else {
            return 1122 + (5 * (level - 215));
        }
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
        PlayerProfile backheadprofile = TexturedHeads.getProfile("https://textures.minecraft.net/texture/f7aacad193e2226971ed95302dba433438be4644fbab5ebf818054061667fbe2");
        ItemStack backhead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headmeta = (SkullMeta) backhead.getItemMeta();
        headmeta.setOwnerProfile(backheadprofile);
        headmeta.setDisplayName("§7➢ Zurück");
        backhead.setItemMeta(headmeta);
        inventory.setItem(45, backhead);
    }

    private static class ShopItem {
        String displayName;
        Material material;
        int price;
        List<String> lore;

        ShopItem(String displayName, Material material, int price, List<String> lore) {
            this.displayName = displayName;
            this.material = material;
            this.price = price;
            this.lore = lore;
        }
    }
}

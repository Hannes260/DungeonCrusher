package de.hannezhd.dungeoncrusher.Commands.LevelSystem;

import de.hannezhd.dungeoncrusher.DungeonCrusher;
import de.hannezhd.dungeoncrusher.sql.MYSQLManager;
import de.hannezhd.dungeoncrusher.utils.ConfigManager;
import de.hannezhd.dungeoncrusher.utils.ScoreboardBuilder;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class SwordUpgradeClickListener implements Listener {
    MYSQLManager mysqlManager;
    ScoreboardBuilder scoreboardBuilder;
    public SwordUpgradeClickListener(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
        this.scoreboardBuilder = new ScoreboardBuilder(dungeonCrusher);
    }
    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getView().getTitle() == "§9§lUpgrades") {
            Player player = (Player) event.getWhoClicked();
            event.setCancelled(true);
            if (event.getCurrentItem().getItemMeta().hasLocalizedName()) {
                switch (event.getCurrentItem().getItemMeta().getLocalizedName()) {

                    case"swordupgrade":
                        handleSwordUpgrade(player);
                        break;
                    case"schließen":
                        player.closeInventory();
                        break;
                }
            }
        }else if (event.getView().getTitle().equals("§9§lUpgrades")) {
            event.setCancelled(true);
        }
    }

    private void handleSwordUpgrade(Player player) {
        int currentLevel = mysqlManager.getSwordLevel(player.getUniqueId().toString());

        int[] upgradeData = getUpgradeData(currentLevel);
        if (upgradeData == null) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.upgradesword", "", ""));
            return;
        }

        int requiredRawCopper = upgradeData[0];
        int requiredCopperIngots = upgradeData[1];
        double requiredMoney = upgradeData[2];
        int requiredCobblestone = upgradeData[3];
        int requiredStone = upgradeData[4];
        int requiredRawIron = upgradeData[5];
        int requiredIron = upgradeData[6];
        int requiredRawGold = upgradeData[7];
        int requiredGold = upgradeData[8];
        int requiredDiamondOre = upgradeData[9];
        int requiredDiamond = upgradeData[10];
        int requiredNetheriteScrap = upgradeData[11];
        int requiredNetherite = upgradeData[12];

        int currentRawCopper = mysqlManager.getItemAmount(player.getUniqueId().toString(), "raw_copper");
        int currentCopperIngots = mysqlManager.getItemAmount(player.getUniqueId().toString(), "copper_ingot");
        int currentCobblestone = mysqlManager.getItemAmount(player.getUniqueId().toString(), "cobblestone");
        int currentStone = mysqlManager.getItemAmount(player.getUniqueId().toString(), "stone");
        int currentRawIron = mysqlManager.getItemAmount(player.getUniqueId().toString(), "raw_iron");
        int currentIron = mysqlManager.getItemAmount(player.getUniqueId().toString(), "iron_ingot");
        int currentRawGold = mysqlManager.getItemAmount(player.getUniqueId().toString(), "raw_gold");
        int currentGold = mysqlManager.getItemAmount(player.getUniqueId().toString(), "gold_ingot");
        int currentDiamondOre = mysqlManager.getItemAmount(player.getUniqueId().toString(), "diamond_ore");
        int currentDiamond = mysqlManager.getItemAmount(player.getUniqueId().toString(), "diamond");
        int currentNetheriteScrap = mysqlManager.getItemAmount(player.getUniqueId().toString(), "netherite_scrap");
        int currentNetherite = mysqlManager.getItemAmount(player.getUniqueId().toString(), "netherite_ingot");

        if (currentRawCopper >= requiredRawCopper && currentCopperIngots >= requiredCopperIngots
                && currentCobblestone >= requiredCobblestone && currentStone >= requiredStone && currentRawIron >= requiredRawIron && currentRawGold >= requiredRawGold
                && currentGold >= requiredGold && currentDiamondOre >= requiredDiamondOre && currentDiamond >= requiredDiamond && currentNetheriteScrap >= requiredNetheriteScrap && currentNetherite >= requiredNetherite) {
            if (removeMoney(player, requiredMoney)) {
                mysqlManager.updateItemAmount(player.getUniqueId().toString(), "raw_copper", currentRawCopper - requiredRawCopper);
                mysqlManager.updateItemAmount(player.getUniqueId().toString(), "copper_ingot", currentCopperIngots - requiredCopperIngots);
                mysqlManager.updateItemAmount(player.getUniqueId().toString(), "cobblestone", currentCobblestone - requiredCobblestone);
                mysqlManager.updateItemAmount(player.getUniqueId().toString(), "stone", currentStone - requiredStone);
                mysqlManager.updateItemAmount(player.getUniqueId().toString(), "raw_iron", currentRawIron - requiredRawIron);
                mysqlManager.updateItemAmount(player.getUniqueId().toString(), "iron_ingot", currentIron - requiredIron);
                mysqlManager.updateItemAmount(player.getUniqueId().toString(), "raw_gold", currentRawGold - requiredRawGold);
                mysqlManager.updateItemAmount(player.getUniqueId().toString(), "gold_ingot", currentGold - requiredGold);
                mysqlManager.updateItemAmount(player.getUniqueId().toString(), "diamond_ore", currentDiamondOre - requiredDiamondOre);
                mysqlManager.updateItemAmount(player.getUniqueId().toString(), "diamond", currentDiamond - requiredDiamond);
                mysqlManager.updateItemAmount(player.getUniqueId().toString(), "netherite_scrap", currentNetheriteScrap - requiredNetheriteScrap);
                mysqlManager.updateItemAmount(player.getUniqueId().toString(), "netherite_ingot", currentNetherite - requiredNetherite);
                mysqlManager.updateSwordLevel(player.getUniqueId().toString(), currentLevel + 1);
                giveSwordToPlayer(player, currentLevel + 1);
                updateItems(player);
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.upgradesword"));
            } else {
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughtmoneyupgrade", "", ""));
            }
        } else {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughtitemsupgrade", "", ""));
        }
    }

    private int[] getUpgradeData(int level) {
        int[][] upgradeData = {
                {10, 6, 365, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {15, 11, 730, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {20, 16, 1095, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {25, 22, 1460, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {30, 27, 1825, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {35, 32, 2190, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {40, 38, 2555, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {45, 43, 2920, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {50, 48, 3285, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {55, 54, 3650, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {60, 59, 4015, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {65, 65, 4380, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {70, 70, 4745, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {75, 75, 5110, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {80, 81, 5475, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {85, 86, 5840, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {90, 91, 6205, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {95, 97, 6570, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {100, 102, 6935, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {105, 108, 7300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {110, 113, 7665, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {115, 118, 8030, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {120, 124, 8395, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {125, 129, 8760, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {130, 134, 9125, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {135, 140, 9490, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {140, 145, 9855, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {145, 151, 10220, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {150, 156, 10585, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {155, 161, 10950, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {160, 167, 11315, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {165, 172, 11680, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {170, 177, 12045, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {175, 183, 12410, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {180, 188, 12775, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {185, 193, 13140, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {190, 199, 13505, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {195, 204, 13870, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {200, 210, 14235, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {205, 215, 14600, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {210, 220, 14965, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {215, 226, 15330, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {220, 231, 15695, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {225, 236, 16060, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 16424, 230, 242, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 16790, 235, 247, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 17155, 240, 253, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 17520, 245, 258, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 17855, 250, 263, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 18250, 255, 269, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 18615, 260, 274, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 18980, 265, 279, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 19345, 270, 285, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 19710, 275, 290, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 20075, 280, 301, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 20440, 285, 306, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 20805, 290, 312, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 21170, 295, 317, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 21535, 300, 323, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 21900, 305, 323, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 22265, 310, 333, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 22630, 315, 339, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 22995, 320, 344, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 23360, 325, 349, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 23725, 330, 355, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 24090, 335, 360, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 24455, 340, 365, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 24820, 345, 371, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 25185, 350, 376, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 25550, 355, 382, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 25915, 360, 387, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 26280, 365, 392, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 26645, 370, 398, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 27010, 375, 403, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 27375, 380, 408, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 27740, 385, 414, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 28105, 390, 419, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 28470, 395, 425, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 28835, 400, 430, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 29200, 405, 435, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 29565, 410, 441, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 29930, 415, 446, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 30295, 420, 451, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 30660, 425, 457, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        if (level >= 1 && level <= upgradeData.length) {
            return upgradeData[level - 1];
        } else {
            return null;
        }
    }

    private void giveSwordToPlayer(Player player, int level) {

        switch (level) {
            case 2:
                updatewoodenSword(player, 5.0, 2);
                break;
            case 3:
                updatewoodenSword(player, 6.0, 3);
                break;
            case 4:
                updatewoodenSword(player, 7.0, 4);
                break;
            case 5:
                updatewoodenSword(player, 8.0, 5);
                break;
            case 6:
                updatewoodenSword(player, 9.0, 6);
                break;
            case 7:
                updatewoodenSword(player, 10.0, 7);
                break;
            case 8:
                updatewoodenSword(player, 11.0, 8);
                break;
            case 9:
                updatewoodenSword(player, 12.0, 9);
                break;
            case 10:
                updatewoodenSword(player, 13.0, 10);
                break;
            case 11:
                updatewoodenSword(player, 14.0, 11);
                break;
            case 12:
                updatewoodenSword(player, 15.0, 12);
                break;
            case 13:
                updatewoodenSword(player, 16.0, 13);
                break;
            case 14:
                updatewoodenSword(player, 17.0, 14);
                break;
            case 15:
                updatewoodenSword(player, 18.0, 15);
                break;
            case 16:
                updatewoodenSword(player, 19.0, 16);
                break;
            case 17:
                updatewoodenSword(player, 20.0, 17);
                break;
            case 18:
                updatewoodenSword(player, 21.0, 18);
                break;
            case 19:
                updatewoodenSword(player, 22.0, 19);
                break;
            case 20:
                updatewoodenSword(player, 23.0, 20);
                break;
            case 21:
                updatewoodenSword(player, 24.0, 21);
                break;
            case 22:
                updatewoodenSword(player, 25.0, 22);
                break;
            case 23:
                updatewoodenSword(player, 26.0, 23);
                break;
            case 24:
                updatewoodenSword(player, 27.0, 24);
                break;
            case 25:
                updatewoodenSword(player, 28.0, 25);
                break;
            case 26:
                updatewoodenSword(player, 29.0, 26);
                break;
            case 27:
                updatewoodenSword(player, 30.0, 27);
                break;
            case 28:
                updatewoodenSword(player, 31.0, 28);
                break;
            case 29:
                updatewoodenSword(player, 32.0, 29);
                break;
            case 30:
                updatewoodenSword(player, 33.0, 30);
                break;
            case 31:
                updatewoodenSword(player, 34.0, 31);
                break;
            case 32:
                updatewoodenSword(player, 35.0, 32);
                break;
            case 33:
                updatewoodenSword(player, 36.0, 33);
                break;
            case 34:
                updatewoodenSword(player, 37.0, 34);
                break;
            case 35:
                updatewoodenSword(player, 38.0, 35);
                break;
            case 36:
                updatewoodenSword(player, 39.0, 36);
                break;
            case 37:
                updatewoodenSword(player, 40.0, 37);
                break;
            case 38:
                updatewoodenSword(player, 41.0, 38);
                break;
            case 39:
                updatewoodenSword(player, 42.0, 39);
                break;
            case 40:
                updatewoodenSword(player, 43.0, 40);
                break;
            case 41:
                updatewoodenSword(player, 44.0, 41);
                break;
            case 42:
                updatewoodenSword(player, 45.0, 42);
                break;
            case 43:
                updatewoodenSword(player, 46.0, 43);
                break;
            case 44:
                updatewoodenSword(player, 47.0, 44);
                break;
            case 45:
                updatewoodenSword(player, 48.0, 45);
                break;
            case 46:
                updatestoneSword(player, 49.0, 46);
                break;
            case 47:
                updatestoneSword(player, 50.0, 47);
                break;
            case 48:
                updatestoneSword(player, 51.0, 48);
                break;
            case 49:
                updatestoneSword(player, 52.0, 49);
                break;
            case 50:
                updatestoneSword(player, 53.0, 50);
                break;
            case 51:
                updatestoneSword(player, 54.0, 51);
                break;
            case 52:
                updatestoneSword(player, 55.0, 52);
                break;
            case 53:
                updatestoneSword(player, 56.0, 53);
                break;
            case 54:
                updatestoneSword(player, 57.0, 54);
                break;
            case 55:
                updatestoneSword(player, 58.0, 55);
                break;
            case 56:
                updatestoneSword(player, 59.0, 56);
                break;
            case 57:
                updatestoneSword(player, 60.0, 57);
                break;
            case 58:
                updatestoneSword(player, 61.0, 58);
                break;
            case 59:
                updatestoneSword(player, 62.0, 59);
                break;
            case 60:
                updatestoneSword(player, 63.0, 60);
                break;
            case 61:
                updatestoneSword(player, 64.0, 61);
                break;
            case 62:
                updatestoneSword(player, 65.0, 62);
                break;
            case 63:
                updatestoneSword(player, 66.0, 63);
                break;
            case 64:
                updatestoneSword(player, 67.0, 64);
                break;
            case 65:
                updatestoneSword(player, 68.0, 65);
                break;
            case 66:
                updatestoneSword(player, 69.0, 66);
                break;
            case 67:
                updatestoneSword(player, 70.0, 67);
                break;
            case 68:
                updatestoneSword(player, 71.0, 68);
                break;
            case 69:
                updatestoneSword(player, 72.0, 69);
                break;
            case 70:
                updatestoneSword(player, 73.0, 70);
                break;
            case 71:
                updatestoneSword(player, 74.0, 71);
                break;
            case 72:
                updatestoneSword(player, 75.0, 72);
                break;
            case 73:
                updatestoneSword(player, 76.0, 73);
                break;
            case 74:
                updatestoneSword(player, 77.0, 74);
                break;
            case 75:
                updatestoneSword(player, 78.0, 75);
                break;
            case 76:
                updatestoneSword(player, 79.0, 76);
                break;
            case 77:
                updatestoneSword(player, 80.0, 77);
                break;
            case 78:
                updatestoneSword(player, 81.0, 78);
                break;
            case 79:
                updatestoneSword(player, 82.0, 79);
                break;
            case 80:
                updatestoneSword(player, 83.0, 80);
                break;
            case 81:
                updatestoneSword(player, 84.0, 81);
                break;
            case 82:
                updatestoneSword(player, 85.0, 82);
                break;
            case 83:
                updatestoneSword(player, 86.0, 83);
                break;
            case 84:
                updatestoneSword(player, 87.0, 84);
                break;
            case 85:
                updatestoneSword(player, 88.0, 85);
                break;
            case 86:
                updateironSword(player, 89.0, 86);
                break;
            case 87:
                updateironSword(player, 90.0, 87);
                break;
            case 88:
                updateironSword(player, 91.0, 88);
                break;
            case 89:
                updateironSword(player, 92.0, 89);
                break;
            case 90:
                updateironSword(player, 93.0, 90);
                break;
            case 91:
                updateironSword(player, 94.0, 91);
                break;
            case 92:
                updateironSword(player, 95.0, 92);
                break;
            case 93:
                updateironSword(player, 96.0, 93);
                break;
            case 94:
                updateironSword(player, 97.0, 94);
                break;
            case 95:
                updateironSword(player, 98.0, 95);
                break;
            case 96:
                updateironSword(player, 99.0, 96);
                break;
            case 97:
                updateironSword(player, 100.0, 97);
                break;
            case 98:
                updateironSword(player, 101.0, 98);
                break;
            case 99:
                updateironSword(player, 102.0, 99);
                break;
            case 100:
                updateironSword(player, 103.0, 100);
                break;
            case 101:
                updateironSword(player, 104.0, 101);
                break;
            case 102:
                updateironSword(player, 105.0, 102);
                break;
            case 103:
                updateironSword(player, 106.0, 103);
                break;
            case 104:
                updateironSword(player, 107.0, 104);
                break;
            case 105:
                updateironSword(player, 108.0, 105);
                break;
            case 106:
                updateironSword(player, 109.0, 106);
                break;
            case 107:
                updateironSword(player, 110.0, 107);
                break;
            case 108:
                updateironSword(player, 111.0, 108);
                break;
            case 109:
                updateironSword(player, 112.0, 109);
                break;
            case 110:
                updateironSword(player, 113.0, 110);
                break;
            case 111:
                updateironSword(player, 114.0, 111);
                break;
            case 112:
                updateironSword(player, 115.0, 112);
                break;
            case 113:
                updateironSword(player, 116.0,113);
                break;
            case 114:
                updateironSword(player, 117.0, 114);
                break;
            case 115:
                updateironSword(player, 118.0, 115);
                break;
            case 116:
                updateironSword(player, 119.0, 116);
                break;
            case 117:
                updateironSword(player, 120.0, 117);
                break;
            case 118:
                updateironSword(player, 121.0, 118);
                break;
            case 119:
                updateironSword(player, 122.0, 119);
                break;
            case 120:
                updateironSword(player, 123.0, 120);
                break;
            case 121:
                updateironSword(player, 124.0, 121);
                break;
            case 122:
                updateironSword(player, 125.0, 122);
                break;
            case 123:
                updateironSword(player, 126.0, 123);
                break;
            case 124:
                updateironSword(player, 127.0, 124);
                break;
            case 125:
                updateironSword(player, 128.0, 125);
                break;
            case 126:
                updateironSword(player, 129.0, 126);
                break;
            case 127:
                updateironSword(player, 130.0, 127);
                break;
            case 128:
                updateironSword(player, 131.0, 128);
                break;
            case 129:
                updateironSword(player, 132.0, 129);
                break;
            case 130:
                updateironSword(player, 133.0, 130);
                break;
            case 131:
                updategoldSword(player, 134.0, 131);
                break;
            case 132:
                updategoldSword(player, 135.0, 132);
                break;
            case 133:
                updategoldSword(player, 136.0, 133);
                break;
            case 134:
                updategoldSword(player, 137.0, 134);
                break;
            case 135:
                updategoldSword(player, 138.0, 135);
                break;
            case 136:
                updategoldSword(player, 139.0, 136);
                break;
            case 137:
                updategoldSword(player, 140.0, 137);
                break;
            case 138:
                updategoldSword(player, 141.0, 138);
                break;
            case 139:
                updategoldSword(player, 142.0, 139);
                break;
            case 140:
                updategoldSword(player, 143.0, 140);
                break;
            case 141:
                updategoldSword(player, 144.0, 141);
                break;
            case 142:
                updategoldSword(player, 145.0, 142);
                break;
            case 143:
                updategoldSword(player, 146.0, 143);
                break;
            case 144:
                updategoldSword(player, 147.0, 144);
                break;
            case 145:
                updategoldSword(player, 148.0, 145);
                break;
            case 146:
                updategoldSword(player, 149.0, 146);
                break;
            case 147:
                updategoldSword(player, 150.0, 147);
                break;
            case 148:
                updategoldSword(player, 151.0, 148);
                break;
            case 149:
                updategoldSword(player, 152.0, 149);
                break;
            case 150:
                updategoldSword(player, 153.0, 150);
                break;
            case 151:
                updategoldSword(player, 154.0, 151);
                break;
            case 152:
                updategoldSword(player, 155.0, 152);
                break;
            case 153:
                updategoldSword(player, 156.0, 153);
                break;
            case 154:
                updategoldSword(player, 157.0, 154);
                break;
            case 155:
                updategoldSword(player, 158.0, 155);
                break;
            case 156:
                updategoldSword(player, 159.0, 156);
                break;
            case 157:
                updategoldSword(player, 160.0, 157);
                break;
            case 158:
                updategoldSword(player, 161.0, 158);
                break;
            case 159:
                updategoldSword(player, 162.0, 159);
                break;
            case 160:
                updategoldSword(player, 163.0, 160);
                break;
            case 161:
                updategoldSword(player, 164.0, 161);
                break;
            case 162:
                updategoldSword(player, 165.0, 162);
                break;
            case 163:
                updategoldSword(player, 166.0, 163);
                break;
            case 164:
                updategoldSword(player, 167.0, 164);
                break;
            case 165:
                updategoldSword(player, 168.0, 165);
                break;
            case 166:
                updategoldSword(player, 169.0, 166);
                break;
            case 167:
                updategoldSword(player, 170.0, 167);
                break;
            case 168:
                updategoldSword(player, 171.0, 168);
                break;
            case 169:
                updategoldSword(player, 172.0, 169);
                break;
            case 170:
                updategoldSword(player, 173.0, 170);
                break;
            case 171:
                updategoldSword(player, 174.0, 171);
                break;
            case 172:
                updategoldSword(player, 175.0, 172);
                break;
            case 173:
                updategoldSword(player, 176.0, 173);
                break;
            case 174:
                updategoldSword(player, 177.0, 174);
                break;
            case 175:
                updategoldSword(player, 178.0, 175);
                break;
            case 176:
                updatediamondSword(player, 179.0, 176);
                break;
            case 177:
                updatediamondSword(player, 180.0, 177);
                break;
            case 178:
                updatediamondSword(player, 181.0, 178);
                break;
            case 179:
                updatediamondSword(player, 182.0, 179);
                break;
            case 180:
                updatediamondSword(player, 183.0, 180);
                break;
            case 181:
                updatediamondSword(player, 184.0, 181);
                break;
            case 182:
                updatediamondSword(player, 185.0, 182);
                break;
            case 183:
                updatediamondSword(player, 186.0, 183);
                break;
            case 184:
                updatediamondSword(player, 187.0, 184);
                break;
            case 185:
                updatediamondSword(player, 188.0, 185);
                break;
            case 186:
                updatediamondSword(player, 189.0, 186);
                break;
            case 187:
                updatediamondSword(player, 190.0, 187);
                break;
            case 188:
                updatediamondSword(player, 191.0, 188);
                break;
            case 189:
                updatediamondSword(player, 192.0, 189);
                break;
            case 190:
                updatediamondSword(player, 193.0, 190);
                break;
            case 191:
                updatediamondSword(player, 194.0, 191);
                break;
            case 192:
                updatediamondSword(player, 195.0, 192);
                break;
            case 193:
                updatediamondSword(player, 196.0, 193);
                break;
            case 194:
                updatediamondSword(player, 197.0, 194);
                break;
            case 195:
                updatediamondSword(player, 198.0, 195);
                break;
            case 196:
                updatediamondSword(player, 199.0, 196);
                break;
            case 197:
                updatediamondSword(player, 200.0, 197);
                break;
            case 198:
                updatediamondSword(player, 201.0, 198);
                break;
            case 199:
                updatediamondSword(player, 202.0, 199);
                break;
            case 200:
                updatediamondSword(player, 203.0, 200);
                break;
            case 201:
                updatediamondSword(player, 204.0, 201);
                break;
            case 202:
                updatediamondSword(player, 205.0, 202);
                break;
            case 203:
                updatediamondSword(player, 206.0, 203);
                break;
            case 204:
                updatediamondSword(player, 207.0, 204);
                break;
            case 205:
                updatediamondSword(player, 208.0, 205);
                break;
            case 206:
                updatediamondSword(player, 209.0, 206);
                break;
            case 207:
                updatediamondSword(player, 210.0, 207);
                break;
            case 208:
                updatediamondSword(player, 211.0, 208);
                break;
            case 209:
                updatediamondSword(player, 212.0, 209);
                break;
            case 210:
                updatediamondSword(player, 213.0, 210);
                break;
            case 211:
                updatediamondSword(player, 214.0, 211);
                break;
            case 212:
                updatediamondSword(player, 215.0, 212);
                break;
            case 213:
                updatediamondSword(player, 216.0, 213);
                break;
            case 214:
                updatediamondSword(player, 217.0, 214);
                break;
            case 215:
                updatediamondSword(player, 218.0, 215);
                break;
            case 216:
                updatenetheriteSword(player, 219.0, 216);
                break;
            case 217:
                updatenetheriteSword(player, 220.0, 217);
                break;
            case 218:
                updatenetheriteSword(player, 221.0, 218);
                break;
            case 219:
                updatenetheriteSword(player, 222.0, 219);
                break;
            case 220:
                updatenetheriteSword(player, 223.0, 220);
                break;
            case 221:
                updatenetheriteSword(player, 224.0, 221);
                break;
            case 222:
                updatenetheriteSword(player, 225.0, 222);
                break;
            case 223:
                updatenetheriteSword(player, 226.0, 223);
                break;
            case 224:
                updatenetheriteSword(player, 227.0, 224);
                break;
            case 225:
                updatenetheriteSword(player, 228.0, 225);
                break;
            case 226:
                updatenetheriteSword(player, 229.0, 226);
                break;
            case 227:
                updatenetheriteSword(player, 230.0, 227);
                break;
            case 228:
                updatenetheriteSword(player, 231.0, 228);
                break;
            case 229:
                updatenetheriteSword(player, 232.0, 229);
                break;
            case 230:
                updatenetheriteSword(player, 233.0, 230);
                break;
            case 231:
                updatenetheriteSword(player, 234.0, 231);
                break;
            case 232:
                updatenetheriteSword(player, 235.0, 232);
                break;
            case 233:
                updatenetheriteSword(player, 236.0, 233);
                break;
            case 234:
                updatenetheriteSword(player, 237.0, 234);
                break;
            case 235:
                updatenetheriteSword(player, 238.0, 235);
                break;
            case 236:
                updatenetheriteSword(player, 239.0, 236);
                break;
            case 237:
                updatenetheriteSword(player, 240.0, 237);
                break;
            case 238:
                updatenetheriteSword(player, 241.0, 238);
                break;
            case 239:
                updatenetheriteSword(player, 242.0, 239);
                break;
            case 240:
                updatenetheriteSword(player, 243.0, 240);
                break;
            case 241:
                updatenetheriteSword(player, 244.0, 241);
                break;
            case 242:
                updatenetheriteSword(player, 245.0, 242);
                break;
            case 243:
                updatenetheriteSword(player, 246.0, 243);
                break;
            case 244:
                updatenetheriteSword(player, 247.0, 244);
                break;
            case 245:
                updatenetheriteSword(player, 248.0, 245);
                break;
            case 246:
                updatenetheriteSword(player, 249.0, 246);
                break;
            case 247:
                updatenetheriteSword(player, 250.0, 247);
                break;
            case 248:
                updatenetheriteSword(player, 251.0, 248);
                break;
            case 249:
                updatenetheriteSword(player, 252.0, 249);
                break;
            case 250:
                updatenetheriteSword(player, 253.0, 250);
                break;
            case 251:
                updatenetheriteSword(player, 254.0, 251);
                break;
            case 252:
                updatenetheriteSword(player, 255.0, 252);
                break;
            case 253:
                updatenetheriteSword(player, 256.0, 253);
                break;
            case 254:
                updatenetheriteSword(player, 257.0, 254);
                break;
            case 255:
                updatenetheriteSword(player, 258.0, 255);
                break;
            case 256:
                updatenetheriteSword(player, 259.0, 256);
                break;
            case 257:
                updatenetheriteSword(player, 260.0, 257);
                break;
            case 258:
                updatenetheriteSword(player, 261.0, 258);
                break;
            case 259:
                updatenetheriteSword(player, 262.0, 259);
                break;
            case 260:
                updatenetheriteSword(player, 263.0, 260);
                break;
            default:
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.upgradeswordfailure","",""));
                return;
        }
    }
    private boolean removeMoney(Player p, double amount) {
        double currentMoney = 0;
        String currentMoneyString = mysqlManager.getBalance(p.getUniqueId().toString());
        currentMoneyString = currentMoneyString.replace(",", "");
        currentMoney = Double.parseDouble(currentMoneyString);

        double newMoney = currentMoney - amount;
        if (newMoney < 0) {
            return false;
        }

        String formattedMoney = String.format(Locale.ENGLISH, "%,.2f", newMoney);
        mysqlManager.updateBalance(p.getUniqueId().toString(), formattedMoney);
        scoreboardBuilder.updateMoney(p);

        return true;
    }
    private void updateItems(Player player) {
        ItemStack rawcopper = new ItemStack(Material.RAW_COPPER);
        ItemMeta rawcoppermeta = rawcopper.getItemMeta();
        rawcoppermeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "raw_copper"));
        rawcoppermeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        rawcoppermeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        rawcopper.setItemMeta(rawcoppermeta);
        player.getInventory().setItem(9, rawcopper);

        ItemStack copperingot = new ItemStack(Material.COPPER_INGOT);
        ItemMeta copperingotmeta = copperingot.getItemMeta();
        copperingotmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "copper_ingot"));
        copperingotmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        copperingotmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        copperingot.setItemMeta(copperingotmeta);
        player.getInventory().setItem(10, copperingot);

        ItemStack rawgold = new ItemStack(Material.RAW_GOLD);
        ItemMeta rawgoldmeta = rawgold.getItemMeta();
        rawgoldmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "raw_gold"));
        rawgoldmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        rawgoldmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        rawgold.setItemMeta(rawgoldmeta);
        player.getInventory().setItem(18, rawgold);

        ItemStack goldingot = new ItemStack(Material.GOLD_INGOT);
        ItemMeta goldingotmeta = goldingot.getItemMeta();
        goldingotmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "gold_ingot"));
        goldingotmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        goldingotmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        goldingot.setItemMeta(goldingotmeta);
        player.getInventory().setItem(19, goldingot);

        ItemStack rawiron = new ItemStack(Material.RAW_IRON);
        ItemMeta rawironmeta = rawiron.getItemMeta();
        rawironmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "raw_iron"));
        rawironmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        rawironmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        rawiron.setItemMeta(rawironmeta);
        player.getInventory().setItem(27, rawiron);

        ItemStack ironingot = new ItemStack(Material.IRON_INGOT);
        ItemMeta ironingotmeta = ironingot.getItemMeta();
        ironingotmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "iron_ingot"));
        ironingotmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        ironingotmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ironingot.setItemMeta(ironingotmeta);
        player.getInventory().setItem(28, ironingot);

        ItemStack coal = new ItemStack(Material.COAL);
        ItemMeta coalmeta = coal.getItemMeta();
        coalmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "coal"));
        coalmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        coalmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        coal.setItemMeta(coalmeta);
        player.getInventory().setItem(22, coal);

        ItemStack cobblestone = new ItemStack(Material.COBBLESTONE);
        ItemMeta cobblestonemeta = cobblestone.getItemMeta();
        cobblestonemeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "cobblestone"));
        cobblestonemeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        cobblestonemeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        cobblestone.setItemMeta(cobblestonemeta);
        player.getInventory().setItem(17, cobblestone);

        ItemStack stone = new ItemStack(Material.STONE);
        ItemMeta stonemeta = stone.getItemMeta();
        stonemeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "stone"));
        stonemeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        stonemeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        stone.setItemMeta(stonemeta);
        player.getInventory().setItem(16, stone);

        ItemStack diamondore = new ItemStack(Material.DIAMOND_ORE);
        ItemMeta diamondoremeta = diamondore.getItemMeta();
        diamondoremeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "diamond_ore"));
        diamondoremeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        diamondoremeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        diamondore.setItemMeta(diamondoremeta);
        player.getInventory().setItem(26, diamondore);

        ItemStack diamond = new ItemStack(Material.DIAMOND);
        ItemMeta diamondmeta = diamond.getItemMeta();
        diamondmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "diamond"));
        diamondmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        diamondmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        diamond.setItemMeta(diamondmeta);
        player.getInventory().setItem(25, diamond);

        ItemStack netherite = new ItemStack(Material.NETHERITE_INGOT);
        ItemMeta netheritemeta = netherite.getItemMeta();
        netheritemeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "netherite_ingot"));
        netheritemeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        netheritemeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        netherite.setItemMeta(netheritemeta);
        player.getInventory().setItem(34, netherite);

        ItemStack netheritescrap = new ItemStack(Material.NETHERITE_SCRAP);
        ItemMeta netheritescrapmeta = netheritescrap.getItemMeta();
        netheritescrapmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "netherite_scrap"));
        netheritescrapmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        netheritescrapmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        netheritescrap.setItemMeta(netheritescrapmeta);
        player.getInventory().setItem(35, netheritescrap);
    }
    private void updatewoodenSword(Player player, double damage, int level) {
        ItemStack woodensword = new ItemStack(Material.WOODEN_SWORD);
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
        ItemMeta woodenmeta = woodensword.getItemMeta();
        woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv."+ level + "§7>>");
        woodenmeta.setLore(Collections.singletonList("§9"+ damage + " Angrifsschaden"));
        woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
        woodenmeta.setUnbreakable(true);
        woodensword.setItemMeta(woodenmeta);
        player.getInventory().setItem(0, woodensword);
    }
    private void updatestoneSword(Player player, double damage, int level) {
        ItemStack stonesword = new ItemStack(Material.STONE_SWORD);
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
        ItemMeta stonemeta = stonesword.getItemMeta();
        stonemeta.setDisplayName("§7<< §6Steinschwert §7- §aLv."+ level + "§7>>");
        stonemeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));;
        stonemeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        stonemeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
        stonemeta.setUnbreakable(true);
        stonesword.setItemMeta(stonemeta);
        player.getInventory().setItem(0, stonesword);
    }
    private void updateironSword(Player player, double damage, int level) {
        ItemStack ironsword = new ItemStack(Material.IRON_SWORD);
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
        ItemMeta ironmeta = ironsword.getItemMeta();
        ironmeta.setDisplayName("§7<< §6Steinschwert §7- §aLv."+ level + "§7>>");
        ironmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
        ironmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ironmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
        ironmeta.setUnbreakable(true);
        ironsword.setItemMeta(ironmeta);
        player.getInventory().setItem(0, ironsword);
    }
    private void updategoldSword(Player player, double damage, int level) {
        ItemStack goldsword = new ItemStack(Material.GOLDEN_SWORD);
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
        ItemMeta goldmeta = goldsword.getItemMeta();
        goldmeta.setDisplayName("§7<< §6Steinschwert §7- §aLv."+ level + "§7>>");
        goldmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
        goldmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        goldmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
        goldmeta.setUnbreakable(true);
        goldsword.setItemMeta(goldmeta);
        player.getInventory().setItem(0, goldsword);
    }
    private void updatediamondSword(Player player, double damage, int level) {
        ItemStack diamondsword = new ItemStack(Material.GOLDEN_SWORD);
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
        ItemMeta diamondmeta = diamondsword.getItemMeta();
        diamondmeta.setDisplayName("§7<< §6Steinschwert §7- §aLv."+ level + "§7>>");
        diamondmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
        diamondmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        diamondmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
        diamondmeta.setUnbreakable(true);
        diamondsword.setItemMeta(diamondmeta);
        player.getInventory().setItem(0, diamondsword);
    }
    private void updatenetheriteSword(Player player, double damage, int level) {
        ItemStack netheritesword = new ItemStack(Material.GOLDEN_SWORD);
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
        ItemMeta netheritemeta = netheritesword.getItemMeta();
        netheritemeta.setDisplayName("§7<< §6Steinschwert §7- §aLv."+ level + "§7>>");
        netheritemeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
        netheritemeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        netheritemeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
        netheritemeta.setUnbreakable(true);
        netheritesword.setItemMeta(netheritemeta);
        player.getInventory().setItem(0, netheritesword);
    }
}

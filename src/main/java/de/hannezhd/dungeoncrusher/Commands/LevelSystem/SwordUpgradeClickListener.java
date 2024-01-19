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
        int requiredRawCopper = 0;
        int requiredCopperIngots = 0;
        double requiredMoney = 0;

        switch (currentLevel) {
            case 1:
                requiredRawCopper = 10;
                requiredCopperIngots = 6;
                requiredMoney = 365;
                break;
            case 2:
                requiredRawCopper = 15;
                requiredCopperIngots = 11;
                requiredMoney = 730;
                break;
            case 3:
                requiredRawCopper = 20;
                requiredCopperIngots = 16;
                requiredMoney = 1095;
                break;
            case 4:
                requiredRawCopper = 25;
                requiredCopperIngots = 22;
                requiredMoney = 1460;
                break;
            case 5:
                requiredRawCopper = 30;
                requiredCopperIngots = 27;
                requiredMoney = 1825;
                break;
            case 6:
                requiredRawCopper = 35;
                requiredCopperIngots = 32;
                requiredMoney = 2190;
                break;
            case 7:
                requiredRawCopper = 40;
                requiredCopperIngots = 38;
                requiredMoney = 2555;
                break;
            case 8:
                requiredRawCopper = 45;
                requiredCopperIngots = 43;
                requiredMoney = 2920;
                break;
            case 9:
                requiredRawCopper = 50;
                requiredCopperIngots = 48;
                requiredMoney = 3285;
                break;
            case 10:
                requiredRawCopper = 55;
                requiredCopperIngots = 54;
                requiredMoney = 3650;
                break;
            case 11:
                requiredRawCopper = 60;
                requiredCopperIngots = 59;
                requiredMoney = 4015;
                break;
            case 12:
                requiredRawCopper = 65;
                requiredCopperIngots = 65;
                requiredMoney = 4380;
                break;
            case 13:
                requiredRawCopper = 70;
                requiredCopperIngots = 70;
                requiredMoney = 4745;
                break;
            case 14:
                requiredRawCopper = 75;
                requiredCopperIngots = 75;
                requiredMoney = 5110;
                break;
            case 15:
                requiredRawCopper = 80;
                requiredCopperIngots = 81;
                requiredMoney = 5475;
                break;
            case 16:
                requiredRawCopper = 85;
                requiredCopperIngots = 86;
                requiredMoney = 5840;
                break;
            case 17:
                requiredRawCopper = 90;
                requiredCopperIngots = 91;
                requiredMoney = 6205;
                break;
            case 18:
                requiredRawCopper = 95;
                requiredCopperIngots = 97;
                requiredMoney = 6570;
                break;
            case 19:
                requiredRawCopper = 100;
                requiredCopperIngots = 102;
                requiredMoney = 6935;
                break;
            case 20:
                requiredRawCopper = 105;
                requiredCopperIngots = 108;
                requiredMoney = 7300;
                break;
            case 21:
                requiredRawCopper = 110;
                requiredCopperIngots = 113;
                requiredMoney = 7665;
                break;
            case 22:
                requiredRawCopper = 115;
                requiredCopperIngots = 118;
                requiredMoney = 8030;
                break;
            case 23:
                requiredRawCopper = 120;
                requiredCopperIngots = 124;
                requiredMoney = 8395;
                break;
            case 24:
                requiredRawCopper = 125;
                requiredCopperIngots = 129;
                requiredMoney = 8760;
                break;
            case 25:
                requiredRawCopper = 130;
                requiredCopperIngots = 134;
                requiredMoney = 9125;
                break;
            case 26:
                requiredRawCopper = 135;
                requiredCopperIngots = 140;
                requiredMoney = 9490;
                break;
            case 27:
                requiredRawCopper = 140;
                requiredCopperIngots = 145;
                requiredMoney = 9855;
                break;
            case 28:
                requiredRawCopper = 145;
                requiredCopperIngots = 151;
                requiredMoney = 10220;
                break;
            case 29:
                requiredRawCopper = 150;
                requiredCopperIngots = 156;
                requiredMoney = 10585;
                break;
            case 30:
                requiredRawCopper = 155;
                requiredCopperIngots = 161;
                requiredMoney = 10950;
                break;
            case 31:
                requiredRawCopper = 160;
                requiredCopperIngots = 167;
                requiredMoney = 11315;
                break;
            case 32:
                requiredRawCopper = 165;
                requiredCopperIngots = 172;
                requiredMoney = 11680;
                break;
            case 33:
                requiredRawCopper = 170;
                requiredCopperIngots = 177;
                requiredMoney = 12045;
                break;
            case 34:
                requiredRawCopper = 175;
                requiredCopperIngots = 183;
                requiredMoney = 12410;
                break;
            case 35:
                requiredRawCopper = 180;
                requiredCopperIngots = 188;
                requiredMoney = 12775;
                break;
            case 36:
                requiredRawCopper = 185;
                requiredCopperIngots = 193;
                requiredMoney = 13140;
                break;
            case 37:
                requiredRawCopper = 190;
                requiredCopperIngots = 199;
                requiredMoney = 13505;
                break;
            case 38:
                requiredRawCopper = 195;
                requiredCopperIngots = 204;
                requiredMoney = 13870;
                break;
            case 39:
                requiredRawCopper = 200;
                requiredCopperIngots = 210;
                requiredMoney = 14235;
                break;
            case 40:
                requiredRawCopper = 205;
                requiredCopperIngots = 215;
                requiredMoney = 14600;
                break;
            case 41:
                requiredRawCopper = 210;
                requiredCopperIngots = 220;
                requiredMoney = 14965;
                break;
            case 42:
                requiredRawCopper = 215;
                requiredCopperIngots = 226;
                requiredMoney = 15330;
                break;
            case 43:
                requiredRawCopper = 220;
                requiredCopperIngots = 231;
                requiredMoney = 15695;
                break;
            case 44:
                requiredRawCopper = 225;
                requiredCopperIngots = 236;
                requiredMoney = 16060;
                break;
            case 45:
                break;
            case 46:
                break;
            case 47:
                break;
            case 48:
                break;
            case 49:
                break;
            case 50:
                break;
            case 51:
                break;
            case 52:
                break;
            case 53:
                break;
            case 54:
                break;
            case 55:
                break;
            case 56:
                break;
            case 57:
                break;
            case 58:
                break;
            case 59:
                break;
            case 60:
                break;
            case 61:
                break;
            case 62:
                break;
            case 63:
                break;
            case 64:
                break;
            case 65:
                break;
            case 66:
                break;
            case 67:
                break;
            case 68:
                break;
            case 69:
                break;
            case 70:
                break;
            case 71:
                break;
            case 72:
                break;
            case 73:
                break;
            case 74:
                break;
            case 75:
                break;
            case 76:
                break;
            case 77:
                break;
            case 78:
                break;
            case 79:
                break;
            case 80:
                break;
            case 81:
                break;
            case 82:
                break;
            case 83:
                break;
            case 84:
                break;
            case 85:
                break;
            case 86:
                break;
            case 87:
                break;
            case 88:
                break;
            case 89:
                break;
            case 90:
                break;
            case 91:
                break;
            case 92:
                break;
            case 93:
                break;
            case 94:
                break;
            case 95:
                break;
            case 96:
                break;
            case 97:
                break;
            case 98:
                break;
            case 99:
                break;
            case 100:
                break;
            case 101:
                break;
            case 102:
                break;
            case 103:
                break;
            case 104:
                break;
            case 105:
                break;
            case 106:
                break;
            case 107:
                break;
            case 108:
                break;
            case 109:
                break;
            case 110:
                break;
            case 111:
                break;
            case 112:
                break;
            case 113:
                break;
            case 114:
                break;
            case 115:
                break;
            case 116:
                break;
            case 117:
                break;
            case 118:
                break;
            case 119:
                break;
            case 120:
                break;
            case 121:
                break;
            case 122:
                break;
            case 123:
                break;
            case 124:
                break;
            case 125:
                break;
            case 126:
                break;
            case 127:
                break;
            case 128:
                break;
            case 129:
                break;
            case 130:
                break;
            case 131:
                break;
            case 132:
                break;
            case 133:
                break;
            case 134:
                break;
            case 135:
                break;
            case 136:
                break;
            case 137:
                break;
            case 138:
                break;
            case 139:
                break;
            case 140:
                break;
            case 141:
                break;
            case 142:
                break;
            case 143:
                break;
            case 144:
                break;
            case 145:
                break;
            case 146:
                break;
            case 147:
                break;
            case 148:
                break;
            case 149:
                break;
            case 150:
                break;
            case 151:
                break;
            case 152:
                break;
            case 153:
                break;
            case 154:
                break;
            case 155:
                break;
            case 156:
                break;
            case 157:
                break;
            case 158:
                break;
            case 159:
                break;
            case 160:
                break;
            case 161:
                break;
            case 162:
                break;
            case 163:
                break;
            case 164:
                break;
            case 165:
                break;
            case 166:
                break;
            case 167:
                break;
            case 168:
                break;
            case 169:
                break;
            case 170:
                break;
            case 171:
                break;
            case 172:
                break;
            case 173:
                break;
            case 174:
                break;
            case 175:
                break;
            case 176:
                break;
            case 177:
                break;
            case 178:
                break;
            case 179:
                break;
            case 180:
                break;
            case 181:
                break;
            case 182:
                break;
            case 183:
                break;
            case 184:
                break;
            case 185:
                break;
            case 186:
                break;
            case 187:
                break;
            case 188:
                break;
            case 189:
                break;
            case 190:
                break;
            case 191:
                break;
            case 192:
                break;
            case 193:
                break;
            case 194:
                break;
            case 195:
                break;
            case 196:
                break;
            case 197:
                break;
            case 198:
                break;
            case 199:
                break;
            case 200:
                break;
            case 201:
                break;
            case 202:
                break;
            case 203:
                break;
            case 204:
                break;
            case 205:
                break;
            case 206:
                break;
            case 207:
                break;
            case 208:
                break;
            case 209:
                break;
            case 210:
                break;
            case 211:
                break;
            case 212:
                break;
            case 213:
                break;
            case 214:
                break;
            case 215:
                break;
            case 216:
                break;
            case 217:
                break;
            case 218:
                break;
            case 219:
                break;
            case 220:
                break;
            case 221:
                break;
            case 222:
                break;
            case 223:
                break;
            case 224:
                break;
            case 225:
                break;
            case 226:
                break;
            case 227:
                break;
            case 228:
                break;
            case 229:
                break;
            case 230:
                break;
            case 231:
                break;
            case 232:
                break;
            case 233:
                break;
            case 234:
                break;
            case 235:
                break;
            case 236:
                break;
            case 237:
                break;
            case 238:
                break;
            case 239:
                break;
            case 240:
                break;
            case 241:
                break;
            case 242:
                break;
            case 243:
                break;
            case 244:
                break;
            case 245:
                break;
            case 246:
                break;
            case 247:
                break;
            case 248:
                break;
            case 249:
                break;
            case 250:
                break;
            case 251:
                break;
            case 252:
                break;
            case 253:
                break;
            case 254:
                break;
            case 255:
                break;
            case 256:
                break;
            case 257:
                break;
            case 258:
                break;
            case 259:
                break;
            case 260:
                break;
            default:
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.upgradesword", "", ""));
                return;
        }


        int currentRawCopper = mysqlManager.getItemAmount(player.getUniqueId().toString(), "raw_copper");
        int currentCopperIngots = mysqlManager.getItemAmount(player.getUniqueId().toString(), "copper_ingot");
        if (currentRawCopper >= requiredRawCopper && currentCopperIngots >= requiredCopperIngots) {
            if (removeMoney(player, requiredMoney)) {
                mysqlManager.updateItemAmount(player.getUniqueId().toString(), "raw_copper", currentRawCopper - requiredRawCopper);
                mysqlManager.updateItemAmount(player.getUniqueId().toString(), "copper_ingot", currentCopperIngots - requiredCopperIngots);
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
                updatewoodenSword(player, 50.0, 47);
                break;
            case 48:
                updatestoneSword(player, 51.0, 48);
                break;
            case 49:
                updatewoodenSword(player, 52.0, 49);
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
                updateironSword(player, 101, 98);
                break;
            case 99:
                updateironSword(player, 102, 99);
                break;
            case 100:
                updateironSword(player, 103, 100);
                break;
            case 101:
                updateironSword(player, 104, 101);
                break;
            case 102:
                break;
            case 103:
                break;
            case 104:
                break;
            case 105:
                break;
            case 106:
                break;
            case 107:
                break;
            case 108:
                break;
            case 109:
                break;
            case 110:
                break;
            case 111:
                break;
            case 112:
                break;
            case 113:
                break;
            case 114:
                break;
            case 115:
                break;
            case 116:
                break;
            case 117:
                break;
            case 118:
                break;
            case 119:
                break;
            case 120:
                break;
            case 121:
                break;
            case 122:
                break;
            case 123:
                break;
            case 124:
                break;
            case 125:
                break;
            case 126:
                break;
            case 127:
                break;
            case 128:
                break;
            case 129:
                break;
            case 130:
                break;
            case 131:
                break;
            case 132:
                break;
            case 133:
                break;
            case 134:
                break;
            case 135:
                break;
            case 136:
                break;
            case 137:
                break;
            case 138:
                break;
            case 139:
                break;
            case 140:
                break;
            case 141:
                break;
            case 142:
                break;
            case 143:
                break;
            case 144:
                break;
            case 145:
                break;
            case 146:
                break;
            case 147:
                break;
            case 148:
                break;
            case 149:
                break;
            case 150:
                break;
            case 151:
                break;
                case 152:
                    break;
            case 153:
                break;
            case 154:
                break;
            case 155:
                break;
            case 156:
                break;
            case 157:
                break;
            case 158:
                break;
            case 159:
                break;
            case 160:
                break;
            case 161:
                break;
            case 162:
                break;
            case 163:
                break;
            case 164:
                break;
            case 165:
                break;
            case 166:
                break;
            case 167:
                break;
            case 168:
                break;
            case 169:
                break;
            case 170:
                break;
            case 171:
                break;
            case 172:
                break;
            case 173:
                break;
            case 174:
                break;
            case 175:
                break;
            case 176:
                break;
            case 177:
                break;
            case 178:
                break;
            case 179:
                break;
            case 180:
                break;
            case 181:
                break;
            case 182:
                break;
            case 183:
                break;
            case 184:
                break;
            case 185:
                break;
            case 186:
                break;
            case 187:
                break;
            case 188:
                break;
            case 189:
                break;
            case 190:
                break;
            case 191:
                break;
            case 192:
                break;
            case 193:
                break;
            case 194:
                break;
            case 195:
                break;
            case 196:
                break;
            case 197:
                break;
            case 198:
                break;
            case 199:
                break;
            case 200:
                break;
            case 201:
                break;
            case 202:
                break;
            case 203:
                break;
            case 204:
                break;
            case 205:
                break;
            case 206:
                break;
            case 207:
                break;
            case 208:
                break;
            case 209:
                break;
            case 210:
                break;
            case 211:
                break;
            case 212:
                break;
            case 213:
                break;
            case 214:
                break;
            case 215:
                break;
            case 216:
                break;
            case 217:
                break;
            case 218:
                break;
            case 219:
                break;
            case 220:
                break;
            case 221:
                break;
            case 222:
                break;
            case 223:
                break;
            case 224:
                break;
            case 225:
                break;
            case 226:
                break;
            case 227:
                break;
            case 228:
                break;
            case 229:
                break;
            case 230:
                break;
            case 231:
                break;
            case 232:
                break;
            case 233:
                break;
            case 234:
                break;
            case 235:
                break;
            case 236:
                break;
            case 237:
                break;
            case 238:
                break;
            case 239:
                break;
            case 240:
                break;
            case 241:
                break;
            case 242:
                break;
            case 243:
                break;
            case 244:
                break;
                case 245:
                    break;
            case 246:
                break;
            case 247:
                break;
            case 248:
                break;
            case 249:
                break;
            case 250:
                break;
            case 251:
                break;
            case 252:
                break;
            case 253:
                break;
            case 254:
                break;
            case 255:
                break;
            case 256:
                break;
            case 257:
                break;
            case 258:
                break;
            case 259:
                break;
            case 260:
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
        woodenmeta.setLore(Collections.singletonList("§a "));
        woodenmeta.setLore(Collections.singletonList("§9"+ damage + " Angrifsschaden"));
        woodenmeta.setLore(Collections.singletonList("§b "));
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
        stonemeta.setLore(Collections.singletonList("§a "));
        stonemeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
        stonemeta.setLore(Collections.singletonList("§b "));
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
        ironmeta.setLore(Collections.singletonList("§a "));
        ironmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
        ironmeta.setLore(Collections.singletonList("§b "));
        ironmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ironmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
        ironmeta.setUnbreakable(true);
        ironsword.setItemMeta(ironmeta);
        player.getInventory().setItem(0, ironsword);
    }
}

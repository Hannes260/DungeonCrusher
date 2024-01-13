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

public class UpgradeClickListener implements Listener {
    MYSQLManager mysqlManager;
    ScoreboardBuilder scoreboardBuilder;
    public UpgradeClickListener(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager) {
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
                player.sendMessage("upgrade erfolg");
            } else {
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughtmoneyupgrade", "", ""));
            }
        } else {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughtitemsupgrade", "", ""));
        }
    }
    private void giveSwordToPlayer(Player player, int level) {

        ItemStack woodensword = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta woodenmeta = woodensword.getItemMeta();
        woodenmeta.setUnbreakable(true);

        switch (level) {
            case 2:
                double damage = 5.0;
                AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 2 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9"+ damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0,woodensword);
                player.updateInventory();
                break;
            case 3:
                damage = 6.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 3 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 4:
                damage = 7.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 4 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 5:
                damage = 8.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 5 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 6:
                damage = 9.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 6 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 7:
                damage = 10.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 7 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 8:
                damage = 11.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 8 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 9:
                damage = 12.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 9 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 10:
                damage = 13.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 10 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 11:
                damage = 14.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 11 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 12:
                damage = 15.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 12 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 13:
                damage = 16.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 13 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 14:
                damage = 17.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 14 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 15:
                damage = 18.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 15 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 16:
                damage = 19.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 16 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 17:
                damage = 20.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 17 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 18:
                damage = 21.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 18 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 19:
                damage = 22.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 19 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 20:
                damage = 23.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 20 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 21:
                damage = 24.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 21 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 22:
                damage = 25.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 22 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 23:
                damage = 26.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 23 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 24:
                damage = 27.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 24 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 25:
                damage = 28.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 25 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 26:
                damage = 29.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 26 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 27:
                damage = 30.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 27 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 28:
                damage = 31.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 28 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 29:
                damage = 32.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 29 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 30:
                damage = 33.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 30 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 31:
                damage = 34.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 31 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 32:
                damage = 35.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 32 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 33:
                damage = 36.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 33 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 34:
                damage = 37.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 34 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 35:
                damage = 38.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 35 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 36:
                damage = 39.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 36 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 37:
                damage = 40.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 37 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 38:
                damage = 41.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 38 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 39:
                damage = 42.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 39 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 40:
                damage = 43.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 40 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 41:
                damage = 44.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 41 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 42:
                damage = 45.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 42 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 43:
                damage = 46.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 43 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 44:
                damage = 47.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 44 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            case 45:
                damage = 48.0;
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
                woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                woodenmeta.setDisplayName("§7<< §6Holzschwert §7- §aLv. 45 §7>>");
                woodenmeta.setLore(Collections.singletonList("§a"));
                woodenmeta.setLore(Collections.singletonList("§9" + damage + " Angrifsschaden"));
                woodensword.setItemMeta(woodenmeta);
                player.getInventory().setItem(0, woodensword);
                player.updateInventory();
                break;
            default:
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.upgradesword","",""));
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
    private void addMoney(Player player, double amount) {
        String playerName = player.getUniqueId().toString();
        double currentMoney = 0;

        String currentMoneyString = mysqlManager.getBalance(playerName);
        currentMoneyString = currentMoneyString.replace(",", "");
        currentMoney = Double.parseDouble(currentMoneyString);

        double newMoney = currentMoney + amount;
        String formattedMoney = String.format(Locale.ENGLISH, "%,.2f", newMoney);
        mysqlManager.updateBalance(playerName, formattedMoney);
        scoreboardBuilder.updateMoney(player);
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
}

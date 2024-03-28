package net.dbsgameplay.dungeoncrusher.Commands.LevelSystem;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.UpgradeData;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.ItemBuilder;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.Locale;
import java.util.UUID;

public class ArmorUpgradeClickListener implements Listener {
    MYSQLManager mysqlManager;
    ScoreboardBuilder scoreboardBuilder;
    public ArmorUpgradeClickListener(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
        this.scoreboardBuilder = new ScoreboardBuilder(dungeonCrusher);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getView().getTitle().equals("§9§lUpgrades")) {
            Player player = (Player) event.getWhoClicked();
            int currentLevelhelmet = mysqlManager.getHelmetLevel(player.getUniqueId().toString());
            int currentLevelChestplate = mysqlManager.getChestplateLevel(player.getUniqueId().toString());
            int currentLevelLeggings = mysqlManager.getLeggingsLevel(player.getUniqueId().toString());
            int currentLevelBoots = mysqlManager.getBootsLevel(player.getUniqueId().toString());
            int currentLevel = mysqlManager.getArmorLvl(player.getUniqueId().toString());
            int currentLevelSword = mysqlManager.getSwordLevel(player.getUniqueId().toString());
            String nextLevelHelmet = String.valueOf(currentLevelhelmet + 1);
            String nextLevelSword = String.valueOf(currentLevelSword + 1);
            String nextLevelChestplate = String.valueOf(currentLevelChestplate + 1);
            String nextLevelLeggings = String.valueOf(currentLevelLeggings + 1);
            String nextLevelboots = String.valueOf(currentLevelBoots + 1);
            String currentmoney = mysqlManager.getBalance(player.getUniqueId().toString());
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
            int [] upgradeData = UpgradeData.getUpgradeData(currentLevel);
            String nextLevel = String.valueOf(currentLevel + 1);
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

            int [] upgradeDataSword = UpgradeData.getUpgradeData(currentLevelSword);
            int requiredRawCopperSword = upgradeDataSword[0];
            int requiredCopperIngotsSword = upgradeDataSword[1];
            double requiredMoneySword = upgradeDataSword[2];
            int requiredCobblestoneSword = upgradeDataSword[3];
            int requiredStoneSword = upgradeDataSword[4];
            int requiredRawIronSword = upgradeDataSword[5];
            int requiredIronSword = upgradeDataSword[6];
            int requiredRawGoldSword = upgradeDataSword[7];
            int requiredGoldSword = upgradeDataSword[8];
            int requiredDiamondOreSword = upgradeDataSword[9];
            int requiredDiamondSword= upgradeDataSword[10];
            int requiredNetheriteScrapSword = upgradeDataSword[11];
            int requiredNetheriteSword = upgradeDataSword[12];


            event.setCancelled(true);
            if (event.getCurrentItem().getItemMeta().hasLocalizedName()) {
                switch (event.getCurrentItem().getItemMeta().getLocalizedName()) {
                    case "helmetupgrade":
                        Inventory upgrade = Bukkit.createInventory(null, 9 * 6, "§9§lUpgrades");
                            if (currentLevelhelmet >= 10) {
                            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.upgradefirstchestplate","",""));
                            upgrade.setItem(13, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Helm Upgrade").setLocalizedName("helmetupgrade").setLore("§cMax Level erreicht!").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                upgrade.setItem(31, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Hosen Upgrade").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                upgrade.setItem(40, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Schuh Upgrade").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                upgrade.setItem(22, new ItemBuilder(Material.DIAMOND_CHESTPLATE).setDisplayname("§7➢ Chestplate Upgrade").setLocalizedName("chestplateupgrade").setLore("§7Level: §6" + nextLevelChestplate,
                                        "§7Geld: §6" + currentmoney + "§7/§6" + requiredMoney, "§7Rohkupfer: §6" + currentRawCopper + "§7/§6" + requiredRawCopper, "§7Kupferbarren: §6" + currentCopperIngots + "§7/§6" + requiredCopperIngots,
                                        "§7Bruchstein: §6" + currentCobblestone + "§7/§6" + requiredCobblestone,
                                        "§7Stein: §6" + currentStone + "§7/§6" + requiredStone, "§7RohEisen: §6" + currentRawIron + "§7/§6" + requiredRawIron,
                                        "§7Eisenbarren: §6" + currentIron + "§7/§6" + requiredIron, "§7RohGold: §6" + currentRawGold + "§7/§6" + requiredRawGold,
                                        "§7GoldBarren: §6" + currentGold + "§7/§6" + requiredGold, "§7DiamantErz: §6" + currentDiamondOre + "§7/§6" + requiredDiamondOre,
                                        "§7Diamanten: §6" + currentDiamond + "§7/§6" + requiredDiamond, "§7Netheriteplatten: §6" + currentNetheriteScrap + "§7/§6" + requiredNetheriteScrap,
                                        "§7NetheriteBarren: §6" + currentNetherite + "§6/§6" + requiredNetherite).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                if (currentLevelSword >= 280) {
                                    ItemStack maxLevelSword = new ItemBuilder(Material.DIAMOND_SWORD)
                                            .setDisplayname("§7➢ Schwert Upgrade")
                                            .setLocalizedName("swordupgrade")
                                            .setLore("§7Level: §6§lMaximales Level erreicht!")
                                            .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                                            .build();
                                    upgrade.setItem(20, maxLevelSword);
                                }else {
                                    upgrade.setItem(20, new ItemBuilder(Material.DIAMOND_SWORD).setDisplayname("§7➢ Schwert Upgrade").setLocalizedName("swordupgrade").setLore("§7Level: §6" + nextLevelSword,
                                            "§7Geld: §6" + currentmoney +"§7/§6" + requiredMoneySword,"§7Rohkupfer: §6" + currentRawCopper + "§7/§6" + requiredRawCopperSword, "§7Kupferbarren: §6" +currentCopperIngots  + "§7/§6" + requiredCopperIngotsSword,
                                            "§7Bruchstein: §6" + currentCobblestone + "§7/§6" +requiredCobblestoneSword ,
                                            "§7Stein: §6" + currentStone + "§7/§6" + requiredStoneSword, "§7RohEisen: §6" + currentRawIron + "§7/§6" + requiredRawIronSword,
                                            "§7Eisenbarren: §6" + currentIron + "§7/§6" + requiredIronSword, "§7RohGold: §6" + currentRawGold + "§7/§6" + requiredRawGoldSword,
                                            "§7GoldBarren: §6" + currentGold + "§7/§6" + requiredGoldSword, "§7DiamantErz: §6" + currentDiamondOre + "§7/§6" + requiredDiamondOreSword,
                                            "§7Diamanten: §6" + currentDiamond + "§7/§6" + requiredDiamondSword, "§7Netheriteplatten: §6" + currentNetheriteScrap + "§7/§6" + requiredNetheriteScrapSword,
                                            "§7NetheriteBarren: §6" + currentNetherite + "§6/§6" + requiredNetheriteSword).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                }
                            player.openInventory(upgrade);
                            return;
                        }else {
                                handleHelmUpgrade(player);
                                upgrade.setItem(13, new ItemBuilder(Material.DIAMOND_HELMET).setDisplayname("§7➢ Helm Upgrade").setLocalizedName("helmetupgrade").setLore("§7Level: §6" + nextLevelHelmet,
                                        "§7Geld: §6" + currentmoney + "§7/§6" + requiredMoney, "§7Rohkupfer: §6" + currentRawCopper + "§7/§6" + requiredRawCopper, "§7Kupferbarren: §6" + currentCopperIngots + "§7/§6" + requiredCopperIngots,
                                        "§7Bruchstein: §6" + currentCobblestone + "§7/§6" + requiredCobblestone,
                                        "§7Stein: §6" + currentStone + "§7/§6" + requiredStone, "§7RohEisen: §6" + currentRawIron + "§7/§6" + requiredRawIron,
                                        "§7Eisenbarren: §6" + currentIron + "§7/§6" + requiredIron, "§7RohGold: §6" + currentRawGold + "§7/§6" + requiredRawGold,
                                        "§7GoldBarren: §6" + currentGold + "§7/§6" + requiredGold, "§7DiamantErz: §6" + currentDiamondOre + "§7/§6" + requiredDiamondOre,
                                        "§7Diamanten: §6" + currentDiamond + "§7/§6" + requiredDiamond, "§7Netheriteplatten: §6" + currentNetheriteScrap + "§7/§6" + requiredNetheriteScrap,
                                        "§7NetheriteBarren: §6" + currentNetherite + "§6/§6" + requiredNetherite).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                upgrade.setItem(22, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Chestplate Upgrade").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                upgrade.setItem(31, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Hosen Upgrade").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                upgrade.setItem(40, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Schuh Upgrade").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                if (currentLevelSword >= 280) {
                                    ItemStack maxLevelSword = new ItemBuilder(Material.DIAMOND_SWORD)
                                            .setDisplayname("§7➢ Schwert Upgrade")
                                            .setLocalizedName("swordupgrade")
                                            .setLore("§7Level: §6§lMaximales Level erreicht!")
                                            .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                                            .build();
                                    upgrade.setItem(20, maxLevelSword);
                                }else {
                                    upgrade.setItem(20, new ItemBuilder(Material.DIAMOND_SWORD).setDisplayname("§7➢ Schwert Upgrade").setLocalizedName("swordupgrade").setLore("§7Level: §6" + nextLevelSword,
                                            "§7Geld: §6" + currentmoney +"§7/§6" + requiredMoneySword,"§7Rohkupfer: §6" + currentRawCopper + "§7/§6" + requiredRawCopperSword, "§7Kupferbarren: §6" +currentCopperIngots  + "§7/§6" + requiredCopperIngotsSword,
                                            "§7Bruchstein: §6" + currentCobblestone + "§7/§6" +requiredCobblestoneSword ,
                                            "§7Stein: §6" + currentStone + "§7/§6" + requiredStoneSword, "§7RohEisen: §6" + currentRawIron + "§7/§6" + requiredRawIronSword,
                                            "§7Eisenbarren: §6" + currentIron + "§7/§6" + requiredIronSword, "§7RohGold: §6" + currentRawGold + "§7/§6" + requiredRawGoldSword,
                                            "§7GoldBarren: §6" + currentGold + "§7/§6" + requiredGoldSword, "§7DiamantErz: §6" + currentDiamondOre + "§7/§6" + requiredDiamondOreSword,
                                            "§7Diamanten: §6" + currentDiamond + "§7/§6" + requiredDiamondSword, "§7Netheriteplatten: §6" + currentNetheriteScrap + "§7/§6" + requiredNetheriteScrapSword,
                                            "§7NetheriteBarren: §6" + currentNetherite + "§6/§6" + requiredNetheriteSword).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                }
                                player.openInventory(upgrade);
                        }
                        break;
                    case"chestplateupgrade":
                        upgrade = Bukkit.createInventory(null, 9 * 6, "§9§lUpgrades");
                        if (currentLevel >= 20) {
                            player.sendMessage(ConfigManager.getConfigMessage("message.upgradefirstleggings","",""));
                            upgrade.setItem(22, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Chestplate Upgrade").setLore("§cMax Level erreicht!").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            upgrade.setItem(13, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Helm Upgrade").setLore("§cMax Level erreicht!").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            upgrade.setItem(40, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Schuh Upgrade").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            upgrade.setItem(31, new ItemBuilder(Material.DIAMOND_LEGGINGS).setDisplayname("§7➢ Hosen Upgrade").setLocalizedName("leggingsupgrade").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            if (currentLevelSword >= 280) {
                                ItemStack maxLevelSword = new ItemBuilder(Material.DIAMOND_SWORD)
                                        .setDisplayname("§7➢ Schwert Upgrade")
                                        .setLocalizedName("swordupgrade")
                                        .setLore("§7Level: §6§lMaximales Level erreicht!")
                                        .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                                        .build();
                                upgrade.setItem(20, maxLevelSword);
                            }else {
                                upgrade.setItem(20, new ItemBuilder(Material.DIAMOND_SWORD).setDisplayname("§7➢ Schwert Upgrade").setLocalizedName("swordupgrade").setLore("§7Level: §6" + nextLevelSword,
                                        "§7Geld: §6" + currentmoney +"§7/§6" + requiredMoneySword,"§7Rohkupfer: §6" + currentRawCopper + "§7/§6" + requiredRawCopperSword, "§7Kupferbarren: §6" +currentCopperIngots  + "§7/§6" + requiredCopperIngotsSword,
                                        "§7Bruchstein: §6" + currentCobblestone + "§7/§6" +requiredCobblestoneSword ,
                                        "§7Stein: §6" + currentStone + "§7/§6" + requiredStoneSword, "§7RohEisen: §6" + currentRawIron + "§7/§6" + requiredRawIronSword,
                                        "§7Eisenbarren: §6" + currentIron + "§7/§6" + requiredIronSword, "§7RohGold: §6" + currentRawGold + "§7/§6" + requiredRawGoldSword,
                                        "§7GoldBarren: §6" + currentGold + "§7/§6" + requiredGoldSword, "§7DiamantErz: §6" + currentDiamondOre + "§7/§6" + requiredDiamondOreSword,
                                        "§7Diamanten: §6" + currentDiamond + "§7/§6" + requiredDiamondSword, "§7Netheriteplatten: §6" + currentNetheriteScrap + "§7/§6" + requiredNetheriteScrapSword,
                                        "§7NetheriteBarren: §6" + currentNetherite + "§6/§6" + requiredNetheriteSword).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            }
                            player.openInventory(upgrade);
                            return;
                        }else {
                            upgrade.setItem(13, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Helm Upgrade").setLocalizedName("helmetupgrade").setLore("§cMax Level erreicht!").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            upgrade.setItem(31, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Hosen Upgrade").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            upgrade.setItem(40, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Schuh Upgrade").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            upgrade.setItem(22, new ItemBuilder(Material.DIAMOND_CHESTPLATE).setDisplayname("§7➢ Chestplate Upgrade").setLocalizedName("chestplateupgrade").setLore("§7Level: §6" + nextLevelChestplate,
                                    "§7Geld: §6" + currentmoney + "§7/§6" + requiredMoney, "§7Rohkupfer: §6" + currentRawCopper + "§7/§6" + requiredRawCopper, "§7Kupferbarren: §6" + currentCopperIngots + "§7/§6" + requiredCopperIngots,
                                    "§7Bruchstein: §6" + currentCobblestone + "§7/§6" + requiredCobblestone,
                                    "§7Stein: §6" + currentStone + "§7/§6" + requiredStone, "§7RohEisen: §6" + currentRawIron + "§7/§6" + requiredRawIron,
                                    "§7Eisenbarren: §6" + currentIron + "§7/§6" + requiredIron, "§7RohGold: §6" + currentRawGold + "§7/§6" + requiredRawGold,
                                    "§7GoldBarren: §6" + currentGold + "§7/§6" + requiredGold, "§7DiamantErz: §6" + currentDiamondOre + "§7/§6" + requiredDiamondOre,
                                    "§7Diamanten: §6" + currentDiamond + "§7/§6" + requiredDiamond, "§7Netheriteplatten: §6" + currentNetheriteScrap + "§7/§6" + requiredNetheriteScrap,
                                    "§7NetheriteBarren: §6" + currentNetherite + "§6/§6" + requiredNetherite).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            if (currentLevelSword >= 280) {
                                ItemStack maxLevelSword = new ItemBuilder(Material.DIAMOND_SWORD)
                                        .setDisplayname("§7➢ Schwert Upgrade")
                                        .setLocalizedName("swordupgrade")
                                        .setLore("§7Level: §6§lMaximales Level erreicht!")
                                        .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                                        .build();
                                upgrade.setItem(20, maxLevelSword);
                            }else {
                                upgrade.setItem(20, new ItemBuilder(Material.DIAMOND_SWORD).setDisplayname("§7➢ Schwert Upgrade").setLocalizedName("swordupgrade").setLore("§7Level: §6" + nextLevelSword,
                                        "§7Geld: §6" + currentmoney +"§7/§6" + requiredMoneySword,"§7Rohkupfer: §6" + currentRawCopper + "§7/§6" + requiredRawCopperSword, "§7Kupferbarren: §6" +currentCopperIngots  + "§7/§6" + requiredCopperIngotsSword,
                                        "§7Bruchstein: §6" + currentCobblestone + "§7/§6" +requiredCobblestoneSword ,
                                        "§7Stein: §6" + currentStone + "§7/§6" + requiredStoneSword, "§7RohEisen: §6" + currentRawIron + "§7/§6" + requiredRawIronSword,
                                        "§7Eisenbarren: §6" + currentIron + "§7/§6" + requiredIronSword, "§7RohGold: §6" + currentRawGold + "§7/§6" + requiredRawGoldSword,
                                        "§7GoldBarren: §6" + currentGold + "§7/§6" + requiredGoldSword, "§7DiamantErz: §6" + currentDiamondOre + "§7/§6" + requiredDiamondOreSword,
                                        "§7Diamanten: §6" + currentDiamond + "§7/§6" + requiredDiamondSword, "§7Netheriteplatten: §6" + currentNetheriteScrap + "§7/§6" + requiredNetheriteScrapSword,
                                        "§7NetheriteBarren: §6" + currentNetherite + "§6/§6" + requiredNetheriteSword).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            }
                            handlechestplateUpgrade(player);
                            player.openInventory(upgrade);
                        }
                        break;
                    case "leggingsupgrade":
                        upgrade = Bukkit.createInventory(null, 9 * 6, "§9§lUpgrades");
                        if (currentLevel >= 30) {
                            player.sendMessage(ConfigManager.getConfigMessage("message.upgradefirstleggings","",""));
                            upgrade.setItem(22, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Chestplate Upgrade").setLore("§cMax Level erreicht!").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            upgrade.setItem(13, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Helm Upgrade").setLore("§cMax Level erreicht!").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            upgrade.setItem(31, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Hosen Upgrade").setLore("§cMax Level Erreicht").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            upgrade.setItem(40, new ItemBuilder(Material.DIAMOND_BOOTS).setDisplayname("§7➢ Schuh Upgrade").setLocalizedName("bootsupgrade").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            if (currentLevelSword >= 280) {
                                ItemStack maxLevelSword = new ItemBuilder(Material.DIAMOND_SWORD)
                                        .setDisplayname("§7➢ Schwert Upgrade")
                                        .setLocalizedName("swordupgrade")
                                        .setLore("§7Level: §6§lMaximales Level erreicht!")
                                        .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                                        .build();
                                upgrade.setItem(20, maxLevelSword);
                            }else {
                                upgrade.setItem(20, new ItemBuilder(Material.DIAMOND_SWORD).setDisplayname("§7➢ Schwert Upgrade").setLocalizedName("swordupgrade").setLore("§7Level: §6" + nextLevelSword,
                                        "§7Geld: §6" + currentmoney +"§7/§6" + requiredMoneySword,"§7Rohkupfer: §6" + currentRawCopper + "§7/§6" + requiredRawCopperSword, "§7Kupferbarren: §6" +currentCopperIngots  + "§7/§6" + requiredCopperIngotsSword,
                                        "§7Bruchstein: §6" + currentCobblestone + "§7/§6" +requiredCobblestoneSword ,
                                        "§7Stein: §6" + currentStone + "§7/§6" + requiredStoneSword, "§7RohEisen: §6" + currentRawIron + "§7/§6" + requiredRawIronSword,
                                        "§7Eisenbarren: §6" + currentIron + "§7/§6" + requiredIronSword, "§7RohGold: §6" + currentRawGold + "§7/§6" + requiredRawGoldSword,
                                        "§7GoldBarren: §6" + currentGold + "§7/§6" + requiredGoldSword, "§7DiamantErz: §6" + currentDiamondOre + "§7/§6" + requiredDiamondOreSword,
                                        "§7Diamanten: §6" + currentDiamond + "§7/§6" + requiredDiamondSword, "§7Netheriteplatten: §6" + currentNetheriteScrap + "§7/§6" + requiredNetheriteScrapSword,
                                        "§7NetheriteBarren: §6" + currentNetherite + "§6/§6" + requiredNetheriteSword).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            }
                            player.openInventory(upgrade);
                            return;
                        }else {
                            upgrade.setItem(13, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Helm Upgrade").setLore("§cMax Level erreicht!").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            upgrade.setItem(22, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Chestplate Upgrade").setLore("§cMax Level erreicht!").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            upgrade.setItem(40, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Schuh Upgrade").setLore("§cMax Level Erreicht").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            upgrade.setItem(31, new ItemBuilder(Material.DIAMOND_LEGGINGS).setDisplayname("§7➢ Hosen Upgrade").setLocalizedName("leggingsupgrade").setLore("§7Level: §6" + nextLevelLeggings,
                                    "§7Geld: §6" + currentmoney + "§7/§6" + requiredMoney, "§7Rohkupfer: §6" + currentRawCopper + "§7/§6" + requiredRawCopper, "§7Kupferbarren: §6" + currentCopperIngots + "§7/§6" + requiredCopperIngots,
                                    "§7Bruchstein: §6" + currentCobblestone + "§7/§6" + requiredCobblestone,
                                    "§7Stein: §6" + currentStone + "§7/§6" + requiredStone, "§7RohEisen: §6" + currentRawIron + "§7/§6" + requiredRawIron,
                                    "§7Eisenbarren: §6" + currentIron + "§7/§6" + requiredIron, "§7RohGold: §6" + currentRawGold + "§7/§6" + requiredRawGold,
                                    "§7GoldBarren: §6" + currentGold + "§7/§6" + requiredGold, "§7DiamantErz: §6" + currentDiamondOre + "§7/§6" + requiredDiamondOre,
                                    "§7Diamanten: §6" + currentDiamond + "§7/§6" + requiredDiamond, "§7Netheriteplatten: §6" + currentNetheriteScrap + "§7/§6" + requiredNetheriteScrap,
                                    "§7NetheriteBarren: §6" + currentNetherite + "§6/§6" + requiredNetherite).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            if (currentLevelSword >= 280) {
                                ItemStack maxLevelSword = new ItemBuilder(Material.DIAMOND_SWORD)
                                        .setDisplayname("§7➢ Schwert Upgrade")
                                        .setLocalizedName("swordupgrade")
                                        .setLore("§7Level: §6§lMaximales Level erreicht!")
                                        .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                                        .build();
                                upgrade.setItem(20, maxLevelSword);
                            } else {
                                upgrade.setItem(20, new ItemBuilder(Material.DIAMOND_SWORD).setDisplayname("§7➢ Schwert Upgrade").setLocalizedName("swordupgrade").setLore("§7Level: §6" + nextLevelSword,
                                        "§7Geld: §6" + currentmoney +"§7/§6" + requiredMoneySword,"§7Rohkupfer: §6" + currentRawCopper + "§7/§6" + requiredRawCopperSword, "§7Kupferbarren: §6" +currentCopperIngots  + "§7/§6" + requiredCopperIngotsSword,
                                        "§7Bruchstein: §6" + currentCobblestone + "§7/§6" +requiredCobblestoneSword ,
                                        "§7Stein: §6" + currentStone + "§7/§6" + requiredStoneSword, "§7RohEisen: §6" + currentRawIron + "§7/§6" + requiredRawIronSword,
                                        "§7Eisenbarren: §6" + currentIron + "§7/§6" + requiredIronSword, "§7RohGold: §6" + currentRawGold + "§7/§6" + requiredRawGoldSword,
                                        "§7GoldBarren: §6" + currentGold + "§7/§6" + requiredGoldSword, "§7DiamantErz: §6" + currentDiamondOre + "§7/§6" + requiredDiamondOreSword,
                                        "§7Diamanten: §6" + currentDiamond + "§7/§6" + requiredDiamondSword, "§7Netheriteplatten: §6" + currentNetheriteScrap + "§7/§6" + requiredNetheriteScrapSword,
                                        "§7NetheriteBarren: §6" + currentNetherite + "§6/§6" + requiredNetheriteSword).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                handleleggingsUpgrade(player);
                                player.openInventory(upgrade);
                            }
                        }
                        break;
                    case "bootsupgrade":
                        upgrade = Bukkit.createInventory(null, 9 * 6, "§9§lUpgrades");
                        if (currentLevel >= 45) {
                            player.sendMessage(ConfigManager.getConfigMessage("message.upgradefirsthelmet","",""));
                            upgrade.setItem(22, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Chestplate Upgrade").setLore("§cMax Level Erreicht").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            upgrade.setItem(31, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Hosen Upgrade").setLore("§cMax Level Erreicht").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            upgrade.setItem(40, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Schuh Upgrade").setLore("§cMax Level Erreicht").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            upgrade.setItem(13, new ItemBuilder(Material.DIAMOND_HELMET).setDisplayname("§7➢ Helm Upgrade").setLocalizedName("helmetupgrade").setLore("§7Level: §6" + nextLevelHelmet,
                                    "§7Geld: §6" + currentmoney + "§7/§6" + requiredMoney, "§7Rohkupfer: §6" + currentRawCopper + "§7/§6" + requiredRawCopper, "§7Kupferbarren: §6" + currentCopperIngots + "§7/§6" + requiredCopperIngots,
                                    "§7Bruchstein: §6" + currentCobblestone + "§7/§6" + requiredCobblestone,
                                    "§7Stein: §6" + currentStone + "§7/§6" + requiredStone, "§7RohEisen: §6" + currentRawIron + "§7/§6" + requiredRawIron,
                                    "§7Eisenbarren: §6" + currentIron + "§7/§6" + requiredIron, "§7RohGold: §6" + currentRawGold + "§7/§6" + requiredRawGold,
                                    "§7GoldBarren: §6" + currentGold + "§7/§6" + requiredGold, "§7DiamantErz: §6" + currentDiamondOre + "§7/§6" + requiredDiamondOre,
                                    "§7Diamanten: §6" + currentDiamond + "§7/§6" + requiredDiamond, "§7Netheriteplatten: §6" + currentNetheriteScrap + "§7/§6" + requiredNetheriteScrap,
                                    "§7NetheriteBarren: §6" + currentNetherite + "§6/§6" + requiredNetherite).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());

                            if (currentLevelSword >= 280) {
                                ItemStack maxLevelSword = new ItemBuilder(Material.DIAMOND_SWORD)
                                        .setDisplayname("§7➢ Schwert Upgrade")
                                        .setLocalizedName("swordupgrade")
                                        .setLore("§7Level: §6§lMaximales Level erreicht!")
                                        .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                                        .build();
                                upgrade.setItem(20, maxLevelSword);
                            }else {
                                upgrade.setItem(20, new ItemBuilder(Material.DIAMOND_SWORD).setDisplayname("§7➢ Schwert Upgrade").setLocalizedName("swordupgrade").setLore("§7Level: §6" + nextLevelSword,
                                        "§7Geld: §6" + currentmoney +"§7/§6" + requiredMoneySword,"§7Rohkupfer: §6" + currentRawCopper + "§7/§6" + requiredRawCopperSword, "§7Kupferbarren: §6" +currentCopperIngots  + "§7/§6" + requiredCopperIngotsSword,
                                        "§7Bruchstein: §6" + currentCobblestone + "§7/§6" +requiredCobblestoneSword ,
                                        "§7Stein: §6" + currentStone + "§7/§6" + requiredStoneSword, "§7RohEisen: §6" + currentRawIron + "§7/§6" + requiredRawIronSword,
                                        "§7Eisenbarren: §6" + currentIron + "§7/§6" + requiredIronSword, "§7RohGold: §6" + currentRawGold + "§7/§6" + requiredRawGoldSword,
                                        "§7GoldBarren: §6" + currentGold + "§7/§6" + requiredGoldSword, "§7DiamantErz: §6" + currentDiamondOre + "§7/§6" + requiredDiamondOreSword,
                                        "§7Diamanten: §6" + currentDiamond + "§7/§6" + requiredDiamondSword, "§7Netheriteplatten: §6" + currentNetheriteScrap + "§7/§6" + requiredNetheriteScrapSword,
                                        "§7NetheriteBarren: §6" + currentNetherite + "§6/§6" + requiredNetheriteSword).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            }
                            player.openInventory(upgrade);
                            return;
                        }else {
                            upgrade.setItem(13, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Helm Upgrade").setLore("§cMax Level erreicht!").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            upgrade.setItem(22, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Chestplate Upgrade").setLore("§cMax Level erreicht!").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            upgrade.setItem(31, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Hosen Upgrade").setLore("§cMax Level Erreicht").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            upgrade.setItem(40, new ItemBuilder(Material.DIAMOND_BOOTS).setDisplayname("§7➢ Schuh Upgrade").setLocalizedName("bootsupgrade").setLore("§7Level: §6" + nextLevelboots,
                                    "§7Geld: §6" + currentmoney + "§7/§6" + requiredMoney, "§7Rohkupfer: §6" + currentRawCopper + "§7/§6" + requiredRawCopper, "§7Kupferbarren: §6" + currentCopperIngots + "§7/§6" + requiredCopperIngots,
                                    "§7Bruchstein: §6" + currentCobblestone + "§7/§6" + requiredCobblestone,
                                    "§7Stein: §6" + currentStone + "§7/§6" + requiredStone, "§7RohEisen: §6" + currentRawIron + "§7/§6" + requiredRawIron,
                                    "§7Eisenbarren: §6" + currentIron + "§7/§6" + requiredIron, "§7RohGold: §6" + currentRawGold + "§7/§6" + requiredRawGold,
                                    "§7GoldBarren: §6" + currentGold + "§7/§6" + requiredGold, "§7DiamantErz: §6" + currentDiamondOre + "§7/§6" + requiredDiamondOre,
                                    "§7Diamanten: §6" + currentDiamond + "§7/§6" + requiredDiamond, "§7Netheriteplatten: §6" + currentNetheriteScrap + "§7/§6" + requiredNetheriteScrap,
                                    "§7NetheriteBarren: §6" + currentNetherite + "§6/§6" + requiredNetherite).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                            if (currentLevelSword >= 280) {
                                ItemStack maxLevelSword = new ItemBuilder(Material.DIAMOND_SWORD)
                                        .setDisplayname("§7➢ Schwert Upgrade")
                                        .setLocalizedName("swordupgrade")
                                        .setLore("§7Level: §6§lMaximales Level erreicht!")
                                        .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                                        .build();
                                upgrade.setItem(20, maxLevelSword);
                            } else {
                                upgrade.setItem(20, new ItemBuilder(Material.DIAMOND_SWORD).setDisplayname("§7➢ Schwert Upgrade").setLocalizedName("swordupgrade").setLore("§7Level: §6" + nextLevelSword,
                                        "§7Geld: §6" + currentmoney +"§7/§6" + requiredMoneySword,"§7Rohkupfer: §6" + currentRawCopper + "§7/§6" + requiredRawCopperSword, "§7Kupferbarren: §6" +currentCopperIngots  + "§7/§6" + requiredCopperIngotsSword,
                                        "§7Bruchstein: §6" + currentCobblestone + "§7/§6" +requiredCobblestoneSword ,
                                        "§7Stein: §6" + currentStone + "§7/§6" + requiredStoneSword, "§7RohEisen: §6" + currentRawIron + "§7/§6" + requiredRawIronSword,
                                        "§7Eisenbarren: §6" + currentIron + "§7/§6" + requiredIronSword, "§7RohGold: §6" + currentRawGold + "§7/§6" + requiredRawGoldSword,
                                        "§7GoldBarren: §6" + currentGold + "§7/§6" + requiredGoldSword, "§7DiamantErz: §6" + currentDiamondOre + "§7/§6" + requiredDiamondOreSword,
                                        "§7Diamanten: §6" + currentDiamond + "§7/§6" + requiredDiamondSword, "§7Netheriteplatten: §6" + currentNetheriteScrap + "§7/§6" + requiredNetheriteScrapSword,
                                        "§7NetheriteBarren: §6" + currentNetherite + "§6/§6" + requiredNetheriteSword).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                handlebootsUpgrade(player);
                                player.openInventory(upgrade);
                            }
                        }
                        break;
                    case "schließen":
                        player.closeInventory();
                        break;
                }
            }
        } else if (event.getView().getTitle().equals("§9§lUpgrades")) {
            event.setCancelled(true);
        }
    }
        private void handleHelmUpgrade(Player player) {
            int currentLevel = mysqlManager.getArmorLvl(player.getUniqueId().toString());
            int currentlevelhelmet = mysqlManager.getHelmetLevel(player.getUniqueId().toString());

            if (currentLevel >= 60) {
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.maxlevel","",""));
                return;
            }

            int[] upgradeData = UpgradeData.getUpgradeData(currentLevel);
            if (upgradeData == null) {
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.upgradehelm", "", ""));
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
                    mysqlManager.updateArmorLvl(player.getUniqueId().toString(), currentLevel + 1);
                    mysqlManager.updateHelmLevel(player.getUniqueId().toString(), currentLevel + 1);
                    giveHelmToPlayer(player, currentLevel + 1);
                    updateItems(player);
                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.upgradehelm"));
                } else {
                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughtmoneyupgrade", "", ""));
                }
            } else {
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughtitemsupgrade", "", ""));
            }
        }
    private void handlechestplateUpgrade(Player player) {
        int currentLevel = mysqlManager.getArmorLvl(player.getUniqueId().toString());
        int currentLevelChestplate = mysqlManager.getChestplateLevel(player.getUniqueId().toString());
        if (currentLevel >= 60) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.maxlevel","",""));
            return;
        }

        int[] upgradeData = UpgradeData.getUpgradeData(currentLevel);
        if (upgradeData == null) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.upgradehelm", "", ""));
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
                mysqlManager.updateArmorLvl(player.getUniqueId().toString(), currentLevel + 1);
                mysqlManager.updateChestplateLevel(player.getUniqueId().toString(), currentLevelChestplate + 1);
                giveChestplateToPlayer(player, currentLevel + 1);
                updateItems(player);
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.upgradehelm"));
            } else {
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughtmoneyupgrade", "", ""));
            }
        } else {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughtitemsupgrade", "", ""));
        }
    }
    private void handleleggingsUpgrade(Player player) {
        int currentLevel = mysqlManager.getArmorLvl(player.getUniqueId().toString());
        int currentLevelLeggings = mysqlManager.getLeggingsLevel(player.getUniqueId().toString());
        if (currentLevel >= 60) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.maxlevel","",""));
            return;
        }

        int[] upgradeData = UpgradeData.getUpgradeData(currentLevel);
        if (upgradeData == null) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.upgradehelm", "", ""));
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
                mysqlManager.updateArmorLvl(player.getUniqueId().toString(), currentLevel + 1);
                mysqlManager.updateLeggingsLevel(player.getUniqueId().toString(), currentLevelLeggings + 1);
                giveLeggingsToPlayer(player, currentLevel + 1);
                updateItems(player);
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.upgradehelm"));
            } else {
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughtmoneyupgrade", "", ""));
            }
        } else {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughtitemsupgrade", "", ""));
        }
    }
    private void handlebootsUpgrade(Player player) {
        int currentLevel = mysqlManager.getArmorLvl(player.getUniqueId().toString());
        int currentLevelboots = mysqlManager.getBootsLevel(player.getUniqueId().toString());
        if (currentLevel >= 60) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.maxlevel","",""));
            return;
        }

        int[] upgradeData = UpgradeData.getUpgradeData(currentLevel);
        if (upgradeData == null) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.upgradehelm", "", ""));
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
                mysqlManager.updateArmorLvl(player.getUniqueId().toString(), currentLevel + 1);
                mysqlManager.updateBootsLevel(player.getUniqueId().toString(), currentLevelboots + 1);
                giveBootsToPlayer(player, currentLevel + 1);
                updateItems(player);
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.upgradehelm"));
            } else {
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughtmoneyupgrade", "", ""));
            }
        } else {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughtitemsupgrade", "", ""));
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
    private void giveHelmToPlayer(Player player, int level) {

        switch (level) {
            case 1:
                updatelederhelmet(player, 2, 2, 0);
                break;
            case 2:
                updatelederhelmet(player, 2, 3, 1);
                break;
            case 3:
                updatelederhelmet(player, 3, 4, 0);
                break;
            case 4:
                updatelederhelmet(player, 3, 5, 1);
                break;
            case 5:
                updatelederhelmet(player, 4, 6, 0);
                break;
            case 6:
                updatelederhelmet(player, 4, 7, 1);
                break;
            case 7:
                updatelederhelmet(player, 5, 8, 0);
                break;
            case 8:
                updatelederhelmet(player, 5, 9, 1);
                break;
            case 9:
                updatelederhelmet(player, 6,10,0);
                break;
        }
    }
    private void giveChestplateToPlayer(Player player, int level) {
        switch (level) {
            case 11:
                updatelederchestplate(player, 3, 1, 1);
                break;
            case 12:
                updatelederchestplate(player, 4, 2, 0);
                break;
            case 13:
                updatelederchestplate(player, 4, 3, 1);
                break;
            case 14:
                updatelederchestplate(player, 5, 4, 0);
                break;
            case 15:
                updatelederchestplate(player, 5, 5, 1);
                break;
            case 16:
                updatelederchestplate(player, 6, 6, 0);
                break;
            case 17:
                updatelederchestplate(player, 6, 7, 1);
                break;
            case 18:
                updatelederchestplate(player, 7, 8, 0);
                break;
            case 19:
                updatelederchestplate(player, 7, 9, 1);
                break;
            case 20:
                updatelederchestplate(player, 8, 10, 0);
                break;
            default:
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.upgradeswordfailure","",""));
                return;
        }
    }
    private void giveLeggingsToPlayer(Player player, int level) {
        switch (level) {

            case 21:
                updatelederleggings(player, 2, 1, 1);
                break;
            case 22:
                updatelederleggings(player, 3, 2, 0);
                break;
            case 23:
                updatelederleggings(player, 3, 3, 1);
                break;
            case 24:
                updatelederleggings(player, 4, 4, 0);
                break;
            case 25:
                updatelederleggings(player, 4, 5, 1);
                break;
            case 26:
                updatelederleggings(player, 5, 6, 0);
                break;
            case 27:
                updatelederleggings(player, 5, 7, 1);
                break;
            case 28:
                updatelederleggings(player, 6, 8, 0);
                break;
            case 29:
                updatelederleggings(player, 6, 9, 1);
                break;
            case 30:
                updatelederleggings(player, 7, 10, 0);
                break;
            default:
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.upgradeswordfailure","",""));
                return;
        }
    }
    private void giveBootsToPlayer(Player player, int level) {
        switch (level) {
            case 31:
                updatelederboots(player, 1, 1, 1);
                break;
            case 32:
                updatelederboots(player, 2, 2, 0);
                break;
            case 33:
                updatelederboots(player, 2, 3, 1);
                break;
            case 34:
                updatelederboots(player, 3, 4, 0);
                break;
            case 35:
                updatelederboots(player, 3, 5, 1);
                break;
            case 36:
                updatelederboots(player, 4, 6, 0);
                break;
            case 37:
                updatelederboots(player, 4, 7, 1);
                break;
            case 38:
                updatelederboots(player, 5, 8, 0);
                break;
            case 39:
                updatelederboots(player, 5, 9, 1);
                break;
            case 40:
                updatelederboots(player, 6, 10, 0);
                break;
            case 41:
                updatelederboots(player, 6, 11, 1);
                break;
            case 42:
                updatelederboots(player, 7, 12, 0);
                break;
            case 43:
                updatelederboots(player, 7, 13, 1);
                break;
            case 44:
                updatelederboots(player, 8, 14, 0);
                break;
            case 45:
                updatelederboots(player, 8, 15, 1);
                break;
            default:
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.upgradeswordfailure","",""));
                return;
        }
    }
    private void updatelederhelmet(Player player, double rüstung, int level, double health) {
        ItemStack woodenhelm = new ItemStack(Material.LEATHER_HELMET);
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", rüstung, AttributeModifier.Operation.ADD_NUMBER);
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(player.getHealth() + health);
        ItemMeta woodenmeta = woodenhelm.getItemMeta();
        woodenmeta.setDisplayName("§7<< §6LederHelm §7- §aLv."+ level + "§7>>");
        woodenmeta.setLore(Collections.singletonList("§9+"+ rüstung + " Rüstung"));
        woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        woodenmeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, modifier);
        woodenmeta.setUnbreakable(true);
        woodenhelm.setItemMeta(woodenmeta);
        player.getInventory().setHelmet(woodenhelm);
    }
    private void updatelederchestplate(Player player, double rüstung, int level, double health) {
        ItemStack woodenhelm = new ItemStack(Material.LEATHER_CHESTPLATE);
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", rüstung, AttributeModifier.Operation.ADD_NUMBER);
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(player.getHealth() + health);
        ItemMeta woodenmeta = woodenhelm.getItemMeta();
        woodenmeta.setDisplayName("§7<< §6LederBrustplatte §7- §aLv."+ level + "§7>>");
        woodenmeta.setLore(Collections.singletonList("§9+"+ rüstung + " Rüstung"));
        woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        woodenmeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, modifier);
        woodenmeta.setUnbreakable(true);
        woodenhelm.setItemMeta(woodenmeta);
        player.getInventory().setChestplate(woodenhelm);
    }
    private void updatelederleggings(Player player, double rüstung, int level, double health) {
        ItemStack woodenhelm = new ItemStack(Material.LEATHER_LEGGINGS);
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", rüstung, AttributeModifier.Operation.ADD_NUMBER);
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(player.getHealth() + health);
        ItemMeta woodenmeta = woodenhelm.getItemMeta();
        woodenmeta.setDisplayName("§7<< §6LederHose §7- §aLv."+ level + "§7>>");
        woodenmeta.setLore(Collections.singletonList("§9+"+ rüstung + " Rüstung"));
        woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        woodenmeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, modifier);
        woodenmeta.setUnbreakable(true);
        woodenhelm.setItemMeta(woodenmeta);
        player.getInventory().setLeggings(woodenhelm);
    }
    private void updatelederboots(Player player, double rüstung, int level, double health) {
        ItemStack woodenhelm = new ItemStack(Material.LEATHER_BOOTS);
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", rüstung, AttributeModifier.Operation.ADD_NUMBER);
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(player.getHealth() + health);
        ItemMeta woodenmeta = woodenhelm.getItemMeta();
        woodenmeta.setDisplayName("§7<< §6LederSchuhe §7- §aLv."+ level + "§7>>");
        woodenmeta.setLore(Collections.singletonList("§9+"+ rüstung + " Rüstung"));
        woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        woodenmeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, modifier);
        woodenmeta.setUnbreakable(true);
        woodenhelm.setItemMeta(woodenmeta);
        player.getInventory().setBoots(woodenhelm);
    }
    }

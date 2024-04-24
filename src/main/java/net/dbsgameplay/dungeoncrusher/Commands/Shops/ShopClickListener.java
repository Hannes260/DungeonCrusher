package net.dbsgameplay.dungeoncrusher.Commands.Shops;

import com.google.common.collect.ImmutableList;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.UpgradeData;
import net.dbsgameplay.dungeoncrusher.objects.PlayerHead;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.ItemBuilder;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
import net.dbsgameplay.dungeoncrusher.utils.TexturedHeads;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ShopClickListener implements Listener {
    private MYSQLManager mysqlManager;
    private ScoreboardBuilder scoreboardBuilder;
    private DungeonCrusher dungeonCrusher;
    public ShopClickListener(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager){
        this.mysqlManager = mysqlManager;
        this.dungeonCrusher = dungeonCrusher;
        this.scoreboardBuilder = new ScoreboardBuilder(dungeonCrusher);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getView().getTitle() == "§9§lShop") {
            Player player = (Player) event.getWhoClicked();
            event.setCancelled(true);
            String currentmoney = mysqlManager.getBalance(player.getUniqueId().toString());
            if (event.getCurrentItem().getItemMeta().hasLocalizedName()) {
                switch (event.getCurrentItem().getItemMeta().getLocalizedName()) {
                    case"essen":
                        Inventory foodinv = Bukkit.createInventory(null , 9*6,"§9§lShop");
                        foodinv.setItem(4, new ItemBuilder(Material.GOLDEN_APPLE).setDisplayname("§7➢ GoldApfel").setLocalizedName("goldenapple").setLore("§7Anzahl: §61", "§7Preis: §6450€").build());
                        foodinv.setItem(20, new ItemBuilder(Material.COOKIE).setDisplayname("§7➢ Cookie").setLocalizedName("cookie").setLore("§7Anzahl: §61", "§7Preis: §61€").build());
                        foodinv.setItem(22, new ItemBuilder(Material.ENCHANTED_GOLDEN_APPLE).setDisplayname("§7➢ Verzauberter Goldenerapfel").setLocalizedName("enchantedgoldenapple").setLore("§7Anzahl: §61", "§7Preis: §61000€").build());
                        foodinv.setItem(24, new ItemBuilder(Material.BREAD).setDisplayname("§7➢ Brot").setLocalizedName("bread").setLore("§7Anzahl: §61", "§7Preis: §610€").build());
                        foodinv.setItem(49, new ItemBuilder(Material.GRAY_DYE).setLocalizedName("64switchfood").setDisplayname("§bEssen x1").setLore("§7Anzahl: §61", "§7Anzahl: §764").build());
                        backheadinventory(player, foodinv,45,"main");
                        player.openInventory(foodinv);
                        break;
                        case"64switchfood":
                            Inventory foodinv64 = Bukkit.createInventory(null , 9*6,"§9§lShop");
                            foodinv64.setItem(4, new ItemBuilder(Material.GOLDEN_APPLE).setDisplayname("§7➢ GoldApfel").setLocalizedName("goldenapple64").setLore("§7Anzahl: §664", "§7Preis: §628800€").build());
                            foodinv64.setItem(20, new ItemBuilder(Material.COOKIE).setDisplayname("§7➢ Cookie").setLocalizedName("cookie64").setLore("§7Anzahl: §664", "§7Preis: §664€").build());
                            foodinv64.setItem(22, new ItemBuilder(Material.ENCHANTED_GOLDEN_APPLE).setDisplayname("§7➢ Verzauberter Goldenerapfel").setLocalizedName("enchantedgoldenapple64").setLore("§7Anzahl: §664", "§7Preis: §664000€").build());
                            foodinv64.setItem(24, new ItemBuilder(Material.BREAD).setDisplayname("§7➢ Brot").setLocalizedName("bread64").setLore("§7Anzahl: §664", "§7Preis: §6640€").build());
                            foodinv64.setItem(49, new ItemBuilder(Material.LIME_DYE).setLocalizedName("essen").setDisplayname("§bEssen x64").setLore("§7Anzahl: §71", "§7Anzahl: §664").build());
                            backheadinventory(player, foodinv64,45,"main");
                            player.openInventory(foodinv64);
                            break;
                    case"potions":
                        Inventory potionsinventory = Bukkit.createInventory(null, 9*6, "§9§lShop");
                        ItemStack regpotion = new ItemStack(Material.POTION);
                        PotionMeta potionsmeta = (PotionMeta) regpotion.getItemMeta();
                        potionsmeta.setDisplayName("§7➢ Regeneration");
                        potionsmeta.setLore(ImmutableList.of("§7Anzahl: §61", "§7Preis: §6100"));
                        potionsmeta.setLocalizedName("regpotion");
                        potionsmeta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 45 * 20, 0, true, false), true);
                        regpotion.setItemMeta(potionsmeta);
                        potionsinventory.setItem(2, regpotion);

                        ItemStack regpotiona = new ItemStack(Material.POTION);
                        PotionMeta potionMetaa = (PotionMeta) regpotiona.getItemMeta();
                        potionMetaa.setDisplayName("§7➢ Regeneration");
                        potionMetaa.setLore(ImmutableList.of("§7Anzahl: §61", "§7Preis: §6150"));
                        potionMetaa.setLocalizedName("regpotiona");
                        potionMetaa.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 90*20, 0, true, false), true);
                        regpotiona.setItemMeta(potionMetaa);
                        potionsinventory.setItem(4, regpotiona);

                        ItemStack regpotion2 = new ItemStack(Material.POTION);
                        PotionMeta PotionMetaa2 = (PotionMeta) regpotion2.getItemMeta();
                        PotionMetaa2.setDisplayName("§7➢ Regeneration 2");
                        PotionMetaa2.setLore(ImmutableList.of("§7Anzahl: §61", "§7Preis: §6200"));
                        PotionMetaa2.setLocalizedName("regpotion2");
                        PotionMetaa2.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 22*20, 1, true, false), true);
                        regpotion2.setItemMeta(PotionMetaa2);
                        potionsinventory.setItem(6, regpotion2);

                        ItemStack strength1 = new ItemStack(Material.POTION);
                        PotionMeta strength1meta = (PotionMeta) strength1.getItemMeta();
                        strength1meta.setDisplayName("§7➢ Stärke");
                        strength1meta.setLore(ImmutableList.of("§7Anzahl: §61", "§7Preis: §6200"));
                        strength1meta.setLocalizedName("strengthpotion1");
                        strength1meta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 180*20, 0, true, false), true);
                        strength1.setItemMeta(strength1meta);
                        potionsinventory.setItem(10, strength1);

                        ItemStack strength2 = new ItemStack(Material.POTION);
                        PotionMeta strength2meta = (PotionMeta) strength2.getItemMeta();
                        strength2meta.setDisplayName("§7➢ Stärke");
                        strength2meta.setLore(ImmutableList.of("§7Anzahl: §61", "§7Preis: §6250"));
                        strength2meta.setLocalizedName("strengthpotion2");
                        strength2meta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 480*20, 0, true, false), true);
                        strength2.setItemMeta(strength2meta);
                        potionsinventory.setItem(19, strength2);

                        ItemStack strength3 = new ItemStack(Material.POTION);
                        PotionMeta strength3meta = (PotionMeta) strength3.getItemMeta();
                        strength3meta.setDisplayName("§7➢ Stärke");
                        strength3meta.setLore(ImmutableList.of("§7Anzahl: §61", "§7Preis: §6500"));
                        strength3meta.setLocalizedName("strengthpotion3");
                        strength3meta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 90*20, 1, true, false), true);
                        strength3.setItemMeta(strength3meta);
                        potionsinventory.setItem(29, strength3);

                        ItemStack speed1 = new ItemStack(Material.POTION);
                        PotionMeta speed1meta = (PotionMeta) speed1.getItemMeta();
                        speed1meta.setDisplayName("§7➢ Geschwindigkeit");
                        speed1meta.setLore(ImmutableList.of("§7Anzahl: §61", "§7Preis: §650"));
                        speed1meta.setLocalizedName("speedpotion1");
                        speed1meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 180*20, 0, true, false), true);
                        speed1.setItemMeta(speed1meta);
                        potionsinventory.setItem(16, speed1);

                        ItemStack speed2 = new ItemStack(Material.POTION);
                        PotionMeta speed2meta = (PotionMeta) speed2.getItemMeta();
                        speed2meta.setDisplayName("§7➢ Geschwindigkeit");
                        speed2meta.setLore(ImmutableList.of("§7Anzahl: §61", "§7Preis: §6150"));
                        speed2meta.setLocalizedName("speedpotion2");
                        speed2meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 480*20, 0, true, false), true);
                        speed2.setItemMeta(speed2meta);
                        potionsinventory.setItem(25, speed2);

                        ItemStack speed3 = new ItemStack(Material.POTION);
                        PotionMeta speed3meta = (PotionMeta) speed3.getItemMeta();
                        speed3meta.setDisplayName("§7➢ Geschwindigkeit");
                        speed3meta.setLore(ImmutableList.of("§7Anzahl: §61", "§7Preis: §6300"));
                        speed3meta.setLocalizedName("speedpotion3");
                        speed3meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 90*20, 1, true, false), true);
                        speed3.setItemMeta(speed3meta);
                        potionsinventory.setItem(33, speed3);

                        ItemStack health1 = new ItemStack(Material.POTION);
                        PotionMeta health1meta = (PotionMeta) health1.getItemMeta();
                        health1meta.setDisplayName("§7➢ Wundertrank");
                        health1meta.setLore(ImmutableList.of("§7Anzahl: §61", "§7Preis: §6100"));
                        health1meta.setLocalizedName("healthpotion1");
                        health1meta.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 1, 0, true, false), true);
                        health1.setItemMeta(health1meta);
                        potionsinventory.setItem(39, health1);

                        ItemStack health2 = new ItemStack(Material.POTION);
                        PotionMeta health2meta = (PotionMeta) health2.getItemMeta();
                        health2meta.setDisplayName("§7➢ Wundertrank");
                        health2meta.setLore(ImmutableList.of("§7Anzahl: §61", "§7Preis: §6250"));
                        health2meta.setLocalizedName("healthpotion2");
                        health2meta.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 1, 1, true, false), true);
                        health2.setItemMeta(health2meta);
                        potionsinventory.setItem(41, health2);

                        backheadinventory(player, potionsinventory, 45, "main");
                        player.openInventory(potionsinventory);
                        break;
                    case"main":
                        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 9*6, "§9§lShop");
                        inventory.setItem(45, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ §bSchließen").setLocalizedName("schließen").build());
                        inventory.setItem(53, (new PlayerHead(player.getName(), "§9Dein Geld: §a" + currentmoney + "§9€", new String[0])).getItemStack());
                        this.fillEmptySlots(inventory, Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1);

                        PlayerProfile profile = TexturedHeads.getProfile("https://textures.minecraft.net/texture/1591b61529d25a7ecd6bec00948e6fe155e3007f2d7fe559f3a83c6f808e434d");
                        ItemStack food = new ItemStack(Material.PLAYER_HEAD);
                        SkullMeta meta = (SkullMeta) food.getItemMeta();
                        meta.setOwnerProfile(profile);
                        meta.setDisplayName("§7➢ Essen");
                        meta.setLocalizedName("essen");
                        food.setItemMeta(meta);
                        inventory.setItem(20, food);
                        PlayerProfile upgradeprofile = TexturedHeads.getProfile("https://textures.minecraft.net/texture/77334cddfab45d75ad28e1a47bf8cf5017d2f0982f6737da22d4972952510661");
                        ItemStack upgrade = new ItemStack(Material.PLAYER_HEAD);
                        SkullMeta upgrademeta = (SkullMeta) upgrade.getItemMeta();
                        upgrademeta.setOwnerProfile(upgradeprofile);
                        upgrademeta.setDisplayName("§7➢ Upgrades");
                        upgrademeta.setLocalizedName("upgrades");
                        upgrade.setItemMeta(upgrademeta);
                        inventory.setItem(22, upgrade);
                        PlayerProfile potionprofile = TexturedHeads.getProfile("https://textures.minecraft.net/texture/dcedb2f4c97016cae7b89e4c6d6978d22ac3476c815c5a09a6792450dd918b6c");
                        ItemStack potions = new ItemStack(Material.PLAYER_HEAD);
                        SkullMeta potionmeta = (SkullMeta) potions.getItemMeta();
                        potionmeta.setOwnerProfile(potionprofile);
                        potionmeta.setDisplayName("§7➢ Tränke");
                        potionmeta.setLocalizedName("potions");
                        potions.setItemMeta(potionmeta);
                        inventory.setItem(24, potions);
                        player.openInventory(inventory);
                        break;
                    case"upgrades":
                        Inventory upgrades = Bukkit.createInventory(null, 9*6, "§9§lUpgrades");
                        upgrades.setItem(45, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ §bSchließen").setLocalizedName("schließen").build());

                        upgrades.setItem(53, (new PlayerHead(player.getName(), "§9Dein Geld: §a" + currentmoney + "§9€", new String[0])).getItemStack());
                        this.fillEmptySlots(upgrades, Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1);
                        player.openInventory(upgrades);

                        int currentLevel = mysqlManager.getSwordLevel(player.getUniqueId().toString());
                        int currentLevelHelmet = mysqlManager.getHelmetLevel(player.getUniqueId().toString());
                        int[] upgradeData = UpgradeData.getUpgradeData(currentLevel);
                        int [] upgradeDataHelmet = UpgradeData.getUpgradeData(currentLevelHelmet);

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

                        String nextLevelHelmet = String.valueOf(currentLevelHelmet + 1);
                        int requiredRawCopperHelmet = upgradeDataHelmet[0];
                        int requiredCopperIngotsHelmet = upgradeDataHelmet[1];
                        double requiredMoneyHelmet = upgradeDataHelmet[2];
                        int requiredCobblestoneHelmet = upgradeDataHelmet[3];
                        int requiredStoneHelmet = upgradeDataHelmet[4];
                        int requiredRawIronHelmet = upgradeDataHelmet[5];
                        int requiredIronHelmet = upgradeDataHelmet[6];
                        int requiredRawGoldHelmet = upgradeDataHelmet[7];
                        int requiredGoldHelmet = upgradeDataHelmet[8];
                        int requiredDiamondOreHelmet = upgradeDataHelmet[9];
                        int requiredDiamondHelmet = upgradeDataHelmet[10];
                        int requiredNetheriteScrapHelmet = upgradeDataHelmet[11];
                        int requiredNetheriteHelmet = upgradeDataHelmet[12];

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

                        BukkitRunnable task = new BukkitRunnable() {
                            int tick = 0;
                            @Override
                            public void run() {
                                tick++;
                                switch (tick) {
                                    case 1:
                                        if (currentLevelHelmet >= 280) {
                                            ItemStack maxLevelSword = new ItemBuilder(Material.DIAMOND_HELMET)
                                                    .setDisplayname("§7➢ Helm Upgrade")
                                                    .setLocalizedName("helmetupgrade")
                                                    .setLore("§7Level: §6§lMaximales Level erreicht!")
                                                    .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                                                    .build();
                                            upgrades.setItem(20, maxLevelSword);
                                        }else if (currentLevelHelmet >= 10) {
                                            upgrades.setItem(13, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Helm Upgrade").setLocalizedName("helmetupgrade").setLore("§cMax Level erreicht!").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                            return;
                                        }else {
                                            upgrades.setItem(13, new ItemBuilder(Material.DIAMOND_HELMET).setDisplayname("§7➢ Helm Upgrade").setLocalizedName("helmetupgrade").setLore("§7Level: §6" + nextLevelHelmet,
                                                    "§7Geld: §6" + currentmoney + "§7/§6" + requiredMoneyHelmet, "§7Rohkupfer: §6" + currentRawCopper + "§7/§6" + requiredRawCopperHelmet, "§7Kupferbarren: §6" + currentCopperIngots + "§7/§6" + requiredCopperIngotsHelmet,
                                                    "§7Bruchstein: §6" + currentCobblestone + "§7/§6" + requiredCobblestoneHelmet,
                                                    "§7Stein: §6" + currentStone + "§7/§6" + requiredStoneHelmet, "§7RohEisen: §6" + currentRawIron + "§7/§6" + requiredRawIronHelmet,
                                                    "§7Eisenbarren: §6" + currentIron + "§7/§6" + requiredIronHelmet, "§7RohGold: §6" + currentRawGold + "§7/§6" + requiredRawGoldHelmet,
                                                    "§7GoldBarren: §6" + currentGold + "§7/§6" + requiredGoldHelmet, "§7DiamantErz: §6" + currentDiamondOre + "§7/§6" + requiredDiamondOreHelmet,
                                                    "§7Diamanten: §6" + currentDiamond + "§7/§6" + requiredDiamondHelmet, "§7Netheriteplatten: §6" + currentNetheriteScrap + "§7/§6" + requiredNetheriteScrapHelmet,
                                                    "§7NetheriteBarren: §6" + currentNetherite + "§6/§6" + requiredNetheriteHelmet).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                        }

                                        break;
                                    case 2:
                                        upgrades.setItem(22, new ItemBuilder(Material.DIAMOND_CHESTPLATE).setDisplayname("§7➢ Chestplate Upgrade").setLocalizedName("chestplateupgrade").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                        break;
                                    case 3:
                                        upgrades.setItem(31, new ItemBuilder(Material.DIAMOND_LEGGINGS).setDisplayname("§7➢ Hosen Upgrade").setLocalizedName("leggingsupgrade").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                        break;
                                    case 4:
                                        upgrades.setItem(40, new ItemBuilder(Material.DIAMOND_BOOTS).setDisplayname("§7➢ Schuh Upgrade").setLocalizedName("bootsupgrade").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                        break;
                                    case 5:
                                        if (currentLevel >= 280) {
                                            ItemStack maxLevelSword = new ItemBuilder(Material.DIAMOND_SWORD)
                                                    .setDisplayname("§7➢ Schwert Upgrade")
                                                    .setLocalizedName("swordupgrade")
                                                    .setLore("§7Level: §6§lMaximales Level erreicht!")
                                                    .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                                                    .build();
                                            upgrades.setItem(20, maxLevelSword);
                                        }else {
                                            upgrades.setItem(20, new ItemBuilder(Material.DIAMOND_SWORD).setDisplayname("§7➢ Schwert Upgrade").setLocalizedName("swordupgrade").setLore("§7Level: §6" + nextLevel,
                                                    "§7Geld: §6" + currentmoney + "§7/§6" + requiredMoney, "§7Rohkupfer: §6" + currentRawCopper + "§7/§6" + requiredRawCopper, "§7Kupferbarren: §6" + currentCopperIngots + "§7/§6" + requiredCopperIngots,
                                                    "§7Bruchstein: §6" + currentCobblestone + "§7/§6" + requiredCobblestone,
                                                    "§7Stein: §6" + currentStone + "§7/§6" + requiredStone, "§7RohEisen: §6" + currentRawIron + "§7/§6" + requiredRawIron,
                                                    "§7Eisenbarren: §6" + currentIron + "§7/§6" + requiredIron, "§7RohGold: §6" + currentRawGold + "§7/§6" + requiredRawGold,
                                                    "§7GoldBarren: §6" + currentGold + "§7/§6" + requiredGold, "§7DiamantErz: §6" + currentDiamondOre + "§7/§6" + requiredDiamondOre,
                                                    "§7Diamanten: §6" + currentDiamond + "§7/§6" + requiredDiamond, "§7Netheriteplatten: §6" + currentNetheriteScrap + "§7/§6" + requiredNetheriteScrap,
                                                    "§7NetheriteBarren: §6" + currentNetherite + "§6/§6" + requiredNetherite).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                        }
                                    case 13:
                                        try {
                                            Thread.sleep(50000000 / 1000000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
                                        break;
                                    case 14:
                                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                                        this.cancel();
                                        break;
                                }
                            }
                        };
                        task.runTaskTimer(this.dungeonCrusher, 0, 1); //
                        break;
                    case"schließen":
                        player.closeInventory();
                        break;
                        //Buy
                    case"goldenapple":
                        buyItem(player, Material.GOLDEN_APPLE,1,450);
                        break;
                    case"cookie":
                        buyItem(player, Material.COOKIE,1,1);
                        break;
                    case"enchantedgoldenapple":
                        buyItem(player, Material.ENCHANTED_GOLDEN_APPLE,1,1000);
                        break;
                    case"bread":
                        buyItem(player, Material.BREAD,1,10);
                        break;
                    case"goldenapple64":
                        buyItem(player, Material.GOLDEN_APPLE,64,28800);
                        break;
                    case"cookie64":
                        buyItem(player, Material.COOKIE,64,64);
                        break;
                    case"enchantedgoldenapple64":
                        buyItem(player, Material.ENCHANTED_GOLDEN_APPLE,64,64000);
                        break;
                    case"bread64":
                        buyItem(player, Material.BREAD,64,640);
                        break;
                    case"regpotion":
                        buyPotion(player, PotionEffectType.REGENERATION, 45*20, 0, 1, 100);
                        break;
                    case"regpotiona":
                        buyPotion(player, PotionEffectType.REGENERATION, 90*20, 0, 1, 150);
                        break;
                    case"regpotion2":
                        buyPotion(player, PotionEffectType.REGENERATION, 22*20, 1, 1, 200);
                        break;
                    case"strengthpotion1":
                        buyPotion(player, PotionEffectType.INCREASE_DAMAGE, 180*20, 0, 1, 200);
                        break;
                    case"strengthpotion2":
                        buyPotion(player, PotionEffectType.INCREASE_DAMAGE, 480*20, 0, 1, 250);
                        break;
                    case"strengthpotion3":
                        buyPotion(player, PotionEffectType.INCREASE_DAMAGE, 90*20, 1, 1, 500);
                        break;
                    case"speedpotion1":
                        buyPotion(player, PotionEffectType.SPEED, 180*20, 0,1,50);
                        break;
                    case"speedpotion2":
                        buyPotion(player, PotionEffectType.SPEED, 480*20, 0,1,150);
                        break;
                    case"speedpotion3":
                        buyPotion(player, PotionEffectType.SPEED, 90*20, 1,1,300);
                        break;
                    case"healthpotion1":
                        buyPotion(player, PotionEffectType.HEAL, 1, 0,1,100);
                        break;
                    case"healthpotion2":
                        buyPotion(player, PotionEffectType.HEAL, 1, 1,1,250);
                        break;
                }
            }
        }else if (event.getView().getTitle().equals("§9§lAdminShop")) {
            event.setCancelled(true);
        }
    }
    private void buyPotion(Player p, PotionEffectType potionType,int duration,int amplifier, int amount, double price) {
        if (removeMoney(p, price)) {
            ItemStack potion = new ItemStack(Material.POTION);
            PotionMeta potionmeta = (PotionMeta) potion.getItemMeta();
            potionmeta.addCustomEffect(new PotionEffect(potionType, duration, amplifier,  true, false), true);
            potionmeta.setDisplayName("§7Mystisches  Getränk");
            potion.setItemMeta(potionmeta);
            Inventory playerInventory = p.getInventory();
            HashMap<Integer, ItemStack> leftOverItems = playerInventory.addItem(potion);
            if (leftOverItems.isEmpty()) {
                p.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.buypotionshop","%amount%", String.valueOf(amount),"%potion%", potionmeta.getDisplayName()));
            } else {
                p.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.inventoryfull","",""));
                addMoney(p, price); // Gib dem Spieler das Geld zurück, da der Kauf nicht abgeschlossen wurde
            }
        } else {
            p.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughmoney","%price%", String.valueOf(price)));
        }
    }

    private void buyItem(Player p, Material material, int amount, double price) {
        if (removeMoney(p, price)) {
            ItemStack item = new ItemStack(material, amount);
            Inventory playerInventory = p.getInventory();
            HashMap<Integer, ItemStack> leftOverItems = playerInventory.addItem(item);
            if (leftOverItems.isEmpty()) {
                p.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.buyshop","%amount%", String.valueOf(amount),"%material%", material.toString()));
            } else {
                p.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.inventoryfull","",""));
                addMoney(p, price); // Gib dem Spieler das Geld zurück, da der Kauf nicht abgeschlossen wurde
            }
        } else {
            p.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughmoney","%price%", String.valueOf(price)));
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
    public void backheadinventory(Player p, Inventory inventar, int slot, String inventory) {
        ItemStack kopf = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) kopf.getItemMeta();
        skullMeta.setDisplayName("§7➢ Zurück");
        skullMeta.setLocalizedName(inventory);
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer("MHF_ArrowLeft"));
        kopf.setItemMeta(skullMeta);
        inventar.setItem(slot, kopf);
        p.openInventory(inventar);
        this.fillEmptySlots(inventar, Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1);
    }
    private void fillEmptySlots(Inventory inventory, Material material, int amount) {
        for (int i = 0; i < inventory.getSize(); ++i) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                ItemStack newItemStack = new ItemStack(material, amount, (short) 7);
                ItemMeta itemMeta = newItemStack.getItemMeta();
                itemMeta.setDisplayName("§a ");
                newItemStack.setItemMeta(itemMeta);
                inventory.setItem(i, newItemStack);
            }
        }
    }
}

package net.dbsgameplay.dungeoncrusher.Commands.LevelSystem;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.UpgradeData;
import net.dbsgameplay.dungeoncrusher.objects.PlayerHead;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class UpgradeCommand implements CommandExecutor {
    MYSQLManager mysqlManager;
    private DungeonCrusher dungeonCrusher;
    public UpgradeCommand(DungeonCrusher dungeonCrusher,MYSQLManager mysqlManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.mysqlManager = mysqlManager;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {

                Inventory upgrade = Bukkit.createInventory(null, 9*6, "§9§lUpgrades");
                upgrade.setItem(45, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ §bSchließen").setLocalizedName("schließen").build());

                String currentmoney = mysqlManager.getBalance(player.getUniqueId().toString());

                upgrade.setItem(53, (new PlayerHead(player.getName(), "§9Dein Geld: §a" + currentmoney + "§9€", new String[0])).getItemStack());
                this.fillEmptySlots(upgrade, Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1);
                player.openInventory(upgrade);
                int currentLevelLeggings = mysqlManager.getLeggingsLevel(player.getUniqueId().toString());
                int currentLevelChestplate = mysqlManager.getChestplateLevel(player.getUniqueId().toString());
                int currentLevel = mysqlManager.getSwordLevel(player.getUniqueId().toString());
                int currentLevelHelmet = mysqlManager.getHelmetLevel(player.getUniqueId().toString());
                String nextLevelChestplate = String.valueOf(currentLevelChestplate + 1);
                String nextLevelLeggings = String.valueOf(currentLevelLeggings + 1);
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
                                    upgrade.setItem(13, maxLevelSword);
                                }else if (currentLevelHelmet >= 10) {
                                    upgrade.setItem(13, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Helm Upgrade").setLocalizedName("helmetupgrade").setLore("§cMax Level erreicht!").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                    return;
                                }else {
                                    upgrade.setItem(13, new ItemBuilder(Material.DIAMOND_HELMET).setDisplayname("§7➢ Helm Upgrade").setLocalizedName("helmetupgrade").setLore("§7Level: §6" + nextLevelHelmet,
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
                                if (currentLevelChestplate >= 280) {
                                    upgrade.setItem(22, new ItemBuilder(Material.DIAMOND_CHESTPLATE).setDisplayname("§7➢ Chestplate Upgrade").setLocalizedName("chestplateupgrade").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                }else if (currentLevelChestplate >= 20) {
                                    upgrade.setItem(22, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ Chestplate Upgrade").setLocalizedName("chestplateupgrade").setLore("§cMax Level erreicht!").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                }else {
                                    upgrade.setItem(22, new ItemBuilder(Material.DIAMOND_CHESTPLATE).setDisplayname("§7➢ Chestplate Upgrade").setLocalizedName("chestplateupgrade").setLore("§7Level: §6" + nextLevel,
                                            "§7Geld: §6" + currentmoney + "§7/§6" + requiredMoney, "§7Rohkupfer: §6" + currentRawCopper + "§7/§6" + requiredRawCopper, "§7Kupferbarren: §6" + currentCopperIngots + "§7/§6" + requiredCopperIngots,
                                            "§7Bruchstein: §6" + currentCobblestone + "§7/§6" + requiredCobblestone,
                                            "§7Stein: §6" + currentStone + "§7/§6" + requiredStone, "§7RohEisen: §6" + currentRawIron + "§7/§6" + requiredRawIron,
                                            "§7Eisenbarren: §6" + currentIron + "§7/§6" + requiredIron, "§7RohGold: §6" + currentRawGold + "§7/§6" + requiredRawGold,
                                            "§7GoldBarren: §6" + currentGold + "§7/§6" + requiredGold, "§7DiamantErz: §6" + currentDiamondOre + "§7/§6" + requiredDiamondOre,
                                            "§7Diamanten: §6" + currentDiamond + "§7/§6" + requiredDiamond, "§7Netheriteplatten: §6" + currentNetheriteScrap + "§7/§6" + requiredNetheriteScrap,
                                            "§7NetheriteBarren: §6" + currentNetherite + "§6/§6" + requiredNetherite).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                }
                                break;
                            case 3:
                                upgrade.setItem(31, new ItemBuilder(Material.DIAMOND_LEGGINGS).setDisplayname("§7➢ Hosen Upgrade").setLocalizedName("leggingsupgrade").setLore("§7Level: §6" + nextLevelLeggings,
                                        "§7Geld: §6" + currentmoney + "§7/§6" + requiredMoney, "§7Rohkupfer: §6" + currentRawCopper + "§7/§6" + requiredRawCopper, "§7Kupferbarren: §6" + currentCopperIngots + "§7/§6" + requiredCopperIngots,
                                        "§7Bruchstein: §6" + currentCobblestone + "§7/§6" + requiredCobblestone,
                                        "§7Stein: §6" + currentStone + "§7/§6" + requiredStone, "§7RohEisen: §6" + currentRawIron + "§7/§6" + requiredRawIron,
                                        "§7Eisenbarren: §6" + currentIron + "§7/§6" + requiredIron, "§7RohGold: §6" + currentRawGold + "§7/§6" + requiredRawGold,
                                        "§7GoldBarren: §6" + currentGold + "§7/§6" + requiredGold, "§7DiamantErz: §6" + currentDiamondOre + "§7/§6" + requiredDiamondOre,
                                        "§7Diamanten: §6" + currentDiamond + "§7/§6" + requiredDiamond, "§7Netheriteplatten: §6" + currentNetheriteScrap + "§7/§6" + requiredNetheriteScrap,
                                        "§7NetheriteBarren: §6" + currentNetherite + "§6/§6" + requiredNetherite).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                break;
                            case 4:
                                upgrade.setItem(40, new ItemBuilder(Material.DIAMOND_BOOTS).setDisplayname("§7➢ Schuh Upgrade").setLocalizedName("bootsupgrade").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                break;
                            case 5:
                                if (currentLevel >= 280) {
                                    ItemStack maxLevelSword = new ItemBuilder(Material.DIAMOND_SWORD)
                                            .setDisplayname("§7➢ Schwert Upgrade")
                                            .setLocalizedName("swordupgrade")
                                            .setLore("§7Level: §6§lMaximales Level erreicht!")
                                            .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                                            .build();
                                    upgrade.setItem(20, maxLevelSword);
                                }else {
                                    upgrade.setItem(20, new ItemBuilder(Material.DIAMOND_SWORD).setDisplayname("§7➢ Schwert Upgrade").setLocalizedName("swordupgrade").setLore("§7Level: §6" + nextLevel,
                                            "§7Geld: §6" + currentmoney +"§7/§6" + requiredMoney,"§7Rohkupfer: §6" + currentRawCopper + "§7/§6" + requiredRawCopper, "§7Kupferbarren: §6" +currentCopperIngots  + "§7/§6" + requiredCopperIngots,
                                            "§7Bruchstein: §6" + currentCobblestone + "§7/§6" +requiredCobblestone ,
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
            }

        } else sender.sendMessage(ConfigManager.getConfigMessage("message.noplayer","",""));
        return false;
    }
    private void fillEmptySlots (Inventory inventory, Material material, int amount){
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

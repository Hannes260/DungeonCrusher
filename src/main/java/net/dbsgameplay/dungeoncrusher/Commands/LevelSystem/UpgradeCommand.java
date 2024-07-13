package net.dbsgameplay.dungeoncrusher.Commands.LevelSystem;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.UpgradeData;
import net.dbsgameplay.dungeoncrusher.objects.PlayerHead;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
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
    private static final int MAX_LEVEL = 10;
    private static final int INVENTORY_SIZE = 9 * 6;
    private static final String INVENTORY_TITLE = "§9§lUpgrades";
    private static final Material CLOSE_BUTTON_MATERIAL = Material.BARRIER;
    private static final String CLOSE_BUTTON_NAME = "§7➢ §bSchließen";

    private final MYSQLManager mysqlManager;
    private final DungeonCrusher dungeonCrusher;

    public UpgradeCommand(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.mysqlManager = mysqlManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                openUpgradeInventory(player);
            }
        } else {
            sender.sendMessage(ConfigManager.getConfigMessage("message.noplayer", "", ""));
        }
        return false;
    }

    private void openUpgradeInventory(Player player) {
        Inventory upgradeInventory = Bukkit.createInventory(null, INVENTORY_SIZE, INVENTORY_TITLE);
        upgradeInventory.setItem(45, new ItemBuilder(CLOSE_BUTTON_MATERIAL).setDisplayname(CLOSE_BUTTON_NAME).setLocalizedName("schließen").build());
        String currentMoney = mysqlManager.getBalance(player.getUniqueId().toString());
        upgradeInventory.setItem(53, new PlayerHead(player.getName(), "§9Dein Geld: §a" + currentMoney + "§9€", new String[0]).getItemStack());

        fillEmptySlots(upgradeInventory, Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1);
        player.openInventory(upgradeInventory);

        int currentLevelHelmet = mysqlManager.getHelmetLevel(player.getUniqueId().toString());
        int[] upgradeDataHelmet = UpgradeData.getUpgradeData(currentLevelHelmet);

        int currentLevelChestplate = mysqlManager.getChestplateLevel(player.getUniqueId().toString());
        int[] upgradeDataChestplate = UpgradeData.getUpgradeData(currentLevelChestplate);

        int currentLevelLeggings = mysqlManager.getLeggingsLevel(player.getUniqueId().toString());
        int[] upgradeDataLeggings = UpgradeData.getUpgradeData(currentLevelLeggings);

        int currentLevelBoots = mysqlManager.getBootsLevel(player.getUniqueId().toString());
        int[] upgradeDataBoots = UpgradeData.getUpgradeData(currentLevelBoots);

        int currentLevelSword = mysqlManager.getSwordLevel(player.getUniqueId().toString());
        int[] upgradeDataSword = UpgradeData.getUpgradeData(currentLevelSword);

        startUpgradeTask(player, upgradeInventory, currentMoney, currentLevelHelmet, upgradeDataHelmet, currentLevelChestplate, upgradeDataChestplate, currentLevelLeggings, upgradeDataLeggings, currentLevelBoots, upgradeDataBoots, currentLevelSword, upgradeDataSword);
    }

    private void startUpgradeTask(Player player, Inventory upgradeInventory, String currentMoney, int currentLevelHelmet, int[] upgradeDataHelmet, int currentLevelChestplate, int[] upgradeDataChestplate, int currentLevelLeggings, int[] upgradeDataLeggings, int currentLevelBoots, int[] upgradeDataBoots, int currentLevelSword, int[] upgradeDataSword) {
        new BukkitRunnable() {
            int tick = 0;

            @Override
            public void run() {
                tick++;
                if (tick > 5) {
                    this.cancel();
                    return;
                }

                switch (tick) {
                    case 1:
                        updateInventorySlot(player, upgradeInventory, 13, Material.DIAMOND_HELMET, "Helm Upgrade", currentLevelHelmet, upgradeDataHelmet, currentMoney);
                        break;
                    case 2:
                        updateInventorySlot(player, upgradeInventory, 22, Material.DIAMOND_CHESTPLATE, "Brustplatten Upgrade", currentLevelChestplate, upgradeDataChestplate, currentMoney);
                        break;
                    case 3:
                        updateInventorySlot(player, upgradeInventory, 31, Material.DIAMOND_LEGGINGS, "Hosen Upgrade", currentLevelLeggings, upgradeDataLeggings, currentMoney);
                        break;
                    case 4:
                        updateInventorySlot(player, upgradeInventory, 40, Material.DIAMOND_BOOTS, "Schuhe Upgrade", currentLevelBoots, upgradeDataBoots, currentMoney);
                        break;
                    case 5:
                        updateInventorySlot(player, upgradeInventory, 20, Material.DIAMOND_SWORD, "Schwert Upgrade", currentLevelSword, upgradeDataSword, currentMoney);
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                        break;
                }
            }
        }.runTaskTimer(this.dungeonCrusher, 0, 1);
    }

    private void updateInventorySlot(Player player, Inventory inventory, int slot, Material material, String upgradeType, int currentLevel, int[] upgradeData, String currentMoney) {
        if (currentLevel >= MAX_LEVEL) {
            inventory.setItem(slot, new ItemBuilder(CLOSE_BUTTON_MATERIAL).setDisplayname("§7➢ " + upgradeType).setLore("§cMax Level Erreicht").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
        } else {
            inventory.setItem(slot, new ItemBuilder(material).setDisplayname("§7➢ " + upgradeType).setLocalizedName(upgradeType.toLowerCase().replace(" ", "")).setLore(generateUpgradeLore(upgradeData, currentMoney)).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
        }
    }

    private String[] generateUpgradeLore(int[] upgradeData, String currentMoney) {
        String[] materials = {"Geld", "Rohkupfer", "Kupferbarren", "Bruchstein", "Stein", "RohEisen", "Eisenbarren", "RohGold", "GoldBarren", "DiamantErz", "Diamanten", "Netheriteplatten", "NetheriteBarren"};
        String[] lore = new String[materials.length];
        for (int i = 0; i < materials.length; i++) {
            lore[i] = String.format("§7%s: §6%s§7/§6%s", materials[i], currentMoney, upgradeData[i]);
        }
        return lore;
    }

    private void fillEmptySlots(Inventory inventory, Material material, int amount) {
        for (int i = 0; i < inventory.getSize(); ++i) {
            if (inventory.getItem(i) == null) {
                ItemStack newItemStack = new ItemStack(material, amount);
                ItemMeta itemMeta = newItemStack.getItemMeta();
                itemMeta.setDisplayName("§a ");
                newItemStack.setItemMeta(itemMeta);
                inventory.setItem(i, newItemStack);
            }
        }
    }
}

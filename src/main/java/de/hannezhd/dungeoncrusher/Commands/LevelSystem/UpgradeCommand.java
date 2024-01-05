package de.hannezhd.dungeoncrusher.Commands.LevelSystem;

import de.hannezhd.dungeoncrusher.DungeonCrusher;
import de.hannezhd.dungeoncrusher.objects.PlayerHead;
import de.hannezhd.dungeoncrusher.sql.MYSQLManager;
import de.hannezhd.dungeoncrusher.utils.ConfigManager;
import de.hannezhd.dungeoncrusher.utils.ItemBuilder;
import de.hannezhd.dungeoncrusher.utils.TexturedHeads;
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

                BukkitRunnable task = new BukkitRunnable() {
                    int tick = 0;

                    @Override
                    public void run() {
                        tick++;
                        switch (tick) {
                            case 1:
                                upgrade.setItem(13, new ItemBuilder(Material.DIAMOND_HELMET).setDisplayname("§7➢ Helm Upgrade").setLocalizedName("helmetupgrade").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                break;
                            case 2:
                                upgrade.setItem(22, new ItemBuilder(Material.DIAMOND_CHESTPLATE).setDisplayname("§7➢ Chestplate Upgrade").setLocalizedName("chestplateupgrade").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                break;
                            case 3:
                                upgrade.setItem(31, new ItemBuilder(Material.DIAMOND_LEGGINGS).setDisplayname("§7➢ Hosen Upgrade").setLocalizedName("leggingsupgrade").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                break;
                            case 4:
                                upgrade.setItem(40, new ItemBuilder(Material.DIAMOND_BOOTS).setDisplayname("§7➢ Schuh Upgrade").setLocalizedName("bootsupgrade").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
                                break;
                            case 5:
                                upgrade.setItem(20, new ItemBuilder(Material.DIAMOND_SWORD).setDisplayname("§7➢ Schwert Upgrade").setLocalizedName("swordupgrade").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build());
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

package de.hannezhd.dungeoncrusher.Commands.Shops;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.hannezhd.dungeoncrusher.DungeonCrusher;
import de.hannezhd.dungeoncrusher.objects.PlayerHead;
import de.hannezhd.dungeoncrusher.sql.MYSQLManager;
import de.hannezhd.dungeoncrusher.utils.ItemBuilder;
import de.hannezhd.dungeoncrusher.utils.TexturedHeads;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class ShopCommand implements CommandExecutor {
    MYSQLManager mysqlManager;
    private DungeonCrusher dungeonCrusher;
    public ShopCommand(DungeonCrusher dungeonCrusher,MYSQLManager mysqlManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.mysqlManager = mysqlManager;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                Inventory inventory = Bukkit.createInventory((InventoryHolder) null, 9 * 6, "§9§lShop");
                inventory.setItem(45, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ §bSchließen").setLocalizedName("schließen").build());


                String currentmoney = mysqlManager.getBalance(player.getUniqueId().toString());

                inventory.setItem(53, (new PlayerHead(player.getName(), "§9Dein Geld: §a" + currentmoney + "§9€", new String[0])).getItemStack());
                this.fillEmptySlots(inventory, Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1);
                player.openInventory(inventory);

                // Animation
                BukkitRunnable task = new BukkitRunnable() {
                    int tick = 0;

                    @Override
                    public void run() {
                        tick++;
                        switch (tick) {
                            case 1:
                                PlayerProfile profile = TexturedHeads.getProfile("https://textures.minecraft.net/texture/1591b61529d25a7ecd6bec00948e6fe155e3007f2d7fe559f3a83c6f808e434d");
                                ItemStack food = new ItemStack(Material.PLAYER_HEAD);
                                SkullMeta meta = (SkullMeta) food.getItemMeta();
                                meta.setOwnerProfile(profile);
                                meta.setDisplayName("§7➢ Essen");
                                meta.setLocalizedName("essen");
                                food.setItemMeta(meta);
                                inventory.setItem(20, food);
                                break;
                            case 2:
                                PlayerProfile upgradeprofile = TexturedHeads.getProfile("https://textures.minecraft.net/texture/77334cddfab45d75ad28e1a47bf8cf5017d2f0982f6737da22d4972952510661");
                                ItemStack upgrade = new ItemStack(Material.PLAYER_HEAD);
                                SkullMeta upgrademeta = (SkullMeta) upgrade.getItemMeta();
                                upgrademeta.setOwnerProfile(upgradeprofile);
                                upgrademeta.setDisplayName("§7➢ Upgrades");
                                upgrademeta.setLocalizedName("upgrades");
                                upgrade.setItemMeta(upgrademeta);
                                inventory.setItem(22, upgrade);
                                break;
                            case 3:
                                PlayerProfile potionprofile = TexturedHeads.getProfile("https://textures.minecraft.net/texture/dcedb2f4c97016cae7b89e4c6d6978d22ac3476c815c5a09a6792450dd918b6c");
                                ItemStack potions = new ItemStack(Material.PLAYER_HEAD);
                                SkullMeta potionmeta = (SkullMeta) potions.getItemMeta();
                                potionmeta.setOwnerProfile(potionprofile);
                                potionmeta.setDisplayName("§7➢ Tränke");
                                potionmeta.setLocalizedName("potions");
                                potions.setItemMeta(potionmeta);
                               inventory.setItem(24, potions);
                                break;
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
        }
        return false;
    }
    private void fillEmptySlots (Inventory inventory, Material material,int amount){
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

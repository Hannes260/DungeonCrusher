package de.hannezhd.dungeoncrusher.Commands.Shops;

import de.hannezhd.dungeoncrusher.DungeonCrusher;
import de.hannezhd.dungeoncrusher.objects.PlayerHead;
import de.hannezhd.dungeoncrusher.sql.MYSQLManager;
import de.hannezhd.dungeoncrusher.utils.ConfigManager;
import de.hannezhd.dungeoncrusher.utils.ItemBuilder;
import de.hannezhd.dungeoncrusher.utils.ScoreboardBuilder;
import de.hannezhd.dungeoncrusher.utils.TexturedHeads;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

import java.util.HashMap;
import java.util.Locale;

public class ShopClickListener implements Listener {
    private MYSQLManager mysqlManager;
    private ScoreboardBuilder scoreboardBuilder;
    public ShopClickListener(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager){
        this.mysqlManager = mysqlManager;
        this.scoreboardBuilder = new ScoreboardBuilder(dungeonCrusher);
    }
    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getView().getTitle() == "§9§lShop") {
            Player player = (Player) event.getWhoClicked();
            event.setCancelled(true);
            if (event.getCurrentItem().getItemMeta().hasLocalizedName()) {
                switch (event.getCurrentItem().getItemMeta().getLocalizedName()) {
                    case"essen":
                        Inventory foodinv = Bukkit.createInventory(null , 9*6,"§9§lShop");
                        foodinv.setItem(4, new ItemBuilder(Material.GOLDEN_APPLE).setDisplayname("§7➢ GoldApfel").setLocalizedName("goldenapple").setLore("§7Anzahl: §61", "§7Preis: §6450€").build());
                        foodinv.setItem(20, new ItemBuilder(Material.COOKIE).setDisplayname("§7➢ Cookie").setLocalizedName("cookie").setLore("§7Anzahl: §61", "§7Preis: §615€").build());
                        foodinv.setItem(22, new ItemBuilder(Material.ENCHANTED_GOLDEN_APPLE).setDisplayname("§7➢ Verzauberter Goldenerapfel").setLocalizedName("enchantedgoldenapple").setLore("§7Anzahl: §61", "§7Preis: §61000€").build());
                        foodinv.setItem(24, new ItemBuilder(Material.BREAD).setDisplayname("§7➢ Brot").setLocalizedName("bread").setLore("§7Anzahl: §61", "§7Preis: §675€").build());
                        foodinv.setItem(49, new ItemBuilder(Material.GRAY_DYE).setLocalizedName("64switchfood").setDisplayname("§bEssen x1").setLore("§7Anzahl: §61", "§7Anzahl: §764").build());
                        backheadinventory(player, foodinv,45,"main");
                        player.openInventory(foodinv);
                        break;
                        case"64switchfood":
                            Inventory foodinv64 = Bukkit.createInventory(null , 9*6,"§9§lShop");
                            foodinv64.setItem(4, new ItemBuilder(Material.GOLDEN_APPLE).setDisplayname("§7➢ GoldApfel").setLocalizedName("goldenapple64").setLore("§7Anzahl: §664", "§7Preis: §628800€").build());
                            foodinv64.setItem(20, new ItemBuilder(Material.COOKIE).setDisplayname("§7➢ Cookie").setLocalizedName("cookie64").setLore("§7Anzahl: §664", "§7Preis: §6960€").build());
                            foodinv64.setItem(22, new ItemBuilder(Material.ENCHANTED_GOLDEN_APPLE).setDisplayname("§7➢ Verzauberter Goldenerapfel").setLocalizedName("enchantedgoldenapple64").setLore("§7Anzahl: §664", "§7Preis: §664000€").build());
                            foodinv64.setItem(24, new ItemBuilder(Material.BREAD).setDisplayname("§7➢ Brot").setLocalizedName("bread64").setLore("§7Anzahl: §664", "§7Preis: §64800€").build());
                            foodinv64.setItem(49, new ItemBuilder(Material.LIME_DYE).setLocalizedName("essen").setDisplayname("§bEssen x64").setLore("§7Anzahl: §71", "§7Anzahl: §664").build());
                            backheadinventory(player, foodinv64,45,"main");
                            player.openInventory(foodinv64);
                            break;
                    case"main":
                        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 9*6, "§9§lShop");
                        inventory.setItem(45, new ItemBuilder(Material.BARRIER).setDisplayname("§7➢ §bSchließen").setLocalizedName("schließen").build());

                        String currentmoney = mysqlManager.getBalance(player.getUniqueId().toString());
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
                        player.closeInventory();
                        player.openInventory(inventory);
                        break;
                    case"schließen":
                        player.closeInventory();
                        break;
                        //Buy
                    case"goldenapple":
                        buyItem(player, Material.GOLDEN_APPLE,1,450);
                        break;
                    case"cookie":
                        buyItem(player, Material.COOKIE,1,15);
                        break;
                    case"enchantedgoldenapple":
                        buyItem(player, Material.ENCHANTED_GOLDEN_APPLE,1,1000);
                        break;
                    case"bread":
                        buyItem(player, Material.BREAD,1,75);
                        break;
                    case"goldenapple64":
                        buyItem(player, Material.GOLDEN_APPLE,64,28800);
                        break;
                    case"cookie64":
                        buyItem(player, Material.COOKIE,64,960);
                        break;
                    case"enchantedgoldenapple64":
                        buyItem(player, Material.ENCHANTED_GOLDEN_APPLE,64,64000);
                        break;
                    case"bread64":
                        buyItem(player, Material.BREAD,64,4800);
                        break;
                }
            }
        }else if (event.getView().getTitle().equals("§9§lAdminShop")) {
            event.setCancelled(true);
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
        p.closeInventory();
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

package net.dbsgameplay.dungeoncrusher.enums;

import net.dbsgameplay.dungeoncrusher.Commands.interfaces.ShopCategory;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
import net.dbsgameplay.dungeoncrusher.utils.shops.ShopManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Locale;

public class FoodCategory implements ShopCategory {

    @Override
    public void openMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9*6, "Essen");
        ItemStack item1 = new ItemStack(Material.APPLE);
        ItemMeta meta1 = item1.getItemMeta();
        meta1.setDisplayName("Apfel");
        item1.setItemMeta(meta1);

        ItemStack item2 = new ItemStack(Material.BREAD);
        ItemMeta meta2 = item2.getItemMeta();
        meta2.setDisplayName("Brot");
        item2.setItemMeta(meta2);

        inv.setItem(10, item1);  // Setze Apfel auf Position 10 (9er Inventory)
        inv.setItem(12, item2);  // Setze Brot auf Position 12 (9er Inventory)
        addBackButton(player, inv);
        player.openInventory(inv);
    }

    @Override
    public void handleItemClick(Player player, ItemStack clickedItem) {
        if (clickedItem.getItemMeta().getDisplayName().equals("§cZurück")){
        }else if (clickedItem.getItemMeta().getDisplayName().equals("Apfel")) {
            player.sendMessage("Apfel");
        } else if (clickedItem.getItemMeta().getDisplayName().equals("Brot")) {
            player.sendMessage("Brot");
        }
    }

    private static void addBackButton(Player player, Inventory inventory) {
        ItemStack backButton = new ItemStack(Material.ARROW);
        backButton.getItemMeta().setDisplayName("§cZurück");
        inventory.setItem(0, backButton);
    }
}
    class upgradeCategory implements ShopCategory {
    @Override
    public void openMenu(Player player) {

    }

    @Override
    public void handleItemClick(Player player, ItemStack clickedItem) {

    }
}
    class potioncategory implements ShopCategory{

    @Override
    public void openMenu(Player player) {

    }

    @Override
    public void handleItemClick(Player player, ItemStack clickedItem) {

    }
}

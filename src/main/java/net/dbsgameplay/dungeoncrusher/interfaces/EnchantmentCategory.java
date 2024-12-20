package net.dbsgameplay.dungeoncrusher.interfaces;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public interface EnchantmentCategory {
    void openMenu(Player player);
    void handleItemClick(Player player, ItemStack clickedItem, ClickType clickType);
}

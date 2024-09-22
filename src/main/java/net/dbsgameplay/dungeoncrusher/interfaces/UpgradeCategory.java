package net.dbsgameplay.dungeoncrusher.interfaces;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface UpgradeCategory {
    void openMenu(Player player);
    void handleItemClick(Player player, ItemStack clickedItem);
}

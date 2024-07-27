package net.dbsgameplay.dungeoncrusher.Commands.interfaces;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface UpgradeCategory {
    void openMenu(Player player);
    void handleItemClick(Player player, ItemStack clickedItem);
    void handleShiftClick(Player player, ItemStack clickedItem);
}

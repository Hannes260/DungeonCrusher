package net.dbsgameplay.dungeoncrusher.Commands.interfaces;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ShopCategory {
    void openMenu(Player player);
    void handleItemClick(Player player, ItemStack clickedItem);
    void handleShiftClick(Player player, ItemStack clickedItem); // Neue Methode für Shift + Linksklick hinzufügen
}

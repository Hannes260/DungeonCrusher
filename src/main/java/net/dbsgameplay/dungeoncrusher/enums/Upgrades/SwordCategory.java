package net.dbsgameplay.dungeoncrusher.enums.Upgrades;

import net.dbsgameplay.dungeoncrusher.Commands.interfaces.UpgradeCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SwordCategory implements UpgradeCategory {
    @Override
    public void openMenu(Player player) {

    }

    @Override
    public void handleItemClick(Player player, ItemStack clickedItem) {

    }

    @Override
    public void handleShiftClick(Player player, ItemStack clickedItem) {
        return;
    }
}

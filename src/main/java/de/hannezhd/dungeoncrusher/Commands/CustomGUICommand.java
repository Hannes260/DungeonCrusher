package de.hannezhd.dungeoncrusher.Commands;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import dev.lone.itemsadder.api.FontImages.TexturedInventoryWrapper;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CustomGUICommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            TexturedInventoryWrapper inventory = new TexturedInventoryWrapper(null,
                    54,
                    ChatColor.BLACK + "Test",
                    new FontImageWrapper("custom:blank_menu")
            );
            inventory.showInventory(player);

            FontImageWrapper texture = new FontImageWrapper("custom:blank_menu");
            if(!texture.exists())
            {
                sender.sendMessage("Cannot find this texture, please make sure you have it in your data folder of ItemsAdder (items_packs and resource_pack)");
                return true;
            }
        }
        return false;
    }
}

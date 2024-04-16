package net.dbsgameplay.dungeoncrusher.Commands;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import dev.lone.itemsadder.api.FontImages.TexturedInventoryWrapper;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestComand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {
        if(!(commandSender instanceof Player))
            return true;

        Player player = (Player) commandSender;

        TexturedInventoryWrapper inventory = new TexturedInventoryWrapper(null,
                54,
                ChatColor.BLACK + "Test",
                new FontImageWrapper("_iainternal:blank_menu")
        );
        inventory.showInventory(player);
        return true;
    }
}

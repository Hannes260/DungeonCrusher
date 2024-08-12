package net.dbsgameplay.dungeoncrusher.Commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class Testcommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String DisplayName = "§f<shift:-8>%oraxen_upgrade%";
            DisplayName = PlaceholderAPI.setPlaceholders(player, DisplayName);
            Inventory inv = Bukkit.createInventory(null, 9*6, DisplayName);
            player.openInventory(inv);
        }
        return false;
    }
}

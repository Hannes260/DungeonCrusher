package net.dbsgameplay.dungeoncrusher.Commands.Admin;

import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class InvseeCommand implements CommandExecutor {
    private String targetName;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null || !target.isOnline()) {
                        sender.sendMessage(ConfigManager.getPrefix() + "§cDer angegebene Spieler ist nicht online.");
                        return true;
                    }
                            if (player.hasPermission("citybuild.invsee.admin")) {
                            player.openInventory(target.getInventory());
                        }else if (player.hasPermission("citybuild.invsee.view")) {
                                targetName = target.getName();
                            Inventory inventory = Bukkit.createInventory(null, InventoryType.PLAYER, "SpielerInventar: " + target.getName());
                            inventory.setContents(target.getInventory().getContents());
                            player.openInventory(inventory);

                        }else
                            player.sendMessage(ConfigManager.nopermission());
                }else
                    player.sendMessage(ConfigManager.getPrefix() + "§cBenutze /invsee <spieler>");
            }else
                player.sendMessage(ConfigManager.getPrefix() + "§cBenutze /invsee <spieler>");
        }else
            sender.sendMessage(ConfigManager.getPrefix() + "§cDu musst ein Spieler sein!");
        return false;
    }
    public String getTargetName() {
        return targetName;
    }

}

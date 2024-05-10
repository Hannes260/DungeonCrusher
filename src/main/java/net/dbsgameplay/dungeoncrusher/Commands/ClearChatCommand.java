package net.dbsgameplay.dungeoncrusher.Commands;

import net.dbsgameplay.dungeoncrusher.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("dc.command.clearchat")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasPermission("dc.clearchat.bypass")) {
                        p.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.clearchatbypass", "%player%",player.getName()));
                    }else {
                        for (int x = 0;x<100;x++) {
                            p.sendMessage(" ");
                        }
                        p.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.clearchat","%player%", player.getName()));
                    }
                }
            }else {
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.nopermission());
            }
        }else
            sender.sendMessage(ConfigManager.getConfigMessage("message.noplayer", "",""));
        return false;
    }
}

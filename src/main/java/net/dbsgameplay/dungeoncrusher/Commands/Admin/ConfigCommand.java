package net.dbsgameplay.dungeoncrusher.Commands.Admin;

import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConfigCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("dc.command.config")) {
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        ConfigManager.reloadConfig();
                        player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.configreload", "", ""));
                    } else
                        player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.configusage", "", ""));
                } else
                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.configusage", "", ""));
            }else
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.nopermission());
            } else
                sender.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.noplayer", "", ""));
        return false;
    }
}

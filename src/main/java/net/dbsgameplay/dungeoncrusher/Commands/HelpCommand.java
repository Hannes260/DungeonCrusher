package net.dbsgameplay.dungeoncrusher.Commands;

import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand implements CommandExecutor {
    @Override
    public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage("§cComing soon...");
            }else
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.helpusage","",""));
        }else
            sender.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.noplayer","",""));
        return false;
    }
}

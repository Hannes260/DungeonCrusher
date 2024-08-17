package net.dbsgameplay.dungeoncrusher.Commands.Admin;

import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("dc.command.heal")) {
                if (args.length == 0) {
                    player.setHealth(20);
                    player.setFoodLevel(20);
                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.healed", "", ""));
                } else if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        target.setHealth(20);
                        target.setFoodLevel(20);
                        player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.healed", "", ""));
                        player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.healedotherplayer", "%player%", target.getName()));
                    } else
                        player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.playernotonline", "", ""));
                } else
                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.healusage", "", ""));
            } else
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.nopermission());
        } else {
            sender.sendMessage(ConfigManager.getConfigMessage("message.noplayer", "", ""));
        }
        return false;
    }
}

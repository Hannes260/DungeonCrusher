package net.dbsgameplay.dungeoncrusher.Commands;

import net.dbsgameplay.dungeoncrusher.utils.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.LocationConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
public class SetSpawnCommand implements CommandExecutor {
    LocationConfigManager locationConfigManager;
    public SetSpawnCommand(LocationConfigManager locationConfigManager) {
        this.locationConfigManager = locationConfigManager;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("dc.command.setspawn")) {
                if (args.length == 0) {
                    locationConfigManager.setSpawn(player.getLocation());
                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.setspawn", "", ""));
                } else {
                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.setspawnusage", "", ""));
                }
            }else {
                player.sendMessage(ConfigManager.nopermission());
            }
        }else {
            sender.sendMessage(ConfigManager.getConfigMessage("message.noplayer", "", ""));
        }
        return false;
    }
}

package de.hannezhd.dungeoncrusher.Commands;

import de.hannezhd.dungeoncrusher.utils.ConfigManager;
import de.hannezhd.dungeoncrusher.utils.LocationConfigManager;
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
            if (args.length == 0) {
                locationConfigManager.setSpawn(player.getLocation());
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.setspawn", "",""));
            }else {
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.setspawnusage", "",""));
            }
        } sender.sendMessage(ConfigManager.getConfigMessage("message.noplayer", "",""));
        ConfigManager.getConfigMessage("message.noplayer", "","");
        return false;
    }
}

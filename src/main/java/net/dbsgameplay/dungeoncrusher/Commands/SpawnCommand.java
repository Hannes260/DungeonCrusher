package net.dbsgameplay.dungeoncrusher.Commands;

import net.dbsgameplay.dungeoncrusher.utils.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.SavezoneManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    LocationConfigManager locationConfigManager;
    SavezoneManager savezoneManager;
    public SpawnCommand(LocationConfigManager locationConfigManager, SavezoneManager savezoneManager) {
        this.locationConfigManager = locationConfigManager;
        this.savezoneManager = savezoneManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                Location spawnLocation = locationConfigManager.getSpawn();
                if (spawnLocation != null) {
                    player.teleport(spawnLocation);
                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.spawn", "",""));
                }else
                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.nospawn", "",""));
            }else
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.spawnusage", "",""));
        }else
            sender.sendMessage(ConfigManager.getConfigMessage("message.noplayer", "",""));
        return false;
    }
}

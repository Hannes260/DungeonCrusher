package de.hannezhd.dungeoncrusher.Commands;

import de.hannezhd.dungeoncrusher.utils.ConfigManager;
import de.hannezhd.dungeoncrusher.utils.LocationConfigManager;
import de.hannezhd.dungeoncrusher.utils.SavezoneManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

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

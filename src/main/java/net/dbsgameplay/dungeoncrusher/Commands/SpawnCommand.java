package net.dbsgameplay.dungeoncrusher.Commands;

import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Manager.SavezoneManager;
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

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (args.length == 0) {
                Location spawnLocation = this.locationConfigManager.getSpawn();
                if (spawnLocation != null) {
                    player.teleport(spawnLocation);
                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getPrefix());
                } else {
                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getPrefix());
                }
            } else {
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getPrefix());
            }
        } else {
            sender.sendMessage(ConfigManager.getConfigMessage("message.noplayer", new String[] { "", "" }));
        }
        return false;
    }
}
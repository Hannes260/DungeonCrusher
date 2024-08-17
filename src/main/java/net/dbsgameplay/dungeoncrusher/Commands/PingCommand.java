package net.dbsgameplay.dungeoncrusher.Commands;

import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class PingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            int ping = player.getPing();
            Server server = player.getServer();
            player.sendMessage(ConfigManager.getPrefix()+ ConfigManager.getConfigMessage("message.getping", "%ping%",String.valueOf(ping)));
        }else{
            sender.sendMessage(ConfigManager.getConfigMessage("message.noplayer","",""));
        }
        return false;
    }
}

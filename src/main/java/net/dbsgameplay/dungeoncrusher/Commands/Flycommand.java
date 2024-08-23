package net.dbsgameplay.dungeoncrusher.Commands;

import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Flycommand implements CommandExecutor {
    private final Set<Player> flyingPlayers = new HashSet<>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("dc.command.fly")) {
                if (flyingPlayers.contains(player)) {
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    flyingPlayers.remove(player);
                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.flydisabled","",""));

                }else {
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    flyingPlayers.add(player);
                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.flyenabled","",""));
                }
            }else
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.nopermission());
        }else
            sender.sendMessage(ConfigManager.getConfigMessage("message.noplayer","",""));
        return false;
    }
}

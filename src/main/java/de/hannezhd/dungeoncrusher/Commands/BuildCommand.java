package de.hannezhd.dungeoncrusher.Commands;

import de.hannezhd.dungeoncrusher.listener.DungeonProtectionListener;
import de.hannezhd.dungeoncrusher.utils.ConfigManager;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BuildCommand implements CommandExecutor {
    private DungeonProtectionListener dungeonProtectionListener;

    public BuildCommand(DungeonProtectionListener dungeonProtectionListener) {
        this.dungeonProtectionListener = dungeonProtectionListener;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("dc.command.build")) {
                boolean buildModeEnabled = dungeonProtectionListener.isBuildModeEnabled(player);
                if (buildModeEnabled) {
                    dungeonProtectionListener.setBuildMode(player, false);
                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.buildmodeoff", "",""));
                    player.setGameMode(GameMode.SURVIVAL);
                } else {
                    dungeonProtectionListener.setBuildMode(player, true);
                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.buildmodeon","",""));
                    player.setGameMode(GameMode.CREATIVE);
                }
                return true;
            } else {
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.nopermission","",""));
            }
        }
        return false;
    }
}

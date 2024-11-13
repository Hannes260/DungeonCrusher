package net.dbsgameplay.dungeoncrusher.Commands;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.listener.ErfolgeListener;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.ErfolgeBuilders;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ErfolgeCommand implements CommandExecutor {
    private LocationConfigManager locationConfigManager;
    private DungeonCrusher dungeonCrusher;

    public ErfolgeCommand(DungeonCrusher dungeonCrusher,LocationConfigManager locationConfigManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.locationConfigManager = locationConfigManager;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player p)) return true;

        if (args.length == 0) {
            ErfolgeListener.ebeneHashMap.put(p.getUniqueId(), 0);

            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 1);

            p.openInventory(ErfolgeBuilders.getInventory(p, 0));
        }else {
            p.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.erfolgeusage","",""));
        }

        return false;
    }
}
package net.dbsgameplay.dungeoncrusher.Commands;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.listener.ErfolgeListener;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.ErfolgeBuilders;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
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
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player p)) return true;

        if (strings.length == 0) {
            ErfolgeListener.ebeneHashMap.put(p.getUniqueId(), locationConfigManager.getEbene(p));
            ErfolgeListener.ebeneHashMap.replace(p.getUniqueId(), locationConfigManager.getEbene(p));

            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
            p.openInventory(ErfolgeBuilders.getInventory(locationConfigManager.getEbene(p), p));
        }else {
            p.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.erfolgeusage"),"","");
        }

        return false;
    }
}
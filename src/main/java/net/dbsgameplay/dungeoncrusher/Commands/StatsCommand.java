package net.dbsgameplay.dungeoncrusher.Commands;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {
    public final DungeonCrusher dungeonCrusher;
    private MYSQLManager mysqlManager;

    public StatsCommand(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.mysqlManager = mysqlManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0) {
                player.sendMessage(ConfigManager.getPrefix() + "§aKills: §6" + mysqlManager.getKills(String.valueOf(player.getUniqueId())));
                System.out.println(mysqlManager.getItemAmount(player.getUniqueId().toString(), "copper_ingot"));
            }else
                player.sendMessage(ConfigManager.getConfigMessage("message.statsusage",""));
        }else
                ConfigManager.getConfigMessage("message.noplayer","");
        return false;
    }
}

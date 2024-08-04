package net.dbsgameplay.dungeoncrusher.Commands.Upgrades;

import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.upgrades.UpgradeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UpgradeCommand implements CommandExecutor {
    MYSQLManager mysqlManager;
    public UpgradeCommand(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                UpgradeManager.openMainMenu(player);
            }
        }
        return false;
    }
}

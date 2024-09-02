package net.dbsgameplay.dungeoncrusher.Commands.Quests;

import com.destroystokyo.paper.Title;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.QuestBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class QuestCommand implements CommandExecutor {

    MYSQLManager mysqlManager;

    public QuestCommand(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return true;


        if (mysqlManager.getTutorialQuest(p.getUniqueId().toString()).equalsIgnoreCase("t0")) {
            QuestBuilder.fillQuestmenü(p);
            p.openInventory(QuestBuilder.getQuestmenü());
        }else {
            p.sendTitle(new Title("§cDu hast das tutorial noch nicht abgeschlossen (Bossbar)!"));
        }

        return false;
    }
}

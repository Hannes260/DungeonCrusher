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
            if (QuestBuilder.unclaimedQuestRewards.containsKey(p.getUniqueId().toString())) {
                String[] questIDs = QuestBuilder.unclaimedQuestRewards.get(p.getUniqueId().toString()).keySet().toArray(new String[0]);
                String questID = questIDs[0];
                QuestBuilder.openRewardmenü(p, questID, QuestBuilder.unclaimedQuestRewards.get(p.getUniqueId().toString()));
                QuestBuilder.unclaimedQuestRewards.remove(p.getUniqueId().toString());
                return true;
            }
            QuestBuilder.fillQuestmenü(p);
            p.openInventory(QuestBuilder.getQuestmenü());
        }else {
            p.sendActionBar("§c§lDu hast das TUTORIAL noch nicht abgeschlossen!");
        }

        return false;
    }
}

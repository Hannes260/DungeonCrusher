package net.dbsgameplay.dungeoncrusher.Commands.Quests;

import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.quests.QuestBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QuestCommand implements CommandExecutor {

    MYSQLManager mysqlManager;

    public QuestCommand(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return true;

        if (mysqlManager.getTutorialQuest(p.getUniqueId().toString()).equalsIgnoreCase("t0")) {
            for (int i = 0; i != 10; i++) {
              if (QuestBuilder.unclaimedQuestRewards.containsKey(p.getUniqueId().toString()+i)) {
                  QuestBuilder.openRewardmenü(p, QuestBuilder.unclaimedQuestRewards.get(p.getUniqueId().toString()+i));
                  QuestBuilder.unclaimedQuestRewards.remove(p.getUniqueId().toString()+i);
                  return true;
              }
            }
            
            QuestBuilder.fillQuestmenü(p);
            p.openInventory(QuestBuilder.getQuestmenü());
        }else {
            p.sendActionBar("§c§lDu hast das TUTORIAL noch nicht abgeschlossen!");
        }

        return false;
    }
}

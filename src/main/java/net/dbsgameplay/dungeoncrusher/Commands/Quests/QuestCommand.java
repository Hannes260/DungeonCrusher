package net.dbsgameplay.dungeoncrusher.Commands.Quests;

import net.dbsgameplay.dungeoncrusher.utils.QuestBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class QuestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return true;
        //Öffne ein Inventar mit 4 Quest types [Tutorial, Daily, Weekly, Monthly]
        //In ner cfg speichern ob man die tutorial fertig hat um die anderen quest machenzu koönnen
        //"Liste" mit QuestID - Quest- Beohnung
        //Quest setzten
        //Quest tracken
        //Belohnung bekommen
        //Quest Sperren
        //Quest entsperren wenn neue kommt

        QuestBuilder.fillQuestmenü(p);
        p.openInventory(QuestBuilder.getQuestmenü());
        return false;
    }
}

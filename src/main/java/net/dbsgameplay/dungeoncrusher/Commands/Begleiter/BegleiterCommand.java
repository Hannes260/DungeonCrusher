package net.dbsgameplay.dungeoncrusher.Commands.Begleiter;

import net.dbsgameplay.dungeoncrusher.utils.Begleiter.BegleiterBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BegleiterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player p = (Player) commandSender;

        if (strings.length == 0) {
            BegleiterBuilder.openBegleiterMenü(p);
        }else if (strings[0].equalsIgnoreCase("ziehen")) {
            BegleiterBuilder.zieheBegleiter(p);
        }

        return false;
    }
}

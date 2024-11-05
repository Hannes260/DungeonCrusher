package net.dbsgameplay.dungeoncrusher.Commands.Begleiter;

import net.dbsgameplay.dungeoncrusher.utils.Begleiter.BegleiterBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BegleiterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player p = (Player) commandSender;

        BegleiterBuilder begleiterBuilder = new BegleiterBuilder();
        begleiterBuilder.setLocation(p).setName("text1").setSkin(Material.ZOMBIE_HEAD).build();

        return false;
    }
}

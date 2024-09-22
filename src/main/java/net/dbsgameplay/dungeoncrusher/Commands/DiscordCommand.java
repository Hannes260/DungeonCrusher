package net.dbsgameplay.dungeoncrusher.Commands;

import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;


public class DiscordCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;
        if (sender instanceof Player){
            TextComponent c = new TextComponent("§9System &f● §9Unser Discord: ");
            TextComponent clickMe = new TextComponent("§e§nKlicke hier");
            clickMe.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/KFdJtJ5pZD"));
            clickMe.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§aBesuche unseren Discord")));
            c.addExtra(clickMe);

            player.sendMessage(c);
        }else {
            sender.sendMessage(ConfigManager.getPrefix() + "§cDu musst ein Spieler sein");
        }

        return false;
    }
}

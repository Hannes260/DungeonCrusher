package net.dbsgameplay.dungeoncrusher.Commands.Admin;

import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlySpeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Dieser Befehl kann nur von Spielern verwendet werden.");
            return true;
        }
        Player player = (Player) sender;
            if (!player.hasPermission("dc.command.flyspeed")) {
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.nopermission());
                return true;
            }

        if (args.length != 1) {
            sender.sendMessage(ConfigManager.getPrefix() + "Verwendung: /flyspeed <Geschwindigkeit>");
            return true;
        }

        try {
            float speedMultiplier = Float.parseFloat(args[0]);
            if (speedMultiplier < 0.1 || speedMultiplier > 10.0) {
                sender.sendMessage(ConfigManager.getPrefix() + "Die Geschwindigkeit muss zwischen 0.1 und 10.0 liegen.");
                return true;
            }

            float speed = speedMultiplier / 10.0f; // Konvertiere den Multiplikator in eine Geschwindigkeitszahl
            player.setFlySpeed(speed);
            sender.sendMessage(ConfigManager.getPrefix() + "Fluggeschwindigkeit auf " + speedMultiplier + "x gesetzt.");

        } catch (NumberFormatException e) {
            sender.sendMessage(ConfigManager.getPrefix() + "Ungültige Geschwindigkeitsangabe. Verwende eine Dezimalzahl zwischen 0.1 und 10.0.");
        }

        return true;
    }
}

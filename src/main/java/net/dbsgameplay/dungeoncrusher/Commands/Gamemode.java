package net.dbsgameplay.dungeoncrusher.Commands;

import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Gamemode implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("0")) {
                    if (args.length == 1) {
                        player.setGameMode(GameMode.SURVIVAL);
                        player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.setsurvival","",""));
                    } else if (args.length == 2) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            target.setGameMode(GameMode.SURVIVAL);
                            target.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.setsurvival","",""));
                            player.sendMessage(ConfigManager.getPrefix()+ ConfigManager.getConfigMessage("message.setanothersurvival","%target_player", target.getName()));
                        } else
                            player.sendMessage(ConfigManager.getPrefix() + "§aDer Spieler §e" + args[0] + " §aist nicht online");
                    } else
                        player.sendMessage(ConfigManager.getPrefix() + "§cBitte benutze /gm 0 | 1 | 2 | 3");

                } else if (args[0].equalsIgnoreCase("1")) {
                    if (args.length == 1) {
                        player.setGameMode(GameMode.CREATIVE);
                        player.sendMessage(ConfigManager.getPrefix() + "§aDu wurdest in den Creative Gesetzt");
                    } else if (args.length == 2) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            target.setGameMode(GameMode.CREATIVE);
                            target.sendMessage(ConfigManager.getPrefix() + "§aDu wurdest in den Creative gesetzt");
                            player.sendMessage(ConfigManager.getPrefix() + "§aDu hast §e" + target.getName() + " §ain den Creative gesetzt");
                        } else
                            player.sendMessage(ConfigManager.getPrefix() + "§aDer Spieler §e" + args[0] + " §aist nicht online");
                    } else
                        player.sendMessage(ConfigManager.getPrefix() + "§cBitte benutze /gm 0 | 1 | 2 | 3");

                } else if (args[0].equalsIgnoreCase("2")) {
                    if (args.length == 1) {
                        player.setGameMode(GameMode.ADVENTURE);
                        player.sendMessage(ConfigManager.getPrefix() + "§aDu wurdest in den Adventure Gesetzt");
                    } else if (args.length == 2) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            target.setGameMode(GameMode.CREATIVE);
                            target.sendMessage(ConfigManager.getPrefix() + "§aDu wurdest in den Adventure  gesetzt");
                            player.sendMessage(ConfigManager.getPrefix() + "§aDu hast §e" + target.getName() + " §ain den Adventure  gesetzt");
                        } else
                            player.sendMessage(ConfigManager.getPrefix() + "§aDer Spieler §e" + args[0] + " §aist nicht online");
                    } else
                        player.sendMessage(ConfigManager.getPrefix() + "§cBitte benutze /gm 0 | 1 | 2 | 3");

                } else if (args[0].equalsIgnoreCase("3")) {
                    if (args.length == 1) {
                        player.setGameMode(GameMode.SPECTATOR);
                        player.sendMessage(ConfigManager.getPrefix() + "§aDu wurdest in den Spectator Gesetzt");
                    } else if (args.length == 2) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            target.setGameMode(GameMode.SPECTATOR);
                            target.sendMessage(ConfigManager.getPrefix() + "§aDu wurdest in den Spectator  gesetzt");
                            player.sendMessage(ConfigManager.getPrefix() + "§aDu hast §e" + target.getName() + " §ain den Spectator  gesetzt");
                        } else
                            player.sendMessage(ConfigManager.getPrefix() + "§aDer Spieler §e" + args[0] + " §aist nicht online");
                    } else
                        player.sendMessage(ConfigManager.getPrefix() + "§cBitte benutze /gm 0 | 1 | 2 | 3");
                }
            } else
                player.sendMessage(ConfigManager.getPrefix() + "§cBenutze /gm 1 | 2 | 3 | 0");
        }
                return false;
            }
        }


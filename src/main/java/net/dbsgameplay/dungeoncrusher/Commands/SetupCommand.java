package net.dbsgameplay.dungeoncrusher.Commands;

import net.dbsgameplay.dungeoncrusher.utils.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.MarkierungsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class SetupCommand implements CommandExecutor {
    private final MarkierungsManager markierungsManager;
    private LocationConfigManager locationConfigManager;

    public SetupCommand(MarkierungsManager markierungsManager, LocationConfigManager locationConfigManager) {
        this.markierungsManager = markierungsManager;
        this.locationConfigManager = locationConfigManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 1) {
                String name = args[1];

                if (player.hasPermission("dc.setup")) {
                    if (args[0].equalsIgnoreCase("setdungeon")) {
                        if (!locationConfigManager.getDungeonsAndSavezones().containsKey(name)) {
                            markierungsManager.toggleMarkierungsModus(player, name, "dungeon", "");
                            return true;
                        } else {
                            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.setupdungeonexists", "", ""));
                            return false;
                        }
                    } else if (args[0].equalsIgnoreCase("setsavezone")) {
                        if (args.length > 2) {
                            String savezoneName = args[2];
                            if (!locationConfigManager.isLocationSet(name + "." + savezoneName + ".pos1")) {
                                markierungsManager.toggleMarkierungsModus(player, name, "savezone", savezoneName);
                                return true;
                            } else {
                                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.setupsavezoneexists", "", ""));
                                return false;
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("removedungeon")) {
                        locationConfigManager.deleteDungeon(name);
                        player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.setupremovedungeon","",""));
                    }else if (args[0].equalsIgnoreCase("removesavezone")) {
                        if (args.length > 2) {
                            locationConfigManager.deleteSavezone(name, args[2]);
                            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.setupremovesavezone","",""));
                        }else
                            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.setupremovesavezoneusage","",""));
                    }else if (args[0].equalsIgnoreCase("setmobtypes") && args.length > 2) {
                        locationConfigManager.setMobTypes(name, Arrays.copyOfRange(args, 2, args.length));
                        player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.setmobtypes", "",""));
                        return true;
                    } else if (args[0].equalsIgnoreCase("setspawnchance") && args.length > 2) {
                        setSpawnChance(player, name, args[2]);
                        return true;
                    }else if (args[0].equalsIgnoreCase("setspawn")) {
                            if (name != null) {
                                locationConfigManager.saveSpawnpoint(name, player.getLocation());
                                return true;
                            }else
                                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.setspawnusage", "",""));
                        } else if (args[0].equalsIgnoreCase("setkills")) {
                        if (args.length > 2) {
                            locationConfigManager.saveKills(name, Integer.valueOf(args[2]));
                            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.savekills","%kills%",args[2]));
                        }else
                            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.savekillsusage", "",""));
                    }
                } else {
                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.nopermission());
                }
            }else
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.setupusage","",""));
        }else
            sender.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.noplayer","",""));
        return false;
    }


    private void setSpawnChance(Player player, String dungeonName, String spawnChance) {
        try {
            double spawnChanceValue = Double.parseDouble(spawnChance);

            // Stelle sicher, dass die Spawn-Chance im gültigen Bereich liegt (0.0 - 1.0)
            if (spawnChanceValue < 0.0 || spawnChanceValue > 1.0) {
                player.sendMessage("Spawn chance must be between 0.0 and 1.0.");
                return;
            }

            locationConfigManager.getConfiguration().set(dungeonName + ".spawnChance", spawnChanceValue);
            locationConfigManager.saveConfig();
            player.sendMessage(ConfigManager.getPrefix() + "Spawn chance set for dungeon: " + dungeonName);
        } catch (NumberFormatException e) {
            player.sendMessage("Invalid spawn chance value. Please enter a valid number.");
        }
    }
}

package de.hannezhd.dungeoncrusher.Commands.Economy;

import de.hannezhd.dungeoncrusher.DungeonCrusher;
import de.hannezhd.dungeoncrusher.sql.MYSQLManager;
import de.hannezhd.dungeoncrusher.utils.ConfigManager;
import de.hannezhd.dungeoncrusher.utils.ScoreboardBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

public class CoinsCommand implements CommandExecutor {
    public final DungeonCrusher dungeonCrusher;
    private final ScoreboardBuilder scoreboardBuilder;
    private MYSQLManager mysqlManager;
    public CoinsCommand(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.scoreboardBuilder = new ScoreboardBuilder(dungeonCrusher);
        this.mysqlManager = mysqlManager;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                if (args.length == 1) {
                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.moneyusage","",""));
                    return true;
                }
                Player selectedPlayer = Bukkit.getPlayer(args[1]);

                if (sender != null) {
                    double moneyremove;
                    if (args[0].equalsIgnoreCase("set")) {
                        if (player.hasPermission("dc.command.money.set")) {
                            if (args.length == 3) {

                                if (selectedPlayer == null) {
                                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.noplayerfound","",""));
                                    return true;
                                }

                                moneyremove = Double.parseDouble(args[2]);
                                if (moneyremove < 0) {
                                    moneyremove = 0;
                                }

                                String formattedMoney = String.format(Locale.ENGLISH, "%,.2f", moneyremove);
                                mysqlManager.updateBalance(String.valueOf(selectedPlayer.getUniqueId()), formattedMoney);

                                scoreboardBuilder.updateMoney(selectedPlayer);

                                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.moneyset", "%selected_player%", selectedPlayer.getName(), "%money%", formattedMoney));
                            } else {
                                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.moneysetusage", "",""));
                            }
                        } else
                            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.nopermission());
                    } else {
                        double newmoney;
                        if (args[0].equalsIgnoreCase("add")) {
                            if (player.hasPermission("dc.command.money.add")) {
                                if (args.length == 3) {
                                        moneyremove = Double.parseDouble(args[2]);

                                    if (args[1].equalsIgnoreCase("*")) {
                                        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                                                String currentmoney = mysqlManager.getBalance(onlinePlayer.getUniqueId().toString());
                                                currentmoney = currentmoney.replace(",", "");
                                                double currentmoney3 = Double.parseDouble(currentmoney);
                                                newmoney = currentmoney3 + moneyremove;
                                                if (newmoney < 0) {
                                                    newmoney = 0;
                                                }
                                                String formattedMoney = String.format(Locale.ENGLISH, "%,.2f", newmoney);
                                                mysqlManager.updateBalance(String.valueOf(onlinePlayer.getUniqueId()), formattedMoney);

                                                scoreboardBuilder.updateMoney(onlinePlayer);

                                                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.moneyadd", "%selected_player%", selectedPlayer.getName(),"%money%", String.valueOf(moneyremove)));
                                        }
                                    } else {
                                        if (selectedPlayer == null) {
                                            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.noplayerfound","",""));
                                            return true;
                                        }
                                            String currentmoney = mysqlManager.getBalance(selectedPlayer.getUniqueId().toString());//10000.00
                                            currentmoney = currentmoney.replace(",", "");
                                            double currentmoney3 = Double.parseDouble(currentmoney);
                                            newmoney = currentmoney3 + moneyremove;
                                            if (newmoney < 0) {
                                                newmoney = 0;
                                                return false;
                                            }
                                            String formattedMoney = String.format(Locale.ENGLISH, "%,.2f", newmoney);
                                            mysqlManager.updateBalance(String.valueOf(selectedPlayer.getUniqueId()), formattedMoney);

                                            scoreboardBuilder.updateMoney(selectedPlayer);

                                            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.moneyadd", "%selected_player%",selectedPlayer.getName(),"%money%", String.valueOf(moneyremove)));
                                         }
                                        } else{
                                            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.moneyaddusage", "",""));
                                        }
                                    } else {
                                        player.sendMessage(ConfigManager.getPrefix() + ConfigManager.nopermission());
                                    }
                                } else if (args[0].equalsIgnoreCase("remove")) {
                                    if (player.hasPermission("dc.command.money.remove")) {
                                        if (args.length == 3) {

                                            if (selectedPlayer == null) {
                                                player.sendMessage(ConfigManager.getConfigMessage("message.noplayerfound","",""));
                                                return true;
                                            }
                                            moneyremove = Double.parseDouble(args[2]);
                                                String currentmoney2 = mysqlManager.getBalance(selectedPlayer.getUniqueId().toString());
                                                currentmoney2 = currentmoney2.replace(",", "");
                                                double currentmoney3 = Double.parseDouble(currentmoney2);
                                                newmoney = currentmoney3 - moneyremove;
                                                if (newmoney < 0) {
                                                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.moneyremovenoenoughmoney", "%selected_player%",selectedPlayer.getName(), "%current_money%", String.valueOf(currentmoney3)));
                                                    return false;
                                                }
                                                String formattedMoney = String.format(Locale.ENGLISH, "%,.2f", newmoney);
                                                mysqlManager.updateBalance(String.valueOf(selectedPlayer.getUniqueId()), formattedMoney);

                                                scoreboardBuilder.updateMoney(selectedPlayer);

                                                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.moneyremove","%selected_player%",selectedPlayer.getName(), "%money_remove%", String.valueOf(moneyremove)));
                                        } else {
                                            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.moneyremoveusage","",""));
                                        }
                                    } else {
                                        player.sendMessage(ConfigManager.getPrefix() + ConfigManager.nopermission());
                                    }
                                } else if (args[0].equalsIgnoreCase("see")) {
                                    if (args.length == 2) {

                                        if (selectedPlayer == null) {
                                            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.noplayerfound","",""));
                                            return true;
                                        }

                                        String currentMoney = mysqlManager.getBalance(selectedPlayer.getUniqueId().toString());

                                        player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.moneysee", "%selected_player%",selectedPlayer.getName(), "%current_money%", currentMoney));
                                    } else
                                        player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.moneyusage", "", ""));
                                }
                            }
                        } else {
                            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.noplayerfound","",""));
                        }
                    } else{
                        Player selectedPlayer = (Player) sender;
                        String currentMoney = mysqlManager.getBalance(selectedPlayer.getUniqueId().toString());

                        player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.yourmoney", "%current_money%", currentMoney));

                    }

                } else if (args.length > 0) {
                    Player selectedPlayer = Bukkit.getPlayer(args[1]);
                    if (selectedPlayer == null) {
                        return true;
                    }

                    if (sender != null) {
                        double moneyremove;
                        if (args[0].equalsIgnoreCase("set")) {
                            if (args.length == 3) {

                                moneyremove = Double.parseDouble(args[2]);
                                if (moneyremove < 0) {
                                    moneyremove = 0;
                                }

                                String formattedMoney = String.format(Locale.ENGLISH, "%,.2f", moneyremove);
                                mysqlManager.updateBalance(selectedPlayer.getUniqueId().toString(), formattedMoney);

                                scoreboardBuilder.updateMoney(selectedPlayer);

                                sender.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.moneyset",  "%selected_player%",selectedPlayer.getName(), "%money%",formattedMoney));
                            } else {
                                sender.sendMessage(ConfigManager.getPrefix() + "§cBenutze /money set <name> <Anzahl>");
                            }
                        } else {
                            double newmoney;
                            if (args[0].equalsIgnoreCase("add")) {
                                if (args.length == 3) {
                                    moneyremove = Double.parseDouble(args[2]);
                                    if (args[1].equalsIgnoreCase("*")) {
                                        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                                            String currentmoney = mysqlManager.getBalance(onlinePlayer.getUniqueId().toString());
                                            currentmoney = currentmoney.replace(",", "");
                                            double currentmoney3 = Double.parseDouble(currentmoney);
                                            newmoney = currentmoney3 + moneyremove;
                                            if (newmoney < 0) {
                                                newmoney = 0;
                                            }
                                            String formattedMoney = String.format(Locale.ENGLISH, "%,.2f", newmoney);
                                            mysqlManager.updateBalance(String.valueOf(onlinePlayer.getUniqueId()), formattedMoney);

                                            scoreboardBuilder.updateMoney(onlinePlayer);

                                            sender.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.moneyadd", "%selected_player%",onlinePlayer.getName(),"%money%", String.valueOf(moneyremove)));
                                        }
                                    }else {
                                        String currentmoney = mysqlManager.getBalance(selectedPlayer.getUniqueId().toString());
                                        currentmoney = currentmoney.replace(",", "");
                                        double currentmoney3 = Double.parseDouble(currentmoney);
                                        newmoney = currentmoney3 + moneyremove;
                                        if (newmoney < 0) {
                                            newmoney = 0;
                                            return false;
                                        }
                                        String formattedMoney = String.format(Locale.ENGLISH, "%,.2f", newmoney);
                                        mysqlManager.updateBalance(selectedPlayer.getUniqueId().toString(), formattedMoney);

                                        scoreboardBuilder.updateMoney(selectedPlayer);

                                        sender.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.moneyadd", "%selected_player%",selectedPlayer.getName(), "%money%", String.valueOf(moneyremove)));
                                    }
                                } else {
                                    sender.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.moneyaddusage","",""));
                                }
                            } else if (args[0].equalsIgnoreCase("remove")) {
                                if (args.length == 3) {
                                    moneyremove = Double.parseDouble(args[2]);
                                        String currentmoney2 = mysqlManager.getBalance(selectedPlayer.getUniqueId().toString());
                                        currentmoney2 = currentmoney2.replace(",", "");
                                        double currentmoney3 = Double.parseDouble(currentmoney2);
                                        newmoney = currentmoney3 - moneyremove;
                                        if (newmoney < 0) {
                                            sender.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.moneyremovenotenoughmone", "%selected_player%",selectedPlayer.getName(), "%current_money%", String.valueOf(currentmoney3)));
                                            return false;
                                        }
                                        String formattedMoney = String.format(Locale.ENGLISH, "%,.2f", newmoney);
                                        mysqlManager.updateBalance(selectedPlayer.getUniqueId().toString(), formattedMoney);


                                        scoreboardBuilder.updateMoney(selectedPlayer);

                                        sender.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.moneyremove","%selected_player%", selectedPlayer.getName(), "%money_remove%", String.valueOf(moneyremove)));
                                } else {
                                    sender.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.moneyremoveusage","",""));
                                }
                            }
                        }
                    }
                } else {
                    sender.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.noplayer","",""));
                }

                return false;
            }
}
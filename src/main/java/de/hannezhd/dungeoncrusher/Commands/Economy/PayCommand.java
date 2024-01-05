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

import java.io.ObjectInputFilter;
import java.util.Locale;

public class PayCommand implements CommandExecutor {
    private final DungeonCrusher dungeonCrusher;
    private final ScoreboardBuilder scoreboardBuilder;
    private final MYSQLManager mysqlManager;

    public PayCommand(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.scoreboardBuilder = new ScoreboardBuilder(dungeonCrusher);
        this.mysqlManager = mysqlManager;
    }
    @Override
    public boolean onCommand(CommandSender sender,Command command,String label,String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length >= 2) {
                Player selectedPlayer = Bukkit.getPlayer(args[0]);
                 if (selectedPlayer != null && selectedPlayer.isOnline()) {
                    if (selectedPlayer.getName().equalsIgnoreCase(player.getName())) {
                        player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.selfpay","",""));
                        return false;

                    }
                    double amount = Double.parseDouble(args[1]);

                    if (amount < 1.0) {
                        player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.payminimumamount","",""));
                        return false;
                    }
                        String currentmoneySender = mysqlManager.getBalance(player.getUniqueId().toString());
                        String currentmoneyReceiver = mysqlManager.getBalance(selectedPlayer.getUniqueId().toString());
                        currentmoneySender = currentmoneySender.replace(",", "");
                        currentmoneyReceiver = currentmoneyReceiver.replace(",", "");
                        double currentmoneySender3 = Double.parseDouble(currentmoneySender);
                        double currentmoneyReceiver3 = Double.parseDouble(currentmoneyReceiver);
                        if (currentmoneySender3 < amount) {
                            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.paynotenoughmoney","%current_money_sender%", String.valueOf(currentmoneySender3)));
                            return false;
                        }
                        String formattedMoneySender = String.format(Locale.ENGLISH, "%,.2f", currentmoneySender3 - amount);
                        String formattedMoneyReceiver = String.format(Locale.ENGLISH, "%,.2f", currentmoneyReceiver3 + amount);
                        mysqlManager.updateBalance(player.getUniqueId().toString(), formattedMoneySender);
                        mysqlManager.updateBalance(selectedPlayer.getUniqueId().toString(), formattedMoneyReceiver);
                        selectedPlayer.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.pay", "%player%",player.getName(), "%amount%", String.valueOf(amount)));
                        selectedPlayer.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.paynewbalance", "%money_reciever%", formattedMoneyReceiver));

                        scoreboardBuilder.updateMoney(player);
                        scoreboardBuilder.updateMoney(selectedPlayer);

                        String formattedAmount = String.format(Locale.ENGLISH, "%,.2f", amount);
                        player.sendMessage( ConfigManager.getPrefix()+ ConfigManager.getConfigMessage("message.paysend", "%selected_player%",selectedPlayer.getName(),"%amount%", formattedAmount));
                } else {
                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.playernotonline","",""));
                }
            }else
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.payusage","",""));
        }else
            sender.sendMessage(ConfigManager.getConfigMessage("message.noplayer","",""));
        return false;
    }
}
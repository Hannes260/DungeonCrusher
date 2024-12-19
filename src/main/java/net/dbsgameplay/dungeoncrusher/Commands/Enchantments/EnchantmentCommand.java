package net.dbsgameplay.dungeoncrusher.Commands.Enchantments;

import net.dbsgameplay.dungeoncrusher.enums.Enchantments.SwordEnchantments;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EnchantmentCommand implements CommandExecutor {
    MYSQLManager mysqlManager;
    public EnchantmentCommand(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                SwordEnchantments swordEnchantments = new SwordEnchantments(mysqlManager);
                swordEnchantments.openMenu(player);
            }
        }else
            sender.sendMessage(ConfigManager.getConfigMessage("message.noplayer","",""));
        return false;
    }
}

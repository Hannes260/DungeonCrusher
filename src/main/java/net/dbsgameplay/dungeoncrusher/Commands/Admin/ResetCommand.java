package net.dbsgameplay.dungeoncrusher.Commands.Admin;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.Upgrades.SwordCategory;
import net.dbsgameplay.dungeoncrusher.listener.ErfolgeListener;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.ErfolgeBuilders;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ResetCommand implements CommandExecutor {
    private final MYSQLManager mysqlManager;
    private final ScoreboardBuilder scoreboardBuilder;
    private final DungeonCrusher dungeonCrusher;
    public ResetCommand(DungeonCrusher dungeonCrusher,MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
        this.dungeonCrusher = dungeonCrusher;
        this.scoreboardBuilder = new ScoreboardBuilder(dungeonCrusher);
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("dc.command.reset")) {
                if (args.length == 0) {
                    ResetPlayer(player);
                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.reset","",""));
                } else if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        ResetPlayer(target);
                        player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.reset","",""));
                    }else{
                        player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.playernotonline", "", ""));
                    }
                }
            }else {
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.nopermission());
            }
        }else{
            sender.sendMessage(ConfigManager.getConfigMessage("message.noplayer","",""));
        }
        return false;
    }
    private void ResetPlayer(Player player) {
        String playerUUID = player.getUniqueId().toString();
        mysqlManager.updateBalance(playerUUID, "500.00");
        mysqlManager.updateDeaths(playerUUID, 0);
        mysqlManager.updateKills(playerUUID, String.valueOf(0));
        mysqlManager.updateSwordLevel(playerUUID, 1);
        mysqlManager.updateHelmLevel(playerUUID, 1);
        mysqlManager.updateChestplateLevel(playerUUID, 0);
        mysqlManager.updateLeggingsLevel(playerUUID, 0);
        mysqlManager.updateBootsLevel(playerUUID, 0);
        mysqlManager.updateArmorLvl(playerUUID, 1);
        mysqlManager.updateDungeonCount(playerUUID, 1);
        mysqlManager.deletePlayerfromItems(playerUUID);
        mysqlManager.deletePlayerfromMobKills(playerUUID);
        scoreboardBuilder.setup(player);
        player.getInventory().clear();

        SwordCategory swordCategory = new SwordCategory(mysqlManager);
        swordCategory.setPlayerInventoryItems(player);

        ItemStack woodensword = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta woodenmeta = woodensword.getItemMeta();
        woodenmeta.setDisplayName("§7<<§6Holzschwert §7- §aLv.1§7>>");
        woodenmeta.setLore(Collections.singletonList("§94.0 Angriffsschaden"));
        woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        woodenmeta.setUnbreakable(true);
        woodensword.setItemMeta(woodenmeta);

        ItemStack food = new ItemStack(Material.BREAD, 5);

        ItemStack navigator = new ItemStack(Material.PAPER);
        ItemMeta navigatormeta = navigator.getItemMeta();
        navigatormeta.setCustomModelData(200);
        navigatormeta.setDisplayName("§8➡ §9Teleporter §8✖ §7Rechtsklick");
        navigator.setItemMeta(navigatormeta);
        player.getInventory().setItem(1, food);
        player.getInventory().setItem(0, woodensword);
        player.getInventory().setItem(8, navigator);

        ErfolgeListener.removeSuffix(player);
    }
}

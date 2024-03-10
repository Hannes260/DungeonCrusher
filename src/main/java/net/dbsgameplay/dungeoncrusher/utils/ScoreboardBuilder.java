package net.dbsgameplay.dungeoncrusher.utils;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardBuilder implements Listener {

    public final DungeonCrusher dungeonCrusher;
    private MYSQLManager mysqlManager;
    private static MYSQLManager instance;

    public ScoreboardBuilder(DungeonCrusher dungeonCrusher) {
        this.dungeonCrusher = dungeonCrusher;
        this.mysqlManager = MYSQLManager.getInstance(dungeonCrusher.getDataFolder());
    }

    public void setup(Player player) {

        Player selectedPlayer = (Player) player;

        String currentmoney = getPlayerBalanceFromDB(player);


        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = scoreboard.registerNewObjective("abcd", "abcd");
        obj.setDisplayName(ConfigManager.getConfigMessage("message.scoreboardprefix"));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.getScore("§4").setScore(10);
        obj.getScore("§3» Geld").setScore(9);
        obj.getScore("§d").setScore(8);
        obj.getScore("§b").setScore(7);
        obj.getScore("§3» Dungeon").setScore(6);
        obj.getScore("§3").setScore(5);
        obj.getScore("§5").setScore(4);
        obj.getScore("§3» Online").setScore(3);
        obj.getScore(ChatColor.BLACK + "" + ChatColor.WHITE).setScore(2);
        obj.getScore("§1").setScore(1);

        //Teams
        Team onlinecounter = scoreboard.registerNewTeam("onlineCounter");
        onlinecounter.addEntry(ChatColor.BLACK + "" + ChatColor.WHITE);

        if (Bukkit.getOnlinePlayers().size() == 0) {
            onlinecounter.setPrefix(ChatColor.WHITE + "0" + ChatColor.WHITE + "/" + ChatColor.WHITE + Bukkit.getMaxPlayers());
        }else {
            onlinecounter.setPrefix("" + ChatColor.WHITE + Bukkit.getOnlinePlayers().size() + ChatColor.WHITE + "/" + ChatColor.WHITE + Bukkit.getMaxPlayers());
        }

        //Money
        Team money = scoreboard.registerNewTeam("money");
        money.addEntry("§d");
        money.setPrefix("§f" + currentmoney + " €");
        player.setScoreboard(scoreboard);
        //Kills
        Team kills = scoreboard.registerNewTeam("kills");
        kills.addEntry("§3");
        updateKills(player);
        //Items
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        setup(event.getPlayer());
        Bukkit.getScheduler().runTaskLater(dungeonCrusher, new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    updateScoreboard(p);
                }
            }
        }, 60L);
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(dungeonCrusher, new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    updateScoreboard(p);
                }
                player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
            }
        }, 60L);
    }
    public void updateScoreboard(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        if (Bukkit.getOnlinePlayers().size() == 0) {
            scoreboard.getTeam("onlineCounter").setPrefix(ChatColor.WHITE + "0" + ChatColor.WHITE + "/" + ChatColor.WHITE + Bukkit.getMaxPlayers());
        }else {
            scoreboard.getTeam("onlineCounter").setPrefix("" + ChatColor.WHITE + Bukkit.getOnlinePlayers().size() + ChatColor.WHITE + "/" + ChatColor.WHITE + Bukkit.getMaxPlayers());
        }
    }
    public void updateKills(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        String playerUUID = player.getUniqueId().toString();
        String kills = mysqlManager.getKills(playerUUID);

        Team killsTeam = scoreboard.getTeam("kills");
        if (killsTeam == null) {
            killsTeam = scoreboard.registerNewTeam("kills");
        }

        String newPrefix = "§3Kills: " + ChatColor.WHITE + kills;
        killsTeam.setPrefix(newPrefix);
    }

    public String getPlayerBalanceFromDB(Player player) {
        String playerUUID = player.getUniqueId().toString();
        String balance = "0.00"; // Standardwert, falls der Spieler noch nicht in der Datenbank existiert
        balance = String.valueOf(mysqlManager.getBalance(playerUUID));
        return balance;
    }

    public void updateMoney(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        String currentMoneyFormatted = getPlayerBalanceFromDB(player);

        Team moneyTeam = scoreboard.getTeam("money");
        if (moneyTeam == null) {
            moneyTeam = scoreboard.registerNewTeam("money");
        }

        String newPrefix = "§f" + currentMoneyFormatted + "§f€";
        moneyTeam.setPrefix(newPrefix);
    }

}
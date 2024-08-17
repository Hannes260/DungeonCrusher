package net.dbsgameplay.dungeoncrusher.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.MobNameTranslator;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
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

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        String Geld = "%oraxen_geld%";
        Geld = PlaceholderAPI.setPlaceholders(player, Geld);

        String Online = "%oraxen_online%";
        Online = PlaceholderAPI.setPlaceholders(player, Online);

        String Dungeon = "%oraxen_dungeon%";
        Dungeon = PlaceholderAPI.setPlaceholders(player, Dungeon);

        String displayname = "%oraxen_dungeoncrusher%";
        displayname = PlaceholderAPI.setPlaceholders(player, displayname);
        obj.setDisplayName(displayname);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.getScore("§4").setScore(11);
        obj.getScore(Geld).setScore(10);
        obj.getScore("§d").setScore(9);
        obj.getScore("§b").setScore(8);
        obj.getScore(Dungeon).setScore(7);
        obj.getScore("§3").setScore(6);
        obj.getScore("§6").setScore(5);
        obj.getScore("§5").setScore(4);
        obj.getScore(Online).setScore(3);
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
        //Dungeons
        Team dungeonkills = scoreboard.registerNewTeam("dungeonkills");
        dungeonkills.addEntry("§6");
        updateDungeonKills(player);
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
        String Kills = "%oraxen_kills%";
        Kills = PlaceholderAPI.setPlaceholders(player, Kills);
        String newPrefix = Kills + " §f"+ kills;
        killsTeam.setPrefix(newPrefix);
    }
    public void updateDungeonKills(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        String playerUUID = player.getUniqueId().toString();
        LocationConfigManager locationConfigManager = new LocationConfigManager(DungeonCrusher.getInstance());
        // Finde den nächsten nicht freigeschalteten Dungeon
        int currentDungeonCount = mysqlManager.getDungeonCountForPlayer(String.valueOf(player.getUniqueId()));
        int nextDungeonCount = currentDungeonCount + 1;

            int requiredKills = locationConfigManager.getKills(String.valueOf("dungeon"+ nextDungeonCount));
                String previousDungeonMobType = String.valueOf(locationConfigManager.getMobTypesForDungeon(String.valueOf("dungeon" + currentDungeonCount)));
                String germanMobType = MobNameTranslator.translateToGerman(previousDungeonMobType);
                int kills = mysqlManager.getPlayerMobKills(String.valueOf(player.getUniqueId()), germanMobType);
                int finalkills = requiredKills - kills;

                Team dungeonKillsTeam = scoreboard.getTeam("dungeonkills");
                if (dungeonKillsTeam == null) {
                    dungeonKillsTeam = scoreboard.registerNewTeam("dungeonkills");
                }
                String nextDungeon = "%oraxen_next_dungeon%";
                nextDungeon = PlaceholderAPI.setPlaceholders(player, nextDungeon);
                String skull = "%oraxen_skull%";
                skull = PlaceholderAPI.setPlaceholders(player, skull);
                String newPrefix = nextDungeon + " §f" + finalkills + skull;
                dungeonKillsTeam.setPrefix(newPrefix);
    }

    private int extractDungeonNumber(String dungeonName) {
        try {
            return Integer.parseInt(dungeonName.replace("dungeon", ""));
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
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
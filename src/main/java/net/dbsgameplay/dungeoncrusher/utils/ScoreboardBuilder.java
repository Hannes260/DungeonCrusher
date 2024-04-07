package net.dbsgameplay.dungeoncrusher.utils;

import me.clip.placeholderapi.PlaceholderAPI;
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

import java.util.List;
import java.util.UUID;
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

        String Geld = "%img_geld%";
        Geld = PlaceholderAPI.setPlaceholders(player, Geld);

        String Online = "%img_online%";
        Online = PlaceholderAPI.setPlaceholders(player, Online);

        String Dungeon = "%img_dungeon%";
        Dungeon = PlaceholderAPI.setPlaceholders(player, Dungeon);

        String displayname = "%img_dc%";
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
        String Kills = "%img_kills%";
        Kills = PlaceholderAPI.setPlaceholders(player, Kills);
        String newPrefix = Kills + " §f"+ kills;
        killsTeam.setPrefix(newPrefix);
    }
    public void updateDungeonKills(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        String playerUUID = player.getUniqueId().toString();
        LocationConfigManager locationConfigManager = new LocationConfigManager(DungeonCrusher.getPlugin());
        // Finde den nächsten nicht freigeschalteten Dungeon
        String nextUnlockedDungeon = getNextUnlockedDungeon(player.getUniqueId().toString());

        if (nextUnlockedDungeon != null) {
            Integer requiredKills = locationConfigManager.getKills(nextUnlockedDungeon);
            System.out.println(nextUnlockedDungeon);
            if (requiredKills != null) {
                String currentKillsString = mysqlManager.getKills(playerUUID);
                int currentKills = currentKillsString != null ? Integer.parseInt(currentKillsString) : 0;
                int killsNeeded = Math.max(requiredKills - currentKills, 0); // Berechne benötigte Kills für den nächsten Dungeon

                Team dungeonKillsTeam = scoreboard.getTeam("dungeonkills");
                if (dungeonKillsTeam == null) {
                    dungeonKillsTeam = scoreboard.registerNewTeam("dungeonkills");
                }
                String nextDungeon = "%img_next_dungeon%";
                nextDungeon = PlaceholderAPI.setPlaceholders(player, nextDungeon);
                String newPrefix = nextDungeon  + " §f" + killsNeeded + "💀";
                dungeonKillsTeam.setPrefix(newPrefix);
            }
        }
    }
    // LocationConfigManager locationConfigManager = new LocationConfigManager(DungeonCrusher.getPlugin());
    private String getNextUnlockedDungeon(String playerUUID) {
        // Alle Dungeons aus der Konfigurationsdatei abrufen
        LocationConfigManager locationConfigManager = new LocationConfigManager(DungeonCrusher.getPlugin());
        List<String> allDungeons = locationConfigManager.getDungeonsAndSavezones().keySet().stream()
                .filter(name -> name.startsWith("dungeon"))
                .collect(Collectors.toList());

        // Aktuelle Anzahl der Kills des Spielers abrufen
        String currentKillsString = mysqlManager.getKills(playerUUID);
        int currentKills = currentKillsString != null ? Integer.parseInt(currentKillsString) : 0;

        // Variablen initialisieren, um den nächsten nicht freigeschalteten Dungeon zu verfolgen
        String nextDungeon = null;
        int minDifference = Integer.MAX_VALUE;

        // Den nächsten nicht freigeschalteten Dungeon finden
        for (String dungeon : allDungeons) {
            Integer requiredKills = locationConfigManager.getKills(dungeon);
            if (requiredKills != null && currentKills < requiredKills) {
                int difference = requiredKills - currentKills;
                if (difference < minDifference) {
                    minDifference = difference;
                    nextDungeon = dungeon;
                }
            }
        }

        return nextDungeon;
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
    private boolean hasRequiredKills(Player player, String dungeonName) {
        LocationConfigManager locationConfigManager = new LocationConfigManager(DungeonCrusher.getPlugin());
        Integer kills = Integer.parseInt(mysqlManager.getKills(String.valueOf(player.getUniqueId())));
        Integer requiredKills = locationConfigManager.getKills(dungeonName);

        // Wenn requiredKills null ist, setze den Wert auf 0
        if (requiredKills == null) {
            requiredKills = 0;
        }

        return kills >= requiredKills;
    }
}
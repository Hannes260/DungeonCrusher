package net.dbsgameplay.dungeoncrusher.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.MobNameTranslator;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.configs.LocationConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
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

        String Geld = "%oraxen_geld%";
        Geld = PlaceholderAPI.setPlaceholders(player, Geld);

        String Online = "%oraxen_online%";
        Online = PlaceholderAPI.setPlaceholders(player, Online);

        String displayname = "%oraxen_dungeoncrusher%";
        displayname = PlaceholderAPI.setPlaceholders(player, displayname);
        obj.setDisplayName(displayname);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.getScore("§1").setScore(14);
        obj.getScore(Geld).setScore(13);
        obj.getScore("§d").setScore(12);
        obj.getScore("§b").setScore(11);
        obj.getScore("§2").setScore(10);
        obj.getScore("§3").setScore(9);
        obj.getScore("§6").setScore(8);
        obj.getScore("§7").setScore(7);
        obj.getScore("§8").setScore(6);
        obj.getScore("§4").setScore(5);
        obj.getScore("§5").setScore(4);
        obj.getScore(Online).setScore(3);
        obj.getScore(ChatColor.BLACK + "" + ChatColor.WHITE).setScore(2);
        obj.getScore("§9").setScore(1);

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
        Team dungeon = scoreboard.registerNewTeam("dungeon");
        dungeon.addEntry("§2");
        updateDungeon(player);

        //nextdungeonKills
        Team dungeonkills = scoreboard.registerNewTeam("dungeonkills");
        dungeonkills.addEntry("§6");
        updateDungeonKills(player);

        //Swordlevel
        Team swordlevel = scoreboard.registerNewTeam("sword");
        swordlevel.addEntry("§8");
        updateSwordLevel(player);
        //Armorlevel
        Team armorlevel = scoreboard.registerNewTeam("armor");
        armorlevel.addEntry("§4");
        updateArmorLevel(player);

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
    public void updateDungeon(Player player) {
        new BukkitRunnable(){
            @Override
            public void run() {
                Scoreboard scoreboard = player.getScoreboard();

                String playerUUID = String.valueOf(player.getUniqueId());
                String dungeon = String.valueOf(mysqlManager.getDungeonCountForPlayer(playerUUID));

                Team dungeonTeam = scoreboard.getTeam("dungeon");
                if (dungeonTeam == null) {
                    dungeonTeam = scoreboard.registerNewTeam("dungeon");
                }
                String Dungeon = "%oraxen_dungeon%";
                Dungeon = PlaceholderAPI.setPlaceholders(player, Dungeon);
                String newPrefix = Dungeon + " §f"+ dungeon;
                dungeonTeam.setPrefix(newPrefix);
            }
        }.runTaskLater(DungeonCrusher.getInstance(), 40L);

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
        new BukkitRunnable() {
            @Override
            public void run() {
                Scoreboard scoreboard = player.getScoreboard();
                String playerUUID = player.getUniqueId().toString();
                LocationConfigManager locationConfigManager = new LocationConfigManager(DungeonCrusher.getInstance());
                // Finde den nächsten nicht freigeschalteten Dungeon
                int currentDungeonCount = mysqlManager.getDungeonCountForPlayer(String.valueOf(player.getUniqueId()));
                int nextDungeonCount = currentDungeonCount + 1;

                Integer requiredKills = locationConfigManager.getKills("dungeon" + nextDungeonCount);
                if (requiredKills == null) {
                    requiredKills = 0;
                }
                String previousDungeonMobType = String.valueOf(locationConfigManager.getMobTypesForDungeon(String.valueOf("dungeon" + currentDungeonCount)));
                String germanMobType = MobNameTranslator.translateToGerman(previousDungeonMobType);
                int kills = mysqlManager.getPlayerMobKills(String.valueOf(player.getUniqueId()), germanMobType);
                int finalkills = requiredKills - kills;
                if (finalkills < 0) {
                    finalkills = 0;
                }

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
        }.runTaskLater(DungeonCrusher.getInstance(), 20L);
    }
    public void updateSwordLevel(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        String currentSwordLevel = String.valueOf((mysqlManager.getSwordLevel((player.getUniqueId().toString()))));

        Team SwordTeam = scoreboard.getTeam("sword");
        if (SwordTeam == null) {
            SwordTeam = scoreboard.registerNewTeam("sword");
        }
        String Swordlvl = "%oraxen_sword_level%";
        Swordlvl = PlaceholderAPI.setPlaceholders(player, Swordlvl);
        String newPrefix = Swordlvl + " §f" + currentSwordLevel;
        SwordTeam.setPrefix(newPrefix);
    }
    public void updateArmorLevel(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        String currentArmorLevel = String.valueOf((mysqlManager.getArmorLvl(((player.getUniqueId().toString())))));

        Team ArmorTeam = scoreboard.getTeam("armor");
        if (ArmorTeam == null) {
            ArmorTeam = scoreboard.registerNewTeam("armor");
        }
        String Armorlvl = "%oraxen_armor_level%";
        Armorlvl = PlaceholderAPI.setPlaceholders(player, Armorlvl);
        String newPrefix = Armorlvl + " §f" + currentArmorLevel;
        ArmorTeam.setPrefix(newPrefix);
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
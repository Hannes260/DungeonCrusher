package net.dbsgameplay.dungeoncrusher;

import net.dbsgameplay.dungeoncrusher.Commands.*;
import net.dbsgameplay.dungeoncrusher.Commands.Economy.CoinsCommand;
import net.dbsgameplay.dungeoncrusher.Commands.Economy.PayCommand;
import net.dbsgameplay.dungeoncrusher.Commands.Shops.ShopCommand;
import net.dbsgameplay.dungeoncrusher.Commands.Upgrades.UpgradeCommand;
import net.dbsgameplay.dungeoncrusher.listener.*;
import net.dbsgameplay.dungeoncrusher.listener.Damage.*;
import net.dbsgameplay.dungeoncrusher.listener.Navigator.NavigatorListener;
import net.dbsgameplay.dungeoncrusher.listener.Upgrades.UpgradeListener;
import net.dbsgameplay.dungeoncrusher.listener.protections.DungeonProtectionListener;
import net.dbsgameplay.dungeoncrusher.listener.shops.ShopListener;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.*;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.DropsConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.RewardConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Manager.MarkingsManager;
import net.dbsgameplay.dungeoncrusher.utils.Manager.SavezoneManager;
import net.dbsgameplay.dungeoncrusher.utils.shops.ShopManager;
import net.dbsgameplay.dungeoncrusher.utils.upgrades.UpgradeManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class DungeonCrusher extends JavaPlugin {

    private static DungeonCrusher instance;
    private ConfigManager configManager;
    private LocationConfigManager locationConfigManager;
    private DropsConfigManager dropsConfigManager;
    private RewardConfigManager rewardConfigManager;
    private MYSQLManager mysqlManager;
    private MarkingsManager markierungsManager;
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static LuckPerms api;
    @Override
    public void onEnable() {
        instance = this;

        api = LuckPermsProvider.get();

        // Laden der Konfigurationen und Datenbankverbindung
        saveDefaultConfig();
        configManager = new ConfigManager(this);
        locationConfigManager = new LocationConfigManager(this);
        dropsConfigManager = new DropsConfigManager(this);
        rewardConfigManager = new RewardConfigManager(this);
        mysqlManager = MYSQLManager.getInstance(getDataFolder());
        markierungsManager = new MarkingsManager(locationConfigManager);
        ErfolgeBuilders erfolgeBuilders = new ErfolgeBuilders(mysqlManager);

        getLogger().info(ANSI_BLUE +" ");
        getLogger().info(ANSI_BLUE +"  ____   ____ ");
        getLogger().info(ANSI_BLUE +" |  _ \\ / ___|");
        getLogger().info(ANSI_BLUE +" | | | | |        " + ANSI_GREEN + "DungeonCrusher v1.0");
        getLogger().info(ANSI_BLUE +" | |_| | |___     "+ ANSI_WHITE + "Running on Bukkit - Paper");
        getLogger().info(ANSI_BLUE +" |____/ \\____|");
        getLogger().info(ANSI_BLUE +" ");
        getLogger().info(ANSI_WHITE +"Authors: HanneZHD, Ditomax");
        getLogger().info(ANSI_GREEN +" ");

        // Register Commands
        registerCommands();

        // Register Listeners
        registerListeners();

        ShopManager shopManager = new ShopManager(mysqlManager);
        UpgradeManager upgradeManager = new UpgradeManager(mysqlManager);

        ErfolgeMapBuilder.buildErfolgeMap();
    }

    @Override
    public void onDisable() {
        ConfigManager.saveConfig();
        if (mysqlManager != null) {
            mysqlManager.disconnect();
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (cmd.getName().equalsIgnoreCase("config")) {
            if (args.length == 1) {
                completions.addAll(Arrays.asList("reload"));
            }
        } else if (cmd.getName().equalsIgnoreCase("money")) {
            if (args.length == 1) {
                completions.addAll(Arrays.asList("add", "set", "remove", "see"));
            } else if (args.length == 2) {
                completions.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
            } else if (args.length == 3) {
                completions.addAll(Arrays.asList("1", "10", "100", "1000"));
            }
        } else if (cmd.getName().equalsIgnoreCase("pay") && args.length == 2) {
            completions.addAll(Arrays.asList("1", "10", "100", "1000"));
        } else if (cmd.getName().equalsIgnoreCase("setup")) {
            if (args.length == 1) {
                completions.addAll(Arrays.asList("setdungeon", "setsavezone", "removedungeon", "removesavezone", "setspawn", "setkills","setmobcount"));
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("setspawn") || args[0].equalsIgnoreCase("setkills")) {
                    completions.addAll(locationConfigManager.getDungeonsAndSavezones().keySet().stream()
                            .filter(name -> name.startsWith("dungeon"))
                            .sorted(Comparator.comparingInt(this::extractDungeonNumber))
                            .collect(Collectors.toList()));
                }
            }
        }

        return completions;
    }

    private void registerCommands() {
        getCommand("config").setExecutor(new ConfigCommand());
        getCommand("pay").setExecutor(new PayCommand(this, mysqlManager));
        getCommand("money").setExecutor(new CoinsCommand(this, mysqlManager));
        getCommand("stats").setExecutor(new StatsCommand(this, mysqlManager));
        getCommand("setup").setExecutor(new SetupCommand(markierungsManager, locationConfigManager));
        getCommand("build").setExecutor(new BuildCommand(new DungeonProtectionListener()));
        getCommand("setspawn").setExecutor(new SetSpawnCommand(locationConfigManager));
        getCommand("spawn").setExecutor(new SpawnCommand(locationConfigManager, new SavezoneManager(locationConfigManager)));
        getCommand("shop").setExecutor(new ShopCommand(mysqlManager));
        getCommand("upgrades").setExecutor(new UpgradeCommand(mysqlManager));
        getCommand("flyspeed").setExecutor(new FlySpeedCommand());
        getCommand("help").setExecutor(new HelpCommand());
        getCommand("daily").setExecutor(new Dailyreward(this, mysqlManager, rewardConfigManager, locationConfigManager));
        getCommand("cc").setExecutor(new ClearChatCommand());
        getCommand("erfolge").setExecutor(new ErfolgeCommand(this, locationConfigManager));
        getCommand("gm").setExecutor(new Gamemode());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("test").setExecutor(new Testcommand());


        // Tab Completers
        getCommand("config").setTabCompleter(this);
        getCommand("money").setTabCompleter(this);
        getCommand("pay").setTabCompleter(this);
        getCommand("setup").setTabCompleter(this);
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ScoreboardBuilder(this), this);
        pluginManager.registerEvents(new Joinlistener(this, mysqlManager, locationConfigManager), this);
        pluginManager.registerEvents(new DeathListener(this, mysqlManager, locationConfigManager), this);
        pluginManager.registerEvents(new KillListener(this, mysqlManager), this);
        pluginManager.registerEvents(new BlockListener(markierungsManager), this); // Use the singleton instance
        pluginManager.registerEvents(new DungeonProtectionListener(), this);
        pluginManager.registerEvents(new SavezoneListener(new SavezoneManager(locationConfigManager)), this);
        pluginManager.registerEvents(new MobDamageListener(new MobHealthBuilder()), this);
        pluginManager.registerEvents(new CustomDropListener(this, mysqlManager, dropsConfigManager), this);
        pluginManager.registerEvents(new DungeonListener(locationConfigManager), this);
        pluginManager.registerEvents(new MobkillListener(), this);
        pluginManager.registerEvents(new ShopListener(), this);
        pluginManager.registerEvents(new NavigatorListener(this, locationConfigManager, mysqlManager), this);
        pluginManager.registerEvents(new PotionListener(), this);
        pluginManager.registerEvents(new ErfolgeListener(this,locationConfigManager), this);
        pluginManager.registerEvents(new UpgradeListener(), this);
        pluginManager.registerEvents(new ChatListener(), this);
    }

    public void sendToDiscord(String content, int color) {
        try {
            URL url = new URL("https://discord.com/api/webhooks/1270115709258829845/4lnrB58kwejDto1ach5tkG4OmumMZzcdKqb6uWTthA6dv8sm9Uqd_G46spy15BnfgYWd");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("content", "");

            // Embed-Objekt erstellen
            JSONObject embedObject = new JSONObject();
            embedObject.put("title", "DungeonCrusher Notification");
            embedObject.put("description", content);
            embedObject.put("color", color);  // Farbe des Embeds in Dezimal

            // Array von Embeds
            jsonObject.put("embeds", new org.json.JSONArray().put(embedObject));

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("User-Agent", "Java-DiscordWebhook");
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            OutputStream stream = connection.getOutputStream();
            stream.write(jsonObject.toString().getBytes());
            stream.flush();
            stream.close();

            connection.getInputStream().close();
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DungeonCrusher getInstance() {
        return instance;
    }

    public static ConfigManager getConfigManager() {
        return instance.configManager;
    }

    public static DropsConfigManager getDropsManager() {
        return instance.dropsConfigManager;
    }

    public static LocationConfigManager getLocationConfigManager() {
        return instance.locationConfigManager;
    }
    public static MYSQLManager getMysqlManager() {
        return instance.mysqlManager;
    }

    private int extractDungeonNumber(String dungeonName) {
        try {
            return Integer.parseInt(dungeonName.replace("dungeon", ""));
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }
}

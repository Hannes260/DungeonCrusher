package de.hannezhd.dungeoncrusher;

import de.hannezhd.dungeoncrusher.Commands.*;
import de.hannezhd.dungeoncrusher.Commands.Economy.CoinsCommand;
import de.hannezhd.dungeoncrusher.Commands.Economy.PayCommand;
import de.hannezhd.dungeoncrusher.Commands.LevelSystem.SwordUpgradeClickListener;
import de.hannezhd.dungeoncrusher.Commands.LevelSystem.UpgradeCommand;
import de.hannezhd.dungeoncrusher.Commands.Shops.ShopClickListener;
import de.hannezhd.dungeoncrusher.Commands.Shops.ShopCommand;
import de.hannezhd.dungeoncrusher.listener.*;
import de.hannezhd.dungeoncrusher.sql.MYSQLManager;
import de.hannezhd.dungeoncrusher.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DungeonCrusher extends JavaPlugin {
    public static DungeonCrusher plugin;
    private static DungeonCrusher instance;
    private ConfigManager configManager;
    private LocationConfigManager locationconfigManager;
    private MarkierungsManager markierungsManager;
    MobHealthBuilder healthBuilder = new MobHealthBuilder();
    MobDamageListener damageListener = new MobDamageListener(healthBuilder);
    MYSQLManager mysqlManager;
    @Override
    public void onEnable() {
        instance = this;
        this.configManager = new ConfigManager(this);
        mysqlManager = MYSQLManager.getInstance(getDataFolder());
        locationconfigManager = new LocationConfigManager(this);
        MarkierungsManager markierungsManager = new MarkierungsManager(locationconfigManager);
        DungeonProtectionListener dungeonProtectionListener = new DungeonProtectionListener();
        SavezoneManager savezoneManager = new SavezoneManager(locationconfigManager);

        getLogger().info(" ");
        getLogger().info("________                                                    _________                         .__                     \n");
        getLogger().info("\\______ \\   __ __   ____     ____    ____    ____    ____   \\_   ___ \\ _______  __ __   ______|  |__    ____  _______ \n");
        getLogger().info(" |    |  \\ |  |  \\ /    \\   / ___\\ _/ __ \\  /  _ \\  /    \\  /    \\  \\/ \\_  __ \\|  |  \\ /  ___/|  |  \\ _/ __ \\ \\_  __ \\\n");
        getLogger().info(" |    `   \\|  |  /|   |  \\ / /_/  >\\  ___/ (  <_> )|   |  \\ \\     \\____ |  | \\/|  |  / \\___ \\ |   Y  \\\\  ___/  |  | \\/\n");
        getLogger().info("/_______  /|____/ |___|  / \\___  /  \\___  > \\____/ |___|  /  \\______  / |__|   |____/ /____  >|___|  / \\___  > |__|   \n");
        getLogger().info("        \\/             \\/ /_____/       \\/              \\/          \\/                     \\/      \\/      \\/         ");
        getLogger().info(" ");
        getLogger().info("Authors: HanneZHD,DBsGameplay");
        getLogger().info("Version: 1.0");
        getLogger().info(" ");

        this.getCommand("config").setExecutor(new ConfigCommand());
        this.getCommand("pay").setExecutor(new PayCommand(this, mysqlManager));
        this.getCommand("money").setExecutor(new CoinsCommand(this, mysqlManager));
        this.getCommand("stats").setExecutor(new StatsCommand(this,mysqlManager));
        this.getCommand("setup").setExecutor(new SetupCommand(markierungsManager,locationconfigManager));
        this.getCommand("build").setExecutor(new BuildCommand(dungeonProtectionListener));
        this.getCommand("setspawn").setExecutor(new SetSpawnCommand(locationconfigManager));
        this.getCommand("spawn").setExecutor(new SpawnCommand(locationconfigManager,savezoneManager));
        this.getCommand("shop").setExecutor(new ShopCommand(this,mysqlManager));
        this.getCommand("upgrades").setExecutor(new UpgradeCommand(this, mysqlManager));
        this.getCommand("test").setExecutor(new TestComand(this,mysqlManager));
        this.getCommand("customgui").setExecutor(new CustomGUICommand());
        this.getCommand("flyspeed").setExecutor(new FlySpeedCommand());
        //Tab
        this.getCommand("config").setTabCompleter(this);
        this.getCommand("money").setTabCompleter(this);
        this.getCommand("pay").setTabCompleter(this);
        this.getCommand("setup").setTabCompleter(this);

        //Listener
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ScoreboardBuilder(this), this);
        pluginManager.registerEvents(new Joinlistener(this, mysqlManager, locationconfigManager), this);
        pluginManager.registerEvents(new DeathListener(this, mysqlManager),this);
        pluginManager.registerEvents(new KillListener(this, mysqlManager), this);
        pluginManager.registerEvents(new BlockListener(markierungsManager),this);
        pluginManager.registerEvents(new DungeonProtectionListener(),this);
        pluginManager.registerEvents(new SavezoneListener(savezoneManager), this);
        pluginManager.registerEvents(new MobDamageListener(healthBuilder), this);
        pluginManager.registerEvents(new CustomDropListener(this,mysqlManager), this);
        pluginManager.registerEvents(new DungeonListener(locationconfigManager),this);
        pluginManager.registerEvents(new MobkillListener(), this);
        pluginManager.registerEvents(new ShopClickListener(this, mysqlManager),this);
        pluginManager.registerEvents(new SwordUpgradeClickListener(this, mysqlManager),this);
    }
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (cmd.getName().equalsIgnoreCase("config")) {
            Player player = (Player) sender;
            if (args.length == 1) {
                return Arrays.asList("reload");
            }
        } else if (cmd.getName().equalsIgnoreCase("money")) {
            if (args.length == 1) {
                return Arrays.asList("add", "set", "remove", "see");
            } else if (args.length == 2) {
                List<String> playerNames = new ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    playerNames.add(player.getName());
                    playerNames.add("*");
                }
                return playerNames;
            } else if (args.length == 3) {
                return Arrays.asList("1", "10", "100", "1000");
            }
        } else if (cmd.getName().equalsIgnoreCase("pay")) {
            if (args.length == 2) {
                return Arrays.asList("1", "10", "100", "1000");
            }
        } else if (cmd.getName().equalsIgnoreCase("setup")) {
            if (args.length == 1) {
                return Arrays.asList("setdungeon","setsavezone","removedungeon","removesavezone");
            }else if (args.length == 2) {
                return Arrays.asList("name");
            }else if (args.length == 3) {
                return Arrays.asList("name");
            }
        }
        return null;
    }
    public static DungeonCrusher getPlugin() {
        return instance;
    }
    @Override
    public void onDisable() {
            ConfigManager.saveConfig();
        if (mysqlManager != null) {
            mysqlManager.disconnect();
        }
    }
    public static DungeonCrusher getInstance() {
        return instance;
    }
    public static ConfigManager getConfigManager() {
        return plugin.configManager;
    }
}

package net.dbsgameplay.dungeoncrusher;

import net.dbsgameplay.dungeoncrusher.Commands.*;
import net.dbsgameplay.dungeoncrusher.Commands.Admin.*;
import net.dbsgameplay.dungeoncrusher.Commands.Economy.CoinsCommand;
import net.dbsgameplay.dungeoncrusher.Commands.Economy.PayCommand;
import net.dbsgameplay.dungeoncrusher.Commands.Quests.QuestCommand;
import net.dbsgameplay.dungeoncrusher.Commands.Shops.ShopCommand;
import net.dbsgameplay.dungeoncrusher.Commands.Upgrades.UpgradeCommand;
import net.dbsgameplay.dungeoncrusher.enums.MyPlaceholderExpansion;
import net.dbsgameplay.dungeoncrusher.enums.Shop.FoodCategory;
import net.dbsgameplay.dungeoncrusher.listener.*;
import net.dbsgameplay.dungeoncrusher.listener.Damage.*;
import net.dbsgameplay.dungeoncrusher.listener.Navigator.NavigatorListener;
import net.dbsgameplay.dungeoncrusher.listener.Stats.StatsListener;
import net.dbsgameplay.dungeoncrusher.listener.Upgrades.UpgradeListener;
import net.dbsgameplay.dungeoncrusher.listener.protections.DungeonProtectionListener;
import net.dbsgameplay.dungeoncrusher.listener.shops.ShopListener;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.*;
import net.dbsgameplay.dungeoncrusher.utils.Configs.*;
import net.dbsgameplay.dungeoncrusher.utils.Manager.MarkingsManager;
import net.dbsgameplay.dungeoncrusher.utils.Manager.SavezoneManager;
import net.dbsgameplay.dungeoncrusher.utils.Stats.StatsManager;
import net.dbsgameplay.dungeoncrusher.utils.quests.Daily;
import net.dbsgameplay.dungeoncrusher.utils.quests.Monthly;
import net.dbsgameplay.dungeoncrusher.utils.quests.Weekly;
import net.dbsgameplay.dungeoncrusher.utils.shops.ShopManager;
import net.dbsgameplay.dungeoncrusher.utils.upgrades.UpgradeManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.JSONArray;
import org.json.simple.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public final class DungeonCrusher extends JavaPlugin {

    private static DungeonCrusher instance;
    private ConfigManager configManager;
    private LocationConfigManager locationConfigManager;
    private DropsConfigManager dropsConfigManager;
    private RewardConfigManager rewardConfigManager;
    private QuestConfigManager questConfigManager;
    private ErfolgeConfigManager erfolgeConfigManager;
    private MYSQLManager mysqlManager;
    private MarkingsManager markierungsManager;
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static LuckPerms api;
    private final Set<EntityType> mobTypesToKill = new HashSet<>();
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
        questConfigManager = new QuestConfigManager(this);
        erfolgeConfigManager = new ErfolgeConfigManager(this);
        mysqlManager = MYSQLManager.getInstance(getDataFolder());
        markierungsManager = new MarkingsManager(locationConfigManager);
        ErfolgeBuilders erfolgeBuilders = new ErfolgeBuilders(mysqlManager);
        QuestBuilder questBuilder = new QuestBuilder(this, mysqlManager);
        Daily daily = new Daily(mysqlManager, this);
        Weekly weekly = new Weekly(mysqlManager, this);
        Monthly monthly = new Monthly(mysqlManager, this);

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

        new MyPlaceholderExpansion(mysqlManager).register();

        ShopManager shopManager = new ShopManager(mysqlManager);
        UpgradeManager upgradeManager = new UpgradeManager(mysqlManager);
        StatsManager statsManager = new StatsManager(mysqlManager);

        ErfolgeConfigManager.loadMap();
        QuestConfigManager.loadMap();

        Daily.load();
        Weekly.load();
        Monthly.load();

        Daily.checkForOrginQuest();
        Weekly.checkForOrginQuest();
        Monthly.checkForOrginQuest();


        mobTypesToKill.add(EntityType.SHEEP);
        mobTypesToKill.add(EntityType.PIG);
        mobTypesToKill.add(EntityType.COW);
        mobTypesToKill.add(EntityType.HORSE);
        mobTypesToKill.add(EntityType.DONKEY);
        mobTypesToKill.add(EntityType.CAMEL);
        mobTypesToKill.add(EntityType.FROG);
        mobTypesToKill.add(EntityType.GOAT);
        mobTypesToKill.add(EntityType.LLAMA);
        mobTypesToKill.add(EntityType.MOOSHROOM); // Mooshroom
        mobTypesToKill.add(EntityType.MULE);
        mobTypesToKill.add(EntityType.SNIFFER);
        mobTypesToKill.add(EntityType.PANDA);
        mobTypesToKill.add(EntityType.TURTLE);
        mobTypesToKill.add(EntityType.OCELOT);
        mobTypesToKill.add(EntityType.AXOLOTL);
        mobTypesToKill.add(EntityType.FOX);
        mobTypesToKill.add(EntityType.CAT);
        mobTypesToKill.add(EntityType.CHICKEN);
        mobTypesToKill.add(EntityType.VILLAGER);
        mobTypesToKill.add(EntityType.RABBIT);
        mobTypesToKill.add(EntityType.ARMADILLO);
        mobTypesToKill.add(EntityType.SILVERFISH);
        mobTypesToKill.add(EntityType.VINDICATOR);
        mobTypesToKill.add(EntityType.POLAR_BEAR);
        mobTypesToKill.add(EntityType.ZOMBIE_HORSE);
        mobTypesToKill.add(EntityType.WOLF);
        mobTypesToKill.add(EntityType.BREEZE); // Falls dieser Mob existiert
        mobTypesToKill.add(EntityType.ZOMBIE_VILLAGER);
        mobTypesToKill.add(EntityType.SNOW_GOLEM); // Snow Golem
        mobTypesToKill.add(EntityType.SKELETON);
        mobTypesToKill.add(EntityType.DROWNED);
        mobTypesToKill.add(EntityType.HUSK);
        mobTypesToKill.add(EntityType.SPIDER);
        mobTypesToKill.add(EntityType.ZOMBIE);
        mobTypesToKill.add(EntityType.STRAY);
        mobTypesToKill.add(EntityType.CREEPER);
        mobTypesToKill.add(EntityType.CAVE_SPIDER);
        mobTypesToKill.add(EntityType.ENDERMITE);
        mobTypesToKill.add(EntityType.STRIDER);
        mobTypesToKill.add(EntityType.BLAZE);
        mobTypesToKill.add(EntityType.SKELETON_HORSE);
        mobTypesToKill.add(EntityType.WITCH);
        mobTypesToKill.add(EntityType.SLIME);
        mobTypesToKill.add(EntityType.MAGMA_CUBE);
        mobTypesToKill.add(EntityType.ENDERMAN);
        mobTypesToKill.add(EntityType.PIGLIN);
        mobTypesToKill.add(EntityType.ZOMBIFIED_PIGLIN);
        mobTypesToKill.add(EntityType.PIGLIN_BRUTE);
        mobTypesToKill.add(EntityType.PILLAGER);
        mobTypesToKill.add(EntityType.HOGLIN);
        mobTypesToKill.add(EntityType.EVOKER);
        mobTypesToKill.add(EntityType.GHAST);
        mobTypesToKill.add(EntityType.WITHER_SKELETON);
        mobTypesToKill.add(EntityType.ZOGLIN);
        mobTypesToKill.add(EntityType.RAVAGER);
        mobTypesToKill.add(EntityType.IRON_GOLEM);
        mobTypesToKill.add(EntityType.WARDEN);

        startPlaytimer(mysqlManager, getConfig(), this);
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
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("reset").setExecutor(new ResetCommand(this,mysqlManager));
        getCommand("fly").setExecutor(new Flycommand());
        getCommand("quest").setExecutor(new QuestCommand(mysqlManager));
        getCommand("test").setExecutor(new test());
        getCommand("invsee").setExecutor(new InvseeCommand());
        getCommand("dc").setExecutor(new DiscordCommand());

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
        pluginManager.registerEvents(new MobkillListener(mysqlManager, this), this);
        pluginManager.registerEvents(new ShopListener(this, locationConfigManager, mysqlManager), this);
        pluginManager.registerEvents(new NavigatorListener(this, locationConfigManager, mysqlManager), this);
        pluginManager.registerEvents(new PotionListener(), this);
        pluginManager.registerEvents(new ErfolgeListener(this,locationConfigManager), this);
        pluginManager.registerEvents(new UpgradeListener(this,locationConfigManager, mysqlManager), this);
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(new QuestListener(mysqlManager, this), this);
        pluginManager.registerEvents(new StatsListener(this, locationConfigManager, mysqlManager), this);
        pluginManager.registerEvents(new InvseeListener(), this);
        pluginManager.registerEvents(new ChunkListener(this, mobTypesToKill), this);
    }

    public void sendToDiscord(String content, int color) {
        new Thread(() -> {
            try {
                URL url = new URL("https://discord.com/api/webhooks/1270115709258829845/4lnrB58kwejDto1ach5tkG4OmumMZzcdKqb6uWTthA6dv8sm9Uqd_G46spy15BnfgYWd");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("content", content);   // Optionaler Inhalt

                // Embed-Objekt erstellen
                JSONObject embedObject = new JSONObject();
                embedObject.put("color", color);  // Farbe des Embeds in Dezimal

                if (color == 0xFFFF00) { // Wenn die Nachricht als beleidigend erkannt wird
                    embedObject.put("title", "**🚨 Beleidigung erkannt 🚨**"); // Titel
                    embedObject.put("description", content); // Hauptinhalt der Nachricht

                    // Array von Feldern erstellen
                    JSONArray fieldsArray = new JSONArray();
                    JSONObject fieldObject = new JSONObject();
                    fieldObject.put("name", "\u200B"); // Leerer Name für Abstand
                    fieldObject.put("value", "**Diese Nachricht wurde als unangemessen markiert! ⚠️**");
                    fieldObject.put("inline", false);
                    fieldsArray.put(fieldObject);
                    embedObject.put("fields", fieldsArray);
                } else {
                    embedObject.put("title", "DungeonCrusher Notification");
                    embedObject.put("description", content);
                }

                // Array von Embeds
                JSONArray embedsArray = new JSONArray();
                embedsArray.put(embedObject);
                jsonObject.put("embeds", embedsArray);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("User-Agent", "Java-DiscordWebhook");
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                // Write data
                try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {
                    writer.write(jsonObject.toString());
                    writer.flush();
                }

                // Check response code
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    System.out.println("Nachricht erfolgreich gesendet.");
                }

                connection.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start(); // Start new thread
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

    public static void startPlaytimer(MYSQLManager mysqlManager, FileConfiguration cfg, DungeonCrusher dungeonCrusher) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Daily.checkForOrginQuestUpdate();
                Weekly.checkForOrginQuestUpdate();
                Monthly.checkForOrginQuestUpdate();

                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    if (QuestBuilder.isTutorialDone(p)) {
                        FileConfiguration cfg = dungeonCrusher.getConfig();
                        FoodCategory foodCategory = new FoodCategory(mysqlManager);

                        // MySQL-Abfragen asynchron ausführen
                        CompletableFuture.supplyAsync(() -> mysqlManager.getOrginQuest("daily1")).thenAccept(dailyQuest1 -> {
                            CompletableFuture.supplyAsync(() -> mysqlManager.getOrginQuest("daily2")).thenAccept(dailyQuest2 -> {
                                CompletableFuture.supplyAsync(() -> mysqlManager.getOrginQuest("daily3")).thenAccept(dailyQuest3 -> {
                                    CompletableFuture.supplyAsync(() -> mysqlManager.getOrginQuest("weekly1")).thenAccept(weeklyQuest1 -> {
                                        CompletableFuture.supplyAsync(() -> mysqlManager.getOrginQuest("weekly2")).thenAccept(weeklyQuest2 -> {
                                            CompletableFuture.supplyAsync(() -> mysqlManager.getOrginQuest("weekly3")).thenAccept(weeklyQuest3 -> {
                                                CompletableFuture.supplyAsync(() -> mysqlManager.getOrginQuest("monthly1")).thenAccept(monthlyQuest1 -> {
                                                    CompletableFuture.supplyAsync(() -> mysqlManager.getOrginQuest("monthly2")).thenAccept(monthlyQuest2 -> {
                                                        CompletableFuture.supplyAsync(() -> mysqlManager.getOrginQuest("monthly3")).thenAccept(monthlyQuest3 -> {
                                                            // Hier kannst du die Logik nach den Abfragen weiter fortsetzen
                                                            // Daily Quests
                                                            processDailyQuests(p, dailyQuest1, dailyQuest2, dailyQuest3, mysqlManager, foodCategory, dungeonCrusher);
                                                            // Weekly Quests
                                                            processWeeklyQuests(p, weeklyQuest1, weeklyQuest2, weeklyQuest3, mysqlManager, foodCategory, dungeonCrusher);
                                                            // Monthly Quests
                                                            processMonthlyQuests(p, monthlyQuest1, monthlyQuest2, monthlyQuest3, mysqlManager, foodCategory, dungeonCrusher);
                                                        });
                                                    });
                                                });
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    }
                }
            }
        }.runTaskTimer(dungeonCrusher, 0L, 10 * 20L);
    }

    private static void processDailyQuests(Player p, String dailyQuest1, String dailyQuest2, String dailyQuest3, MYSQLManager mysqlManager, FoodCategory foodCategory, DungeonCrusher dungeonCrusher) {
        for (String s : Daily.PlayQuestList.keySet()) {
            if (s.equals(dailyQuest1)) {
                handleDailyQuest(p, 1, dailyQuest1, mysqlManager, foodCategory, dungeonCrusher);
            } else if (s.equals(dailyQuest2)) {
                handleDailyQuest(p, 2, dailyQuest2, mysqlManager, foodCategory, dungeonCrusher);
            } else if (s.equals(dailyQuest3)) {
                handleDailyQuest(p, 3, dailyQuest3, mysqlManager, foodCategory, dungeonCrusher);
            }
        }
    }

    private static void handleDailyQuest(Player p, int questNumber, String quest, MYSQLManager mysqlManager, FoodCategory foodCategory, DungeonCrusher dungeonCrusher) {
        if (!Daily.isDone(questNumber, p)) {
            CompletableFuture.supplyAsync(() -> mysqlManager.getPlayerTempQuest("daily" + questNumber, p.getUniqueId().toString())).thenAccept(tempQuest -> {
                if (tempQuest >= Daily.PlayQuestList.get(quest) * 60) {
                    p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
                    mysqlManager.updatePlayerQuest("daily" + questNumber, true, p.getUniqueId().toString());
                    if (Daily.RewardMoneyList.get(quest) != null) {
                        foodCategory.addMoney(p, Daily.RewardMoneyList.get(quest));
                        p.sendMessage(" §7[§a+§7] §6" + Daily.RewardMoneyList.get(quest) + "€");
                    }
                    p.sendActionBar("§6Du hast eine Quest abgeschlossen hol dir deine Belohnung §d/quest");
                    for (int i = 0; i!= 10; i++) {
                        if (!QuestBuilder.unclaimedQuestRewards.containsKey(p.getUniqueId().toString()+i)) {
                            QuestBuilder.unclaimedQuestRewards.put(p.getUniqueId().toString()+i, Daily.RewardItemList.get(mysqlManager.getOrginQuest("daily" + questNumber)));
                            break;
                        }
                    }
                } else {
                    QuestListener.playtimeMap.replace(p.getUniqueId(), QuestListener.playtimeMap.get(p.getUniqueId()) + 10);
                    mysqlManager.updatePlayerTempQuest("daily" + questNumber, p.getUniqueId().toString(), QuestListener.playtimeMap.get(p.getUniqueId()));
                }
            });
        }
    }

    private static void processWeeklyQuests(Player p, String weeklyQuest1, String weeklyQuest2, String weeklyQuest3, MYSQLManager mysqlManager, FoodCategory foodCategory, DungeonCrusher dungeonCrusher) {
        for (String s : Weekly.PlayQuestList.keySet()) {
            if (s.equals(weeklyQuest1)) {
                handleWeeklyQuest(p, 1, weeklyQuest1, mysqlManager, foodCategory, dungeonCrusher);
            } else if (s.equals(weeklyQuest2)) {
                handleWeeklyQuest(p, 2, weeklyQuest2, mysqlManager, foodCategory, dungeonCrusher);
            } else if (s.equals(weeklyQuest3)) {
                handleWeeklyQuest(p, 3, weeklyQuest3, mysqlManager, foodCategory, dungeonCrusher);
            }
        }
    }

    private static void handleWeeklyQuest(Player p, int questNumber, String quest, MYSQLManager mysqlManager, FoodCategory foodCategory, DungeonCrusher dungeonCrusher) {
        if (!Weekly.isDone(questNumber, p)) {
            CompletableFuture.supplyAsync(() -> mysqlManager.getPlayerTempQuest("weekly" + questNumber, p.getUniqueId().toString())).thenAccept(tempQuest -> {
                if (tempQuest >= Weekly.PlayQuestList.get(quest) * 60) {
                    p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
                    mysqlManager.updatePlayerQuest("weekly" + questNumber, true, p.getUniqueId().toString());
                    if (Weekly.RewardMoneyList.get(quest) != null) {
                        foodCategory.addMoney(p, Weekly.RewardMoneyList.get(quest));
                        p.sendMessage(" §7[§a+§7] §6" + Weekly.RewardMoneyList.get(quest) + "€");
                    }
                    p.sendActionBar("§6Du hast eine Quest abgeschlossen hol dir deine Belohnung §d/quest");
                    for (int i = 0; i!= 10; i++) {
                        if (!QuestBuilder.unclaimedQuestRewards.containsKey(p.getUniqueId().toString()+i)) {
                            QuestBuilder.unclaimedQuestRewards.put(p.getUniqueId().toString()+i, Daily.RewardItemList.get(mysqlManager.getOrginQuest("weekly" + questNumber)));
                            break;
                        }
                    }
                } else {
                    QuestListener.playtimeMap.replace(p.getUniqueId(), QuestListener.playtimeMap.get(p.getUniqueId()) + 10);
                    mysqlManager.updatePlayerTempQuest("weekly" + questNumber, p.getUniqueId().toString(), QuestListener.playtimeMap.get(p.getUniqueId()));
                }
            });
        }
    }

    private static void processMonthlyQuests(Player p, String monthlyQuest1, String monthlyQuest2, String monthlyQuest3, MYSQLManager mysqlManager, FoodCategory foodCategory, DungeonCrusher dungeonCrusher) {
        for (String s : Monthly.PlayQuestList.keySet()) {
            if (s.equals(monthlyQuest1)) {
                handleMonthlyQuest(p, 1, monthlyQuest1, mysqlManager, foodCategory, dungeonCrusher);
            } else if (s.equals(monthlyQuest2)) {
                handleMonthlyQuest(p, 2, monthlyQuest2, mysqlManager, foodCategory, dungeonCrusher);
            } else if (s.equals(monthlyQuest3)) {
                handleMonthlyQuest(p, 3, monthlyQuest3, mysqlManager, foodCategory, dungeonCrusher);
            }
        }
    }

    private static void handleMonthlyQuest(Player p, int questNumber, String quest, MYSQLManager mysqlManager, FoodCategory foodCategory, DungeonCrusher dungeonCrusher) {
        if (!Monthly.isDone(questNumber, p)) {
            CompletableFuture.supplyAsync(() -> mysqlManager.getPlayerTempQuest("monthly" + questNumber, p.getUniqueId().toString())).thenAccept(tempQuest -> {
                if (tempQuest >= Monthly.PlayQuestList.get(quest) * 60) {
                    p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
                    mysqlManager.updatePlayerQuest("monthly" + questNumber, true, p.getUniqueId().toString());
                    if (Monthly.RewardMoneyList.get(quest) != null) {
                        foodCategory.addMoney(p, Monthly.RewardMoneyList.get(quest));
                        p.sendMessage(" §7[§a+§7] §6" + Monthly.RewardMoneyList.get(quest) + "€");
                    }
                    p.sendActionBar("§6Du hast eine Quest abgeschlossen hol dir deine Belohnung §d/quest");
                    for (int i = 0; i!= 10; i++) {
                        if (!QuestBuilder.unclaimedQuestRewards.containsKey(p.getUniqueId().toString()+i)) {
                            QuestBuilder.unclaimedQuestRewards.put(p.getUniqueId().toString()+i, Daily.RewardItemList.get(mysqlManager.getOrginQuest("monthly" + questNumber)));
                            break;
                        }
                    }
                } else {
                    QuestListener.playtimeMap.replace(p.getUniqueId(), QuestListener.playtimeMap.get(p.getUniqueId()) + 10);
                    mysqlManager.updatePlayerTempQuest("monthly" + questNumber, p.getUniqueId().toString(), QuestListener.playtimeMap.get(p.getUniqueId()));
                }
            });
        }
    }
}

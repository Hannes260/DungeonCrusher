package net.dbsgameplay.dungeoncrusher.utils.Configs;


import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private final DungeonCrusher plugin;

    public ConfigManager(DungeonCrusher plugin) {
        this.plugin = plugin;
        initializeConfig();
    }
    private void initializeConfig() {
        FileConfiguration config = this.plugin.getConfig();
        if (!config.isSet("prefix")) {
            config.set("prefix", "&x&f&b&1&b&b&6D&x&f&b&1&e&a&du&x&f&b&2&1&a&4n&x&f&b&2&4&9&bg&x&f&c&2&7&9&2e&x&f&c&2&a&8&9o&x&f&c&2&d&8&0n&x&f&c&2&f&7&7C&x&f&c&3&2&6&er&x&f&c&3&5&6&5u&x&f&d&3&8&5&cs&x&f&d&3&b&5&3h&x&f&d&3&e&4&ae&x&f&d&4&1&4&1r &f● ");
            this.plugin.saveConfig();
        } else {
            setDefaultIfNotSet("message.noplayer", "&cDu musst ein Spieler sein!");
            setDefaultIfNotSet("message.playernotonline", "&cDer Spieler muss Online sein!");
            setDefaultIfNotSet("message.firstjoin", "&bDer Spieler &6%player% &bist das erste mal hier!");
            setDefaultIfNotSet("message.nopermission", "&cDazu hast du keine Rechte!");
            setDefaultIfNotSet("message.configusage", "&cBenutze: /config reload.");
            setDefaultIfNotSet("message.configreload", "&aConfig wurde neu geladen.");
            setDefaultIfNotSet("message.moneyusage", "&cBenutze: /money <set | add | remove> <name> <anzahl>");
            setDefaultIfNotSet("message.noplayerfound", "&cDer Spieler wurde nicht gefunden");
            setDefaultIfNotSet("message.moneyset", "&aDu hast den Kontostand von &6%selected_player% &aauf &6%money% &agesetzt.");
            setDefaultIfNotSet("message.moneysetusage", "&cBenutze /money set <name> <Anzahl>");
            setDefaultIfNotSet("message.moneyadd","&aDas Geld von &6 %selected_player% &awurde um &6%money% &aerhöht.");
            setDefaultIfNotSet("message.moneyaddusage", "&cBenutze: /money add <name> <Anzahl>");
            setDefaultIfNotSet("message.moneyremovenotenoughmoney", "&cDer Spieler hat nicht genug Geld. &aGeld von %selected_player% %current_money%");
            setDefaultIfNotSet("message.moneyremove", "&aDas Geld von &6%selected_player% &awurde um &6%money_remove% &agesenkt.");
            setDefaultIfNotSet("message.moneyremoveusage", "&cBenutze /money remove <name> <Anzahl>");
            setDefaultIfNotSet("message.moneysee", "&aGeld von &e%selected_player% &6%current_money%");
            setDefaultIfNotSet("message.moneyusage", "&cBenutze /money see <Spieler>");
            setDefaultIfNotSet("message.yourmoney", "&aDein Geld &6%current_money%");
            setDefaultIfNotSet("message.selfpay", "&cDu kannst dir nicht selbst Geld geben.");
            setDefaultIfNotSet("message.payminimumamount", "&cDer Mindestbetrag für Überweisungen beträgt 1 €.");
            setDefaultIfNotSet("message.paynotenoughmoney", "&cDu hast nicht genug Geld. Dein aktueller Kontostand ist: §6%current_money_sender%");
            setDefaultIfNotSet("message.pay", "&aDu hast von &6%player% &aerfolgreich &6%amount% &abekommen.");
            setDefaultIfNotSet("message.paynewbalance", "&aDein Neuer Kontostand: &6%money_reciever%");
            setDefaultIfNotSet("message.paysend", "&aDu hast &6%selected_player% &aerfolgreich &6%amount% &aüberwiesen.");
            setDefaultIfNotSet("message.payusage", "&cBenutze /pay <Spieler> <Betrag>");
            setDefaultIfNotSet("message.firtstjoin", "&a[+] &b%player% &6ist zum ersten mal auf dem Server Willkommen!");
            setDefaultIfNotSet("message.statsusage", "&cBenutze /stats");
            setDefaultIfNotSet("message.posmodefailure", "&cMarkierungsmodus ist nicht aktiv. &aVerwende /setup setdungeon zum Aktivieren.");
            setDefaultIfNotSet("message.posmodeoff", "&cMarkierungsmodus deaktiviert.");
            setDefaultIfNotSet("message.setpos", "&aMarkierung gesetzt für &6%name% - pos %pos% : %location%");
            setDefaultIfNotSet("message.posmodeon", "&aDu befindest dich jetzt im Markierungsmodus für Dungeon: &6%name%. &cLinksklick auf Blöcke zum Markieren.");
            setDefaultIfNotSet("message.buildmodeoff", "&cDer Build-Modus wurde deaktiviert.");
            setDefaultIfNotSet("message.buildmodeon", "&aDer Build-Modus wurde aktiviert.");
            setDefaultIfNotSet("message.setspawn", "&aSpawn wurde erfolgreich gesetzt.");
            setDefaultIfNotSet("message.setspawnusage", "&cBenutze: /setspawn");
            setDefaultIfNotSet("message.spawn", "&aDu wurdest zum spawn Teleportiert.");
            setDefaultIfNotSet("message.nospawn", "&cDer Spawn wurde nicht gefunden!");
            setDefaultIfNotSet("message.sapawnusage", "&cBenutze: /spawn");
            setDefaultIfNotSet("message.setupusage", "&cBenutze: /setup setdungeon|setsavezone|removedungeon|removesavezone");
            setDefaultIfNotSet("message.setupremovedungeon","&aDer Dungeon wurde erfolgreich Gelöscht!");
            setDefaultIfNotSet("message.setupremovesavezoneusage", "&cBenutze: /setup removesavezone [name]");
            setDefaultIfNotSet("message.setupremovesavezone","&aDu hast erfolgreich die Savezone gelöscht!");
            setDefaultIfNotSet("message.setupdungeonexists", "&cDieser Dungeon existiert bereits!");
            setDefaultIfNotSet("message.inventoryfull", "&cDein Inventar ist voll!");
            setDefaultIfNotSet("message.shopusage","&cBenutze: /shop");
            setDefaultIfNotSet("message.buyshop","&aDu hast &6%amount% %material% &aerfolgreich gekauft!");
            setDefaultIfNotSet("message.buypotionshop", "&aDu hast &6%amount% %potion% &aerfolgreich gekauft!");
            setDefaultIfNotSet("message.notenoughmoney", "&cDu hast nicht genug Geld für diesen Kauf. Preis: %price%");
            setDefaultIfNotSet("message.additem", "&7[&a+&7] &6%item% &7[&a%amount%x&7]");
            setDefaultIfNotSet("message.addmobkilledmoney","&7[&a+&7] &6%money%€");
            setDefaultIfNotSet("message.setmobtypes", "&aMob Typ erfolgreich erstellt");
            setDefaultIfNotSet("message.dungeonotsetspawnpoint", "&cSpawnpoint wurde nicht gesetzt.");
            setDefaultIfNotSet("message.setspawnusage", "&cDungeon wurde nicht gefunden");
            setDefaultIfNotSet("message.textureurlfail","&cMobType oder Texture URL wurde nicht gefunden für Dungeon: ");
            setDefaultIfNotSet("message.savekills", "&aKills wurden gesetzt auf %kills%.");
            setDefaultIfNotSet("message.savekillsusage", "&cBenutze: /setup setkills (Anzahl).");
            setDefaultIfNotSet("message.requiredkillsforupgrade","&cNicht genügend Kills du benötigst: &6%required_kills%.");
            setDefaultIfNotSet("message.notenoughkills", "&cNicht genügend Kills.");
            setDefaultIfNotSet("message.navigatorheadlore","&aMob: &6%mob_type%");
            setDefaultIfNotSet("message.blockedcommand", "&cDieser Command ist gesperrt");
            setDefaultIfNotSet("message.helpusage", "&cBenutze: /help");
            setDefaultIfNotSet("message.already_claimed_reward","&cDu hast schon deine Tägliche Belohnung abgeholt");
            setDefaultIfNotSet("message.claimed_daily_reward", "&aDu hast deine Tägliche Belohnung abgeholt");
            setDefaultIfNotSet("message.canclaimdailyreward", "&aDu kannst deine Tägliche Belohnung abholen &7[&6/daily&7]");
            setDefaultIfNotSet("message.clearchatbypass", "&aDer Chat wurde gelöscht von &6%player% &aaber du hast einen Bypass!");
            setDefaultIfNotSet("message.clearchat","&aDer Chat wurde gelöscht von &6%player%");
            setDefaultIfNotSet("message.setmobcountusage", "&cBenutze: /setup setmobcount <dungeonname> <count>");
            setDefaultIfNotSet("message.exchanged", "&aErfolgreich getauscht: %item_name%");
            setDefaultIfNotSet("message.exchangedfailed", "§cNicht genügend Rohmaterialien. Benötigt: %required_amount% %required_item%");
            setDefaultIfNotSet("message.changedcoal", "§adu hast 100 Kohle für 100€ getauscht");
            setDefaultIfNotSet("message.notenoughcoaltochange", "§cDu hast nicht genügend Kohle!");
            setDefaultIfNotSet("message.changedmoneytocoal", "§aDu hast 100 Kohle für 100€ gekauft.");
            setDefaultIfNotSet("message.notenoughmoneyforchangecoal", "§cDu hast nicht genügend geld um kohle zu Tauschen");
            setDefaultIfNotSet("message.erfolgeusage", "§cUsage§7: /erfolge");
            setDefaultIfNotSet("message.upgradesword", "§6Dein Schwert wurde erfolgreich auf Level %currentLevel% aufgewertet.");
            setDefaultIfNotSet("message.notenoughmoneyforupgrade", "§cNicht genügend Geld für das Upgrade!");
            setDefaultIfNotSet("message.notenoughmaterialforupgrade", "§cNicht genügend %materialtypes% für das Upgrade!");
            setDefaultIfNotSet("message.notenoughmaterialsforupgrade", "§cNicht genügend Materialien für das Upgrade!");
            setDefaultIfNotSet("message.upgradesuccesfull", "§aUpgrade erfolgreich durchgeführt!");
            setDefaultIfNotSet("message.setsurvial", "§aDu wurdest in den Survival Gesetzt!");
            setDefaultIfNotSet("message.setanothersurvival", "§aDu hast §e %target_player% §ain den Survival gesetzt");
            setDefaultIfNotSet("message.healed", "§aDu wurdest geheilt");
            setDefaultIfNotSet("message.healedotherplayer", "§aDu hast erfolgreich §6%player% §ageheilt");
            setDefaultIfNotSet("message.healusage","§cVerwende: /heal <spieler>");
            setDefaultIfNotSet("message.addhealth","§aDu hast zusätzliche Lebenspunkte erhalten!");
            setDefaultIfNotSet("message.upgradearmor", "§6Deine Rüstung wurde erfolgreich auf Level %currentLevel% aufgewertet.");
            setDefaultIfNotSet("message.scoreboardprefix", "&x&f&b&1&b&b&6D&x&f&b&1&e&a&du&x&f&b&2&1&a&4n&x&f&b&2&4&9&bg&x&f&c&2&7&9&2e&x&f&c&2&a&8&9o&x&f&c&2&d&8&0n&x&f&c&2&f&7&7C&x&f&c&3&2&6&er&x&f&c&3&5&6&5u&x&f&d&3&8&5&cs&x&f&d&3&b&5&3h&x&f&d&3&e&4&ae&x&f&d&4&1&4&1r");
            config.options().copyDefaults(true);
            this.plugin.saveConfig();
        }
    }
    public static void saveConfig() {
        DungeonCrusher instance = DungeonCrusher.getInstance();
        if (instance != null) {
            instance.saveConfig();
        } else {
            // Hier behandeln, wenn DungeonCrusher.getInstance() null ist
            // Zum Beispiel durch Logging oder Fehlermeldung
            System.out.println("DungeonCrusher.getInstance() ist null!");
        }
    }
    public static void reloadConfig() {
        DungeonCrusher.getInstance().reloadConfig();
    }
    public FileConfiguration getConfig(){
        return this.plugin.getConfig();
    }
    private void setDefaultIfNotSet(String path, String defaultValue) {
        FileConfiguration config = this.plugin.getConfig();
        if (!config.isSet(path)) {
            config.set(path, defaultValue);
        }
        config.options().copyDefaults(true);
    }
    public static String getPrefix() {
        DungeonCrusher pluginInstance = DungeonCrusher.getInstance();
        if (pluginInstance != null && pluginInstance.getConfig() != null) {
            String prefix = pluginInstance.getConfig().getString("prefix");
            prefix = ChatColor.translateAlternateColorCodes('&', prefix);
            return prefix;
        } else {
            // Handle den Fall, wenn das Plugin nicht initialisiert wurde
            return "Plugin nicht initialisiert";
        }
    }
    public static String nopermission() { //"§cDazu hast du keine Rechte"
        DungeonCrusher pluginInstance = DungeonCrusher.getInstance();
        if (pluginInstance != null && pluginInstance.getConfig() != null) {
        String nopermission = pluginInstance.getConfig().getString("message.nopermission");
        nopermission = ChatColor.translateAlternateColorCodes('&', nopermission);
        return nopermission;
        } else {
        return "Plugin nicht initialisiert";
        }
    }
    public static String getConfigMessage(String path, String... replacements) {
        if (replacements.length % 2 != 0) {
            throw new IllegalArgumentException("Anzahl der Platzhalter und Ersetzungen stimmen nicht überein.");
        }

        String message = DungeonCrusher.getInstance().getConfig().getString(path);

        if (message != null) {
            message = ChatColor.translateAlternateColorCodes('&', message);

            for (int i = 0; i < replacements.length; i += 2) {
                message = message.replace(replacements[i], replacements[i + 1]);
            }

            return message;
        } else {
            // Handle den Fall, wenn die Nachricht null ist
            return "Keine Nachricht gefunden";
        }
    }
}

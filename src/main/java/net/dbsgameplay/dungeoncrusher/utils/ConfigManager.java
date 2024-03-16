package net.dbsgameplay.dungeoncrusher.utils;


import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

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
            setDefaultIfNotSet("message.notenoughmoney", "&cDu hast nicht genug Geld für diesen Kauf. Preis: %price%");
            setDefaultIfNotSet("message.additem", "&7[&a+&7] &6%item%");
            setDefaultIfNotSet("message.addmobkilledmoney","&7[&a+&7] &6%money%€");
            setDefaultIfNotSet("message.upgradeswordfailure", "&cUpgrade für dieses Level nicht verfügbar.");
            setDefaultIfNotSet("message.upgradesword", "&aSchwert erfolgreich aufgerüstet");
            setDefaultIfNotSet("message.upgradehelm", "&aHelm erfolgreich aufgerüstet");
            setDefaultIfNotSet("message.notenoughtmoneyupgrade","&cDu hast nicht genügend Geld für das Upgrade!");
            setDefaultIfNotSet("message.notenoughtitemsupgrade","&cDu hast nicht genügend Ressourcen für das Upgrade!");
            setDefaultIfNotSet("message.maxlevel", "&6Du hast das Maximale Level erreicht!");
            setDefaultIfNotSet("message.upgradefirstchestplate", "&6Level als nächstes deine Brustplatte.");
            setDefaultIfNotSet("message.upgradefirstleggings", "&6Level als nächstes deine Hose");
            setDefaultIfNotSet("message.upgradefirsthelmet", "&6Level als nächstes deinen Helm");
            setDefaultIfNotSet("message.scoreboardprefix", "&x&f&b&1&b&b&6D&x&f&b&1&e&a&du&x&f&b&2&1&a&4n&x&f&b&2&4&9&bg&x&f&c&2&7&9&2e&x&f&c&2&a&8&9o&x&f&c&2&d&8&0n&x&f&c&2&f&7&7C&x&f&c&3&2&6&er&x&f&c&3&5&6&5u&x&f&d&3&8&5&cs&x&f&d&3&b&5&3h&x&f&d&3&e&4&ae&x&f&d&4&1&4&1r");
            config.options().copyDefaults(true);
            this.plugin.saveConfig();
        }
    }
    public static void saveConfig() {
        DungeonCrusher.getInstance().saveConfig();
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
        DungeonCrusher pluginInstance = DungeonCrusher.getPlugin();
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
        DungeonCrusher pluginInstance = DungeonCrusher.getPlugin();
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

        String message = DungeonCrusher.getPlugin().getConfig().getString(path);

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

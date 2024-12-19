package net.dbsgameplay.dungeoncrusher.enums.Stats;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.interfaces.StatsCategory;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.configs.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.stats.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.Collections;

public class GeneralStatsCategory implements StatsCategory {
    private final MYSQLManager mysqlManager;

    public GeneralStatsCategory(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
    }

    @Override
    public void openMenu(Player player) {
        String DisplayName = "§f<shift:-8>%oraxen_general_stats_gui%";
        DisplayName = PlaceholderAPI.setPlaceholders(player, DisplayName);

        Inventory inv = Bukkit.createInventory(null, 9 * 6, DisplayName);
        ItemStack playTimeItem = new ItemStack(Material.PAPER);
        ItemMeta playTimeMeta = playTimeItem.getItemMeta();
        playTimeMeta.setDisplayName("§7➢ Spielzeit");
        int totalPlayTimeInTicks = player.getStatistic(Statistic.PLAY_ONE_MINUTE);

// Konvertiere die Ticks in Sekunden
        int totalPlayTimeInSeconds = totalPlayTimeInTicks / 20;

// Berechne die verschiedenen Einheiten
        int seconds = totalPlayTimeInSeconds % 60;
        int minutes = (totalPlayTimeInSeconds / 60) % 60;
        int hours = (totalPlayTimeInSeconds / 3600) % 24;
        int days = totalPlayTimeInSeconds / 86400;

// Erstelle die Lore mit den verschiedenen Einheiten
        String lore = String.format("§aSpielzeit: %d Tage, %d Stunden, %d Minuten, %d Sekunden", days, hours, minutes, seconds);

// Setze die Lore für das Item
        playTimeMeta.setLore(Collections.singletonList(lore));
        playTimeMeta.setCustomModelData(100);
        playTimeItem.setItemMeta(playTimeMeta);

        int[] playtimeslots = {11,12,20,21};
        for (int slot : playtimeslots) {
            inv.setItem(slot, playTimeItem);
        }
        ItemStack damageItem = new ItemStack(Material.PAPER);
        ItemMeta damageMeta = damageItem.getItemMeta();
        damageMeta.setDisplayName("§7➢ Ausgerichteter Schaden");
        damageMeta.setLore(Collections.singletonList("§a" + (player.getStatistic(Statistic.DAMAGE_DEALT) + " Schaden")));
        damageMeta.setCustomModelData(100);
        damageItem.setItemMeta(damageMeta);

        int[] damageslots = {13,14,22,22};
        for (int slot : damageslots) {
            inv.setItem(slot, damageItem);
        }
        ItemStack takeddamageItem = new ItemStack(Material.PAPER);
        ItemMeta takeddamageMeta = takeddamageItem.getItemMeta();
        takeddamageMeta.setDisplayName("§7➢ Erlittener Schaden");
        takeddamageMeta.setLore(Collections.singletonList("§a" + (player.getStatistic(Statistic.DAMAGE_TAKEN) + " Erlitten")));
        takeddamageMeta.setCustomModelData(100);
        takeddamageItem.setItemMeta(takeddamageMeta);
        int[] takedamageslots = {15,16,24,25};
        for (int slot : takedamageslots) {
            inv.setItem(slot, takeddamageItem);
        }

        ItemStack deathItem = new ItemStack(Material.PAPER);
        ItemMeta deatItemMeta = deathItem.getItemMeta();
        deatItemMeta.setDisplayName("§7➢ Tode");
        deatItemMeta.setLore(Collections.singletonList("§a" + (mysqlManager.getDeaths(player.getUniqueId().toString())) + " Tode"));
        deatItemMeta.setCustomModelData(100);
        deathItem.setItemMeta(deatItemMeta);
        int[] deathslots = {29,30,38,39};
        for (int slot : deathslots) {
            inv.setItem(slot, deathItem);
        }

        ItemStack swordlvl = new ItemStack(Material.PAPER);
        ItemMeta swordlvlMeta = swordlvl.getItemMeta();
        swordlvlMeta.setDisplayName("§7➢ Schwert Level");
        swordlvlMeta.setLore(Collections.singletonList("§a" +String.valueOf(mysqlManager.getSwordLevel(player.getUniqueId().toString())) + " Level"));
        swordlvlMeta.setCustomModelData(100);
        swordlvl.setItemMeta(swordlvlMeta);
        int[] swordlvlslots = {31,32,40,41};
        for (int slot : swordlvlslots) {
            inv.setItem(slot, swordlvl);
        }
        ItemStack armorlvl = new ItemStack(Material.PAPER);
        ItemMeta armorlvlMeta = armorlvl.getItemMeta();
        armorlvlMeta.setDisplayName("§7➢ Rüstungs Level");
        armorlvlMeta.setLore(Collections.singletonList("§a" +String.valueOf(mysqlManager.getArmorLvl(player.getUniqueId().toString())) + " Level"));
        armorlvlMeta.setCustomModelData(100);
        armorlvl.setItemMeta(armorlvlMeta);
        int[] armorslots = {33,34,42,43};
        for (int slot : armorslots) {
            inv.setItem(slot, armorlvl);
        }

        ItemStack searchItem = new ItemStack(Material.PAPER);
        ItemMeta searchMeta = searchItem.getItemMeta();
        searchMeta.setDisplayName("§7➢ Spieler suchen");
        searchMeta.setCustomModelData(206);
        searchItem.setItemMeta(searchMeta);
        inv.setItem(53, searchItem);

        ItemStack backhead = new ItemStack(Material.PAPER);
        ItemMeta headmeta = backhead.getItemMeta();
        headmeta.setDisplayName("§7➢ Zurück");
        headmeta.setCustomModelData(100);
        backhead.setItemMeta(headmeta);
        inv.setItem(45, backhead);
        player.openInventory(inv);
    }
    public void openSecondMenu(Player player, String founduuid) {
        String DisplayName = "§f<shift:-8>%oraxen_general_stats_gui%";
        DisplayName = PlaceholderAPI.setPlaceholders(player, DisplayName);

        Inventory inv = Bukkit.createInventory(null, 9 * 6, DisplayName);
        ItemStack playTimeItem = new ItemStack(Material.PAPER);
        ItemMeta playTimeMeta = playTimeItem.getItemMeta();
        playTimeMeta.setDisplayName("§7➢ Spielzeit");
        int totalPlayTimeInSeconds = mysqlManager.getPlayTime(founduuid);

// Berechne die verschiedenen Einheiten
        int seconds = totalPlayTimeInSeconds % 60;
        int minutes = (totalPlayTimeInSeconds / 60) % 60;
        int hours = (totalPlayTimeInSeconds / 3600) % 24;
        int days = totalPlayTimeInSeconds / 86400;

// Erstelle die Lore mit den verschiedenen Einheiten
        String lore = String.format("§aSpielzeit: %d Tage, %d Stunden, %d Minuten, %d Sekunden", days, hours, minutes, seconds);

// Setze die Lore für das Item
        playTimeMeta.setLore(Collections.singletonList(lore));
        playTimeMeta.setCustomModelData(100);
        playTimeItem.setItemMeta(playTimeMeta);

        int[] playtimeslots = {11,12,20,21};
        for (int slot : playtimeslots) {
            inv.setItem(slot, playTimeItem);
        }
        ItemStack damageItem = new ItemStack(Material.PAPER);
        ItemMeta damageMeta = damageItem.getItemMeta();
        damageMeta.setDisplayName("§7➢ Ausgerichteter Schaden");
        damageMeta.setLore(Collections.singletonList("§a" + mysqlManager.getDamageDealt(founduuid) + " Schaden"));
        damageMeta.setCustomModelData(100);
        damageItem.setItemMeta(damageMeta);

        int[] damageslots = {13,14,22,22};
        for (int slot : damageslots) {
            inv.setItem(slot, damageItem);
        }
        ItemStack takeddamageItem = new ItemStack(Material.PAPER);
        ItemMeta takeddamageMeta = takeddamageItem.getItemMeta();
        takeddamageMeta.setDisplayName("§7➢ Erlittener Schaden");
        takeddamageMeta.setLore(Collections.singletonList("§a" + mysqlManager.getDamageAbsorbed(founduuid) + " Erlitten"));
        takeddamageMeta.setCustomModelData(100);
        takeddamageItem.setItemMeta(takeddamageMeta);
        int[] takedamageslots = {15,16,24,25};
        for (int slot : takedamageslots) {
            inv.setItem(slot, takeddamageItem);
        }

        ItemStack deathItem = new ItemStack(Material.PAPER);
        ItemMeta deatItemMeta = deathItem.getItemMeta();
        deatItemMeta.setDisplayName("§7➢ Tode");
        deatItemMeta.setLore(Collections.singletonList("§a" + (mysqlManager.getDeaths(founduuid)) + " Tode"));
        deatItemMeta.setCustomModelData(100);
        deathItem.setItemMeta(deatItemMeta);
        int[] deathslots = {29,30,38,39};
        for (int slot : deathslots) {
            inv.setItem(slot, deathItem);
        }

        ItemStack swordlvl = new ItemStack(Material.PAPER);
        ItemMeta swordlvlMeta = swordlvl.getItemMeta();
        swordlvlMeta.setDisplayName("§7➢ Schwert Level");
        swordlvlMeta.setLore(Collections.singletonList("§a" + (mysqlManager.getSwordLevel(founduuid)) + " Level"));
        swordlvlMeta.setCustomModelData(100);
        swordlvl.setItemMeta(swordlvlMeta);
        int[] swordlvlslots = {31,32,40,41};
        for (int slot : swordlvlslots) {
            inv.setItem(slot, swordlvl);
        }
        ItemStack armorlvl = new ItemStack(Material.PAPER);
        ItemMeta armorlvlMeta = armorlvl.getItemMeta();
        armorlvlMeta.setDisplayName("§7➢ Rüstungs Level");
        armorlvlMeta.setLore(Collections.singletonList("§a" + (mysqlManager.getArmorLvl(founduuid)) + " Level"));
        armorlvlMeta.setCustomModelData(100);
        armorlvl.setItemMeta(armorlvlMeta);
        int[] armorslots = {33,34,42,43};
        for (int slot : armorslots) {
            inv.setItem(slot, armorlvl);
        }

        ItemStack backhead = new ItemStack(Material.PAPER);
        ItemMeta headmeta = backhead.getItemMeta();
        headmeta.setDisplayName("§7➢ Zurück");
        headmeta.setCustomModelData(100);
        backhead.setItemMeta(headmeta);
        inv.setItem(45, backhead);
        player.openInventory(inv);
    }
    @Override
    public void handleItemClick(Player player, ItemStack clickedItem) {
        ItemMeta clickedMeta = clickedItem.getItemMeta();
        if (clickedMeta == null) {
            return;
        }
        if ("§7➢ Spieler suchen".equals(clickedItem.getItemMeta().getDisplayName())) {
            player.closeInventory();
            player.sendMessage(ConfigManager.getPrefix() + "§aGib den Namen des Spielers ein, den du suchen möchtest:");

            DungeonCrusher.getInstance().getServer().getPluginManager().registerEvents(new Listener() {
                @EventHandler
                public void onChat(AsyncPlayerChatEvent chatEvent) {
                    if (chatEvent.getPlayer().equals(player)) {
                        chatEvent.setCancelled(true);
                        String searchQuery = chatEvent.getMessage();

                        // Hier rufen wir die UUID aus der MySQL-Datenbank ab
                        String foundPlayerUUID = mysqlManager.getPlayerUUIDByName(searchQuery);

                        if (foundPlayerUUID == null) {
                            player.sendMessage(ConfigManager.getPrefix() + "§cKein Spieler mit diesem Namen gefunden.");
                        } else {
                            if (player.getUniqueId().toString().equals(foundPlayerUUID)) {
                                player.sendMessage(ConfigManager.getPrefix() + "§cDu kannst dich nicht Selbst suchen!");
                            } else {
                                // Verwende BukkitRunnable, um das Öffnen des Menüs synchron durchzuführen
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        String playerfoundName = mysqlManager.getPlayerNameByUUID(foundPlayerUUID);
                                        openSecondMenu(player, foundPlayerUUID);
                                    }
                                }.runTask(DungeonCrusher.getInstance()); // `plugin` ist eine Instanz deiner Haupt-Plugin-Klasse
                            }
                        }
                        // Deregistriere den Listener nach der Eingabe
                        AsyncPlayerChatEvent.getHandlerList().unregister(this);
                    }
                }
            }, DungeonCrusher.getInstance());

        } else {
            String clickedDisplayName = clickedMeta.getDisplayName();
            if ("§7➢ Zurück".equals(clickedDisplayName)) {
                StatsManager.openMainShopMenu(player);
            }
        }
    }
}

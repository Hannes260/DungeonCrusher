package net.dbsgameplay.dungeoncrusher.enums.Stats;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.Commands.interfaces.StatsCategory;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.MobNameTranslator;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Stats.StatsManager;
import net.dbsgameplay.dungeoncrusher.utils.shops.ShopManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class KillsCategory implements StatsCategory {
    final Map<String,Integer> mobTextures = new HashMap<>();
    private final MYSQLManager mysqlManager;;
    public KillsCategory(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;

        mobTextures.put("sheep", 101);
        mobTextures.put("pig", 102);
        mobTextures.put("cow", 103);
        mobTextures.put("horse", 104);
        mobTextures.put("donkey", 105);
        mobTextures.put("camel", 106);
        mobTextures.put("frog", 107);
        mobTextures.put("goat", 108);
        mobTextures.put("llama", 109);
        mobTextures.put("mooshroom", 110);
        mobTextures.put("mule", 111);
        mobTextures.put("sniffer", 112);
        mobTextures.put("panda", 113);
        mobTextures.put("turtle", 114);
        mobTextures.put("ocelot", 115);
        mobTextures.put("axolotl", 116);
        mobTextures.put("fox", 117);
        mobTextures.put("cat", 118);
        mobTextures.put("chicken", 119);
        mobTextures.put("villager", 120);
        mobTextures.put("rabbit", 121);
        mobTextures.put("armadillo", 122);
        mobTextures.put("silverfish", 123);
        mobTextures.put("vindicator", 124);
        mobTextures.put("polar_bear", 125);
        mobTextures.put("zombie_horse", 126);
        mobTextures.put("wolf", 127);
        mobTextures.put("breeze", 128);
        mobTextures.put("zombie_villager", 129);
        mobTextures.put("snow_golem", 130);
        mobTextures.put("skeleton", 131);
        mobTextures.put("drowned", 132);
        mobTextures.put("husk", 133);
        mobTextures.put("spider", 134);
        mobTextures.put("zombie", 135);
        mobTextures.put("stray", 136);
        mobTextures.put("creeper", 137);
        mobTextures.put("cave_spider", 138);
        mobTextures.put("endermite", 139);
        mobTextures.put("strider", 140);
        mobTextures.put("blaze", 141);
        mobTextures.put("skeleton_horse", 142);
        mobTextures.put("witch", 143);
        mobTextures.put("slime", 144);
        mobTextures.put("magma_cube", 145);
        mobTextures.put("bogged", 146);
        mobTextures.put("enderman", 147);
        mobTextures.put("piglin", 148);
        mobTextures.put("zombified_piglin", 149);
        mobTextures.put("piglin_brutte", 150);
        mobTextures.put("pillager", 151);
        mobTextures.put("hoglin", 152);
        mobTextures.put("evoker", 153);
        mobTextures.put("ghast", 154);
        mobTextures.put("wither_skeleton", 155);
        mobTextures.put("zoglin", 156);
        mobTextures.put("ravager", 157);
        mobTextures.put("iron_golem", 158);
        mobTextures.put("warden", 159);

    }
    @Override
    public void openMenu(Player player) {
        openMenu(player, 1);
    }
    public void openMenu(Player player, int page) {
        String displayNameKills = "§f<shift:-8>%oraxen_kills_gui%";
        displayNameKills = PlaceholderAPI.setPlaceholders(player, displayNameKills);

        Inventory inv = Bukkit.createInventory(null, 9 * 6, displayNameKills);
        LocationConfigManager locationConfigManager = new LocationConfigManager(DungeonCrusher.getInstance());

        Map<String, List<String>> dungeonsAndSavezones = locationConfigManager.getDungeonsAndSavezones();
        List<String> sortedDungeonNames = dungeonsAndSavezones.keySet().stream()
                .filter(name -> name.startsWith("dungeon"))
                .sorted(Comparator.comparingInt(this::extractDungeonNumber))
                .collect(Collectors.toList());

        int totalItems = sortedDungeonNames.size();
        int itemsPerPage = 45; // 44 Mobs pro Seite, 1 für "Nächste Seite"
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

        int startIndex = (page - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

        // Generiere die Mob-Köpfe mit den entsprechenden Texturen und Lore
        for (int i = startIndex; i < endIndex; i++) {
            String dungeonName = sortedDungeonNames.get(i);
            String mobType = getMobTypeForDungeon(dungeonName);
            if (mobType != null && mobTextures.containsKey(mobType)) {
                    Integer customModelData = mobTextures.get(mobType);
                    ItemStack mobHead = createCustomMobHead(dungeonName, customModelData);
                    if (mobHead != null) {
                        ItemMeta meta = mobHead.getItemMeta();
                        if (meta != null) {
                            String germanMobType = MobNameTranslator.translateToGerman(mobType);
                            meta.setDisplayName("§6" + dungeonName);
                            int kills = mysqlManager.getPlayerMobKills(player.getUniqueId().toString(), germanMobType);
                            meta.setLore(Collections.singletonList("§aDeine Kills: §6" + String.valueOf(kills)));
                            mobHead.setItemMeta(meta);
                            inv.setItem(i - startIndex, mobHead);
                        }
                }
            }
        }
        // "Nächste Seite"-Button
        if (page < totalPages) {
            ItemStack nextPageButton = new ItemStack(Material.ARROW);
            ItemMeta nextPageMeta = nextPageButton.getItemMeta();
            nextPageMeta.setDisplayName("§aNächste Seite");
            nextPageButton.setItemMeta(nextPageMeta);
            inv.setItem(44, nextPageButton);
        }

        // "Vorherige Seite"-Button
        if (page > 1) {
            ItemStack previousPageButton = new ItemStack(Material.ARROW);
            ItemMeta previousPageMeta = previousPageButton.getItemMeta();
            previousPageMeta.setDisplayName("§cVorherige Seite");
            previousPageButton.setItemMeta(previousPageMeta);
            inv.setItem(44, previousPageButton);
        }
        ItemStack searchItem = new ItemStack(Material.COMPASS);
        ItemMeta searchMeta = searchItem.getItemMeta();
        searchMeta.setDisplayName("§7➢ Spieler suchen");
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
    public void openSecondMenu(Player player, int page, String founduuid) {
        String displayNameKills = "§f<shift:-8>%oraxen_kills_gui%";
        displayNameKills = PlaceholderAPI.setPlaceholders(player, displayNameKills);

        Inventory inv = Bukkit.createInventory(null, 9 * 6, displayNameKills);
        LocationConfigManager locationConfigManager = new LocationConfigManager(DungeonCrusher.getInstance());

        Map<String, List<String>> dungeonsAndSavezones = locationConfigManager.getDungeonsAndSavezones();
        List<String> sortedDungeonNames = dungeonsAndSavezones.keySet().stream()
                .filter(name -> name.startsWith("dungeon"))
                .sorted(Comparator.comparingInt(this::extractDungeonNumber))
                .collect(Collectors.toList());

        int totalItems = sortedDungeonNames.size();
        int itemsPerPage = 45; // 44 Mobs pro Seite, 1 für "Nächste Seite"
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

        int startIndex = (page - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

        // Generiere die Mob-Köpfe mit den entsprechenden Texturen und Lore
        for (int i = startIndex; i < endIndex; i++) {
            String dungeonName = sortedDungeonNames.get(i);
            String mobType = getMobTypeForDungeon(dungeonName);
            if (mobType != null && mobTextures.containsKey(mobType)) {
                Integer customModelData = mobTextures.get(mobType);
                ItemStack mobHead = createCustomMobHead(dungeonName, customModelData);
                if (mobHead != null) {
                    ItemMeta meta = mobHead.getItemMeta();
                    if (meta != null) {
                        String germanMobType = MobNameTranslator.translateToGerman(mobType);
                        meta.setDisplayName("§6" + dungeonName);
                        int kills = mysqlManager.getPlayerMobKills(founduuid, germanMobType);
                        meta.setLore(Collections.singletonList("§aDeine Kills: §6" + String.valueOf(kills)));
                        mobHead.setItemMeta(meta);
                        inv.setItem(i - startIndex, mobHead);
                    }
                }
            }
        }
        // "Nächste Seite"-Button
        if (page < totalPages) {
            ItemStack nextPageButton = new ItemStack(Material.ARROW);
            ItemMeta nextPageMeta = nextPageButton.getItemMeta();
            nextPageMeta.setDisplayName("§aNächste Seite");
            nextPageButton.setItemMeta(nextPageMeta);
            inv.setItem(53, nextPageButton);
        }

        // "Vorherige Seite"-Button
        if (page > 1) {
            ItemStack previousPageButton = new ItemStack(Material.ARROW);
            ItemMeta previousPageMeta = previousPageButton.getItemMeta();
            previousPageMeta.setDisplayName("§cVorherige Seite");
            previousPageButton.setItemMeta(previousPageMeta);
            inv.setItem(45, previousPageButton);
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
                                        openSecondMenu(player, 1, foundPlayerUUID);
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
    private String getMobTypeForDungeon(String dungeonName) {
        LocationConfigManager locationConfigManager = new LocationConfigManager(DungeonCrusher.getInstance());
        String mobType = locationConfigManager.getConfiguration().getString(dungeonName + ".mobTypes");
        if (mobType != null && mobType.startsWith("[") && mobType.endsWith("]")) {
            // Entferne die Klammern am Anfang und am Ende des Strings
            mobType = mobType.substring(1, mobType.length() - 1);
        }
        return mobType;
    }
    private ItemStack createCustomMobHead(String dungeonname, Integer modeldata) {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setCustomModelData(modeldata);
        itemmeta.setDisplayName(dungeonname);
        item.setItemMeta(itemmeta);
        return item;
    }
    private int extractDungeonNumber(String dungeonName) {
        try {
            return Integer.parseInt(dungeonName.replace("dungeon", ""));
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }
}

package net.dbsgameplay.dungeoncrusher.enums.Stats;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.Commands.interfaces.StatsCategory;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.MobNameTranslator;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
                            meta.setDisplayName(dungeonName);
                            String previousDungeonName = locationConfigManager.getPreviousDungeon(dungeonName);
                            if (previousDungeonName != null) {

                            String previousDungeonMobType = getMobTypeForDungeon(previousDungeonName);
                            if (previousDungeonMobType != null) {
                            String germanMobType = MobNameTranslator.translateToGerman(previousDungeonMobType);
                            int kills = mysqlManager.getPlayerMobKills(player.getUniqueId().toString(), germanMobType);
                            meta.setLore(Collections.singletonList("Deine Kills" + String.valueOf(kills)));
                            mobHead.setItemMeta(meta);
                            inv.setItem(i - startIndex, mobHead);
                                }
                            }
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

        player.openInventory(inv);
    }

    @Override
    public void handleItemClick(Player player, ItemStack clickedItem) {

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
    private List<String> getMobList(){
        return List.of("sheep", "pig", "cow", "horse", "donkey", "camel", "frog", "goat", "llama");
    }
}

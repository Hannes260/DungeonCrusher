package net.dbsgameplay.dungeoncrusher.listener.Navigator;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.MobNameTranslator;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Stats.StatsManager;
import net.dbsgameplay.dungeoncrusher.utils.shops.ShopManager;
import net.dbsgameplay.dungeoncrusher.utils.upgrades.UpgradeManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

public class NavigatorListener implements Listener {
      final Map<String,Integer> mobTextures = new HashMap<>();
    private final LocationConfigManager locationConfigManager;
    private final MYSQLManager mysqlManager;
    private final DungeonCrusher dungeonCrusher;

    public NavigatorListener(DungeonCrusher dungeonCrusher, LocationConfigManager locationConfigManager, MYSQLManager mysqlManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.locationConfigManager = locationConfigManager;
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

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() != null && event.getItem().getItemMeta() != null) {
            if (event.getItem().getItemMeta().getDisplayName().equals("§8➡ §9Teleporter §8✖ §7Rechtsklick")) {
                ItemStack item = event.getItem();
                    openNavigator(player); // Aufruf der bestehenden Methode zum Öffnen des Navigators
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String DisplayName = "§f<shift:-8>%nexo_teleporter_gui%";
        DisplayName = PlaceholderAPI.setPlaceholders(player, DisplayName);

        // Prüfen, ob das Inventar das spezielle Navigator-Inventar ist
        if (event.getView().getTitle().startsWith("§f" + DisplayName)) {
            event.setCancelled(true); // Verhindern, dass Spieler Items bewegen

            ItemStack clickedItem = event.getCurrentItem();

            // Sicherstellen, dass auf ein valides Item geklickt wird
            if (clickedItem == null || clickedItem.getItemMeta() == null || clickedItem.getItemMeta().getDisplayName() == null) {
                return; // Kein gültiges Item, also nichts tun
            }

            // Verarbeite nur bestimmte Items (mit bekannten DisplayNames)
            handleNavigatorClick(player, clickedItem);
        }
    }


    public void openNavigator(Player player) {
        openNavigator(player, 1); // Startet auf Seite 1
    }

    // Erweiterte Methode mit Seitennavigation
    private void openNavigator(Player player, int page) {

        Map<String, List<String>> dungeonsAndSavezones = locationConfigManager.getDungeonsAndSavezones();
        List<String> sortedDungeonNames = dungeonsAndSavezones.keySet().stream()
                .filter(name -> name.startsWith("dungeon"))
                .sorted(Comparator.comparingInt(this::extractDungeonNumber))
                .collect(Collectors.toList());

        int totalItems = sortedDungeonNames.size();
        int itemsPerPage = 45; // 44 für Items, 1 für den "Nächste Seite"-Button
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
        String DisplayName = "§f<shift:-8>%nexo_teleporter_gui%";
        DisplayName = PlaceholderAPI.setPlaceholders(player, DisplayName);
        Inventory navigatorInventory = Bukkit.createInventory(null, 9 * 6, "§f" + DisplayName);

        int startIndex = (page - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

        for (int i = startIndex; i < endIndex; i++) {
            String dungeonName = sortedDungeonNames.get(i);
            String mobType = getMobTypeForDungeon(dungeonName);
            if (mobType != null && mobTextures.containsKey(mobType)) {
                Integer requiredKills = locationConfigManager.getKills(dungeonName);
                if (requiredKills != null && hasRequiredKills(player, dungeonName)) {
                    Integer customModelData = mobTextures.get(mobType);
                    ItemStack mobHead = createCustomMobHead(dungeonName, customModelData);
                    if (mobHead != null) {
                        ItemMeta meta = mobHead.getItemMeta();
                        if (meta != null) {
                            meta.setDisplayName(dungeonName);
                            meta.setLore(Collections.singletonList(ConfigManager.getConfigMessage("message.navigatorheadlore","%mob_type%",mobType)));
                            mobHead.setItemMeta(meta);
                            navigatorInventory.setItem(i - startIndex, mobHead);
                        }
                    }
                } else {
                    if (requiredKills != null) {
                        ItemStack barrierItem = new ItemStack(Material.PAPER);
                        ItemMeta meta = barrierItem.getItemMeta();
                        meta.setCustomModelData(202);
                        if (meta != null) {
                            meta.setDisplayName("§c§lNicht Verfügbar.");
                            String previousDungeonName = locationConfigManager.getPreviousDungeon(dungeonName);
                            if (previousDungeonName != null) {
                                // Hole den Mob-Typ für den vorherigen Dungeon
                                String previousDungeonMobType = getMobTypeForDungeon(previousDungeonName);
                                if (previousDungeonMobType != null) {
                                    String germanMobType = MobNameTranslator.translateToGerman(previousDungeonMobType);
                                    // Hole die Kills des Spielers für den Mob-Typ des vorherigen Dungeons
                                    int kills = mysqlManager.getPlayerMobKills(player.getUniqueId().toString(), germanMobType);
                                    int finalKills = requiredKills - kills;
                                    String requiredKillsString = String.valueOf(finalKills);
                                    meta.setLore(Collections.singletonList(ConfigManager.getConfigMessage("message.requiredkillsforupgrade", "%required_kills%", requiredKillsString, "%required_mob%", germanMobType)));


                                }
                            } else {
                                meta.setLore(Collections.singletonList("§cKeine vorherigen Dungeons verfügbar."));
                            }
                            barrierItem.setItemMeta(meta);
                            navigatorInventory.setItem(i - startIndex, barrierItem);
                        }
                    }
                }
            } else {
                System.out.println(ConfigManager.getConfigMessage("message.textureurlfail","","") + dungeonName);
            }
        }

        // "Nächste Seite"-Button hinzufügen
        if (page < totalPages) {
            ItemStack nextPageButton = new ItemStack(Material.ARROW);
            ItemMeta nextPageMeta = nextPageButton.getItemMeta();
            nextPageMeta.setDisplayName("§aNächste Seite");
            nextPageButton.setItemMeta(nextPageMeta);
            navigatorInventory.setItem(45, nextPageButton);
        }

        // "Vorherige Seite"-Button hinzufügen
        if (page > 1) {
            ItemStack previousPageButton = new ItemStack(Material.ARROW);
            ItemMeta previousPageMeta = previousPageButton.getItemMeta();
            previousPageMeta.setDisplayName("§cVorherige Seite");
            previousPageButton.setItemMeta(previousPageMeta);
            navigatorInventory.setItem(53, previousPageButton);
        }

        // Hinzufügen von statischen Elementen, wie in der einfachen Methode
        addStaticElementsToNavigator(navigatorInventory, player);

        player.openInventory(navigatorInventory);
    }
    private void addStaticElementsToNavigator(Inventory navigatorInventory, Player player) {
        // Spawn Item hinzufügen
        ItemStack spawn = new ItemStack(Material.PAPER);
        ItemMeta meta = spawn.getItemMeta();
        meta.setDisplayName("§7➢ §bSpawn");
        meta.setCustomModelData(204);
        spawn.setItemMeta(meta);
        navigatorInventory.setItem(49, spawn);

        // Upgrades Item hinzufügen
        ItemStack upgrade = new ItemStack(Material.PAPER);
        ItemMeta upgrademeta = upgrade.getItemMeta();
        upgrademeta.setDisplayName("§7➢ §bUpgrades");
        upgrademeta.setCustomModelData(207);
        upgrade.setItemMeta(upgrademeta);
        navigatorInventory.setItem(51, upgrade);

        // Shop Item hinzufügen
        ItemStack shop = new ItemStack(Material.PAPER);
        ItemMeta shopmeta =  shop.getItemMeta();
        shopmeta.setDisplayName("§7➢ §bShop");
        shopmeta.setCustomModelData(208);
        shop.setItemMeta(shopmeta);
        navigatorInventory.setItem(47, shop);

        // Spielerstatistiken anzeigen
        String Geld = "§f%nexo_geld%";
        Geld = PlaceholderAPI.setPlaceholders(player, Geld);
        String Kills = "§f%nexo_kills%";
        Kills = PlaceholderAPI.setPlaceholders(player, Kills);
        String Tode = "§f%nexo_tode%";
        Tode = PlaceholderAPI.setPlaceholders(player, Tode);

        String currentmoney = mysqlManager.getBalance(player.getUniqueId().toString());
        ItemStack profileItem = new ItemStack(Material.PAPER);
        ItemMeta profilemeta = profileItem.getItemMeta();
        profilemeta.setDisplayName("§7➢ §bDeine Stats");
        profilemeta.setCustomModelData(205);
        profileItem.setItemMeta(profilemeta);
        navigatorInventory.setItem(53, profileItem);

        // Schließen Item hinzufügen
        ItemStack closeItem = new ItemStack(Material.PAPER);
        ItemMeta closemeta = closeItem.getItemMeta();
        closemeta.setDisplayName("§7➢ §bSchließen");
        closemeta.setCustomModelData(201);
        closeItem.setItemMeta(closemeta);
        navigatorInventory.setItem(45, closeItem);
    }
    private void handleNavigatorClick(Player player, ItemStack clickedItem) {
        String itemName = clickedItem.getItemMeta().getDisplayName();

        if (itemName.equals("§7➢ §bSpawn")) {
            player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1.0f, 1.0f);
            teleportToSpawn(player);
        } else if (itemName.equals("§c§lNicht Verfügbar.")) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughkills", "", ""));
            player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 1.0f, 1.0f);
        } else if (itemName.equals("§7➢ §bUpgrades")) {
            player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1.0f, 1.0f);
            UpgradeManager.openMainMenu(player);
        } else if (clickedItem.getType().equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE)) {
            return;
        } else if (itemName.equals("§7➢ §bShop")) {
            player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1.0f, 1.0f);
            ShopManager.openMainShopMenu(player, mysqlManager);
        } else if (itemName.equals("§7➢ §bSchließen")) {
            player.playSound(player.getLocation(), Sound.BLOCK_BARREL_CLOSE, 1.0f, 1.0f);
            player.closeInventory();
        } else if (itemName.equals("§7➢ §bDeine Stats")) {
            player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1.0f, 1.0f);
            StatsManager.openMainShopMenu(player);
        } else {
            if (hasRequiredKills(player, itemName)) {
                teleportToDungeon(player, itemName);
            }
        }
    }
    private String getMobTypeForDungeon(String dungeonName) {
        String mobType = locationConfigManager.getConfiguration().getString(dungeonName + ".mobTypes");
        if (mobType != null && mobType.startsWith("[") && mobType.endsWith("]")) {
            // Entferne die Klammern am Anfang und am Ende des Strings
            mobType = mobType.substring(1, mobType.length() - 1);
        }
        return mobType;
    }
    private int extractDungeonNumber(String dungeonName) {
        try {
            return Integer.parseInt(dungeonName.replace("dungeon", ""));
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }
    private void teleportToDungeon(Player player, String dungeonName) {
        Location dungeonSpawn = locationConfigManager.getSpawnpoint(dungeonName);
        if (dungeonSpawn != null) {
            player.teleport(dungeonSpawn);
        } else {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.dungeonotsetspawnpoint", "", ""));
        }
    }
    private void teleportToSpawn(Player player) {
        Location spawnLocation = locationConfigManager.getSpawn();
        player.teleport(spawnLocation);
}
    private boolean hasRequiredKills(Player player, String currentDungeonName) {
        // Prüfe, ob es sich um Dungeon 1 handelt
        if ("dungeon1".equals(currentDungeonName)) {
            // Dungeon 1 ist immer freigeschaltet
            return true;
        }
        // Hole den Namen des vorherigen Dungeons
        String previousDungeonName = locationConfigManager.getPreviousDungeon(currentDungeonName);
        if (previousDungeonName == null) {
            return false; // Kein vorheriger Dungeon gefunden
        }

        // Hole den Mob-Typ für den vorherigen Dungeon
        String previousDungeonMobType = String.valueOf(locationConfigManager.getMobTypesForDungeon(previousDungeonName));
        String germanMobType = MobNameTranslator.translateToGerman(previousDungeonMobType);

        // Hole die Kills des Spielers für den Mob-Typ des vorherigen Dungeons
        int kills = mysqlManager.getPlayerMobKills(String.valueOf(player.getUniqueId()), germanMobType);

        // Hole die erforderlichen Kills für den aktuellen Dungeon
        Integer requiredKills = locationConfigManager.getKills(currentDungeonName);
        if (requiredKills == null) {
            requiredKills = 0;
        }

        return kills >= requiredKills;
    }
    private ItemStack createCustomMobHead(String dungeonname, Integer modeldata) {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setCustomModelData(modeldata);
        itemmeta.setDisplayName(dungeonname);
        item.setItemMeta(itemmeta);
        return item;
    }
}


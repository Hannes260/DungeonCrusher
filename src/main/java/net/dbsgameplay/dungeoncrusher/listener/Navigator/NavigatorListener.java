package net.dbsgameplay.dungeoncrusher.listener.Navigator;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.MobNameTranslator;
import net.dbsgameplay.dungeoncrusher.objects.PlayerHead;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.TexturedHeads;
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
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

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
        String DisplayName = "§f<shift:-8>%oraxen_teleporter_gui%";
        DisplayName = PlaceholderAPI.setPlaceholders(player, DisplayName);
        if (event.getView().getTitle().startsWith("§f" + DisplayName)) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.getItemMeta() != null && clickedItem.getItemMeta().getDisplayName() != null) {
                String itemName = clickedItem.getItemMeta().getDisplayName();

                // Nächste Seite
                if (itemName.equals("§aNächste Seite")) {
                    openNavigator(player, 2);
                }

                // Vorherige Seite
                if (itemName.equals("§cVorherige Seite")) {
                    openNavigator(player, 1);
                }
                // Handle other clicks (z.B. für die Menüoptionen)
                handleNavigatorClick(player, clickedItem);
            }
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
        String DisplayName = "§f<shift:-8>%oraxen_teleporter_gui%";
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
                                    meta.setLore(Collections.singletonList(ConfigManager.getConfigMessage("message.requiredkillsforupgrade", "%required_kills%", requiredKillsString)));


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
        PlayerProfile profile = TexturedHeads.getProfile("https://textures.minecraft.net/texture/1289d5b178626ea23d0b0c3d2df5c085e8375056bf685b5ed5bb477fe8472d94");
        ItemStack spawn = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) spawn.getItemMeta();
        meta.setOwnerProfile(profile);
        meta.setDisplayName("§7➢ §bSpawn");
        spawn.setItemMeta(meta);
        navigatorInventory.setItem(49, spawn);

        // Upgrades Item hinzufügen
        PlayerProfile upgradeprofile = TexturedHeads.getProfile("https://textures.minecraft.net/texture/77334cddfab45d75ad28e1a47bf8cf5017d2f0982f6737da22d4972952510661");
        ItemStack upgrade = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta upgrademeta = (SkullMeta) upgrade.getItemMeta();
        upgrademeta.setOwnerProfile(upgradeprofile);
        upgrademeta.setDisplayName("§7➢ §bUpgrades");
        upgrade.setItemMeta(upgrademeta);
        navigatorInventory.setItem(51, upgrade);

        // Shop Item hinzufügen
        PlayerProfile shopprofile = TexturedHeads.getProfile("https://textures.minecraft.net/texture/67505cb45a0dfc4ec0b741adbce6b5056ed51aba63fea9b2d66d758cac0f2412");
        ItemStack shop = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta shopmeta = (SkullMeta) shop.getItemMeta();
        shopmeta.setOwnerProfile(shopprofile);
        shopmeta.setDisplayName("§7➢ §bShop");
        shop.setItemMeta(shopmeta);
        navigatorInventory.setItem(47, shop);

        // Spielerstatistiken anzeigen
        String Geld = "§f%oraxen_geld%";
        Geld = PlaceholderAPI.setPlaceholders(player, Geld);
        String Kills = "§f%oraxen_kills%";
        Kills = PlaceholderAPI.setPlaceholders(player, Kills);
        String Tode = "§f%oraxen_tode%";
        Tode = PlaceholderAPI.setPlaceholders(player, Tode);

        String currentmoney = mysqlManager.getBalance(player.getUniqueId().toString());
        navigatorInventory.setItem(53, (new PlayerHead(player.getName(), "§eDeine Stats: ", Geld +" §e" + currentmoney,
                Kills + " §e"+ mysqlManager.getKills(String.valueOf(player.getUniqueId())), Tode +" §e" + mysqlManager.getDeaths(player.getUniqueId().toString()))).getItemStack());

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
        } else if (itemName.equals("§eDeine Stats: ")) {
            return;
        } else {
            if (hasRequiredKills(player, itemName)) {
                teleportToDungeon(player, itemName);
            } else {
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughkills", "", ""));
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


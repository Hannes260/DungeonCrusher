package net.dbsgameplay.dungeoncrusher.listener;

import net.dbsgameplay.dungeoncrusher.utils.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.TexturedHeads;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
    private final Map<String, String> mobTextures = new HashMap<>();
    private final LocationConfigManager locationConfigManager;

    public NavigatorListener(LocationConfigManager locationConfigManager) {
        this.locationConfigManager = locationConfigManager;
        mobTextures.put("frog", "https://textures.minecraft.net/texture/137c0c9d3824537b0652b404f07baadf9a55b6a02b28e26a5a8807e8ecfcc7e3");
        mobTextures.put("rabbit", "https://textures.minecraft.net/texture/a0badbfb06ee97714dd55e162d0ce6742da0396a6d291341e043d0edb40ccbf1");
        mobTextures.put("chicken","https://textures.minecraft.net/texture/4d8fefad255d6e5c9f8245c516f0d9bbff4f0552db8b1afc3dcf7d1dd5be23fd");
        mobTextures.put("cat","https://textures.minecraft.net/texture/c67b2b6a9c75168b50776ff002739002c84fc8217f739fb185018ffe77b0b5ed");
        mobTextures.put("fox", "https://textures.minecraft.net/texture/962270da2b352337c0f0bb2de3c03d1e6bfc4fbd2fe41e1bb5f8b998253dc8fc");
        mobTextures.put("wolf", "https://textures.minecraft.net/texture/b8bdd0df38511079fbda75a1c6dc1b9b03412118246a237bd5c47faf6e794e12");
        mobTextures.put("sheep", "https://textures.minecraft.net/texture/a723893df4cfb9c7240fc47b560ccf6ddeb19da9183d33083f2c71f46dad290a");
        mobTextures.put("pig", "https://textures.minecraft.net/texture/319b7fcd8ab72e293edbdb1c615d658908d2ed354880eb6964634a4657898c60");
        mobTextures.put("cow", "https://textures.minecraft.net/texture/b667c0e107be79d7679bfe89bbc57c6bf198ecb529a3295fcfdfd2f24408dca3");
        mobTextures.put("goat","https://textures.minecraft.net/texture/cb4223e8582edbb0c98687aa5faff98da04670c241289bc4ff1f25d7988fa966");
        mobTextures.put("horse", "https://textures.minecraft.net/texture/2f14d79b02e7382f35ebc25b93d564d2327c102d955b5e132b8c6da105ab8d3d");
        mobTextures.put("donkey","https://textures.minecraft.net/texture/399bb50d1a214c394917e25bb3f2e20698bf98ca703e4cc08b42462df309d6e6");
        mobTextures.put("camel", "https://textures.minecraft.net/texture/74b8a333dfa92e7e5a95ad4ae2d84b1bafa33dc28c054925277f60e79dafc8c4");
        mobTextures.put("llama", "https://textures.minecraft.net/texture/9f7d90b305aa64313c8d4404d8d652a96eba8a754b67f4347dcccdd5a6a63398");
        mobTextures.put("ocelot", "https://textures.minecraft.net/texture/15852d265d960da3e06b8b3a91632253f4a107febfb7069c9e46e5f73a4de9b4");
        mobTextures.put("mushroom_cow", "https://textures.minecraft.net/texture/8c56476c74ebafbf8d4d3cd83e29ee662a46c8f4f8129d4386027bd49460c488");
        mobTextures.put("polar_bear","https://textures.minecraft.net/texture/5e867925c43405dcd845e31df9f069b9d50fb4665b032680c654e19cbbaca0b");
        mobTextures.put("mule","https://textures.minecraft.net/texture/46dcda265e57e4f51b145aacbf5b59bdc6099ffd3cce0a661b2c0065d80930d8");
        mobTextures.put("sniffer", "https://textures.minecraft.net/texture/fe5a8341c478a134302981e6a7758ea4ecfd8d62a0df4067897e75502f9b25de");
        mobTextures.put("panda", "https://textures.minecraft.net/texture/8339c4ee85e04663524d44b388d0951ed9b5ae27f43c2411ec11e824e55f5263");
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() != null) {
            if (event.getItem().getItemMeta().getDisplayName().equals("§8➡ §9Teleporter §8✖ §7Rechtsklick")) {
                ItemStack item = event.getItem();
                if (item != null && item.getType() == Material.ENDER_EYE) {
                    event.setCancelled(true);
                    openNavigator(player);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("§e§lTeleporter")) {
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.getItemMeta() != null && clickedItem.getItemMeta().getDisplayName() != null) {
                String itemName = clickedItem.getItemMeta().getDisplayName();
                if (itemName.equals("§7➢ Spawn")) {
                    teleportToSpawn(player);
                } else {
                    teleportToDungeon(player, itemName);
                }
            }
        }
    }

    private void openNavigator(Player player) {
        Inventory navigatorInventory = Bukkit.createInventory(null, 9 * 6, "§e§lTeleporter");
        Map<String, List<String>> dungeonsAndSavezones = locationConfigManager.getDungeonsAndSavezones();

        List<String> sortedDungeonNames = dungeonsAndSavezones.keySet().stream()
                .filter(name -> name.startsWith("dungeon"))
                .sorted(Comparator.comparingInt(this::extractDungeonNumber))
                .collect(Collectors.toList());

        int inventorySlot = 0;
        for (String dungeonName : sortedDungeonNames) {
            String mobType = getMobTypeForDungeon(dungeonName);
            if (mobType != null && mobTextures.containsKey(mobType)) {
                ItemStack mobHead = createCustomMobHead(mobTextures.get(mobType));
                if (mobHead != null) {
                    // Setze den Namen des Dungeons als Display-Namen des Kopfes
                    ItemMeta meta = mobHead.getItemMeta();
                    if (meta != null) {
                        meta.setDisplayName(dungeonName);
                        mobHead.setItemMeta(meta);
                        navigatorInventory.setItem(inventorySlot, mobHead);
                        inventorySlot++;
                    }
                }
            } else {
                System.out.println(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.textureurlfail","","") + dungeonName);
            }
        }
        PlayerProfile profile = TexturedHeads.getProfile("https://textures.minecraft.net/texture/1289d5b178626ea23d0b0c3d2df5c085e8375056bf685b5ed5bb477fe8472d94");
        ItemStack spawn = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) spawn.getItemMeta();
        meta.setOwnerProfile(profile);
        meta.setDisplayName("§7➢ Spawn");
        spawn.setItemMeta(meta);
        navigatorInventory.setItem(49, spawn);
        player.openInventory(navigatorInventory);
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

    private ItemStack createCustomMobHead(String textureURL) {
        PlayerProfile profile = TexturedHeads.getProfile(textureURL);
        ItemStack mobHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) mobHead.getItemMeta();
        meta.setOwnerProfile(profile);
        mobHead.setItemMeta(meta);
        return mobHead;
    }
}


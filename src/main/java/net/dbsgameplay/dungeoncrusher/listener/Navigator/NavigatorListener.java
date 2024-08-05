package net.dbsgameplay.dungeoncrusher.listener.Navigator;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.objects.PlayerHead;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.TexturedHeads;
import net.dbsgameplay.dungeoncrusher.utils.shops.ShopManager;
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
    private final Map<String, String> mobTextures = new HashMap<>();
    private final LocationConfigManager locationConfigManager;
    private final MYSQLManager mysqlManager;
    private final DungeonCrusher dungeonCrusher;

    public NavigatorListener(DungeonCrusher dungeonCrusher, LocationConfigManager locationConfigManager, MYSQLManager mysqlManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.locationConfigManager = locationConfigManager;
        this.mysqlManager = mysqlManager;

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
        mobTextures.put("panda", "https://textures.minecraft.net/texture/8339c4ee85e04663524d44b388d0951ed9b5ae27f43c2411ec11e824e55f5263");
        mobTextures.put("sniffer", "https://textures.minecraft.net/texture/fe5a8341c478a134302981e6a7758ea4ecfd8d62a0df4067897e75502f9b25de");
        mobTextures.put("silverfish", "https://textures.minecraft.net/texture/92ec2c3cb95ab77f7a60fb4d160bced4b879329b62663d7a9860642e588ab210");
        mobTextures.put("axolotl","https://textures.minecraft.net/texture/5c167410409336acc58e6433ffa8b7f86a8786e35ec7300b9062340281d4691c");
        mobTextures.put("vindicator", "https://textures.minecraft.net/texture/daeed9d8ed1769e77e3cfe11dc179668ed0db1de6ce29f1c8e0d5fe5e6573b60");
        mobTextures.put("turtle","https://textures.minecraft.net/texture/e55c917d968dd77ad2bf71c31d1f0700a4e30110a0fdf12886b8c7d03a43ede0");
        mobTextures.put("zombie_horse","https://textures.minecraft.net/texture/f896a1dbde1a19540ce7336c6c94f59652aa98bb1068b2ef8c8fa6ef85804f57");
        mobTextures.put("villager","https://textures.minecraft.net/texture/a36e9841794a37eb99524925668b47a62b5cb72e096a9f8f95e106804ae13e1b");
        mobTextures.put("zombie_villager","https://textures.minecraft.net/texture/acf384602d4ef3f6f5dd18eafc2cb72792fe10ed9e2cd81247820f5c7590671f");
        mobTextures.put("snow_golem","https://textures.minecraft.net/texture/1fdfd1f7538c040258be7a91446da89ed845cc5ef728eb5e690543378fcf4");
        mobTextures.put("skeleton","https://textures.minecraft.net/texture/38f6c044a1b4db6f8102cb51e2c36c6b32751128abbd18a5d9c9c67a55b19afb");
        mobTextures.put("drowned","https://textures.minecraft.net/texture/34a78bf783bd445448cdf87c5403e39bbab850cf3a131d909cd9e881d88d8c6e");
        mobTextures.put("husk","https://textures.minecraft.net/texture/1e83aca14ae242af01a1f459c7d0a7f3ef71cbfbbcbf49a99e6e6d70952313ac");
        mobTextures.put("spider","https://textures.minecraft.net/texture/ac2359e7b3627d7fe45abbbb80b9a2696acf4139188067d702dad4abf617a9f7");
        mobTextures.put("zombie","https://textures.minecraft.net/texture/bf65f954d5a2cdd009d4e3e674e1234fcbfd61def915f231bb941e8d92af2c1c");
        mobTextures.put("stray","https://textures.minecraft.net/texture/9e391c6e535f7aa5a2b6ee6d137f59f2d7c60def88853ba611ceb2d16a7e7c73");
        mobTextures.put("creeper","https://textures.minecraft.net/texture/b99aeec93cd56ebf81dbd21fc6c8bf141905121769baca1bc8c56ff46c6b99b8");
        mobTextures.put("cave_spider","https://textures.minecraft.net/texture/eccc4a32d45d74e8b14ef1ffd55cd5f381a06d4999081d52eaea12e13293e209");
        mobTextures.put("endermite","https://textures.minecraft.net/texture/2fc4a7542b754420b1b19f9a28ea00040555a9e876052b97f65840308a93348d");
        mobTextures.put("strider","https://textures.minecraft.net/texture/125851a86ee1c54c94fc5bed017823dfb3ba08eddbcab2a914ef45b596c1603");
        mobTextures.put("blaze","https://textures.minecraft.net/texture/c74f65f9b9958a6392c8b63324d76e80d2b509c1985a00232aecce409585ae2a");
        mobTextures.put("skeleton_horse","https://textures.minecraft.net/texture/47effce35132c86ff72bcae77dfbb1d22587e94df3cbc2570ed17cf8973a");
        mobTextures.put("witch","https://textures.minecraft.net/texture/8aa986a6e1c2d88ff198ab2c3259e8d2674cb83a6d206f883bad2c8ada819");
        mobTextures.put("slime","https://textures.minecraft.net/texture/841a3197d5d3b54c3cbb7d5f7530593d149c7fec753a5ad8f10276d264342705");
        mobTextures.put("magma_cube","https://textures.minecraft.net/texture/a1c97a06efde04d00287bf20416404ab2103e10f08623087e1b0c1264a1c0f0c");
        mobTextures.put("enderman","https://textures.minecraft.net/texture/8a108a0a7a387859f2c44fb9702cf73dbafee3ecfdc4f5def46c0d651b7a49f7");
        mobTextures.put("piglin","https://textures.minecraft.net/texture/a792b6997d739f535beed3ab1d4aeadfa76777bf8e38a666f54f82ff9f858186");
        mobTextures.put("zombified_piglin","https://textures.minecraft.net/texture/8954d0d1c286c1b34fb091841c06aed741a1bf9b65b9a430e4e5ca1d1c4b9f6f");
        mobTextures.put("piglin_brute","https://textures.minecraft.net/texture/81fc7957e4602b32dc135014383d93deba43dc484332bc19c6792be1a90f720a");
        mobTextures.put("pillager","https://textures.minecraft.net/texture/18e57841607f449e76b7c820fcbd1913ec1b80c4ac81728874db230f5df2b3b");
        mobTextures.put("hoglin","https://textures.minecraft.net/texture/9bb9bc0f01dbd762a08d9e77c08069ed7c95364aa30ca1072208561b730e8d75");
        mobTextures.put("evoker","https://textures.minecraft.net/texture/3433322e2ccbd9c55ef41d96f38dbc666c803045b24391ac9391dccad7cd");
        mobTextures.put("ghast","https://textures.minecraft.net/texture/64ab8a22e7687cc4c78f3b6ff5b1eb04917b51cd3cd7dbce36171160b3c77ced");
        mobTextures.put("wither_skeleton","https://textures.minecraft.net/texture/84e7c6fd4aed536d1047c19cb335ccfe20b3e68b56ec16a731e548b9b23a269a");
        mobTextures.put("zoglin","https://textures.minecraft.net/texture/e67e18602e03035ad68967ce090235d8996663fb9ea47578d3a7ebbc42a5ccf9");
        mobTextures.put("ravager","https://textures.minecraft.net/texture/5c73e16fa2926899cf18434360e2144f84ef1eb981f996148912148dd87e0b2a");
        mobTextures.put("iron_golem","https://textures.minecraft.net/texture/e13f34227283796bc017244cb46557d64bd562fa9dab0e12af5d23ad699cf697");
        mobTextures.put("warden","https://textures.minecraft.net/texture/2622cd6ee1f774948acf909ff9805c5a4340174e8b385cbe464825b946f891e9");
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
        Player player = (Player) event.getWhoClicked();
        String DisplayName = "%oraxen_teleporter%";
        DisplayName = PlaceholderAPI.setPlaceholders(player, DisplayName);
        if (event.getView().getTitle().equals("§f"+DisplayName)) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.getItemMeta() != null && clickedItem.getItemMeta().getDisplayName() != null) {
                String itemName = clickedItem.getItemMeta().getDisplayName();
                if (itemName.equals("§7➢ §bSpawn")) {
                    player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1.0f, 1.0f);
                    teleportToSpawn(player);
                } else if (itemName.equals("§c§lNicht Verfügbar.")) {
                    player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughkills", "", ""));
                } else if (itemName.equals("§7➢ §bUpgrades")) {
                    player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1.0f, 1.0f);
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
                    }else {
                        player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.notenoughkills","",""));
                    }
                }
            }
        }
    }

    private void openNavigator(Player player) {
        String DisplayName = "%img_teleporter%";
        DisplayName = PlaceholderAPI.setPlaceholders(player, DisplayName);
        Inventory navigatorInventory = Bukkit.createInventory(null, 9 * 6, "§f"+DisplayName);
        Map<String, List<String>> dungeonsAndSavezones = locationConfigManager.getDungeonsAndSavezones();

        List<String> sortedDungeonNames = dungeonsAndSavezones.keySet().stream()
                .filter(name -> name.startsWith("dungeon"))
                .sorted(Comparator.comparingInt(this::extractDungeonNumber))
                .collect(Collectors.toList());

        int inventorySlot = 0;
        for (String dungeonName : sortedDungeonNames) {
            String mobType = getMobTypeForDungeon(dungeonName);
            if (mobType != null && mobTextures.containsKey(mobType)) {
                Integer requiredKills = locationConfigManager.getKills(dungeonName);
                if (requiredKills != null && hasRequiredKills(player, dungeonName)) {
                ItemStack mobHead = createCustomMobHead(mobTextures.get(mobType));
                if (mobHead != null) {
                    ItemMeta meta = mobHead.getItemMeta();
                    if (meta != null) {
                        meta.setDisplayName(dungeonName);
                        meta.setLore(Collections.singletonList(ConfigManager.getConfigMessage("message.navigatorheadlore","%mob_type%",mobType)));
                        mobHead.setItemMeta(meta);
                        navigatorInventory.setItem(inventorySlot, mobHead);
                        inventorySlot++;
                    }
                }
                }else {
                    // Füge ein Barrier-Item hinzu oder ein Kopf, der anzeigt, dass der Dungeon gesperrt ist
                    if (requiredKills != null) { // Überprüfen, ob requiredKills nicht null ist
                        PlayerProfile profile = TexturedHeads.getProfile("https://textures.minecraft.net/texture/afd2400002ad9fbbbd0066941eb5b1a384ab9b0e48a178ee96e4d129a5208654");
                        ItemStack barrierItem = new ItemStack(Material.PLAYER_HEAD);
                        SkullMeta meta = (SkullMeta) barrierItem.getItemMeta();
                        meta.setOwnerProfile(profile);
                        if (meta != null) {
                            String requiredKillsString = String.valueOf(requiredKills);
                            meta.setDisplayName("§c§lNicht Verfügbar.");
                            meta.setLore(Collections.singletonList(ConfigManager.getConfigMessage("message.requiredkillsforupgrade", "%required_kills%", requiredKillsString)));
                            barrierItem.setItemMeta(meta);
                            navigatorInventory.setItem(inventorySlot, barrierItem);
                            inventorySlot++;
                        }
                    }
                }

            } else {
                System.out.println(ConfigManager.getConfigMessage("message.textureurlfail","","") + dungeonName);
            }
        }
        PlayerProfile profile = TexturedHeads.getProfile("https://textures.minecraft.net/texture/1289d5b178626ea23d0b0c3d2df5c085e8375056bf685b5ed5bb477fe8472d94");
        ItemStack spawn = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) spawn.getItemMeta();
        meta.setOwnerProfile(profile);
        meta.setDisplayName("§7➢ §bSpawn");
        spawn.setItemMeta(meta);
        navigatorInventory.setItem(49, spawn);

        PlayerProfile upgradeprofile = TexturedHeads.getProfile("https://textures.minecraft.net/texture/77334cddfab45d75ad28e1a47bf8cf5017d2f0982f6737da22d4972952510661");
        ItemStack upgrade = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta upgrademeta = (SkullMeta) upgrade.getItemMeta();
        upgrademeta.setOwnerProfile(upgradeprofile);
        upgrademeta.setDisplayName("§7➢ §bUpgrades");
        upgrade.setItemMeta(upgrademeta);
        navigatorInventory.setItem(51, upgrade);

        PlayerProfile shopprofile = TexturedHeads.getProfile("https://textures.minecraft.net/texture/67505cb45a0dfc4ec0b741adbce6b5056ed51aba63fea9b2d66d758cac0f2412");
        ItemStack shop = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta shopmeta = (SkullMeta) upgrade.getItemMeta();
        shopmeta.setOwnerProfile(shopprofile);
        shopmeta.setDisplayName("§7➢ §bShop");
        shop.setItemMeta(shopmeta);
        navigatorInventory.setItem(47, shop);

        String Geld = "§f%img_geld%";
        Geld = PlaceholderAPI.setPlaceholders(player, Geld);
        String Kills = "§f%img_kills%";
        Kills = PlaceholderAPI.setPlaceholders(player, Kills);

        String Tode = "§f%img_tode%";
        Tode = PlaceholderAPI.setPlaceholders(player, Tode);

        String currentmoney = mysqlManager.getBalance(player.getUniqueId().toString());
        navigatorInventory.setItem(53, (new PlayerHead(player.getName(), "§eDeine Stats: ", Geld +" §e" + currentmoney ,
                Kills + " §e"+ mysqlManager.getKills(String.valueOf(player.getUniqueId())), Tode +" §e" + mysqlManager.getDeaths(player.getUniqueId().toString()))).getItemStack());

        fillEmptySlots(navigatorInventory, Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1);

        PlayerProfile closeprofile = TexturedHeads.getProfile("https://textures.minecraft.net/texture/afd2400002ad9fbbbd0066941eb5b1a384ab9b0e48a178ee96e4d129a5208654");
        ItemStack closeItem = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta closemeta = (SkullMeta) closeItem.getItemMeta();
        closemeta.setOwnerProfile(closeprofile);
        closemeta.setDisplayName("§7➢ §bSchließen");
        closeItem.setItemMeta(closemeta);
        navigatorInventory.setItem(45, closeItem);
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
    private boolean hasRequiredKills(Player player, String dungeonName) {
        Integer kills = Integer.parseInt(mysqlManager.getKills(String.valueOf(player.getUniqueId())));
        Integer requiredKills = locationConfigManager.getKills(dungeonName);

        // Wenn requiredKills null ist, setze den Wert auf 0
        if (requiredKills == null) {
            requiredKills = 0;
        }

        return kills >= requiredKills;
    }
    private ItemStack createCustomMobHead(String textureURL) {
        PlayerProfile profile = TexturedHeads.getProfile(textureURL);
        ItemStack mobHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) mobHead.getItemMeta();
        meta.setOwnerProfile(profile);
        mobHead.setItemMeta(meta);
        return mobHead;
    }
    private void fillEmptySlots(Inventory inventory, Material material, int amount) {
        for (int i = 0; i < inventory.getSize(); ++i) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                ItemStack newItemStack = new ItemStack(material, amount, (short) 7);
                ItemMeta itemMeta = newItemStack.getItemMeta();
                itemMeta.setDisplayName("§a ");
                newItemStack.setItemMeta(itemMeta);
                inventory.setItem(i, newItemStack);
            }
        }
    }
}


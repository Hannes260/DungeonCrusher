package net.dbsgameplay.dungeoncrusher.listener;


import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Joinlistener implements Listener {
    private final MYSQLManager mysqlManager;
    private final LocationConfigManager locationConfigManager;
    private final ScoreboardBuilder scoreboardBuilder;
    private final DungeonCrusher dungeonCrusher;
    public Joinlistener(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager, LocationConfigManager locationConfigManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.mysqlManager = mysqlManager;
        this.scoreboardBuilder = new ScoreboardBuilder(dungeonCrusher);
        this.locationConfigManager = locationConfigManager;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(ChatColor.GREEN + "[+]" + " " + ChatColor.GRAY + player.getDisplayName());
        mysqlManager.updatePlayerUUIDByName(player.getName(), player.getUniqueId().toString());
        player.teleport(locationConfigManager.getSpawn());
        String playerName = event.getPlayer().getName();
        String message = "Hat das Spiel betreten";
        String fullMessage = playerName + ": " + message;
        DungeonCrusher.getInstance().sendToDiscord(fullMessage, 65280);
        if (!player.hasPlayedBefore()) {
            Bukkit.broadcastMessage(ConfigManager.getConfigMessage("message.firstjoin", "%player%", player.getName()));
            double startmoney = 500;
            String formattedMoney = String.format(Locale.ENGLISH, "%,.2f", startmoney);
            mysqlManager.updateBalance(player.getUniqueId().toString(), formattedMoney);
            mysqlManager.updateSwordLevel(player.getUniqueId().toString(), 1);
            scoreboardBuilder.updateMoney(player);

            // Initialisiere das Holzschwert
            ItemStack woodensword = new ItemStack(Material.WOODEN_SWORD);
            ItemMeta woodenmeta = woodensword.getItemMeta();
            woodenmeta.setDisplayName("§7<<§6Holzschwert §7- §aLv.1§7>>");
            woodenmeta.setLore(Collections.singletonList("§94.0 Angriffsschaden"));
            woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            woodenmeta.setUnbreakable(true);
            woodensword.setItemMeta(woodenmeta);
            player.getInventory().setItem(0, woodensword);

            // Füge 64 gekochtes Rindfleisch hinzu
            ItemStack food = new ItemStack(Material.BREAD, 5);
            player.getInventory().setItem(1, food);
        }

        // Initialisiere den Schwert-Upgrade-ClickListener
        //SwordUpgradeClickListener swordUpgradeClickListener = new SwordUpgradeClickListener(dungeonCrusher, mysqlManager);
        //int currentlevel = mysqlManager.getSwordLevel(player.getUniqueId().toString());
        //swordUpgradeClickListener.giveSwordToPlayer(player, currentlevel);

        // Setze Glaspaneele in das Inventar
        ItemStack glass = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta glassmeta = glass.getItemMeta();
        glassmeta.setDisplayName(" ");
        glass.setItemMeta(glassmeta);
        for (int slot : new int[]{11, 12, 13, 14, 15, 20, 21, 23, 24, 29, 30, 31, 32, 33}) {
            player.getInventory().setItem(slot, glass);
        }

        // Zeige eine Nachricht, wenn der Spieler seine tägliche Belohnung beanspruchen kann
        if (mysqlManager.canClaimDailyReward(player.getUniqueId().toString())) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.canclaimdailyreward"));
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        }

        // Füge den Navigator und das Rohkupfer zum Inventar hinzu
        ItemStack navigator = new ItemStack(Material.PAPER);
        ItemMeta navigatormeta = navigator.getItemMeta();
        navigatormeta.setCustomModelData(200);
        navigatormeta.setDisplayName("§8➡ §9Teleporter §8✖ §7Rechtsklick");
        navigator.setItemMeta(navigatormeta);
        player.getInventory().setItem(8, navigator);

        ItemStack rawcopper = new ItemStack(Material.RAW_COPPER);
        ItemMeta rawcoppermeta = rawcopper.getItemMeta();
        rawcoppermeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "raw_copper"));
        rawcoppermeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
        rawcoppermeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        rawcopper.setItemMeta(rawcoppermeta);
        player.getInventory().setItem(9, rawcopper);

        // Setze die restlichen Inventargegenstände
        setPlayerInventoryItems(player);
    }

    // Methode zum Setzen der restlichen Inventargegenstände
    public void setPlayerInventoryItems(Player player) {
        // Define items
        ItemStack[] items = new ItemStack[13];
        String[] materialNames = {
                "RAW_COPPER", "COPPER_INGOT", "RAW_GOLD", "GOLD_INGOT",
                "RAW_IRON", "IRON_INGOT", "COAL", "COBBLESTONE",
                "STONE", "DIAMOND_ORE", "DIAMOND", "NETHERITE_INGOT",
                "NETHERITE_SCRAP"
        };
        Material[] materials = {
                Material.RAW_COPPER, Material.COPPER_INGOT, Material.RAW_GOLD, Material.GOLD_INGOT,
                Material.RAW_IRON, Material.IRON_INGOT, Material.COAL, Material.COBBLESTONE,
                Material.STONE, Material.DIAMOND_ORE, Material.DIAMOND, Material.NETHERITE_INGOT,
                Material.NETHERITE_SCRAP
        };

        // Set items
        for (int i = 0; i < items.length; i++) {
            items[i] = new ItemStack(materials[i]);
            ItemMeta itemMeta = items[i].getItemMeta();
            String itemName = materialNames[i].toLowerCase();
            itemMeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), itemName));
            itemMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            items[i].setItemMeta(itemMeta);
        }

        // Set inventory slots
        int[] slots = {9, 10, 18, 19, 27, 28, 22, 17, 16, 26, 25, 34, 35};
        for (int i = 0; i < items.length; i++) {
            player.getInventory().setItem(slots[i], items[i]);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(ChatColor.RED + "[-]" + " " + ChatColor.GRAY + player.getDisplayName());
        String playerName = event.getPlayer().getName();
        String message = "Hat das Spiel verlassen!";
        String fullMessage = playerName + ": " + message;
        DungeonCrusher.getInstance().sendToDiscord(fullMessage, 16711680);
    }

}


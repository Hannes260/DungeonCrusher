package net.dbsgameplay.dungeoncrusher.listener;


import net.dbsgameplay.dungeoncrusher.Commands.LevelSystem.SwordUpgradeClickListener;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.Locale;
import java.util.UUID;

public class Joinlistener implements Listener {
    private MYSQLManager mysqlManager;
    private LocationConfigManager locationConfigManager;
    private final ScoreboardBuilder scoreboardBuilder;
    public final DungeonCrusher dungeonCrusher;

    public Joinlistener(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager, LocationConfigManager locationConfigManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.mysqlManager = mysqlManager;
        this.scoreboardBuilder = new ScoreboardBuilder(dungeonCrusher);
        this.locationConfigManager = locationConfigManager;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        event.setJoinMessage(ChatColor.GREEN + "[+]"+ " "+ ChatColor.GRAY + player.getDisplayName());
        player.teleport(locationConfigManager.getSpawn());

        if(!player.hasPlayedBefore()) {
            Bukkit.broadcastMessage(ConfigManager.getConfigMessage("message.firstjoin", "%player%", player.getName()));
            double startmoney;
            startmoney = 1000;
            String formattedMoney = String.format(Locale.ENGLISH , "%,.2f", startmoney);
            mysqlManager.updateBalance(String.valueOf(player.getUniqueId()), formattedMoney);
            mysqlManager.updateSwordLevel(String.valueOf(player.getUniqueId()), 1);
            scoreboardBuilder.updateMoney(player);

            ItemStack woodensword = new ItemStack(Material.WOODEN_SWORD);
            double damage = 4.0;
            int level = 1;
            AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
            ItemMeta woodenmeta = woodensword.getItemMeta();
            woodenmeta.setDisplayName("§7<<§6Holzschwert §7- §aLv."+ level + "§7>>");
            woodenmeta.setLore(Collections.singletonList("§9"+ damage + " Angrifsschaden"));
            woodenmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            woodenmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
            woodenmeta.setUnbreakable(true);
            woodensword.setItemMeta(woodenmeta);
            player.getInventory().setItem(0, woodensword);

            ItemStack food = new ItemStack(Material.COOKED_BEEF);
            food.setAmount(64);
            player.getInventory().setItem(1, food);
        }

        SwordUpgradeClickListener swordUpgradeClickListener = new SwordUpgradeClickListener(dungeonCrusher, mysqlManager);
        int currentlevel = mysqlManager.getSwordLevel(player.getUniqueId().toString());
        swordUpgradeClickListener.giveSwordToPlayer(player, currentlevel);

        ItemStack glass = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta glassmeta = glass.getItemMeta();
        glassmeta.setDisplayName(" ");
        glass.setItemMeta(glassmeta);
        player.getInventory().setItem(11, glass);
        player.getInventory().setItem(12, glass);
        player.getInventory().setItem(13, glass);
        player.getInventory().setItem(14, glass);
        player.getInventory().setItem(15, glass);
        player.getInventory().setItem(20, glass);
        player.getInventory().setItem(21, glass);
        player.getInventory().setItem(23, glass);
        player.getInventory().setItem(24, glass);
        player.getInventory().setItem(29, glass);
        player.getInventory().setItem(30, glass);
        player.getInventory().setItem(31, glass);
        player.getInventory().setItem(32, glass);
        player.getInventory().setItem(33, glass);

        if (mysqlManager.canClaimDailyReward(player.getUniqueId().toString())) {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.canclaimdailyreward"));
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        }

        ItemStack navigator = new ItemStack(Material.ENDER_EYE);
        ItemMeta navigatormeta = navigator.getItemMeta();
        navigatormeta.setDisplayName("§8➡ §9Teleporter §8✖ §7Rechtsklick");
        navigator.setItemMeta(navigatormeta);
        player.getInventory().setItem(8, navigator);
        ItemStack rawcopper = new ItemStack(Material.RAW_COPPER);
        ItemMeta rawcoppermeta = rawcopper.getItemMeta();
        rawcoppermeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "raw_copper"));
        rawcoppermeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        rawcoppermeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        rawcopper.setItemMeta(rawcoppermeta);
        player.getInventory().setItem(9, rawcopper);

        setPlayerInventoryItems(player);
    }
    public void setPlayerInventoryItems(Player player) {
        // Define items
        ItemStack[] items = new ItemStack[12];
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
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        event.setQuitMessage(ChatColor.RED + "[-]"+ " "+ ChatColor.GRAY + player.getDisplayName());

    }
}

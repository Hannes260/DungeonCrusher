package net.dbsgameplay.dungeoncrusher.listener;


import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.LocationConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Locale;

public class Joinlistener implements Listener {
    private MYSQLManager mysqlManager;
    private LocationConfigManager locationConfigManager;
    public final DungeonCrusher dungeonCrusher;

    public Joinlistener(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager, LocationConfigManager locationConfigManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.mysqlManager = mysqlManager;
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
            startmoney = 2500;
            String formattedMoney = String.format(Locale.ENGLISH , "%,.2f", startmoney);
            mysqlManager.updateBalance(String.valueOf(player.getUniqueId()), formattedMoney);
            mysqlManager.updateSwordLevel(String.valueOf(player.getUniqueId()), 1);
            ItemStack sword = new ItemStack(Material.WOODEN_SWORD);
            ItemMeta swordmeta = sword.getItemMeta();
            swordmeta.setUnbreakable(true);
            sword.setItemMeta(swordmeta);
            player.getInventory().setItem(0, sword);

            ItemStack food = new ItemStack(Material.COOKED_BEEF);
            food.setAmount(64);
            player.getInventory().setItem(1, food);
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

            ItemStack copperingot = new ItemStack(Material.COPPER_INGOT);
            ItemMeta copperingotmeta = copperingot.getItemMeta();
            copperingotmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "copper_ingot"));
        copperingotmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        copperingotmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            copperingot.setItemMeta(copperingotmeta);
            player.getInventory().setItem(10, copperingot);

            ItemStack rawgold = new ItemStack(Material.RAW_GOLD);
            ItemMeta rawgoldmeta = rawgold.getItemMeta();
            rawgoldmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "raw_gold"));
             rawgoldmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
            rawgoldmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            rawgold.setItemMeta(rawgoldmeta);
            player.getInventory().setItem(18, rawgold);

            ItemStack goldingot = new ItemStack(Material.GOLD_INGOT);
            ItemMeta goldingotmeta = goldingot.getItemMeta();
            goldingotmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "gold_ingot"));
        goldingotmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        goldingotmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            goldingot.setItemMeta(goldingotmeta);
            player.getInventory().setItem(19, goldingot);

            ItemStack rawiron = new ItemStack(Material.RAW_IRON);
            ItemMeta rawironmeta = rawiron.getItemMeta();
            rawironmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "raw_iron"));
        rawironmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        rawironmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            rawiron.setItemMeta(rawironmeta);
            player.getInventory().setItem(27, rawiron);

            ItemStack ironingot = new ItemStack(Material.IRON_INGOT);
            ItemMeta ironingotmeta = ironingot.getItemMeta();
            ironingotmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "iron_ingot"));
        ironingotmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        ironingotmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            ironingot.setItemMeta(ironingotmeta);
            player.getInventory().setItem(28, ironingot);

            ItemStack coal = new ItemStack(Material.COAL);
            ItemMeta coalmeta = coal.getItemMeta();
            coalmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "coal"));
            coalmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
            coalmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            coal.setItemMeta(coalmeta);
            player.getInventory().setItem(22, coal);

            ItemStack cobblestone = new ItemStack(Material.COBBLESTONE);
            ItemMeta cobblestonemeta = cobblestone.getItemMeta();
            cobblestonemeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "cobblestone"));
            cobblestonemeta.addEnchant(Enchantment.KNOCKBACK,1,true);
            cobblestonemeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            cobblestone.setItemMeta(cobblestonemeta);
            player.getInventory().setItem(17, cobblestone);

            ItemStack stone = new ItemStack(Material.STONE);
            ItemMeta stonemeta = stone.getItemMeta();
            stonemeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "stone"));
            stonemeta.addEnchant(Enchantment.KNOCKBACK,1,true);
            stonemeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            stone.setItemMeta(stonemeta);
            player.getInventory().setItem(16, stone);

            ItemStack diamondore = new ItemStack(Material.DIAMOND_ORE);
            ItemMeta diamondoremeta = diamondore.getItemMeta();
            diamondoremeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "diamond_ore"));
        diamondoremeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        diamondoremeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            diamondore.setItemMeta(diamondoremeta);
            player.getInventory().setItem(26, diamondore);

            ItemStack diamond = new ItemStack(Material.DIAMOND);
            ItemMeta diamondmeta = diamond.getItemMeta();
            diamondmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "diamond"));
        diamondmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        diamondmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            diamond.setItemMeta(diamondmeta);
            player.getInventory().setItem(25, diamond);

            ItemStack netherite = new ItemStack(Material.NETHERITE_INGOT);
            ItemMeta netheritemeta = netherite.getItemMeta();
            netheritemeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "netherite_ingot"));
        netheritemeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        netheritemeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            netherite.setItemMeta(netheritemeta);
            player.getInventory().setItem(34, netherite);

            ItemStack netheritescrap = new ItemStack(Material.NETHERITE_SCRAP);
            ItemMeta netheritescrapmeta = netheritescrap.getItemMeta();
            netheritescrapmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "netherite_scrap"));
        netheritescrapmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        netheritescrapmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            netheritescrap.setItemMeta(netheritescrapmeta);
            player.getInventory().setItem(35, netheritescrap);




    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        event.setQuitMessage(ChatColor.RED + "[-]"+ " "+ ChatColor.GRAY + player.getDisplayName());


    }
}

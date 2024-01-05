package de.hannezhd.dungeoncrusher.listener;


import de.hannezhd.dungeoncrusher.DungeonCrusher;
import de.hannezhd.dungeoncrusher.sql.MYSQLManager;
import de.hannezhd.dungeoncrusher.utils.ConfigManager;
import de.hannezhd.dungeoncrusher.utils.LocationConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

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
            player.getInventory().setItem(0, sword);
            ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
            player.getInventory().setHelmet(helmet);
            mysqlManager.updateHelmLevel(String.valueOf(player.getUniqueId()),1);
            ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
            player.getInventory().setChestplate(chestplate);
            mysqlManager.updateChestplateLevel(String.valueOf(player.getUniqueId()),1);
            ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
            player.getInventory().setLeggings(leggings);
            mysqlManager.updateLeggingsLevel(String.valueOf(player.getUniqueId()),1);
            ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
            player.getInventory().setBoots(boots);
            mysqlManager.updateBootsLevel(String.valueOf(player.getUniqueId()), 1);
        }

    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        event.setQuitMessage(ChatColor.RED + "[-]"+ " "+ ChatColor.GRAY + player.getDisplayName());


    }
}

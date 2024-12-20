package net.dbsgameplay.dungeoncrusher.listener.protections;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class DungeonProtectionListener implements Listener {
    private static Set<UUID> buildModePlayers = new HashSet<>();

    public void setBuildMode(Player player, boolean enabled) {
        UUID playerId = player.getUniqueId();
        if (enabled) {
            buildModePlayers.add(playerId);
        } else {
            buildModePlayers.remove(playerId);
        }
    }

    public boolean isBuildModeEnabled(Player player) {
        return buildModePlayers.contains(player.getUniqueId());
    }
    @EventHandler
    public final void onEntityFarmBreak(EntityInteractEvent e) {
            if (e.getBlock().getType() == Material.FARMLAND) {
                e.getBlock().setType(Material.DIRT);

        } else {
            if (e.getBlock().getType() == Material.FARMLAND) {
                e.setCancelled(true);
            }

        }
    }
    @EventHandler
    public final void onFarmlandChange(BlockFadeEvent e) {
            if (e.getBlock().getType() == Material.FARMLAND) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getLocation().distanceSquared(e.getBlock().getLocation()) <= 25) {
                        if (!isBuildModeEnabled(player)) {
                            e.setCancelled(true);
                            break;
                        }
                    }
                }
            }
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!isBuildModeEnabled(player)) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() != null && event.getClickedInventory().equals(event.getWhoClicked().getInventory())) {
            if (!isBuildModeEnabled(player))
                event.setCancelled(true);
        }
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (!isBuildModeEnabled(player)) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if (!isBuildModeEnabled(player)) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (!isBuildModeEnabled(player)) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                Projectile projectile = null;

                if (event instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent entityDamageEvent = (EntityDamageByEntityEvent) event;

                    if (entityDamageEvent.getDamager() instanceof Projectile) {
                        projectile = (Projectile) entityDamageEvent.getDamager();
                    }
                }

                if (projectile != null && projectile.getShooter() instanceof Player) {
                    Player damager = (Player) projectile.getShooter();
                    Player damaged = (Player) event.getEntity();

                    if (!isBuildModeEnabled(damager) && !isBuildModeEnabled(damaged)) {
                        event.setCancelled(true);
                    }
                }
            } else if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                if (event instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent entityDamageEvent = (EntityDamageByEntityEvent) event;

                    if (entityDamageEvent.getDamager() instanceof Player) {
                        Player damager = (Player) entityDamageEvent.getDamager();
                        Player damaged = (Player) event.getEntity();

                        if (!isBuildModeEnabled(damager) && !isBuildModeEnabled(damaged)) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        event.blockList().clear();
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        event.setCancelled(true);
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!isBuildModeEnabled(player) && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerInteractWithArmorStand(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof ArmorStand) {
            Player player = event.getPlayer();
            // Überprüfen, ob der Spieler im Build-Modus ist
            if (!isBuildModeEnabled(player)) {
                event.setCancelled(true); // Verhindert die Interaktion// Optional: Nachricht an den Spieler
            }
        }
    }

    @EventHandler
    public void onArmorStandInteract(PlayerArmorStandManipulateEvent event) {
        Player player = event.getPlayer();
        if (!isBuildModeEnabled(player)) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerRide(EntityMountEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (!isBuildModeEnabled(player)) {
                event.setCancelled(true);
            }
        }
    }
}

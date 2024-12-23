package net.dbsgameplay.dungeoncrusher.listener.Enchantments;

import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EquipedEnchantmentsListener implements Listener {
    MYSQLManager mysqlManager;

    public EquipedEnchantmentsListener(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        checkAndApplyWindKlinge(player);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity && event.getDamager() instanceof Player) {
            final LivingEntity livingEntity = (LivingEntity) entity;
            Player player = (Player) event.getDamager();
            applyFesselschlag(player, livingEntity);
        }
    }

    @EventHandler
    public void onMainHand(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack newItem = player.getInventory().getItem(event.getNewSlot());
        ItemStack oldItem = player.getInventory().getItem(event.getPreviousSlot());

        if (newItem != null && isSword(newItem.getType())) {
            applyWindKlinge(player);
        } else if (oldItem != null && isSword(oldItem.getType())) {
            removeWindKlinge(player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        removeWindKlinge(player);
    }

    private boolean isSword(Material material) {
        return material == Material.WOODEN_SWORD || material == Material.STONE_SWORD ||
                material == Material.IRON_SWORD || material == Material.GOLDEN_SWORD ||
                material == Material.DIAMOND_SWORD || material == Material.NETHERITE_SWORD;
    }

    private void applyFesselschlag(Player player, LivingEntity entity) {
        String playerUUID = player.getUniqueId().toString();
        if (!mysqlManager.getEquipedEnchantment(playerUUID, "fesselschlag")) {
            return;
        }

        int level = mysqlManager.getlevelEnchantment(playerUUID, "fesselschlag");
        if (level > 0) {
            int duration = 3 * 20; // 3 seconds in ticks
            int amplifier = level - 1; // 0 for level 1, 1 for level 2, 2 for level 3
            int slownessPercentage = 15 * level; // 15% for level 1, 30% for level 2, 45% for level 3

            entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, duration, amplifier));
        }
    }

    private void applyWindKlinge(Player player) {
        String playerUUID = player.getUniqueId().toString();
        if (!mysqlManager.getEquipedEnchantment(playerUUID, "windklinge")) {
            return;
        }

        int level = mysqlManager.getlevelEnchantment(playerUUID, "windklinge");
        if (level > 0) {
            int duration = Integer.MAX_VALUE; // Effect duration
            int amplifier = level - 1; // 0 for level 1, 1 for level 2, etc.
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration, amplifier));
        }
    }

    private void removeWindKlinge(Player player) {
        player.removePotionEffect(PotionEffectType.SPEED);
    }

    public void checkAndApplyWindKlinge(Player player) {
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (itemInHand != null && isSword(itemInHand.getType())) {
            applyWindKlinge(player);
        }
    }
}
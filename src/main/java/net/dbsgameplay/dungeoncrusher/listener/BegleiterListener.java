package net.dbsgameplay.dungeoncrusher.listener;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Begleiter.BegleiterBuilder;
import net.dbsgameplay.dungeoncrusher.utils.ErfolgeBuilders;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class BegleiterListener implements Listener {
    public static MYSQLManager mysqlManager;
    public static DungeonCrusher dungeonCrusher;
    public static HashMap<UUID, String> statusHashmap = new HashMap<>();
    public static HashMap<UUID, LivingEntity> targetHashmap = new HashMap<>();


    public BegleiterListener(MYSQLManager mysqlManager, DungeonCrusher dungeonCrusher) {
        this.mysqlManager = mysqlManager;
        this.dungeonCrusher = dungeonCrusher;
    }


    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase("Begleitermenü") || e.getView().getTitle().equalsIgnoreCase("Begleiter Auswahl") || e.getView().getTitle().equalsIgnoreCase("Meine Begleiter") || e.getView().getTitle().equalsIgnoreCase("Levelupmenü")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;

            Player p = (Player) e.getWhoClicked();
            FileConfiguration cfg = dungeonCrusher.getConfig();

            if (e.getCurrentItem().equals(ErfolgeBuilders.createCustomMobHead("3ed1aba73f639f4bc42bd48196c715197be2712c3b962c97ebf9e9ed8efa025", "§cZurück"))) {
                BegleiterBuilder.openBegleiterMenü(p);
                e.setCancelled(true);
                return;
            }

            if (e.getView().getTitle().equalsIgnoreCase("Begleiter Auswahl")) {
                if (Integer.parseInt(e.getCurrentItem().getItemMeta().getItemName()) >= 10000 && Integer.parseInt(e.getCurrentItem().getItemMeta().getItemName()) <= 999999) {
                    if (mysqlManager.getBegleiterID(p.getUniqueId().toString()) != null) {
                        String itemname = e.getCurrentItem().getItemMeta().getItemName();
                        mysqlManager.updateBegleiterID(p.getUniqueId().toString(), null);

                        despawn_Begleiter(p, e.getCurrentItem().getItemMeta().getItemName());

                    }else {

                        String itemname = e.getCurrentItem().getItemMeta().getItemName();
                        mysqlManager.updateBegleiterID(p.getUniqueId().toString(), itemname);

                        spawn_Begleiter(p, e.getCurrentItem().getItemMeta().getItemName());
                    }
                    BegleiterBuilder.openBegleiterAuswahlMenü(p);
                    return;
                }
            }


            switch (e.getCurrentItem().getItemMeta().getItemName()) {

                case "begleiter_auswahl" :
                    BegleiterBuilder.openBegleiterAuswahlMenü(p);
                    break;

                case "levelup" :
                    BegleiterBuilder.openLevelupMenü(p);
                    break;

                case "meine_begleiter" :
                    BegleiterBuilder.openMeineBegleiterMenü(p);
                    break;
            }
        }
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        //only precess if player is moving
        if (e.getFrom().getX() == e.getTo().getX() && e.getFrom().getZ() == e.getTo().getZ()) {
            return;
        }

        //only process if player has a begleiter
        if (mysqlManager.getBegleiterID(p.getUniqueId().toString()) == null) {
            return;
        }

        //find the begleiter
        ArmorStand begleiter = findPlayerBegleiter(p);
        if (begleiter == null) {
            return;
        }

        switch (statusHashmap.getOrDefault(p.getUniqueId(), "following")) {
            case "following" :
                handeFollowing(p, begleiter, e);
                break;
            case "progressAttack" :
                handeProgressAttack(p, begleiter);
                break;
            case "attacking" :
                handeAttacking(p, begleiter);
                break;
        }
    }

    private ArmorStand findPlayerBegleiter(Player p) {
        return p.getWorld().getEntitiesByClass(ArmorStand.class).stream()
                .filter(entity -> entity.getCustomName() != null && entity.getCustomName().startsWith(p.getUniqueId().toString()))
                .findFirst()
                .orElse(null);
    }

    private void handeFollowing(Player p, ArmorStand begleiter, PlayerMoveEvent e) {
        //Update begleiter position and direction
        begleiter.teleport(p.getLocation());
        begleiter.teleport(begleiter.getLocation().setDirection(e.getTo().toVector().subtract(e.getFrom().toVector()).normalize()));

        //find potential target
        Optional<LivingEntity> target = findPotentialTarget(begleiter);
        if (target.isPresent()) {
            statusHashmap.put(p.getUniqueId(), "progressAttack");
            targetHashmap.put(p.getUniqueId(), target.get());
        }

    }

    private void handeProgressAttack(Player p, ArmorStand begleiter) {
        LivingEntity target = targetHashmap.get(p.getUniqueId());
        if (target == null) {
            statusHashmap.put(p.getUniqueId(), "following");
            return;
        }

        statusHashmap.put(p.getUniqueId(), "attacking");

        //move begleiter to target
        Vector directionToTarget = target.getLocation().toVector()
                .subtract(p.getLocation().toVector())
                .normalize();
        begleiter.setVelocity(directionToTarget);
        target.setAI(false);

        //Start attack sequence
        startAttackSequence(p, target);
    }

    private void handeAttacking(Player p, ArmorStand begleiter) {
        LivingEntity target = targetHashmap.get(p.getUniqueId());
        if (target == null || target.isDead()) {
            //return to player if target is dead
            Vector directionToPlayer = p.getLocation().toVector()
                    .subtract(begleiter.getLocation().toVector())
                    .normalize();
            begleiter.setVelocity(directionToPlayer);
            statusHashmap.put(p.getUniqueId(), "following");
            targetHashmap.remove(p.getUniqueId());
        }
    }

    private void startAttackSequence(Player p, LivingEntity target) {
        String begleiterID = mysqlManager.getBegleiterID(p.getUniqueId().toString());
        double damage = Double.parseDouble(mysqlManager.getBegleiterData(begleiterID, "damage"));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (target.isDead() || !target.isValid()) {
                    this.cancel();
                    return;
                }

                target.damage(damage);
                p.playSound(target.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1, 1);
            }
        }.runTaskTimer(dungeonCrusher, 0L, 20L*2);
    }

    private Optional<LivingEntity> findPotentialTarget(ArmorStand begleiter) {
        return begleiter.getNearbyEntities(5,5,5).stream()
                .filter(entity -> entity instanceof LivingEntity)
                .filter(entity -> !(entity instanceof Player || entity instanceof ArmorStand))
                .filter(entity -> !targetHashmap.containsKey(entity))
                .map(entity -> (LivingEntity) entity)
                .findFirst();
    }

    public static void spawn_Begleiter(Player p, String ID) {
        ArmorStand begleiter = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), org.bukkit.entity.EntityType.ARMOR_STAND);
        begleiter.setHelmet(ItemStack.of(Material.getMaterial(mysqlManager.getBegleiterData(ID, "material"))));
        begleiter.setVisible(false);
        begleiter.setGravity(true);
        begleiter.setCustomNameVisible(false);
        begleiter.setSmall(true);
        begleiter.setBasePlate(false);
        begleiter.setCustomName(p.getUniqueId() + " - " + ID);

        statusHashmap.put(p.getUniqueId(), "following");
    }

    public static void despawn_Begleiter(Player p, String ID) {
        p.getWorld().getEntitiesByClass(ArmorStand.class).stream()
                .filter(entity -> entity.getCustomName() != null && entity.getCustomName().startsWith(p.getUniqueId().toString()))
                .forEach(Entity::remove);

        targetHashmap.remove(p.getUniqueId());
        statusHashmap.remove(p.getUniqueId());
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        if (mysqlManager.getBegleiterID(p.getUniqueId().toString()) != null) {
            despawn_Begleiter(p, mysqlManager.getBegleiterID(p.getUniqueId().toString()));
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (mysqlManager.getBegleiterID(p.getUniqueId().toString()) != null) {
            String ID = mysqlManager.getBegleiterID(p.getUniqueId().toString());
            spawn_Begleiter(p, ID);
        }
    }
}

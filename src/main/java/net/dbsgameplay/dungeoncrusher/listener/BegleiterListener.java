package net.dbsgameplay.dungeoncrusher.listener;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Begleiter.BegleiterBuilder;
import net.dbsgameplay.dungeoncrusher.utils.ErfolgeBuilders;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class BegleiterListener implements Listener {
    public static MYSQLManager mysqlManager;
    DungeonCrusher dungeonCrusher;

    public BegleiterListener(MYSQLManager mysqlManager, DungeonCrusher dungeonCrusher) {
        this.mysqlManager = mysqlManager;
        this.dungeonCrusher = dungeonCrusher;
    }


    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        if (e.getInventory().getHolder() == null) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;

            Player p = (Player) e.getWhoClicked();
            FileConfiguration cfg = dungeonCrusher.getConfig();

            if (e.getCurrentItem().equals(ErfolgeBuilders.createCustomMobHead("3ed1aba73f639f4bc42bd48196c715197be2712c3b962c97ebf9e9ed8efa025", "§cZurück"))) {
                BegleiterBuilder.openBegleiterMenü(p);
                return;
            }

            if (e.getView().getTitle().equalsIgnoreCase("Begleiter Auswahl")) {
                if (Integer.parseInt(e.getCurrentItem().getItemMeta().getItemName()) >= 10000 && Integer.parseInt(e.getCurrentItem().getItemMeta().getItemName()) <= 999999) {
                    if (cfg.contains("begleiter_ausgewählt." + p.getUniqueId() + "." + e.getCurrentItem().getItemMeta().getItemName())) {
                        String itemname = e.getCurrentItem().getItemMeta().getItemName();
                        cfg.set("begleiter_ausgewählt." + p.getUniqueId(), null);
                        dungeonCrusher.saveConfig();

                        despawn_Begleiter(p, e.getCurrentItem().getItemMeta().getItemName());

                    }else {
                        if (cfg.isConfigurationSection("begleiter_ausgewählt." + p.getUniqueId())) {
                            if (cfg.getConfigurationSection("begleiter_ausgewählt." + p.getUniqueId()).getKeys(false).size() == 1) {
                                p.sendMessage("     §cDu hast bereits einen Begleiter ausgerüstet!");
                                return;
                            }
                        }

                        String itemname = e.getCurrentItem().getItemMeta().getItemName();
                        cfg.set("begleiter_ausgewählt." + p.getUniqueId() + "." + itemname, "");
                        dungeonCrusher.saveConfig();

                        spawn_Begleiter(p, e.getCurrentItem().getType(), e.getCurrentItem().getItemMeta().getItemName());
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

    public static void spawn_Begleiter(Player p, Material mat, String ID) {
        ArmorStand begleiter = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), org.bukkit.entity.EntityType.ARMOR_STAND);
        begleiter.setHelmet(ItemStack.of(mat));
        begleiter.setVisible(false);
        begleiter.setGravity(true);
        begleiter.setCustomNameVisible(false);
        begleiter.setSmall(true);
        begleiter.setBasePlate(false);
        begleiter.setCustomName(p.getUniqueId() + " - " + ID);
    }

    public static void despawn_Begleiter(Player p, String ID) {
        for (Entity entity : p.getWorld().getEntitiesByClass(ArmorStand.class)) {
            if (entity.getCustomName() == null) continue;

            if (entity.getCustomName().equalsIgnoreCase(p.getUniqueId() + " - " + ID)) {
                entity.remove();
            }
        }
    }


//    boolean isattacking = false;
//    boolean attackInProgress = false;
//    LivingEntity entity_target = null;
//
//    public static List<Entity> begleiterliste = new ArrayList<>();
//
//    @EventHandler
//    public void PlayerMoveEvent(PlayerMoveEvent e) {
//        Player p = e.getPlayer();
//
//            for (Entity entity : p.getWorld().getEntities()) {
//                if (entity instanceof ArmorStand armorStand) {
//                    if (begleiterliste.contains(armorStand)) {
//                        //Choose entity target
//                        if (!armorStand.getNearbyEntities(5,5,5).isEmpty() && !isattacking) {
//                            List<Entity> entityList = armorStand.getNearbyEntities(5,5,5);
//
//                            while (!(entityList.getFirst() instanceof LivingEntity) || (entityList.getFirst() instanceof Player) || (entityList.getFirst() instanceof ArmorStand)) {
//                                entityList.remove(entityList.getFirst());
//                                if (entityList.isEmpty()) break;
//                            }
//
//                            if (entityList.isEmpty()) {
//                                isattacking = false;
//                            }else {
//                                isattacking = true;
//                                entity_target = (LivingEntity) entityList.getFirst();
//                            }
//
//                        }
//
//                        //Deside to attack or follow
//                        if (isattacking && !attackInProgress) {
//                            p.sendMessage("2");
//                            isattacking = false;
//                            attackInProgress = true;
//                            Vector direction = entity_target.getLocation().toVector().subtract(armorStand.getLocation().toVector());
//
//                            direction.normalize();
//                            armorStand.setVelocity(direction);
//
//                            entity_target.setAI(false);
//
//                            new BukkitRunnable() {
//                                @Override
//                                public void run() {
//                                    if (entity_target.isDead()) {
//                                        p.sendMessage("1");
//                                        Vector direction2 = p.getLocation().toVector().subtract(armorStand.getLocation().toVector());
//
//                                        direction2.normalize();
//                                        armorStand.setVelocity(direction2);
//                                        attackInProgress = false;
//                                        this.cancel();
//                                    }
//
//                                    entity_target.damage(2.5);
//                                    p.playSound(armorStand.getLocation(), Sound.BLOCK_STONE_HIT, 10, 1);
//                                }
//                            }.runTaskTimer(dungeonCrusher, 0,20L*2);
//
//                        } else if (!isattacking && !attackInProgress){
//                            p.sendMessage("3");
//                            armorStand.teleport(p.getLocation().add(1,0,-1));
//                        }
//
//                    }
//                }
//            }
//    }
//
//    public void PlayerQuitEvent(PlayerQuitEvent e) {
//        Player p = e.getPlayer();
//
//        for (Entity entity : begleiterliste) {
//            entity.remove();
//        }
//    }
}

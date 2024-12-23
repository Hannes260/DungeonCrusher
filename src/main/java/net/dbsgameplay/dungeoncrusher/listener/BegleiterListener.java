package net.dbsgameplay.dungeoncrusher.listener;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Begleiter.BegleiterBuilder;
import net.dbsgameplay.dungeoncrusher.utils.ErfolgeBuilders;
import net.dbsgameplay.dungeoncrusher.utils.quests.Daily;
import net.dbsgameplay.dungeoncrusher.utils.quests.Monthly;
import net.dbsgameplay.dungeoncrusher.utils.quests.Weekly;
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

import java.util.*;

public class BegleiterListener implements Listener {
    public static MYSQLManager mysqlManager;
    public static DungeonCrusher dungeonCrusher;

    public static HashMap<UUID, String> begleiterMap = new HashMap<>();

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
                        begleiterMap.remove(p.getUniqueId());

                    }else {
//                        if (mysqlManager.getBegleiterID(p.getUniqueId().toString())) {
//                            p.sendMessage("§cDu hast bereits einen Begleiter ausgerüstet!");
//                            return;
//                        }

                        String itemname = e.getCurrentItem().getItemMeta().getItemName();
                        mysqlManager.updateBegleiterID(p.getUniqueId().toString(), itemname);

                        spawn_Begleiter(p, e.getCurrentItem().getType(), e.getCurrentItem().getItemMeta().getItemName());
                        begleiterMap.put(p.getUniqueId(), e.getCurrentItem().getItemMeta().getItemName());
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

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent e) {
        if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ()) {
            if (begleiterMap.containsKey(e.getPlayer().getUniqueId())) {
                Player p = e.getPlayer();

                for (Entity entity : p.getWorld().getEntitiesByClass(ArmorStand.class)) {
                    if (entity.getCustomName() == null) continue;

                    if (entity.getCustomName().startsWith(p.getUniqueId().toString())) {
                        entity.teleport(p.getLocation());

                        Vector differenz = e.getTo().toVector().subtract(e.getFrom().toVector());
                        entity.getLocation().setDirection(differenz);
                    }
                }
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

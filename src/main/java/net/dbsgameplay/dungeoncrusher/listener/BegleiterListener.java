package net.dbsgameplay.dungeoncrusher.listener;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class BegleiterListener implements Listener {
    public static MYSQLManager mysqlManager;
    DungeonCrusher dungeonCrusher;

    public BegleiterListener(MYSQLManager mysqlManager, DungeonCrusher dungeonCrusher) {
        this.mysqlManager = mysqlManager;
        this.dungeonCrusher = dungeonCrusher;
    }

    boolean isattacking = false;
    boolean attackInProgress = false;
    Entity entity_target = null;

    @EventHandler
    public void Playermovevent(PlayerMoveEvent e) {
        Player p = e.getPlayer();

            for (Entity entity : p.getWorld().getEntities()) {
                if (entity instanceof ArmorStand armorStand) {
                    if (armorStand.getCustomName() == null) continue;
                    if (armorStand.getCustomName().equals("text1")) {


                        if (armorStand.getNearbyEntities(5,5,5).isEmpty() && !isattacking) {
                            isattacking = false;
                        }else {
                            isattacking = true;
                            List<Entity> entityList = armorStand.getNearbyEntities(5,5,5);
                            while (!(entityList.getFirst() instanceof LivingEntity)) {
                                entityList.remove(entityList.getFirst());
                            }
                            entity_target = entityList.getFirst();
                        }

                        if (isattacking) {
                            isattacking = false;
                            attackInProgress = true;
                            Vector direction = entity_target.getLocation().toVector().subtract(armorStand.getLocation().toVector());

                            direction.normalize();
                            armorStand.setVelocity(direction);
                            armorStand.setVelocity(new Vector(0,0,0));

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    Vector direction2 = p.getLocation().toVector().subtract(armorStand.getLocation().toVector());

                                    direction2.normalize();
                                    armorStand.setVelocity(direction2);
                                    armorStand.setVelocity(new Vector(0,0,0));
                                    attackInProgress = false;
                                }
                            }.runTaskLater(dungeonCrusher, 20*2);
//                            entity_target.remove();



                        } else if (!isattacking && !attackInProgress){
                            armorStand.teleport(p.getLocation().add(1,0,-1));
                        }

                    }
                }
            }
    }
}

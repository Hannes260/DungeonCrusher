package net.dbsgameplay.dungeoncrusher.utils;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class HologramManager {

    public static void spawnItemHologram(Location location, String itemName) {
        // Zufällige Offset-Position für das Hologramm
        double yOffset = ThreadLocalRandom.current().nextDouble(0.0, 1.5);

        Location hologramLocation = location.clone().add(0.5, yOffset + -2.5, 0.5);

        // Erstelle ArmorStand
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(hologramLocation, org.bukkit.entity.EntityType.ARMOR_STAND);

        // Konfiguriere ArmorStand
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName("§7[§a+§7] §6" + itemName);
        armorStand.setGravity(false);
        armorStand.setVisible(false);

        // Entferne das Hologramm nach einigen Sekunden
        Bukkit.getScheduler().runTaskLater(DungeonCrusher.getInstance(), () -> armorStand.remove(), 50);
    }
}


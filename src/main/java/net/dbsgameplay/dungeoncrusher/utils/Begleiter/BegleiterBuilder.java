package net.dbsgameplay.dungeoncrusher.utils.Begleiter;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class BegleiterBuilder {

    private Player player;
    private Location location;
    ArmorStand begleiter;
    private Material head = Material.ZOMBIE_HEAD;
    private String name = "";

    public BegleiterBuilder setLocation(Player player) {
        this.player = player;
        this.location = player.getLocation().clone().add(1, 0, -1);
        begleiter = (ArmorStand) player.getWorld().spawnEntity(location, org.bukkit.entity.EntityType.ARMOR_STAND);
        return this;
    }

    public BegleiterBuilder setSkin(Material skin) {
        this.head = skin;
        return this;
    }

    public BegleiterBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public void build() {
        begleiter.setHelmet(ItemStack.of(head));
        begleiter.setVisible(false);
        begleiter.setGravity(true);
        begleiter.setCustomNameVisible(true);
        begleiter.setSmall(true);
        begleiter.setBasePlate(false);
        begleiter.setCustomName(name);
    }
}

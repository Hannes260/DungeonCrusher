package net.dbsgameplay.dungeoncrusher.objects;

import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
public class PlayerHead {
    String skullowner;
    String DisplayName;
    List<String> lore;

    public PlayerHead(String skullowner, String displayName, String... lore) {
        this.skullowner = skullowner;
        this.DisplayName = displayName;
        this.lore = Arrays.asList(lore);
    }

    public ItemStack getItemStack() {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta)item.getItemMeta();
        meta.setOwner(this.skullowner);
        meta.setDisplayName(this.DisplayName);
        meta.setLore(this.lore);
        item.setItemMeta(meta);
        return item;
    }
}

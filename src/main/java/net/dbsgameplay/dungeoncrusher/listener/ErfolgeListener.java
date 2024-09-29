package net.dbsgameplay.dungeoncrusher.listener;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.ErfolgeBuilders;
import net.luckperms.api.node.types.SuffixNode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.UUID;

public class ErfolgeListener implements Listener {
    private LocationConfigManager locationConfigManager;
    private DungeonCrusher dungeonCrusher;

    public ErfolgeListener(DungeonCrusher dungeonCrusher, LocationConfigManager locationConfigManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.locationConfigManager = locationConfigManager;
    }

    public static HashMap<UUID, Integer> ebeneHashMap = new HashMap<UUID, Integer>();

    @EventHandler
    public void PlayerCloseInvEvent(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();

        if (e.getInventory().equals(ErfolgeBuilders.inv)) {
            ebeneHashMap.remove(p.getUniqueId());
        }
    }

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if  (e.getClickedInventory() == null) return;
        if  (!e.getClickedInventory().equals(ErfolgeBuilders.inv)) return;
        if  (e.getCurrentItem() == null) return;

        if (e.getCurrentItem().equals(ErfolgeBuilders.createCustomMobHead("3ed1aba73f639f4bc42bd48196c715197be2712c3b962c97ebf9e9ed8efa025", "§cSchließen"))) {
            p.closeInventory();
            return;
        }
        if (e.getCurrentItem().equals(ErfolgeBuilders.createCustomMobHead("ac9c67a9f1685cd1da43e841fe7ebb17f6af6ea12a7e1f2722f5e7f0898db9f3", "§7Nächste Seite"))) {
            if (ebeneHashMap.get(p.getUniqueId())+1 >= 57) {
                e.setCancelled(true);
                return;
            }
            ebeneHashMap.replace(p.getUniqueId(), ebeneHashMap.get(p.getUniqueId())+1);
            p.openInventory(ErfolgeBuilders.getInventory(ebeneHashMap.get(p.getUniqueId()), p));
            return;
        }
        if (e.getCurrentItem().equals(ErfolgeBuilders.createCustomMobHead("1a1ef398a17f1af7477014517f7f141d886df41a32c738cc8a83fb50297bd921", "§7Vorherige Seite"))) {
            if (ebeneHashMap.get(p.getUniqueId())-1 <= 0) {
                e.setCancelled(true);
                return;
            }
            ebeneHashMap.replace(p.getUniqueId(), ebeneHashMap.get(p.getUniqueId())-1);
            p.openInventory(ErfolgeBuilders.getInventory(ebeneHashMap.get(p.getUniqueId()), p));
            return;
        }

        if (e.getCurrentItem().getType().equals(Material.NAME_TAG)) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().endsWith("✅")) {

                if  (e.getCurrentItem().getItemMeta().getEnchantmentGlintOverride()) {
                    p.sendMessage("§6Du hast deinen Titel entfernt.");
                    p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 10, 1);
                    ItemMeta meta = e.getCurrentItem().getItemMeta();
                    meta.setEnchantmentGlintOverride(false);
                    e.getCurrentItem().setItemMeta(meta);
                    removeSuffix(p);
                    e.setCancelled(true);
                    return;
                }

                for (ItemStack items : e.getClickedInventory().getContents()) {
                    if (items == null) continue;

                    if (items.getItemMeta().hasEnchantmentGlintOverride()) {
                        ItemMeta meta = items.getItemMeta();
                        meta.setEnchantmentGlintOverride(false);
                        items.setItemMeta(meta);
                    }
                }

                ItemMeta meta = e.getCurrentItem().getItemMeta();
                meta.setEnchantmentGlintOverride(true);
                e.getCurrentItem().setItemMeta(meta);

                String itemName = e.getCurrentItem().getItemMeta().getItemName();

                p.sendMessage("§6Du hast deinen Titel auf " +ErfolgeBuilders.titlesHashmap.get(itemName) + " §6gewechselt!");
                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 10, 1);

                setSuffix(itemName, p);

                Inventory inventory = ErfolgeBuilders.getInventory(locationConfigManager.getEbene(p), p);

                if (inventory == null) {
                    inventory = ErfolgeBuilders.getInventory(1, p);
                }

                p.openInventory(inventory);
            }

        }

        e.setCancelled(true);
    }

    public void setSuffix(String suffix, Player player) {
        // Load, modify, then save
        DungeonCrusher.api.getUserManager().modifyUser(player.getUniqueId(), user -> {
            // Add the permission
            for (String s : user.getCachedData().getMetaData().getSuffixes().values()) {
                user.data().remove(SuffixNode.builder(s, 150).build());
            }
            user.data().add(SuffixNode.builder(" §7[§e" +ErfolgeBuilders.titlesHashmap.get(suffix) + "§7]", 150).build());
        });
    }

    public static void removeSuffix(Player player) {
        // Load, modify, then save
        DungeonCrusher.api.getUserManager().modifyUser(player.getUniqueId(), user -> {
            // Add the permission
            for (String s : user.getCachedData().getMetaData().getSuffixes().values()) {
                user.data().remove(SuffixNode.builder(s, 150).build());
            }
        });
    }
}


package net.dbsgameplay.dungeoncrusher.listener;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.ErfolgeBuilders;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.SuffixNode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.naming.spi.DirObjectFactory;
import java.util.HashMap;
import java.util.UUID;

public class ErfolgeListener implements Listener {
    private LocationConfigManager locationConfigManager;
    private DungeonCrusher dungeonCrusher;

    public ErfolgeListener(DungeonCrusher dungeonCrusher, LocationConfigManager locationConfigManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.locationConfigManager = locationConfigManager;
    }

    public static HashMap<UUID, Integer> ebeneHashMap = new HashMap<>();

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

        if (e.getClick() == ClickType.NUMBER_KEY) {
            e.setCancelled(true);
            return;
        }

        if  (e.getClickedInventory() == null) return;
        if  (!e.getClickedInventory().equals(ErfolgeBuilders.inv)) return;
        if  (e.getCurrentItem() == null) return;

        if (e.getCurrentItem().equals(ErfolgeBuilders.createCustomMobHead("3ed1aba73f639f4bc42bd48196c715197be2712c3b962c97ebf9e9ed8efa025", "§cSchließen"))) {
            p.closeInventory();
            e.setCancelled(true);
            return;
        }
        if (e.getCurrentItem().equals(ErfolgeBuilders.createCustomMobHead("ac9c67a9f1685cd1da43e841fe7ebb17f6af6ea12a7e1f2722f5e7f0898db9f3", "§7Nächste Seite"))) {
            if (ebeneHashMap.get(p.getUniqueId())+1 >= 2) {
                e.setCancelled(true);
                return;
            }
            ebeneHashMap.replace(p.getUniqueId(), ebeneHashMap.get(p.getUniqueId())+1);
            if (ebeneHashMap.get(p.getUniqueId()) == 1) {
                p.openInventory(ErfolgeBuilders.getInventory(p, 1));
            }else if (ebeneHashMap.get(p.getUniqueId()) == 0) {
                p.openInventory(ErfolgeBuilders.getInventory(p, 0));
            }
            e.setCancelled(true);
            return;
        }
        if (e.getCurrentItem().equals(ErfolgeBuilders.createCustomMobHead("1a1ef398a17f1af7477014517f7f141d886df41a32c738cc8a83fb50297bd921", "§7Vorherige Seite"))) {
            if (ebeneHashMap.get(p.getUniqueId())-1 <= -2) {
                e.setCancelled(true);
                return;
            }
            ebeneHashMap.replace(p.getUniqueId(), ebeneHashMap.get(p.getUniqueId())-1);
            if (ebeneHashMap.get(p.getUniqueId()) == -1) {
                p.openInventory(ErfolgeBuilders.getInventory(p, -1));
            }else if (ebeneHashMap.get(p.getUniqueId()) == 0) {
                p.openInventory(ErfolgeBuilders.getInventory(p, 0));
            }
            e.setCancelled(true);
            return;
        }
        if (!e.getClick().isRightClick()) {e.setCancelled(true); return;}
        if (e.getCurrentItem().getType() == Material.GRAY_DYE || e.getCurrentItem().getType() == Material.YELLOW_DYE || e.getCurrentItem().getType() == Material.ORANGE_DYE || e.getCurrentItem().getType() == Material.GREEN_DYE || e.getCurrentItem().getType() == Material.LIME_DYE) {
            ErfolgeBuilders.openTitleMenü(p, e.getCurrentItem().getItemMeta().getItemName());
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void InventoryClickEvent2(InventoryClickEvent e) {
        if  (e.getClickedInventory() == null) return;
        if  (!e.getClickedInventory().equals(ErfolgeBuilders.inventory)) return;
        if  (e.getCurrentItem() == null) return;

        Player p = (Player) e.getWhoClicked();
        User user = DungeonCrusher.api.getUserManager().getUser(p.getUniqueId());

        if (e.getCurrentItem().equals(ErfolgeBuilders.createCustomMobHead("3ed1aba73f639f4bc42bd48196c715197be2712c3b962c97ebf9e9ed8efa025", "§cZurück"))) {
            p.openInventory(ErfolgeBuilders.getInventory(p, 0));
            //geht ned das untere
            if (ebeneHashMap.containsKey(p.getUniqueId())) {
                ebeneHashMap.remove(p.getUniqueId());
            }
            ebeneHashMap.put(p.getUniqueId(), 0);
            e.setCancelled(true);
            return;
        }

        if (e.getCurrentItem().getType().equals(Material.NAME_TAG)) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().endsWith("✅")) {
                ItemStack currentItem = e.getCurrentItem();
                ItemMeta itemMeta = currentItem.getItemMeta();

                String itemName = itemMeta.getItemName();

                if (user.getCachedData().getMetaData().getSuffix() != null) {
                    String rawSuffix =user.getCachedData().getMetaData().getSuffix();
                    String suffix = user.getCachedData().getMetaData().getSuffix().substring(0, rawSuffix.length() -3 ).substring(6);

                    if (suffix.equalsIgnoreCase(ErfolgeBuilders.titlesHashmap.get(itemName))) {
                        removeSuffix(p);

                        itemMeta.removeEnchant(Enchantment.KNOCKBACK);

                        p.sendMessage("§6Du hast deinen Titel entfernt.");
                        p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 10, 1);

                        currentItem.setItemMeta(itemMeta);
                        e.setCancelled(true);
                        return;
                    }
                }

                setSuffix(itemName, p);

                for (ItemStack item : e.getClickedInventory().getContents()) {
                    if (item != null) {
                        if (item.containsEnchantment(Enchantment.KNOCKBACK)) {
                            ItemMeta itemMeta1 = item.getItemMeta();
                            itemMeta1.removeEnchant(Enchantment.KNOCKBACK);
                            item.setItemMeta(itemMeta1);
                        }
                    }
                }

                itemMeta.addEnchant(Enchantment.KNOCKBACK,1,true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

                p.sendMessage("§6Du hast deinen Titel auf " +ErfolgeBuilders.titlesHashmap.get(itemName) + " §6gewechselt!");
                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 10, 1);

                currentItem.setItemMeta(itemMeta);
                e.setCancelled(true);
            }
        }
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


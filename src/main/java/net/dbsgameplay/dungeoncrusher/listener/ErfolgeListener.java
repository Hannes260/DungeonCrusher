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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        ebeneHashMap.put(p.getUniqueId(), locationConfigManager.getEbene(p));
    }

    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        ebeneHashMap.remove(p.getUniqueId());
    }

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (!e.getClickedInventory().equals(ErfolgeBuilders.inv)) return;
        if (e.getCurrentItem() == null) return;

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

                p.sendMessage("§6Du hast deinen Titel gewechselt.");
                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 10, 1);

                String itemName = e.getCurrentItem().getItemMeta().getItemName();

                switch (e.getCurrentItem().getItemMeta().getItemName()) {

                    case "Erfolg_Schaf_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schaf_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schaf_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schaf_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schaf_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schaf_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schaf_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schaf_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schaf_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schaf_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schaf_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schaf_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schaf_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schaf_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schaf_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schaf_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schaf_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schaf_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schaf_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schaf_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schweine_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schweine_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schweine_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schweine_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schweine_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schweine_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schweine_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schweine_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schweine_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schweine_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schweine_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schweine_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schweine_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schweine_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schweine_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schweine_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schweine_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schweine_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schweine_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schweine_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kuh_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kuh_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kuh_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kuh_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kuh_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kuh_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kuh_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kuh_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kuh_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kuh_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kuh_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kuh_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kuh_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kuh_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kuh_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kuh_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kuh_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kuh_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kuh_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kuh_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Pferd_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Pferd_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Pferd_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Pferd_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Pferd_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Pferd_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Pferd_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Pferd_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Pferd_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Pferd_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Pferd_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Pferd_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Pferd_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Pferd_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Pferd_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Pferd_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Pferd_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Pferd_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Pferd_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Pferd_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Esel_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Esel_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Esel_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Esel_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Esel_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Esel_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Esel_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Esel_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Esel_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Esel_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Esel_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Esel_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Esel_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Esel_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Esel_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Esel_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Esel_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Esel_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Esel_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Esel_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kamel_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kamel_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kamel_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kamel_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kamel_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kamel_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kamel_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kamel_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kamel_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kamel_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kamel_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kamel_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kamel_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kamel_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kamel_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kamel_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kamel_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kamel_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kamel_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kamel_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Dorfbewohner_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Dorfbewohner_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Dorfbewohner_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Dorfbewohner_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Dorfbewohner_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Dorfbewohner_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Dorfbewohner_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Dorfbewohner_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Dorfbewohner_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Dorfbewohner_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Dorfbewohner_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Dorfbewohner_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Dorfbewohner_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Dorfbewohner_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Dorfbewohner_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Dorfbewohner_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Dorfbewohner_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Dorfbewohner_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Dorfbewohner_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Dorfbewohner_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ziege_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ziege_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ziege_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ziege_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ziege_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ziege_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ziege_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ziege_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ziege_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ziege_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ziege_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ziege_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ziege_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ziege_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ziege_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ziege_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ziege_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ziege_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ziege_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ziege_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lama_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lama_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lama_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lama_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lama_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lama_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lama_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lama_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lama_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lama_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lama_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lama_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lama_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lama_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lama_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lama_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lama_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lama_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lama_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lama_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Mooshroom_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Mooshroom_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Mooshroom_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Mooshroom_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Mooshroom_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Mooshroom_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Mooshroom_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Mooshroom_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Mooshroom_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Mooshroom_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Mooshroom_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Mooshroom_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Mooshroom_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Mooshroom_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Mooshroom_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Mooshroom_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Mooshroom_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Mooshroom_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Mooshroom_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Mooshroom_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Maultier_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Maultier_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Maultier_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Maultier_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Maultier_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Maultier_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Maultier_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Maultier_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Maultier_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Maultier_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Maultier_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Maultier_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Maultier_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Maultier_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Maultier_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Maultier_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Maultier_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Maultier_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Maultier_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Maultier_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schnüffler_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schnüffler_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schnüffler_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schnüffler_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schnüffler_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schnüffler_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schnüffler_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schnüffler_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schnüffler_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schnüffler_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schnüffler_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schnüffler_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schnüffler_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schnüffler_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schnüffler_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schnüffler_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schnüffler_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schnüffler_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schnüffler_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schnüffler_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Panda_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Panda_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Panda_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Panda_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Panda_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Panda_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Panda_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Panda_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Panda_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Panda_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Panda_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Panda_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Panda_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Panda_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Panda_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Panda_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Panda_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Panda_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Panda_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Panda_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schildkröten_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schildkröten_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schildkröten_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schildkröten_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schildkröten_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schildkröten_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schildkröten_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schildkröten_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schildkröten_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schildkröten_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schildkröten_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schildkröten_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schildkröten_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schildkröten_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schildkröten_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schildkröten_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schildkröten_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schildkröten_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schildkröten_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schildkröten_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ozelot_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ozelot_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ozelot_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ozelot_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ozelot_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ozelot_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ozelot_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ozelot_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ozelot_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ozelot_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ozelot_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ozelot_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ozelot_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ozelot_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ozelot_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ozelot_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ozelot_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ozelot_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ozelot_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ozelot_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Axolotl_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Axolotl_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Axolotl_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Axolotl_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Axolotl_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Axolotl_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Axolotl_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Axolotl_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Axolotl_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Axolotl_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Axolotl_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Axolotl_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Axolotl_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Axolotl_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Axolotl_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Axolotl_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Axolotl_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Axolotl_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Axolotl_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Axolotl_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Fuchs_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Fuchs_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Fuchs_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Fuchs_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Fuchs_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Fuchs_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Fuchs_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Fuchs_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Fuchs_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Fuchs_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Fuchs_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Fuchs_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Fuchs_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Fuchs_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Fuchs_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Fuchs_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Fuchs_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Fuchs_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Fuchs_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Fuchs_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Katzen_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Katzen_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Katzen_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Katzen_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Katzen_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Katzen_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Katzen_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Katzen_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Katzen_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Katzen_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Katzen_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Katzen_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Katzen_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Katzen_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Katzen_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Katzen_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Katzen_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Katzen_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Katzen_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Katzen_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Huhn_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Huhn_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Huhn_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Huhn_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Huhn_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Huhn_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Huhn_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Huhn_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Huhn_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Huhn_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Huhn_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Huhn_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Huhn_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Huhn_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Huhn_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Huhn_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Huhn_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Huhn_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Huhn_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Huhn_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Frosch_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Frosch_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Frosch_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Frosch_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Frosch_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Frosch_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Frosch_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Frosch_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Frosch_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Frosch_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Frosch_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Frosch_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Frosch_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Frosch_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Frosch_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Frosch_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Frosch_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Frosch_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Frosch_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Frosch_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kaninchen_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kaninchen_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kaninchen_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kaninchen_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kaninchen_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kaninchen_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kaninchen_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kaninchen_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kaninchen_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kaninchen_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kaninchen_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kaninchen_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kaninchen_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kaninchen_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kaninchen_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kaninchen_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kaninchen_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kaninchen_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kaninchen_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Kaninchen_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Silberfisch_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Silberfisch_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Silberfisch_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Silberfisch_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Silberfisch_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Silberfisch_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Silberfisch_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Silberfisch_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Silberfisch_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Silberfisch_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Silberfisch_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Silberfisch_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Silberfisch_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Silberfisch_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Silberfisch_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Silberfisch_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Silberfisch_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Silberfisch_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Silberfisch_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Silberfisch_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Diener_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Diener_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Diener_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Diener_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Diener_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Diener_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Diener_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Diener_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Diener_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Diener_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Diener_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Diener_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Diener_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Diener_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Diener_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Diener_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Diener_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Diener_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Diener_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Diener_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisbären_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisbären_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisbären_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisbären_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisbären_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisbären_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisbären_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisbären_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisbären_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisbären_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisbären_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisbären_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisbären_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisbären_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisbären_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisbären_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisbären_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisbären_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisbären_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisbären_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiepferd_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiepferd_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiepferd_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiepferd_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiepferd_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiepferd_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiepferd_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiepferd_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiepferd_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiepferd_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiepferd_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiepferd_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiepferd_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiepferd_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiepferd_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiepferd_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiepferd_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiepferd_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiepferd_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiepferd_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wolf_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wolf_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wolf_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wolf_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wolf_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wolf_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wolf_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wolf_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wolf_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wolf_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wolf_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wolf_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wolf_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wolf_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wolf_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wolf_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wolf_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wolf_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wolf_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wolf_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiedorfbewohner_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiedorfbewohner_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiedorfbewohner_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiedorfbewohner_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiedorfbewohner_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiedorfbewohner_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiedorfbewohner_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiedorfbewohner_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiedorfbewohner_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiedorfbewohner_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiedorfbewohner_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiedorfbewohner_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiedorfbewohner_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiedorfbewohner_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiedorfbewohner_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiedorfbewohner_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiedorfbewohner_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiedorfbewohner_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiedorfbewohner_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiedorfbewohner_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schneegolem_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schneegolem_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schneegolem_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schneegolem_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schneegolem_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schneegolem_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schneegolem_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schneegolem_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schneegolem_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schneegolem_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schneegolem_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schneegolem_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schneegolem_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schneegolem_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schneegolem_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schneegolem_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schneegolem_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schneegolem_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schneegolem_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schneegolem_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelett_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelett_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelett_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelett_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelett_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelett_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelett_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelett_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelett_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelett_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelett_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelett_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelett_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelett_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelett_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelett_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelett_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelett_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelett_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelett_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ertrunkenen_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ertrunkenen_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ertrunkenen_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ertrunkenen_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ertrunkenen_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ertrunkenen_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ertrunkenen_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ertrunkenen_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ertrunkenen_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ertrunkenen_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ertrunkenen_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ertrunkenen_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ertrunkenen_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ertrunkenen_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ertrunkenen_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ertrunkenen_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ertrunkenen_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ertrunkenen_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ertrunkenen_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ertrunkenen_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wüstenzombie_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wüstenzombie_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wüstenzombie_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wüstenzombie_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wüstenzombie_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wüstenzombie_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wüstenzombie_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wüstenzombie_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wüstenzombie_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wüstenzombie_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wüstenzombie_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wüstenzombie_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wüstenzombie_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wüstenzombie_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wüstenzombie_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wüstenzombie_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wüstenzombie_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wüstenzombie_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wüstenzombie_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wüstenzombie_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Spinnen_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Spinnen_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Spinnen_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Spinnen_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Spinnen_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Spinnen_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Spinnen_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Spinnen_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Spinnen_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Spinnen_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Spinnen_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Spinnen_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Spinnen_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Spinnen_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Spinnen_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Spinnen_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Spinnen_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Spinnen_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Spinnen_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Spinnen_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombie_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombie_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombie_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombie_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombie_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombie_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombie_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombie_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombie_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombie_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombie_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombie_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombie_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombie_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombie_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombie_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombie_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombie_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombie_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombie_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eiswanderer_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eiswanderer_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eiswanderer_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eiswanderer_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eiswanderer_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eiswanderer_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eiswanderer_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eiswanderer_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eiswanderer_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eiswanderer_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eiswanderer_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eiswanderer_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eiswanderer_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eiswanderer_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eiswanderer_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eiswanderer_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eiswanderer_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eiswanderer_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eiswanderer_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eiswanderer_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Creeper_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Creeper_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Creeper_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Creeper_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Creeper_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Creeper_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Creeper_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Creeper_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Creeper_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Creeper_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Creeper_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Creeper_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Creeper_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Creeper_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Creeper_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Creeper_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Creeper_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Creeper_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Creeper_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Creeper_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Höhlenspinnen_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Höhlenspinnen_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Höhlenspinnen_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Höhlenspinnen_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Höhlenspinnen_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Höhlenspinnen_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Höhlenspinnen_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Höhlenspinnen_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Höhlenspinnen_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Höhlenspinnen_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Höhlenspinnen_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Höhlenspinnen_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Höhlenspinnen_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Höhlenspinnen_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Höhlenspinnen_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Höhlenspinnen_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Höhlenspinnen_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Höhlenspinnen_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Höhlenspinnen_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Höhlenspinnen_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Endermiten_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Endermiten_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Endermiten_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Endermiten_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Endermiten_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Endermiten_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Endermiten_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Endermiten_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Endermiten_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Endermiten_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Endermiten_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Endermiten_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Endermiten_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Endermiten_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Endermiten_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Endermiten_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Endermiten_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Endermiten_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Endermiten_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Endermiten_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schreiter_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schreiter_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schreiter_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schreiter_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schreiter_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schreiter_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schreiter_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schreiter_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schreiter_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schreiter_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schreiter_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schreiter_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schreiter_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schreiter_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schreiter_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schreiter_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schreiter_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schreiter_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schreiter_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schreiter_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lohen_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lohen_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lohen_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lohen_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lohen_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lohen_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lohen_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lohen_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lohen_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lohen_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lohen_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lohen_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lohen_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lohen_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lohen_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lohen_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lohen_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lohen_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lohen_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Lohen_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelletpferde_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelletpferde_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelletpferde_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelletpferde_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelletpferde_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelletpferde_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelletpferde_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelletpferde_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelletpferde_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelletpferde_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelletpferde_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelletpferde_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelletpferde_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelletpferde_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelletpferde_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelletpferde_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelletpferde_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelletpferde_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelletpferde_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Skelletpferde_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hexen_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hexen_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hexen_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hexen_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hexen_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hexen_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hexen_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hexen_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hexen_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hexen_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hexen_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hexen_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hexen_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hexen_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hexen_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hexen_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hexen_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hexen_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hexen_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hexen_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schleim_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schleim_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schleim_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schleim_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schleim_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schleim_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schleim_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schleim_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schleim_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schleim_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schleim_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schleim_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schleim_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schleim_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schleim_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schleim_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schleim_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schleim_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schleim_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Schleim_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magmawürfel_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magmawürfel_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magmawürfel_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magmawürfel_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magmawürfel_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magmawürfel_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magmawürfel_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magmawürfel_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magmawürfel_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magmawürfel_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magmawürfel_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magmawürfel_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magmawürfel_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magmawürfel_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magmawürfel_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magmawürfel_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magmawürfel_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magmawürfel_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magmawürfel_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magmawürfel_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Enderman_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Enderman_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Enderman_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Enderman_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Enderman_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Enderman_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Enderman_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Enderman_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Enderman_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Enderman_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Enderman_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Enderman_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Enderman_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Enderman_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Enderman_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Enderman_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Enderman_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Enderman_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Enderman_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Enderman_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiefizierter Piglin_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiefizierter Piglin_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiefizierter Piglin_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiefizierter Piglin_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiefizierter Piglin_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiefizierter Piglin_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiefizierter Piglin_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiefizierter Piglin_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiefizierter Piglin_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiefizierter Piglin_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiefizierter Piglin_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiefizierter Piglin_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiefizierter Piglin_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiefizierter Piglin_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiefizierter Piglin_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiefizierter Piglin_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiefizierter Piglin_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiefizierter Piglin_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiefizierter Piglin_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zombiefizierter Piglin_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin Barbaren_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin Barbaren_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin Barbaren_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin Barbaren_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin Barbaren_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin Barbaren_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin Barbaren_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin Barbaren_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin Barbaren_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin Barbaren_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin Barbaren_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin Barbaren_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin Barbaren_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin Barbaren_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin Barbaren_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin Barbaren_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin Barbaren_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin Barbaren_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin Barbaren_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Piglin Barbaren_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Plünderer_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Plünderer_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Plünderer_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Plünderer_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Plünderer_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Plünderer_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Plünderer_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Plünderer_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Plünderer_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Plünderer_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Plünderer_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Plünderer_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Plünderer_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Plünderer_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Plünderer_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Plünderer_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Plünderer_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Plünderer_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Plünderer_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Plünderer_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hoglin_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hoglin_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hoglin_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hoglin_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hoglin_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hoglin_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hoglin_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hoglin_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hoglin_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hoglin_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hoglin_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hoglin_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hoglin_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hoglin_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hoglin_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hoglin_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hoglin_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hoglin_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hoglin_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Hoglin_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magier_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magier_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magier_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magier_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magier_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magier_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magier_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magier_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magier_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magier_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magier_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magier_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magier_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magier_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magier_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magier_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magier_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magier_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magier_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Magier_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ghast_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ghast_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ghast_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ghast_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ghast_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ghast_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ghast_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ghast_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ghast_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ghast_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ghast_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ghast_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ghast_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ghast_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ghast_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ghast_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ghast_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ghast_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ghast_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Ghast_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Whiter Skellet_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Whiter Skellet_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Whiter Skellet_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Whiter Skellet_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Whiter Skellet_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Whiter Skellet_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Whiter Skellet_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Whiter Skellet_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Whiter Skellet_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Whiter Skellet_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Whiter Skellet_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Whiter Skellet_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Whiter Skellet_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Whiter Skellet_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Whiter Skellet_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Whiter Skellet_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Whiter Skellet_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Whiter Skellet_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Whiter Skellet_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Whiter Skellet_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zoglin_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zoglin_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zoglin_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zoglin_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zoglin_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zoglin_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zoglin_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zoglin_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zoglin_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zoglin_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zoglin_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zoglin_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zoglin_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zoglin_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zoglin_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zoglin_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zoglin_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zoglin_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zoglin_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Zoglin_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Verwüster_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Verwüster_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Verwüster_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Verwüster_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Verwüster_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Verwüster_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Verwüster_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Verwüster_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Verwüster_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Verwüster_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Verwüster_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Verwüster_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Verwüster_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Verwüster_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Verwüster_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Verwüster_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Verwüster_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Verwüster_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Verwüster_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Verwüster_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisengolem_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisengolem_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisengolem_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisengolem_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisengolem_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisengolem_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisengolem_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisengolem_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisengolem_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisengolem_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisengolem_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisengolem_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisengolem_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisengolem_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisengolem_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisengolem_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisengolem_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisengolem_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisengolem_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Eisengolem_20":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wärter_1":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wärter_2":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wärter_3":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wärter_4":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wärter_5":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wärter_6":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wärter_7":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wärter_8":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wärter_9":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wärter_10":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wärter_11":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wärter_12":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wärter_13":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wärter_14":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wärter_15":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wärter_16":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wärter_17":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wärter_18":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wärter_19":
                        setSuffix(itemName, p);
                        break;
                    case "Erfolg_Wärter_20":
                        setSuffix(itemName, p);
                        break;
                }
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
            user.data().add(SuffixNode.builder(ErfolgeBuilders.titlesHashmap.get(suffix), 150).build());
        });
    }
}


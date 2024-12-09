package net.dbsgameplay.dungeoncrusher.utils.Begleiter;

import com.destroystokyo.paper.entity.Pathfinder;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.listener.BegleiterListener;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.ErfolgeBuilders;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BegleiterBuilder {
    public static MYSQLManager mysqlManager;
    public static DungeonCrusher dungeonCrusher;

    public BegleiterBuilder(MYSQLManager mysqlManager, DungeonCrusher dungeonCrusher) {
        this.mysqlManager = mysqlManager;
        this.dungeonCrusher = dungeonCrusher;
    }

    public static void openBegleiterMenü(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, "Begeleitermenü");

        ItemStack levelup_item = new ItemStack(Material.NETHER_STAR);
        ItemMeta levelup_meta = levelup_item.getItemMeta();
        levelup_meta.setItemName("levelup");
        levelup_meta.setDisplayName("§6Levelup");
        levelup_meta.setLore(null);
        levelup_item.setItemMeta(levelup_meta);

        ItemStack meine_Begleiter_item = new ItemStack(Material.ZOMBIE_HEAD);
        ItemMeta meine_Begleiter_meta = meine_Begleiter_item.getItemMeta();
        meine_Begleiter_meta.setItemName("meine_begleiter");
        meine_Begleiter_meta.setDisplayName("§6Meine Begleiter");
        meine_Begleiter_meta.setLore(null);
        meine_Begleiter_item.setItemMeta(meine_Begleiter_meta);

        ItemStack begleiter_Auswahl_item = new ItemStack(Material.STONE_SWORD);
        ItemMeta begleiter_Auswahl_meta = begleiter_Auswahl_item.getItemMeta();
        begleiter_Auswahl_meta.setItemName("begleiter_auswahl");
        begleiter_Auswahl_meta.setDisplayName("§6Begleiter Auswahl");
        begleiter_Auswahl_meta.setLore(null);
        begleiter_Auswahl_item.setItemMeta(begleiter_Auswahl_meta);

        inv.setItem(11, begleiter_Auswahl_item);
        inv.setItem(13, levelup_item);
        inv.setItem(15, meine_Begleiter_item);

        p.openInventory(inv);
    }

    public static void openLevelupMenü(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, "Levelupmenü");
        inv.setItem(26, ErfolgeBuilders.createCustomMobHead("3ed1aba73f639f4bc42bd48196c715197be2712c3b962c97ebf9e9ed8efa025", "§cZurück"));

        //logic - Ausgewählte nur mit id speichern und anzeigen (levelsystem soon)

        p.openInventory(inv);
    }

    public static void openBegleiterAuswahlMenü(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, "Begleiter Auswahl");
        inv.setItem(26, ErfolgeBuilders.createCustomMobHead("3ed1aba73f639f4bc42bd48196c715197be2712c3b962c97ebf9e9ed8efa025", "§cZurück"));

        if (dungeonCrusher.getConfig().contains("begleiter." + p.getUniqueId())) {
            FileConfiguration cfg = dungeonCrusher.getConfig();

            if (cfg.isConfigurationSection("begleiter_ausgewählt." + p.getUniqueId())) {
                for (String ID : cfg.getConfigurationSection("begleiter_ausgewählt." + p.getUniqueId()).getKeys(false)) {
                    int level = cfg.getInt("begleiter." + p.getUniqueId() + "." + ID + "." + "level");
                    Material material = Material.getMaterial(cfg.getString("begleiter." + p.getUniqueId() + "." + ID + "." + "material"));

                    inv.setItem(4, build_Begleiter_Itemstack(material, ID, level));
                }
            }

            for (int i = 0; i != 9; i++) {
                if (inv.getItem(i) == null) {
                    inv.setItem(i, ItemStack.of(Material.BLUE_STAINED_GLASS_PANE));
                }
            }

            for (String ID : cfg.getConfigurationSection("begleiter." + p.getUniqueId()).getKeys(false)) {
                if (cfg.contains("begleiter_ausgewählt." + p.getUniqueId() + "." + ID)) continue;

                int level = cfg.getInt("begleiter." + p.getUniqueId() + "." + ID + "." + "level");
                Material material = Material.getMaterial(cfg.getString("begleiter." + p.getUniqueId() + "." + ID + "." + "material"));

                inv.addItem(build_Begleiter_Itemstack(material, ID, level));
            }
        }

        p.openInventory(inv);
    }

    public static void openMeineBegleiterMenü(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, "Meine Begleiter");
        inv.setItem(26, ErfolgeBuilders.createCustomMobHead("3ed1aba73f639f4bc42bd48196c715197be2712c3b962c97ebf9e9ed8efa025", "§cZurück"));

        if (dungeonCrusher.getConfig().contains("begleiter." + p.getUniqueId())) {
            FileConfiguration cfg = dungeonCrusher.getConfig();

            for (String ID : cfg.getConfigurationSection("begleiter." + p.getUniqueId()).getKeys(false)) {
                int level = cfg.getInt("begleiter." + p.getUniqueId() + "." + ID + "." + "level");
                Material material = Material.getMaterial(cfg.getString("begleiter." + p.getUniqueId() + "." + ID + "." + "material"));

                inv.addItem(build_Begleiter_Itemstack(material, ID, level));
            }
        }

        p.openInventory(inv);
    }

    public static void zieheBegleiter(Player p) {
        Material[] begleiterListe = {Material.ZOMBIE_HEAD, Material.CREEPER_HEAD, Material.SKELETON_SKULL, Material.PLAYER_HEAD};

        Random random = new Random();
        Material choosenBegleiter = begleiterListe[random.nextInt(0, begleiterListe.length)];
        String random_ID = String.valueOf(random.nextInt(10000, 999999));

        while (dungeonCrusher.getConfig().contains("begleiter." + p.getUniqueId() + "." + random_ID)) {
            random_ID = String.valueOf(random.nextInt(10000, 999999));
        }

        dungeonCrusher.getConfig().set("begleiter." + p.getUniqueId() + "." + random_ID + "." + "material", choosenBegleiter.name());
        dungeonCrusher.getConfig().set("begleiter." + p.getUniqueId() + "." + random_ID + "." + "level", 0);
        dungeonCrusher.saveConfig();

        p.sendMessage("Du hast einen Begleiter gezogen [Debug: Type " + choosenBegleiter.name() + ", ID " + random_ID);
    }

    public static ItemStack build_Begleiter_Itemstack(Material mat, String ID, int level) {
        ItemStack begleiter_ItemStack = new ItemStack(mat);
        ItemMeta begleiter_ItemMeta = begleiter_ItemStack.getItemMeta();
        begleiter_ItemMeta.setItemName(ID);
        begleiter_ItemMeta.setDisplayName("----- Begleiter -----");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§6Level: §7" + level);
        lore.add("");
        lore.add("§e» Linksklick um Begleiter zu wechseln.");
        begleiter_ItemMeta.setLore(lore);
        begleiter_ItemStack.setItemMeta(begleiter_ItemMeta);

        return  begleiter_ItemStack;
    }


//    private Player player;
//    private Location location;
//    ArmorStand begleiter;
//    private Material head = Material.ZOMBIE_HEAD;
//    private String name = "";
//
//    public BegleiterBuilder setLocation(Player player) {
//        this.player = player;
//        this.location = player.getLocation().clone().add(1, 0, -1);
//        begleiter = (ArmorStand) player.getWorld().spawnEntity(location, org.bukkit.entity.EntityType.ARMOR_STAND);
//        return this;
//    }
//
//    public BegleiterBuilder setSkin(Material skin) {
//        this.head = skin;
//        return this;
//    }
//
//    public BegleiterBuilder setName(String name) {
//        this.name = name;
//        return this;
//    }
//
//    public void build() {
//        begleiter.setHelmet(ItemStack.of(head));
//        begleiter.setVisible(false);
//        begleiter.setGravity(true);
//        begleiter.setCustomNameVisible(true);
//        begleiter.setSmall(true);
//        begleiter.setBasePlate(false);
//        begleiter.setCustomName(name);
//    }
//
//    public BegleiterBuilder addToList() {
//        BegleiterListener.begleiterliste.add(begleiter);
//        return this;
//    }
}

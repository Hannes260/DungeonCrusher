package net.dbsgameplay.dungeoncrusher.utils.Begleiter;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.ErfolgeBuilders;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class BegleiterBuilder {
    public static MYSQLManager mysqlManager;
    public static DungeonCrusher dungeonCrusher;

    public BegleiterBuilder(MYSQLManager mysqlManager, DungeonCrusher dungeonCrusher) {
        this.mysqlManager = mysqlManager;
        this.dungeonCrusher = dungeonCrusher;
    }

    public static void openBegleiterMenü(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, "Begleitermenü");

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
        FileConfiguration cfg = dungeonCrusher.getConfig();
        Inventory inv = Bukkit.createInventory(null, 27, "Begleiter Auswahl");
        inv.setItem(26, ErfolgeBuilders.createCustomMobHead("3ed1aba73f639f4bc42bd48196c715197be2712c3b962c97ebf9e9ed8efa025", "§cZurück"));

        String ID_ausgewählt = mysqlManager.getBegleiterID(p.getUniqueId().toString());

        if (ID_ausgewählt != null) {
            String level = mysqlManager.getBegleiterData(ID_ausgewählt, "level");
            String maxLevel = mysqlManager.getBegleiterData(ID_ausgewählt, "maxLevel");
            String damage = mysqlManager.getBegleiterData(ID_ausgewählt, "damage");
            String xp = mysqlManager.getBegleiterData(ID_ausgewählt, "xp");
            String maxXp = mysqlManager.getBegleiterData(ID_ausgewählt, "maxXp");
            String material = mysqlManager.getBegleiterData(ID_ausgewählt, "material");

            ItemStack begleiter_ItemStack = build_Begleiter_Itemstack(ID_ausgewählt, level, maxLevel, damage, xp, maxXp, material);
            ItemMeta begleiter_ItemMeta = begleiter_ItemStack.getItemMeta();
            List<String> lore = begleiter_ItemMeta.getLore();
            lore.add("§e» Linksklick um Begleiter abzuwählen.");
            begleiter_ItemMeta.setLore(lore);
            begleiter_ItemStack.setItemMeta(begleiter_ItemMeta);

            inv.setItem(4, begleiter_ItemStack);
        }

        for (int i = 0; i != 9; i++) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, ItemStack.of(Material.BLUE_STAINED_GLASS_PANE));
            }
        }

        for (String ID : mysqlManager.getAllBegleiterIDsOfPlayer(p.getUniqueId().toString())) {
            if (ID.equals(mysqlManager.getBegleiterID(p.getUniqueId().toString()))) continue;

            String level = mysqlManager.getBegleiterData(ID, "level");
            String maxLevel = mysqlManager.getBegleiterData(ID, "maxLevel");
            String damage = mysqlManager.getBegleiterData(ID, "damage");
            String xp = mysqlManager.getBegleiterData(ID, "xp");
            String maxXp = mysqlManager.getBegleiterData(ID, "maxXp");
            String material = mysqlManager.getBegleiterData(ID, "material");

            ItemStack begleiter_ItemStack = build_Begleiter_Itemstack(ID, level, maxLevel, damage, xp, maxXp, material);
            ItemMeta begleiter_ItemMeta = begleiter_ItemStack.getItemMeta();
            List<String> lore = begleiter_ItemMeta.getLore();
            lore.add("§e» Linksklick um Begleiter auszurüsten.");
            begleiter_ItemMeta.setLore(lore);
            begleiter_ItemStack.setItemMeta(begleiter_ItemMeta);

            inv.addItem(begleiter_ItemStack);
        }

        p.openInventory(inv);
    }

    public static void openMeineBegleiterMenü(Player p) {
        FileConfiguration cfg = dungeonCrusher.getConfig();
        Inventory inv = Bukkit.createInventory(null, 27, "Meine Begleiter");
        inv.setItem(26, ErfolgeBuilders.createCustomMobHead("3ed1aba73f639f4bc42bd48196c715197be2712c3b962c97ebf9e9ed8efa025", "§cZurück"));

        for (String ID : mysqlManager.getAllBegleiterIDsOfPlayer(p.getUniqueId().toString())) {
            String level = mysqlManager.getBegleiterData(ID, "level");
            String maxLevel = mysqlManager.getBegleiterData(ID, "maxLevel");
            String damage = mysqlManager.getBegleiterData(ID, "damage");
            String xp = mysqlManager.getBegleiterData(ID, "xp");
            String maxXp = mysqlManager.getBegleiterData(ID, "maxXp");
            String material = mysqlManager.getBegleiterData(ID, "material");

            inv.addItem(build_Begleiter_Itemstack(ID, level, maxLevel, damage, xp, maxXp, material));
        }

        p.openInventory(inv);
    }

    public static void zieheBegleiter(Player p) {
        Material[] begleiterListe = {Material.ZOMBIE_HEAD, Material.CREEPER_HEAD, Material.SKELETON_SKULL, Material.PLAYER_HEAD};

        Random random = new Random();
        Material choosenBegleiter = begleiterListe[random.nextInt(0, begleiterListe.length)];
        String random_ID = String.valueOf(random.nextInt(10000, 999999));

        while (mysqlManager.getBegleiterData(random_ID, "id") != null) {
            random_ID = String.valueOf(random.nextInt(10000, 999999));
        }

        mysqlManager.updateBegleiterData(random_ID, "uuid", p.getUniqueId().toString());
        mysqlManager.updateBegleiterData(random_ID, "material", choosenBegleiter.name());
        mysqlManager.updateBegleiterData(random_ID, "level", "0");
        mysqlManager.updateBegleiterData(random_ID, "maxLevel", "10");
        mysqlManager.updateBegleiterData(random_ID, "xp", "0");
        mysqlManager.updateBegleiterData(random_ID, "maxXp", "150");
        mysqlManager.updateBegleiterData(random_ID, "damage", "1");

        p.sendMessage("Du hast einen Begleiter gezogen [Debug: Type " + choosenBegleiter.name() + ", ID " + random_ID);
    }

    public static ItemStack build_Begleiter_Itemstack(String ID, String level, String maxLevel, String damage, String xp, String maxXp, String material) {
        ItemStack begleiter_ItemStack = new ItemStack(Material.valueOf(material));
        ItemMeta begleiter_ItemMeta = begleiter_ItemStack.getItemMeta();
        begleiter_ItemMeta.setItemName(ID);
        begleiter_ItemMeta.setDisplayName("§e----- Begleiter -----");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("§6Damage: §7" + damage);
        lore.add("§e--------------------");
        lore.add("§6Level: §7" + level);
        lore.add("§6XP:     §7" + xp + " " + loadXpBar(Integer.parseInt(xp)*100/Integer.parseInt(maxXp)));
        lore.add("");
        begleiter_ItemMeta.setLore(lore);

        begleiter_ItemStack.setItemMeta(begleiter_ItemMeta);

        return begleiter_ItemStack;
    }

    private static String loadXpBar(int percentage) {
        int completedBars = percentage / 10;

        return "§a" + "|".repeat(completedBars) + "§8" + "|".repeat(10 - completedBars);
    }
}

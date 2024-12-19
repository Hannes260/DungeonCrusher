package net.dbsgameplay.dungeoncrusher.Commands.MiniBoss;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.listener.DungeonListener;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SpawnMiniBossCommand implements CommandExecutor {

    public static MYSQLManager mysqlManager;
    public static DungeonCrusher dungeonCrusher;

    public SpawnMiniBossCommand(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.mysqlManager = mysqlManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player p)) return true;
        if(strings.length == 0) {

            String dungeon = DungeonListener.getCurrentDungeon(p.getLocation());

            if(dungeon != null) {
                CreateMiniBossSpawnInv(dungeon, p);
            }else {
                p.sendMessage("§4Du kannst nur in einem Dungeon einen Miniboss beschwören!");
                return true;
            }

        }

        return false;
    }

    private void CreateMiniBossSpawnInv(String dungeon, Player p) {

        Inventory bossInv = Bukkit.createInventory(null, 27, "Miniboss " + dungeon);

        FileConfiguration config = dungeonCrusher.getConfig();

        ItemStack lv1BossItem = new ItemStack(Material.CREEPER_SPAWN_EGG);
        ItemMeta lv1BossMeta = lv1BossItem.getItemMeta();
        lv1BossMeta.setDisplayName("Miniboss §aLevel 1");
        List<String> lv1Lore = new ArrayList();
        lv1Lore.add("§6Preis: " + config.getString(dungeon + ".lvl1.preis"));
        lv1Lore.add("§6Mobs getötet: " + config.getString(dungeon + ".lvl1.kills"));
        lv1BossMeta.setLore(lv1Lore);

        ItemStack lv2BossItem = new ItemStack(Material.BEE_SPAWN_EGG);
        ItemMeta lv2BossMeta = lv2BossItem.getItemMeta();
        lv2BossMeta.setDisplayName("Miniboss §aLevel 2");
        List<String> lv2Lore = new ArrayList();
        lv1Lore.add("§6Preis: " + config.getString(dungeon + ".lvl2.preis"));
        lv1Lore.add("§6Mobs getötet: " + config.getString(dungeon + ".lvl2.kills"));
        lv2BossMeta.setLore(lv2Lore);

        ItemStack lv3BossItem = new ItemStack(Material.CREEPER_SPAWN_EGG);
        ItemMeta lv3BossMeta = lv3BossItem.getItemMeta();
        lv3BossMeta.setDisplayName("Miniboss §aLevel 3");
        List<String> lv3Lore = new ArrayList();
        lv1Lore.add("§6Preis: " + config.getString(dungeon + ".lvl3.preis"));
        lv1Lore.add("§6Mobs getötet: " + config.getString(dungeon + ".lvl3.kills"));
        lv3BossMeta.setLore(lv3Lore);

        bossInv.setItem(11, lv1BossItem);
        bossInv.setItem(13, lv2BossItem);
        bossInv.setItem(15, lv3BossItem);

        p.openInventory(bossInv);

    }
}

package net.dbsgameplay.dungeoncrusher.Commands.MiniBoss;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.listener.DungeonListener;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.MinibossConfigManager;
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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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

                int currentDungeonNumber = Integer.parseInt(dungeon.replace("dungeon", ""));

                if(currentDungeonNumber > 10) {
                    p.sendMessage("§cDie Minibosse sind bisher nur bis Dungeon 10 spielbar!");
                }else {
                    CreateMiniBossSpawnInv(dungeon, p);
                }
            }else {
                p.sendMessage("§cDu kannst nur in einem Dungeon einen Miniboss beschwören!");
                return true;
            }

        }

        return false;
    }

    private void CreateMiniBossSpawnInv(String dungeon, Player p) {
        Inventory bossInv = Bukkit.createInventory(null, 27, "Spawn Miniboss");

        MinibossConfigManager.loadConfig();
        HashMap<String, HashMap<String, HashMap<String, Double>>> minibossHashMap = MinibossConfigManager.miniboss_data_Hashmap;

        ItemStack lv1BossItem = new ItemStack(Material.CREEPER_SPAWN_EGG);
        ItemMeta lv1BossMeta = lv1BossItem.getItemMeta();
        lv1BossMeta.setDisplayName("Miniboss §aLevel 1");
        List<String> lv1Lore = new ArrayList();
        lv1Lore.add("§6Preis: " + "§7" +  minibossHashMap.get(dungeon).get("lvl1").get("preis").intValue() + "€");
        lv1Lore.add("§6Mobs getötet: " + "§7" +  minibossHashMap.get(dungeon).get("lvl1").get("kills").intValue());
        lv1BossMeta.setLore(lv1Lore);
        lv1BossItem.setItemMeta(lv1BossMeta);

        ItemStack lv2BossItem = new ItemStack(Material.BEE_SPAWN_EGG);
        ItemMeta lv2BossMeta = lv2BossItem.getItemMeta();
        lv2BossMeta.setDisplayName("Miniboss §aLevel 2");
        List<String> lv2Lore = new ArrayList();
        lv2Lore.add("§6Preis: " + "§7" + minibossHashMap.get(dungeon).get("lvl2").get("preis").intValue() + "€");
        lv2Lore.add("§6Mobs getötet: " + "§7" +  minibossHashMap.get(dungeon).get("lvl2").get("kills").intValue());
        lv2BossMeta.setLore(lv2Lore);
        lv2BossItem.setItemMeta(lv2BossMeta);

        ItemStack lv3BossItem = new ItemStack(Material.MOOSHROOM_SPAWN_EGG);
        ItemMeta lv3BossMeta = lv3BossItem.getItemMeta();
        lv3BossMeta.setDisplayName("Miniboss §aLevel 3");
        List<String> lv3Lore = new ArrayList();
        lv3Lore.add("§6Preis: " + "§7" +  minibossHashMap.get(dungeon).get("lvl3").get("preis").intValue() + "€");
        lv3Lore.add("§6Mobs getötet: " + "§7" +  minibossHashMap.get(dungeon).get("lvl3").get("kills").intValue());
        lv3BossMeta.setLore(lv3Lore);
        lv3BossItem.setItemMeta(lv3BossMeta);

        int bossLevel = 0;

        bossLevel = mysqlManager.getMinibossLevel(p.getUniqueId().toString(), dungeon);

        bossInv.setItem(11, lv1BossItem);

        if(bossLevel >= 1) {
            bossInv.setItem(13, lv2BossItem);
        } else {
            bossInv.setItem(13, new ItemStack(Material.BARRIER));
        }

        if(bossLevel >= 2) {
            bossInv.setItem(15, lv3BossItem);
        }else {
            bossInv.setItem(15, new ItemStack(Material.BARRIER));
        }

        p.openInventory(bossInv);

    }
}

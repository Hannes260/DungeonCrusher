package net.dbsgameplay.dungeoncrusher.listener.Miniboss;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.listener.DungeonListener;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.MinibossConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.attribute.Attribute;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Random;

public class MinibossListener implements Listener {

    MYSQLManager mysqlManager;
    private DungeonCrusher dungeonCrusher;

    private HashMap<LivingEntity, BossBar> bossBars = new HashMap<>();

    public MinibossListener(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.mysqlManager = mysqlManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        Inventory clickedInventory = event.getInventory();

        if (clickedItem == null) {
            return;
        }

        if(event.getView().getTitle().equalsIgnoreCase("Spawn Miniboss")) {

            event.setCancelled(true);

            String dungeon = DungeonListener.getCurrentDungeon(p.getLocation());
            String currentMoneyString = mysqlManager.getBalance(p.getUniqueId().toString());
            currentMoneyString = currentMoneyString.replace(",", "");
            Double currentMoney = Double.parseDouble(currentMoneyString);

            HashMap<String, HashMap<String, HashMap<String, Integer>>> minibossHashMap = MinibossConfigManager.miniboss_data_Hashmap;

            if(clickedItem.getType() == Material.CREEPER_SPAWN_EGG) {

                double costs = minibossHashMap.get(dungeon).get("lvl1").get("preis");

                if (currentMoney >= costs) {
                    Location playerLocation = p.getLocation();

                    Random random = new Random();
                    int x = playerLocation.getBlockX() + random.nextInt(21) - 10;
                    int y = playerLocation.getBlockY();
                    int z = playerLocation.getBlockZ() + random.nextInt(21) - 10;

                    // Zombie spawnen
                    Location spawnLocation = new Location(playerLocation.getWorld(), x, y, z);

                    LivingEntity boss = (LivingEntity) p.getWorld().spawnEntity(spawnLocation, EntityType.SHEEP);
                    boss.setCustomName("§cMiniboss");
                    boss.setCustomNameVisible(true);
                    boss.setMaxHealth(minibossHashMap.get(dungeon).get("lvl1").get("health"));
                    boss.setHealth(minibossHashMap.get(dungeon).get("lvl1").get("health"));
                    boss.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(2.0);

                    BossBar bossBar = Bukkit.createBossBar(
                            "§cMiniboss §f" + (int)boss.getHealth() + "§c/§f" + (int)boss.getMaxHealth() + " §cHP",
                            BarColor.RED,
                            BarStyle.SOLID,
                            BarFlag.PLAY_BOSS_MUSIC
                    );

                    bossBar.addPlayer(p);
                    bossBar.setProgress(boss.getHealth() / boss.getMaxHealth());
                    bossBars.put(boss, bossBar);

                    p.closeInventory();

                }else {
                    p.sendMessage("§cDu hast nicht genug Geld um diesen Miniboss zu beschwören");
                }
            }


        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) return;
        
        LivingEntity entity = (LivingEntity) event.getEntity();

        if (!bossBars.containsKey(entity)) return;
        BossBar bossBar = bossBars.get(entity);

        Bukkit.getScheduler().runTaskLater(dungeonCrusher, () -> {
            bossBar.setTitle("§cMiniboss §f" + (int) entity.getHealth() + "§c/§f" + (int) entity.getMaxHealth() + " §cHP");
            bossBar.setProgress(Math.max(0, entity.getHealth() / entity.getMaxHealth()));

            if (entity.isDead()) {
                bossBar.removeAll();
                bossBars.remove(entity);
            }
        }, 1L );
    }
}

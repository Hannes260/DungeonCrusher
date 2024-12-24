package net.dbsgameplay.dungeoncrusher.listener.Miniboss;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.listener.DungeonListener;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ErfolgeConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.MinibossConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.attribute.Attribute;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MinibossListener implements Listener {

    MYSQLManager mysqlManager;
    private DungeonCrusher dungeonCrusher;

    private HashMap<LivingEntity, BossBar> bossBars = new HashMap<>();
    private HashMap<LivingEntity, Integer> bossLevel = new HashMap<>();
    public static  HashMap<LivingEntity, Boolean> livingMinibosse = new HashMap<>();

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

            HashMap<String, HashMap<String, HashMap<String, Double>>> minibossHashMap = MinibossConfigManager.miniboss_data_Hashmap;

            if(clickedItem.getType() == Material.CREEPER_SPAWN_EGG) {

                double costs = minibossHashMap.get(dungeon).get("lvl1").get("preis").intValue();

                if (currentMoney >= costs) {
                    Location playerLocation = p.getLocation();

                    Random random = new Random();
                    int x = playerLocation.getBlockX() + random.nextInt(21) - 10;
                    int y = playerLocation.getBlockY();
                    int z = playerLocation.getBlockZ() + random.nextInt(21) - 10;

                    // Zombie spawnen
                    Location spawnLocation = new Location(playerLocation.getWorld(), x, y, z);

                    List<String> dungeonMobList =  ErfolgeConfigManager.enNamesList;
                    EntityType entityType = EntityType.fromName(dungeonMobList.get((Integer.parseInt(dungeon.replace("dungeon", "")) - 1)));

                    LivingEntity boss = (LivingEntity) p.getWorld().spawnEntity(spawnLocation, entityType);
                    boss.setCustomName("§cMiniboss");
                    boss.setCustomNameVisible(true);
                    boss.setMaxHealth(minibossHashMap.get(dungeon).get("lvl1").get("health").intValue());
                    boss.setHealth(minibossHashMap.get(dungeon).get("lvl1").get("health").intValue());
                    boss.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(2.0);
                    boss.setVisibleByDefault(false);
                    p.showEntity(dungeonCrusher, boss);
                    followPlayer((Mob) boss, p);
                    applyDamageToNearbyPlayers((Mob) boss, p, minibossHashMap.get(dungeon).get("lvl1").get("damage"));

                    BossBar bossBar = Bukkit.createBossBar(
                            "§cMiniboss §f" + (int)boss.getHealth() + "§c/§f" + (int)boss.getMaxHealth() + " §cHP",
                            BarColor.RED,
                            BarStyle.SOLID,
                            BarFlag.PLAY_BOSS_MUSIC
                    );

                    bossBar.addPlayer(p);
                    bossBar.setProgress(boss.getHealth() / boss.getMaxHealth());
                    bossBars.put(boss, bossBar);
                    bossLevel.put(boss, 1);
                    livingMinibosse.put(boss, true);

                    p.closeInventory();

                }else {
                    p.sendMessage("§cDu hast nicht genug Geld um diesen Miniboss zu beschwören");
                }
            }

            if(clickedItem.getType() == Material.BEE_SPAWN_EGG) {

                double costs = minibossHashMap.get(dungeon).get("lvl2").get("preis").intValue();

                if (currentMoney >= costs) {
                    Location playerLocation = p.getLocation();

                    Random random = new Random();
                    int x = playerLocation.getBlockX() + random.nextInt(21) - 10;
                    int y = playerLocation.getBlockY();
                    int z = playerLocation.getBlockZ() + random.nextInt(21) - 10;

                    // Zombie spawnen
                    Location spawnLocation = new Location(playerLocation.getWorld(), x, y, z);

                    List<String> dungeonMobList =  ErfolgeConfigManager.enNamesList;
                    EntityType entityType = EntityType.fromName(dungeonMobList.get((Integer.parseInt(dungeon.replace("dungeon", "")) - 1)));

                    LivingEntity boss = (LivingEntity) p.getWorld().spawnEntity(spawnLocation, entityType);
                    boss.setCustomName("§cMiniboss");
                    boss.setCustomNameVisible(true);
                    boss.setMaxHealth(minibossHashMap.get(dungeon).get("lvl2").get("health").intValue());
                    boss.setHealth(minibossHashMap.get(dungeon).get("lvl2").get("health").intValue());
                    boss.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(2.0);
                    boss.setVisibleByDefault(false);
                    p.showEntity(dungeonCrusher, boss);
                    followPlayer((Mob) boss, p);
                    applyDamageToNearbyPlayers((Mob) boss, p, minibossHashMap.get(dungeon).get("lvl2").get("damage"));

                    BossBar bossBar = Bukkit.createBossBar(
                            "§cMiniboss §f" + (int)boss.getHealth() + "§c/§f" + (int)boss.getMaxHealth() + " §cHP",
                            BarColor.RED,
                            BarStyle.SOLID,
                            BarFlag.PLAY_BOSS_MUSIC
                    );

                    bossBar.addPlayer(p);
                    bossBar.setProgress(boss.getHealth() / boss.getMaxHealth());
                    bossBars.put(boss, bossBar);
                    bossLevel.put(boss, 2);
                    livingMinibosse.put(boss, true);

                    p.closeInventory();

                }else {
                    p.sendMessage("§cDu hast nicht genug Geld um diesen Miniboss zu beschwören");
                }
            }

            if(clickedItem.getType() == Material.MOOSHROOM_SPAWN_EGG) {

                double costs = minibossHashMap.get(dungeon).get("lvl3").get("preis").intValue();

                if (currentMoney >= costs) {
                    Location playerLocation = p.getLocation();

                    Random random = new Random();
                    int x = playerLocation.getBlockX() + random.nextInt(21) - 10;
                    int y = playerLocation.getBlockY();
                    int z = playerLocation.getBlockZ() + random.nextInt(21) - 10;

                    // Zombie spawnen
                    Location spawnLocation = new Location(playerLocation.getWorld(), x, y, z);

                    List<String> dungeonMobList =  ErfolgeConfigManager.enNamesList;
                    EntityType entityType = EntityType.fromName(dungeonMobList.get((Integer.parseInt(dungeon.replace("dungeon", "")) - 1)));

                    LivingEntity boss = (LivingEntity) p.getWorld().spawnEntity(spawnLocation, entityType);
                    boss.setCustomName("§cMiniboss");
                    boss.setCustomNameVisible(true);
                    boss.setMaxHealth(minibossHashMap.get(dungeon).get("lvl3").get("health").intValue());
                    boss.setHealth(minibossHashMap.get(dungeon).get("lvl3").get("health").intValue());
                    boss.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(2.0);
                    boss.setVisibleByDefault(false);
                    p.showEntity(dungeonCrusher, boss);
                    followPlayer((Mob) boss, p);
                    applyDamageToNearbyPlayers((Mob) boss, p, minibossHashMap.get(dungeon).get("lvl3").get("damage"));

                    BossBar bossBar = Bukkit.createBossBar(
                            "§cMiniboss §f" + (int)boss.getHealth() + "§c/§f" + (int)boss.getMaxHealth() + " §cHP",
                            BarColor.RED,
                            BarStyle.SOLID,
                            BarFlag.PLAY_BOSS_MUSIC
                    );

                    bossBar.addPlayer(p);
                    bossBar.setProgress(boss.getHealth() / boss.getMaxHealth());
                    bossBars.put(boss, bossBar);
                    bossLevel.put(boss, 3);
                    livingMinibosse.put(boss, true);

                    p.closeInventory();

                }else {
                    p.sendMessage("§cDu hast nicht genug Geld um diesen Miniboss zu beschwören");
                }
            }


        }
    }

    private void followPlayer(Mob mob, Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (mob.isDead() || !player.isOnline()) {
                    this.cancel();
                    return;
                }

                if (mob.getLocation().distance(player.getLocation()) > 50) {
                    mob.setTarget(null); // Stoppe die Verfolgung
                    return;
                }

                mob.setTarget(player);
                mob.getPathfinder().moveTo(player);
            }
        }.runTaskTimer(DungeonCrusher.getInstance(), 0L, 5L);
    }

    private void applyDamageToNearbyPlayers(Mob mob, Player p, double damage) {
        new BukkitRunnable() {

            @Override
            public void run() {
                if (mob.isDead()) {
                    this.cancel();
                    return;
                }
                for (Entity entity : mob.getNearbyEntities(2,2,2)){
                    if (entity instanceof Player) {
                        Player nearbyPlayer = (Player) entity;
                        if(nearbyPlayer == p){
                            nearbyPlayer.damage(damage);
                        }
                    }
                }
            }
        }.runTaskTimer(DungeonCrusher.getInstance(), 0L, 20L);
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
                livingMinibosse.remove(entity);

                if(entity.getKiller() instanceof Player){

                    Player p = entity.getKiller();
                    String dungeon = DungeonListener.getCurrentDungeon(p.getLocation());
                    int level = bossLevel.get(entity);

                    mysqlManager.updateMinibossLevel(p.getUniqueId().toString(), dungeon, level);

                    p.sendMessage("§aDu hast erfolgreich den Miniboss gekillt");
                }
            }
        }, 1L );
    }
}

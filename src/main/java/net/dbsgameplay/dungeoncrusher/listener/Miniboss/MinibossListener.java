package net.dbsgameplay.dungeoncrusher.listener.Miniboss;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.listener.DungeonListener;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ErfolgeConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.MinibossConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
import org.bukkit.*;
import org.bukkit.boss.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.attribute.Attribute;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MinibossListener implements Listener {
    private static final int SPAWN_RADIUS = 10;
    private static final int FOLLOW_DISTANCE = 50;
    private static final double BOSS_SCALE = 2.0;
    private static final int DAMAGE_RADIUS = 2;

    private final DungeonCrusher dungeonCrusher;
    private final MYSQLManager mysqlManager;
    private final Map<LivingEntity, BossBar> bossBars = new ConcurrentHashMap<>();
    private final Map<LivingEntity, Integer> bossLevel = new ConcurrentHashMap<>();
    public static final Map<LivingEntity, Boolean> livingMinibosse = new ConcurrentHashMap<>();
    public final HashMap<Player, HashMap<EntityType, Integer>> MinibossMobCount = new HashMap<>();
    public final HashMap<Player, HashMap<EntityType, BossBar>> MinibossMobBossbar = new HashMap<>();
    private Integer level;

    private final Map<Material, Integer> spawnEggToLevel = new EnumMap<>(Material.class) {{
        put(Material.CREEPER_SPAWN_EGG, 1);
        put(Material.BEE_SPAWN_EGG, 2);
        put(Material.MOOSHROOM_SPAWN_EGG, 3);
    }};

    public MinibossListener(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.mysqlManager = mysqlManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player) ||
                !event.getView().getTitle().equalsIgnoreCase("Spawn Miniboss") ||
                event.getCurrentItem() == null) {
            return;
        }

        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        Material clickedType = event.getCurrentItem().getType();
        HashMap<String, HashMap<String, HashMap<String, Double>>> minibossData = MinibossConfigManager.miniboss_data_Hashmap;
        String dungeon = DungeonListener.getCurrentDungeon(player.getLocation());

        level = spawnEggToLevel.get(clickedType);
        if (level == null) return;

        if(MinibossMobCount.containsKey(player) && MinibossMobBossbar.containsKey(player)) {
            player.sendMessage("§cDu hast bereits einen Miniboss beschworen");
            return;
        }

        double currentMoney = Double.parseDouble(mysqlManager.getBalance(player.getUniqueId().toString()).replace(",", ""));
        double cost = minibossData.get(dungeon).get("lvl" + level).get("preis");

        if (currentMoney < cost) {
            player.sendMessage("§cDu hast nicht genug Geld um diesen Miniboss zu beschwören");
            return;
        }

        double newBalance = currentMoney - cost;
        mysqlManager.updateBalance(player.getUniqueId().toString(), String.valueOf(newBalance));
        ScoreboardBuilder scoreboardBuilder = new ScoreboardBuilder(dungeonCrusher);
        scoreboardBuilder.updateMoney(player);

        int mobsToKill = minibossData.get(dungeon).get("lvl" + level).get("kills").intValue();

        player.sendMessage("§aTöte " + mobsToKill + " Mobs auf der Ebene um den Miniboss zu spawnen");

        List<String> dungeonMobList = ErfolgeConfigManager.enNamesList;
        EntityType entityType = EntityType.fromName(dungeonMobList.get(Integer.parseInt(dungeon.replace("dungeon", "")) - 1));

        BossBar MobBossbar = Bukkit.createBossBar("§aGetötete Mobs: §60 §a/ §a" + mobsToKill, BarColor.GREEN, BarStyle.SOLID);
        MobBossbar.addPlayer(player);
        MobBossbar.setProgress(0);

        HashMap<EntityType, BossBar> a = new HashMap<>();
        a.put(entityType, MobBossbar);
        MinibossMobBossbar.put(player, a);

        HashMap<EntityType, Integer> b = new HashMap<>();
        b.put(entityType, 0);
        MinibossMobCount.put(player, b);

    }

    @EventHandler
    public void onMobkill(EntityDeathEvent event) {

        EntityType killedMob = event.getEntityType();
        Player player = event.getEntity().getKiller();
        HashMap<String, HashMap<String, HashMap<String, Double>>> minibossData = MinibossConfigManager.miniboss_data_Hashmap;

        if(player == null) return;
        if(player instanceof Player) {

            String dungeon = DungeonListener.getCurrentDungeon(player.getLocation());

            if(MinibossMobCount.containsKey(player) && MinibossMobCount.get(player).containsKey(killedMob)) {
                if(level == null) return;

                int currentKills = MinibossMobCount.get(player).get(killedMob);
                int updatedKills = currentKills + 1;
                MinibossMobCount.get(player).put(killedMob, updatedKills);

                int mobsToKill = minibossData.get(dungeon).get("lvl" + level).get("kills").intValue();

                Bukkit.getScheduler().runTaskLater(dungeonCrusher, () -> {
                    MinibossMobBossbar.get(player).get(killedMob).setTitle("§aGetötete Mobs: §6" + updatedKills + " §a/ §a" + mobsToKill);
                    MinibossMobBossbar.get(player).get(killedMob).setProgress((double) updatedKills / mobsToKill);

                    if(updatedKills == mobsToKill) {
                        MinibossMobBossbar.get(player).get(killedMob).removeAll();
                        MinibossMobCount.remove(player);
                        MinibossMobBossbar.remove(player);
                        spawnMiniboss(player, level);
                        player.sendMessage("§aDu hast den Miniboss erfolgreich beschworen");

                    }
                }, 1L);
            }
        }

        return;
    }

    private void spawnMiniboss(Player player, int Minibosslevel) {
        String dungeon = DungeonListener.getCurrentDungeon(player.getLocation());
        HashMap<String, HashMap<String, HashMap<String, Double>>> minibossData = MinibossConfigManager.miniboss_data_Hashmap;




        Location spawnLoc = getRandomSpawnLocation(player.getLocation());
        LivingEntity boss = spawnBoss(player, spawnLoc, dungeon, Minibosslevel, minibossData);

        createBossBar(boss, player);
        followPlayer(boss, player);
        applyDamageToNearbyPlayers(boss, player, minibossData.get(dungeon).get("lvl" + Minibosslevel).get("damage"));

        player.closeInventory();
    }

    private Location getRandomSpawnLocation(Location playerLoc) {
        return playerLoc.clone().add(
                (Math.random() * 2 - 1) * SPAWN_RADIUS,
                0,
                (Math.random() * 2 - 1) * SPAWN_RADIUS
        );
    }

    private LivingEntity spawnBoss(Player player, Location location, String dungeon, int Minibosslevel, Map<String, HashMap<String, HashMap<String, Double>>> minibossData) {
        List<String> dungeonMobList = ErfolgeConfigManager.enNamesList;
        EntityType entityType = EntityType.fromName(dungeonMobList.get(Integer.parseInt(dungeon.replace("dungeon", "")) - 1));

        LivingEntity boss = (LivingEntity) player.getWorld().spawnEntity(location, entityType);
        double health = minibossData.get(dungeon).get("lvl" + Minibosslevel).get("health");

        boss.setCustomName("§cMiniboss");
        boss.setCustomNameVisible(true);
        boss.setMaxHealth(health);
        boss.setHealth(health);
        boss.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(BOSS_SCALE);
        boss.setVisibleByDefault(false);

        player.showEntity(dungeonCrusher, boss);
        bossLevel.put(boss, Minibosslevel);
        livingMinibosse.put(boss, true);

        return boss;
    }

    private void createBossBar(LivingEntity boss, Player player) {
        BossBar bossBar = Bukkit.createBossBar(
                getBossBarTitle(boss),
                BarColor.RED,
                BarStyle.SOLID,
                BarFlag.PLAY_BOSS_MUSIC
        );

        bossBar.addPlayer(player);
        bossBar.setProgress(1.0);
        bossBars.put(boss, bossBar);
    }

    private String getBossBarTitle(LivingEntity boss) {
        return String.format("§cMiniboss §f%d§c/§f%d §cHP",
                (int)boss.getHealth(),
                (int)boss.getMaxHealth());
    }

    private void followPlayer(LivingEntity boss, Player player) {
        if (!(boss instanceof Mob)) return;
        Mob mob = (Mob) boss;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!isValidTarget(mob, player)) {
                    this.cancel();
                    return;
                }
                updateMobTarget(mob, player);
            }
        }.runTaskTimer(dungeonCrusher, 0L, 5L);
    }

    private boolean isValidTarget(Mob mob, Player player) {
        return !mob.isDead() &&
                player.isOnline() &&
                mob.getLocation().distance(player.getLocation()) <= FOLLOW_DISTANCE;
    }

    private void updateMobTarget(Mob mob, Player player) {
        mob.setTarget(player);
        mob.getPathfinder().moveTo(player);
    }

    private void applyDamageToNearbyPlayers(LivingEntity boss, Player target, double damage) {
        if (!(boss instanceof Mob)) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (boss.isDead()) {
                    this.cancel();
                    return;
                }

                boss.getNearbyEntities(DAMAGE_RADIUS, DAMAGE_RADIUS, DAMAGE_RADIUS).stream()
                        .filter(entity -> entity instanceof Player && entity.equals(target))
                        .forEach(entity -> ((Player) entity).damage(damage));
            }
        }.runTaskTimer(dungeonCrusher, 0L, 20L);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) return;

        LivingEntity entity = (LivingEntity) event.getEntity();
        BossBar bossBar = bossBars.get(entity);
        if (bossBar == null) return;

        Bukkit.getScheduler().runTaskLater(dungeonCrusher, () -> {
            if (entity.isDead()) {
                handleBossDeath(entity);
                return;
            }
            updateBossBar(entity, bossBar);
        }, 1L);
    }

    private void handleBossDeath(LivingEntity entity) {
        BossBar bossBar = bossBars.get(entity);
        bossBar.removeAll();
        bossBars.remove(entity);
        livingMinibosse.remove(entity);

        if (entity.getKiller() instanceof Player) {
            Player killer = entity.getKiller();
            String dungeon = DungeonListener.getCurrentDungeon(killer.getLocation());

            int currentLevel = mysqlManager.getMinibossLevel(killer.getUniqueId().toString(), dungeon);
            if(!(bossLevel.get(entity) <= currentLevel)) {
                mysqlManager.updateMinibossLevel(killer.getUniqueId().toString(), dungeon, bossLevel.get(entity));
            }
            killer.sendMessage("§aDu hast erfolgreich den Miniboss gekillt");
        }
    }

    private void updateBossBar(LivingEntity entity, BossBar bossBar) {
        bossBar.setTitle(getBossBarTitle(entity));
        bossBar.setProgress(Math.max(0, entity.getHealth() / entity.getMaxHealth()));
    }
}
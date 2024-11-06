package net.dbsgameplay.dungeoncrusher.listener;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.quests.QuestBuilder;
import net.dbsgameplay.dungeoncrusher.utils.quests.Daily;
import net.dbsgameplay.dungeoncrusher.utils.quests.Monthly;
import net.dbsgameplay.dungeoncrusher.utils.quests.Weekly;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class QuestListener implements Listener {

    public static MYSQLManager mysqlManager;
    DungeonCrusher dungeonCrusher;
    public static HashMap<UUID, Integer> rewardChoosenMap = new HashMap<>();

    public QuestListener(MYSQLManager mysqlManager, DungeonCrusher dungeonCrusher) {
        this.mysqlManager = mysqlManager;
        this.dungeonCrusher = dungeonCrusher;
    }

    public static HashMap<UUID, Integer> playtimeMap = new HashMap<UUID, Integer>();

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        HashMap<String, String> tutorialQuestMap = QuestBuilder.tutorialQuestMap;
        String tutorialQuest = mysqlManager.getTutorialQuest(p.getUniqueId().toString());

        if  (!p.hasPlayedBefore()) {
            p.sendMessage("§6Das Tutorial an der Bossbar zeigt dir wie du Spielst!");
        }
        if (tutorialQuest == null) {
            // Spieler existiert noch nicht, daher neuen Eintrag hinzufügen
            tutorialQuest = "t4";  // Standardwert für neue Spieler
            mysqlManager.updateTutorialQuest(p.getUniqueId().toString(), tutorialQuest);
        }

        // Null-Check ist jetzt unnötig, da wir tutorialQuest initialisieren
        switch (tutorialQuest) {
            case "t4":
                QuestBuilder.questBar_t4.addPlayer(p);
                break;
            case "t3":
                QuestBuilder.questBar_t3.addPlayer(p);
                break;
            case "t2":
                QuestBuilder.questBar_t2.addPlayer(p);
                break;
            case "t1":
                QuestBuilder.questBar_t1.addPlayer(p);
                break;
        }
        loadPlaytime(p);
    }

    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        playtimeMap.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        HashMap<String, String> tutorialQuestMap = QuestBuilder.tutorialQuestMap;

        if (mysqlManager.getTutorialQuest(p.getUniqueId().toString()).equalsIgnoreCase("t4")) {
            if (mysqlManager.getSwordLevel(p.getUniqueId().toString()) >= 2) {
                mysqlManager.updateTutorialQuest(p.getUniqueId().toString(), "t3");
                QuestBuilder.questBar_t4.removePlayer(p);
                QuestBuilder.questBar_t3.addPlayer(p);
                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
            }
        }

        if (e.getClickedInventory() == null) {
            return;
        }

        if (e.getClickedInventory().equals(QuestBuilder.questMenu)) {
            e.setCancelled(true);
        }

        if (e.getClickedInventory().equals(QuestBuilder.rewardMenu)) {
            for (int i = 0; i != 10; i++) {
                if (rewardChoosenMap.containsKey(p.getUniqueId())) {
                    rewardChoosenMap.remove(p.getUniqueId());

                    if (e.getCurrentItem() != null) {
                        String material = null;
                        int amount = Integer.parseInt(e.getCurrentItem().getItemMeta().getDisplayName().substring(10));

                        if (amount == 0) {
                            e.setCancelled(true);
                            return;
                        }

                        switch (e.getCurrentItem().getType()) {
                            case Material.RAW_COPPER -> material = "raw_copper";
                            case Material.COPPER_INGOT -> material = "copper_ingot";
                            case Material.RAW_IRON -> material = "raw_iron";
                            case Material.IRON_INGOT -> material = "iron_ingot";
                            case Material.RAW_GOLD -> material = "raw_gold";
                            case Material.GOLD_INGOT -> material = "gold_ingot";
                            case Material.COAL -> material = "coal";
                            case Material.COBBLESTONE -> material = "cobblestone";
                            case Material.DIAMOND_ORE -> material = "diamond_ore";
                            case Material.DIAMOND -> material = "diamond";
                            case Material.STONE -> material = "stone";
                            case Material.NETHERITE_INGOT -> material = "netherite_ingot";
                            case Material.NETHERITE_SCRAP -> material = "netherite_scrap";
                        }
                        p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
                        mysqlManager.updateItemAmount(p.getUniqueId().toString(), material, mysqlManager.getItemAmount(p.getUniqueId().toString(), material)+amount);
                        updateItems(p);
                    }

                    p.closeInventory();
                    e.setCancelled(true);
                    break;
                }else {
                    rewardChoosenMap.put(p.getUniqueId(), 1);
                    if (e.getCurrentItem() != null) {
                        String material = null;
                        int amount = Integer.parseInt(e.getCurrentItem().getItemMeta().getDisplayName().substring(10));

                        if (amount == 0) {
                            e.setCancelled(true);
                            return;
                        }

                        switch (e.getCurrentItem().getType()) {
                            case Material.RAW_COPPER -> material = "raw_copper";
                            case Material.COPPER_INGOT -> material = "copper_ingot";
                            case Material.RAW_IRON -> material = "raw_iron";
                            case Material.IRON_INGOT -> material = "iron_ingot";
                            case Material.RAW_GOLD -> material = "raw_gold";
                            case Material.GOLD_INGOT -> material = "gold_ingot";
                            case Material.COAL -> material = "coal";
                            case Material.COBBLESTONE -> material = "cobblestone";
                            case Material.DIAMOND_ORE -> material = "diamond_ore";
                            case Material.DIAMOND -> material = "diamond";
                            case Material.STONE -> material = "stone";
                            case Material.NETHERITE_INGOT -> material = "netherite_ingot";
                            case Material.NETHERITE_SCRAP -> material = "netherite_scrap";
                        }
                        p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
                        mysqlManager.updateItemAmount(p.getUniqueId().toString(), material, mysqlManager.getItemAmount(p.getUniqueId().toString(), material)+amount);
                        updateItems(p);
                    }
                    e.setCancelled(true);
                    break;
                }
            }

        }

    }

    @EventHandler
    public void DrinkEvent(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        HashMap<String, String> tutorialQuestMap = QuestBuilder.tutorialQuestMap;

        if (e.getItem() == null) return;

        if (e.getItem().hasItemMeta()) {
            if (e.getItem().getItemMeta() instanceof PotionMeta potion  && mysqlManager.getTutorialQuest(p.getUniqueId().toString()).equalsIgnoreCase("t2")) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (p.hasPotionEffect(PotionEffectType.STRENGTH)) {
                            mysqlManager.updateTutorialQuest(p.getUniqueId().toString(), "t1");
                            QuestBuilder.questBar_t2.removePlayer(p);
                            QuestBuilder.questBar_t1.addPlayer(p);
                            p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
                        }
                    }
                }.runTaskLater(dungeonCrusher, 20L);
            }
        }


        //Es gibt nur essen und potions die man konsumieren kann deswegen ist ein iff ned nötig
        if (QuestBuilder.isTutorialDone(p)) {
            if (e.getItem().getItemMeta() instanceof  PotionMeta) {
                Daily.doQuest(p, Daily.DrinkQuestList);
                Weekly.doQuest(p, Weekly.DrinkQuestList);
                Monthly.doQuest(p, Monthly.DrinkQuestList);
            }else {
                Daily.doQuest(p, Daily.EatQuestList);
                Weekly.doQuest(p, Weekly.EatQuestList);
                Monthly.doQuest(p, Monthly.EatQuestList);
            }
        }
    }

    @EventHandler
    public void EntityDeathEvent(EntityDeathEvent e) {
        if (e.getEntity().getKiller() instanceof Player p) {
            if (QuestBuilder.isTutorialDone(p)) {
                Daily.doQuest(p, Daily.KillQuestList);
                Weekly.doQuest(p, Weekly.KillQuestList);
                Monthly.doQuest(p, Monthly.KillQuestList);
            }

            if (mysqlManager.getDungeonCountForPlayer(p.getUniqueId().toString()) >= 2 && mysqlManager.getTutorialQuest(p.getUniqueId().toString()).equalsIgnoreCase("t1")) {
                mysqlManager.updateTutorialQuest(p.getUniqueId().toString(), "t0");
                QuestBuilder.questBar_t1.removePlayer(p);
                p.sendMessage("§6Du hast das Tutorial anbgeschlossen! Viel spaß dir noch auf dem Dungeoncrusher!");
                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
            }
        }
    }

    @EventHandler
    public void EntityDamageEvent(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player p) {
            if (p.getOpenInventory().getTopInventory().equals(QuestBuilder.rewardMenu)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if (QuestBuilder.isTutorialDone(p)) {
            if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
                Daily.doQuest(p, Daily.MoveQuestList);
                Weekly.doQuest(p, Weekly.MoveQuestList);
                Monthly.doQuest(p, Monthly.MoveQuestList);
            }
        }
    }

    @EventHandler
    public void EntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player p) {
            int damage = (int) e.getDamage();

            if (QuestBuilder.isTutorialDone(p)) {
                for (int i = 0; i != damage; i++) {
                    Daily.doQuest(p, Daily.DamageQuestList);
                    Weekly.doQuest(p, Weekly.DamageQuestList);
                    Monthly.doQuest(p, Monthly.DamageQuestList);
                }
            }
        }
    }

    private void updateItems(Player player) {
        ItemStack rawcopper = new ItemStack(Material.RAW_COPPER);
        ItemMeta rawcoppermeta = rawcopper.getItemMeta();
        rawcoppermeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "raw_copper"));
        rawcoppermeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        rawcoppermeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        rawcopper.setItemMeta(rawcoppermeta);
        player.getInventory().setItem(9, rawcopper);

        ItemStack copperingot = new ItemStack(Material.COPPER_INGOT);
        ItemMeta copperingotmeta = copperingot.getItemMeta();
        copperingotmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "copper_ingot"));
        copperingotmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        copperingotmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        copperingot.setItemMeta(copperingotmeta);
        player.getInventory().setItem(10, copperingot);

        ItemStack rawgold = new ItemStack(Material.RAW_GOLD);
        ItemMeta rawgoldmeta = rawgold.getItemMeta();
        rawgoldmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "raw_gold"));
        rawgoldmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        rawgoldmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        rawgold.setItemMeta(rawgoldmeta);
        player.getInventory().setItem(18, rawgold);

        ItemStack goldingot = new ItemStack(Material.GOLD_INGOT);
        ItemMeta goldingotmeta = goldingot.getItemMeta();
        goldingotmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "gold_ingot"));
        goldingotmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        goldingotmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        goldingot.setItemMeta(goldingotmeta);
        player.getInventory().setItem(19, goldingot);

        ItemStack rawiron = new ItemStack(Material.RAW_IRON);
        ItemMeta rawironmeta = rawiron.getItemMeta();
        rawironmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "raw_iron"));
        rawironmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        rawironmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        rawiron.setItemMeta(rawironmeta);
        player.getInventory().setItem(27, rawiron);

        ItemStack ironingot = new ItemStack(Material.IRON_INGOT);
        ItemMeta ironingotmeta = ironingot.getItemMeta();
        ironingotmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "iron_ingot"));
        ironingotmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        ironingotmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ironingot.setItemMeta(ironingotmeta);
        player.getInventory().setItem(28, ironingot);

        ItemStack coal = new ItemStack(Material.COAL);
        ItemMeta coalmeta = coal.getItemMeta();
        coalmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "coal"));
        coalmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        coalmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        coal.setItemMeta(coalmeta);
        player.getInventory().setItem(22, coal);

        ItemStack cobblestone = new ItemStack(Material.COBBLESTONE);
        ItemMeta cobblestonemeta = cobblestone.getItemMeta();
        cobblestonemeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "cobblestone"));
        cobblestonemeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        cobblestonemeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        cobblestone.setItemMeta(cobblestonemeta);
        player.getInventory().setItem(17, cobblestone);

        ItemStack stone = new ItemStack(Material.STONE);
        ItemMeta stonemeta = stone.getItemMeta();
        stonemeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "stone"));
        stonemeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        stonemeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        stone.setItemMeta(stonemeta);
        player.getInventory().setItem(16, stone);

        ItemStack diamondore = new ItemStack(Material.DIAMOND_ORE);
        ItemMeta diamondoremeta = diamondore.getItemMeta();
        diamondoremeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "diamond_ore"));
        diamondoremeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        diamondoremeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        diamondore.setItemMeta(diamondoremeta);
        player.getInventory().setItem(26, diamondore);

        ItemStack diamond = new ItemStack(Material.DIAMOND);
        ItemMeta diamondmeta = diamond.getItemMeta();
        diamondmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "diamond"));
        diamondmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        diamondmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        diamond.setItemMeta(diamondmeta);
        player.getInventory().setItem(25, diamond);

        ItemStack netherite = new ItemStack(Material.NETHERITE_INGOT);
        ItemMeta netheritemeta = netherite.getItemMeta();
        netheritemeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "netherite_ingot"));
        netheritemeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        netheritemeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        netherite.setItemMeta(netheritemeta);
        player.getInventory().setItem(34, netherite);

        ItemStack netheritescrap = new ItemStack(Material.NETHERITE_SCRAP);
        ItemMeta netheritescrapmeta = netheritescrap.getItemMeta();
        netheritescrapmeta.setDisplayName("§bAnzahl ➝ §6" + mysqlManager.getItemAmount(player.getUniqueId().toString(), "netherite_scrap"));
        netheritescrapmeta.addEnchant(Enchantment.KNOCKBACK,1,true);
        netheritescrapmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        netheritescrap.setItemMeta(netheritescrapmeta);
        player.getInventory().setItem(35, netheritescrap);
    }

    public static void loadPlaytime(Player p) {
        if (Daily.getQuestKategorie(mysqlManager.getOrginQuest("daily1")).equalsIgnoreCase("Play")) {
            playtimeMap.put(p.getUniqueId(), mysqlManager.getPlayerTempQuest("daily1", p.getUniqueId().toString()));
        }
        if (Daily.getQuestKategorie(mysqlManager.getOrginQuest("daily2")).equalsIgnoreCase("Play")) {
            playtimeMap.put(p.getUniqueId(), mysqlManager.getPlayerTempQuest("daily2", p.getUniqueId().toString()));
        }
        if (Daily.getQuestKategorie(mysqlManager.getOrginQuest("daily3")).equalsIgnoreCase("Play")) {
            playtimeMap.put(p.getUniqueId(), mysqlManager.getPlayerTempQuest("daily3", p.getUniqueId().toString()));
        }
        if (Weekly.getQuestKategorie(mysqlManager.getOrginQuest("weekly1")).equalsIgnoreCase("Play")) {
            playtimeMap.put(p.getUniqueId(), mysqlManager.getPlayerTempQuest("weekly1", p.getUniqueId().toString()));
        }
        if (Weekly.getQuestKategorie(mysqlManager.getOrginQuest("weekly2")).equalsIgnoreCase("Play")) {
            playtimeMap.put(p.getUniqueId(), mysqlManager.getPlayerTempQuest("weekly2", p.getUniqueId().toString()));
        }
        if (Weekly.getQuestKategorie(mysqlManager.getOrginQuest("weekly3")).equalsIgnoreCase("Play")) {
            playtimeMap.put(p.getUniqueId(), mysqlManager.getPlayerTempQuest("weekly3", p.getUniqueId().toString()));
        }
        if (Monthly.getQuestKategorie(mysqlManager.getOrginQuest("monthly1")).equalsIgnoreCase("Play")) {
            playtimeMap.put(p.getUniqueId(), mysqlManager.getPlayerTempQuest("monthly1", p.getUniqueId().toString()));
        }
        if (Monthly.getQuestKategorie(mysqlManager.getOrginQuest("monthly2")).equalsIgnoreCase("Play")) {
            playtimeMap.put(p.getUniqueId(), mysqlManager.getPlayerTempQuest("monthly2", p.getUniqueId().toString()));
        }
        if (Monthly.getQuestKategorie(mysqlManager.getOrginQuest("monthly3")).equalsIgnoreCase("Play")) {
            playtimeMap.put(p.getUniqueId(), mysqlManager.getPlayerTempQuest("monthly3", p.getUniqueId().toString()));
        }
    }
}

package net.dbsgameplay.dungeoncrusher.listener;

import it.unimi.dsi.fastutil.Hash;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.Upgrades.SwordCategory;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.QuestBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.boss.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.lang.module.Configuration;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class QuestListener implements Listener {

    MYSQLManager mysqlManager;
    DungeonCrusher dungeonCrusher;

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

        if (tutorialQuest == null) {
            // Spieler existiert noch nicht, daher neuen Eintrag hinzufügen
            tutorialQuest = "t3";  // Standardwert für neue Spieler
            mysqlManager.updateTutorialQuest(p.getUniqueId().toString(), tutorialQuest);
        }

        // Null-Check ist jetzt unnötig, da wir tutorialQuest initialisieren
        switch (tutorialQuest) {
            case "t3":
                BossBar bossBar3 = QuestBuilder.bossBar;
                bossBar3.setTitle(tutorialQuestMap.get("t3"));
                bossBar3.addPlayer(p);
                break;
            case "t2":
                BossBar bossBar2 = QuestBuilder.bossBar;
                bossBar2.setTitle(tutorialQuestMap.get("t2"));
                bossBar2.addPlayer(p);
                break;
            case "t1":
                BossBar bossBar1 = QuestBuilder.bossBar;
                bossBar1.setTitle(tutorialQuestMap.get("t1"));
                bossBar1.addPlayer(p);
                break;
        }

        if (dungeonCrusher.getConfig().contains("quest." + p.getUniqueId().toString() + "." + "daily")) {
            playtimeMap.put(p.getUniqueId(), dungeonCrusher.getConfig().getInt("quest." + p.getUniqueId().toString() + "." + "daily"));
        }else {
            playtimeMap.put(p.getUniqueId(), 0);
        }

    }

    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        playtimeMap.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        HashMap<String, String> tutorialQuestMap = QuestBuilder.tutorialQuestMap;

        if (mysqlManager.getTutorialQuest(p.getUniqueId().toString()).equalsIgnoreCase("t3")) {
            if (mysqlManager.getSwordLevel(p.getUniqueId().toString()) >= 2) {
                mysqlManager.updateTutorialQuest(p.getUniqueId().toString(), "t2");
                BossBar bossBar2 = QuestBuilder.bossBar;
                bossBar2.setTitle(tutorialQuestMap.get("t2"));
                bossBar2.addPlayer(p);
            }
        }
    }

    @EventHandler
    public void DrinkEvent(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        HashMap<String, String> tutorialQuestMap = QuestBuilder.tutorialQuestMap;

        if (e.getItem() != null && e.getItem().hasItemMeta()) {
            if (e.getItem().getItemMeta() instanceof PotionMeta && mysqlManager.getTutorialQuest(p.getUniqueId().toString()).equalsIgnoreCase("t1")) {
                mysqlManager.updateTutorialQuest(p.getUniqueId().toString(), "t0");
                BossBar bossBar1 = QuestBuilder.bossBar;
                bossBar1.removePlayer(p);
            }
        }

    }

    @EventHandler
    public void EntityDeathEvent(EntityDeathEvent e) {
        if (mysqlManager.getOrginQuest("daily") != null && mysqlManager.getOrginQuest("daily").equalsIgnoreCase("d1") || mysqlManager.getOrginQuest("daily") != null && mysqlManager.getOrginQuest("daily").equalsIgnoreCase("d2")) {
            if (e.getEntity().getKiller() instanceof  Player p && mysqlManager.getTutorialQuest(p.getUniqueId().toString()).equalsIgnoreCase("t0")) {
                FileConfiguration cfg = DungeonCrusher.getInstance().getConfig();

                if (cfg.contains("quest." + p.getUniqueId().toString() + "." + "daily")) {
                    cfg.set("quest." + p.getUniqueId().toString() + "." + "daily", cfg.getInt("quest." + p.getUniqueId().toString() + "." + "daily")+1);
                }else {
                    cfg.set("quest." + p.getUniqueId().toString() + "." + "daily", 1);
                }

                if (mysqlManager.getOrginQuest("daily").equalsIgnoreCase("d1") && cfg.getInt("quest." + p.getUniqueId().toString() + "." + "daily") == 100) {
                    cfg.set("quest." + p.getUniqueId().toString() + "." + "daily", null);
                    mysqlManager.updatePlayerQuest("daily", true, p.getUniqueId().toString());

                    Random rdm = new Random();
                    mysqlManager.updateBalance(p.getUniqueId().toString(), mysqlManager.getBalance(p.getUniqueId().toString() + rdm.nextInt(90, 151)));

                }else if (mysqlManager.getOrginQuest("daily").equalsIgnoreCase("d2") && cfg.getInt("quest." + p.getUniqueId().toString() + "." + "daily") == 150) {
                    cfg.set("quest." + p.getUniqueId().toString() + "." + "daily", null);
                    mysqlManager.updatePlayerQuest("daily", true, p.getUniqueId().toString());

                    Random rdm = new Random();
                    mysqlManager.updateBalance(p.getUniqueId().toString(), mysqlManager.getBalance(p.getUniqueId().toString() + rdm.nextInt(100, 171)));
                }
            }
        }
    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if (mysqlManager.getOrginQuest("daily") != null && mysqlManager.getTutorialQuest(p.getUniqueId().toString()).equalsIgnoreCase("t0") && mysqlManager.getOrginQuest("daily").equalsIgnoreCase("d9") || mysqlManager.getOrginQuest("daily") != null && mysqlManager.getTutorialQuest(p.getUniqueId().toString()).equalsIgnoreCase("t0") && mysqlManager.getOrginQuest("daily").equalsIgnoreCase("d10")) {
            if(e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ()) {
                FileConfiguration cfg = DungeonCrusher.getInstance().getConfig();

                if (cfg.contains("quest." + p.getUniqueId().toString() + "." + "daily")) {
                    cfg.set("quest." + p.getUniqueId().toString() + "." + "daily", cfg.getInt("quest." + p.getUniqueId().toString() + "." + "daily")+1);
                }else {
                    cfg.set("quest." + p.getUniqueId().toString() + "." + "daily", 1);
                }

                if (mysqlManager.getOrginQuest("daily").equalsIgnoreCase("d9") && cfg.getInt("quest." + p.getUniqueId().toString() + "." + "daily") == 500) {
                    cfg.set("quest." + p.getUniqueId().toString() + "." + "daily", null);
                    mysqlManager.updatePlayerQuest("daily", true, p.getUniqueId().toString());

                    Random rdm = new Random();
                    mysqlManager.updateBalance(p.getUniqueId().toString(), mysqlManager.getBalance(p.getUniqueId().toString() + rdm.nextInt(90, 151)));

                }else if (mysqlManager.getOrginQuest("daily").equalsIgnoreCase("d10") && cfg.getInt("quest." + p.getUniqueId().toString() + "." + "daily") == 1000) {
                    cfg.set("quest." + p.getUniqueId().toString() + "." + "daily", null);
                    mysqlManager.updatePlayerQuest("daily", true, p.getUniqueId().toString());

                    Random rdm = new Random();
                    mysqlManager.updateBalance(p.getUniqueId().toString(), mysqlManager.getBalance(p.getUniqueId().toString() + rdm.nextInt(100, 171)));
                }
            }
        }
    }
}

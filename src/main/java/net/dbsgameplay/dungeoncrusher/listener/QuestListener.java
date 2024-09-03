package net.dbsgameplay.dungeoncrusher.listener;

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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class QuestListener implements Listener {

    MYSQLManager mysqlManager;

    public QuestListener(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        HashMap<String, String> tutorialQuestMap = QuestBuilder.tutorialQuestMap;
        String tutorialQuest = mysqlManager.getTutorialQuest(p.getUniqueId().toString());

        if (tutorialQuest == null) {
            // Spieler existiert noch nicht, daher neuen Eintrag hinzufügen
            tutorialQuest = "t1";  // Standardwert für neue Spieler
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

        if (e.getItem().getType() == Material.POTION && mysqlManager.getTutorialQuest(p.getUniqueId().toString()).equalsIgnoreCase("t1")) {
            if (p.hasPotionEffect(PotionEffectType.STRENGTH)) {
                mysqlManager.updateTutorialQuest(p.getUniqueId().toString(), "t0");
                BossBar bossBar1 = QuestBuilder.bossBar;
                bossBar1.removePlayer(p);
            }
        }
    }
}

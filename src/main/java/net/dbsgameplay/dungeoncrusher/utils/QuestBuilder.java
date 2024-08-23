package net.dbsgameplay.dungeoncrusher.utils;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class QuestBuilder {

    public QuestBuilder(DungeonCrusher dungeonCrusher, LocationConfigManager locationConfigManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.locationConfigManager = locationConfigManager;
    }
    private LocationConfigManager locationConfigManager;
    private static DungeonCrusher dungeonCrusher;
    public static Inventory questMenu = Bukkit.createInventory(null, 54, "§7Questmenü");

    public static Inventory getQuestmenü() {
        return questMenu;
    }

    public static void fillQuestmenü(Player player) {
        //tutorial
        //hinzufügen wenn das Tutorial abgeschlossen ist
        ItemStack tutorialStack = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta tutorialMeta = tutorialStack.getItemMeta();
        tutorialMeta.setItemName("§aTutorial");
        tutorialMeta.setLore(null);
        if (dungeonCrusher.getConfig().getBoolean("xxxIDontKnowxxx")) {
            tutorialMeta.setEnchantmentGlintOverride(true);
        }else {
            tutorialMeta.setEnchantmentGlintOverride(false);
        }
        tutorialStack.setItemMeta(tutorialMeta);
        questMenu.setItem(9, tutorialStack);

        //Daily
        //hinzufügen wenn die Daily abgeschlossen ist
        ItemStack dailyStack = new ItemStack(Material.CLOCK);
        ItemMeta dailylMeta = dailyStack.getItemMeta();
        dailylMeta.setItemName("§6Daily");
        dailylMeta.setLore(null);
        if (dungeonCrusher.getConfig().getBoolean("daily." + player.getUniqueId().toString())) {
            dailylMeta.setEnchantmentGlintOverride(true);
        }else {
            dailylMeta.setEnchantmentGlintOverride(false);
        }
        dailyStack.setItemMeta(dailylMeta);
        questMenu.setItem(11, dailyStack);

        //Weekly
        //hinzufügen wenn die Weekly abgeschlossen ist
        ItemStack weeklyStack = new ItemStack(Material.CLOCK);
        ItemMeta weeklylMeta = weeklyStack.getItemMeta();
        weeklylMeta.setItemName("§6Daily");
        weeklylMeta.setLore(null);
        if (dungeonCrusher.getConfig().getBoolean("weekly." + player.getUniqueId().toString())) {
            weeklylMeta.setEnchantmentGlintOverride(true);
        }else {
            weeklylMeta.setEnchantmentGlintOverride(false);
        }
        weeklyStack.setItemMeta(weeklylMeta);
        questMenu.setItem(13, weeklyStack);

        //Month
        //hinzufügen wenn die Month abgeschlossen ist
        ItemStack monthlyStack = new ItemStack(Material.CLOCK);
        ItemMeta monthlylMeta = monthlyStack.getItemMeta();
        monthlylMeta.setItemName("§6Daily");
        monthlylMeta.setLore(null);
        if (dungeonCrusher.getConfig().getBoolean("monthly." + player.getUniqueId().toString())) {
            monthlylMeta.setEnchantmentGlintOverride(true);
        }else {
            monthlylMeta.setEnchantmentGlintOverride(false);
        }
        monthlyStack.setItemMeta(weeklylMeta);
        questMenu.setItem(15, weeklyStack);
    }
}

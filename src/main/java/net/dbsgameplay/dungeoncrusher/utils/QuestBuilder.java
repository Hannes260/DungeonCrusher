package net.dbsgameplay.dungeoncrusher.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class QuestBuilder {


    public static Inventory getQuestmenü() {
        Inventory questMenu = Bukkit.createInventory(null, 54, "§7Questmenü");
        return questMenu;
    }

    public static void fillQuestmenü() {

    }
}

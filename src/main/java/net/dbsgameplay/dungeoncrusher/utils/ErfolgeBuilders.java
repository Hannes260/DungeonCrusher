package net.dbsgameplay.dungeoncrusher.utils;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.Shop.FoodCategory;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class ErfolgeBuilders {
    private static final UUID RANDOM_UUID = UUID.fromString("92864445-51c5-4c3b-9039-517c9927d1b4");
    public static Inventory inv;
    public static Inventory inventory;
    public static HashMap<String, String> titlesHashmap = new HashMap<>();
    public static List<String> moblist = new ArrayList<>();
    private static MYSQLManager mysqlManager;
    public static int killAmount = 0;
    public static int rewardAmount = 0;

    public ErfolgeBuilders(MYSQLManager mysqlManager) {
            this.mysqlManager = mysqlManager;
        }

    public static PlayerProfile getProfile(String url) {

        PlayerProfile playerProfile = Bukkit.createPlayerProfile(RANDOM_UUID); // Get a new player profile
        PlayerTextures textures = playerProfile.getTextures();
        URL urlObject;

        try {
            urlObject = new URL("https://textures.minecraft.net/texture/" + url); // The URL to the skin, for example: https://textures.minecraft.net/texture/18813764b2abc94ec3c3bc67b9147c21be850cdf996679703157f4555997ea63a
        } catch (MalformedURLException exception) {
            throw new RuntimeException("Invalid URL", exception);
        }

        textures.setSkin(urlObject); // Set the skin of the player profile to the URL
        playerProfile.setTextures(textures); // Set the textures back to the profile
        return playerProfile;
    }

    public static ItemStack createCustomMobHead(String textureURL, String name) {
        PlayerProfile profile = getProfile(textureURL);
        ItemStack mobHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) mobHead.getItemMeta();
        meta.setDisplayName(name);
        meta.setOwnerProfile(profile);
        mobHead.setItemMeta(meta);
        return mobHead;
    }

    public static Inventory getInventory(Player p, int invNum) {
        inv = Bukkit.createInventory(null, 54, "§7Erfolge");
        switch (invNum) {
            case 0 :
                fillInv1(p);
                break;
            case 1 :
                fillInv2(p);
                break;
            case -1 :
                //fillinv3
                break;
        }
        return inv;
    }

    public static void setOperators(Inventory inv) {
        inv.setItem(49, createCustomMobHead("3ed1aba73f639f4bc42bd48196c715197be2712c3b962c97ebf9e9ed8efa025", "§cSchließen"));
        inv.setItem(45, createCustomMobHead("1a1ef398a17f1af7477014517f7f141d886df41a32c738cc8a83fb50297bd921", "§7Vorherige Seite"));
        inv.setItem(53, createCustomMobHead("ac9c67a9f1685cd1da43e841fe7ebb17f6af6ea12a7e1f2722f5e7f0898db9f3", "§7Nächste Seite"));
    }

    public static void fillInv1(Player p) {
        inv.clear();
        setOperators(inv);

        for (String s : moblist) {
            if (s.equals(moblist.get(36))) break;
            int kills = mysqlManager.getPlayerMobKills(p.getUniqueId().toString(), s);
            double rawstufe = Math.ceil((double) kills/killAmount);
            int stufe = (int) rawstufe;

            if (stufe == 0) {
                stufe = 1;
            }

            if (stufe*killAmount == kills && stufe != 20) {
                stufe++;
            }

            int needed_kills = stufe*killAmount;
            ArrayList<String> lore = new ArrayList<>();

            ItemStack item = new ItemStack(Material.GRAY_DYE);;
            ItemMeta meta = item.getItemMeta();

            meta.setItemName(s);

            if (stufe < 5) {
                meta.setDisplayName("§f" + s + " - §7Stufe: " + stufe);
                item = new ItemStack(Material.GRAY_DYE);
            }else if (stufe < 10 && stufe > 5) {
                meta.setDisplayName("§f" + s + " - §eStufe: " + stufe);
                item = new ItemStack(Material.YELLOW_DYE);
            }else if (stufe < 15 && stufe > 10) {
                meta.setDisplayName("§f" + s + " - §6Stufe: " + stufe);
                item = new ItemStack(Material.ORANGE_DYE);
            }else if (stufe < 20 && stufe > 15) {
                meta.setDisplayName("§f" + s + " - §2Stufe: " + stufe);
                item = new ItemStack(Material.GREEN_DYE);
            } else if (stufe == 20) {
                meta.setDisplayName("§f" + s + " - §aStufe: " + stufe);
                item = new ItemStack(Material.LIME_DYE);
            }

            if (stufe == 20) {
                lore.add("§aDu hast diese Erfolgsreihe abgeschlossen!");
            }else {
                lore.add("§7Du brauchst noch §6" + (needed_kills-kills) + " §7Kills.");
            }

            lore.add("");
            lore.add("§e» Rechtsklick um die Titel zu sehen.");

            meta.setLore(lore);
            item.setItemMeta(meta);

            inv.addItem(item);
        }
    }

    public static void fillInv2(Player p) {
        inv.clear();
        setOperators(inv);

        for (int i = 37; i != moblist.size(); i++) {
            String s = moblist.get(i);

            int kills = mysqlManager.getPlayerMobKills(p.getUniqueId().toString(), s);
            double rawstufe = Math.ceil((double) kills/killAmount);
            int stufe = (int) rawstufe;

            if (stufe == 0) {
                stufe = 1;
            }

            if (stufe*killAmount == kills && stufe != 20) {
                stufe++;
            }

            int needed_kills = stufe*killAmount;
            ArrayList<String> lore = new ArrayList<>();

            ItemStack item = new ItemStack(Material.GRAY_DYE);;
            ItemMeta meta = item.getItemMeta();

            meta.setItemName(s);

            if (stufe < 5) {
                meta.setDisplayName("§f" + s + " - §7Stufe: " + stufe);
                item = new ItemStack(Material.GRAY_DYE);
            }else if (stufe < 10 && stufe > 5) {
                meta.setDisplayName("§f" + s + " - §eStufe: " + stufe);
                item = new ItemStack(Material.YELLOW_DYE);
            }else if (stufe < 15 && stufe > 10) {
                meta.setDisplayName("§f" + s + " - §6Stufe: " + stufe);
                item = new ItemStack(Material.ORANGE_DYE);
            }else if (stufe < 20 && stufe > 15) {
                meta.setDisplayName("§f" + s + " - §2Stufe: " + stufe);
                item = new ItemStack(Material.GREEN_DYE);
            } else if (stufe == 20) {
                meta.setDisplayName("§f" + s + " - §aStufe: " + stufe);
                item = new ItemStack(Material.LIME_DYE);
            }

            if (stufe == 20) {
                lore.add("§aDu hast diese Erfolgsreihe abgeschlossen!");
            }else {
                lore.add("§7Du brauchst noch §6" + (needed_kills-kills) + " §7Kills.");
            }

            lore.add("");
            lore.add("§e» Rechtsklick um die Titel zu sehen.");

            meta.setLore(lore);
            item.setItemMeta(meta);

            inv.addItem(item);
        }
    }

    public static void openTitleMenü(Player p, String mob) {
        int kills = mysqlManager.getPlayerMobKills(p.getUniqueId().toString(), mob);
        double rawstufe = Math.ceil((double) kills/killAmount);
        int stufe = (int) rawstufe;
        inventory = Bukkit.createInventory(null, 27, mob + " Titelmenü");
        User user = DungeonCrusher.api.getUserManager().getUser(p.getUniqueId());

        if (stufe == 0) {
            stufe = 1;
        }

        if (stufe*killAmount == kills && stufe != 20) {
            stufe++;
        }

        for (int i = 1; i != 21; i++) {
            ArrayList<String> arrayList = new ArrayList<>();
            ItemStack itemStack = new ItemStack(Material.NAME_TAG);
            ItemMeta itemMeta = itemStack.getItemMeta();
            String itemName = "Erfolg_" + mob + "_"+i;
            int needed_kills = i*killAmount;

            itemMeta.setItemName(itemName);
            if  (kills >= needed_kills) {
                itemMeta.setDisplayName("§fKill §d" + needed_kills + " §f" + mob + ".§a" + " ✅");

                arrayList.add("§7[" + "§8" + titlesHashmap.get(itemName) + "§7]");

            }else {
                itemMeta.setDisplayName("§fKille §d" + needed_kills + " §f" + mob + ".§a");

                arrayList.add("§7Du brauchst noch §6" + (needed_kills-kills) + " §7Kills.");

            }

            if (user.getCachedData().getMetaData().getSuffix() != null) {
                String rawSuffix =user.getCachedData().getMetaData().getSuffix();
                String suffix = user.getCachedData().getMetaData().getSuffix().substring(0, rawSuffix.length() -3 ).substring(6);

                if (suffix.equalsIgnoreCase(titlesHashmap.get(itemName))) {
                    itemMeta.addEnchant(Enchantment.KNOCKBACK,1, true);
                }
            }

            itemMeta.setLore(arrayList);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemStack.setItemMeta(itemMeta);

            inventory.addItem(itemStack);
        }
        inventory.setItem(26, createCustomMobHead("3ed1aba73f639f4bc42bd48196c715197be2712c3b962c97ebf9e9ed8efa025", "§cZurück"));
        p.openInventory(inventory);
    }

    public static void reward(int value, Player p) {
        FoodCategory foodCategory = new FoodCategory(mysqlManager);

        p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 1);
        p.sendActionBar("§6Du hast einen Erfolg abgeschlossen!");
        foodCategory.addMoney(p, value);
        p.sendMessage("»Erfolge §7[§a+§7] §6" + value + "€");
        mysqlManager.updateItemAmount(p.getUniqueId().toString(), "raw_copper", mysqlManager.getItemAmount(p.getUniqueId().toString(), "raw_copper")+value/10);
        p.sendMessage("»Erfolge §7[§a+§7] §6RohKupfer §7[§a" + value/10 + "x§7]");
        mysqlManager.updateItemAmount(p.getUniqueId().toString(), "copper_ingot", mysqlManager.getItemAmount(p.getUniqueId().toString(), "copper_ingot")+value/10);
        p.sendMessage("»Erfolge §7[§a+§7] §6Kupferbarren §7[§a" + value/10 + "x§7]");
        mysqlManager.updateItemAmount(p.getUniqueId().toString(), "coal", mysqlManager.getItemAmount(p.getUniqueId().toString(), "coal")+value/10);
        p.sendMessage("»Erfolge §7[§a+§7] §6Kohle §7[§a" + value/10 + "x§7]");
    }


    //Geld, Schaden, Materialien, Minibosse
}

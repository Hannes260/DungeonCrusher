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

    public static Inventory getInventory(int ebene, Player p) {
        String mob;
        if (ebene == 0) {
            mob = moblist.getFirst();
        }else {
            mob = moblist.get(ebene-1);
        }
        inv = Bukkit.createInventory(null, 54, "§7Erfolge                    §3" + ebene + "§7. Ebene");
        fillInv(p, mob);
        return inv;
    }

    public static void setOperators(Inventory inv) {
        inv.setItem(49, createCustomMobHead("3ed1aba73f639f4bc42bd48196c715197be2712c3b962c97ebf9e9ed8efa025", "§cSchließen"));
        inv.setItem(45, createCustomMobHead("1a1ef398a17f1af7477014517f7f141d886df41a32c738cc8a83fb50297bd921", "§7Vorherige Seite"));
        inv.setItem(53, createCustomMobHead("ac9c67a9f1685cd1da43e841fe7ebb17f6af6ea12a7e1f2722f5e7f0898db9f3", "§7Nächste Seite"));
    }

    public static void fillInv(Player p, String mob) {
        int kills = mysqlManager.getPlayerMobKills(p.getUniqueId().toString(), mob);
        User user = DungeonCrusher.api.getUserManager().getUser(p.getUniqueId());

        inv.clear();
        setOperators(inv);

        for (int i = 1; i != 21; i++) {
            int neededKills = killAmount*i;
            String itemName = "Erfolg_" + mob + "_"+i;
            ArrayList<String> arrayList = new ArrayList<>();

            ItemStack itemStack = new ItemStack(Material.NAME_TAG);
            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setItemName(itemName);

            if  (kills >= neededKills) {
                if (mob.endsWith("e") || mob.endsWith("l")) {
                    itemMeta.setDisplayName("§fKill §d" + neededKills + " §f" + mob + ".§a" + " ✅");
                }else {
                    itemMeta.setDisplayName("§fKill §d" + neededKills + " §f" + mob + "e.§a" + " ✅");
                }

                arrayList.add("§7[" + "§8" + titlesHashmap.get(itemName) + "§7]");

            }else {
                if (mob.endsWith("e") || mob.endsWith("l")) {
                    itemMeta.setDisplayName("§fKill §d" + neededKills + " §f" + mob + ".§a");
                }else {
                    itemMeta.setDisplayName("§fKill §d" + neededKills + " §f" + mob + "e.§a");
                }

                arrayList.add("§7Du brauchst noch §6" + (neededKills-kills) + " §7Kills.");

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

            int[] slots = {0,9,18,27,28,29,20,11,2,3,4,13,22,31,32,33,24,15,6,7};
            inv.setItem(slots[i-1], itemStack);
        }

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

}

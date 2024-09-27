package net.dbsgameplay.dungeoncrusher.utils;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.Shop.FoodCategory;
import net.dbsgameplay.dungeoncrusher.listener.DungeonListener;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Manager.DungeonManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
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

@SuppressWarnings("ALL")
public class ErfolgeBuilders {
    private static final UUID RANDOM_UUID = UUID.fromString("92864445-51c5-4c3b-9039-517c9927d1b4");
    public static Inventory inv;
    public static HashMap<String, String> titlesHashmap = new HashMap<String, String>();
    public static String[] moblist = {"Schaf","Schweine","Kuh","Pferd","Esel","Kamel","Dorfbewohner","Ziegen", "Lama","Mooshroom","Maultier","Schnüffler","Panda","Schildkröten","Ozelot","Axolotl","Fuchs", "Katzen","Huhn","Frosch","Kaninchen","Silberfisch","Diener","Eisbären","Zombiepferd","Wolf", "Zombiedorfbewohner","Schneegolem","Skelett","Ertrunkenen","Wüstenzombie","Spinnen","Zombie","Eiswanderer","Creeper", "Höhlenspinnen","Endermiten","Schreiter","Lohen","Skelettpferde","Hexen","Schleim","Magmawürfel","Enderman", "Piglin","Zombiefizierter_Piglin", "Piglin_Barbaren","Plünderer","Hoglin","Magier","Ghast","Wither_Skelett","Zoglin","Verwüster","Eisengolem", "Wärter"};
            
    private static MYSQLManager mysqlManager;

    public ErfolgeBuilders(MYSQLManager mysqlManager) {
            this.mysqlManager = mysqlManager;
        }
//    public static int ebene;

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

        switch (ebene) {
            default:
                return null;
            case 1:
                //noinspection deprecation
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Schaf");
                setOperators(inv);
                break;
            case 2:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Schweine");
                setOperators(inv);
                break;
            case 3:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Kuh");
                setOperators(inv);
                break;
            case 4:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Pferd");
                setOperators(inv);
                break;
            case 5:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Esel");
                setOperators(inv);
                break;
            case 6:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Kamel");
                setOperators(inv);
                break;
            case 7:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Frosch");
                setOperators(inv);
                break;
            case 8:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Ziegen");
                setOperators(inv);
                break;
            case 9:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Lama");
                setOperators(inv);
                break;
            case 10:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Mooshroom");
                setOperators(inv);
                break;
            case 11:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Maultier");
                setOperators(inv);
                break;
            case 12:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Schnüffler");
                setOperators(inv);
                break;
            case 13:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Panda");
                setOperators(inv);
                break;
            case 14:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Schildkröten");
                setOperators(inv);
                break;
            case 15:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Ozelot");
                setOperators(inv);
                break;
            case 16:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Axolotl");
                setOperators(inv);
                break;
            case 17:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Fuchs");
                setOperators(inv);
                break;
            case 18:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Katzen");
                setOperators(inv);
                break;
            case 19:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Huhn");
                setOperators(inv);
                break;
            case 20:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Dorfbewohner");
                setOperators(inv);
                break;
            case 21:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Kaninchen");
                setOperators(inv);
                break;
            case 22:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Gürteltier");
                setOperators(inv);
                break;
            case 23:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Silberfisch");
                setOperators(inv);
                break;
            case 24:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Diener");
                setOperators(inv);
                break;
            case 25:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Eisbären");
                setOperators(inv);
                break;
            case 26:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Zombiepferd");
                setOperators(inv);
                break;
            case 27:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Wolf");
                setOperators(inv);
                break;
            case 28:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Zombiedorfbewohner");
                setOperators(inv);
                break;
            case 29:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Schneegolem");
                setOperators(inv);
                break;
            case 30:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Böe");
                setOperators(inv);
                break;
            case 31:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Skelett");
                setOperators(inv);
                break;
            case 32:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Ertrunkenen");
                setOperators(inv);
                break;
            case 33:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Wüstenzombie");
                setOperators(inv);
                break;
            case 34:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Spinnen");
                setOperators(inv);
                break;
            case 35:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Zombie");
                setOperators(inv);
                break;
            case 36:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Eiswanderer");
                setOperators(inv);
                break;
            case 37:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Creeper");
                setOperators(inv);
                break;
            case 38:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Höhlenspinnen");
                setOperators(inv);
                break;
            case 39:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Endermiten");
                setOperators(inv);
                break;
            case 40:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Schreiter");
                setOperators(inv);
                break;
            case 41:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Lohen");
                setOperators(inv);
                break;
            case 42:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Skelettpferde");
                setOperators(inv);
                break;
            case 43:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Hexen");
                setOperators(inv);
                break;
            case 44:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Schleim");
                setOperators(inv);
                break;
            case 45:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Magmawürfel");
                setOperators(inv);
                break;
            case 46:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Enderman");
                setOperators(inv);
                break;
            case 47:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Piglin");
                setOperators(inv);
                break;
            case 48:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Sumpfskelett");
                setOperators(inv);
                break;
            case 49:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Zombiefizierter_Piglin");
                setOperators(inv);
                break;
            case 50:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Piglin_Barbaren");
                setOperators(inv);
                break;
            case 51:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Plünderer");
                setOperators(inv);
                break;
            case 52:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Hoglin");
                setOperators(inv);
                break;
            case 53:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Magier");
                setOperators(inv);
                break;
            case 54:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Ghast");
                setOperators(inv);
                break;
            case 55:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Wither_Skelett");
                setOperators(inv);
                break;
            case 56:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Zoglin");
                setOperators(inv);
                break;
            case 57:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Verwüster");
                setOperators(inv);
                break;
            case 58:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Eisengolem");
                setOperators(inv);
                break;
            case 59:
                inv = Bukkit.createInventory(null, 54, "§fErfolge - Ebene §3" + ebene);
                fillInv(p, "Wärter");
                setOperators(inv);
                break;

        }
        return inv;
    }

    public static void setOperators(Inventory inv) {
        inv.setItem(49, createCustomMobHead("3ed1aba73f639f4bc42bd48196c715197be2712c3b962c97ebf9e9ed8efa025", "§cSchließen"));
        inv.setItem(45, createCustomMobHead("1a1ef398a17f1af7477014517f7f141d886df41a32c738cc8a83fb50297bd921", "§7Vorherige Seite"));
        inv.setItem(53, createCustomMobHead("ac9c67a9f1685cd1da43e841fe7ebb17f6af6ea12a7e1f2722f5e7f0898db9f3", "§7Nächste Seite"));
    }

    public static void fillInv(Player p, String mob) {
        int kills = 0;

        inv.clear();
        setOperators(inv);

        kills = mysqlManager.getPlayerMobKills(p.getUniqueId().toString(), mob);

        for (int i = 1; i != 21; i++) {
            int neededKillsForComplete = 150*i;

            ItemStack itemStack = new ItemStack(Material.NAME_TAG);
            ItemMeta itemMeta = itemStack.getItemMeta();
            ArrayList<String> arrayList = new ArrayList<String>();
            if (mob.endsWith("e")) {
                arrayList.add("§bKill §d" + neededKillsForComplete + " §b" + mob + ".");
            }else {
                arrayList.add("§bKill §d" + neededKillsForComplete + " §b" + mob + "e.");
            }
            if (kills >= neededKillsForComplete) {
                itemMeta.setDisplayName("§aErfolg " + i + " ✅");
            }else {
                itemMeta.setDisplayName("§fErfolg " + i);
                arrayList.add("§7Du brauchst noch §6" + (neededKillsForComplete-kills) + " §7Kills");
            }
            String mobID = "Erfolg_" + mob + "_" + i;
            arrayList.add("§7[" + "§8" + titlesHashmap.get(mobID) + "§7]");
            itemMeta.setItemName("Erfolg_" + mob + "_"+i);
            itemMeta.setLore(arrayList);
            itemMeta.setEnchantmentGlintOverride(false);
            if (DungeonCrusher.api.getPlayerAdapter(Player.class).getUser(p).getCachedData().getMetaData().getSuffix() != null) {
                String suffix = DungeonCrusher.api.getPlayerAdapter(Player.class).getUser(p).getCachedData().getMetaData().getSuffix();
                if (suffix.substring(0, suffix.length() -3 ).substring(6).equalsIgnoreCase(titlesHashmap.get(itemMeta.getItemName()))) {
                    itemMeta.setEnchantmentGlintOverride(true);
                }
            }
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
        p.sendMessage(" §7[§a+§7] §6" + value + "€");
        mysqlManager.updateItemAmount(p.getUniqueId().toString(), "raw_copper", mysqlManager.getItemAmount(p.getUniqueId().toString(), "raw_copper")+value/10);
        p.sendMessage(" §7[§a+§7] §6RohKupfer §7[§a" + value + "x§7]");
        mysqlManager.updateItemAmount(p.getUniqueId().toString(), "copper_ingot", mysqlManager.getItemAmount(p.getUniqueId().toString(), "copper")+value/10);
        p.sendMessage(" §7[§a+§7] §6Kupferbarren §7[§a" + value + "x§7]");
        mysqlManager.updateItemAmount(p.getUniqueId().toString(), "coal", mysqlManager.getItemAmount(p.getUniqueId().toString(), "coal")+value/10);
        p.sendMessage(" §7[§a+§7] §6Kohle §7[§a" + value + "x§7]");
    }

}

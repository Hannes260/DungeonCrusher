package de.hannezhd.dungeoncrusher.utils;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerHeadAPI {
    private static Class<?> skullMetaClass;
    private static Class<?> tileEntityClass;
    private static Class<?> blockPositionClass;
    private static int mcVersion;

    static {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        mcVersion = Integer.parseInt(version.replaceAll("[^0-9]", ""));

        try {
            skullMetaClass = Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftMetaSkull");
            tileEntityClass = Class.forName("net.minecraft.server." + version + ".TileEntitySkull");
            if (mcVersion > 174) {
                blockPositionClass = Class.forName("net.minecraft.server." + version + ".BlockPosition");
            } else {
                blockPositionClass = null;
            }
        } catch (ClassNotFoundException var2) {
            var2.printStackTrace();
        }

    }

    public static ItemStack getSkull(String skinURL) {
        return getSkull(skinURL, 1);
    }

    public static ItemStack getSkull(String skinURL, int amount) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, amount, (short)3);
        SkullMeta meta = (SkullMeta)skull.getItemMeta();

        try {
            Field profileField = skullMetaClass.getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, getProfile(skinURL));
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        skull.setItemMeta(meta);
        return skull;
    }

    public static boolean setBlock(Location loc, String skinURL) {
        return setBlock(loc.getBlock(), skinURL);
    }

    public static boolean setBlock(Block block, String skinURL) {
        boolean flag = block.getType() == Material.PLAYER_HEAD;
        if (!flag) {
            block.setType(Material.PLAYER_HEAD);
        }

        try {
            Object nmsWorld = block.getWorld().getClass().getMethod("getHandle").invoke(block.getWorld());
            Object tileEntity = null;
            Method getTileEntity;
            if (mcVersion <= 174) {
                getTileEntity = nmsWorld.getClass().getMethod("getTileEntity", Integer.TYPE, Integer.TYPE, Integer.TYPE);
                tileEntity = tileEntityClass.cast(getTileEntity.invoke(nmsWorld, block.getX(), block.getY(), block.getZ()));
            } else {
                getTileEntity = nmsWorld.getClass().getMethod("getTileEntity", blockPositionClass);
                tileEntity = tileEntityClass.cast(getTileEntity.invoke(nmsWorld, getBlockPositionFor(block.getX(), block.getY(), block.getZ())));
            }

            tileEntityClass.getMethod("setGameProfile", GameProfile.class).invoke(tileEntity, getProfile(skinURL));
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return !flag;
    }

    private static Object getProfile(String skinURL) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), (String)null);
        String base64encoded = Base64.getEncoder().encodeToString((new String("{textures:{SKIN:{url:\"" + skinURL + "\"}}}")).getBytes());
        Property property = new Property("textures", base64encoded);
        profile.getProperties().put("textures", property);
        return profile;
    }

    private static Object getBlockPositionFor(int x, int y, int z) {
        Object blockPosition = null;

        try {
            Constructor<?> cons = blockPositionClass.getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE);
            blockPosition = cons.newInstance(x, y, z);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return blockPosition;
    }
}

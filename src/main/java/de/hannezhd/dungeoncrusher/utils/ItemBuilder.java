package de.hannezhd.dungeoncrusher.utils;

import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
    private ItemMeta itemMeta;
    private ItemStack itemStack;

    public ItemBuilder(Material mat) {
        this.itemStack = new ItemStack(mat);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder setDisplayname(String s) {
        this.itemMeta.setDisplayName(s);
        return this;
    }

    public ItemBuilder setLocalizedName(String s) {
        this.itemMeta.setLocalizedName(s);
        return this;
    }

    public ItemBuilder setLore(String... s) {
        this.itemMeta.setLore(Arrays.asList(s));
        return this;
    }

    public ItemBuilder setUnbreakable(boolean s) {
        this.itemMeta.setUnbreakable(s);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... s) {
        this.itemMeta.addItemFlags(s);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        this.itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    public String toString() {
        return "ItemBuilder{itemMeta=" + this.itemMeta + ", itemStack=" + this.itemStack + '}';
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }

}
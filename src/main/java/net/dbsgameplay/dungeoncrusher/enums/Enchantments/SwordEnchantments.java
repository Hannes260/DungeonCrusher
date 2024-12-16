package net.dbsgameplay.dungeoncrusher.enums.Enchantments;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.Upgrades.SwordCategory;
import net.dbsgameplay.dungeoncrusher.interfaces.EnchantmentCategory;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwordEnchantments implements EnchantmentCategory {
    private final MYSQLManager mysqlManager;
    private final ScoreboardBuilder scoreboardBuilder;
    private final Map<Integer, ShopItem> items;

    public SwordEnchantments(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;

        this.scoreboardBuilder = new ScoreboardBuilder(DungeonCrusher.getInstance());

        this.items = new HashMap<>();
        items.put(0, new ShopItem("",Material.BOOK, 500, Arrays.asList("")));
        items.put(9, new ShopItem("",Material.BOOK, 500, Arrays.asList("")));
        items.put(18, new ShopItem("",Material.BOOK, 500, Arrays.asList("")));
        items.put(27, new ShopItem("",Material.BOOK, 500, Arrays.asList("")));
        items.put(36, new ShopItem("",Material.BOOK, 500, Arrays.asList("")));
    }

    @Override
    public void openMenu(Player player) {
        String DisplayName = "§f<shift:-8>%oraxen_upgrade_enchantment%";
        DisplayName = PlaceholderAPI.setPlaceholders(player, DisplayName);
        Inventory inv = Bukkit.createInventory(null, 9 * 6, DisplayName);
        for (int slot : items.keySet()) {
            SwordEnchantments.ShopItem shopItem = items.get(slot);
            ItemStack itemStack = new ItemStack(shopItem.material);
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(shopItem.displayName);
            meta.setCustomModelData(500);
        }

    }

    @Override
    public void handleItemClick(Player player, ItemStack clickedItem) {

    }
    private static class ShopItem {
        private final String displayName;
        private final Material material;
        private final int modeldata;
        private final List<String> lore;

        private ShopItem(String displayName, Material material, int ModelData, List<String> lore) {
            this.displayName = displayName;
            this.material = material;
            this.modeldata = ModelData;
            this.lore = lore;
        }
    }
}
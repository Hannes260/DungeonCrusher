package net.dbsgameplay.dungeoncrusher.enums.Enchantments;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.interfaces.EnchantmentCategory;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.ScoreboardBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
        items.put(0, new ShopItem("",Material.PAPER, 100, Arrays.asList("")));
    }

    @Override
    public void openMenu(Player player) {

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
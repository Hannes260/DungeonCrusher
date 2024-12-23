package net.dbsgameplay.dungeoncrusher.listener.Upgrades;
import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.enums.Upgrades.ArmorCategory;
import net.dbsgameplay.dungeoncrusher.enums.Upgrades.SwordCategory;
import net.dbsgameplay.dungeoncrusher.interfaces.UpgradeCategory;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.listener.Navigator.NavigatorListener;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.upgrades.UpgradeManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class UpgradeListener implements Listener {
    private final LocationConfigManager locationConfigManager;
    private final MYSQLManager mysqlManager;
    private final DungeonCrusher dungeonCrusher;

    public UpgradeListener(DungeonCrusher dungeonCrusher, LocationConfigManager locationConfigManager, MYSQLManager mysqlManager){
        this.dungeonCrusher = dungeonCrusher;
        this.locationConfigManager = locationConfigManager;
        this.mysqlManager = mysqlManager;
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedInventory == null || clickedItem == null || clickedItem.getType().isAir()) {
            return;
        }

        if (clickedItem == null || clickedItem.getItemMeta() == null || clickedItem.getItemMeta().getDisplayName() == null) {
            return; // Kein gültiges Item, also nichts tun
        }
        String title = event.getView().getTitle();
        String DisplayNameSwordUpgrade = "§f<shift:-8>%nexo_upgrade_sword%";
        DisplayNameSwordUpgrade = PlaceholderAPI.setPlaceholders(player, DisplayNameSwordUpgrade);

        String DisplayNameArmorUpgrade = "§f<shift:-8>%nexo_upgrade_armor%";
        DisplayNameArmorUpgrade = PlaceholderAPI.setPlaceholders(player, DisplayNameArmorUpgrade);

        String DisplayName = "§f<shift:-8>%nexo_upgrade%";
        DisplayName = PlaceholderAPI.setPlaceholders(player, DisplayName);
        // Prüfe, ob der Titel des Inventars mit dem Upgrade-Inventar übereinstimmt
        if (DisplayName.equalsIgnoreCase(title)) {
            event.setCancelled(true);
            String categoryName = clickedItem.getItemMeta().getDisplayName().toLowerCase();
            UpgradeCategory category = UpgradeManager.getCategory(categoryName);
            if (category != null) {
                category.openMenu(player);
                player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1.0f, 1.0f);
            } else if ("§cZurück".equals(clickedItem.getItemMeta().getDisplayName())) {
                LocationConfigManager locationConfigManager = new LocationConfigManager(DungeonCrusher.getInstance());
                NavigatorListener navigatorListener = new NavigatorListener(dungeonCrusher, locationConfigManager, mysqlManager);
                navigatorListener.openNavigator(player);
                player.playSound(player.getLocation(), Sound.BLOCK_BARREL_CLOSE, 1.0f, 1.0f);
            }
        }else if (title.equalsIgnoreCase(DisplayNameSwordUpgrade)) {
            event.setCancelled(true);
            SwordCategory swordCategory = new SwordCategory(mysqlManager);
            swordCategory.handleItemClick(player, clickedItem);
        }else if (title.equalsIgnoreCase(DisplayNameArmorUpgrade)) {
            event.setCancelled(true);
            ArmorCategory armorCategory = new ArmorCategory(mysqlManager);
            armorCategory.handleItemClick(player, clickedItem);
        }else if (title.startsWith("§7➢")) {  // Annahme: Kategorien-Inventare haben einen spezifischen Titelstart
            event.setCancelled(true);
            UpgradeCategory category = UpgradeManager.getCategory(title.toLowerCase());
            if (category != null) {
                category.handleItemClick(player, clickedItem);
            }
        }
    }
}

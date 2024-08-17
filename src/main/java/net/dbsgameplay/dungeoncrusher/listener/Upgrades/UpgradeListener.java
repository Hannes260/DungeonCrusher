package net.dbsgameplay.dungeoncrusher.listener.Upgrades;
import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.Commands.interfaces.UpgradeCategory;
import net.dbsgameplay.dungeoncrusher.utils.upgrades.UpgradeManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class UpgradeListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedInventory == null || clickedItem == null || clickedItem.getType().isAir()) {
            return;
        }

        String title = event.getView().getTitle();
        System.out.println(title);
        String DisplayName = "§f<shift:-8>%oraxen_upgrade%";
        DisplayName = PlaceholderAPI.setPlaceholders(player, DisplayName);
        if (event.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD || event.getClick() == ClickType.NUMBER_KEY) {
            event.setCancelled(true);
            return;
        }
        // Prüfe, ob der Titel des Inventars mit dem Upgrade-Inventar übereinstimmt
        if (DisplayName.equalsIgnoreCase(title)) {
            event.setCancelled(true);
            String categoryName = clickedItem.getItemMeta().getDisplayName().toLowerCase();
            UpgradeCategory category = UpgradeManager.getCategory(categoryName);
            if (category != null) {
                category.openMenu(player);
                player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1.0f, 1.0f);
            } else if ("§cSchließen".equals(clickedItem.getItemMeta().getDisplayName())) {
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.BLOCK_BARREL_CLOSE, 1.0f, 1.0f);
            }
        } else if (title.startsWith("§7➢")) {  // Annahme: Kategorien-Inventare haben einen spezifischen Titelstart
            event.setCancelled(true);
            UpgradeCategory category = UpgradeManager.getCategory(title.toLowerCase());
            if (category != null) {
                category.handleItemClick(player, clickedItem);
            }
        }
    }
}

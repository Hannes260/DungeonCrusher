package net.dbsgameplay.dungeoncrusher.listener.shops;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.interfaces.ShopCategory;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.Shop.ExchangeCategory;
import net.dbsgameplay.dungeoncrusher.enums.Shop.FoodCategory;
import net.dbsgameplay.dungeoncrusher.enums.Shop.PotionCategory;
import net.dbsgameplay.dungeoncrusher.listener.Navigator.NavigatorListener;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.shops.ShopManager;
import org.bukkit.Material;
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
import org.bukkit.inventory.meta.ItemMeta;

public class ShopListener implements Listener {
    private final LocationConfigManager locationConfigManager;
    private final MYSQLManager mysqlManager;
    private final DungeonCrusher dungeonCrusher;

    public ShopListener(DungeonCrusher dungeonCrusher, LocationConfigManager locationConfigManager, MYSQLManager mysqlManager){
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

        if (event.getClick() == ClickType.NUMBER_KEY) {
            // Abbrechen der Aktion
            event.setCancelled(true);

            // Überprüfen, ob der Slot ein Slot im Spielerinventar ist (nicht nur Slot 1, sondern das ganze Inventar)
            if (event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.PLAYER) {
                // Verhindern, dass Gegenstände per Hotkey verschoben werden
                event.setCancelled(true);
            }
        }
        String displayName = "§f<shift:-8>%oraxen_shop%";
        displayName = PlaceholderAPI.setPlaceholders(player, displayName);
        String title = event.getView().getTitle();

        String displayNameFood = "§f<shift:-8>%oraxen_food_gui%";
        displayNameFood = PlaceholderAPI.setPlaceholders(player, displayNameFood);

        String displayNamePotion = "§f<shift:-8>%oraxen_potion_gui%";
        displayNamePotion = PlaceholderAPI.setPlaceholders(player, displayNamePotion);

        String displayNameExchange = "§f<shift:-8>%oraxen_exchange_gui%";
        displayNameExchange = PlaceholderAPI.setPlaceholders(player, displayNameExchange);

        // Originales Shop-Inventar
        if (displayName.equalsIgnoreCase(title)) {
            event.setCancelled(true);
            String categoryName = clickedItem.getItemMeta().getDisplayName().toLowerCase();
            ShopCategory category = ShopManager.getCategory(categoryName);
            if (category != null) {
                category.openMenu(player);
                player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1.0f, 1.0f);
            } else if ("§cZurück".equals(clickedItem.getItemMeta().getDisplayName())) {
                LocationConfigManager locationConfigManager = new LocationConfigManager(DungeonCrusher.getInstance());
                NavigatorListener navigatorListener = new NavigatorListener(dungeonCrusher, locationConfigManager, mysqlManager);
                navigatorListener.openNavigator(player);
                player.playSound(player.getLocation(), Sound.BLOCK_BARREL_CLOSE, 1.0f, 1.0f);
            }
        } else if (title.equalsIgnoreCase(displayNameFood)) {
            event.setCancelled(true);
            FoodCategory customInventory = new FoodCategory(mysqlManager);
            if (event.getClick().isShiftClick() && event.getClick().isLeftClick()) {
                customInventory.handleShiftClick(player, clickedItem);
            } else {
                customInventory.handleItemClick(player, clickedItem);
            }
        } else if (title.equalsIgnoreCase(displayNamePotion)) {
            event.setCancelled(true);
            PotionCategory customInventorypotion = new PotionCategory(mysqlManager);
            if (event.getClick().isShiftClick() && event.getClick().isLeftClick()) {
                customInventorypotion.handleShiftClick(player, clickedItem);
            } else {
                customInventorypotion.handleItemClick(player, clickedItem);
            }
        } else if (title.equalsIgnoreCase(displayNameExchange)) {
            event.setCancelled(true);
            ExchangeCategory customInventoryexchange = new ExchangeCategory(mysqlManager);
            if (event.getClick().isShiftClick() && event.getClick().isLeftClick()) {
                customInventoryexchange.handleShiftClick(player, clickedItem);
            } else {
                customInventoryexchange.handleItemClick(player, clickedItem);
            }
        }else if (ShopManager.getCategory(title.toLowerCase()) != null) {
            event.setCancelled(true);
            ShopCategory category = ShopManager.getCategory(title.toLowerCase());
            if (category != null) {
                if (event.getClick().isShiftClick() && event.getClick().isLeftClick()) {
                    category.handleShiftClick(player, clickedItem); // Shift + Linksklick
                } else {
                    category.handleItemClick(player, clickedItem); // Normaler Linksklick
                }
            } else {
                handleExchangeClick(player, clickedItem); // Austausch-Items verarbeiten
            }
        }
    }

    private void handleExchangeClick(Player player, ItemStack clickedItem) {
        ItemMeta meta = clickedItem.getItemMeta();
        if (meta == null) return;

        String displayName = meta.getDisplayName();
        switch (displayName) {
            case "20x Kupferbarren":
                exchangeItems(player, Material.COPPER_INGOT, 20, Material.RAW_COPPER, 100);
                break;
            case "200x Kupferbarren":
                exchangeItems(player, Material.COPPER_INGOT, 200, Material.RAW_COPPER, 1000);
                break;
            default:
                player.sendMessage("Item not recognized for exchange.");
                break;
        }
    }

    private void exchangeItems(Player player, Material from, int fromAmount, Material to, int toAmount) {
        Inventory playerInventory = player.getInventory();
        if (playerInventory.containsAtLeast(new ItemStack(from), fromAmount)) {
            playerInventory.removeItem(new ItemStack(from, fromAmount));
            playerInventory.addItem(new ItemStack(to, toAmount));
            player.sendMessage("Exchanged " + fromAmount + " " + from + " for " + toAmount + " " + to + ".");
        } else {
            player.sendMessage("You do not have enough " + from + " to exchange.");
        }
    }
}
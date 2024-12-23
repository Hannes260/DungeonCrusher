package net.dbsgameplay.dungeoncrusher.listener.Enchantments;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.enums.Enchantments.SwordEnchantments;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class EnchantmentListener implements Listener {
    private final SwordEnchantments swordEnchantments;
    public EnchantmentListener(SwordEnchantments swordEnchantments) {
        this.swordEnchantments = swordEnchantments; // Zugriff auf die SwordEnchantments-Klasse
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        ItemStack clickedItem = event.getCurrentItem();
        ClickType clickType = event.getClick();
        // Debug: Klicken in ein Inventar?
        if (clickedInventory == null || clickedItem == null || clickedItem.getType().isAir()) {
            return;
        }

        // Debug: Titel des Inventars prüfen
        String title = event.getView().getTitle();
        String placeholderTitle = "§f<shift:-8>%nexo_enchantment%";
        String expectedTitle = PlaceholderAPI.setPlaceholders(player, placeholderTitle);

        if (!expectedTitle.equalsIgnoreCase(title)) {
            return;
        }

        // Event abbrechen, um Standard-Interaktionen zu verhindern
        event.setCancelled(true);

        // Prüfen, ob das Item ein Enchantment-Item ist
        ItemMeta meta = clickedItem.getItemMeta();
        if (meta == null) {
            return;
        }

        NamespacedKey key = new NamespacedKey(DungeonCrusher.getInstance(), "enchantment");
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();

        // Debug: Prüfen, ob das Item ein Enchantment-Tag hat
        if (dataContainer.has(key, PersistentDataType.STRING)) {
            String enchantmentName = dataContainer.get(key, PersistentDataType.STRING);
            if (enchantmentName != null) {
                swordEnchantments.handleItemClick(player, clickedItem, clickType);
            }
        }
    }

}

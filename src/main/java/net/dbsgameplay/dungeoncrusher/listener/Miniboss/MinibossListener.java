package net.dbsgameplay.dungeoncrusher.listener.Miniboss;

import net.dbsgameplay.dungeoncrusher.listener.DungeonListener;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.MinibossConfigManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.attribute.Attribute;

import java.util.HashMap;
import java.util.Random;

public class MinibossListener implements Listener {

    MYSQLManager mysqlManager;
    public MinibossListener(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        Inventory clickedInventory = event.getInventory();

        if (clickedInventory == null || clickedItem == null || clickedItem.getType().isAir()) {
            return;
        }

        if(event.getView().getTitle().equalsIgnoreCase("Spawn Miniboss")) {

            String dungeon = DungeonListener.getCurrentDungeon(p.getLocation());
            String currentMoneyString = mysqlManager.getBalance(p.getUniqueId().toString());
            currentMoneyString = currentMoneyString.replace(",", "");
            Double currentMoney = Double.parseDouble(currentMoneyString);

            HashMap<String, HashMap<String, HashMap<String, Integer>>> minibossHashMap = MinibossConfigManager.miniboss_data_Hashmap;

            if(clickedItem.getType() == Material.CREEPER_SPAWN_EGG) {

                double costs = minibossHashMap.get(dungeon).get("lvl1").get("preis");

                if (currentMoney >= costs) {
                    Location playerLocation = p.getLocation();

                    Random random = new Random();
                    int x = playerLocation.getBlockX() + random.nextInt(21) - 10;
                    int y = playerLocation.getBlockY();
                    int z = playerLocation.getBlockZ() + random.nextInt(21) - 10;

                    // Zombie spawnen
                    Location spawnLocation = new Location(playerLocation.getWorld(), x, y, z);

                    Sheep boss = (Sheep) p.getWorld().spawnEntity(spawnLocation, EntityType.SHEEP);
                    boss.setCustomName("§cMiniboss");
                    boss.setCustomNameVisible(true);
                    boss.setMaxHealth(minibossHashMap.get(dungeon).get("lvl1").get("health"));
                    boss.setHealth(minibossHashMap.get(dungeon).get("lvl1").get("health"));
                    boss.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(4.0);

                    p.closeInventory();

                }else {
                    p.sendMessage("§cDu hast nicht genug Geld um diesen Miniboss zu beschwören");
                }
            }


        }
    }
}

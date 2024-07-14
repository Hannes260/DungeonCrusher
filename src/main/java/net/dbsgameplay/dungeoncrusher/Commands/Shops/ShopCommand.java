package net.dbsgameplay.dungeoncrusher.Commands.Shops;

import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.shops.ShopManager;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ShopCommand implements CommandExecutor {
    MYSQLManager mysqlManager;
    public ShopCommand(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                ShopManager.openMainShopMenu(player, mysqlManager);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f,1.0f);
            }
        }
        return false;
    }
//    private void fillEmptySlots (Inventory inventory, Material material,int amount){
//         for (int i = 0; i < inventory.getSize(); ++i) {
//            ItemStack itemStack = inventory.getItem(i);
//            if (itemStack == null || itemStack.getType() == Material.AIR) {
//                ItemStack newItemStack = new ItemStack(material, amount, (short) 7);
//                ItemMeta itemMeta = newItemStack.getItemMeta();
//               itemMeta.setDisplayName("§a ");
//               newItemStack.setItemMeta(itemMeta);
//               inventory.setItem(i, newItemStack);
//        }
//    }
//  }
}

package de.hannezhd.dungeoncrusher.Commands;

import de.hannezhd.dungeoncrusher.DungeonCrusher;
import de.hannezhd.dungeoncrusher.sql.MYSQLManager;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class TestComand implements CommandExecutor {
    private MYSQLManager mysqlManager;
    public final DungeonCrusher dungeonCrusher;

    public TestComand(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.mysqlManager = mysqlManager;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack customSword = new ItemStack(Material.WOODEN_SWORD);
            ItemMeta meta = customSword.getItemMeta();

            double damage = 1000000.0;
            AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER);
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
            customSword.setItemMeta(meta);
            player.getInventory().addItem(customSword);
        }
        return false;
    }
}

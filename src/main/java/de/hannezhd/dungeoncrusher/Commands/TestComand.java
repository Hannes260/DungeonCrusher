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
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(10);
        }
        return false;
    }
}

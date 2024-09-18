package net.dbsgameplay.dungeoncrusher.enums;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import org.bukkit.entity.Player;

public class MyPlaceholderExpansion extends PlaceholderExpansion {
    MYSQLManager mysqlManager;

    public MyPlaceholderExpansion(MYSQLManager mysqlManager) {
        this.mysqlManager = mysqlManager;
    }
    @Override
    public String getIdentifier() {
        return "dc";
    }

    @Override
    public String getAuthor() {
        return "DeinName";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (identifier.equals("top_10")) {
            return mysqlManager.getToplistKills(10);
        } else if (identifier.equals("top_9")) {
            return mysqlManager.getToplistKills(9);
        } else if (identifier.equals("top_8")) {
            return mysqlManager.getToplistKills(8);
        } else if (identifier.equals("top_7")) {
            return mysqlManager.getToplistKills(7);
        } else if (identifier.equals("top_6")) {
            return mysqlManager.getToplistKills(6);
        } else if (identifier.equals("top_5")) {
            return mysqlManager.getToplistKills(5);
        } else if (identifier.equals("top_4")) {
            return mysqlManager.getToplistKills(4);
        } else if (identifier.equals("top_3")) {
            return mysqlManager.getToplistKills(3);
        } else if (identifier.equals("top_2")) {
            return mysqlManager.getToplistKills(2);
        } else if (identifier.equals("top_1")) {
            return mysqlManager.getToplistKills(1);
        }    else if (identifier.equals("top_dungeon_10")) {
            return mysqlManager.getToplistDungeonCount(10);
        } else if (identifier.equals("top_dungeon_9")) {
            return mysqlManager.getToplistDungeonCount(9);
        } else if (identifier.equals("top_dungeon_8")) {
            return mysqlManager.getToplistDungeonCount(8);
        } else if (identifier.equals("top_dungeon_7")) {
            return mysqlManager.getToplistDungeonCount(7);
        } else if (identifier.equals("top_dungeon_6")) {
            return mysqlManager.getToplistDungeonCount(6);
        } else if (identifier.equals("top_dungeon_5")) {
            return mysqlManager.getToplistDungeonCount(5);
        } else if (identifier.equals("top_dungeon_4")) {
            return mysqlManager.getToplistDungeonCount(4);
        } else if (identifier.equals("top_dungeon_3")) {
            return mysqlManager.getToplistDungeonCount(3);
        } else if (identifier.equals("top_dungeon_2")) {
            return mysqlManager.getToplistDungeonCount(2);
        } else if (identifier.equals("top_dungeon_1")) {
            return mysqlManager.getToplistDungeonCount(1);
        }
        return null;
    }
}


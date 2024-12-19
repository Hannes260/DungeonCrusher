package net.dbsgameplay.dungeoncrusher.utils.Miniboss;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;

public class Miniboss {
    public static MYSQLManager mysqlManager;
    public static DungeonCrusher dungeonCrusher;

    public Miniboss(MYSQLManager mysqlManager, DungeonCrusher dungeonCrusher) {
        this.mysqlManager = mysqlManager;
        this.dungeonCrusher = dungeonCrusher;
    }
}

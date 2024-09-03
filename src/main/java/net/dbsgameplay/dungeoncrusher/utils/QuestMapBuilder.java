package net.dbsgameplay.dungeoncrusher.utils;

import java.util.HashMap;

public class QuestMapBuilder {

    public static void BuildMap() {
        HashMap<String, String> tutorialQuestMap = QuestBuilder.tutorialQuestMap;

        tutorialQuestMap.put("t1", "§6Verbessere dein Schwert auf Lvl. 2 §7(Material & Geld bekommst du indem du Mobs killst!)");
        tutorialQuestMap.put("t2", "§6Kaufe dir Essen im Teleporter");
        tutorialQuestMap.put("t3", "§6Trinke einen Stärketrank, den du im Teleporter kaufen kannst.");
    }
}

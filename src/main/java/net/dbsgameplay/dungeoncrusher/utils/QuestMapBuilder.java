package net.dbsgameplay.dungeoncrusher.utils;

import java.util.HashMap;

public class QuestMapBuilder {

    public static void BuildMap() {
        HashMap<String, String> tutorialQuestMap = QuestBuilder.tutorialQuestMap;

        tutorialQuestMap.put("t3", "§6Verbessere dein Schwert auf Lvl. 2.");
        tutorialQuestMap.put("t2", "§6Kaufe dir Essen im Teleporter.");
        tutorialQuestMap.put("t1", "§6Trinke einen Stärketrank.");
    }
}

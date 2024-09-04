package net.dbsgameplay.dungeoncrusher.utils;

import java.util.HashMap;

public class QuestMapBuilder {

    public static void BuildMap() {
        HashMap<String, String> tutorialQuestMap = QuestBuilder.tutorialQuestMap;
        HashMap<String, String> dailyQuestMap = QuestBuilder.dailyQuestMap;

        tutorialQuestMap.put("t3", "§6Verbessere dein Schwert auf Lvl. 2.");
        tutorialQuestMap.put("t2", "§6Kaufe dir Essen im Teleporter.");
        tutorialQuestMap.put("t1", "§6Trinke einen Stärketrank.");

        dailyQuestMap.put("d1", "§6Töte 100 Kreaturen.");
        dailyQuestMap.put("d2", "§6Töte 150 Kreaturen.");
        dailyQuestMap.put("d3", "§6Sammle 250 Materialien.");
        dailyQuestMap.put("d4", "§6Sammle 300 Materialien.");
        dailyQuestMap.put("d5", "§6Kaufe 3 Tränke.");
        dailyQuestMap.put("d6", "§6Kaufe 20 mal Essen.");
        dailyQuestMap.put("d7", "§6Spiele 60 Minuten.");
        dailyQuestMap.put("d8", "§6Spiele 90 Minuten.");
        dailyQuestMap.put("d9", "§6Lege 500 Meter zurück.");
        dailyQuestMap.put("d10", "§6Lege 1000 Meter zurück.");

    }


}

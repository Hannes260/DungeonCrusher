package net.dbsgameplay.dungeoncrusher.utils;

import java.util.HashMap;

public class QuestMapBuilder {

    public static void BuildMap() {
        HashMap<String, String> tutorialQuestMap = QuestBuilder.tutorialQuestMap;
        HashMap<String, String> dailyQuestMap = QuestBuilder.dailyQuestMap;
        HashMap<String, String> dailyQuestBMap = QuestBuilder.dailyQuestBMap;

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

        dailyQuestBMap.put("d1b", "§6Töte 100 Kreaturen.");
        dailyQuestBMap.put("d2b", "§6Töte 150 Kreaturen.");
        dailyQuestBMap.put("d3b", "§6Sammle 250 Materialien.");
        dailyQuestBMap.put("d4b", "§6Sammle 300 Materialien.");
        dailyQuestBMap.put("d5b", "§6Kaufe 3 Tränke.");
        dailyQuestBMap.put("d6b", "§6Kaufe 20 mal Essen.");
        dailyQuestBMap.put("d7b", "§6Spiele 60 Minuten.");
        dailyQuestBMap.put("d8b", "§6Spiele 90 Minuten.");
        dailyQuestBMap.put("d9b", "§6Lege 500 Meter zurück.");
        dailyQuestBMap.put("d10b", "§6Lege 1000 Meter zurück.");
    }


}

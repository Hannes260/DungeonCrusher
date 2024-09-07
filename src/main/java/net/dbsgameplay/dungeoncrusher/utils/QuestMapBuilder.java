package net.dbsgameplay.dungeoncrusher.utils;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;

import java.util.HashMap;

public class QuestMapBuilder {

    public static void BuildMap() {
        HashMap<String, String> tutorialQuestMap = QuestBuilder.tutorialQuestMap;
        HashMap<String, String> dailyQuestMap = QuestBuilder.dailyQuestMap;
        HashMap<String, String> dailyQuestRewardMap = QuestBuilder.dailyQuestRewardMap;

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

        dailyQuestRewardMap.put("d1", "§6Töte 100 Kreaturen.");
        dailyQuestRewardMap.put("d2", "§6Töte 150 Kreaturen.");
        dailyQuestRewardMap.put("d3", "§6Sammle 250 Materialien.");
        dailyQuestRewardMap.put("d4", "§6Sammle 300 Materialien.");
        dailyQuestRewardMap.put("d5", "§6Kaufe 3 Tränke.");
        dailyQuestRewardMap.put("d6", "§6Kaufe 20 mal Essen.");
        dailyQuestRewardMap.put("d7", "§6Spiele 60 Minuten.");
        dailyQuestRewardMap.put("d8", "§6Spiele 90 Minuten.");
        dailyQuestRewardMap.put("d9", "§6Lege 500 Meter zurück.");
        dailyQuestRewardMap.put("d10", "§6Lege 1000 Meter zurück.");

        //getten
        if (DungeonCrusher.getInstance().getConfig().isConfigurationSection("questReward.")) {
            for (String s : DungeonCrusher.getInstance().getConfig().getConfigurationSection("questReward.").getKeys(false)) {
                String key = s;
                String value = DungeonCrusher.getInstance().getConfig().getString("questReward." + s);
                dailyQuestRewardMap.replace(key, value);
            }
        }
        Object[] keys = dailyQuestRewardMap.keySet().toArray();
        Object[] values = dailyQuestRewardMap.values().toArray();
        //Setten
        for (int i = 0; i != dailyQuestRewardMap.size(); i++) {
            DungeonCrusher.getInstance().getConfig().set("questReward." + keys[i], values[i]);
            DungeonCrusher.getInstance().saveConfig();
        }
    }


}

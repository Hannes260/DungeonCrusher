package net.dbsgameplay.dungeoncrusher.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class MarkierungsManager {

    private final LocationConfigManager locationConfigManager;
    private final Map<Player, Markierungsschritt> markierungen = new HashMap<>();

    public MarkierungsManager(LocationConfigManager locationConfigManager) {
        this.locationConfigManager = locationConfigManager;
    }

    public void toggleMarkierungsModus(Player player, String dungeonName, String markierungsTyp, String savezoneName) {
        if (markierungen.containsKey(player)) {
            markierungen.remove(player);
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.posmodeoff", "", ""));
        } else {
            markierungen.put(player, new Markierungsschritt(dungeonName, markierungsTyp, savezoneName));
            String message = ConfigManager.getConfigMessage("message.posmodeon", "%name%", dungeonName + (savezoneName.isEmpty() ? "" : "_savezone_" + savezoneName));
            player.sendMessage(ConfigManager.getPrefix() + message);
        }
    }

    public boolean isMarkierungsModusAktiv(Player player) {
        return markierungen.containsKey(player);
    }

    public void handleMarkierungSetzen(Player player, Location location) {
        if (markierungen.containsKey(player)) {
            Markierungsschritt markierungsschritt = markierungen.get(player);
            String dungeonName = markierungsschritt.getDungeonName();
            String markierungsTyp = markierungsschritt.getMarkierungsTyp();

            if ("dungeon".equalsIgnoreCase(markierungsTyp)) {
                locationConfigManager.saveLocation(dungeonName, location, markierungsschritt.getCurrentStep());
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.setpos", "%name%", dungeonName, "%pos%", String.valueOf(markierungsschritt.getCurrentStep()), "%location%", formatLocation(location)));
            } else if ("savezone".equalsIgnoreCase(markierungsTyp)) {
                String savezoneName = markierungsschritt.getSavezoneName();
                locationConfigManager.saveSavezone(dungeonName, savezoneName, location, markierungsschritt.getCurrentStep());
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.setpos", "%name%", dungeonName + "_" + savezoneName, "%pos%", String.valueOf(markierungsschritt.getCurrentStep()), "%location%", formatLocation(location)));
            }

            if (markierungsschritt.getCurrentStep() == 2) {
                markierungen.remove(player);
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.posmodeoff", "", ""));
            } else {
                markierungsschritt.nextStep();
            }
        } else {
            player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.posmodefailure", "", ""));
        }
    }

    private static class Markierungsschritt {
        private final String dungeonName;
        private final String markierungsTyp;
        private final String savezoneName;
        private int currentStep;

        public Markierungsschritt(String dungeonName, String markierungsTyp, String savezoneName) {
            this.dungeonName = dungeonName;
            this.markierungsTyp = markierungsTyp;
            this.savezoneName = savezoneName;
            this.currentStep = 1;
        }

        public String getDungeonName() {
            return dungeonName;
        }
        public String getMarkierungsTyp() {
            return markierungsTyp;
        }
        public String getSavezoneName() {
            return savezoneName;
        }
        public int getCurrentStep() {
            return currentStep;
        }

        public void nextStep() {
            currentStep++;
        }
    }

    private String formatLocation(Location location) {
        return "x: " + location.getBlockX() + ", y: " + location.getBlockY() + ", z: " + location.getBlockZ();
    }
}

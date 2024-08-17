package net.dbsgameplay.dungeoncrusher.listener;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ChatListener implements Listener {
    private static final List<String> OFFENSIVE_WORDS = Arrays.asList(
            "hure", "schlampe", "idiot", "trottel", "vollidiot", "arschloch", "pisskopf",
            "drecksau", "penner", "geizkragen", "fick dich", "ficker", "miststück",
            "kleiner scheißer", "schnauze", "spast", "behindert", "zicke", "arschgeige",
            "kotzbrocken", "sauhund", "wichser", "schwuchtel", "hurensohn", "schnorrer",
            "kackbratze", "fresse", "möpse", "versager", "dödel", "schwanzlutscher",
            "pimmel", "ranzkiste", "stinkstiefel", "kacke", "kotze", "blödmann",
            "langweiler", "loser", "geblödet", "vollpfosten", "hassmaul", "hirni",
            "sackgesicht", "nervensäge", "strunzdoof", "pissnase", "scheißkerl",
            "drücker", "dummschwätzer", "blödes ding", "schwachsinn", "trottelkopf",
            "saftschubse", "pennergesicht", "flachzange", "zirkusclown", "abschreiber",
            "schlafmütze", "lauch", "apfelschäler", "stinkfinger", "schwachkopf",
            "vollspast", "abfalleimer", "kackbrötchen", "faulpelz", "arsch", "arschloch"
    );

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String playerName = event.getPlayer().getName();
        String originalMessage = event.getMessage();
        String message = originalMessage.toLowerCase(); // Nachricht in Kleinbuchstaben umwandeln

        // Überprüfen, ob die Nachricht eine Beleidigung enthält
        boolean isOffensive = OFFENSIVE_WORDS.stream()
                .map(String::toLowerCase) // Beleidigungen in Kleinbuchstaben umwandeln
                .anyMatch(word -> Pattern.compile("\\b" + Pattern.quote(word) + "\\b", Pattern.CASE_INSENSITIVE).matcher(message).find());

        // Beleidigungen durch Sternchen ersetzen für Minecraft-Chat
        String filteredMessage = originalMessage;
        for (String word : OFFENSIVE_WORDS) {
            String regex = "\\b" + Pattern.quote(word) + "\\b"; // Regulärer Ausdruck für das Wort
            filteredMessage = filteredMessage.replaceAll("(?i)" + regex, getReplacement(word));
        }

        // Gefilterte Nachricht an Minecraft-Chat senden
        event.setMessage(filteredMessage);

        // Originalnachricht an Discord senden
        String fullMessage = playerName + ": " + originalMessage;
        int color = isOffensive ? 0xFFFF00 : 0x00FF00; // Gelb für Beleidigungen, Grün für normale Nachrichten
        DungeonCrusher.getInstance().sendToDiscord(fullMessage, color);
    }

    private String getReplacement(String word) {
        // Ersetze Beleidigung durch Sternchen, gleiche Länge wie das Wort
        return "*".repeat(word.length());
    }
}

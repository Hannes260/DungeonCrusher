package net.dbsgameplay.dungeoncrusher.enums;

public class MobNameTranslator {

    public static String translateToGerman(String englishName) {
        if (englishName == null || englishName.isEmpty()) {
            return "Unbekannt"; // Rückfallwert für null oder leere Eingaben
        }

        // Entferne eckige Klammern, falls vorhanden
        englishName = englishName.replace("[", "").replace("]", "");

        // Übersetzungen
        switch (englishName.toLowerCase()) {
            case "sheep":
                return "Schaf";
            case "pig":
                return "Schweine"; // Einzel- und Mehrzahlform konsistent
            case "cow":
                return "Kuh";
            case "horse":
                return "Pferd";
            case "donkey":
                return "Esel";
            case "camel":
                return "Kamel";
            case "villager":
                return "Dorfbewohner";
            case "goat":
                return "Ziegen"; // Singularform für Konsistenz
            case "llama":
                return "Lama";
            case "mooshroom":
                return "Mooshroom";
            case "mule":
                return "Maultier";
            case "stray":
                return "Schnüffler";
            case "panda":
                return "Panda";
            case "turtle":
                return "Schildkröte"; // Singularform für Konsistenz
            case "ocelot":
                return "Ozelot";
            case "axolotl":
                return "Axolotl";
            case "fox":
                return "Fuchs";
            case "cat":
                return "Katze"; // Singularform für Konsistenz
            case "chicken":
                return "Huhn";
            case "frog":
                return "Frosch";
            case "rabbit":
                return "Kaninchen";
            case "silverfish":
                return "Silberfisch";
            case "evoker":
                return "Diener";
            case "polar_bear":
                return "Eisbär"; // Singularform für Konsistenz
            case "zombie_horse":
                return "Zombiepferd";
            case "wolf":
                return "Wolf";
            case "zombified_villager":
                return "Zombiedorfbewohner";
            case "snow_golem":
                return "Schneegolem";
            case "skeleton":
                return "Skelett";
            case "drowned":
                return "Ertrunkener"; // Singularform für Konsistenz
            case "husk":
                return "Wüstenzombie";
            case "spider":
                return "Spinne"; // Singularform für Konsistenz
            case "zombie":
                return "Zombie";
            case "strider":
                return "Eiswanderer";
            case "creeper":
                return "Creeper";
            case "cave_spider":
                return "Höhlenspinne"; // Singularform für Konsistenz
            case "endermite":
                return "Endermite";
            case "blaze":
                return "Blaze"; // "Schreiter" kann nicht überall passen
            case "skeleton_horse":
                return "Skelettpferd";
            case "witch":
                return "Hexe"; // Singularform für Konsistenz
            case "slime":
                return "Schleim";
            case "magma_cube":
                return "Magmawürfel";
            case "enderman":
                return "Enderman";
            case "piglin":
                return "Piglin";
            case "zombified_piglin":
                return "Zombiefizierter Piglin";
            case "piglin_brute":
                return "Piglin-Brutal";
            case "ravager":
                return "Plünderer";
            case "hoglin":
                return "Hoglin";
            case "illusioner":
                return "Illusionist";
            case "ghast":
                return "Ghast";
            case "wither_skeleton":
                return "Witherskelett";
            case "zoglin":
                return "Zoglin";
            case "pillager":
                return "Verwüster";
            case "iron_golem":
                return "Eisengolem";
            case "warden":
                return "Wärter";
            default:
                return null; // Rückfall auf "Unbekannt" bei fehlender Übersetzung
        }
    }
}

package fr.univlille.dotsboxes.display;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public final class AsciiArt {

    public static final String MENU;

    static {
        MENU = load("/assets/logo.txt");
    }

    private AsciiArt() {}

    private static String load(String resourcePath) {
        try (InputStream is = AsciiArt.class.getResourceAsStream(resourcePath)) {
            assert is != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (Exception e) {
            return "Erreur lors du chargement du fichier ASCII: " + resourcePath;
        }
    }
}

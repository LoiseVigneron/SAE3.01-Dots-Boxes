package fr.univlille.dotsboxes.menu;

import fr.univlille.dotsboxes.color.Color;
import fr.univlille.dotsboxes.display.AnsiChar;
import fr.univlille.dotsboxes.display.AsciiArt;
import fr.univlille.dotsboxes.game.Duel;
import fr.univlille.dotsboxes.game.GameRules;
import fr.univlille.dotsboxes.textStyle.TextStyle;
import fr.univlille.dotsboxes.util.menu.Menu;
import fr.univlille.dotsboxes.util.menu.item.ActionItem;
import fr.univlille.dotsboxes.util.menu.item.MenuItem;
import fr.univlille.dotsboxes.util.menu.item.SubMenuItem;

import java.util.Scanner;

public final class ModeMenu extends Menu {

    private static final Scanner SCANNER = new Scanner(System.in);

    private static final MenuItem PLAY_DUEL_BUTTON = new ActionItem(
        Color.CYAN + "Démarrer la partie" + Color.BLUE + "             ║" + Color.RESET, ModeMenu::startDuelMode);
    
        private static final MenuItem READ_RULES = new ActionItem(
        Color.CYAN + "Voir les règles du jeu" + Color.BLUE + "         ║" + Color.RESET, () -> {
            readRules();
        });

    private static final MenuItem BACK_BUTTON = new SubMenuItem(
        Color.CYAN + "Retour au menu principal" + Color.BLUE + "       ║" + Color.RESET, "main");

    @Override
    protected void buildMenu() {
        addItem(PLAY_DUEL_BUTTON);
        addItem(READ_RULES);
        addItem(BACK_BUTTON);
    }

    @Override
    protected void onMenuOpen() {
        System.out.println(Color.RED.getColorCode() + AsciiArt.MENU + Color.RESET.getColorCode() + "\n");
    }

    private static void readRules() {
        GameRules.displayRules();
    }

    private static void startDuelMode() {
        System.out.println(AnsiChar.CLEAR + TextStyle.BOLD + "\n=== Configuration du Mode Classique ===\n" + TextStyle.RESET);

        String player1Name = getPlayerName("➡ Nom du Joueur 1 : ", "Joueur 1");
        String player2Name = getPlayerName("➡ Nom du Joueur 2 : ", "Joueur 2");

        int gridWidth = requestNumber("➡ Nombre de colonnes (3-8): ", 3, 8);
        int gridHeight = requestNumber("➡ Nombre de lignes (3-8): ", 3, 8);

        Duel duel = new Duel(player1Name, player2Name, gridWidth, gridHeight);
        duel.startGame();
    }

    private static String getPlayerName(String prompt, String defaultName) {
        System.out.print(prompt);
        String playerName = SCANNER.nextLine().trim();
        return playerName.isEmpty() ? defaultName : playerName;
    }

    private static int requestNumber(String text, int min, int max) {
        while (true) {
            System.out.print(text);
            if (!SCANNER.hasNextInt()) {
                System.out.println("Erreur : vous devez entrer un nombre !");
                SCANNER.next();
                continue;
            }

            int value = SCANNER.nextInt();
            SCANNER.nextLine();

            if (value < min || value > max) {
                System.out.println("Entrez un nombre entre " + min + " et " + max + ".");
            } else {
                return value;
            }
        }
    }
}

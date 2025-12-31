package fr.univlille.dotsboxes.game;

import java.util.Scanner;

import fr.univlille.dotsboxes.color.Color;
import fr.univlille.dotsboxes.display.AnsiChar;
import fr.univlille.dotsboxes.exception.MenuNotFoundException;
import fr.univlille.dotsboxes.menu.registry.MenuRegistry;
import fr.univlille.dotsboxes.textStyle.TextStyle;

public class GameRules {
    private static final String RULES_TITLE = TextStyle.BOLD + "\n=== RÈGLES DU JEU ===" + TextStyle.RESET;
    private static final String TUTORIAL_TITLE = TextStyle.BOLD + "\n=== TUTORIEL DU FORMAT A RENTRER ===" + TextStyle.RESET;
    private static final String FINISH_RULES = "\nAppuyer sur Entrée pour revenir au menu  ";

    public static void displayRules() {

        displayRulesWithoutReturn();

        try {
            MenuRegistry.get("mode").open();
        } catch (MenuNotFoundException exception){
            System.err.println("Menu des modes introuvable.");
        }
    }

    public static void displayRulesWithoutReturn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(AnsiChar.CLEAR);
        displayGameRules();

        System.out.print("\nAppuyer sur Entrée pour continuer ");
        scanner.nextLine();

        System.out.println(AnsiChar.CLEAR);
        displayTutorial();

        System.out.print("\nAppuyer sur Entrée pour continuer ");
        scanner.nextLine();

        System.out.println(AnsiChar.CLEAR);
        displayExample1();

        System.out.print("\nAppuyer sur Entrée pour continuer ");
        scanner.nextLine();

        System.out.println(AnsiChar.CLEAR);
        displayExample2();

        System.out.print(FINISH_RULES);
        scanner.nextLine();
    }

    private static void displayGameRules() {
        System.out.println(RULES_TITLE+"\n");
        System.out.println("1- Tour par tour, les joueurs choisissent un segment entre 2 points.");
        System.out.println("2- Lorsqu'un joueur trace le 4ème côté d'une case, il gagne 1 point colore la case de sa couleur.");
    }

    private static void displayTutorial() {
        System.out.println(TUTORIAL_TITLE+"\n");
        System.out.println("1ÈRE ÉTAPE : On regarde dans quel position on veut placer le trait, soit horizontal soit vertical.");
        System.out.println("2ÈME ÉTAPE : Ensuite on repère dans quelle coordonnée est le trait, on repère la ligne et la colonne.");
        System.out.println("3ÈME ÉTAPE : On rentre les informations dans le terminal en respectant ce format :");
        System.out.println(" ==> V ou H + espace + numéro de ligne + espace + numéro de colonne");
        System.out.println("     Ex : H 1 2");
    }

    private static void displayExample1() {
        System.out.println(TextStyle.BOLD+"\n=== Exemple 1 ===\n"+TextStyle.RESET);
        displayExampleGrid();
        System.out.println("\nPrenons comme exemple cette grille de 5x5.");
        System.out.println("En saisissant 'H 0 0', le joueur de couleur rouge a sélectionné une arrête.");
    }

    private static void displayExample2() {
        System.out.println(TextStyle.BOLD+"\n=== Exemple 2 ===\n"+TextStyle.RESET);
        displaySecondExampleGrid();
        System.out.println("\nPrenons comme exemple une autre grille de 5x5.");
        System.out.println("En saisissant 'V 2 3', le joueur de couleur verte a sélectionné la dernière arrête d'une case est a gagné un point.");
        System.out.println("La case devient alors de couleur verte.");
    }

    private static void displayExampleGrid() {
        System.out.println("  0    1    2    3    4");
        System.out.println("0 O"+Color.RED+"━━━━"+Color.RESET+"O════O════O════O");
        System.out.println("  ║    ║    ║    ║    ║");
        System.out.println("1 O════O════O════O════O");
        System.out.println("  ║    ║    ║    ║    ║");
        System.out.println("2 O════O════O════O════O");
        System.out.println("  ║    ║    ║    ║    ║");
        System.out.println("3 O════O════O════O════O");
        System.out.println("  ║    ║    ║    ║    ║");
        System.out.println("4 O════O════O════O════O");
    }

    private static void displaySecondExampleGrid() {
        System.out.println("  0    1    2    3    4");
        System.out.println("0 O════O════O════O════O");
        System.out.println("  ║    ║    ║    ║    ║");
        System.out.println("1 O════O════O════O════O");
        System.out.println("  ║    ║    ║    ║    ║");
        System.out.println("2 O════O════O"+Color.RED+"━━━━"+Color.RESET+"O════O");
        System.out.println("  ║    ║    "+Color.GREEN+"┃ ██ ┃"+Color.RESET+"    ║");
        System.out.println("3 O════O════O"+Color.RED+"━━━━"+Color.RESET+"O════O");
        System.out.println("  ║    ║    ║    ║    ║");
        System.out.println("4 O════O════O════O════O");
    }
}

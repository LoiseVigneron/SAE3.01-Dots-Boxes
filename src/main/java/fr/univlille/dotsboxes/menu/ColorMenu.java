package fr.univlille.dotsboxes.menu;

import fr.univlille.dotsboxes.color.Color;
import fr.univlille.dotsboxes.display.AnsiChar;
import fr.univlille.dotsboxes.exception.MenuNotFoundException;
import fr.univlille.dotsboxes.manager.GameManager;
import fr.univlille.dotsboxes.menu.registry.MenuRegistry;
import fr.univlille.dotsboxes.textStyle.TextStyle;
import fr.univlille.dotsboxes.util.menu.Menu;
import fr.univlille.dotsboxes.util.menu.item.ActionItem;
import fr.univlille.dotsboxes.util.menu.item.MenuItem;
import fr.univlille.dotsboxes.util.menu.item.SubMenuItem;

import java.util.Scanner;

public final class ColorMenu extends Menu {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String COLOR_MENU_TITLE = TextStyle.BOLD+"===== Menu Couleurs =====\n"+TextStyle.RESET;

    private static final MenuItem CHANGE_PLAYER_ONE_COLOR = new ActionItem(
        Color.CYAN + "Changer la couleur du Joueur 1" + Color.BLUE + " ║" + Color.RESET, ColorMenu::changePlayer1Color);

    private static final MenuItem CHANGE_PLAYER_TWO_COLOR = new ActionItem(
        Color.CYAN + "Changer la couleur du Joueur 2" + Color.BLUE + " ║" + Color.RESET, ColorMenu::changePlayer2Color);

    private static final MenuItem BACK = new SubMenuItem(
        Color.CYAN + "Retour au menu principal" + Color.BLUE + "       ║" + Color.RESET, "main");

    @Override
    protected void buildMenu() {
        addItem(CHANGE_PLAYER_ONE_COLOR);
        addItem(CHANGE_PLAYER_TWO_COLOR);
        addItem(BACK);
    }

    @Override
    protected void onMenuOpen() {
        GameManager gameManager = GameManager.getInstance();
        System.out.println(COLOR_MENU_TITLE);

        Color playerOneColor = gameManager.getPlayer1Color();
        Color playerTwoColor = gameManager.getPlayer2Color();

        System.out.println("Couleur Joueur 1 actuelle : " + TextStyle.BOLD +
            playerOneColor.getColorCode() + playerOneColor.name() + AnsiChar.RESET);
        System.out.println("Couleur Joueur 2 actuelle : " + TextStyle.BOLD +
            playerTwoColor.getColorCode() + playerTwoColor.name() + AnsiChar.RESET);
        System.out.println();
    }

    private static void changePlayer1Color() {
        chooseColor(true);
        reopenColorMenu();
    }

    private static void changePlayer2Color() {
        chooseColor(false);
        reopenColorMenu();
    }

    private static void reopenColorMenu() {
        try {
            MenuRegistry.get("color").open();
        } catch (MenuNotFoundException exception) {
            System.err.println("Menu des couleurs introuvable.");
            System.exit(1);
        }
    }

    private static void chooseColor(boolean isPlayerOne) {
        GameManager gameManager = GameManager.getInstance();

        while (true) {
            clearScreenAndShowHeader(isPlayerOne, gameManager);

            Color[] availableColors = Color.values();
            displayColorOptions(availableColors);

            int choice = getUserChoice();
            if (choice == 0) {
                return;
            }

            if (isInvalidChoice(choice, availableColors.length)) {
                showInvalidChoiceMessage();
                continue;
            }

            Color selectedColor = availableColors[choice - 1];
            if (isColorAlreadyUsed(selectedColor, isPlayerOne, gameManager)) {
                showColorAlreadyUsedMessage(isPlayerOne);
                continue;
            }

            applyColorChange(selectedColor, isPlayerOne, gameManager);
            showSuccessMessage(selectedColor, isPlayerOne);
            return;
        }
    }

    private static void clearScreenAndShowHeader(boolean isPlayerOne, GameManager gameManager) {
        System.out.print(AnsiChar.CLEAR);
        System.out.flush();

        System.out.println("Sélection de la couleur pour le Joueur " + (isPlayerOne ? "1" : "2"));

        Color playerOneColor = gameManager.getPlayer1Color();
        Color playerTwoColor = gameManager.getPlayer2Color();

        System.out.println("Couleur Joueur 1 actuelle : " + TextStyle.BOLD +
            playerOneColor.getColorCode() + playerOneColor.name() + AnsiChar.RESET);
        System.out.println("Couleur Joueur 2 actuelle : " + TextStyle.BOLD +
            playerTwoColor.getColorCode() + playerTwoColor.name() + AnsiChar.RESET);
        System.out.println();
    }

    private static void displayColorOptions(Color[] availableColors) {
        for (int i = 0; i < availableColors.length; i++) {
            System.out.println((i + 1) + " - " +
                availableColors[i].getColorCode() + availableColors[i].name() + AnsiChar.RESET);
        }
        System.out.println("0 - Annuler");
        System.out.print("Entrez votre choix : ");
    }

    private static int getUserChoice() {
        while (!SCANNER.hasNextInt()) {
            System.out.print("Veuillez entrer un nombre valide. Appuyez sur Entrée pour réessayer.");
            SCANNER.next();
            SCANNER.nextLine();
        }

        int choice = SCANNER.nextInt();
        SCANNER.nextLine();
        return choice;
    }

    private static boolean isInvalidChoice(int choice, int maxChoice) {
        return choice < 0 || choice > maxChoice;
    }

    private static void showInvalidChoiceMessage() {
        System.out.print("Choix invalide. Appuyez sur Entrée pour réessayer.");
        SCANNER.nextLine();
    }

    private static boolean isColorAlreadyUsed(Color selectedColor, boolean isPlayerOne, GameManager gameManager) {
        return (isPlayerOne && selectedColor == gameManager.getPlayer2Color()) ||
               (!isPlayerOne && selectedColor == gameManager.getPlayer1Color());
    }

    private static void showColorAlreadyUsedMessage(boolean isPlayerOne) {
        int otherPlayer = isPlayerOne ? 2 : 1;
        System.out.print("Cette couleur est déjà utilisée par le Joueur " + otherPlayer +
            ". Appuyez sur Entrée pour réessayer.");
        SCANNER.nextLine();
    }

    private static void applyColorChange(Color selectedColor, boolean isPlayerOne, GameManager gameManager) {
        if (isPlayerOne) {
            gameManager.setPlayer1Color(selectedColor);
        } else {
            gameManager.setPlayer2Color(selectedColor);
        }
    }

    private static void showSuccessMessage(Color selectedColor, boolean isPlayerOne) {
        System.out.println("Couleur du Joueur " + (isPlayerOne ? "1" : "2") +
            " changée par la couleur " + TextStyle.BOLD + selectedColor.getColorCode() + selectedColor.name() + AnsiChar.RESET);
        System.out.print("Appuyez sur Entrée pour continuer...");
        SCANNER.nextLine();
    }
}

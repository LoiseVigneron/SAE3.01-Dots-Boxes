package fr.univlille.dotsboxes.menu;

import fr.univlille.dotsboxes.color.Color;
import fr.univlille.dotsboxes.display.AsciiArt;
import fr.univlille.dotsboxes.menu.registry.MenuRegistry;
import fr.univlille.dotsboxes.util.menu.Menu;
import fr.univlille.dotsboxes.util.menu.item.ActionItem;
import fr.univlille.dotsboxes.util.menu.item.MenuItem;
import fr.univlille.dotsboxes.util.menu.item.SubMenuItem;

public final class MainMenu extends Menu {

    private static final MenuItem START_GAME = new SubMenuItem(
        Color.CYAN + "Jouer au jeu" + Color.BLUE + "                   ║" + Color.RESET, "mode");

    private static final MenuItem LEADERBOARD = new ActionItem(
        Color.CYAN + "Visualisez le leaderboard" + Color.BLUE + "      ║" + Color.RESET,
        LeaderboardMenu::display);

    private static final MenuItem COLOR_CHANGE = new SubMenuItem(
        Color.CYAN + "Changer de couleur" + Color.BLUE + "             ║" + Color.RESET, "color");

    private static final MenuItem QUIT = new ActionItem(
        Color.CYAN + "Quitter" + Color.BLUE + "                        ║" + Color.RESET,
        MainMenu::quitApplication);

    @Override
    protected void buildMenu() {
        addItem(START_GAME);
        addItem(LEADERBOARD);
        addItem(COLOR_CHANGE);
        addItem(QUIT);
    }

    @Override
    protected void onMenuOpen() {
        System.out.println(Color.RED.getColorCode() + AsciiArt.MENU + Color.RESET.getColorCode() + "\n");
    }

    private static void quitApplication() {
        Menu mainMenu = MenuRegistry.get("main");
        if (mainMenu != null) {
            mainMenu.closeMenu();
        }
        System.out.println("Au revoir !");
        System.exit(0);
    }
}

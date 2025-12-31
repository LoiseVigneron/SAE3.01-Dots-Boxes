package fr.univlille.dotsboxes;


import fr.univlille.dotsboxes.exception.MenuNotFoundException;
import fr.univlille.dotsboxes.manager.LeaderboardManager;
import fr.univlille.dotsboxes.menu.MainMenu;
import fr.univlille.dotsboxes.menu.registry.MenuRegistry;
import fr.univlille.dotsboxes.menu.ModeMenu;
import fr.univlille.dotsboxes.menu.ColorMenu;

public class DotBoxesEnhanced {

    public static void main(String[] args) {
        loadServices();
        registerMenus();

        try {
            MenuRegistry.get("main").open();
        } catch (MenuNotFoundException exception) {
            System.err.println("Menu principal introuvable.");
            System.exit(1);
        }
    }

    private static void registerMenus(){
        MenuRegistry.register("main", new MainMenu());
        MenuRegistry.register("mode", new ModeMenu());
        MenuRegistry.register("color", new ColorMenu());
    }

    private static void loadServices(){
        LeaderboardManager.getInstance().loadLeaderboard();
    }

}

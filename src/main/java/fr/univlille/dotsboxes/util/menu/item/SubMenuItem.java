package fr.univlille.dotsboxes.util.menu.item;

import fr.univlille.dotsboxes.exception.MenuNotFoundException;
import fr.univlille.dotsboxes.menu.registry.MenuRegistry;
import fr.univlille.dotsboxes.util.menu.Menu;

public class SubMenuItem extends MenuItem {

    private final String menuKey;

    public SubMenuItem(String label, String menuKey) {
        super(label);
        this.menuKey = menuKey;
    }

    @Override
    public void select() {
        try {
            Menu menu = MenuRegistry.get(menuKey);
            menu.open();
        } catch (MenuNotFoundException exception){
            System.err.println("Menu introuvable: " + menuKey);
        }
    }
}

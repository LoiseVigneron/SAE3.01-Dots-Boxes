package fr.univlille.dotsboxes.menu.registry;

import fr.univlille.dotsboxes.exception.MenuNotFoundException;
import fr.univlille.dotsboxes.util.menu.Menu;
import java.util.HashMap;
import java.util.Map;

public final class MenuRegistry {
    private static final Map<String, Menu> registry = new HashMap<>();

    private MenuRegistry() {}

    public static void register(String name, Menu menu) {
        registry.put(name, menu);
    }

    public static Menu get(String name) throws MenuNotFoundException {
        if(!registry.containsKey(name)){
            throw new MenuNotFoundException("Menu introuvable: " + name);
        }
        return registry.get(name);
    }
}


package fr.univlille.dotsboxes.util.menu;

import fr.univlille.dotsboxes.color.Color;
import fr.univlille.dotsboxes.display.AnsiChar;
import fr.univlille.dotsboxes.util.menu.item.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Menu {
    private final List<MenuItem> items = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private boolean running;

    protected Menu() {
        buildMenu();
    }

    protected abstract void buildMenu();

    protected abstract void onMenuOpen();

    protected void addItem(MenuItem item) {
        items.add(item);
    }

    public void open() {
        running = true;

        while (running) {
            System.out.print(AnsiChar.CLEAR);
            System.out.flush();

            onMenuOpen();
            displayMenuItems();

            System.out.print("\nEntrez votre choix : ");

            if (!scanner.hasNextInt()) {
                System.out.println("Entrez un nombre, s'il vous plaît.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            if (choice > 0 && choice <= items.size()) {
                items.get(choice - 1).select();
                return;
            } else {
                System.out.println("Mettez un nombre valide.");
                scanner.next();
            }
        }
    }

    private void displayMenuItems() {
        System.out.println(Color.BLUE + "╔════════════════════════════════════╗" + Color.RESET);
        for (int i = 0; i < items.size(); i++) {
            System.out.println(Color.BLUE + "║ " + Color.CYAN + (i + 1) + " : " + Color.RESET + items.get(i).getLabel());
        }
        System.out.println(Color.BLUE + "╚════════════════════════════════════╝" + Color.RESET);
    }

    public void closeMenu() {
        running = false;
    }
}
package fr.univlille.dotsboxes.util.menu.item;

import fr.univlille.dotsboxes.util.menu.action.MenuAction;

public class ActionItem extends MenuItem{
    private final MenuAction action;

    public ActionItem(String label, MenuAction action) {
        super(label);

        this.action = action;
    }

    @Override
    public void select() {
        action.execute();
    }
}

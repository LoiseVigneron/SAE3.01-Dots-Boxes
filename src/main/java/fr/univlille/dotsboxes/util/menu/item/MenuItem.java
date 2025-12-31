package fr.univlille.dotsboxes.util.menu.item;

public abstract class MenuItem {
    private final String label;

    protected MenuItem(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public abstract void select();

}

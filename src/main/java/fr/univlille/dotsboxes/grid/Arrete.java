package fr.univlille.dotsboxes.grid;

import fr.univlille.dotsboxes.player.Player;

public class Arrete {

    private final Point pointOne;
    private final Point pointTwo;
    private boolean isSelected;
    private Player owner;

    public Arrete(Point pointOne, Point pointTwo) {
        this.pointOne = pointOne;
        this.pointTwo = pointTwo;
        this.isSelected = false;
        this.owner = null;
    }

    public boolean getSelection() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
        this.isSelected = true;
    }

    public Point getPointOne() {
        return pointOne;
    }

    public Point getPointTwo() {
        return pointTwo;
    }

    public boolean isHorizontal() {
        return pointOne.getY() == pointTwo.getY();
    }

    public boolean isVertical() {
        return pointOne.getX() == pointTwo.getX();
    }
}

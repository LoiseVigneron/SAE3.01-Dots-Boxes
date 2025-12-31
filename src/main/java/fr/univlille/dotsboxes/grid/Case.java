package fr.univlille.dotsboxes.grid;

import fr.univlille.dotsboxes.player.Player;

public class Case {
    private final int x;
    private final int y;
    private final Arrete topEdge;
    private final Arrete bottomEdge;
    private final Arrete leftEdge;
    private final Arrete rightEdge;
    private Player owner;

    public Case(int x, int y, Arrete topEdge, Arrete bottomEdge, Arrete leftEdge, Arrete rightEdge) {
        this.x = x;
        this.y = y;
        this.topEdge = topEdge;
        this.bottomEdge = bottomEdge;
        this.leftEdge = leftEdge;
        this.rightEdge = rightEdge;
        this.owner = null;
    }

    public boolean isCompleted() {
        return topEdge.getSelection() && bottomEdge.getSelection() &&
               leftEdge.getSelection() && rightEdge.getSelection();
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public String getContent() {
        if (owner == null) {
            return "  ";
        }
        return owner.getSymbol();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Arrete getTopEdge() {
        return topEdge;
    }

    public Arrete getBottomEdge() {
        return bottomEdge;
    }

    public Arrete getLeftEdge() {
        return leftEdge;
    }

    public Arrete getRightEdge() {
        return rightEdge;
    }
}

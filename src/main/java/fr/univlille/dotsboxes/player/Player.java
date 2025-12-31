package fr.univlille.dotsboxes.player;


public class Player {
    private final String name;
    private final String symbol;
    private int score;

    public Player(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        this.score++;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return name;
    }
}

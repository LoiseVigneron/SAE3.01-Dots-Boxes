package fr.univlille.dotsboxes.manager;

import fr.univlille.dotsboxes.color.Color;
import fr.univlille.dotsboxes.player.Player;
import fr.univlille.dotsboxes.util.Tuple;

public class GameManager {
    private static GameManager instance;
    private Tuple<Player, Player> players;
    private Tuple<Color, Color> colors = new Tuple<>(Color.RED, Color.GREEN);

    private GameManager() {}

    public static synchronized GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void setPlayers(Tuple<Player, Player> players) {
        this.players = players;
    }

    public void setColors(Tuple<Color, Color> colors) {
        this.colors = colors;
    }

    public void setPlayer1Color(Color color) {
        this.colors = new Tuple<>(color, this.colors.second());
    }

    public void setPlayer2Color(Color color) {
        this.colors = new Tuple<>(this.colors.first(), color);
    }

    public Tuple<Color, Color> getColors() {
        return colors;
    }

    public Color getPlayer1Color() {
        return this.colors.first();
    }

    public Color getPlayer2Color() {
        return this.colors.second();
    }

    public Player getFirstPlayer() {
        return this.players.first();
    }

    public Player getSecondPlayer() {
        return this.players.second();
    }

    public Tuple<Player, Player> getPlayers() {
        return this.players;
    }

    public Player getHighestScore() {
        if (this.players.first().getScore() < this.players.second().getScore()) {
            return this.getSecondPlayer();
        }
        return this.getFirstPlayer();
    }
}

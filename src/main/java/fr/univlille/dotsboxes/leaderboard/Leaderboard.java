package fr.univlille.dotsboxes.leaderboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leaderboard {

    private final List<LeaderboardPlayer> leaderboard;

    public Leaderboard(List<LeaderboardPlayer> playersList) {
        this.leaderboard = new ArrayList<>(playersList);
    }

    public Leaderboard() {
        this(new ArrayList<>());
    }

    public void addLeaderboardPlayer(LeaderboardPlayer player) {
        this.leaderboard.add(player);
    }

    public boolean isPresent(LeaderboardPlayer player) {
        return this.leaderboard.contains(player);
    }

    public void sort() {
        Collections.sort(this.leaderboard);
    }

    public List<LeaderboardPlayer> getLeaderboardPlayers() {
        return new ArrayList<>(this.leaderboard);
    }

    @Override
    public String toString() {
        return this.leaderboard.toString();
    }
}

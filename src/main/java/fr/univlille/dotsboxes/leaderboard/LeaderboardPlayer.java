package fr.univlille.dotsboxes.leaderboard;

import java.time.LocalDate;
import java.util.Objects;

public class LeaderboardPlayer implements Comparable<LeaderboardPlayer> {

    private final String username;
    private final Integer score;
    private final LocalDate scoreDate;

    public LeaderboardPlayer(String username, int score, LocalDate scoreDate) {
        this.username = username;
        this.score = score;
        this.scoreDate = scoreDate;
    }

    public LeaderboardPlayer(String username, int score) {
        this(username, score, LocalDate.now());
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public LocalDate getScoreDate() {
        return scoreDate;
    }

    @Override
    public int compareTo(LeaderboardPlayer other) {
        return other.score.compareTo(this.score);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LeaderboardPlayer that = (LeaderboardPlayer) obj;
        return Objects.equals(username, that.username) &&
               Objects.equals(score, that.score) &&
               Objects.equals(scoreDate, that.scoreDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, score, scoreDate);
    }

    @Override
    public String toString() {
        return this.username + ";" + this.score + ";" + this.scoreDate;
    }
}

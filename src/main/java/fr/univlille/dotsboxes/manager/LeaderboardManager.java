package fr.univlille.dotsboxes.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import fr.univlille.dotsboxes.leaderboard.Leaderboard;
import fr.univlille.dotsboxes.leaderboard.LeaderboardPlayer;

public class LeaderboardManager {
    private static LeaderboardManager instance;
    private final Leaderboard leaderboard = new Leaderboard();
    private static final Path SCORE_FOLDER = Path.of("scores");
    private static final Path SCORE_FILE = SCORE_FOLDER.resolve("scores.csv");

    private LeaderboardManager() {}

    public static synchronized LeaderboardManager getInstance() {
        if (instance == null) {
            instance = new LeaderboardManager();
        }
        return instance;
    }

    public Leaderboard getLeaderboard() {
        return this.leaderboard;
    }

    public void sortLeaderboard() {
        this.leaderboard.sort();
    }

    private void copyScoreIfNotExists() {
        try {
            if (Files.notExists(SCORE_FOLDER)) {
                Files.createDirectories(SCORE_FOLDER);
            }

            if (Files.notExists(SCORE_FILE)) {
                try (InputStream resourceStream = getClass().getResourceAsStream("/assets/csv/scores.csv")) {
                    if (resourceStream == null) {
                        System.err.println("Le fichier de ressources /assets/csv/scores.csv n'existe pas !");
                        Thread.currentThread().interrupt();
                        return;
                    }
                    Files.copy(resourceStream, SCORE_FILE);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la copie du fichier de scores : " + e.getMessage());
        }
    }

    public Leaderboard loadLeaderboard() {
        if (Files.notExists(SCORE_FILE)) {
            copyScoreIfNotExists();
        }

        try (BufferedReader bufferedReader = Files.newBufferedReader(SCORE_FILE)) {
            String line = bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(";");

                if (values.length != 3) {
                    continue;
                }

                String pseudo = values[0].trim();
                int score = Integer.parseInt(values[1].trim());
                LocalDate date = LocalDate.parse(values[2].trim());

                this.leaderboard.addLeaderboardPlayer(new LeaderboardPlayer(pseudo, score, date));
            }

        } catch (IOException ioException) {
            System.err.println("Echec du chargement du leaderboard : " + ioException.getMessage());
        } catch (NumberFormatException | DateTimeParseException exception) {
            System.err.println("Erreur de parsing dans le leaderboard : " + exception.getMessage());
        }

        return this.leaderboard;
    }

    public void saveLeaderboard() {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(SCORE_FILE)) {
            bufferedWriter.write("PSEUDO;SCORE;DATE");
            bufferedWriter.newLine();

            for (LeaderboardPlayer leaderboardPlayer : this.leaderboard.getLeaderboardPlayers()) {
                bufferedWriter.write(leaderboardPlayer.toString());
                bufferedWriter.newLine();
            }

        } catch (IOException ioException) {
            System.err.println("Erreur lors de la sauvegarde du leaderboard : " + ioException.getMessage());
        }
    }
}

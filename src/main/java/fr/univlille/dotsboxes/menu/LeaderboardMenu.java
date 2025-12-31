package fr.univlille.dotsboxes.menu;

import fr.univlille.dotsboxes.exception.MenuNotFoundException;
import fr.univlille.dotsboxes.leaderboard.LeaderboardPlayer;
import fr.univlille.dotsboxes.manager.LeaderboardManager;
import fr.univlille.dotsboxes.display.AnsiChar;
import fr.univlille.dotsboxes.menu.registry.MenuRegistry;
import fr.univlille.dotsboxes.textStyle.TextStyle;

import java.util.List;
import java.util.Scanner;

public final class LeaderboardMenu {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String LEADERBOARD_TITLE = TextStyle.BOLD+"===== Leaderboard =====\n"+TextStyle.RESET;
    private static final String NO_SCORES_MESSAGE = "Aucun score a été enregistré !";
    private static final String RETURN_MESSAGE = "\nAppuyez sur Entrée pour retourner au menu principal";
    private static final int MAX_DISPLAYED_PLAYERS = 10;
    private static final int MAX_USERNAME_LENGTH = 16;

    private LeaderboardMenu() {}

    public static synchronized void display() {
        System.out.print(AnsiChar.CLEAR);
        System.out.flush();
        System.out.println(LEADERBOARD_TITLE);

        LeaderboardManager leaderboardManager = LeaderboardManager.getInstance();
        leaderboardManager.sortLeaderboard();
        List<LeaderboardPlayer> players = leaderboardManager.getLeaderboard().getLeaderboardPlayers();

        if (players.isEmpty()) {
            System.out.println(NO_SCORES_MESSAGE);
        } else {
            displayTopPlayers(players);
        }

        System.out.print(RETURN_MESSAGE);
        SCANNER.nextLine();
        try {
            MenuRegistry.get("main").open();
        } catch (MenuNotFoundException exception) {
            System.err.println("Menu principal introuvable.");
            System.exit(1);
        }
    }

    private static void displayTopPlayers(List<LeaderboardPlayer> players) {
        for (int i = 0; i < Math.min(players.size(), MAX_DISPLAYED_PLAYERS); i++) {
            LeaderboardPlayer player = players.get(i);
            String username = player.getUsername();

            if (username.length() > MAX_USERNAME_LENGTH) {
                username = username.substring(0, MAX_USERNAME_LENGTH);
            }

            String couleurTop3 = "";

            if (i == 0) {
                couleurTop3 = "\u001B[38;5;220m";
            } else if (i == 1) {
                couleurTop3 = "\u001B[38;5;250m";
            } else if (i == 2) {
                couleurTop3 = "\u001B[38;5;130m";
            } else {
                couleurTop3 = "\u001B[38;5;15m";
            }

            System.out.printf(TextStyle.BOLD+couleurTop3+"#%d"+TextStyle.RESET+" : %s | %d points %n",
                    i + 1,
                    username,
                    player.getScore());
        }
    }
}

package fr.univlille.dotsboxes.game;

import fr.univlille.dotsboxes.color.Color;
import fr.univlille.dotsboxes.display.AnsiChar;
import fr.univlille.dotsboxes.exception.MenuNotFoundException;
import fr.univlille.dotsboxes.grid.Grid;
import fr.univlille.dotsboxes.leaderboard.LeaderboardPlayer;
import fr.univlille.dotsboxes.manager.GameManager;
import fr.univlille.dotsboxes.manager.LeaderboardManager;
import fr.univlille.dotsboxes.menu.registry.MenuRegistry;
import fr.univlille.dotsboxes.player.Player;
import fr.univlille.dotsboxes.textStyle.TextStyle;
import fr.univlille.dotsboxes.util.Tuple;

import java.time.LocalDate;
import java.util.Scanner;

public class Duel {
    private static final String PLAYER_SYMBOL = "██";
    private static final String GAME_TITLE = AnsiChar.CLEAR + TextStyle.BOLD + "\n=== BIENVENUE DANS DOT AND BOXES ===\n" + TextStyle.RESET;
    private final Grid grid;
    private boolean isPlayer1Turn;
    private final Scanner scanner;
    private final GameManager gameManager;

    public Duel(String player1Name, String player2Name, int gridWidth, int gridHeight) {
        this.gameManager = GameManager.getInstance();
        this.scanner = new Scanner(System.in);
        this.isPlayer1Turn = true;

        initializePlayers(player1Name, player2Name);
        this.grid = new Grid(gridWidth, gridHeight);
    }

    private void initializePlayers(String player1Name, String player2Name) {
        Player player1 = new Player(player1Name, PLAYER_SYMBOL);
        Player player2 = new Player(player2Name, PLAYER_SYMBOL);

        Tuple<Player, Player> players = new Tuple<>(player1, player2);
        gameManager.setPlayers(players);
    }

    public void startGame() {
        displayWelcomeMessage();
        gameLoop();
        displayResults();
    }

    private void displayWelcomeMessage() {
        System.out.println(GAME_TITLE);
        System.out.println("Joueurs: " + gameManager.getPlayer1Color() + TextStyle.BOLD + gameManager.getFirstPlayer() + Color.RESET + " vs " + gameManager.getPlayer2Color() + TextStyle.BOLD + gameManager.getSecondPlayer() + Color.RESET);
        System.out.println("Objectif: Conquérir le plus de carrés possibles sur la grille !");
        System.out.print("\nAppuyer sur Entrée pour commencer ");
        scanner.nextLine();
    }

    private void gameLoop() {
        while (!grid.isGameFinished()) {
            playTurn();
        }
    }

    private void playTurn() {
        boolean validMove = false;
        while (!validMove) {
            displayGameState();
            validMove = grid.selectEdge(getCurrentPlayer(), scanner);
            if (!validMove) {
                System.out.print("Appuyer sur Entrée pour saisir à nouveau ");
                scanner.nextLine();
            }
        }

        int squaresClaimed = grid.checkAndClaimBoxes(getCurrentPlayer());
        handleTurnResult(squaresClaimed);
    }

    private void handleTurnResult(int squaresClaimed) {
        if (squaresClaimed > 0) {
            System.out.println("\n" + getCurrentPlayer().getName() + " a complété " + squaresClaimed + " carré(s) !");
            System.out.println("Vous rejouez !");
        } else {
            switchPlayer();
        }
    }

    private void displayGameState() {
        displayScores();
        grid.display();
    }

    private void displayScores() {
        System.out.print(AnsiChar.CLEAR + "\n" + gameManager.getPlayer1Color() + TextStyle.BOLD + gameManager.getFirstPlayer().getName() + Color.RESET + ": " + gameManager.getFirstPlayer().getScore() + "   ");
        System.out.print("" + gameManager.getPlayer2Color() + TextStyle.BOLD + gameManager.getSecondPlayer().getName() + Color.RESET + ": " + gameManager.getSecondPlayer().getScore());
        System.out.println();
    }

    private Player getCurrentPlayer() {
        return isPlayer1Turn ? gameManager.getFirstPlayer() : gameManager.getSecondPlayer();
    }

    private void switchPlayer() {
        isPlayer1Turn = !isPlayer1Turn;
    }

    private void displayResults() {
        System.out.println(TextStyle.BOLD + "\n=== JEU TERMINÉ ===" + TextStyle.RESET);
        grid.display();
        displayFinalScores();

        Player winner = determineWinner();
        GameManager gameManager = GameManager.getInstance();
        if (winner != null) {
            System.out.println("🏆 Félicitations " + getPlayerColor(winner, gameManager) + TextStyle.BOLD + winner.getName() + Color.RESET + " ! Vous avez gagné avec " + TextStyle.BOLD + winner.getScore() + TextStyle.RESET + " points !");
            saveWinnerToLeaderboard(winner);
        } else {
            System.out.println("🤝 Match nul ! Beau jeu à tous les deux !");
        }
        
        waitForUserInput();

        try{
            MenuRegistry.get("main").open();
        } catch (MenuNotFoundException exception){
            System.err.println("Menu principale introuvable");
        }
    }

    private void displayFinalScores() {
        System.out.println("Scores finaux:");
        System.out.println("- " + getPlayerColor(gameManager.getFirstPlayer(), gameManager) + TextStyle.BOLD + gameManager.getFirstPlayer().getName() + TextStyle.RESET + ": " + gameManager.getFirstPlayer().getScore());
        System.out.println("- " + getPlayerColor(gameManager.getSecondPlayer(), gameManager) + TextStyle.BOLD + gameManager.getSecondPlayer().getName() + TextStyle.RESET + ": " + gameManager.getSecondPlayer().getScore());
    }

    private Player determineWinner() {
        Player player1 = gameManager.getFirstPlayer();
        Player player2 = gameManager.getSecondPlayer();

        if (player1.getScore() > player2.getScore()) {
            return player1;
        } else if (player2.getScore() > player1.getScore()) {
            return player2;
        }
        return null;
    }

    private void saveWinnerToLeaderboard(Player winner) {
        LeaderboardManager leaderboardManager = LeaderboardManager.getInstance();
        LeaderboardPlayer leaderboardPlayer = new LeaderboardPlayer(winner.getName(), winner.getScore(), LocalDate.now());

        leaderboardManager.getLeaderboard().addLeaderboardPlayer(leaderboardPlayer);
        leaderboardManager.saveLeaderboard();

        System.out.println("Score sauvegardé dans le classement !");
    }

    private void waitForUserInput() {
        System.out.print("\nAppuyez sur Entrée pour retourner au menu ");
        scanner.nextLine();
    }

    private Color getPlayerColor(Player owner, GameManager gameManager) {
        if (owner == gameManager.getFirstPlayer()) {
            return gameManager.getPlayer1Color();
        } else if (owner == gameManager.getSecondPlayer()) {
            return gameManager.getPlayer2Color();
        }
        return Color.RESET;
    }

}

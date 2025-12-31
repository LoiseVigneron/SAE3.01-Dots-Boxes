package fr.univlille.dotsboxes.grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.univlille.dotsboxes.game.GameRules;
import fr.univlille.dotsboxes.player.Player;
import fr.univlille.dotsboxes.textStyle.TextStyle;
import fr.univlille.dotsboxes.manager.GameManager;
import fr.univlille.dotsboxes.color.Color;
import fr.univlille.dotsboxes.display.AnsiChar;

public class Grid {
    
    private final int maxX;
    private final int maxY;
    private final List<List<Point>> pointsList = new ArrayList<>();
    private final List<List<Arrete>> edgesList = new ArrayList<>();
    private final List<List<Case>> casesList = new ArrayList<>();

    public Grid(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
        createPoints();
        createEdges();
        createCases();
    }

    private void createPoints() {
        for (int x = 0; x < this.maxY; x++) {
            pointsList.add(new ArrayList<>());
            for (int y = 0; y < this.maxX; y++) {
                pointsList.get(x).add(new Point(x, y));
            }
        }
    }

    private void createEdges() {
        edgesList.add(new ArrayList<>());
        edgesList.add(new ArrayList<>());

        for (int x = 0; x < this.maxY; x++) {
            for (int y = 0; y < this.maxX - 1; y++) {
                edgesList.get(0).add(new Arrete(pointsList.get(x).get(y), pointsList.get(x).get(y + 1)));
            }
        }

        for (int x = 0; x < this.maxY - 1; x++) {
            for (int y = 0; y < this.maxX; y++) {
                edgesList.get(1).add(new Arrete(pointsList.get(x).get(y), pointsList.get(x + 1).get(y)));
            }
        }
    }

    private void createCases() {
        for (int x = 0; x < this.maxY - 1; x++) {
            casesList.add(new ArrayList<>());
            for (int y = 0; y < this.maxX - 1; y++) {
                casesList.get(x).add(new Case(x, y,
                    edgesList.get(0).get((maxX - 1) * x + y),
                    edgesList.get(0).get((maxX - 1) * (x + 1) + y),
                    edgesList.get(1).get(maxX * x + y),
                    edgesList.get(1).get(maxX * x + y + 1)));
            }
        }
    }

    public boolean selectEdge(Player player, Scanner scanner) {
        GameManager gameManager = GameManager.getInstance();
        System.out.println("\n" + getPlayerColor(player, gameManager) + TextStyle.BOLD + player.getName() + Color.RESET + ", c'est votre tour !");
        System.out.println("\nFormat: H ou V (Horizontal ou Vertical) + espace + n° de ligne + espace + n° de colonne");
        System.out.println("Ex : H 0 0");
        System.out.print("Sélectionnez une arrête (Tapez 'help' pour revoir les règles du jeu) : ");

        String input = scanner.nextLine().trim().toUpperCase();

        if(input.equals("HELP")) {
            GameRules.displayRulesWithoutReturn();
            display();
            return selectEdge(player, scanner);
        }

        String[] parts = input.split(" ");

        if (parts.length != 3) {
            System.out.println(AnsiChar.CLEAR + "Format invalide ! Utilisez : H/V ligne colonne");
            return false;
        }

        try {
            String orientation = parts[0];
            int row = Integer.parseInt(parts[1]);
            int col = Integer.parseInt(parts[2]);

            if (orientation.equals("H")) {
                return selectHorizontalEdge(player, row, col);
            } else if (orientation.equals("V")) {
                return selectVerticalEdge(player, row, col);
            } else {
                System.out.println(AnsiChar.CLEAR + "Orientation invalide ! Utilisez H ou V");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println(AnsiChar.CLEAR + "Coordonnées invalides !");
            return false;
        }
    }

    private boolean selectHorizontalEdge(Player player, int row, int col) {
        if (row < 0 || row >= maxY || col < 0 || col >= maxX - 1) {
            System.out.println(AnsiChar.CLEAR + "Coordonnées hors limites !");
            return false;
        }

        int index = (maxX - 1) * row + col;
        if (index >= edgesList.get(0).size()) {
            System.out.println(AnsiChar.CLEAR + "Arête invalide !");
            return false;
        }

        Arrete edge = edgesList.get(0).get(index);
        if (edge.getSelection()) {
            System.out.println(AnsiChar.CLEAR + "Cette arête est déjà sélectionnée !");
            return false;
        }

        edge.setOwner(player);
        return true;
    }

    private boolean selectVerticalEdge(Player player, int row, int col) {
        if (row < 0 || row >= maxY - 1 || col < 0 || col >= maxX) {
            System.out.println(AnsiChar.CLEAR + "Coordonnées hors limites !");
            return false;
        }

        int index = maxX * row + col;
        if (index >= edgesList.get(1).size()) {
            System.out.println(AnsiChar.CLEAR + "Arête invalide !");
            return false;
        }

        Arrete edge = edgesList.get(1).get(index);
        if (edge.getSelection()) {
            System.out.println(AnsiChar.CLEAR + "Cette arête est déjà sélectionnée !");
            return false;
        }

        edge.setOwner(player);
        return true;
    }

    public int checkAndClaimBoxes(Player player) {
        int boxesClaimed = 0;
        for (int i = 0; i < maxY - 1; i++) {
            for (int j = 0; j < maxX - 1; j++) {
                Case currentCase = casesList.get(i).get(j);
                if (currentCase.getOwner() == null && currentCase.isCompleted()) {
                    currentCase.setOwner(player);
                    player.incrementScore();
                    boxesClaimed++;
                }
            }
        }
        return boxesClaimed;
    }

    public boolean isGameFinished() {
        for (int i = 0; i < maxY - 1; i++) {
            for (int j = 0; j < maxX - 1; j++) {
                if (!casesList.get(i).get(j).isCompleted()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void display() {
        StringBuilder displayBuilder = new StringBuilder();
        GameManager gameManager = GameManager.getInstance();

        displayBuilder.append("   ");
        for (int col = 0; col < this.maxX; col++) {
            if (col == 0) {
                displayBuilder.append(String.format("%d", col)).append(" ");
            } else {
                displayBuilder.append(String.format("%4d ", col));
            }
        }
        displayBuilder.append("\n");

        for (int i = 0; i < this.maxY - 1; i++) {
            displayBuilder.append(String.format("%2d ", i));

            for (int x = 0; x < this.maxX - 1; x++) {
                Arrete horizontalEdge = edgesList.get(0).get((maxX - 1) * i + x);
                if (!horizontalEdge.getSelection()) {
                    displayBuilder.append("O════");
                } else {
                    Color edgeColor = getPlayerColor(horizontalEdge.getOwner(), gameManager);
                    displayBuilder.append("O").append(edgeColor).append("━━━━").append(Color.RESET);
                }
            }
            displayBuilder.append("O\n");

            displayBuilder.append("   ");
            for (int x = 0; x < this.maxX - 1; x++) {
                Arrete verticalEdge = edgesList.get(1).get(maxX * i + x);
                if (!verticalEdge.getSelection()) {
                    displayBuilder.append("║ ").append(this.casesList.get(i).get(x).getContent()).append(" ");
                } else {
                    Color edgeColor = getPlayerColor(verticalEdge.getOwner(), gameManager);
                    Color caseColor = getPlayerColor(this.casesList.get(i).get(x).getOwner(), gameManager);
                    displayBuilder.append(edgeColor).append("┃").append(Color.RESET)
                               .append(" ").append(caseColor).append(this.casesList.get(i).get(x).getContent())
                               .append(Color.RESET).append(" ");
                }
            }

            Arrete lastVerticalEdge = edgesList.get(1).get(maxX * (i + 1) - 1);
            if (!lastVerticalEdge.getSelection()) {
                displayBuilder.append("║\n");
            } else {
                Color edgeColor = getPlayerColor(lastVerticalEdge.getOwner(), gameManager);
                displayBuilder.append(edgeColor).append("┃").append(Color.RESET).append("\n");
            }
        }

        displayBuilder.append(String.format("%2d ", this.maxY - 1));
        for (int x = 0; x < this.maxX - 1; x++) {
            Arrete horizontalEdge = edgesList.get(0).get((maxX - 1) * (maxY - 1) + x);
            if (!horizontalEdge.getSelection()) {
                displayBuilder.append("O════");
            } else {
                Color edgeColor = getPlayerColor(horizontalEdge.getOwner(), gameManager);
                displayBuilder.append("O").append(edgeColor).append("━━━━").append(Color.RESET);
            }
        }
        displayBuilder.append("O\n");
        System.out.println(displayBuilder.toString());
    }

    private Color getPlayerColor(Player owner, GameManager gameManager) {
        if (owner == gameManager.getFirstPlayer()) {
            return gameManager.getPlayer1Color();
        } else if (owner == gameManager.getSecondPlayer()) {
            return gameManager.getPlayer2Color();
        }
        return Color.RESET;
    }

    public int countCompletedCases() {
        int completedCases = 0;
        for (int i = 0; i < maxY - 1; i++) {
            for (int j = 0; j < maxX - 1; j++) {
                if (casesList.get(i).get(j).isCompleted()) {
                    completedCases++;
                }
            }
        }
        return completedCases;
    }

    public List<List<Case>> getCasesList() {
        return casesList;
    }

    public List<List<Arrete>> getEdgesList() {
        return edgesList;
    }

    public List<List<Point>> getPointsList() {
        return pointsList;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

}


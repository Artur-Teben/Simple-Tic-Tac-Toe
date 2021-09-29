package tictactoe;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int gridSize = 3;
        int moveCount = 1;
        boolean gameInProgress = true;
        String[][] grid = new String[gridSize][gridSize];

        initialize(grid);
        print(grid);

        while (gameInProgress) {
            makeMove(grid, moveCount);
            print(grid);
            gameInProgress = printGameProgress(grid);
            moveCount++;
        }
    }

    private static void initialize(String[][] grid) {
        for (String[] raws : grid) {
            Arrays.fill(raws, " ");
        }
    }

    public static void print(String[][] grid) {
        System.out.println("---------");
        for (String[] raw : grid) {
            System.out.print("| ");
            for (String cell : raw) {
                System.out.print(cell + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    private static void makeMove(String[][] grid, int moveCount) {
        Scanner scanner = new Scanner(System.in);
        boolean validCoordinates = false;
        int x;
        int y;

        while (!validCoordinates) {
            System.out.print("Enter the coordinates: ");
            String[] coordinates = scanner.nextLine().split(" ");

            try {
                x = Integer.parseInt(coordinates[0]);
                y = Integer.parseInt(coordinates[1]);
            } catch (NumberFormatException exception) {
                System.out.println("You should enter numbers!");
                continue;
            }

            if (x > 3 || x < 1 || y > 3 || y < 1 ) {
                System.out.println("Coordinates should be from 1 to 3!");
            } else if (!" ".equals(grid[x - 1][y - 1])) {
                System.out.println("This cell is occupied! Choose another one!");
            } else {
                grid[x - 1][y - 1] = moveCount % 2 == 1 ?  "X" : "O";
                validCoordinates = true;
            }
        }
    }

    private static boolean printGameProgress(String[][] grid) {
        if (!wins("X", grid) && !wins("O", grid) && !hasEmptyCells(grid) && !impossibleState(grid)) {
            System.out.println("Draw");
            return false;
        } else if (wins("X", grid) && !impossibleState(grid)) {
            System.out.println("X wins");
            return false;
        } else if (wins("O", grid) && !impossibleState(grid)) {
            System.out.println("O wins");
            return false;
        } else if (hasEmptyCells(grid) && !impossibleState(grid)) {
            System.out.println("Game not finished");
        } else if (impossibleState(grid)) {
            System.out.println("Impossible");
        }
        return true;
    }

    private static boolean hasEmptyCells(String[][] grid) {
        for (String[] raw : grid) {
            for (String cell : raw) {
                if (cell.equals(" ")) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean wins(String symbol, String[][] grid) {
        return symbol.equals(grid[0][0]) && symbol.equals(grid[0][1]) && symbol.equals(grid[0][2])
                || symbol.equals(grid[1][0]) && symbol.equals(grid[1][1]) && symbol.equals(grid[1][2])
                || symbol.equals(grid[2][0]) && symbol.equals(grid[2][1]) && symbol.equals(grid[2][2])
                || symbol.equals(grid[0][0]) && symbol.equals(grid[1][0]) && symbol.equals(grid[2][0])
                || symbol.equals(grid[0][1]) && symbol.equals(grid[1][1]) && symbol.equals(grid[2][1])
                || symbol.equals(grid[0][2]) && symbol.equals(grid[1][2]) && symbol.equals(grid[2][2])
                || symbol.equals(grid[0][0]) && symbol.equals(grid[1][1]) && symbol.equals(grid[2][2])
                || symbol.equals(grid[0][2]) && symbol.equals(grid[1][1]) && symbol.equals(grid[2][0]);
    }

    private static boolean impossibleState(String[][] grid) {
        int xCount = 0;
        int oCount = 0;
        for (String[] raw : grid) {
            for (String cell: raw) {
                if (cell.equals("X")) {
                    xCount++;
                } else if (cell.equals("O")) {
                    oCount++;
                }
            }
        }
        return Math.abs(xCount - oCount) > 1 || wins("X", grid) && wins("O", grid);
    }
}

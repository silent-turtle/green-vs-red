package greenred.game;

import greenred.utils.Parser;
import greenred.utils.Wrapper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Grid initialState;
    private int positionX;
    private int positionY;
    private int maxGenerations;

    public static void main(String[] args) {
        Game game = new Game();
        boolean correctInput;
        do {
            try {
                correctInput = true;
                game.initialise();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                correctInput = false;
            }
        } while (!correctInput);
        int result = game.calculateOccurrences();
        System.out.println("Number of occurrences: " + result);
    }

    public void initialise() {
        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();
        System.out.print("Enter grid height and width: ");
        Wrapper<Integer> height = new Wrapper<>(), width = new Wrapper<>();
        String line = scanner.nextLine();
        String regex = ",[ ]*";
        parser.parseToInteger(line, regex, width, height);
        initialState = new Grid(width.getValue(), height.getValue());
        List<String> rows = new LinkedList<>();
        System.out.println("Enter grid values:");
        for (int i = 0; i < height.getValue(); ++i) {
            String row = scanner.nextLine();
            rows.add(row);
        }
        System.out.print("Enter cell position and generation limit: ");
        Wrapper<Integer> x = new Wrapper<>(), y = new Wrapper<>(), maxGen = new Wrapper<>();
        line = scanner.nextLine();
        parser.parseToInteger(line, regex, x, y, maxGen);
        positionY = y.getValue();
        positionX = x.getValue();
        maxGenerations = maxGen.getValue();
        createGenerationZero(rows);
    }

    public int calculateOccurrences() {
        int count = 0;
        Grid current = initialState;
        for (int i = 0; i <= maxGenerations; ++i) {
            if (current.getCell(positionX, positionY)) {
                ++count;
            }
            current = createNextGeneration(current);
        }
        return count;
    }


    private void createGenerationZero(List<String> rows) {
        Iterator<String> iterator = rows.iterator();
        for (int i = 0; i < initialState.getHeight(); i++) {
            String row = iterator.next();
            for (int j = 0; j < initialState.getWidth(); j++) {
                int cellValue = Integer.parseInt(row.substring(j, j + 1));
                boolean value = true;
                if (cellValue == 0) {
                    value = false;
                }
                initialState.setCell(j, i, value);
            }
        }
    }

    private Grid createNextGeneration(Grid current) {
        Grid next = new Grid(current.getWidth(), current.getHeight());
        for (int i = 0; i < current.getHeight(); i++) {
            for (int j = 0; j < current.getWidth(); j++) {
                int count = countGreenNeighbours(current, i, j);
                // cell is green
                if (current.getCell(j, i)) {
                    if (count == 2 || count == 3 || count == 6) {
                        next.setCell(j, i, true);
                        continue;
                    }
                    next.setCell(j, i, false);
                    continue;
                }
                //cell is red
                if (count == 3 || count == 6) {
                    next.setCell(j, i, true);
                    continue;
                }
                next.setCell(j, i, false);
            }
        }
        return next;
    }

    public int countGreenNeighbours(Grid grid, int i, int j) {
        int count = 0;
        for (int k = i - 1; k <= i + 1; ++k) {
            if (k < 0 || k >= grid.getHeight()) {
                continue;
            }
            for (int s = j - 1; s <= j + 1; ++s) {
                if (s < 0 || s >= grid.getWidth()) {
                    continue;
                }
                if (grid.getCell(s, k)) {
                    ++count;
                }
            }
        }
        if (grid.getCell(j, i)) {
            --count;
        }
        return count;
    }
}

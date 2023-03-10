package maze;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Maze implements Serializable {
    private final Integer[][] maze;
    private static final int WALL = 1;
    private static final int PATH = 0;

    // create and initialize a 2D array
    public Maze(int rows, int columns) {
        maze = new Integer[rows][columns];
        for (int r = 0; r < maze.length; r++) {
            for (int c = 0; c < maze[0].length; c++) {
                maze[r][c] = WALL;
            }
        }
        createMaze(1, 1);
        Random rng = new Random();
        punchEntrance(rng);
        punchExit(rng);
    }

    private void setPath(int row, int column) {
        maze[row][column] = PATH;
    }

    private boolean isInBounds(int row, int column) {
        return 0 <= row && row < maze.length - 1 && 0 <= column && column < maze[0].length;
    }

    private boolean isWall(int row, int column) {
        return isInBounds(row, column) && maze[row][column] == WALL;
    }

    // create maze using recursive backtracking strategy
    private void createMaze(int row, int column) {
        // set the current cell to a path, so that we don't return here later
        setPath(row, column);
        List<List<Integer>> allDirections =
                new ArrayList<>(List.of(List.of(1, 0), List.of(-1, 0), List.of(0, 1), List.of(0, -1)));
        Collections.shuffle(allDirections);

        while (!allDirections.isEmpty()) {
            List<Integer> attemptedDirection = allDirections.remove(0);

            // calculate the new node's coordinates, x2 for leaving room for a wall
            int targetCellRow = row + (attemptedDirection.get(0) * 2);
            int targetCellCol = column + (attemptedDirection.get(1) * 2);

            // check if the target node is a wall (e.g. it hasn't been visited)
            if (isWall(targetCellRow, targetCellCol)) {
                int pathCellRow = row + attemptedDirection.get(0);
                int pathCellColumn = column + attemptedDirection.get(1);
                setPath(pathCellRow, pathCellColumn);

                // path now established, call the same function on the target cell
                createMaze(targetCellRow, targetCellCol);
            }
        }
    }

    private void punchExit(Random random) {
        boolean punchExitOnBottom = maze.length % 2 == 0 ? false : random.nextBoolean();

        if (punchExitOnBottom) {
            int position = random.nextInt((maze[0].length) - 1);
            maze[maze.length - 1][position % 2 == 0 ? position + 1 : position] = PATH;
        } else {
            int position = random.nextInt((maze.length) - 1);
            maze[position % 2 == 0 ? position + 1 : position][maze[0].length - 1] = PATH;
        }
    }

    private void punchEntrance(Random random) {
        if (random.nextBoolean()) {
            int position = random.nextInt((maze[0].length) - 1);
            maze[0][position % 2 == 0 ? position + 1 : position] = PATH;
        } else {
            int position = random.nextInt((maze.length) - 1);
            maze[position % 2 == 0 ? position + 1 : position][0] = PATH;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Integer[] line : maze) {
            for (Integer cell : line) {
                if (cell == 0)
                    sb.append("  ");
                else if (cell == 1)
                    sb.append("██");
            }
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }
}

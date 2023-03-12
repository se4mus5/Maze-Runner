package maze;

import java.io.Serializable;
import java.util.*;

public class Maze implements Serializable {
    private final Integer[][] maze;
    private int entry_row;
    private int entry_column;
    private int exit_row;
    private int exit_column;
    private static final int PATH = 0;
    private static final int WALL = 1;
    private static final int DISCOVERED_PATH = 2;

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
        return 0 <= row && row < maze.length && 0 <= column && column < maze[0].length;
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
        boolean punchExitOnBottom = maze.length % 2 != 0 && random.nextBoolean();

        if (punchExitOnBottom) {
            int position = random.nextInt((maze[0].length) - 1);
            exit_row = maze.length - 1;
            exit_column = position % 2 == 0 ? position + 1 : position;
            maze[exit_row][exit_column] = PATH;
        } else {
            int position = random.nextInt((maze.length) - 1);
            exit_row = position % 2 == 0 ? position + 1 : position;
            exit_column = maze[0].length - 1;
            maze[exit_row][exit_column] = PATH;
        }
    }

    private void punchEntrance(Random random) {
        if (random.nextBoolean()) {
            int position = random.nextInt((maze[0].length) - 1);
            entry_row = 0;
            entry_column = position % 2 == 0 ? position + 1 : position;
            maze[entry_row][entry_column] = PATH;
        } else {
            int position = random.nextInt((maze.length) - 1);
            entry_row = position % 2 == 0 ? position + 1 : position;
            entry_column = 0;
            maze[entry_row][entry_column] = PATH;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == PATH)
                    sb.append("  ");
                else if (maze[i][j] == WALL)
                    sb.append("██");
                else if (maze[i][j] == DISCOVERED_PATH) {
                    maze[i][j] = PATH;
                    sb.append("//");
                }
            }
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

    public void findEscape() {
        Set<List<Integer>> visited = new HashSet<>();
        findEscape(entry_row, entry_column, visited);
    }

    private boolean findEscape(int row, int column, Set<List<Integer>> visited) {
        maze[row][column] = DISCOVERED_PATH;

        visited.add(Arrays.asList(row, column));
        if (row == exit_row && column == exit_column) {
            return true;
        }

        for (List<Integer> coordinates : neighbors(row, column))
            if (!visited.contains(Arrays.asList(coordinates.get(0), coordinates.get(1))))
                if (findEscape(coordinates.get(0), coordinates.get(1), visited)) {
                    return true;
                }

        maze[row][column] = PATH;
        return false;
    }

    private Set<List<Integer>> neighbors(int row, int column) {
        Set<List<Integer>> neighbors = new HashSet<>();
        if (isInBounds(row - 1, column) && maze[row - 1][column] == PATH)
            neighbors.add(Arrays.asList(row - 1, column));
        if (isInBounds(row + 1, column) && maze[row + 1][column] == PATH)
            neighbors.add(Arrays.asList(row + 1, column));
        if (isInBounds(row, column - 1) && maze[row][column - 1] == PATH)
            neighbors.add(Arrays.asList(row, column - 1));
        if (isInBounds(row, column + 1) && maze[row][column + 1] == PATH)
            neighbors.add(Arrays.asList(row, column + 1));

        return neighbors;
    }

}

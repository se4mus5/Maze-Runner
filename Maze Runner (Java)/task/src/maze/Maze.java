package maze;

public class Maze {
    private Integer[][] maze;

    public Maze() {
//        ████████████████████
//            ██  ██  ██    ██
//        ██  ██      ██  ████
//        ██      ██████
//        ██  ██          ████
//        ██  ██  ██████  ████
//        ██  ██  ██      ████
//        ██  ██  ██████  ████
//        ██  ██      ██    ██
//        ████████████████████
//        1 2 3 4 5 6 7 8 9 10
        maze = new Integer[][] {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 1, 0, 1, 0, 1, 0, 0, 1},
                {1, 0, 1, 0, 0, 0, 1, 0, 1, 1},
                {1, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {1, 0, 1, 0, 0, 0, 0, 0, 1, 1},
                {1, 0, 1, 0, 1, 1, 1, 0, 1, 1},
                {1, 0, 1, 0, 1, 0, 0, 0, 1, 1},
                {1, 0, 1, 0, 1, 1, 1, 0, 1, 1},
                {1, 0, 1, 0, 0, 0, 1, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
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

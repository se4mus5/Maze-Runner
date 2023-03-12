package maze;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.util.Map.entry;

public class AppLogic {
    private static Map<Integer, String> menuItems;
    private final Scanner scanner;
    private Maze maze;

    public AppLogic() {
        menuItems = new HashMap<>(Map.ofEntries(
                entry(1, "Generate a new maze"),
                entry(2, "Load a maze"),
                entry(0, "Exit")
        ));
        scanner = new Scanner(System.in);
    }

    private void addMazeExistenceDependentMenuItems() {
        menuItems.putIfAbsent(3, "Save the maze");
        menuItems.putIfAbsent(4, "Display the maze");
        menuItems.putIfAbsent(5, "Find the escape");
    }

    private int getMenuChoice() {
        int menuChoice;
        do {
            menuChoice = Integer.parseInt(scanner.nextLine());
            if (!menuItems.containsKey(menuChoice)) {
                System.out.println("Incorrect option. Please try again");
            }
        } while (!menuItems.containsKey(menuChoice));
        return menuChoice;
    }

    public void start() { // implements main UI workflow
        boolean stop = false;
        do {
            displayMenu();
            int menuChoice = getMenuChoice();
            switch (menuChoice) {
                case 1 -> generateMazeWorkflow();
                case 2 -> loadMazeWorkflow();
                case 3 -> saveMazeWorkflow();
                case 4 -> System.out.println(maze);
                case 5 -> findEscape();
                case 0 -> { stop = true; System.out.println("Bye!"); } 
            }
        } while (!stop);
    }

    private void findEscape() {
        maze.findEscape();
        System.out.println(maze);
    }

    private void saveMazeWorkflow() {
        String fileName = scanner.nextLine();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(maze);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            System.out.printf("The file %s does not exist%n", fileName);
        } catch (IOException e ) {
            e.printStackTrace();
        }
    }

    private void loadMazeWorkflow() {
        String fileName = scanner.nextLine();
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            maze = (Maze) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            addMazeExistenceDependentMenuItems();
        } catch (FileNotFoundException e) {
            System.out.printf("The file %s does not exist%n", fileName);
        } catch (IOException | ClassNotFoundException e ) {
            e.printStackTrace();
        }
    }

    private void generateMazeWorkflow() {
        System.out.println("Enter the size of a new maze");
        int mazeDimension = Integer.parseInt(scanner.nextLine());
        maze = new Maze(mazeDimension, mazeDimension);
        addMazeExistenceDependentMenuItems();
        System.out.println(maze);
    }

    private void displayMenu() {
        System.out.println("=== Menu ===");
        for (Map.Entry<Integer, String> menuItem : menuItems.entrySet()) {
            if (menuItem.getKey() == 0) continue;
            System.out.printf("%d. %s%n", menuItem.getKey(), menuItem.getValue());
        }
        System.out.printf("%d. %s%n", 0, menuItems.get(0));
    }
}

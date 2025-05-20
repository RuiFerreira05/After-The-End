package tps.tp4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {
    public static void printTitle(String title) {
        int size = Math.max(title.length() + 4, 41);
        StringBuilder sb = new StringBuilder();
        sb.append("=".repeat(size)).append("\n")
            .append(" ".repeat(size / 2 - title.length() / 2)).append(title).append("\n")
            .append("=".repeat(size)).append("\n");
        System.out.println(sb.toString());
    }

    public static int choiceList(String[] options, Scanner scanner, int[] separators) {
        List<Integer> separatorList = new ArrayList<>();
        for (int separator : separators) {
            separatorList.add(separator);
        }
        while (true) {
            for (int i = 0; i < options.length; i++) {
                if (separatorList.contains(i)) {
                    System.out.println();
                }
                System.out.println((i + 1) + ". " + options[i]);
            }
            System.out.println("\nChoose an option:\n");
            System.out.print(">> ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                if (choice < 1 || choice > options.length) {
                    System.out.println("Invalid choice. Please try again.");
                    continue;
                }
                return choice;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            } 
        }
    }

    public static int choiceList(String[] options, Scanner scanner, int separator) {
        return choiceList(options, scanner, new int[] {separator});
    }

    public static int choiceList(String[] options, Scanner scanner) {
        return choiceList(options, scanner, -1);
    }
}

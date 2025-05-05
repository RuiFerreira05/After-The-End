package tps.tp4;

import java.util.Scanner;

public class Utils {
    public static void printTitle(String title) {
        int size = title.length() + 4;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append("=");
        }
        System.out.println();
        System.out.println(sb.toString());
        System.out.println("  " + title + "  ");
        System.out.println(sb.toString());
    }

    public static int choiceList(String[] options, Scanner scanner) {
        while (true) {
            int i = 0;
            for (String option : options) {
                System.out.println((i + 1) + ". " + option);
                i++;
            }
            System.out.println("Choose an option: \n");
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
}

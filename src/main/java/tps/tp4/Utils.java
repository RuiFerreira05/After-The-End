package tps.tp4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import tps.tp4.ui.swing.UI_Swing;


/**
 * Utility class providing helper methods for UI and input handling.
 */
public class Utils {
    /**
     * Prints a formatted title with surrounding lines for emphasis.
     * @param title The title to print.
     */
    public static void printTitle(String title) {
        int size = Math.max(title.length() + 4, 41);
        StringBuilder sb = new StringBuilder();
        sb.append("=".repeat(size)).append("\n")
            .append(" ".repeat(size / 2 - title.length() / 2)).append(title).append("\n")
            .append("=".repeat(size)).append("\n");
        System.out.println(sb.toString());
    }

    /**
     * Displays a list of options to the user, with optional separators, and returns the chosen index.
     * @param options The options to display.
     * @param scanner The Scanner for user input.
     * @param separators Indices at which to insert blank lines.
     * @return The user's choice (1-based index).
     */
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

    /**
     * Displays a list of options to the user with a single separator.
     * @param options The options to display.
     * @param scanner The Scanner for user input.
     * @param separator The index at which to insert a blank line.
     * @return The user's choice (1-based index).
     */
    public static int choiceList(String[] options, Scanner scanner, int separator) {
        return choiceList(options, scanner, new int[] {separator});
    }

    /**
     * Displays a list of options to the user with no separators.
     * @param options The options to display.
     * @param scanner The Scanner for user input.
     * @return The user's choice (1-based index).
     */
    public static int choiceList(String[] options, Scanner scanner) {
        return choiceList(options, scanner, -1);
    }

    /**
     * Creates a JButton with the specified text and action listener.
     * Uses the UI_Swing constants for styling (Background, Foreground and Font).
     * Also adds hover effects to change the button's background color when hovered.
     * 
     * @see UI_Swing
     * @param text The text to display on the button.
     * @param action The ActionListener to handle button clicks.
     * @return The created JButton.
     */
    public static JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setBackground(UI_Swing.BUTTON_COLOR);
        button.setForeground(UI_Swing.BUTTON_TEXT_COLOR);
        button.setFocusPainted(false);
        button.setFont(UI_Swing.DEFAULT_FONT);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(button.getBackground().darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(UI_Swing.BUTTON_COLOR);
            }
        });

        button.addActionListener(action);

        return button;
    }
}

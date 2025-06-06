package tps.tp4.ui.swing;

import javax.swing.*;
import java.awt.*;

import tps.tp4.App;
import tps.tp4.ui.UI;

public class UI_Swing implements UI {

    public static final Color BACKGROUND_COLOR = new Color(207, 207, 207);
    public static final Color TITLE_COLOR = new Color(0, 0, 0);
    public static final Color BUTTON_COLOR = new Color(83, 83, 83);
    public static final Color BUTTON_TEXT_COLOR = new Color(255, 255, 255);

    public static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 20);
    public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 62);

    App app;

    public UI_Swing(App app) {
        this.app = app;
    }

    @Override
    public void start() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("After the End");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            JPanel container = new JPanel(new CardLayout());
            frame.add(container);

            UI_Controller controller = new UI_Controller(container);

            controller.registerPanel(new UI_MainMenu(controller), "MAIN_MENU");
            controller.registerPanel(new UI_SettingsMenu(controller), "SETTINGS_MENU");

            controller.showPanel("MAIN_MENU");
            frame.setVisible(true);
        });
    }
}

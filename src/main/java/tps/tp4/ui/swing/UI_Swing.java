package tps.tp4.ui.swing;

import javax.swing.*;
import java.awt.*;

import tps.tp4.App;
import tps.tp4.ui.UI;

public class UI_Swing implements UI {

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

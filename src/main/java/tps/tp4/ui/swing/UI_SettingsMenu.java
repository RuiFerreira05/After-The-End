package tps.tp4.ui.swing;

import javax.swing.*;
import java.awt.*;

public class UI_SettingsMenu extends UI_Menu {

    public UI_SettingsMenu(UI_Controller controller) {
        super(controller);
    }

    @Override
    public void BuildUI() {
        setBackground(Color.DARK_GRAY);
    
        JLabel titleLabel = new JLabel("Settings Menu");
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);
    }
}

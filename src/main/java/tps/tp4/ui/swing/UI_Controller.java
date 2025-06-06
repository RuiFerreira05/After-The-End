package tps.tp4.ui.swing;

import javax.swing.*;
import java.awt.*;

public class UI_Controller {

    private final JPanel container;
    private final CardLayout cardLayout;

    public UI_Controller(JPanel container) {
        this.container = container;
        this.cardLayout = (CardLayout) container.getLayout();
    }

    public void registerPanel(JPanel panel, String name) {
        container.add(panel, name);
    }

    public void showPanel(String name) {
        cardLayout.show(container, name);
    }

}

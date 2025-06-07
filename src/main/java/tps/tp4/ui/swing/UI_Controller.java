package tps.tp4.ui.swing;

import javax.swing.*;

import tps.tp4.App;

import java.awt.*;

public class UI_Controller {

    private final JPanel container;
    private final CardLayout cardLayout;
    private final App app;

    public UI_Controller(JPanel container, App app) {
        this.container = container;
        this.cardLayout = (CardLayout) container.getLayout();
        this.app = app;
    }

    public void registerPanel(JPanel panel, String name) {
        container.add(panel, name);
    }

    public void showPanel(String name) {
        cardLayout.show(container, name);
    }

    public App getApp() {
        return app;
    }

}

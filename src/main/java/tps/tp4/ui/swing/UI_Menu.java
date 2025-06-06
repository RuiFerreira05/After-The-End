package tps.tp4.ui.swing;
import javax.swing.*;
import java.awt.*;

public abstract class UI_Menu extends JPanel {

    protected final UI_Controller controller;

    public UI_Menu(UI_Controller controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        BuildUI();
    }

    public abstract void BuildUI();

}
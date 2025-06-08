package tps.tp4.ui.swing;
import javax.swing.*;
import java.awt.*;

public abstract class UI_Menu extends JPanel {

    protected final UI_Controller controller;

    /**
     * Constructor for UI_Menu.
     * Initializes the menu with a controller and sets the layout to BorderLayout.
     * Then, calls BuildUI
     * @param controller
     */
    public UI_Menu(UI_Controller controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        BuildUI();
    }

    /**
     * Abstract method to build the user interface.
     * This method should be implemented by subclasses to define the specific UI components and layout.
     */
    public abstract void BuildUI();

}
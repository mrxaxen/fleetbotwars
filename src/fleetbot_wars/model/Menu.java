/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author EG
 */
public abstract class Menu extends JPanel {

    protected static final Dimension DEFAULT_BUTTON_SIZE = new Dimension(50,50);
    protected static final Insets BUTTON_INSET = new Insets(30,80,30,80);
    protected final Dimension PREFERRED_BUTTON_SIZE = new Dimension(this.getWidth(),100);
    protected ArrayList<JButton> buttons;
    protected GUI gui;

    protected Menu(GUI gui) {
        super(new GridBagLayout());
        buttons = new ArrayList<>();
        initButtons();
        this.gui = gui;
        this.setVisible(false);
    }

    protected abstract void initButtons();

    protected JButton initOptions() {
        JButton button = new JButton("Options");
        button.addActionListener(e -> {
            gui.putMenuToFront("options");
        });
        return button;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Optional;

/**
 *
 * @author EG
 */
public class MainMenu extends Menu {

    MainMenu(GUI gui) {
        super(gui);
        initLayout();
    }

    private void initLayout() {
        buttons.forEach((button) -> {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = BUTTON_INSET;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridy = GridBagConstraints.RELATIVE;
            gbc.gridx = 0;
            gbc.weightx = 1;
            gbc.gridwidth = GridBagConstraints.RELATIVE;
            this.add(button, gbc);
        });
    }

    private JButton initPlay() {
        JButton button = new JButton("New Game");
        button.addActionListener((ActionEvent e) -> {
            this.setVisible(false);
            gui.getMenu("play").setVisible(true);
        });
        return button;
    }

    private JButton initExit() {
        JButton button = new JButton("Exit");
        button.addActionListener((ActionEvent e) -> {
            System.exit(1);
        });
        return button;
    }

    @Override
    protected void initButtons() {
        buttons.add(initPlay());
        buttons.add(initOptions());
        buttons.add(initExit());
        buttons.forEach((button) -> {
            button.setMinimumSize(DEFAULT_BUTTON_SIZE);
            button.setPreferredSize(PREFERRED_BUTTON_SIZE);
        });
    }
}

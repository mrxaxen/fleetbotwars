/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author EG
 */
class NewGameMenu extends Menu{

    NewGameMenu(GUI gui) {
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

    private JButton initCreate() {
        JButton button = new JButton("Create");
        button.addActionListener((ActionEvent e) -> {
//            create lobby
        });
        return button;
    }

    private JButton initJoin() {
        JButton button = new JButton("Join");
        button.setEnabled(false);
        button.addActionListener((ActionEvent e) -> {
//            Join lobby at ip
        });
        return button;
    }

    private JButton initBack() {
        JButton button = new JButton("Back");
        button.addActionListener((ActionEvent e) -> {
            gui.putComponentToFront(this, GUI.ComponentType.MAIN);
        });
        return button;
    }

    @Override
    protected void initButtons() {
        buttons.add(initCreate());
        buttons.add(initJoin());
        buttons.add(initBack());
        buttons.forEach((button) -> {
            button.setMinimumSize(DEFAULT_BUTTON_SIZE);
            button.setPreferredSize(PREFERRED_BUTTON_SIZE);
        });
    }
}

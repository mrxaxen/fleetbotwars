package fleetbot_wars.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author EG
 */
public class MainMenu extends Menu {

    MainMenu(GUI gui) {
        super(gui);
        initLayout();
        this.setVisible(true);
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
            gui.putComponentToFront(this, GUI.ComponentType.GAME_WINDOW);
//            gui.putComponentToFront(this,gui.ComponentType.PLAY); FUNCTIONAL CODE RESERVED FOR LATER VERISON
//            impl. start game for prototype
        });
        return button;
    }

    private JButton initExit() {
        JButton button = new JButton("Exit");
        button.addActionListener((e) -> {
            System.exit(1);
        });
        return button;
    }

    @Override
    protected void initButtons() {
        buttons.add(initPlay());
//        buttons.add(initOptions());
        buttons.add(initExit());
        buttons.forEach((button) -> {
            button.setMinimumSize(DEFAULT_BUTTON_SIZE);
            button.setPreferredSize(PREFERRED_BUTTON_SIZE);
        });
    }
}


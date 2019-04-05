/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author EG
 */
public class IngameMenu extends Menu {

    IngameMenu(GUI gui) {
        super(gui);
    }

    private JButton initPause() {
        JButton button = new JButton("Pause/Unpause");
        button.addActionListener(e -> {
//            pause game/ send pause request/ vote
        });
        return button;
    }

    private JButton initExitMatch() {
        JButton button = new JButton("Exit match");
        button.addActionListener(e -> {
//            close connection&leave What should we do with the units left?
        });
        return button;
    }

    private JButton initCloseMenu() {
        JButton button = new JButton("Return to game");
        button.addActionListener(e -> {
//            close menu, show game
        });
        return button;
    }

    @Override
    protected void initButtons() {
        buttons.add(initPause());
        buttons.add(initExitMatch());
        buttons.add(initOptions());
        buttons.add(initCloseMenu());
    }
}

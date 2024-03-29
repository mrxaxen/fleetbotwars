/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * @author WB
 */
public class GUI extends JFrame {

    static Dimension mapSize = new Dimension(100,100); //DUMMY GET FROM ENGINE
    private static GUI instance;
    private HashMap<ComponentType, JComponent> panels = new HashMap<>();

    public static GUI getInstance() {
        if (instance == null) {
            instance = new GUI();
            return instance;
        } else {
            return instance;
        }
    }

    private GUI() {
        this.setTitle("Fleetbot Wars");
        this.setMinimumSize(new Dimension(400, 400));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setVisible(true);
        initPanels();
    }

    private void initPanels() {
        MainMenu mainMenu = new MainMenu(this);
        NewGameMenu newGameMenu = new NewGameMenu(this);
        Options options = new Options(this);
        GameWindow gameWindow = new GameWindow();

        panels.put(ComponentType.MAIN, mainMenu);
        panels.put(ComponentType.NEW_GAME, newGameMenu);
//        panels.put(ComponentType.OPTIONS, options);
        panels.put(ComponentType.GAME_WINDOW, gameWindow);

        this.getContentPane().add(mainMenu);
    }

    void putComponentToFront(JComponent caller, ComponentType type) {
        JComponent panel = panels.get(type);
        caller.setVisible(false);
        panel.setVisible(true);
        this.getContentPane().remove(caller);
        this.getContentPane().add(panel);
    }

    enum ComponentType {
        MAIN,NEW_GAME,OPTIONS,INGAME,GAME_WINDOW
    }
}

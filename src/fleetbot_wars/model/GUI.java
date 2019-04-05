/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author WB
 */
public class GUI extends JFrame {
    private static GUI instance;
    private HashMap<String, Menu> menus = new HashMap<>();

    public static GUI getInstance(Dimension size) {
        if (instance == null) {
            instance = new GUI(size);
            return instance;
        } else {
            return instance;
        }
    }

    private GUI(Dimension size) {
        this.setTitle("Fleetbot Wars");
        this.setMinimumSize(new Dimension(400, 400));
        this.setSize(size);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        initMenus();
    }

    private void initMenus() {

        int width = this.getContentPane().getWidth();
        int height = this.getContentPane().getHeight();

        JLayeredPane layeredPane = this.getLayeredPane();

        MainMenu mainMenu = new MainMenu(this);
        NewGameMenu newGameMenu = new NewGameMenu(this);

        mainMenu.setBounds(0, 0, width, height);
        newGameMenu.setBounds(0, 0, width, height);

        layeredPane.add(mainMenu, 0);
        layeredPane.add(newGameMenu, 1);

        this.getContentPane().add(mainMenu);
        this.getContentPane().add(newGameMenu);

        menus.put("mainmenu", mainMenu);
        menus.put("play", newGameMenu);
//        menus.put("options", options);

        layeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                menus.forEach((key,menu) -> {
                    menu.setBounds(0,0, instance.getContentPane().getWidth(), instance.getContentPane().getHeight());
                });
            }
        });
    }

    Menu getMenu(String name) {
        return menus.get(name);
    }

}

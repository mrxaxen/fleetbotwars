/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars;

import fleetbot_wars.model.GUI;

import javax.swing.*;

import fleetbot_wars.model.Engine;
import fleetbot_wars.model.Map;
import fleetbot_wars.model.Player;
import fleetbot_wars.model.enums.VisualType;

import java.awt.*;

import visual.unit.Controllable;
import visual.unit.Infantry;
import fleetbot_wars.model.enums.ResourceType;

/**
 *
 * @author asjf86
 */
public class Main {

    private static Engine e;
    public static Engine getEngine() {
        return e;
    }

    public static void main(String[] args) {
        // TODO code application logic here

        Player[] players = new Player[4];
        players[0] = new Player("bori", 0, Color.red);
        players[1] = new Player("gabor", 1, Color.green);
        players[2] = new Player("laci", 2, Color.blue);
        players[3] = new Player("drszendrey", 3, Color.yellow);

        e = Engine.getInstance(new Map(), players);
        
        SwingUtilities.invokeLater(() -> {
            GUI gui = GUI.getInstance();
            gui.setVisible(true);
        });

        new Thread(() -> {
            try {
                synchronized (e) {
                    e.wait();
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            while (true) {
               e.actionIteration();
               e.update();
               try {
                   Thread.sleep(120);
               } catch (InterruptedException ex) {
                   ex.printStackTrace();
               }
            }
        }).start();
    }
    
}

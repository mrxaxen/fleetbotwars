/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars;

import fleetbot_wars.model.GUI;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author asjf86
 */
public class Main {

    public static void main(String[] args) {
        // TODO code application logic here

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GUI gui = GUI.getInstance();
//                gui.setVisible(true);
            }
        });
    }
    
}

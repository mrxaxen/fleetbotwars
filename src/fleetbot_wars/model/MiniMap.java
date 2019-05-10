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
 * @author WB
 */
public class MiniMap extends JPanel {

    private Tile[][] map;

    MiniMap(Tile[][] map) {
        this.map = map;
    }

    @Override
    public void paintComponent(Graphics g) {
        //int side = (getWidth()/GUI.mapSize.width) == 1 ? 2 : getWidth()/GUI.mapSize.width;
        int side = 1;
        //g.setColor(GameWindow.backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        for (Tile[] arr : map) {
            for (Tile t : arr) {
                g.setColor(t.getTileColor());
                g.fillRect(t.getCoordY() * side, t.getCoordX() * side, side, side);
            }
        }
    }

    public void updateMap(){
        this.repaint();
    }
}

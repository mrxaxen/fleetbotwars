/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars;

import fleetbot_wars.model.Engine;
import fleetbot_wars.model.Map;
import fleetbot_wars.model.Player;

/**
 *
 * @author asjf86
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //iufjgafEIFJPAIEHGTIWUETVOIWHUJORFI
        Player[] players = new Player[4];
        players[0] = new Player("bori", 0);
        players[1] = new Player("gabor", 1);
        players[2] = new Player("laci", 2);
        players[3] = new Player("drszendrey", 3);
        
        
        Engine e = new Engine(new Map(), players);
        
        System.out.println(e.getMap());
    }
    
}

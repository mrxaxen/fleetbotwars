/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author WB
 */
abstract public class Mine extends Controllable {
    
    //paired with Miner
    protected boolean active = false;
    private Timer timer;

    public Mine(int x, int y, String type, Image model, int hp, int team)
    {
        super(x, y, type, model, hp, 0, 0, 0, 1, 0, team);
        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incrRes();
            }
        };
        timer = new Timer(5000, taskPerformer); //demo param
    }   
    
    abstract protected void incrRes();
}

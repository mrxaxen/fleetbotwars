/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.unit;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import fleetbot_wars.model.enums.VisualType;

/**
 *
 * @author WB
 */
abstract public class Mine extends Controllable {

    // paired with Miner
    protected boolean active = false;
    private Timer timer;

    /**
     * called when specific Mine created
     * 
     * @param coords
     * @param type
     * @param model
     * @param hp
     * @param team
     */
    public Mine(Point coords, VisualType type, Image model, int hp, int team) {
        super(coords, type, model, hp, 0, 0, 0, 1, 1, 0, team);
        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incrRes();
            }
        };
        timer = new Timer(5000, taskPerformer); // demo param
    }

    abstract protected void incrRes();
}

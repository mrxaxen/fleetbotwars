/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import javax.swing.*;
import java.util.HashSet;

/**
 *
 * @author WB
 */
public class ActionBar extends JPanel {

    //    Builder     Soldier     Lumberjack    Miner
//    build,move  attack,move harvest,move  harvest,move
    HashSet<JButton> builder = new HashSet<>();
    HashSet<JButton> buildMenu = new HashSet<>();

    //TODO: move on right click
    ActionBar() {
        initBuilder();
    }
    private void changeActionBar(HashSet<JButton> to) {
        this.removeAll();
        for (JButton button : to) {
            this.add(button);
        }
    }

    private void initBuilder() {
        JButton build = new JButton("Build");
        build.addActionListener(e -> {
            //Resource check through statusbar's resource check method?
            changeActionBar(buildMenu);
        });
    }

    private void initBuildMenu() {
        JButton workerSpawn = new JButton("Worker Training Center");
        JButton militarySpawn = new JButton("Military Training Center");
        JButton farm = new JButton("Farm");
        JButton stoneMine = new JButton("Stone Mine");
        JButton goldMine = new JButton("Gold Mine");
        JButton turret = new JButton("Turret");
        JButton barricade = new JButton("Barricade");
        //TODO: implement actionbar menus and functions
        workerSpawn.addActionListener(e -> {

        });
        militarySpawn.addActionListener(e -> {

        });
        farm.addActionListener(e -> {

        });
        stoneMine.addActionListener(e -> {

        });
        goldMine.addActionListener(e -> {

        });
        turret.addActionListener(e -> {

        });
        barricade.addActionListener(e -> {

        });
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;
import jdk.net.SocketFlow;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author WB
 */
public class GameWindow extends JPanel {


    GameWindow(/*HashMap<ResourceType,Integer> resources,*/) {
        initGameWindow(/*resources*/);
    }

    private void initGameWindow(/*HashMap<ResourceType,Integer> resources*/) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(initGameSpace(),gbc);

        gbc.gridy = 0;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(initStatusBar(/*resources*/),gbc);
//        this.add(initMinMap(),gbc); //set GBC
//        this.add(initActionBar(), gbc); //set GBC
//        this.add(initUnitMx, gbc);
    }

    private JScrollPane initGameSpace() {
        GameSpace gameSpace = new GameSpace();
        int max = 100;
        int min = 0;
        int unitIncrement = 40;

        JScrollPane scrollPane = new JScrollPane(gameSpace,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        JScrollBar vertical = scrollPane.getVerticalScrollBar();
        JScrollBar horizontal = scrollPane.getHorizontalScrollBar();
        vertical.setMinimum(min);
        vertical.setMaximum(max);
        vertical.setPreferredSize(new Dimension(0,0));
        horizontal.setMinimum(min);
        horizontal.setMaximum(max);
        horizontal.setPreferredSize(new Dimension(0,0));
        vertical.setUnitIncrement(unitIncrement);
        horizontal.setUnitIncrement(unitIncrement);
        InputMap verticalIM = vertical.getInputMap(WHEN_IN_FOCUSED_WINDOW);
        InputMap horizontalIM = horizontal.getInputMap(WHEN_IN_FOCUSED_WINDOW);

        verticalIM.put(KeyStroke.getKeyStroke('w'),"negativeUnitIncrement");
        verticalIM.put(KeyStroke.getKeyStroke('s'),"positiveUnitIncrement");
        horizontalIM.put(KeyStroke.getKeyStroke('a'), "negativeUnitIncrement");
        horizontalIM.put(KeyStroke.getKeyStroke('d'), "positiveUnitIncrement");

        return scrollPane;
    }
    //Resources
    //TODO: Sync with engine
    private StatusBar initStatusBar(/*HashMap<ResourceType,Integer> resources*/) {
        StatusBar statusBar = new StatusBar(/*resources*/);
        return statusBar;
    }

    private JPanel initMinMap() {
        //TODO: implement
        return null;
    }

    private JPanel initActionBar() {
        //TODO: implement
        return null;
    }

    private JPanel initUnitMx() {
        //TODO: implement
        return null;
    }
//    private void initMenu() {} ?

}

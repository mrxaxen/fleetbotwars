/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 *
 * @author WB
 */
class GameWindow extends JPanel {

    static Border uiBorder = new CompoundBorder(new LineBorder(new Color(127, 78, 45)),new EtchedBorder(new Color(39, 19, 18),new Color(127, 78, 45)));
    static Color backgroundColor = new Color(39, 19, 18);

    GameWindow(/*HashMap<ResourceType,Integer> resources*/) {
        initGameWindow(/*resources*/);
    }

    private void initGameWindow(/*HashMap<ResourceType,Integer> resources*/) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 0.89;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(initGameSpace(),gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.01;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(initStatusBar(/*resources*/),gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 1;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.weighty = 0.1;

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.CENTER;
        MiniMap miniMap = initMinMap();
        this.add(miniMap,gbc); //set GBC

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.7;
        this.add(initActionBar(), gbc); //set GBC

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 0.1;
        this.add(initUnitMx(), gbc);
    }

    private JScrollPane initGameSpace() {
        GameSpace gameSpace = GameSpace.getInstance();
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
        statusBar.setBorder(GameWindow.uiBorder);
        return statusBar;
    }

    private MiniMap initMinMap() {
        //TODO: implement
        MiniMap minMap = new MiniMap(GameSpace.tiles);
        minMap.setBorder(GameWindow.uiBorder);
        return minMap;
    }

    private ActionBar initActionBar() {
        return ActionBar.getInstance();
    }

    private UnitMatrix initUnitMx() {
        //TODO: implement
        UnitMatrix unitMX = new UnitMatrix();
//        JButton button = new JButton("PLACEHOLDER UNITMX");
//        unitMX.add(button);
        return unitMX;
    }
//    private void initMenu() {} ?

}

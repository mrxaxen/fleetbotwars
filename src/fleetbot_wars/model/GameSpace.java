package fleetbot_wars.model;

import visual.ground.Ground;
import visual.unit.Unit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class GameSpace extends JPanel implements Talkative{

    private Ground[][] grounds;
    private Translation serverComm = Translation.getInstance();
    static Tile[][] tiles; //not static just for testing
    private static GameSpace instance;
    private MiniMap miniMap;

    static GameSpace getInstance() {
        if (instance == null) {
            instance = new GameSpace();
            return instance;
        } else {
            return instance;
        }
    }

    private GameSpace() {
        Tile.loadImages();
        Tile.genSections();
        grounds = serverComm.getMap();
        tiles = new Tile[GUI.mapSize.height][GUI.mapSize.width];
        GridLayout layout = new GridLayout(GUI.mapSize.height, GUI.mapSize.width);
        this.setLayout(layout);
        this.setPreferredSize(new Dimension(Tile.TILE_BASE_SIZE*GUI.mapSize.width,Tile.TILE_BASE_SIZE*GUI.mapSize.height));
        genTiles();
        initPlayerChangeKeys();
    }

    private void initPlayerChangeKeys() {
        InputMap keys = this.getInputMap();
        keys.put(KeyStroke.getKeyStroke("F1"),"player0");
        keys.put(KeyStroke.getKeyStroke("F2"),"player1");
        keys.put(KeyStroke.getKeyStroke("F3"),"player2");
        keys.put(KeyStroke.getKeyStroke("F4"),"player3");
        ActionMap actionMap = this.getActionMap();
        actionMap.put("player0", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverComm.changePlayer(0);
            }
        });actionMap.put("player1", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverComm.changePlayer(1);
            }
        });actionMap.put("player2", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverComm.changePlayer(2);
            }
        });actionMap.put("player3", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverComm.changePlayer(3);
            }
        });
    }

    private void genTiles() {
        System.out.println("Generating map...");
        for(int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                GroundType groundType = grounds[y][x].getType().getGroundType();
                Unit currUnit = grounds[y][x].getOwnerReference();
                if(currUnit != null) {
                    tiles[y][x] = new Tile(groundType, currUnit, y, x);
                } else {
                    tiles[y][x] = new Tile(groundType, null, y, x);
                }
                this.add(tiles[y][x], BorderLayout.CENTER);
            }
        }
        System.out.println("Map gen complete.");
        this.repaint();

    }

    /*public void setMiniMap(MiniMap miniMap){
        this.miniMap = miniMap;
    }*/

    public void repaintTile(Point tileAt, Unit unit, boolean isGoingTo) {
        Unit newUnit = isGoingTo ? unit : null;
        Tile tile = tiles[tileAt.x][tileAt.y];
        tile.setUnit(newUnit);
        //miniMap.repaint();
        tile.repaint();
    }

}

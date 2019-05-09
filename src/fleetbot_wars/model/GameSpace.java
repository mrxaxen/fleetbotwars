package fleetbot_wars.model;

import visual.ground.Ground;
import visual.unit.Unit;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

class GameSpace extends JPanel implements Talkative{

    private Ground[][] grounds = new Ground[GUI.mapSize.height][GUI.mapSize.width];
    private Translation serverComm = Translation.getInstance();
    static Tile[][] tiles; //not static just for testing
    private static GameSpace instance;

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
        grounds = serverComm.getMap();
        tiles = new Tile[GUI.mapSize.height][GUI.mapSize.width];
        GridLayout layout = new GridLayout(GUI.mapSize.height, GUI.mapSize.width);
        this.setLayout(layout);
        this.setPreferredSize(new Dimension(Tile.TILE_BASE_SIZE*GUI.mapSize.width,Tile.TILE_BASE_SIZE*GUI.mapSize.height));
//        generateGrounds();
        genTiles();
    }

//    void move() {
//        this.removeAll();
//        grounds = serverComm.getMap();
//        genTiles();
//        this.revalidate();
//        this.repaint();
//    }
    //DUMMY
//    private void generateGrounds() {
//        for (int y = 0; y < grounds.length; y++) {
//            for (int x = 0; x < grounds[y].length; x++) {
//                grounds[y][x] = new Ground();
//            }
//        }
//
//    }
    //DUMMY
//    private class DummyGround {
//        DummyUnit unit = new DummyUnit();
//
//        GroundType type = GroundType.DIRT;
//
//        private DummyUnit getUnit() {
//            return unit;
//        }
//
//        private GroundType getType() {
//            return type;
//        }
//
//    }

    private void genTiles() {
        System.out.println("Generating map...");
        for(int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                GroundType groundType = grounds[y][x].getType().getGroundType();
                Unit currUnit = grounds[y][x].getOwnerReference();
                if(currUnit != null) {
                    tiles[y][x] = new Tile(groundType, currUnit, x, y);
                } else {
                    tiles[y][x] = new Tile(groundType, null, x, y);
                }
                this.add(tiles[y][x], BorderLayout.CENTER);
            }
        }
        System.out.println("Map gen complete.");
        this.repaint();

    }
//    DUMMY
//    private class DummyUnit {
//        UnitType type;
//
//        DummyUnit() {
//            int rand = (int)(Math.random() * 4);
//            switch (rand) {
//                case 0:
//                    type = UnitType.TREE;
//                    break;
//                case 1:
//                    type = UnitType.BUILDER;
//                    break;
//                case 2:
//                    type = UnitType.TURRET;
//                    break;
//            }
//        }

//        private UnitType getType() {
//            return type;
//        }
//    }

}

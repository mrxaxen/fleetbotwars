package fleetbot_wars.model;

import javax.swing.*;
import java.awt.*;

class GameSpace extends JPanel implements Talkative{

    private DummyGround[][] grounds;// DUMMY
    /*private*/ static Tile[][] tiles; //not static just for testing

    GameSpace() {
        Tile.loadImages();
        grounds = new DummyGround[GUI.mapSize.height][GUI.mapSize.width];
        tiles = new Tile[GUI.mapSize.height][GUI.mapSize.width];
        GridLayout layout = new GridLayout(GUI.mapSize.height, GUI.mapSize.width);
        this.setLayout(layout);
        this.setPreferredSize(new Dimension(Tile.TILE_BASE_SIZE*GUI.mapSize.width,Tile.TILE_BASE_SIZE*GUI.mapSize.height));
        generateGrounds();
        genTiles();
    }
    //DUMMY
    private void generateGrounds() {
        for (int y = 0; y < grounds.length; y++) {
            for (int x = 0; x < grounds[y].length; x++) {
                grounds[y][x] = new DummyGround();
            }
        }

    }
    //DUMMY
    private class DummyGround {
        DummyUnit unit = new DummyUnit();

        GroundType type = GroundType.BASE;

        private DummyUnit getUnit() {
            return unit;
        }

        private GroundType getType() {
            return type;
        }

    }

    private void genTiles() {
        System.out.println("Generating map...");
        for(int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                tiles[y][x] = new Tile(grounds[y][x].getType(), grounds[y][x].getUnit().getType(), x, y);
                this.add(tiles[y][x],BorderLayout.CENTER);
            }
        }
        System.out.println("Map gen complete.");
        this.repaint();
    }
    //DUMMY
    private class DummyUnit {
        UnitType type;

        DummyUnit() {
            int rand = (int)(Math.random() * 4);
            switch (rand) {
                case 0:
                    type = UnitType.TREE;
                    break;
                case 1:
                    type = UnitType.BUILDER;
                    break;
                case 2:
                    type = UnitType.TURRET;
                    break;
//                case 4:
//                    type = null;
//                    break;
            }
        }

        private UnitType getType() {
            return type;
        }
    }

}

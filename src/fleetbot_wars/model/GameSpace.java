package fleetbot_wars.model;

import javax.swing.*;
import java.awt.*;

class GameSpace extends JPanel {

    private DummyGround[][] grounds;// DUMMY
    private Tile[][] tiles;

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

    private void genTiles() {
        System.out.println("Generating map...");
        for(int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                tiles[y][x] = new Tile(grounds[y][x].getType(), grounds[y][x].getUnit().getType(), new Dimension());
                this.add(tiles[y][x],BorderLayout.CENTER);
            }
        }
        System.out.println("Map gen complete.");
        this.repaint();
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
    //DUMMY
    private class DummyUnit {
        UnitType type = UnitType.TREE;

        private UnitType getType() {
            return type;
        }
    }

}

package fleetbot_wars.model;

import visual.unit.Unit;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class Tile extends JPanel {

    static final int TILE_BASE_SIZE = 48;
    private static HashMap<GroundType, Image> groundImages;
    private static HashMap<UnitType, Image> unitImages;

    private final int xCoord;
    private final int yCoord;
    private GroundType groundType;
    private UnitType unitType;
    private SelectionController selectionController = SelectionController.getInstance();
    private Translation serverComm = Translation.getInstance();

    Tile(GroundType groundType, UnitType unitType, int x, int y) {
        this.xCoord = x;
        this.yCoord = y;
        this.unitType = unitType;
        this.groundType = groundType;
        this.setPreferredSize(new Dimension(TILE_BASE_SIZE,TILE_BASE_SIZE));
        setMouseListener();
    }

    static void loadImages() {
        Tile.groundImages = new HashMap<>();
        Tile.unitImages = new HashMap<>();
        try{
            for(GroundType type : GroundType.values()) {
                System.out.println(type.getUrl());
                BufferedImage image = ImageIO.read(type.getUrl());
                Tile.groundImages.put(type, image.getScaledInstance(TILE_BASE_SIZE,TILE_BASE_SIZE, BufferedImage.SCALE_SMOOTH));
            }
            for (UnitType type : UnitType.values()) {
                BufferedImage image = ImageIO.read(type.getUrl());
                Tile.unitImages.put(type, image.getScaledInstance(TILE_BASE_SIZE,TILE_BASE_SIZE, BufferedImage.SCALE_SMOOTH));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    int getCoordX() {
        return xCoord;
    }

    int getCoordY() {
        return yCoord;
    }

    private void setMouseListener() {
        Tile tile = this;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getButton() == MouseEvent.BUTTON1) {
                    selectionController.select(tile);
                }
                if(e.getButton() == MouseEvent.BUTTON3) {
                    if(selectionController.getSelectedTile().unitType == UnitType.BUILDER) {
                        serverComm.move(new Point(selectionController.getSelectedTile().getCoordX(), selectionController.getSelectedTile().getCoordY()), new Point(tile.getCoordX(), tile.getCoordY()));
                        tile.unitType = selectionController.getSelectedTile().unitType;
                        selectionController.getSelectedTile().unitType = null;
                        System.out.println("MOVE");
                        tile.repaint();
                        selectionController.getSelectedTile().repaint();
                        selectionController.select(tile);
                    }

                }
                //TODO:implement unit/tile selections
            }
        });
    }

    UnitType getUnitType() {
        return unitType;
    }

    void setUnitType(UnitType ut) {
        unitType = ut;
        this.repaint();
    }

    GroundType getGroundType() {
        return groundType;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(groundImages.get(groundType), 0, 0, null);
        g.drawImage(unitImages.get(unitType), 0, 0, null);
    }

}

package fleetbot_wars.model;

import visual.unit.Unit;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;

public class Tile extends JPanel {

    static final int TILE_BASE_SIZE = 36;
    private static HashMap<GroundType, Image> groundImages;
    private static HashMap<UnitType, Image> unitImages;

    private final int xCoord;
    private final int yCoord;
    private int widthModifier;
    private int heightModifier;
    private Unit unit;
    private HashMap<UnitType, Image[]> imageSections = new HashMap<>();
    private GroundType groundType;
    private UnitType unitType;
    private SelectionController selectionController = SelectionController.getInstance();
    private Translation serverComm = Translation.getInstance();

    public static Image[] genImageSections(int widthInUnits, int heightInUnits, BufferedImage imageToCut) {
        int rows = widthInUnits;
        int cols = heightInUnits;
        Image toolkitImage =  imageToCut.getScaledInstance(Tile.TILE_BASE_SIZE*widthInUnits, Tile.TILE_BASE_SIZE*heightInUnits, BufferedImage.SCALE_SMOOTH);
        int width = toolkitImage.getWidth(null);
        int height = toolkitImage.getHeight(null);
        BufferedImage scaledImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = scaledImage.getGraphics();
        g.drawImage(toolkitImage, 0, 0, null);
        g.dispose();
        Image[] imageParts = new Image[rows * cols];

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                imageParts[(i * rows) + j] = scaledImage.getSubimage(
                        j * TILE_BASE_SIZE,
                        i * TILE_BASE_SIZE,
                        TILE_BASE_SIZE,
                        TILE_BASE_SIZE
                );
            }
        }

        return imageParts;
    }

    private int calculateImgIndex(){
        ArrayList<Point> unitCoords = unit.getCoordsArray();
        System.out.println(unitType.name());
        System.out.println(unitCoords);
        System.out.println(new Point(this.yCoord, this.xCoord));
        return unitCoords.indexOf(new Point(this.yCoord, this.xCoord));
        //return 0;
    }

    private boolean isLargeUnit(Unit unit){
        return unit == null ? false : ((unit.getWidth() + unit.getHeight()) > 2);
    }

    Tile(GroundType groundType, Unit unit, int x, int y) {
        this.xCoord = x;
        this.yCoord = y;
        this.unit = unit;
        this.widthModifier = unit == null ? 1 : unit.getWidth();
        this.heightModifier = unit == null ? 1 : unit.getHeight();
        this.unitType = unit == null ? null : unit.getType().getUnitType();
        this.groundType = groundType;
        if(isLargeUnit(unit) && !this.imageSections.containsKey(unitType)){
            try {
                imageSections.put(unitType, genImageSections(widthModifier, heightModifier, ImageIO.read(unitType.getUrl())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.setPreferredSize(new Dimension(TILE_BASE_SIZE, TILE_BASE_SIZE));

        setMouseListener();
    }

    static void loadImages() {
        Tile.groundImages = new HashMap<>();
        Tile.unitImages = new HashMap<>();
        try {
            for (GroundType type : GroundType.values()) {
                System.out.println(type.getUrl());
                BufferedImage image = ImageIO.read(type.getUrl());
                Tile.groundImages.put(type, image.getScaledInstance(TILE_BASE_SIZE, TILE_BASE_SIZE, BufferedImage.SCALE_SMOOTH));
            }
            for (UnitType type : UnitType.values()) {
                BufferedImage image = ImageIO.read(type.getUrl());
                Tile.unitImages.put(type, image.getScaledInstance(TILE_BASE_SIZE, TILE_BASE_SIZE, BufferedImage.SCALE_SMOOTH));
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

    /**
     * TO DO: finish this method
     */
    //private int indexRelativeToRefCoords(){}

    private void setMouseListener() {
        Tile tile = this;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    selectionController.select(tile);
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (selectionController.getSelectedTile().unitType == UnitType.BUILDER) {
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
        if(isLargeUnit(unit)){
            g.drawImage(imageSections.get(unitType)[calculateImgIndex()], 0, 0, null);
        }else{
            g.drawImage(unitImages.get(unitType), 0, 0, null);
        }
    }

}

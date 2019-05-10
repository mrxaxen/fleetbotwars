package fleetbot_wars.model;

import visual.unit.Controllable;
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
    private static HashMap<UnitType, Image[]> imageSections = new HashMap<>();

    private final int xCoord;
    private final int yCoord;
    private int widthModifier;
    private int heightModifier;
    private Unit unit;
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
        System.out.println(new Point(this.xCoord, this.yCoord));
        return unitCoords.indexOf(new Point(this.xCoord, this.yCoord));
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
                    System.out.println("TILE LISTENER CALLED FOR M1");
                    selectionController.select(tile);
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (selectionController.getSelectedTile() != null) {
                        selectionController.move(tile);
                    }
                }
            }
        });
    }

    UnitType getUnitType() {
        return unitType;
    }

    void setUnit(Unit unit) {
        this.unit = unit;
        unitType = unit != null ? unit.getType().getUnitType() : null;
    }

    GroundType getGroundType() {
        return groundType;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(groundImages.get(groundType), 0, 0, null);
        if(isLargeUnit(unit)){
            Image image = imageSections.get(unitType)[calculateImgIndex()];
            System.out.println("Unittype: "+unitType);
            g.drawImage(image, 0, 0, null);
        }else{
            g.drawImage(unitImages.get(unitType), 0, 0, null);
        }
    }

}

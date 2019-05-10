package fleetbot_wars.model;

import visual.unit.Controllable;
import visual.unit.Tree;
import visual.unit.Unit;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
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

    public static Image[] genImageSections(int widthInUnits, int heightInUnits, BufferedImage imageToCut, Color color) {
        int rows = widthInUnits;
        int cols = heightInUnits;
        Image toolkitImage = imageToCut.getScaledInstance(Tile.TILE_BASE_SIZE * widthInUnits, Tile.TILE_BASE_SIZE * heightInUnits, BufferedImage.SCALE_SMOOTH);
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
        imageParts[0] = colorCorners(imageParts[0], color);

        return imageParts;
    }

    private int calculateImgIndex() {
        ArrayList<Point> unitCoords = unit.getCoordsArray();
        int idx = unitCoords.indexOf(new Point(this.xCoord, this.yCoord));
        return idx > -1 ? idx : 0;

    }

    private boolean isLargeUnit(Unit unit) {
        return unit == null ? false : ((unit.getWidth() + unit.getHeight()) > 2);
    }

    public static Image colorCorners(Image image, Color color) {
        BufferedImage buffImage =
                new BufferedImage(
                        image.getWidth(null),
                        image.getHeight(null),
                        BufferedImage.TYPE_INT_ARGB);
        Graphics g = buffImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        //int c = 0;
        for (int i = 0; i < buffImage.getHeight(); i++) {
            int c = i;
            for (int j = 0; j < buffImage.getWidth(); j++) {
                if(c < TILE_BASE_SIZE/2) {
                    buffImage.setRGB(i, j, color.getRGB());
                }
                c++;
            }
        }
        return buffImage;
    }

    Tile(GroundType groundType, Unit unit, int x, int y) {
        this.xCoord = x;
        this.yCoord = y;
        this.unit = unit;
        this.widthModifier = unit == null ? 1 : unit.getWidth();
        this.heightModifier = unit == null ? 1 : unit.getHeight();
        this.unitType = unit == null ? null : unit.getType().getUnitType();
        this.groundType = groundType;

        if(unit instanceof Controllable){
            Color color = ((Controllable) unit).getColor();
            if (isLargeUnit(unit) && !this.imageSections.containsKey(unitType)) {
                try {
                    imageSections.put(unitType, genImageSections(widthModifier, heightModifier, ImageIO.read(unitType.getUrl()), color));
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                    // Hint: comment/remove comment with Ctrl + /
                    /*System.out.println("tile coords: " + tile.getCoordY() + " - " + tile.getCoordX());
                    System.out.println("unit obj name: " + tile.getUnit());
                    System.out.println("unit reference coords: " + tile.getUnit().getReferenceCoords());
                    System.out.println(tile.getUnit().getCoordsArray());
                    System.out.println("\n");*/

                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (selectionController.getSelectedTile() != null) {
                        if(selectionController.isAttacking()) {
                            selectionController.attack(tile);
                        } else {
                            selectionController.move(tile);
                        }

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

    public Unit getUnit() {
        return unit;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(groundImages.get(groundType), 0, 0, null);
        if (isLargeUnit(unit)) {
            g.drawImage(imageSections.get(unitType)[calculateImgIndex()], 0, 0, null);
        } else {
            if(unit instanceof Tree){
                g.drawImage(unitImages.get(unitType), 0, 0, null);
            }else if(unit != null){
                g.drawImage(colorCorners(unitImages.get(unitType), ((Controllable)unit).getColor()), 0, 0, null);
            }
        }
    }

}

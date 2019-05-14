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
    //private static HashMap<UnitType, Image[]> imageSections = new HashMap<>();
    private static HashMap<Color, HashMap<UnitType, Image[]>> iSections = new HashMap<>();

    private final int xCoord;
    private final int yCoord;
    private int widthModifier;
    private int heightModifier;
    private boolean showAttack;
    private Unit unit;
    private GroundType groundType;
    private UnitType unitType;
    private SelectionController selectionController = SelectionController.getInstance();
    private Color tileColor;

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
                if (c < TILE_BASE_SIZE / 2) {
                    buffImage.setRGB(i, j, color.getRGB());
                }
                c++;
            }
        }
        return buffImage;
    }

    public static void genSections() {
        iSections.put(Color.red, new HashMap<>());
        iSections.put(Color.green, new HashMap<>());
        iSections.put(Color.blue, new HashMap<>());
        iSections.put(Color.yellow, new HashMap<>());

        for(UnitType ut : UnitType.values()) {
            if(ut.getWidthInUnits() > 0 && ut.getHeightInUnits() > 0) {
                try {
                    iSections.get(Color.red).put(ut,genImageSections(ut.getWidthInUnits(),ut.getHeightInUnits(),ImageIO.read(ut.getUrl()),Color.red));
                    iSections.get(Color.green).put(ut,genImageSections(ut.getWidthInUnits(),ut.getHeightInUnits(),ImageIO.read(ut.getUrl()),Color.green));
                    iSections.get(Color.blue).put(ut,genImageSections(ut.getWidthInUnits(),ut.getHeightInUnits(),ImageIO.read(ut.getUrl()),Color.blue));
                    iSections.get(Color.yellow).put(ut,genImageSections(ut.getWidthInUnits(),ut.getHeightInUnits(),ImageIO.read(ut.getUrl()),Color.yellow));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    Tile(GroundType groundType, Unit unit, int x, int y) {
        this.xCoord = x;
        this.yCoord = y;
        this.unit = unit;
        this.widthModifier = unit == null ? 1 : unit.getWidth();
        this.heightModifier = unit == null ? 1 : unit.getHeight();
        this.unitType = unit == null ? null : unit.getType().getUnitType();
        this.groundType = groundType;

//        DEPRECATED? Let's hope so..
        /*if (unit instanceof Controllable) {
            Color color = ((Controllable) unit).getColor();
            HashMap<UnitType, Image[]> imgSections = iSections.get(color);
            if (imgSections == null) {
                imgSections = new HashMap<>();
                iSections.put(color, imgSections);
            }
            try {
                imgSections.put(unitType, genImageSections(widthModifier, heightModifier, ImageIO.read(unitType.getUrl()), color));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        this.setPreferredSize(new Dimension(TILE_BASE_SIZE, TILE_BASE_SIZE));

        setTileColor(unit, groundType);
        setMouseListener();
    }

    private void setTileColor(Unit unit, GroundType groundType) {
        if (unit instanceof Controllable) {
            tileColor = ((Controllable) unit).getPlayer().getColor();
        } else if (unit instanceof Tree) {
            tileColor = new Color(188, 0, 255);
        } else if (unit == null) {
            switch (groundType) {
                case WATER:
                case WATER_1:
                case WATER_2:
                    tileColor = new Color(0, 255, 237);
                    break;
                case MOUNTAIN:
                case MOUNTAIN_1:
                case MOUNTAIN_2:
                case MOUNTAIN_3:
                    tileColor = new Color(255, 190, 27);
                    break;
                case GOLD:
                case GOLD_1:
                case GOLD_2:
                    tileColor = new Color(0, 255, 38);
                    break;
                case STONE:
                case STONE_1:
                case STONE_2:
                    tileColor = new Color(130, 128, 133);
                    break;
                case DIRT:
                case DIRT_1:
                case DIRT_2:
                    tileColor = new Color(255, 255, 255);
                    break;
                default:
                    tileColor = new Color(255, 255, 255);
                    break;

            }
        }
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
            Color color = ((Controllable) unit).getColor();
            HashMap<UnitType, Image[]> imgSections = iSections.get(color);
            g.drawImage(imgSections.get(unitType)[calculateImgIndex()], 0, 0, null);
        } else {
            if (unit instanceof Tree) {
                g.drawImage(unitImages.get(unitType), 0, 0, null);
            } else if (unit != null) {
                g.drawImage(colorCorners(unitImages.get(unitType), ((Controllable) unit).getColor()), 0, 0, null);
            }
        }
        if(showAttack) {
            g.setColor(new Color(200,100,0,80));
            g.drawRect(0,0,getWidth(),getHeight());
        }
    }

    public void setShowAttack(boolean b) {
        showAttack = b;
    }

    public Color getTileColor() {
        return tileColor;
    }
}

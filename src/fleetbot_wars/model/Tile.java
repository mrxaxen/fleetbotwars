package fleetbot_wars.model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class Tile extends JPanel {

    static final int TILE_BASE_SIZE = 128;
    private static HashMap<GroundType, Image> groundImages;
    private static HashMap<UnitType, Image> unitImages;

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

    private final int x;
    private final int y;
    private GroundType groundType;
    private UnitType unitType;

    Tile(GroundType groundType, UnitType unitType, int x, int y) {
        this.x = x;
        this.y = y;
        this.unitType = unitType;
        this.groundType = groundType;
        this.setPreferredSize(new Dimension(TILE_BASE_SIZE,TILE_BASE_SIZE));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //TODO:implement unit/tile selections
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(groundImages.get(groundType), 0, 0, null);
        g.drawImage(unitImages.get(unitType), 0, 0, null);
    }

}

package fleetbot_wars.model;

import java.awt.*;
import java.net.URL;

public enum GroundType {
    DIRT(GroundType.class.getResource("/resources/ground/ground_1.png"), new Color(74, 83,1)),
    DIRT_1(GroundType.class.getResource("/resources/ground/ground_1.png"), new Color(74, 83,1)),
    DIRT_2(GroundType.class.getResource("/resources/ground/ground_2.png"), new Color(74, 83,1)),
    STONE(GroundType.class.getResource("/resources/ground/stone_1.png"), new Color(61, 61, 61)),
    STONE_1(GroundType.class.getResource("/resources/ground/stone_1.png"), new Color(61, 61, 61)),
    STONE_2(GroundType.class.getResource("/resources/ground/stone_2.png"), new Color(61, 61, 61)),
    GOLD(GroundType.class.getResource("/resources/ground/gold_1.png"), new Color(255, 242,0)),
    GOLD_1(GroundType.class.getResource("/resources/ground/gold_1.png"), new Color(255, 242,0)),
    GOLD_2(GroundType.class.getResource("/resources/ground/gold_2.png"), new Color(255, 242,0)),
    WATER(GroundType.class.getResource("/resources/ground/water_1.png"), new Color(0,0,200)),
    WATER_1(GroundType.class.getResource("/resources/ground/water_1.png"), new Color(0,0,200)),
    WATER_2(GroundType.class.getResource("/resources/ground/water_1.png"), new Color(0,0,200)),
    MOUNTAIN(GroundType.class.getResource("/resources/ground/mountain_1.png"), new Color(10,10,10)),
    MOUNTAIN_1(GroundType.class.getResource("/resources/ground/mountain_1.png"), new Color(0,0,200)),
    MOUNTAIN_2(GroundType.class.getResource("/resources/ground/mountain_2.png"), new Color(0,0,200)),
    MOUNTAIN_3(GroundType.class.getResource("/resources/ground/mountain_2.png"), new Color(0,0,200)),
//    WOOD(GroundType.class.getResource("/resources/wood.png"), new Color(58, 29, 2, 208)); NOT USED


    ;
    private URL url;
    private Color color;
    URL getUrl() {return url;}
    Color getColor() {
        return color;
    }

    GroundType(URL url, Color color) {
        this.color = color;
        this.url = url;
    }
}

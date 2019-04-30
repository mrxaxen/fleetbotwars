package fleetbot_wars.model;

import java.awt.*;
import java.net.URL;

public enum GroundType {
    DIRT(GroundType.class.getResource("/resources/ground.png"), new Color(74, 83,1)),
    STONE(GroundType.class.getResource("/resources/stone.png"), new Color(61, 61, 61)),
    GOLD(GroundType.class.getResource("/resources/gold.png"), new Color(255, 242,0)),
    WATER(GroundType.class.getResource("/resources/water.png"), new Color(0,0,200)),
    MOUNTAIN(GroundType.class.getResource("/resources/mountain.png"), new Color(10,10,10)),
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

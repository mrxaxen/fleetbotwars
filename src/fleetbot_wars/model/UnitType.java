package fleetbot_wars.model;

import java.awt.*;
import java.net.URL;

public enum UnitType {
    TREE(UnitType.class.getResource("/resources/tree.png"), new Color(0, 66,0)),
    BUILDER(UnitType.class.getResource("/resources/builder.png"), new Color(147, 131, 7)),

    TURRET(UnitType.class.getResource("/resources/turret.png"), new Color(23, 255, 0)),
    WORKERSPAWN(UnitType.class.getResource("/resources/workerspawn.png"), new Color(255,255,0)),
    MILITARYSPAWN(UnitType.class.getResource("/resources/militaryspawn.png"), new Color(120,0,180)),
    FARM(UnitType.class.getResource("/resources/farm.png"), new Color(200, 69, 132)),
    STONEMINE(UnitType.class.getResource("/resources/stonemine.png"), new Color(100,100,100)),
    GOLDMINE(UnitType.class.getResource("/resources/goldmine.png"), new Color(255, 242,0 )),
    BARRICADE(UnitType.class.getResource("/resources/barricade.png"), new Color(0,0,0)),
    ;

    private URL url;
    private Color color;
    URL getUrl() {return url;}
    Color getColor() {return color;}

    UnitType(URL url, Color color) {
        this.color = color;
        this.url = url;
    }
}

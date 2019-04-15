package fleetbot_wars.model;

import java.awt.*;
import java.net.URL;

enum UnitType {
    TREE(UnitType.class.getResource("/resources/tree.png"), new Color(0,200,0)),
    BUILDER(UnitType.class.getResource("/resources/builder.png"), new Color(147, 131, 7)),

    TURRET(UnitType.class.getResource("/resources/turret.png"), new Color(100,100,100)),
//    WORKERSPAWN(UnitType.class.getResource("/resource/workerspawn.png")),
//    MILITARYSPAWN(UnitType.class.getResource("/resource/militaryspawn.png")),
//    FARM(UnitType.class.getResource("/reousrce/farm.png")),
//    STONEMINE(UnitType.class.getResource("/resource/stonemine.png")),
//    GOLDMINE(UnitType.class.getResource("/resource/goldmine.png")),
//    BARRICADE(UnitType.class.getResource("/resource/barricade.png")),
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

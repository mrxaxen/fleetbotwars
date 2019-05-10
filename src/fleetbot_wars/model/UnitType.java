package fleetbot_wars.model;

import java.awt.*;
import java.net.URL;

public enum UnitType {
    TREE(UnitType.class.getResource("/resources/unit/tree_1.png"), new Color(0, 66,0)),
    TREE_1(UnitType.class.getResource("/resources/unit/tree_1.png"), new Color(0, 66,0)),
    TREE_2(UnitType.class.getResource("/resources/unit/tree_2.png"), new Color(0, 66,0)),
    TREE_3(UnitType.class.getResource("/resources/unit/tree_3.png"), new Color(0, 66,0)),
    TREE_4(UnitType.class.getResource("/resources/unit/tree_4.png"), new Color(0, 66,0)),
    BUILDER(UnitType.class.getResource("/resources/unit/builder.png"), new Color(147, 131, 7)),
    CAVALRY(UnitType.class.getResource("/resources/unit/cavalry.png"), new Color(147, 131, 7)),
    DESTROYER(UnitType.class.getResource("/resources/unit/destroyer.png"), new Color(147, 131, 7)),
    INFANTRY(UnitType.class.getResource("/resources/unit/infantry.png"), new Color(147, 131, 7)),
    LUMBERJACK(UnitType.class.getResource("/resources/unit/lumberjack.png"), new Color(147, 131, 7)),
    MEDIC(UnitType.class.getResource("/resources/unit/medic.png"), new Color(147, 131, 7)),
    MINER(UnitType.class.getResource("/resources/unit/miner.png"), new Color(147, 131, 7)),
    RANGER(UnitType.class.getResource("/resources/unit/ranger.png"), new Color(147, 131, 7)),

    TURRET(UnitType.class.getResource("/resources/building/turret.png"), new Color(23, 255, 0), 2, 2),
    WORKERSPAWN(UnitType.class.getResource("/resources/building/workerspawn.png"), new Color(255,255,0), 3, 2),
    MILITARYSPAWN(UnitType.class.getResource("/resources/building/militaryspawn.png"), new Color(120,0,180), 3, 2),
    FARM(UnitType.class.getResource("/resources/building/farm.png"), new Color(200, 69, 132), 3, 2),
    HARVESTCENTER(UnitType.class.getResource("/resources/building/harvestcenter.png"), new Color(200, 69, 132), 3, 2),
    STONEMINE(UnitType.class.getResource("/resources/building/stonemine.png"), new Color(100,100,100), 2, 2),
    GOLDMINE(UnitType.class.getResource("/resources/building/goldmine.png"), new Color(255, 242,0 ), 3, 2),
    BARRICADE(UnitType.class.getResource("/resources/building/barricade.png"), new Color(0,0,0), 1, 1),
    ;

    private URL url;
    private Color color;
    private int widthInUnits;
    private int heightInUnits;
    URL getUrl() {return url;}
    Color getColor() {return color;}

    UnitType(URL url, Color color) {
        this.color = color;
        this.url = url;
        this.widthInUnits = 0;
        this.heightInUnits = 0;
    }
    UnitType(URL url, Color color, int widthInUnits, int heightInUnits){
        this(url, color);
        this.widthInUnits = widthInUnits;
        this.heightInUnits = heightInUnits;

    }
}

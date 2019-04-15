package fleetbot_wars.model.enums;

import java.awt.Point;
import fleetbot_wars.model.GroundType;
import fleetbot_wars.model.UnitType;
import visual.unit.*;

public enum VisualType {

    BUILDER(UnitType.BUILDER), CAVALRY(UnitType.BUILDER), DESTROYER(UnitType.BUILDER), INFANTRY(UnitType.BUILDER), LUMBERJACK(UnitType.BUILDER), MEDIC(UnitType.BUILDER), MINER(UnitType.BUILDER), RANGER(UnitType.BUILDER),
    GOLDMINE(UnitType.GOLDMINE), HARVESTCENTER(UnitType.TURRET),FARM(UnitType.FARM), BARRICADE(UnitType.BARRICADE),MILITARYSPAWN(UnitType.MILITARYSPAWN), STONEMINE(UnitType.STONEMINE), TURRET(UnitType.TURRET), WORKERSPAWN(UnitType.WORKERSPAWN),
    BASE(GroundType.DIRT), GOLD(GroundType.GOLD), STONE(GroundType.STONE), WATER(GroundType.WATER), MOUNTAIN(GroundType.MOUNTAIN), WOOD(UnitType.BUILDER) ,TREE(UnitType.TREE);

    private GroundType groundType;
    private UnitType unitType;

    VisualType(GroundType groudType) {
        this.groundType = groudType;
    }

    VisualType(UnitType unitType) {
        this.unitType = unitType;
    }

    public GroundType getGroundType() {
        return groundType;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public static Controllable createUnit(VisualType name, Point coords, int playerNum) {
        Controllable retUnit = null;
        switch (name) {
        case BARRICADE:
            retUnit = new Barricade(coords, playerNum);
            break;
        case BUILDER:
            retUnit = new Builder(coords, playerNum);
            break;
        case CAVALRY:
            retUnit = new Cavalry(coords, playerNum);
            break;
        case DESTROYER:
            retUnit = new Destroyer(coords, playerNum);
            break;
        case FARM:
            retUnit = new Farm(coords, playerNum);
            break;
        case GOLDMINE:
            retUnit = new GoldMine(coords, playerNum);
            break;
        case HARVESTCENTER:
            retUnit = new HarvestCenter(coords, playerNum);
            break;
        case INFANTRY:
            retUnit = new Infantry(coords, playerNum);
            break;
        case LUMBERJACK:
            retUnit = new Lumberjack(coords, playerNum);
            break;
        case MEDIC:
            retUnit = new Medic(coords, playerNum);
            break;
        case MILITARYSPAWN:
            retUnit = new MilitarySpawn(coords, playerNum);
            break;
        case MINER:
            retUnit = new Miner(coords, playerNum);
            break;
        case RANGER:
            retUnit = new Ranger(coords, playerNum);
            break;
        case STONEMINE:
            retUnit = new StoneMine(coords, playerNum);
            break;
        case TURRET:
            retUnit = new Turret(coords, playerNum);
            break;
        case WORKERSPAWN:
            retUnit = new WorkerSpawn(coords, playerNum);
            break;
        default:
            break;

        }
        return retUnit;
    }

}

package fleetbot_wars.model.enums;

import java.awt.Point;
import java.util.Random;

import fleetbot_wars.model.GroundType;
import fleetbot_wars.model.UnitType;
import visual.unit.*;

public enum VisualType {

    BUILDER(UnitType.BUILDER),
    CAVALRY(UnitType.CAVALRY),
    DESTROYER(UnitType.DESTROYER),
    INFANTRY(UnitType.INFANTRY),
    LUMBERJACK(UnitType.LUMBERJACK),
    MEDIC(UnitType.MEDIC),
    MINER(UnitType.MINER),
    RANGER(UnitType.RANGER),

    GOLDMINE(UnitType.GOLDMINE),
    HARVESTCENTER(UnitType.HARVESTCENTER),
    FARM(UnitType.FARM),
    BARRICADE(UnitType.BARRICADE),
    MILITARYSPAWN(UnitType.MILITARYSPAWN),
    STONEMINE(UnitType.STONEMINE),
    TURRET(UnitType.TURRET),
    WORKERSPAWN(UnitType.WORKERSPAWN),

    BASE(GroundType.DIRT),
    BASE_1(GroundType.DIRT_1),
    BASE_2(GroundType.DIRT_2),

    GOLD(GroundType.GOLD),
    GOLD_1(GroundType.GOLD_1),
    GOLD_2(GroundType.GOLD_2),

    STONE(GroundType.STONE),
    STONE_1(GroundType.STONE_1),
    STONE_2(GroundType.STONE_2),

    WATER(GroundType.WATER),
    WATER_1(GroundType.WATER_1),
    WATER_2(GroundType.WATER_2),

    MOUNTAIN(GroundType.MOUNTAIN),
    MOUNTAIN_1(GroundType.MOUNTAIN_1),
    MOUNTAIN_2(GroundType.MOUNTAIN_2),
    MOUNTAIN_3(GroundType.MOUNTAIN_3),

    WOOD(UnitType.BUILDER),

    TREE(UnitType.TREE),

    TREE_1(UnitType.TREE_1),
    TREE_2(UnitType.TREE_2),
    TREE_3(UnitType.TREE_3),
    TREE_4(UnitType.TREE_4);

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

    public static VisualType getRandomType(String prefix, int number){
        int max = number;
        int min = 1;
        Random r = new Random();
        int rnd = r.nextInt((max - min) + 1) + min;

        return VisualType.valueOf(prefix + "_" + rnd);
    }

}

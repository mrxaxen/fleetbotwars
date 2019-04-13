package fleetbot_wars.model.enums;

import java.awt.Point;
import visual.unit.*;

public enum VisualType {

    barricade, builder, cavalry, destroyer, farm, goldmine, harvestcenter, infantry, lumberjack, medic, militaryspawn,
    miner, ranger, stonemine, tree, turret, workerspawn, base, gold, stone, water, mountain, wood;

    @Override
    public String toString() {
        return this.name();
    }

    public static Controllable createUnit(VisualType name, Point coords, int playerNum) {
        Controllable retUnit = null;
        switch (name) {
        case barricade:
            retUnit = new Barricade(coords, playerNum);
            break;
        case builder:
            retUnit = new Builder(coords, playerNum);
            break;
        case cavalry:
            retUnit = new Cavalry(coords, playerNum);
            break;
        case destroyer:
            retUnit = new Destroyer(coords, playerNum);
            break;
        case farm:
            retUnit = new Farm(coords, playerNum);
            break;
        case goldmine:
            retUnit = new GoldMine(coords, playerNum);
            break;
        case harvestcenter:
            retUnit = new HarvestCenter(coords, playerNum);
            break;
        case infantry:
            retUnit = new Infantry(coords, playerNum);
            break;
        case lumberjack:
            retUnit = new Lumberjack(coords, playerNum);
            break;
        case medic:
            retUnit = new Medic(coords, playerNum);
            break;
        case militaryspawn:
            retUnit = new MilitarySpawn(coords, playerNum);
            break;
        case miner:
            retUnit = new Miner(coords, playerNum);
            break;
        case ranger:
            retUnit = new Ranger(coords, playerNum);
            break;
        case stonemine:
            retUnit = new StoneMine(coords, playerNum);
            break;
        case turret:
            retUnit = new Turret(coords, playerNum);
            break;
        case workerspawn:
            retUnit = new WorkerSpawn(coords, playerNum);
            break;
        default:
            break;

        }
        return retUnit;
    }

}

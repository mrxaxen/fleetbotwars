package fleetbot_wars.model.enums;

public enum VisualType {

    barricade, builder, cavalry, destroyer, farm, goldmine, harvestcenter, infantry, lumberjack, medic, militaryspawn, mine, miner, ranger, stonemine, tree, turret, workerspawn, base, gold, stone, water, mountain, wood;

    @Override
    public String toString(){
        return this.name();
    }

}



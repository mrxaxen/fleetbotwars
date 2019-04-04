/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import java.util.ArrayList;
import visual.unit.Controllable;

/**
 *
 * @author WB
 */
public class Player
{
    // 0 - food, 1 - wood, 2 - stone, 3 - gold, 4 - upgrade
    private int resources[] = new int[5];
    // 0 - Farm, 1 - HarvestCenter, 2 - StoneMine, 3 - GoldMine, 4 - WorkerSpawn, 5 - MilitarySpawm, 6 - Turret, 7 - Barricade,
    // 8 - Lumberjack, 9 - Miner, 10 - Builder,
    // 11 - Infantry, 12 -  Cavalry, 13 - Ranger, 14 - Destroyer, 15 - Medic
    private ArrayList controlled[] = new ArrayList[16];
}

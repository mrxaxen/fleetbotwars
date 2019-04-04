/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import java.util.ArrayList;
import java.util.HashMap;

import visual.unit.Controllable;

/**
 *
 * @author WB
 */
public class Player{
    
    // 0 - food, 1 - wood, 2 - stone, 3 - gold, 4 - upgrade
    
    // 0 - Farm, 1 - HarvestCenter, 2 - StoneMine, 3 - GoldMine, 4 - WorkerSpawn, 5 - MilitarySpawm, 6 - Turret, 7 - Barricade,
    // 8 - Lumberjack, 9 - Miner, 10 - Builder,
    // 11 - Infantry, 12 -  Cavalry, 13 - Ranger, 14 - Destroyer, 15 - Medic

    private HashMap resources; 
    private ArrayList<Controllable> playerUnits;
    private String playerName;  
    private int team;

    public Player(String name, int team, int[] initResources){
        this.playerName = name;
        this.team = team;
        this.resources = new HashMap<>();
        resources.put("food", initResources[0]);
        resources.put("wood", initResources[1]);
        resources.put("stone", initResources[2]);
        resources.put("gold", initResources[3]);
        resources.put("upgrade", initResources[4]);
    }

    public void decreaseResource(String name, int amount){
        resources.put(name, (int)resources.get(name) - amount);
    }

    public void increaseResource(String name, int amount){
        resources.put(name, (int)resources.get(name) + amount);
    }

    public void addControllable(Controllable unit){

    }

}

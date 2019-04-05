package fleetbot_wars.model;

import visual.unit.Controllable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author trmx
 */
public class Player {

    // 0 - food, 1 - wood, 2 - stone, 3 - gold, 4 - upgrade

    // 0 - Farm, 1 - HarvestCenter, 2 - StoneMine, 3 - GoldMine, 4 - WorkerSpawn, 5
    // - MilitarySpawm, 6 - Turret, 7 - Barricade,
    // 8 - Lumberjack, 9 - Miner, 10 - Builder,
    // 11 - Infantry, 12 - Cavalry, 13 - Ranger, 14 - Destroyer, 15 - Medic

    private Map resources;
    private ArrayList<Controllable> playerUnits;
    private String playerName;
    private int playerNumber;

    /**
     * Create a new Player Object with initial values for the different resources
     * 
     * 
     * @param playerName
     * @param playerNumber
     * @param initResources
     */
    public Player(String playerName, int playerNumber, int[] initResources) {
        this.playerName = playerName;
        this.playerNumber = playerNumber;
        this.resources = new HashMap<String, Integer>();
        resources.put("food", initResources[0]);
        resources.put("wood", initResources[1]);
        resources.put("stone", initResources[2]);
        resources.put("gold", initResources[3]);
        resources.put("upgrade", initResources[4]);
    }

    public void decreaseResource(String resourceName, int amount) {
        int currResource = resources.get(resourceName);
        resources.put(resourceName, currResource - amount);
    }

    public void increaseResource(String resourceName, int amount) {
        int currResource = resources.get(resourceName);
        resources.put(resourceName, currResource + amount);
    }

    public void addControllable(Controllable unit) {

    }

}

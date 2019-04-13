
package fleetbot_wars.model;

import fleetbot_wars.model.enums.VisualType;
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

    private HashMap<String, Integer> resources;
    private ArrayList<Controllable> playerUnits;
    private ArrayList<Controllable> deadUnits;
    private String playerName;
    private int playerNumber;
    public final static VisualType[] initialUnits = {VisualType.barricade, VisualType.builder, VisualType.cavalry, VisualType.destroyer, VisualType.farm, VisualType.goldmine, VisualType.harvestcenter, VisualType.infantry, VisualType.lumberjack, VisualType.medic, VisualType.militaryspawn, VisualType.miner, VisualType.ranger, VisualType.stonemine, VisualType.turret, VisualType.workerspawn};

    /**
     * Player Object containing an individual player's data such as Units,
     * resources, player name and related methods to manipulate these data.
     * 
     * @param initResources It is intended to set the initial resources for given
     *                      Player. The key list of this Map<String, Integer> should
     *                      correspond with the following list of String: food,
     *                      wood, stone, gold, upgrade.
     * @param playerName    The name of the Player.
     * @param playerNumber  The number of the Player.
     * 
     */
    public Player(String playerName, int playerNumber){//, HashMap<String, Integer> initResources) {
        this.playerName = playerName;
        this.playerNumber = playerNumber;
        //this.resources = initResources;
        this.resources = new HashMap<String, Integer>();
        resources.put("food", 9999);
        resources.put("wood", 9999);
        resources.put("gold", 9999);
        resources.put("stone", 9999);
        resources.put("upgrade", 9999);
    }

    /**
     * Convenient way to DECREASE a given resource.
     * 
     * @param resourceName Name of the resource intended to be DECREASED from the
     *                     following list: food, wood, stone, gold, upgrade.
     * @param amount       The amount by the given resource needs to be DECREASED.
     */
    public void decreaseResource(String resourceName, int amount) {
        int currResource = (int)resources.get(resourceName);
        resources.put(resourceName, currResource - amount);
    }

    /**
     * Convenient way to INCREASE a given resource.
     * 
     * @param resourceName Name of the resource intended to be INCREASED from the
     *                     following list: food, wood, stone, gold, upgrade.
     * @param amount       The amount by the given resource needs to be INCREASED.
     */
    public void increaseResource(String resourceName, int amount) {
        int currResource = (int)resources.get(resourceName);
        resources.put(resourceName, currResource + amount);
    }

    /**
     * Adding one unit to the Player's unit Collection.
     * 
     * @param unit The Unit Object which needs to be added.
     */
    public void addControllable(Controllable unit) {
        playerUnits.add(unit);
    }
    
    /**
     * Removing one unit to the Player's unit Collection.
     * 
     * @param unit The Unit Object which needs to be added.
     */
    public void remControllable(Controllable unit) {
        playerUnits.remove(unit);
    }
    
    /**
     * Adding one unit to the Player's dead unit Collection.
     * 
     * @param unit The Unit Object which needs to be added.
     */
    public void addDeadControllable(Controllable unit) {
        deadUnits.add(unit);
    }
    
    public void remDead() {
        playerUnits.removeAll(deadUnits);
    }

    ///// getters, setters
    
    /**
     * @return the playerName
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * @return the playerUnits
     */
    public ArrayList<Controllable> getPlayerUnits() {
        return playerUnits;
    }

    /**
     * Get available amount of given resource.
     * 
     * @param name The name of the requested resource.
     * @return The amount of the requested resource.
     */
    public int getResourceByName(String name) {
        return (int)resources.get(name);
    }

    /**
     * 
     * @return All the available resources as raw data.
     */
    public HashMap<String, Integer> getResourceMap() {
        return resources;
    }

    /**
     * @return the playerNumber
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

}

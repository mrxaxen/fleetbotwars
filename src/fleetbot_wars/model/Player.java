
package fleetbot_wars.model;

import fleetbot_wars.model.enums.*;
import visual.unit.Controllable;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import fleetbot_wars.model.enums.ResourceType;

/**
 *
 * @author trmx
 */
public class Player {

    // 0 - food, 1 - WOOD, 2 - STONE, 3 - GOLD, 4 - upgrade

    // 0 - Farm, 1 - HarvestCenter, 2 - StoneMine, 3 - GoldMine, 4 - WorkerSpawn, 5
    // - MilitarySpawm, 6 - Turret, 7 - Barricade,
    // 8 - Lumberjack, 9 - Miner, 10 - Builder,
    // 11 - Infantry, 12 - Cavalry, 13 - Ranger, 14 - Destroyer, 15 - Medic

    private HashMap<ResourceType, Integer> resources;
    private ArrayList<Controllable> playerUnits = new ArrayList<>();
    private ArrayList<Controllable> deadUnits = new ArrayList<>();
    private ArrayList<Controllable> newUnits = new ArrayList<>();
    private String playerName;
    private int playerNumber;
    private Color color;
    public final static VisualType[] initialUnits = {VisualType.MINER, VisualType.BUILDER, VisualType.LUMBERJACK, VisualType.DESTROYER};/*
            VisualType.FARM, VisualType.GOLDMINE, VisualType.HARVESTCENTER, VisualType.INFANTRY,VisualType.CAVALRY, VisualType.MEDIC,
            VisualType.MILITARYSPAWN, VisualType.BARRICADE, VisualType.RANGER, VisualType.STONEMINE, VisualType.TURRET, VisualType.WORKERSPAWN,
            VisualType.BARRICADE, VisualType.BUILDER, VisualType.CAVALRY, VisualType.DESTROYER, VisualType.FARM, VisualType.GOLDMINE,
            VisualType.HARVESTCENTER, VisualType.INFANTRY, VisualType.LUMBERJACK, VisualType.MEDIC, VisualType.MILITARYSPAWN, VisualType.MINER,
            VisualType.RANGER, VisualType.STONEMINE, VisualType.TURRET, VisualType.WORKERSPAWN, VisualType.BARRICADE, VisualType.BUILDER, VisualType.CAVALRY,
            VisualType.DESTROYER, VisualType.FARM, VisualType.GOLDMINE, VisualType.HARVESTCENTER, VisualType.INFANTRY, VisualType.LUMBERJACK, VisualType.MEDIC,
            VisualType.MILITARYSPAWN, VisualType.MINER, VisualType.RANGER, VisualType.STONEMINE, VisualType.TURRET, VisualType.WORKERSPAWN, VisualType.BARRICADE,
            VisualType.BUILDER, VisualType.CAVALRY, VisualType.DESTROYER, VisualType.FARM, VisualType.GOLDMINE, VisualType.HARVESTCENTER, VisualType.INFANTRY,
            VisualType.LUMBERJACK, VisualType.MEDIC, VisualType.MILITARYSPAWN, VisualType.MINER, VisualType.RANGER, VisualType.STONEMINE, VisualType.TURRET,
            VisualType.WORKERSPAWN, VisualType.BARRICADE, VisualType.BUILDER, VisualType.CAVALRY, VisualType.DESTROYER, VisualType.FARM, VisualType.GOLDMINE,
            VisualType.HARVESTCENTER, VisualType.INFANTRY, VisualType.LUMBERJACK, VisualType.MEDIC, VisualType.MILITARYSPAWN, VisualType.MINER, VisualType.RANGER,
            VisualType.STONEMINE, VisualType.TURRET, VisualType.WORKERSPAWN, VisualType.BARRICADE, VisualType.BUILDER, VisualType.CAVALRY, VisualType.DESTROYER,
            VisualType.FARM, VisualType.GOLDMINE, VisualType.HARVESTCENTER, VisualType.INFANTRY, VisualType.LUMBERJACK, VisualType.MEDIC, VisualType.MILITARYSPAWN,
            VisualType.MINER, VisualType.RANGER, VisualType.STONEMINE, VisualType.TURRET, VisualType.WORKERSPAWN};*/

    /**
     * Player Object containing an individual player's data such as Units,
     * resources, player name and related methods to manipulate these data.
     *
     * //@param initResources It is intended to set the initial resources for given
     *                      Player. The key list of this HashMap should
     *                      correspond with the following list of String: food,
     *                      WOOD, STONE, GOLD, upgrade.
     * @param playerName    The name of the Player.
     * @param playerNumber  The number of the Player.
     *
     */
    public Player(String playerName, int playerNumber, Color color){
        this.playerName = playerName;
        this.playerNumber = playerNumber;
        this.color = color;
        //this.resources = initResources;
        this.resources = new HashMap<ResourceType, Integer>();
        resources.put(ResourceType.food, 100);
        resources.put(ResourceType.wood, 100);
        resources.put(ResourceType.gold, 100);
        resources.put(ResourceType.stone, 100);
        resources.put(ResourceType.upgrade, 100);
    }

    public Player(String playerName, int playerNumber){
        this(playerName, playerNumber, Color.black);
    }

    // used for testing only - Bori
    public void nullifyResources() {
        resources.put(ResourceType.food, 0);
        resources.put(ResourceType.wood, 0);
        resources.put(ResourceType.gold, 0);
        resources.put(ResourceType.stone, 0);
        resources.put(ResourceType.upgrade, 0);
    }

    /**
     * Convenient way to DECREASE a given resource.
     *
     * @param resourceName Name of the resource intended to be DECREASED from the
     *                     following list: food, WOOD, STONE, GOLD, upgrade.
     * @param amount       The amount by the given resource needs to be DECREASED.
     */
    public void decreaseResource(ResourceType resourceName, int amount) {
        int currResource = (int)resources.get(resourceName);
        resources.put(resourceName, currResource - amount);
    }

    /**
     * Convenient way to INCREASE a given resource.
     *
     * @param resourceName Name of the resource intended to be INCREASED from the
     *                     following list: food, WOOD, STONE, GOLD, upgrade.
     * @param amount       The amount by the given resource needs to be INCREASED.
     */
    public void increaseResource(ResourceType resourceName, int amount) {
        int currResource = (int)resources.get(resourceName);
        resources.put(resourceName, currResource + amount);
    }

    /**
     * Adding one unit to the Player's unit Collection.
     *
     * @param unit The Unit Object which needs to be added.
     */
    public void addControllable(Controllable unit) {
        if (unit != null) {
            playerUnits.add(unit);
        }
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
        deadUnits.clear();
    }

    /**
     * Adding one unit to the Player's new unit Collection.
     *
     * @param unit The Unit Object which needs to be added.
     */
    public void addNewControllable(Controllable unit) {
        newUnits.add(unit);
    }

    public void addNew() {
        playerUnits.addAll(newUnits);
        newUnits.clear();
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
    public int getResourceByName(ResourceType name){
        return (int)resources.get(name);
    }

    /**
     *
     * @return All the available resources as raw data.
     */
    public HashMap<ResourceType, Integer> getResourceMap() {
        return resources;
    }

    /**
     * @return the playerNumber
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    public Color getColor() {
        return color;
    }
}

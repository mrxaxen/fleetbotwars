package fleetbot_wars.model;

import exceptions.OutOfMapBoundsException;
import exceptions.PlayersExceedStartingZoneCountException;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import visual.ground.*;
import visual.unit.Controllable;
import visual.unit.Tree;
import fleetbot_wars.model.enums.VisualType;
import visual.unit.Unit;

/**
 *
 * @author WB
 */
public class Map {

    private Ground[][] ground;

    private String rawMapFileLocation;
    private ArrayList<Point> startingZoneCoords;
    private ArrayList<ArrayList<Point>> startingZoneCoordsArr;
    private final int startingZoneDimension = 20;
    private Dimension mapDimension;
    private int groundWidth;
    private int groundHeight;

    /**
     * Generates a Map based on the given raw file.
     * You can make a map by using a simple drawer application like MS Paint, GIMP etc.
     * A parser will generate a map based on the given color-codes:
     * #ff0000 - Starting Zone 1
     * #00ffff - Starting Zone 2
     * #000080 - Starting Zone 3
     * #ff00ff - Starting Zone 4
     * #008000 - Wood
     * #800000 - Mountain
     * #0000ff - Water
     * #999999 - Stone
     * #ffff00 - Gold
     * DEFAULT - Ground (any other color will translate to Ground)
     * 
     * 
     */
    public Map() {
        this.startingZoneCoords = new ArrayList<Point>();
        this.rawMapFileLocation = "maps/100x100";
        File mapFile = new File(rawMapFileLocation);
        this.ground = parseMapFile(mapFile);
        /*
         * for(int i = 0;i<ground.length;i++){ for(int j = 0;j<ground[i].length;j++){
         * System.out.println(ground[i][j].getOwnerReference());
         * System.out.println(ground[i][j].isOccupied()); VisualType v =
         * ground[i][j].getType(); System.out.println(v.name() + ": " +
         * ground[i][j].getReferenceCoords()); System.out.println(new Point(i, j)); } }
         */

        //System.out.println(this.startingZoneCoords);
        fillStartingZoneCoords(startingZoneCoords);
    }

    // used only for easier testing - Bori
    public Map(Ground[][] ground) {
        this.ground = ground;
        this.groundHeight = ground[0].length;
        this.groundWidth = ground.length;
    }

    /**
     * returns the Ground object at given location(x, y specified by Point)
     *
     * @param location
     * @return
     */
    public Ground groundAt(Point location){
        if(location.x < 0 || location.y < 0 || location.x > groundWidth || location.y > groundHeight){
            throw new OutOfMapBoundsException(location);
        }
        return ground[location.x][location.y];
    }

    private Ground[][] parseMapFile(File mapFile) {
        String fName = mapFile.getName();
        this.mapDimension = new Dimension(Integer.parseInt((fName.split("x"))[0]),
                Integer.parseInt((fName.split("x"))[1]));
        groundWidth = this.mapDimension.width;
        groundHeight = this.mapDimension.height;
        Ground[][] ground = new Ground[groundWidth][groundHeight];

        try (Scanner mapScanner = new Scanner(mapFile)) {
            mapScanner.nextLine();
            mapScanner.nextLine();
            mapScanner.nextLine();
            mapScanner.nextLine();
            int currMapInt;

            for (int i = 0; i < groundWidth; i++) {
                for (int j = 0; j < groundHeight; j++) {
                    if (mapScanner.hasNextLine()) {
                        currMapInt = Integer.parseInt(mapScanner.nextLine());
                        ground[i][j] = processMapInput(currMapInt, i, j);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
            System.out.println("error");
        }
        return ground;
    }

    private Ground processMapInput(int inputInt, int i, int j) {
        Ground retGround = null;
        Point currLocation = new Point(i, j);
        switch (inputInt) {
            case 18:
                retGround = new Water(currLocation);
                break;
            case 237:
                retGround = new Gold(currLocation, 5);
                break;
            case 27:
                retGround = new Mountain(currLocation);
                break;
            case 153:
                retGround = new Stone(currLocation, 5);
                break;
            case 173:
                retGround = new Base(currLocation);
                break;
            case 92:
                retGround = new Base(currLocation, new Tree(currLocation));
                break;
            case 54:
                startingZoneCoords.add(currLocation);
                retGround = new Base(currLocation);
                break;
            case 201:
                startingZoneCoords.add(currLocation);
                retGround = new Base(currLocation);
                break;
            case 73:
                startingZoneCoords.add(currLocation);
                retGround = new Base(currLocation);
                break;
            case 9:
                startingZoneCoords.add(currLocation);
                retGround = new Base(currLocation);
                break;
            default:
                retGround = new Base(currLocation);
                break;
        }

        return retGround;
    }

    /**
     * @return the startingZoneCoords
     */
    public ArrayList<Point> getStartingZoneCoords() {
        return startingZoneCoords;
    }

    private void fillStartingZoneCoords(ArrayList<Point> startingZones) {
        startingZoneCoordsArr = new ArrayList<ArrayList<Point>>();
        Point topLeft;
        int c = 0;
        for (Point p : startingZones) {
            topLeft = calcPlayerStartingZone(c);
            startingZoneCoordsArr.add(new ArrayList<Point>());
            int topLeftX = topLeft.x;
            int topLeftY = topLeft.y;
            while (topLeftX < (topLeft.x + startingZoneDimension) && topLeftX < mapDimension.width) {
                while (topLeftY < (topLeft.y + startingZoneDimension) && topLeftY < mapDimension.height) {
                    (startingZoneCoordsArr.get(c)).add(new Point(topLeftX, topLeftY));
                    topLeftY++;
                }
                topLeftX++;
            }
            c++;
        }
    }

    private Point calcPlayerStartingZone(int zoneId) {
        int topLeftX = startingZoneCoords.get(zoneId).x - (int) Math.floor(startingZoneDimension / 2);
        int topLeftY = startingZoneCoords.get(zoneId).y - (int) Math.floor(startingZoneDimension / 2);
        topLeftX = topLeftX < 0 ? 0 : topLeftX;
        topLeftY = topLeftY < 0 ? 0 : topLeftY;
        return new Point(topLeftX, topLeftY);
    }

    private void clearStartingZone(int playerNum) {
        startingZoneCoordsArr.get(playerNum).forEach(coord -> {
            ground[coord.x][coord.y] = new Base(coord);
        });
    }

    private void placeUnitOnMap(Point referenceCoord, Controllable unit) {
        int currX = referenceCoord.x;
        int currY = referenceCoord.y;

        for (int i = currX; i < referenceCoord.x + unit.getWidth(); i++) {
            for (int j = currY; j < referenceCoord.y + unit.getWidth(); j++) {
                ground[i][j].setOwnerReference(unit);
            }
        }
    }

    private boolean isSectionUnOccupied(Point referenceCoords, int width, int height) {
        int currX = referenceCoords.x;
        int currY = referenceCoords.y;
        Ground currentGround = ground[currX][currY];
        boolean isEmpty = !(currentGround.isOccupied());
        while ((currX < referenceCoords.x + width) && isEmpty && currX < this.mapDimension.height) {
            while ((currY < referenceCoords.y + height) && isEmpty  && currY < this.mapDimension.width) {
                currentGround = ground[currX][currY];
                isEmpty = !(currentGround.isOccupied());
                currY++;
            }
            currX++;
        }
        return isEmpty;
    }

    private void placeUnitsOnMap(VisualType[] units, Player player, ArrayList<Point> startingZone) {
        Controllable currUnit;
        int playerNum = player.getPlayerNumber();
        int count = 0;
        Point currentCoord;
        for (VisualType unit : units) {
            //System.out.println(startingZone.size());
            //System.out.println(count);
            currentCoord = startingZone.get(count);
            currUnit = VisualType.createUnit(unit, currentCoord, playerNum);
            player.addControllable(currUnit);
            //System.out.println(currUnit.getType().name());
            while (count < (startingZone.size()-1)
                    && !(isSectionUnOccupied(currentCoord, currUnit.getWidth(), currUnit.getHeight()))) {
                count++;
                currentCoord = startingZone.get(count);

            }
            if (isSectionUnOccupied(currentCoord, currUnit.getWidth(), currUnit.getHeight()) && count < (startingZone.size()-1)) {
                placeUnitOnMap(currentCoord, currUnit);
                count++;
            }
        }
    }

    /*
     * private ArrayList<Controllable> initUnits(){ ArrayList<Controllable>
     * initUnits = new ArrayList<Controllable>(); }
     */

    /**
     * Places players units on the map. Based on their playerNumber, 
     * their units and buildings will be placed in one of the starting zones 
     * matching up with their numbers.
     *  
     * @param players The players that need to be placed on the map.
     */

    public void placePlayersOnMap(Player[] players) {
        int startingZoneCount = startingZoneCoords.size();
        int playerCount = players.length;
        if(playerCount > startingZoneCount){
            throw new PlayersExceedStartingZoneCountException(playerCount, startingZoneCount);
        }
        for (Player p : players) {
            clearStartingZone(p.getPlayerNumber());
            ArrayList<Point> currStartingZone = startingZoneCoordsArr.get(p.getPlayerNumber());
            placeUnitsOnMap(p.initialUnits, p, currStartingZone);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ground.length; i++) {
            for (int j = 0; j < ground[i].length; j++) {
                Ground currGround = ground[i][j];
                Unit unitOnGround = currGround.getOwnerReference();
                String unitTypeName = "";
                String unitPosition = "";
                if(unitOnGround != null){
                    unitTypeName = unitOnGround.getType().name();
                    unitPosition = (unitOnGround.getReferenceCoords()).toString();
                }else{
                    unitTypeName = "No unit here";
                    unitPosition = "No unitCoords here";
                }
                String posInMapArr = (new Point(i, j)).toString();
                String groundType = currGround.getType().toString();
                
                sb.append("[unitTypeName: ").append(unitTypeName).append("]");
                sb.append("[unitPosition: ").append(unitPosition).append("]");
                sb.append("[posInMapArr: ").append(posInMapArr).append("]");
                sb.append("[groundType: ").append(groundType).append("]");
                
                sb.append("\n");
                
            }
        }

        return sb.toString();
    }

    public Ground[][] getGround() {
        return this.ground;
    }

    //UNUSED
    /*
    public void remTree(Point refCoords) {
        ground[refCoords.x][refCoords.y] = new Base(refCoords);
    }*/
    
    /**
     * used to help building
     * @param c
     * @param minableType
     * @return true if at least 1 surrounding Ground is STONE (check 8 grid points)
     */
    //REVISIT
    public boolean adjMineralCheck(Point c, Enum minableType) {
        int x = c.x;
        int y = c.y;
        //also checks ground at c (redundant cuz cannot be occupied)
        try {
        for (int i = x - 1; i < x + 2; ++i) {
            for (int j = y - 1; j < y + 2; ++j) {
                if (ground[i][j].getType().equals(minableType)) {
                    return true;
                }
            }
        }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Checked area extends off the map. (mineral check)");
        }
        return false;
    }
    
    /**
     *
     * @return Dimensions of the map.
     */
    public Dimension getMapDimension() {
        return mapDimension;
    }

}

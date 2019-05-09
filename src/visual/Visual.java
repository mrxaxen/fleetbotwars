/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual;

import fleetbot_wars.model.enums.VisualType;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author asjf86
 */
public class Visual {

    protected Point referenceCoords;
    //private ArrayList<Point> coordsArray;
    protected VisualType type;
    private Image model; // [!]
    protected int width, height;
    //private Rectangle rectangle;
    private int defaultDimX = 1;
    private int defaultDimY = 1;
    //private Point centerCoords;

    /**
     * create Visual Object with default dimensions specified by defaultDimX,
     * defaultDimY
     * 
     * @param referenceCoords Top left coordinates of the current Visual element.
     * @param type            Unit name (eg. Cavalry, Destroyer, etc.)
     * @param model           Serves as the visually represented model of it's
     *                        owner.
     */

    public Visual(Point referenceCoords, VisualType type, Image model) {
        System.out.println(type.name());
        this.referenceCoords = referenceCoords;
        switch(type){
            case TREE:
                this.type = VisualType.getRandomType("TREE", 4);
                break;
            case STONE:
                this.type = VisualType.getRandomType("STONE", 2);
                break;
            case GOLD:
                this.type = VisualType.getRandomType("GOLD", 2);
                break;
            case  BASE:
                this.type = VisualType.getRandomType("BASE", 2);
                break;
            case WATER:
                this.type = VisualType.getRandomType("WATER", 2);
                break;
            case MOUNTAIN:
                this.type = VisualType.getRandomType("MOUNTAIN", 3);
                break;
                default:
                    this.type = type;
        }

        this.model = model;
        this.width = defaultDimX;
        this.height = defaultDimY;

        /*
        this.coordsArray = new ArrayList<Point>();
        coordsArray = fillOccupiedCoords(referenceCoords, defaultDimX, defaultDimY);
        */
    }

    /**
     * @param referenceCoords Top left coordinates of the current Visual element. This
     *                        important if it has any dimension greater then 1.
     * @param type            Unit name (eg. Cavalry, Destroyer, etc.)
     * @param model           Serves as the visually represented model of it's
     *                        owner.
     * @param width           Width of the unit.
     * @param height          Height of the unit.
     */

    public Visual(Point referenceCoords, VisualType type, Image model, int width, int height) {
        this(referenceCoords, type, model);
        this.width = width;
        this.height = height;

        /*
        this.coordsArray = new ArrayList<Point>();
        coordsArray = fillOccupiedCoords(referenceCoords, width, height);
        */
    }

    //UNUSED
    /*
    private ArrayList<Point> fillOccupiedCoords(Point referenceCoords, int width, int height) {
        ArrayList<Point> retArr = new ArrayList<Point>();
        for (int i = referenceCoords.x; i < referenceCoords.x + width; i++) {
            for (int j = referenceCoords.y; j < referenceCoords.y + width; j++) {
                retArr.add(new Point(i, j));
            }
        }
        return retArr;
    }
    */

    /**
     * graphic display of Visual
     */
    public void draw() {
        //PLACEHOLDER
    }

    /**
     * Checks if the given coords are colliding/intersecting the current Visual
     * element.
     * 
     * @param coords The coords where intersection needs to be checked.
     * @return True if intersect, false else.
     */
    /*public Boolean containsPoint(Point coords) {
        return this.rectangle.contains(coords);
    }*/

    /**
     * Returns the distance between the given coords and the center of the Visual
     * Object
     * 
     * @param coordsFrom The coords from where it needs to be checked.
     * @return The distance in int type.
     */
    /*public Integer distanceFrom(Point coordsFrom) {
        return (int) (Point2D.distance(coordsFrom.x, coordsFrom.y, centerCoords.x, centerCoords.y));
    }*/

    /**
     * Calculates the difference between the given coordinates and the current
     * Visual's coordinates as signed value. To achieve this the calculation expect to receive the
     * center coordinates as parameter and uses the current Visual objects center
     * coordinates as well.
     * 
     * @param coordsFrom Center coordinates of the corresponding Visual Object.
     * @return The signed difference between the two center coordinates as Point Object.
     */
   /* public Point getDirectionFrom(Point coordsFrom) {
        int x = coordsFrom.x - centerCoords.x;
        int y = coordsFrom.y - centerCoords.y;
        return new Point(x, y);
    }*/
    
    /**
     * @return the type
     */
    public VisualType getType() {
        return type;
    }

    /**
     * @return the model
     */
    public Image getModel() {
        return model;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the referenceCoords
     */
    public Point getReferenceCoords() {
        return referenceCoords;
    }

    /**
     * @return the coordsArray
     */
    public ArrayList<Point> getCoordsArray() {
        ArrayList<Point> cA = new ArrayList<>();
        for (int i = referenceCoords.x; i < referenceCoords.x + height; i++) {
            for (int j = referenceCoords.y; j < referenceCoords.y + width; j++) {
                cA.add(new Point(i, j));
            }
        }
        return cA;
    }
    
    public void setReferenceCoords(Point referenceCoords) {
        this.referenceCoords = referenceCoords;
    }

}
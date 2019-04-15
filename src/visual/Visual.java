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

/**
 *
 * @author asjf86
 */
public class Visual {

    protected Point referenceCoords;
    private ArrayList<Point> coordsArray;
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
     * @param referenceCoords Top left coords of the current Visual element.
     * @param type            Unit name (eg. Cavalry, Destroyer, etc.)
     * @param model           Serves as the visually represented model of it's
     *                        owner.
     */

    public Visual(Point referenceCoords, VisualType type, Image model) {
        this.referenceCoords = referenceCoords;
        this.type = type;
        this.model = model;
        this.width = defaultDimX;
        this.height = defaultDimY;

        this.coordsArray = new ArrayList<Point>();
        coordsArray = fillOccupiedCoords(referenceCoords, defaultDimX, defaultDimY);
    }

    /**
     * @param referenceCoords Top left coords of the current Visual element. This
     *                        important if it has any dimension greater then 1.
     * @param type            Unit name (eg. Cavalry, Destroyer, etc.)
     * @param model           Serves as the visually represented model of it's
     *                        owner.
     * @param width           Width of the unit.
     * @param height          Height of the unit.
     */

    public Visual(Point referenceCoords, VisualType type, Image model, int width, int height) {
        this.referenceCoords = referenceCoords;
        this.type = type;
        this.model = model;
        this.width = width;
        this.height = height;

        this.coordsArray = new ArrayList<Point>();
        coordsArray = fillOccupiedCoords(referenceCoords, width, height);
    }

    private ArrayList<Point> fillOccupiedCoords(Point referenceCoords, int width, int height) {
        ArrayList<Point> retArr = new ArrayList<Point>();
        for (int i = referenceCoords.x; i < referenceCoords.x + width; i++) {
            for (int j = referenceCoords.y; j < referenceCoords.y + width; j++) {
                retArr.add(new Point(i, j));
            }
        }
        return retArr;
    }

    /**
     * graphic display of Visual
     */
    public void draw() {
        //PLACEHOLDER
    }

    ///// getters, setters
    
    public Rectangle getBodyRect() {
        return new Rectangle(referenceCoords, new Dimension(width, height));
    }
    
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
        return coordsArray;
    }
    
    public void setReferenceCoords(Point referenceCoords) {
        this.referenceCoords = referenceCoords;
    }


}
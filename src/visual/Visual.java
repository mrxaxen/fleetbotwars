/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 *
 * @author asjf86
 */
public class Visual {

    private Point referenceCoords;
    private ArrayList<Point> coordsArray;
    private String type;
    private Image model; // [!]
    private int width, height;
    private Rectangle rectangle;
    private int defaultDimX = 10;
    private int defaultDimY = 10;
    private Point centerCoords;

    /**
     * create Visual Object with default dimensions specified by defaultDimX,
     * defaultDimY
     * 
     * @param referenceCoords Top left coords of the current Visual element.
     * @param type            Unit name (eg. Cavalary, Destroyer, etc.)
     * @param model           Serves as the visually represented modell of it's
     *                        owner.
     */

    public Visual(Point referenceCoords, String type, Image model) {
        this.referenceCoords = referenceCoords;
        this.type = type;
        this.model = model;
        this.rectangle = new Rectangle(referenceCoords, new Dimension(this.defaultDimX, this.defaultDimY));
        this.centerCoords = new Point(rectangle.getCenterX(), rectangle.getCenterY());

        this.coordsArray = new ArrayList<Point>();
        coordsArray = fillOccupiedCoords(referenceCoords, defaultDimX, defaultDimY);

    }

    /**
     * @param referenceCoords Top left coords of the current Visual element. This
     *                        important if it has any dimension greater then 1.
     * @param type            Unit name (eg. Cavalary, Destroyer, etc.)
     * @param model           Serves as the visually represented modell of it's
     *                        owner.
     * @param width           Width of the unit.
     * @param height          Height of the unit.
     */

    public Visual(Point referenceCoords, String type, Image model, int width, int height) {
        this.referenceCoords = referenceCoords;
        this.type = type;
        this.model = model;
        this.rectangle = new Rectangle(referenceCoords, new Dimensions(width, heigth));
        this.centerCoords = new Point(this.rectangle.getCenterX(), this.rectangle.getCenterY());

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
        //
    }

    /**
     * Checks if the given coords are colliding/intersecting the current Visual
     * element.
     * 
     * @param coords The coords where intersection needs to be checked.
     * @return True if intersect, false else.
     */
    public Boolean containsPoint(Point coords) {
        return this.rectangle.contains(coords);
    }

    /**
     * Returns the distance between the given coords and the center of the Visual
     * Object
     * 
     * @param coordsFrom The coords from where it needs to be checked.
     * @return The distance in int type.
     */
    public Integer distanceFrom(Point coordsFrom) {
        return (int) (Point2D.distance(coordsFrom.x, coordsFrom.y, centerCoords.x, centerCoords.y));
    }

    public Point getDirectionFrom(Point coordsFrom) {
        int x = coordsFrom.x - centerCoords.x;
        int y = coordsFrom.y - centerCoords.y;
        return new Point(x, y);
    }

    /**
     * @return the type
     */
    public String getType() {
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
     * @return the centerCoords
     */
    public Point getCenterCoords() {
        return centerCoords;
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
}

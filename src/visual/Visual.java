/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author asjf86
 */
public class Visual {
    
    protected Point referenceCoords;
    private ArrayList<Point> coordsArray;
    protected String type;
    private Image model; //[!]
    protected int width, height;

    /**
     * create Visual
     * @param referenceCoords Top left coords of the current Visual element. This important if it has any dimension greater than 1.
     * @param type Unit name (eg. Cavalary, Destroyer, etc.)
     * @param model Serves as the visually represented modell of it's owner.
     */

    public Visual(Point referenceCoords, String type, Image model) {
        this.referenceCoords = referenceCoords;
        this.type = type;
        this.model = model;
        this.coordsArray = new ArrayList<Point>();
        coordsArray.add(referenceCoords);

    }   
    /**
     * @param referenceCoords Top left coords of the current Visual element. This important if it has any dimension greater then 1.
     * @param type Unit name (eg. Cavalary, Destroyer, etc.)
     * @param model Serves as the visually represented modell of it's owner.
     * @param width Width of the unit
     * @param height Height of the unit
     */

    public Visual(Point referenceCoords, String type, Image model, int width, int height) {
        this.referenceCoords = referenceCoords;
        this.type = type;
        this.model = model;
        this.coordsArray = new ArrayList<Point>();
        coordsArray = fillOccupiedCoords(referenceCoords, width, height);
    }  

    private ArrayList<Point> fillOccupiedCoords(Point referenceCoords, int width, int height){
        ArrayList<Point> retArr = new ArrayList<Point>();
        for(int i = referenceCoords.x; i < referenceCoords.x + width; i++){
            for(int j = referenceCoords.y; j < referenceCoords.y + width; j++){
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
     * tells whether given Point is inside the Visual's area
     * @param p
     * @return 
     */
    public boolean containsPoint(Point p) {
        //PLACEHOLDER
        return true;
    }
    
    /**
     * returns distance between given Point and closest Point of Visual's area
     * @param p
     * @return 
     */
    public Point distanceFromPoint(Point p) {
        //PLACEHOLDER
        //return (distance on x axis, distance on y axis)
        //@Laci, legyszi igy ket reszben add vissza, mert kulon hasznalom 
        //az x es y komponenset a tavolsagnak. koszi!
        return null;
    }
    
    ///// getters, setters
    
    public Point getReferenceCoords() {
        return referenceCoords;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
        
}

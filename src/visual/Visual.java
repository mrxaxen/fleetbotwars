/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual;

import java.awt.Image;
import java.awt.Rectangle;

/**
 *
 * @author asjf86
 */
public class Visual {
    
    //top left corner
    protected int x, y;
    protected Rectangle rect;
    private String type;
    private Image model; //[!]

    ///////////////////////RECT IN CONSTRUCTOR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    
    /**
     * create Visual
     * @param x
     * @param y
     * @param type
     * @param model 
     */
    public Visual(int x, int y, String type, Image model) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.model = model;
    }   
    
    /**
     * graphic display of Visual
     */
    public void draw() {
        //
    }
    
    /////getters, setters

    public Rectangle getRect() {
        return rect;
    }
        
}

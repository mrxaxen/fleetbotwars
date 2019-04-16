/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import java.awt.Point;

/**
 *
 * @author trmx
 */
public class OutOfMapBoundsException extends RuntimeException {
    public OutOfMapBoundsException(Point coords){
        super("The given coords (" + coords.x + "," + coords.y +") are out of the map bounds");
    }
    
}


package visual.ground;

import java.awt.Image;
import java.util.Stack;

import fleetbot_wars.model.enums.VisualType;
import visual.unit.Unit;
import visual.Visual;
import java.awt.Point;

/**
 *
 * @author asjf86
 */
public class Ground extends Visual {

    protected boolean occupied = false;
    private Stack<Unit> ownerReference;

    /**
     * The Ground Object is a representation of an atomic piece of land placed on
     * the map. It is able to tell if it's occupied, whos occupting it and waht are
     * its coordinates.
     * 
     * @param coords         The coordinates.
     * @param type           The name of it's type: water, mountain, resource (gold,
     *                       stone), or clear ground (base).
     * @param model          It's image/sprite representation
     * @param ownerReference The initial unit it's occupying it, else null.
     */
    public Ground(Point coords, VisualType type, Image model, Unit inOwnerReference) {
        super(coords, type, model, 1, 1);
        ownerReference = new Stack<Unit>();
        if (inOwnerReference != null) {
            this.ownerReference.push(inOwnerReference);
            this.occupied = true;
        }
    }

    /**
     * 
     * @return True if occupied, false else.
     */
    public boolean isOccupied() {
        return this.occupied;

    }
    
    /**
     * used to help building
     * @return true if unoccupied or has a Tree, false else
     */
    public boolean isFreeOrTree(){
        boolean hasTree = false;
        Unit owner = getOwnerReference();
        if(owner != null){
            hasTree = owner.getType().equals(VisualType.tree);
        }
        
        return !isOccupied() || hasTree;
    }
    
    /**
     * 
     * @return the reference to the top Unit Object, who owns/occupies the current
     *         ground
     */
    public Unit getOwnerReference() {
        if (!ownerReference.empty()) {
            return ownerReference.peek();
        }
        return null;

    }

    /**
     * Put a unit on this Ground.
     * 
     * @param ownerReference The unit that needs to be put here.
     */
    public void setOwnerReference(Unit ownerReference) {
        this.ownerReference.push(ownerReference);
        this.occupied = true;
    }

    /**
     * Removes the top occupying Unit of the current tile
     */
    public void removeOwner() {
        if (this.occupied) {
            this.ownerReference.pop();
        }
        this.occupied = !(ownerReference.isEmpty());
    }

}

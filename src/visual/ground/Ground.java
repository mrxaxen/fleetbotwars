
package visual.ground;

import java.awt.Image;

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
    private Unit ownerReference;

    /**
     * The Ground Object is a representation of an atomic piece of land placed on
     * the map. It is able to tell if it's occupied, whos occupting it and waht are
     * its coordinates.
     * 
     * @param coords         The coordinates.
     * @param type           The name of it's type: WATER, MOUNTAIN, resource (GOLD,
     *                       STONE), or clear ground (BASE).
     * @param model          It's image/sprite representation
     * @param ownerReference The initial unit it's occupying it, else null.
     */
    public Ground(Point coords, VisualType type, Image model, Unit ownerReference) {
        super(coords, type, model, 1, 1);
        this.ownerReference = ownerReference;
    }

    /**
     * 
     * @return True if occupied, false else.
     */
    public boolean isOccupied() {
        return this.occupied || ownerReference != null;
    }
    
    /**
     * used to help building
     * @return true if unoccupied or has a Tree, false else
     */
    public boolean isFreeOrTree(){
        boolean hasTree = false;
        Unit owner = getOwnerReference();
        if(owner != null){
            VisualType oType = owner.getType();
            hasTree = oType.equals(VisualType.TREE) ||
                    oType.equals(VisualType.TREE_1) ||
                    oType.equals(VisualType.TREE_2) ||
                    oType.equals(VisualType.TREE_3) ||
                    oType.equals(VisualType.TREE_4);
        }
        
        return !isOccupied() || hasTree;
    }
    
    /**
     * 
     * @return the reference to the top Unit Object, who owns/occupies the current
     *         ground
     */
    public Unit getOwnerReference() {
        return ownerReference;
    }

    /**
     * Put a unit on this Ground.
     * 
     * @param ownerReference The unit that needs to be put here.
     */
    public void setOwnerReference(Unit ownerReference) {
        this.ownerReference = ownerReference;
    }

}

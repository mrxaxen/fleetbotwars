package visual.ground;

import java.awt.Image;
import java.awt.Point;
import visual.unit.Unit;
import fleetbot_wars.model.enums.VisualType;

public abstract class Minable extends Ground {

    private int resourceCount;
    private Boolean depleted = false;

    /**
     * Minable Abstract Class serves as parent class for Gold and Stone Ground
     * elements. It can be mined trough the mine method
     * 
     * @param coords         It's coordinates.
     * @param type           The name of it's type (gold, stone)
     * @param model          It's image representation.
     * @param ownerReference
     * @param resourceCount  The initial amount of resource available in the
     *                       resource.
     */
    public Minable(Point coords, VisualType type, Image model, Unit ownerReference, int resourceCount) {
        super(coords, type, model, ownerReference);
        this.resourceCount = resourceCount;
        this.occupied = true;
    }

    /**
     * 
     * @param amount The amount that needs to be mined.
     * @return False if the mine is depleted (empty) true else.
     */
    public boolean mine(int amount) {
        if (!depleted) {
            resourceCount = resourceCount - amount;
            if (resourceCount < 1) {
                depleted = true;
                return false;
            }
            return true;
        } else {
            return depleted;
        }

    }

    /**
     * 
     * @return False if the mine is depleted (empty) true else.
     */
    public boolean mine() {
        if (!depleted) {
            resourceCount = resourceCount - 10;
            if (resourceCount < 1) {
                depleted = true;
                return false;
            }
            return true;
        } else {
            return depleted;
        }
    }

}
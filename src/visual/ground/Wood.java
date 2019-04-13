package visual.ground;

import java.awt.Image;
import java.awt.Point;

import fleetbot_wars.model.enums.VisualType;

/**
 *
 * @author asjf86
 */
public class Wood extends Minable {


    /**
     * Representation of a wood type resource element. This element can be collided,
     * ie. an obsiticle.
     * 
     * @param coords It's coords.
     */
    public Wood(Point coords, int resourceCount) {
        super(coords, VisualType.wood, null, null, resourceCount);
        this.occupied = true;
    }

}

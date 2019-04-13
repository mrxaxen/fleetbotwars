package visual.ground;

import fleetbot_wars.model.enums.VisualType;

import java.awt.Image;
import java.awt.Point;

/**
 *
 * @author asjf86
 */
public class Gold extends Minable {

    /**
     * Representation of a gold type resource element. This element can be collided,
     * ie. an obsiticle.
     * 
     * @param coords It's coords.
     */
    public Gold(Point coords, int resourceCount) {
        super(coords, VisualType.gold, null, null, resourceCount);
    }

}

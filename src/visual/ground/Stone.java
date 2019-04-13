package visual.ground;

import java.awt.Image;
import java.awt.Point;

/**
 *
 * @author asjf86
 */
public class Stone extends Minable {


    /**
     * Representation of a stone type resource element. This element can be collided,
     * ie. an obsiticle.
     * 
     * @param coords It's coords.
     * @param resourceCount
     */
    public Stone(Point coords, int resourceCount) {
        super(coords, "stone", null, null, resourceCount);
        this.occupied = true;
    }


}
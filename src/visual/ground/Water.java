package visual.ground;

import fleetbot_wars.model.enums.VisualType;

import java.awt.Image;
import java.awt.Point;

/**
 *
 * @author asjf86
 */
public class Water extends Ground {

    /**
     * Creating a Mountain element. his element can be collided, ie. an obsiticle.
     * 
     * @param coords It's coords.
     */
    public Water(Point coords) {
        super(coords, VisualType.water, null, null);
        this.occupied = true;
    }

}

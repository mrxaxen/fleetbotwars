package visual.unit;

import java.awt.Image;
import java.awt.Point;

import fleetbot_wars.model.enums.VisualType;
import visual.Visual;

/**
 *
 * @author asjf86
 */
public class Unit extends Visual {

    protected int maxHp, currHp;

    /**
     * create Unit
     * 
     * @param coords
     * @param type
     * @param model
     * @param hp
     */
    public Unit(Point coords, VisualType type, Image model, int hp) {
        super(coords, type, model);
        this.maxHp = this.currHp = hp;
    }

    /**
     * events upon death of Unit
     * 
     * @param enemyTeam: team benefitting from Unit death (team that gets the drops)
     */
    public void deathEvent(int enemyTeam) {
        //
    }

    /**
     * display properties and actions of Unit
     */
    public void inspect() {
        // RETURN ACTIONSPANE, UNIT MATRIX??
    }

    ///// getters, setters

    public int getCurrHp() {
        return currHp;
    }

}

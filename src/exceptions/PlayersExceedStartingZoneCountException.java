/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author trmx
 */
public class PlayersExceedStartingZoneCountException extends RuntimeException {
    public PlayersExceedStartingZoneCountException(int playerCount, int startingZoneCount){
        super("There are more Players than starting Zones on the map.\n"
                + "Players: " + playerCount +"\n"
                + "Starting Zones: "+startingZoneCount);
    }
}

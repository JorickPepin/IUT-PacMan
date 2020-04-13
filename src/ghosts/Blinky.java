 
package ghosts;

import iut.Game;

/**
 * Blinky est le fantôme bleu
 * Ses déplacements sont les suivants :
 * "Blinky attaque directement Pac Man. Il suit Pac-Man comme son ombre."
 * @author Jorick
 */
public class Blinky extends Ghost {

    public Blinky(Game g, int x, int y) {
        super(g, "Ghost/blinkyRight", x, y);
    }
    
    
}

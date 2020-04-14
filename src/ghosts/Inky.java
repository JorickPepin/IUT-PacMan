
package ghosts;

import pacman.PacMan;

/**
 * Inky est le fantôme rouge
 * Ses déplacements sont les suivants :
 * "Inky est capricieux. De temps en temps, il part dans la direction opposée à Pac-Man."
 * @author Jorick
 */
public class Inky extends Ghost {

    public Inky(PacMan game) {
        super(game, "", 0, 0);
    }

    @Override
    public void die() { }
    
    
}

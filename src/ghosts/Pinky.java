
package ghosts;

import pacman.PacMan;

/**
 * Pinky est le fantôme rose
 * Ses déplacements sont les suivants :
 * "Pinky a tendance à se mettre en embuscade. Elle vise l'endroit où va se trouver Pac-Man."
 * @author Jorick
 */
public class Pinky extends Ghost {

    public Pinky(PacMan game) {
        super(game, "", 0, 0);
    }

    @Override
    public void die() {}

 
}

 
package ghosts;

import pacman.PacMan;

/**
 * Blinky est le fantôme bleu
 * Ses déplacements sont les suivants :
 * "Blinky attaque directement Pac Man. Il suit Pac-Man comme son ombre."
 * @author Jorick
 */
public class Blinky extends Ghost {
   
    public Blinky(PacMan game, int x, int y) {
        super(game, "images/Ghost/blinkyRight", x, y);
    }

    @Override
    public void die() {}

    @Override
    public void setTime(int time) {
    }

    @Override
    public int getI() {
        return 0;
    }

    @Override
    public int getJ() {
        return 0;
    }

 
}

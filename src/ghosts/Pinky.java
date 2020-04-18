
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

    @Override
    public void setTime(int time) {}

    @Override
    public int getI() {
        return 0;
    }

    @Override
    public int getJ() {
        return 0;
    }

    @Override
    public void setI(int i) {}

    @Override
    public void setJ(int j) {}

    @Override
    public void initGhost() {}

    @Override
    public String getGhostName() {
        return "Pinky";
    }
}


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

    @Override
    public void setI(int i) {}

    @Override
    public void setJ(int j) {}

    @Override
    public void initGhost() {}

    @Override
    public String getGhostName() {
        return "Inky";
    }
}

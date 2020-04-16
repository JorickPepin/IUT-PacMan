
package stats;

import iut.Game;
import iut.GameItem;
import pacman.PacMan;

/**
 * Classe représentant une vie
 * @author Jorick
 */
public class Life extends GameItem {

    private int nbLives = 2;
    
    private final PacMan game;
    
    public Life(PacMan g, String name, int x, int y) {
        super(g, name, x, y);  
        
        this.game = g;
    }

    public void removeALife() {

        this.nbLives--;
        System.out.println(nbLives);
        if (nbLives == 1) {
            this.changeSprite("images/Lives/1");
        } else {
            game.remove(this);
        }
    }
    
    public int getNbLives() {
        return nbLives;
    }
        
    
    
    
    @Override public boolean isCollide(GameItem gi) {return false;}
    @Override public void collideEffect(GameItem gi) {}
    @Override public String getItemType() {return "Life";}
    @Override public void evolve(long l) {}
}

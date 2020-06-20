
package stats;

import iut.GameItem;
import pacman.Instruction;
import pacman.PacMan;

/**
 * Classe représentant la gestion des vies
 * @author Jorick
 */
public class Life extends GameItem {

    private int nbLives;
    private final PacMan game;
    
    /**
     * Constructeur 
     * @param g = le jeu
     * @param name = le nom du fichier
     * @param x = abscisse
     * @param y  = ordonnée
     */
    public Life(PacMan g, String name, int x, int y) {
        super(g, name, x, y);   
        
        this.game = g;
        this.nbLives = 2; // 2 vies au départ
    }

    /**
     * Méthode permettant d'enlever une vie au joueur
     */
    public void removeALife() {

        this.nbLives--;
      
        if (nbLives == 1) { // il reste une vie au joueur
            this.changeSprite("images/Lives/1");
        } else { // le joeur n'a plus de vie
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


package stats;

import iut.GameItem;
import pacman.PacMan;

/**
 * Classe contenant la gestion des vies du joueur
 * @author Jorick
 */
public class Life extends GameItem {

    /**
     * Nombre de vies du joueur
     */
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
    
    /**
     * Méthode qui retourne le nombre de vies du joueur
     * @return 
     */
    public int getNbLives() {
        return nbLives;
    }

    /**
     * Méthode permettant de fixer le nombre de vies
     * @param nbLives = le nombre de vies
     */
    public void setNbLives(int nbLives) {
        this.nbLives = nbLives;
        
        if (nbLives == 2) {
            this.changeSprite("images/Lives/2");
        }
    }
      
    @Override public boolean isCollide(GameItem gi) {return false;}
    @Override public void collideEffect(GameItem gi) {}
    @Override public String getItemType() {return "Life";}
    @Override public void evolve(long l) {}
}

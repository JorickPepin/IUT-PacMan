
package pacman;

import iut.Game;
import iut.Vector;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

/**
 *
 * @author Jorick
 */
public class PacMan extends Game {

    public static void main(String[] args) throws IOException {
        
        PacMan jeu = new PacMan();
        
        // création de la map
        map = new Map(jeu);
        
        // affichage de la map dans la console (affichage de test)
        // System.out.println(map);
        
        jeu.play();
    }
    
    public PacMan() {
        super(700,560,"Pac-man");
    }
    
    private Player player;
    private static Map map; 
    
    @Override
    protected void drawBackground(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(00, 0, getWidth(), getHeight()); 
    }

    @Override
    protected void createItems() {
        
        // création du joueur 
        // (position de départ sur les cases (x=2, y=9) = (2 * 28, 9 * 28) en pixels)
        player = new Player(this,56,252);
        this.addItem(player); 
        player.setSquares(map.getSquares());
        
        // ajout du label de Score
        Score labelScore = new Score(this, "Score/labelScore", 10, 8);
        this.addItem(labelScore);
    }

    @Override
    protected void lost() {}

    @Override
    protected void win() {}

    /**
     * /!\ Méthode non codée
     * @return 
     */
    @Override
    protected boolean isPlayerWin() {
        return false;
    }
     
    /**
     * /!\ Méthode non codée
     * @return 
     */
    @Override
    protected boolean isPlayerLost() {
        return false; 
    }

    // gravité non utilisée dans ce jeu
    @Override public Vector getGravity() {return new Vector();}
}

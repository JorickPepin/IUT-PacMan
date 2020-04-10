
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
        player = new Player(this,56,252);
        this.addItem(player); 
        player.setSquares(map.getSquares());
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

    /**
     * /!\ Méthode non codée
     * @return 
     */
    @Override
    public Vector getGravity() {
        Vector v = new Vector();
        
        return v;
    }
  
}

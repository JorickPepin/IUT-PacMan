
package pacman;

import ghosts.Blinky;
import ghosts.Clyde;
import ghosts.Ghost;
import ghosts.Inky;
import ghosts.Pinky;
import stats.Life;
import stats.Score;
import iut.Game;
import iut.Vector;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Jorick
 */
public class PacMan extends Game {

    public static void main(String[] args) throws IOException, InterruptedException {
        
        // création du jeu
        game = new PacMan();
        
        // création de la map
        map = new Map(game);
        
        game.play();  
    }
    
    public PacMan() {
        super(700,560,"Pac-man");
    }
    
    /**
     * Attribut représentant le joueur (il est représenté par pacman)
     */
    private Player player;
    
    /**
     * Attribut représentant la map du jeu
     */
    private static Map map; 
    
    private final ArrayList<Ghost> ghostsList = new ArrayList();
    
    private static PacMan game;
    
//    private boolean enterIsPressed = false;
    
    @Override
    protected void drawBackground(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(00, 0, getWidth(), getHeight()); 
    }

    @Override
    protected void createItems() {

        // création du joueur 
        // (position de départ sur les cases (x=12, y=14) = (12 * 28, 14 * 28) en pixels)
        player = new Player(this, 336, 392);
        game.addItem(player);
        player.setSquares(map.getSquares());

        // ajout du label de score
        Score labelScore = new Score(game, "images/Score/labelScore", 10, 8);
        game.addItem(labelScore); 

        // ajout du label du nombre de vies
        Life labelLife = new Life(game, "images/Lives/labelLives", 10, 540);
        game.addItem(labelLife);
        
        Instruction departureInstruction = new Instruction(game, "images/Diverse/departureInstructions");
        game.addItem(departureInstruction);

        // création du fantôme orange
        Clyde clyde = new Clyde(game);
        addGhost(clyde);
        
        // création du fantôme bleu
        Blinky blinky = new Blinky(game);
        addGhost(blinky);
        
        // création du fantôme rouge
        Inky inky = new Inky(game); 
        addGhost(inky);
        
        // création du fantôme rose
        Pinky pinky = new Pinky(game);
        addGhost(pinky);
    }

    @Override
    protected void lost() {
        JOptionPane.showMessageDialog(this, "Vous avez perdu");
    }

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
    
    public void addGhost(Ghost ghost) {
        this.ghostsList.add(ghost); 
    }

    public void removeGhost(Ghost ghost) {
        this.ghostsList.remove(ghost);
    }
    
    public ArrayList<Ghost> getGhostsList() {
        return ghostsList;
    } 

    public static Map getMap() {
        return map;
    } 

    public Player getPlayer() {
        return player;
    }
}

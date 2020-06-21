
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
import java.util.ArrayList;

/**
 * Classe principale du jeu
 * @author Jorick
 */
public class PacMan extends Game {

    public static void main(String[] args) {
        // création du jeu
        game = new PacMan();
        
        // création de la map
        map = new Map(game);
        
        game.play();  
    }
    
    public PacMan() {
        super(700,560,"Pac-Man");          
    }
     
    /**
     * Attribut représentant le joueur (il est représenté par pacman)
     */
    private Player player;
    
    /**
     * Attribut représentant la map du jeu
     */
    private static Map map; 
    
    /**
     * Attribut contenant la liste des fantômes du jeu
     */
    private final ArrayList<Ghost> ghostsList = new ArrayList();
    
    private static PacMan game;
    
    /**
     * Booleen permettant de savoir si un joueur est déjà présent ou non
     */
    private boolean hasPlayer = false;
    
    /**
     * Booleen permettant de savoir si la partie est terminée
     */
    private boolean isOver = false;
    
    @Override
    protected void drawBackground(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(00, 0, getWidth(), getHeight()); 
    }
 
    @Override
    public void createItems() {

        // ajout du label de score
        Score labelScore = new Score(game, "images/Score/labelScore", 25, 5);
        game.addItem(labelScore); 

        // ajout du label du nombre de vies
        Life labelLife = new Life(this, "images/Lives/labelLives", 25, 540);
        game.addItem(labelLife);
    
        Instruction instruction = new Instruction(game, "images/Diverse/departureInstruction", 225, 280);
        game.addItem(instruction);

        if (!hasPlayer) { // si le joueur n'a pas déjà été créé
            // création du joueur 
            // (position de départ sur les cases (x=12, y=14) = (12 * 28, 14 * 28) en pixels)
            player = new Player(this, map, 336, 392);
            game.addItem(player);
            
            hasPlayer=true;
        }

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
     
    public boolean isOver() {
        return this.isOver;
    }
    
    public void over(boolean isOver) {
        this.isOver = isOver;
    }
    
    public void addGhost(Ghost ghost) {
        this.ghostsList.add(ghost); 
    }

    public void removeGhost(Ghost ghost) {
        this.ghostsList.remove(ghost);
    }
    
    public ArrayList<Ghost> getGhostsList() {
        return ghostsList;
    } 
    
    public void removeAllGhosts() {
        this.ghostsList.clear();
    }

    public static Map getMap() {
        return map;
    } 

    public Player getPlayer() {
        return player;
    }
     
    @Override protected void lost() {}
    @Override protected void win() {}
    @Override protected boolean isPlayerWin() {return false;}
    @Override public boolean isPlayerLost() {return false;}
    @Override public Vector getGravity() {return new Vector();}
}

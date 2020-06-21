
package pacman;

import iut.BoxGameItem;
import iut.GameItem;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Classe représentant les différentes instructions données à l'utilisateur
 * 
 * @author Jorick
 */
public class Instruction extends BoxGameItem implements KeyListener {

    private PacMan game;
    
    /**
     * Compteur pour limiter la vitesse dans le evolve
     * on utilise un byte car codé sur 8 bits => modulo calculé sur des 
     * plus petites valeurs qu'avec un int (complexité améliorée ?)
     */
    private byte count = 0;
    
    /**
     * Attribut permettant de faire clignoter l'instruction
     */
    private boolean instructionIsDiplayed = true;

    /**
     * Attribut contenant le nom du fichier de l'instruction
     */
    private String instructionName;
    
    public Instruction(PacMan game, String sprite, int x, int y) {
        super(game, sprite, x , y);
        
        this.game = game;
        this.instructionName = sprite;
    }

    @Override
    public void evolve(long l) {
        
        // est vrai 1 fois sur 20 afin de reduire la vitesse d'exécution
        if (count % 20 == 0) {
            
            // si c'est l'instruction est l'instruction de départ ("pressez entrée")
            // on la fait clignoter
            if (this.instructionName.equals("images/Diverse/departureInstruction")) {
                flickerInstruction();
            }
            
            // si l'instruction est "nouvelle chance" et que count vaut 120 (càd que quelques secondes se sont écoulées)
            // on chance l'instruction en ("pressez entrée")
            if (this.instructionName.equals("images/Diverse/newChance") && count == 80) {
                this.instructionName = "images/Diverse/departureInstruction";
            }
        }
        count++;
    }

    /**
     * Méthode permettant de faire clignoter l'instruction
     */
    private void flickerInstruction() {
        if (instructionIsDiplayed) {
            changeSprite("images/Squares/empty");
            this.instructionIsDiplayed = false;
        } else {
            changeSprite(this.instructionName);
            this.instructionIsDiplayed = true;
        }
    }
    
    @Override public void collideEffect(GameItem gi) {}
    @Override public String getItemType() {return "";}
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {}

    @Override 
    public void keyReleased(KeyEvent e) {
        // si l'utilisateur appuie sur la touche entrée, on enlève l'instruction
        if(e.getKeyCode() == KeyEvent.VK_ENTER && !game.isOver()) {
            game.remove(this);
        }
    }
    
}

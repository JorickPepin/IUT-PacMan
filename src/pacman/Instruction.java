/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import iut.Audio;
import iut.BoxGameItem;
import iut.GameItem;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
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
    
    private boolean test = false;
    
    
    public Instruction(PacMan game) {
        super(game, "images/Diverse/departureInstructions", 225, 280);
        this.game = game;
    }

    @Override
    public void evolve(long l) {
        // est vrai 1 fois sur 20 afin de reduire la vitesse d'exécution
        if (count % 20 == 0) {
            flickerInstruction();
        }
        count++;
        
//        if (!test) {
//            Audio test2 = new Audio("sons/pacman_beginning");
//            test2.start();
//            
//           
//            if(test2.isInterrupted()) {
//                System.out.println("coupéééééé");
//                test = true;
//            }
//    }
            
            
                
        
    }

    
    /**
     * Méthode permettant de faire clignoter l'instruction
     */
    private void flickerInstruction() {
        if (instructionIsDiplayed) {
            changeSprite("images/Squares/empty");
            this.instructionIsDiplayed = false;
        } else {
            changeSprite("images/Diverse/departureInstructions");
            this.instructionIsDiplayed = true;
        }
    }
    
    @Override public void collideEffect(GameItem gi) {}
    @Override public String getItemType() {return "";}
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {}

    @Override 
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            game.remove(this);
        }
    }
    
}

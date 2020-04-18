/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package square;

import iut.Game;
import iut.GameItem;


/**
 * Classe représentant une case vide
 * @author Jorick
 */
public class EmptySquare extends Square {
    
    private String type = "emptyWithPoint";
    
    /**
     * Compteur pour limiter la vitesse dans le evolve
     * on utilise un byte car codé sur 8 bits => modulo calculé sur des 
     * plus petites valeurs qu'avec un int (complexité améliorée ?)
     */
    private byte count = 0;
    
    /**
     * Attribut permettant de faire clignoter les gros points
     */
    private boolean bigPointIsDiplayed = true;
    
    public EmptySquare(Game g, int i, int j) {
        super(g, "images/Squares/emptyWithPoint", i, j);
    }

    @Override
    public String getItemType() {
        return this.type;
    }
    
    @Override
    public String toString() {
        return "(" + getI() + ", " + getJ() + ")"; 
    }

    @Override
    public void evolve(long l) {
        // est vrai 1 fois sur 20 afin de reduire la vitesse d'exécution
        if (count % 20 == 0) {
            // si la case contient un gros point, on appelle la méthode
            if ("emptyWithBigPoint".equals(this.getItemType()) ) {
                flickerBigPoint();        
            }
        }
        count++;
        
    }

    /**
     * Méthode permettant de faire clignoter les gros points
     */
    private void flickerBigPoint() {
        if (bigPointIsDiplayed) {
            changeSprite("images/Squares/empty");
            this.bigPointIsDiplayed = false;
        } else {
            changeSprite("images/Squares/emptyWithBigPoint");
            this.bigPointIsDiplayed = true;
        }
    }
    
    @Override
    public void collideEffect(GameItem gi) {
        ;
    }

    @Override
    public void setItemType(String type) {
        this.type = type;
    }

    @Override
    public String getSimpleType() {
        return "empty";
    }
}

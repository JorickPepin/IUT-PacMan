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
    
    public EmptySquare(Game g, int i, int j) {
        super(g, "Squares/emptyWithPoint", i, j);
    }

    @Override
    public String getItemType() {
        return this.type;
    }
    
    @Override
    public String toString() {
        return "E"; 
    }

    @Override
    public void collideEffect(GameItem gi) {
        ;
    }

    @Override
    public void setItemType(String type) {
        this.type = type;
    }
}

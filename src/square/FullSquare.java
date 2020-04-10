/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package square;

import iut.Game;
import iut.GameItem;

/**
 * Classe représentant une case pleine
 * @author Jorick
 */
public class FullSquare extends Square {
    
    private final String type = "full";
    
    public FullSquare(Game g, int i, int j) {
        super(g, "full", i, j);
    }

    @Override
    public void collideEffect(GameItem gi) {
        if ("Player".equals(gi.getItemType())) {
            System.out.println("j'ai été percuté : " + this.getI() + " " + getJ());
        }        
    }

    @Override
    public String toString() {
        return "F";
    }

    @Override
    public String getItemType() {
        return "full";
    }
}

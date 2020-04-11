/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stats;

import iut.Game;
import iut.GameItem;

/**
 * Classe représentant une vie
 * @author Jorick
 */
public class Life extends GameItem {

    public Life(Game g, String name, int x, int y) {
        super(g, name, x, y);  
    }
        
    @Override
    public boolean isCollide(GameItem gi) {
        return false;
    }

    @Override
    public void collideEffect(GameItem gi) {}

    @Override
    public String getItemType() {
        return "";
    }

    @Override
    public void evolve(long l) {}
    
}

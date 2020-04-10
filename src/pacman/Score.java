/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import iut.Game;
import iut.GameItem;

/**
 *
 * @author Jorick
 */
public class Score extends GameItem {

    public Score(Game g, int val) {
        super(g, String.valueOf(val), 10, 10);  
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


package square;

import iut.BoxGameItem;
import iut.Game;
import java.awt.Graphics;

/**
 * Classe abstraite représentant une case
 * @author Jorick
 */
public abstract class Square extends BoxGameItem {
    
    /**
     * Une case est un carré de 28 de côté
     */
    private static final int size = 28;
    
    private final int i;
    private final int j;
    
    public Square(Game g, String nom, int i, int j) {
        super(g, nom, j * size, i * size);
        
        this.i = i;
        this.j = j;
        
        g.addItem(this);
    }

    public abstract String getItemType();
    
    public abstract void setItemType(String type);
    
    @Override
    public void evolve(long l) {}

    @Override
    public void draw(Graphics g) throws Exception {
        super.draw(g);
    }

    @Override
    public void changeSprite(String name) {
        super.changeSprite(name); 
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    } 
}

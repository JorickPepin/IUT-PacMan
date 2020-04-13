
package ghosts;

import iut.BoxGameItem;
import iut.Game;
import iut.GameItem;

/**
 * Classe abstraite représentant un fantôme
 * Chaque fantôme possède des caractéristiques différentes (apparence, déplacements, ...)
 * @author Jorick
 */
public abstract class Ghost extends BoxGameItem {

    public Ghost(Game g, String name, int x, int y) {
        super(g, name, x, y);
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

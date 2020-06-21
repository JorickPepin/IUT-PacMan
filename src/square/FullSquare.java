
package square;

import iut.Game;
import iut.GameItem;

/**
 * Classe représentant une case pleine
 * @author Jorick
 */
public class FullSquare extends Square {
    
    
    private String type = "full";
    
    public FullSquare(Game g, int i, int j) {
        super(g, "images/Squares/full", i, j);
    }

    @Override public void collideEffect(GameItem gi) {}
    @Override public String toString() {return "";}

    @Override
    public String getItemType() {
        return "full";
    }

    @Override
    public void setItemType(String type) {
        this.type = type;
    }

    @Override
    public String getSimpleType() {
        return "full";
    }
}

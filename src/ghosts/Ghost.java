
package ghosts;

import iut.BoxGameItem;
import iut.GameItem;
import pacman.PacMan;

/**
 * Classe abstraite représentant un fantôme
 * Chaque fantôme possède des caractéristiques différentes (apparence, déplacements, ...)
 * @author Jorick
 */
public abstract class Ghost extends BoxGameItem {
    
    private boolean isVulnerable = false;
    
    private boolean isVulnerableGhostBlue = true;
    
    private boolean isDie = false;
    
    private final PacMan game;
    
    public Ghost(PacMan game, String name, int x, int y) {
        super(game, name, x, y); 
        
        this.game = game;
    }
    
    @Override
    public void collideEffect(GameItem gi) {}

    @Override
    public String getItemType() {
        return "ghost";
    }
 
    @Override
    public void evolve(long l) {}
    
    public void becomeVulnerable(boolean vulnerable) {
        this.isVulnerable = vulnerable; 
        this.changeSprite("Ghosts/dangerBlue");
    }
    
    public boolean isVulnerable() {
        return this.isVulnerable;
    }
    
    public void changeSpriteVulnerableGhost() {
        if (!isVulnerableGhostBlue) {
            changeSprite("Ghosts/dangerBlue");
            this.isVulnerableGhostBlue = true;
        } else {
            super.changeSprite("Ghosts/dangerWhite");
            isVulnerableGhostBlue = false;
        }
    }
    
    public void die() {
        this.isDie = true;
    }
    
    public void live() {
        this.isDie = false;
    }
    
    public boolean isDie() {
        return this.isDie;
    }
}

package ghosts;

import pacman.PacMan;

/**
 * Inky est le fantôme rouge
 * @author Jorick
 */
public class Inky extends Ghost {
    
    /**
     * Constructeur d'Inky (fantôme rouge)
     * @param game = le jeu
     */
    public Inky(PacMan game) {
        super(game, "images/Ghosts/inkyDown", 336, 196);
        
        // /!\ i = lignes = y
        // /!\ j = colonnes = x
        this.i = 7; // 196 / 28
        this.j = 12; // 336 / 28

        game.addItem(this);
    }

    @Override
    public void evolve(long l) {
        
        super.evolve(0);
        
        if (enterHasBeenPressed) {
            
            // si le fantôme est sur la case à l'extrême gauche ou à l'extrême droite
            // il faut le déplacer de l'autre côté
            if (needToCross()) {       
                // on le fait traverser
                crossTheMap();
            }

            // si le fantôme meurt
            if (this.isDead()) {

                if (count % (double)speed/3.0 == 0) {
                    // on appelle la méthode de la classe Ghost qui gère ce cas
                    ghostIsDead();
                }
            }

            // si le fantôme est vivant
            if (!this.isDead() && !this.isImmobilize) {

                // test permettant de limiter le nombre de répétition pour limiter la vitesse
                if (count % speed == 0) {

                    // on regarde si les cases autour du perso sont pleines 
                    sideBlocked();

                    // on définit la direction dans laquelle il doit se diriger
                    direction = defineDirection();

                    // si le fantôme est vulnérable
                    if (this.isVulnerable()) {

                        // on gère la vulnérabilité du fantôme
                        // en donnant le temps depuis lequel il l'est
                        setTheVunerableGhost(time);
                        time++;
                    }

                    // selon la direction, on change le sprite et on le fait avancer
                    switch (direction) {
                        case "left":
                            if (!this.isVulnerable()) { // on change le sprite que si le fantôme n'est pas vulnérable
                                changeSprite("images/Ghosts/inkyLeft");
                            } 
                            this.moveXY(-28, 0); // une case vers la gauche
                            this.j -= 1; // il est maintenant sur la colonne précédente
                            break;
                        case "right":
                            if (!this.isVulnerable()) { // on change le sprite que si le fantôme n'est pas vulnérable
                                changeSprite("images/Ghosts/inkyRight");
                            }
                            this.moveXY(28, 0); // une case vers la droite
                            this.j += 1; // il est maintenant sur la colonne suivante
                            break;
                        case "up":
                            if (!this.isVulnerable()) { // on change le sprite que si le fantôme n'est pas vulnérable
                                changeSprite("images/Ghosts/inkyUp");
                            }
                            this.moveXY(0, -28); // une case vers le haut
                            this.i -= 1; // il est maintenant sur la ligne précédente
                            break;
                        case "down":
                            if (!this.isVulnerable()) { // on change le sprite que si le fantôme n'est pas vulnérable
                                changeSprite("images/Ghosts/inkyDown");
                            }
                            this.moveXY(0, 28); // une case vers le bas
                            this.i += 1; // il est maintenant sur la ligne suivante
                            break;
                    }

                    leftBlocked = false;
                    downBlocked = false;
                    rightBlocked = false;
                    upBlocked = false;
                }
            }
            count++;
        }
    }
   
    @Override
    public String getGhostName() {
        return "Inky";
    }

    @Override
    public String getOrigineSprite() {
        return "images/Ghosts/inkyDown";
    }
}

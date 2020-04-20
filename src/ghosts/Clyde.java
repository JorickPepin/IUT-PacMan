package ghosts;

import pacman.PacMan;

/**
 * Clyde est le fantôme orange 
 * Ses déplacements sont les suivants : 
 * "Clyde feint l'indifférence. De temps en temps, il choisit une direction au hasard."
 *
 * @author Jorick
 */ 
public class Clyde extends Ghost {

    /**
     * Compteur pour savoi si le fantôme peut quitter le spawn ou non
     * Clyde doit le quitter après environ 10 secondes
     */
    private int isReadyToGo = 0;
    
    /**
     * Constructeur du fantôme Clyde
     * @param game = le jeu
     */
    public Clyde(PacMan game) {
        // (position de départ sur les cases (x=13, y=8) = (13 * 28, 8 * 28) en pixels)
        super(game, "images/Ghosts/clydeLeft", 364, 224);
        
        // /!\ i = lignes = y
        // /!\ j = colonnes = x
        this.i = 8; // 224 / 28
        this.j = 13; // 364 / 28

        // on ajout
        game.addItem(this);
    }
 
    @Override
    public void evolve(long l) {
        
        if (enterHasBeenPressed) {
            
            // si le fantôme est sur la case à l'extrême gauche ou à l'extrême droite
            // il faut le déplacer de l'autre côté
            if (needToCross()) {       
                // on le fait traverser
                crossTheMap();
            }

            // si le fantôme meurt
            if (this.isDead()) {

                if (count % (double)SPEED/3.0 == 0) {
                    // on appelle la méthode de la classe Ghost qui gère ce cas
                    ghostIsDead();
                }
            }

            // si le fantôme est vivant
            if (!this.isDead()) {

                // test permettant de limiter le nombre de répétition pour limiter la vitesse
                if (count % SPEED == 0) {

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

                    if (isReadyToGo == 40) {
                        // selon la direction, on change le sprite et on le fait avancer
                        switch (direction) {
                            case "left":
                                if (!this.isVulnerable()) { // on change le sprite que si le fantôme n'est pas vulnérable
                                    changeSprite("images/Ghosts/clydeLeft");
                                } 
                                this.moveXY(-28, 0); // une case vers la gauche
                                this.j -= 1; // il est maintenant sur la colonne précédente
                                break;
                            case "right":
                                if (!this.isVulnerable()) { // on change le sprite que si le fantôme n'est pas vulnérable
                                    changeSprite("images/Ghosts/clydeRight");
                                }
                                this.moveXY(28, 0); // une case vers la droite
                                this.j += 1; // il est maintenant sur la colonne suivante
                                break;
                            case "up":
                                if (!this.isVulnerable()) { // on change le sprite que si le fantôme n'est pas vulnérable
                                    changeSprite("images/Ghosts/clydeUp");
                                }
                                this.moveXY(0, -28); // une case vers le haut
                                this.i -= 1; // il est maintenant sur la ligne précédente
                                break;
                            case "down":
                                if (!this.isVulnerable()) { // on change le sprite que si le fantôme n'est pas vulnérable
                                    changeSprite("images/Ghosts/clydeDown");
                                }
                                this.moveXY(0, 28); // une case vers le bas
                                this.i += 1; // il est maintenant sur la ligne suivante
                                break;
                        }
                    } else {
                        isReadyToGo ++;
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
        return "Clyde";
    }
}

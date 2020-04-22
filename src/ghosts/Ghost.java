
package ghosts;

import iut.BoxGameItem;
import iut.GameItem;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import pacman.Map;
import pacman.PacMan;
import square.Square;

/**
 * Classe abstraite représentant un fantôme
 * Chaque fantôme possède des caractéristiques différentes (apparence, déplacements, ...)
 * @author Jorick
 */
public abstract class Ghost extends BoxGameItem implements KeyListener {
    
    // Coordonnées du fantôme (sur quelle case il est)
    // /!\ i = lignes = y
    // /!\ j = colonnes = x
    protected int i;
    protected int j;
    
    /**
     * Variable représentant la direction dans laquelle se déplace le fantôme
     * Elle est initialisée en haut car il part en haut au démarrage
     */
    protected String direction = "up";
    
    // Ces booleens servent à savoir si les cases autour du fantôme sont pleines
    // (gestion des colisions)
    protected boolean leftBlocked = false;
    protected boolean downBlocked = false;
    protected boolean rightBlocked = false;
    protected boolean upBlocked = false;
    
    /**
     * Attribut permettant de récupérer la map pour effectuer les tests de
     * position du fantôme
     */
    protected Square[][] squares;
    
    /**
     * Booleen permettant de savoir si le fantôme est vulnérable ou non
     * (il est vulnérable lorsque le joueur mange une super pac-gomme = gros point)
     */
    private boolean isVulnerable = false;
    
    /**
     * Booleen servant à alterner entre le sprite bleu et blanc lorsque le 
     * fantôme est vulnérable depuis un certain temps
     */
    private boolean isVulnerableGhostBlue = true;
    
    /**
     * Booleen permettant de savoir si le fantôme est mort ou vivant
     */
    private boolean isDead = false;
    
    /**
     * Compteur pour limiter la vitesse dans le evolve 
     * On utilise un byte car codé sur 8 bits donc le modulo est calculé sur 
     * des plus petites valeurs qu'avec un int (complexité améliorée ?)
     */
    protected byte count = 0;
    
    /**
     * Attribut représentant la vitesse du fantôme
     * /!\ plus la valeur est grande, moins la vitesse est élevée
     */
    protected static int speed = 10; //10
    
    private final PacMan game;
    
    protected final Map map;
    
    /**
     * Attribut représentant le noeud du graphe sur lequel le fantôme est
     * positionné au départ
     */
    private int origine;
    
    /**
     * Attribut représentant le temps écoulé depuis que le fantôme est
     * devenu vulnérable
     */
    protected int time = 0;
    
    protected boolean enterHasBeenPressed = false;
    
    protected boolean isImmobilize = false;
    
    private boolean collisionWithPacman = false;
    
    /**
     * Constructeur du fantôme
     * @param game = le jeu
     * @param name = le sprite correspond au fantôme créé
     * @param x = son abscisse de départ
     * @param y = son ordonnée de départ
     */
    public Ghost(PacMan game, String name, int x, int y) {
        super(game, name, x, y); 
        
        this.game = game;
        
        this.map = PacMan.getMap();
        
        // on récupère le tableau à deux dimensions représentant la map
        this.squares = map.getSquares();
        
        this.origine = getTheOrigine();
    }

    /**
     * Méthode permettant de devenir le fantôme comme étant vulnérable
     * (pacman a mangé une super pac-gomme (gros point))
     * @param vulnerable = true ou false selon s'il est vulnérable ou pas
     */
    public void becomeVulnerable(boolean vulnerable) {
        this.isVulnerable = vulnerable; 
        
        // l'image du fantôme devient celle du fantôme bleu
        if(!this.isDead) {
            this.changeSprite("images/Ghosts/dangerBlue");
        }
        
        // on initialise le compteur permettant de définir la durée pendant
        // laquelle le fantôme va être vulnérable à 0
        this.time = 0;
    }
    
    /**
     * Méthode permettant d'alterner entre l'image du fantôme en bleu et 
     * en blanc lorsqu'il n'est presque plus vulnérable
     */
    public void changeSpriteVulnerableGhost() {
        if (!isVulnerableGhostBlue) {
            changeSprite("images/Ghosts/dangerBlue");
            this.isVulnerableGhostBlue = true;
        } else {
            super.changeSprite("images/Ghosts/dangerWhite");
            isVulnerableGhostBlue = false;
        }
    }
  
    /**
     * Méthode permettant de gérer le temps pendant lequel le fantôme
     * est vulnérable
     * @param time = le temps qui s'est écoulé depuis qu'il est vulnérable
     */
    protected void setTheVunerableGhost(int time) {

        // si le compteur est supérieur à 30, on fait clignoter 
        // le fantôme pour avertir le joueur que c'est bientôt fini
        if (time > 30) {
            this.changeSpriteVulnerableGhost();
        }
        
        // si le compteur est égal à 40 (~ 10sec), le fantôme
        // n'est plus vulnérable
        if (time == 40) {
            this.becomeVulnerable(false);
            this.time = 0;
        }
    }
    
    /**
     * Méthode permettant de faire retourner le fantôme à son origine case par case
     */
    protected void returnToOrigne() {
        
        // on récupère la liste de noeuds qu'il doit emprunter (plus court chemin)
        List<Integer> path = map.getGraph().shortestPath(map.getSquareToNode().get(map.getSquares()[i][j]), origine);

        // on récupère la position x du noeud sur lequel il doit se rendre
        // il s'agit de l'élément 1 car l'élément 0 est le noeud sur lequel il se trouve
        // on récupère la case correspondant à ce noeud avec NodeToSquare et calculons ses coordonnées (j * 28)
        this.getPosition().setX(map.getNodeToSquare().get(path.get(1)).getJ() * 28);
        
        // on récupère la position y du noeud sur lequel il doit se rendre
        // il s'agit de l'élément 1 car l'élément 0 est le noeud sur lequel il se trouve
        // on récupère la case correspondant à ce noeud avec NodeToSquare et calculons ses coordonnées (i * 28)
        this.getPosition().setY(map.getNodeToSquare().get(path.get(1)).getI() * 28);

        // on met à jour ses coordonnées i et j en récupérant celles de la case
        this.i = map.getNodeToSquare().get(path.get(1)).getI();
        this.j = map.getNodeToSquare().get(path.get(1)).getJ();
    }

    /**
     * Méthode permettant de gérer le cas où le fantôme vient d'être mangé
     */
    protected void ghostIsDead() {
        
        game.removeGhost(this);
        
        // il n'est plus vulnérable
        this.becomeVulnerable(false); 
        
        // s'il est à sa position d'origine 
        if (map.getNodeToSquare().get(origine).getI() == this.i
                && map.getNodeToSquare().get(origine).getJ() == this.j) {

            this.live(); // il est de nouveau vivant
            this.direction = "up"; // il part vers le haut
            changeSprite("images/Ghosts/clydeUp"); // on réinitialise son sprite
            game.addGhost(this);

        } else { // il n'est pas encore à sa position d'origine
            
            // son sprite est celui du fantôme mort
            changeSprite("images/Ghosts/deadGhostUp"); 

            // on le fait avancer jusqu'à l'origine
            returnToOrigne();
        }  
    }
    
    /**
     * Méthode permettant d'obtenir la case d'origine du fantôme
     * @return le noeud (int) d'origine
     */
    private int getTheOrigine() {

         // suivant le fantôme, l'origine n'est pas la même
        switch (this.getGhostName()) {
            
            case "Clyde":
                origine = map.getSquareToNode().get(map.getSquares()[8][13]);
                break;
            case "Inky":
                origine = map.getSquareToNode().get(map.getSquares()[7][12]);
                break;
            case "Blinky":
                origine = map.getSquareToNode().get(map.getSquares()[8][11]);
                break;
            case "Pinky":
                origine = map.getSquareToNode().get(map.getSquares()[8][12]);
                break;
        }
        
        return origine;
    }
    
    /**
     * Appelée lorqu'il faut changer de côté sur la map (doite ou gauche)
     */
    protected void crossTheMap() {
        
        // pour qu'il ne puisse pas rentrer dans des blocs pleins du haut et 
        // du bas quand il traverse
        upBlocked = true;
        downBlocked = true;

        if ("left".equals(direction)) {   // il passe de gauche à droite
            this.j = 24;                  // la case sur laquelle il va repartir
            this.getPosition().setX(672); // 24 * 28
        } else {                          // il passe de droite à gauche
            this.j = 0;                   // la case sur laquelle il va repartir
            this.getPosition().setX(0);   // 0 * 28
        }
    }
    
    /**
     * Méthode permettant de renvoyer instantanément le fantôme à sa position
     * initiale (appelée lorsque le joueur meurt mais qu'il lui reste des vies)
     */
    public void initGhost() {
        
        this.i = map.getNodeToSquare().get(origine).getI();
        this.j = map.getNodeToSquare().get(origine).getJ();
        
        this.getPosition().setX(j * 28);
        this.getPosition().setY(i * 28);
        
        this.direction = "up";
        
        this.changeSprite(this.getOrigineSprite());
    }
    
    public void wipeOutGhost() {
        this.changeSprite("images/Squares/empty");
        this.getPosition().setX(336);
        this.getPosition().setY(196);
    }
    
    public void setOriginalSprite() {
        this.changeSprite(this.getOrigineSprite());
    }
    
    /**
     * Méthode permettant de fixer les booleens si les cases autour du fantôme
     * sont pleines (murs)
     */
    protected void sideBlocked() {

        // on enlève le cas où j vaut 0 ou 24 car on teste les j-1 donc si j==0, 
        // on teste j-1 et cette valeur n'existe pas donc erreur
        // pas besoin de tester i car le fantôme ne peut jamais aller sur 
        // les bords extrêmes du i contrairement au j (pour traverser)
        if (this.j != 0 && this.j != 24) {

            // si le type de la case au-dessus est full
            if ("full".equals(squares[i - 1][j].getItemType())) {
                upBlocked = true;
            }

            // si le type de la case en-dessous est full
            if ("full".equals(squares[i + 1][j].getItemType())) {
                downBlocked = true;
            }

            // si le type de la case à gauche est full
            if ("full".equals(squares[i][j - 1].getItemType())) {
                leftBlocked = true;
            }

            // si le type de la case à droite est full
            if ("full".equals(squares[i][j + 1].getItemType())) {
                rightBlocked = true;
            }
        }
    }
    
    /**
     * Méthode permettant de définir la direction de manière aléatoire
     * @return la direction dans laquelle il doit se diriger
     */
    protected String defineDirection() {
        
        // variable représentant le nombre de directions possibles
        int nbOfDirections = 0;
        
        // liste contenant les directions possibles
        ArrayList<String> directionList = new ArrayList();
        
        // on effectue des doubles tests pour éviter que le fantôme ne fasse
        // des demi-tours intempestifs
        
        // si la gauche n'est pas bloquée et qu'il ne vient pas de la droite
        if (!leftBlocked && !this.direction.equals("right")) {
            directionList.add("left"); // on ajoute la direction gauche aux directions possibles
            nbOfDirections++;
        }
        
        // si la droite n'est pas bloquée et qu'il ne vient pas de la gauche
        if (!rightBlocked && !this.direction.equals("left")) {
            directionList.add("right"); // on ajoute la direction droite aux directions possibles
            nbOfDirections++;
        }
        
        // si le haut n'est pas bloquée et qu'il ne vient pas du bas
        if (!upBlocked && !this.direction.equals("down")) {
            directionList.add("up"); // on ajoute la direction haute aux directions possibles
            nbOfDirections++;
        }
        
        // si le bas n'est pas bloquée et qu'il ne vient pas du haut
        if (!downBlocked && !this.direction.equals("up")) {
            directionList.add("down"); // on ajoute la direction basse aux directions possibles
            nbOfDirections++;
        }

        
        // on tire au sort un nombre entre 0 et le nombre de direction - 1
        // pour choisir une direction aléatoirement
        Random random = new Random();
        int nb = random.nextInt(nbOfDirections);
        
        // on retourne la direction tirée au sort
        return directionList.get(nb);    
    }
    
    /**
     * Méthode permettant de savoir si le fantôme doit traverser ou non
     * c'est-à-dire s'il se situe sur l'une des deux extrémités de la map
     * @return true s'il doit traverser, false sinon
     */
    protected boolean needToCross() {
        return this.i == 9 && (this.j == 0 || this.j == 24);
    }

    /**
     * Méthode appelée lorque le fantôme meurt
     */
    public void die() {
        this.isDead = true;
    }
    
    /**
     * Méthode appelée lorsque le fantôme vit
     */
    public void live() {
        this.isDead = false;
    }
    
    /**
     * Méthode permettant de savoir si le fantôme est mort ou vivant
     * @return true s'il est mort, false s'il est vivant
     */
    public boolean isDead() {
        return this.isDead;
    }
    
    /**
     * Méthode permettant de savoir si le fantôme est vulnérable ou non
     * @return true s'il est vulnérable, false sinon
     */
    public boolean isVulnerable() {
        return this.isVulnerable;
    }
        
    public void isImmobilize() {
        this.isImmobilize = true;
    }
    
    
    
    /**
     * Méthode abstraite permettant d'obtenir le nom du fantôme
     * @return le nom du fantôme (Clyde, Inky, ...)
     */
    public abstract String getGhostName();
    
    public abstract String getOrigineSprite();
    
    @Override public void collideEffect(GameItem gi) {}
    
    @Override public void evolve(long l) {
        
        // si le fantôme et le joeur se rentre dedans
        if (this.getMiddleX() == game.getPlayer().getMiddleX() 
                && this.getMiddleY() == game.getPlayer().getMiddleY()) {
            
            // si le fantôme est mort (il retourne au spawn) et ce n'est pas 
            // considéré comme une collision
            if (!this.isDead && !this.isImmobilize) {
                collisionWithPacman = true; 
                time = 0;
            }
        }
        
        if (this.collisionWithPacman) {
            game.getPlayer().collideWithGhost(this, time);
            time ++;
        }


        if (this.isVulnerable) { // si le fantôme est vulnérable
            Ghost.speed = 20;    // il avance plus lentement
        } else {
            Ghost.speed = 10;
        }
    }
    
    @Override 
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.enterHasBeenPressed = true;
        }
    }
    
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {}
    
    @Override
    public String getItemType() {
        return "Ghost";
    }
}
package pacman;

import stats.Score;
import stats.Life;
import iut.BoxGameItem;
import iut.Game;
import iut.GameItem;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import square.Square;

/**
 * Classe représentant le player (pacman)
 * @author Jorick
 */
public class Player extends BoxGameItem implements KeyListener {

    // Coordonnées du pacman (sur quelle case il est)
    // initialisées à (9,2) dans le constructeur, c'est la position 
    // du pacman au démarrage. Elle est définie dans le main
    // (9,2) = (252/28, 56/28) car une case = 28 pixels de côté
    // /!\ i = lignes = y
    // /!\ j = colonnes = x
    private int i;
    private int j;

    // Ces booleens servent à savoir si les cases autour du pacman sont pleines
    // (gestion des colisions)
    private boolean leftBlocked = false;
    private boolean downBlocked = false;
    private boolean rightBlocked = false;
    private boolean upBlocked = false;

    /**
     * Attribut permettant de changer l'image du pacman 1 fois sur 2 pour avoir
     * l'effet de la bouche qui se ferme
     */
    private boolean isPacmanFull = false;

    /**
     * La direction est initialisée à droite car le pacman part à droite au
     * démarrage
     */
    private String direction = "right";

    /**
     * Attribut prenant le nom du fichier .png du pacman suivant sa direction
     */
    private String pacmanSpriteName = "";

    /**
     * Compteur pour limiter la vitesse dans le evolve on utilise un byte car
     * codé sur 8 bits => modulo calculé sur des plus petites valeurs qu'avec un
     * int (complexité améliorée ?)
     */
    private static byte count = 0;

    /**
     * Attribut permettant de récupérer la map pour effectuer les tests de
     * position du player
     */
    private Square[][] squares;

    /**
     * Attribut permettant d'afficher le nombre de vies restantes
     */
    private final Life objLife;

    /**
     * Attribut permettant de récupérer une instance de score et le fixer
     * suivant le score du joueur
     */
    private final Score objScore;
    
    /**
     * Variable représentant le score du jour
     * Ce score est passé à l'instance de Score pour pouvoir être mis à jour
     * dans l'affichage
     */
    private int score = 0;

    /**
     * Constance représentant la vitesse de pacman
     * /!\ plus la valeur est grande, moins la vitesse est élevée
     */
    private static final int SPEED = 10;
    
    /**
     * Constructeur du joueur
     * @param g = le jeu
     * @param x = position de départ en abscisse
     * @param y = position de départ en ordonnée
     */
    public Player(Game g, int x, int y) {
        super(g, "Avance/pacmanright", x, y);

        // voir attributs pour explication
        this.i = y / 28;
        this.j = x / 28;

        // au départ, on a 2 vies
        this.objLife = new Life(g, "Lives/2", 58, 540);
        g.addItem(this.objLife);

        // initialisation d'un object Score 
        this.objScore = new Score(g, "Score/0", 0, 0);
    }

    @Override
    public void evolve(long l) {
        
        // si pacman passe sur une case contenant un petit point ou un gros, 
        // alors on change la case et on incrémente le score
        if (("emptyWithPoint".equals(squares[i][j].getItemType()))
                || ("emptyWithBigPoint".equals(squares[i][j].getItemType()))) {
            changeSquare();
        }

        // si pacman est sur la case à l'extrême gauche ou à l'extrême droite
        // il faut le déplacer de l'autre côté
        if (this.i == 9 && (this.j == 0 || this.j == 24)) {
            crossTheMap(direction);
        }
            
        // est vrai 1 fois sur SPEED pour limiter la vitesse du pacman
        if (count % SPEED == 0) {
            
            // on regarde si les cases autour du perso sont pleines
            sideBlocked();

            switch (direction) {
                case "left":
                    this.pacmanSpriteName = "Avance/pacmanleft";
                    if (!leftBlocked) {
                        this.moveXY(-28, 0); // une case vers la gauche
                        this.j -= 1;
                    }
                    break;
                case "right":
                    this.pacmanSpriteName = "Avance/pacmanright";
                    if (!rightBlocked) {
                        this.moveXY(28, 0); // une case vers la droite
                        this.j += 1;
                    }
                    break;
                case "up":
                    this.pacmanSpriteName = "Avance/pacmanup";
                    if (!upBlocked) {
                        this.moveXY(0, -28); // une case vers le haut
                        this.i -= 1;
                    }
                    break;
                case "down":
                    this.pacmanSpriteName = "Avance/pacmandown";
                    if (!downBlocked) {
                        this.moveXY(0, 28); // une case vers le bas
                        this.i += 1;
                    }
                    break;
            }

            // on réinitialise les différentes direction
            leftBlocked = false;
            downBlocked = false;
            rightBlocked = false;
            upBlocked = false;

            // on change l'image du pacman
            changePacmanSprite(pacmanSpriteName);
        }
        count++;
    }

    /**
     * Méthode permettant de fixer les booleens si les cases autour du perso
     * sont pleines (murs)
     */
    private void sideBlocked() {

        // on enlève le cas où j vaut 0 ou 24 car on teste les j-1 donc si j==0, on teste j-1 et cette valeur n'existe pas donc erreur
        // pas besoin de tester i car l'utilisateur ne peut jamais aller sur les bords extrêmes du i contrairement au j (pour traverser)
        if (this.j != 0 && this.j != 24) {

            // si le type de la case au-dessus est full
            if ("full".equals(squares[i - 1][j].getItemType())) {
                upBlocked = true; // le haut est bloqué
            }

            // si le type de la case en-dessous est full
            if ("full".equals(squares[i + 1][j].getItemType())) {
                downBlocked = true; // le bas est bloqué
            }

            // si le type de la case à gauche est full
            if ("full".equals(squares[i][j - 1].getItemType())) {
                leftBlocked = true; // la gauche est bloquée
            }

            // si le type de la case à droite est full
            if ("full".equals(squares[i][j + 1].getItemType())) {
                rightBlocked = true; // la droite est bloqué
            }
        }
    }

    /**
     * Méthode permettant de changer l'image du pacman 
     * On change l'image 1 fois sur 2 avec le booléen
     * @param pacmanSpriteName = le nom du fichier selon la direction
     */
    private void changePacmanSprite(String pacmanSpriteName) {

        if (!isPacmanFull) {
            changeSprite("Avance/pacmanfull");
            isPacmanFull = true;
        } else {
            changeSprite(pacmanSpriteName);
            isPacmanFull = false;
        }
    }

    /**
     * Appelée lorqu'il faut changer de côté sur la map (doite ou gauche)
     * @param direction = la direction du pacman
     */
    private void crossTheMap(String direction) {

        // pour qu'il ne puisse pas rentrer dans des blocs pleins quand
        // le player fait des "gauches-droites" et qu'il 
        // tente de monter ou descendre
        upBlocked = true;
        downBlocked = true;

        if ("left".equals(direction)) {   // il passe de gauche à droite
            j = 24;                       // la case sur laquelle il va repartir
            this.getPosition().setX(672); // on fixe la nouvelle position (24 * 28)
        } else {                          // il passe de droite à gauche
            j = 0;                        // la case sur laquelle il va repartir
            this.getPosition().setX(0);   // on fixe la nouvelle position (0 * 28)
        }
    }

    /**
     * Mettant permettant de changer une case avec point en case vide
     */
    private void changeSquare() {
        // on remplace la case par une case vide
        this.squares[i][j].changeSprite("Squares/empty");

        // on regarde quel type était la case
        switch (squares[i][j].getItemType()) {
            case "emptyWithPoint": // case avec petit point
                this.score += 10; // on incrémente le score de 10
                break;
            case "emptyWithBigPoint": // case avec gros point
                this.score += 50; // on incrémente le score de 50
                break;
        }
        
        // on fixe le type de la nouvelle case à vide
        this.squares[i][j].setItemType("empty");

        // on met à jour le score
        this.objScore.setScore(score);
    }

    // Amélioration :
    // faire en sorte que le déplacement soit le même que dans la version originale
    // càd ne pouvoir changer de direction que si celle-ci ne contient pas une case pleine
    // actuellement : possibilité de se bloquer contre une paroi volontairement ou non
    
    /**
     * On utilise keyReleased et pas keyPressed car si l'utilisateur reste
     * appuyé sur une flèche lorsqu'il est bloqué, les tests ne vont plus être
     * vrais au bout d'un moment (keyPressed sûrement répété + de fois que
     * evolve) et le pacman va avancer dans les cases pleines (seule solution
     * trouvée pour l'instant)
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {

        // suivant la flèche du clavier appuyée, on change la direction
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                direction = "left";
                break;
            case KeyEvent.VK_RIGHT:
                direction = "right";
                break;
            case KeyEvent.VK_UP:
                direction = "up";
                break;
            case KeyEvent.VK_DOWN:
                direction = "down";
                break;
        }
    }

    public void setSquares(Square[][] squares) {
        this.squares = squares;
    }

    @Override
    public boolean isCollide(GameItem gi) {
        return false;
    }

    @Override
    public void collideEffect(GameItem gi) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public String getItemType() {
        return "player";
    }
}

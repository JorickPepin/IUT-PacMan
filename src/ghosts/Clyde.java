package ghosts;

import graph.Graph;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import pacman.Map;
import pacman.PacMan;
import square.Square;

/**
 * Clyde est le fantôme orange 
 * Ses déplacements sont les suivants : 
 * "Clyde feint l'indifférence. De temps en temps, il choisit une direction au hasard."
 *
 * @author Jorick
 */
public class Clyde extends Ghost {
 
    // Coordonnées du fantôme (sur quelle case il est)
    // initialisées à (8,13) dans le constructeur, c'est la position 
    // du fantôme au démarrage. Elle est définie dans le main
    // (8,13) = (224/28, 364/28) car une case = 28 pixels de côté
    // /!\ i = lignes = y
    // /!\ j = colonnes = x
    private int i;
    private int j;

    // Ces booleens servent à savoir si les cases autour du fantôme sont pleines
    // (gestion des colisions)
    private boolean leftBlocked = false;
    private boolean downBlocked = false;
    private boolean rightBlocked = false;
    private boolean upBlocked = false;

    /**
     * Attribut permettant de récupérer la map pour effectuer les tests de
     * position du fantôme
     */
    private Square[][] squares;

    /**
     * Variable représentant la direction dans laquelle se déplace le fantôme
     * Elle est initialisée en haut car il part en haut au démarrage
     */
    private String direction = "up";
    
    /**
     * Compteur pour limiter la vitesse dans le evolve 
     * On utilise un byte car codé sur 8 bits donc le modulo est calculé sur 
     * des plus petites valeurs qu'avec un int (complexité améliorée ?)
     */
    private static byte count = 0;
  
    private static int time = 0;
    /**
     * Constance représentant la vitesse du fantôme
     * /!\ plus la valeur est grande, moins la vitesse est élevée
     */
    private static final int SPEED = 30; //10
    
    private final PacMan game;
    private final Map map;

    /**
     * Constructeur du fantôme Clyde
     * @param game = le jeu
     * @param map = la map
     */
    public Clyde(PacMan game, Map map) {
        // (position de départ sur les cases (x=13, y=8) = (13 * 28, 8 * 28) en pixels)
        super(game, "images/Ghosts/clydeUp", 364, 224);
        
        // voir attributs pour explication
        this.i = 8; // 224 / 28
        this.j = 13; // 364 / 28
        
        this.game = game;
        this.map = map;
        this.squares = map.getSquares();

        game.addItem(this);
    }

    @Override
    public void evolve(long l) {

        if (this.isDead()) {

            // s'il est arrivé à destination
            if (this.i == 8 && this.j == 13) {
                System.out.println("il revit");
                this.live();
                this.becomeVulnerable(false);
                this.direction = "up";
                changeSprite("images/Ghosts/clydeUp");

            } else {
                
                changeSprite("images/Ghosts/deadGhostUp");

                returnToOrigne(this);
                
//                List<Integer> chemin = findTheShortestPathToOrigine();
//
//                this.getPosition().setX(map.getNodeToSquare().get(chemin.get(1)).getJ() * 28);
//                this.getPosition().setY(map.getNodeToSquare().get(chemin.get(1)).getI() * 28);
//
//                this.i = map.getNodeToSquare().get(chemin.get(1)).getI();
//                this.j = map.getNodeToSquare().get(chemin.get(1)).getJ();
            }
        }

        // si le fantôme est sur la case à l'extrême gauche ou à l'extrême droite
        // il faut le déplacer de l'autre côté
        if (this.i == 9 && (this.j == 0 || this.j == 24)) {
            crossTheMap(direction);
        }

        // test permettant de limiter le nombre de répétition pour limiter la vitesse
        if (count % SPEED == 0) {

            if (!this.isDead()) {

                // on regarde si les cases autour du perso sont pleines 
                sideBlocked();

                // on définit la direction dans laquelle il doit se diriger
                direction = defineDirection();

                // si le fantôme est vulnérable
                if (this.isVulnerable()) {
                    setTheVunerableGhost(time);
                    time++;
                }

                // selon la direction, on change le sprite et on le fait avancer
                switch (direction) {
                    case "left":
                        if (!this.isVulnerable()) {
                            changeSprite("images/Ghosts/clydeLeft");
                        }
                        this.moveXY(-28, 0); // une case vers la gauche
                        this.j -= 1;
                        break;
                    case "right":
                        if (!this.isVulnerable()) {
                            changeSprite("images/Ghosts/clydeRight");
                        }
                        this.moveXY(28, 0); // une case vers la droite
                        this.j += 1;
                        break;
                    case "up":
                        if (!this.isVulnerable()) {
                            changeSprite("images/Ghosts/clydeUp");
                        }
                        this.moveXY(0, -28); // une case vers le haut
                        this.i -= 1;
                        break;
                    case "down":
                        if (!this.isVulnerable()) {
                            changeSprite("images/Ghosts/clydeDown");
                        }
                        this.moveXY(0, 28); // une case vers le bas
                        this.i += 1;
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
    
    private List<Integer> findTheShortestPathToOrigine() {
        int origine = map.getSquareToNode().get(squares[1][1]);
        
        List<Integer> chemin = map.getGraph().shortestPath(map.getSquareToNode().get(squares[i][j]), origine);
        
        return chemin;
    }
    
    /**
     * Méthode permettant de définir la direction de manière aléatoire
     * @return la direction dans laquelle il doit se diriger
     */
    private String defineDirection() {
        
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
     * Méthode permettant de fixer les booleens si les cases autour du fantôme
     * sont pleines (murs)
     */
    private void sideBlocked() {

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
     * Appelée lorqu'il faut changer de côté sur la map (doite ou gauche)
     * @param direction = la direction du pacman
     */
    private void crossTheMap(String direction) {

        // pour qu'il ne puisse pas rentrer dans des blocs pleins du haut et 
        // du bas quand il traverse
        upBlocked = true;
        downBlocked = true;

        if ("left".equals(direction)) {   // il passe de gauche à droite
            j = 24;                       // la case sur laquelle il va repartir
            this.getPosition().setX(672); // 24 * 28
        } else {                          // il passe de droite à gauche
            j = 0;                        // la case sur laquelle il va repartir
            this.getPosition().setX(0);   // 0 * 28
        }
    }

    public void setSquares(Square[][] squares) {
        this.squares = squares;
    }

    public void setTime(int time) {
        Clyde.time = time;
    }

    @Override
    public int getI() {
        return this.i;
    }

    @Override
    public int getJ() {
        return this.j;
    }

    @Override
    public void setI(int i) {
        this.i = i;
    }

    @Override
    public void setJ(int j) {
        this.j = j;
    }
    
    @Override
    public void initGhost() {
        this.i = 8;
        this.j = 13;
        
        this.getPosition().setX(13 * 28);
        this.getPosition().setY(8 * 28);
        
        this.direction = "up";
    }

    @Override
    public String getGhostName() {
        return "Clyde";
    }
    
    
}

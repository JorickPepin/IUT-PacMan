package pacman;

import ghosts.Ghost;
import iut.Audio;
import stats.Score;
import stats.Life;
import iut.BoxGameItem;
import iut.GameItem;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import square.Square;

/**
 * Classe représentant le player (pacman)
 * @author Jorick
 */
public class Player extends BoxGameItem implements KeyListener {

    // Coordonnées du pacman (sur quelle case il est)
    // initialisées à (12,14) dans le constructeur, c'est la position 
    // du pacman au démarrage. Elle est définie dans le main
    // (12,14) = (336/28, 392/28) car une case = 28 pixels de côté
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
     * Constance représentant la vitesse de pacman
     * /!\ plus la valeur est grande, moins la vitesse est élevée
     */
    private static final int SPEED = 10;
    
    private final PacMan game;
    
    /**
     * Attribut permettant de savoir si l'utilisateur a appuyé sur entrée
     */
    private boolean enterHasBeenPressed = false;
    
    private boolean isImmobilize = false;
    
    private boolean exit = false;
    
    /**
     * Constructeur du joueur
     * @param pacman
     * @param x = position de départ en abscisse
     * @param y = position de départ en ordonnée
     */
    public Player(PacMan pacman, int x, int y) {
        super(pacman, "images/Pacman/pacmanright", x, y);

        // voir attributs pour explication
        this.i = y / 28;
        this.j = x / 28;

        this.game = pacman;
        
        // au départ, on a 2 vies
        this.objLife = new Life(game, "images/Lives/2", 73, 541);
        game.addItem(this.objLife);

        // initialisation d'un object Score 
        this.objScore = new Score(game, "images/Score/0", 0, 0);
    }
    
    @Override
    public void evolve(long l) {

        // si pacman passe sur une case contenant une pac-gomme ou une super pac-gomme, 
        // alors on change la case et on incrémente le score
        if (("emptyWithPoint".equals(squares[i][j].getItemType()))
                || ("emptyWithBigPoint".equals(squares[i][j].getItemType()))) {
            changeSquare();
        }
      
        // si le joueur a appuyé sur "entrée" on fait avancer le pacman
        if (enterHasBeenPressed && !isImmobilize) {

            if (exit) {
                System.exit(0);
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
                        this.pacmanSpriteName = "images/Pacman/pacmanleft";
                        if (!leftBlocked) {
                            this.moveXY(-28, 0); // une case vers la gauche
                            this.j -= 1;
                        }
                        break;
                    case "right":
                        this.pacmanSpriteName = "images/Pacman/pacmanright";
                        if (!rightBlocked) {
                            this.moveXY(28, 0); // une case vers la droite
                            this.j += 1;
                        }
                        break;
                    case "up":
                        this.pacmanSpriteName = "images/Pacman/pacmanup";
                        if (!upBlocked) {
                            this.moveXY(0, -28); // une case vers le haut
                            this.i -= 1;
                        }
                        break;
                    case "down":
                        this.pacmanSpriteName = "images/Pacman/pacmandown";
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

                this.objScore.cleanDeltaItemsList();
            }
            count++;
        }
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
            changeSprite("images/Pacman/pacmanfull");
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
        
        // lance le fichier audio
        new Audio("sons/pacman_chomp").start();
        
        // on remplace la case par une case vide
        this.squares[i][j].changeSprite("images/Squares/empty");

        // on regarde de quel type était la case
        switch (squares[i][j].getItemType()) {
            case "emptyWithPoint": // case avec petit point
                this.objScore.addPoints(10, this.i, this.j, this.direction); // on incrémente le score de 10
                break;
            case "emptyWithBigPoint": // case avec gros point
                this.objScore.addPoints(50, this.i, this.j, this.direction); // on incrémente le score de 50
                game.getGhostsList().forEach((g) -> {
                    g.becomeVulnerable(true); // les fantômes deviennent vulnérables
                });
                break;
        }

        // on fixe le type de la nouvelle case à vide
        this.squares[i][j].setItemType("empty");
    }

    /**
     * Méthode appelée lorqu'une collision a lieu avec un fantôme
     * @param g = le fantôme
     * @param time = le temps depuis le début de la collision
     */
    public void collideWithGhost(Ghost g, int time) {
        
        if (g.isVulnerable()) { // si le fantôme est vulnérable
            collideWithVulnerableGhost(g);
            
        } else if (this.objLife.getNbLives() >= 0) { // si le fantôme n'est pas vulnérable et que le joueur a encore des vies    
            collideWithDangerGhost(time);
            
        } else { // si le fantôme n'est pas vulnérable et que le joueur n'a plus de vie
            game.lost(); // le joueur a perdu
        }
    }
    
    /**
     * Méthode appelée lorque le joueur entre en collision avec un fantôme vulnérable
     * @param g = le fantôme
     */
    private void collideWithVulnerableGhost(Ghost g) {

        // le fantôme meurt
        g.die();

        int score = 0;
        
        // suivant le nombre de fantômes restant, le nombre de points gagnés
        // n'est pas le même
        switch (game.getGhostsList().size()) {
            case 4:
                // on ajoute 200 points au joueur  
                score += 200;
                break;
            case 3:
                // on ajoute 400 points au joueur  
                score += 400;
                break;
            case 2:
                // on ajoute 800 points au joueur  
                score += 800;
                break;
            case 1:
                // on ajoute 1600 points au joueur  
                score += 1600;
                break;
        }
        
        this.objScore.addPoints(score, this.i, this.j, this.direction);
    }
    
    /**
     * Méthode appelée lorque le joueur entre en collion avec un fantôme 
     * non vulnérable (pacman meurt)
     */
    private void collideWithDangerGhost(int time) {

        // on fait des tests sur time pour avoir des évènements de manière progressive
        
        if (time == 0) {

            // pacman devient immobile
            this.isImmobilize = true;

            // les fantômes aussi
            for (Ghost g : game.getGhostsList()) {
                g.isImmobilize();
            }

            // on lance l'audio correspondant à la mort de pacman
            new Audio("sons/pacman_death").start();
        }

        if (time == 5) {
            for (Ghost g : game.getGhostsList()) {
                g.wipeOutGhost();
            }
        }

        // selon le temps écoulé, on change le sprite
        switch (time) {
            case 5:
                this.changeSprite("images/DeadPacman/1");
                break;
            case 9:
                this.changeSprite("images/DeadPacman/2");
                break;
            case 13:
                this.changeSprite("images/DeadPacman/3");
                break;
            case 17:
                this.changeSprite("images/DeadPacman/4");
                break;
            case 21:
                this.changeSprite("images/DeadPacman/5");
                break;
            case 25:
                this.changeSprite("images/DeadPacman/6");
                break;
            case 29:
                this.changeSprite("images/DeadPacman/7");
                break;
            case 33:
                this.changeSprite("images/DeadPacman/8");
                break;
            case 37:
                this.changeSprite("images/DeadPacman/9");
                break;
            case 41:
                this.changeSprite("images/DeadPacman/10");
                break;
            case 45:
                this.changeSprite("images/DeadPacman/11");
                break;
            case 49:
                this.changeSprite("images/Squares/empty");
                break;
        }

        if (time == 55) {
            // on "recommence" la partie = on réinitiliase les positions
            restartGame();
        }
    }
    
    private void restartGame() {
        
        // paramètres de départ
        this.direction = "right";
        this.changeSprite("images/Pacman/pacmanright");
        
        // on positionne le joueur à la position initiale
        this.getPosition().setX(336);
        this.j = 336 / 28;
        this.getPosition().setY(392);
        this.i = 392 / 28;
        
        // on fait de même pour les fantômes en appelant la méthode gérant ce 
        // cas dans la classe Ghost
        for(Ghost g : game.getGhostsList()) {
            g.initGhost();
        }
         
        // on affiche le message "Nouvelle chancce"
        Instruction newChance = new Instruction(game, "images/Diverse/newChance", 225, 280);
        game.addItem(newChance);
        
        // pacman n'est plus immobilisé
        this.isImmobilize = false;
        
        // l'utilisateur doit de nouveau presser entrée
        this.enterHasBeenPressed = false;
        
        // on enlève une vie au joueur
        this.objLife.removeALife();
        
        if (objLife.getNbLives() == 1) {
            endOfTheGame(newChance);
        }
    }
    
    private void endOfTheGame(Instruction instruction)  {
        game.remove(instruction);
        
        Instruction reStart = new Instruction(game, "images/Diverse/gameOver", 0, 0);
        game.addItem(reStart);
        
        exit = true;
      
    }
    
    @Override
    public void keyPressed(KeyEvent e) {                
        // on vérifie si les cases aux alentours sont bloquées
        sideBlocked();
        
        // suivant la flèche du clavier appuyée, on change la direction si
        // celle-ci n'est pas bloquée (on évite ainsi de se bloquer contre un mur)
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (!leftBlocked)
                    direction = "left";
                break;
            case KeyEvent.VK_RIGHT:
                if (!rightBlocked)
                    direction = "right";
                break;
            case KeyEvent.VK_UP:
                if (!upBlocked)
                    direction = "up";
                break;
            case KeyEvent.VK_DOWN:
                if (!downBlocked)
                    direction = "down";
                break;
        }
    }
    
    @Override public void collideEffect(GameItem gi) {}
    @Override public boolean isCollide(GameItem gi) {return false;}
    @Override public void keyTyped(KeyEvent e) {}
    
    @Override 
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.enterHasBeenPressed = true;
        }
    }
    
    public void setSquares(Square[][] squares) {
        this.squares = squares;
    }
    
    @Override
    public String getItemType() {
        return "player";
    }
}

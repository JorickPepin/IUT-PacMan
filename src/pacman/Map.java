
package pacman;

import ghosts.Ghost;
import iut.Game;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import square.Square;
import java.util.List;
import square.EmptySquare;
import square.FullSquare;

/**
 * Classe représentant la map
 * La map est un tableau à deux dimensions composé de cases de 28 pixels
 * de côté
 * @author Jorick
 */
public class Map {
    
    /**
     * La map est représentée par un tableau à 2 dimensions
     */
    private final Square[][] squares;
    
    /**
     * La map est composée de 20 lignes
     */
    private final int NB_ROWS = 20;
    
    /**
     * La map est composée de 25 colonnes
     */ 
    private final int NB_COLS = 25;
    
    private ArrayList<Ghost> ghostsList = new ArrayList();
        
    /**
     * Constructeur de la map
     * @param g = le jeu
     * @throws IOException si le fichier n'est pas trouvé
     */
    public Map(Game g) throws IOException {
        
        // initialisation du tableau avec le nombre de lignes et colonnes
        this.squares = new Square[NB_ROWS][NB_COLS];
        
        // la map est "écrite" avec un fichier .map qu'on récupère ici
        List<String> lines = Files.readAllLines(Paths.get("ressources/map.map"));
        
        // on "balaie" le fichier .map (qui est un simple fichier texte)
        // et on récupère le caractère correspond à la case
        // pour chaque caractère, la case a une fonction ou une apparence différente
        for(int i=0; i<NB_ROWS; i++) {
            for(int j=0; j<NB_COLS; j++) {
                switch (lines.get(i).charAt(j)) {
                    case '1':
                        // dans le .map, si le caractère est 1, alors la case est pleine
                        squares[i][j] = new FullSquare(g, i, j);
                        break;
                    case '2':
                        // si le caractère est 2, alors la case est vide sans point
                        squares[i][j] = new EmptySquare(g, i, j);
                        squares[i][j].setItemType("empty");
                        squares[i][j].changeSprite("images/Squares/empty");
                        break;
                    case '3':
                        // si le caractère est 3, alors la case est vide avec un gros point
                        squares[i][j] = new EmptySquare(g, i, j);
                        squares[i][j].setItemType("emptyWithBigPoint");
                        squares[i][j].changeSprite("images/Squares/emptyWithBigPoint");
                        break;
                        
                    // tous les caractères suivants n'ont pas de lien avec la case qu'ils créent
                    // ils servent à remplacer les murs par des images correspondant au jeu originel
                    // pour savoir à quelle image correspond le caractère,
                    // il faut se référer au fichier .map et aux images créées pour ce faire
                    // un caractère correspond à un type de case (angle, trait droit, ...)
                    // et donc à une image précise, c'est pour cela qu'il y en a autant
                        
                    case 'a':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullSideTop");
                        break;
                    case 'b':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullSideLeft");
                        break;
                    case 'c':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullSideBottom");
                        break;
                    case 'd':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullSideRight");
                        break;
                    case 'e':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullAngleTopRight");
                        break;
                    case 'f':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullAngleTopLeft");
                        break;
                    case 'g':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullAngleBottomLeft");
                        break;
                    case 'h':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullAngleBottomRight");
                        break;
                    case 'i':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullAngleTopRight2");
                        break;
                    case 'j':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullAngleTopLeft2");
                        break;
                    case 'k':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullAngleBottomLeft2");
                        break;
                    case 'l':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullAngleBottomRight2");
                        break;
                    case 'm':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullDoubleJoin1");
                        break;
                    case 'n':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullDoubleJoin2");
                        break;
                    case 'o':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullDoubleLines");
                        break;
                    case 'p':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullDoubleJoin3");
                        break;
                    case 'q':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullDoubleJoin4");
                        break;
                    case 'r':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullDoubleJoin5");
                        break;
                    case 's':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullDoubleJoin6");
                        break;
                    case 't':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullDoubleLines2");
                        break;
                    case 'u':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullEnd1");
                        break;
                    case 'v':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullEnd2");
                        break;
                    case 'w':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullEnd3");
                        break;
                    case 'x':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullDoubleDifferentAngles1");
                        break;
                    case 'y':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullDoubleDifferentAngles2");
                        break;
                    case 'z':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullSeparationGhosts");
                        break;
                    case '-':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullDoubleJoin7");
                        break;
                    case '_':
                        squares[i][j] = new FullSquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullDoubleJoin8");
                        break;
                    default:
                        // si le caractère est 0, alors la case est vide avec un petit point (cas général par défaut)
                        squares[i][j] = new EmptySquare(g, i, j);
                        break;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "";
    }

    public Square[][] getSquares() {
        return squares;
    }
    
    public void addGhost(Ghost ghost) {
        this.ghostsList.add(ghost); 
    }

    public ArrayList<Ghost> getGhostsList() {
        return ghostsList;
    }
}
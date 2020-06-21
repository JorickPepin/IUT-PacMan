
package pacman;

import ghosts.Ghost;
import graph.Graph;
import iut.Game;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import square.Square;
import java.util.List;
import javax.swing.JOptionPane;
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
     * Liste permettant de transformer une case en noeud
     */
    private java.util.Map<Square,Integer> squareToNode; 
    
    /**
     * Liste permettant de transformer un noeud en case
     */
    private java.util.Map<Integer,Square> nodeToSquare;
    
    /**
     * Le nombre de noeuds qui vont composer notre graphe
     */
    private int countNode = 0;
    
    /**
     * Graphe associée à la map
     * Il est composé de toutes les cases vides
     * Une case = un noeud
     */
    private final Graph graph;
    
    /**
     * Attribut contenant le nombre de gommes qu'il y a sur la map
     */
    private int gommeNumber;
    
    /**
     * Constructeur de la map
     * @param g = le jeu
     */
    public Map(Game g) {
        
        this.gommeNumber = 0;
        
        // initialisation des listes
        this.squareToNode = new HashMap<>();
        this.nodeToSquare = new HashMap<>();
        
        // initialisation du tableau avec le nombre de lignes et colonnes
        this.squares = new Square[NB_ROWS][NB_COLS];
        
        List<String> lines = null;
        
        // la map est "écrite" avec un fichier .map qu'on récupère ici
        try {
            lines = Files.readAllLines(Paths.get("ressources/map.map"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Fichier .map non trouvé", "Fichier manquant", JOptionPane.ERROR_MESSAGE);
        }
        
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
                        
                        // case vide donc ajoutée au graphe
                        nodeToSquare.put(countNode, squares[i][j]);
                        squareToNode.put(squares[i][j], countNode);
                        
                        // une case = un noeud dans le graphe
                        countNode ++;
                        break;
                    case '3':
                        // si le caractère est 3, alors la case est vide avec un gros point
                        squares[i][j] = new EmptySquare(g, i, j);
                        squares[i][j].setItemType("emptyWithBigPoint");
                        squares[i][j].changeSprite("images/Squares/emptyWithBigPoint");
                        
                        // case vide donc ajoutée au graphe
                        nodeToSquare.put(countNode, squares[i][j]);
                        squareToNode.put(squares[i][j], countNode);
                        
                        // une case = un noeud dans le graphe
                        countNode ++;
                        break;
                        
                    // tous les caractères suivants n'ont pas de lien avec la case qu'ils créent
                    // ils servent à remplacer les murs par des images correspondant au jeu originel
                    // pour savoir à quelle image correspond le caractère,
                    // il faut se référer au fichier .map et aux images créées dans les ressources
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
                        // les 3 cases contenant les fantômes au départ sont considérées comme vide
                        // pour qu'ils puissent y retourner
                        squares[i][j] = new EmptySquare(g, i, j);
                        squares[i][j].changeSprite("images/Squares/fullSeparationGhosts");
                        
                        // on met quand même le type en full pour que l'utilisateur ne puisse pas y aller
                        // et que les fantômes ne se déplacent pas dedans
                        squares[i][j].setItemType("full");
                        
                        // case vide donc ajoutée au graphe
                        nodeToSquare.put(countNode, squares[i][j]);
                        squareToNode.put(squares[i][j], countNode);
                        
                        // une case = un noeud dans le graphe
                        countNode ++;
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
                        // si le caractère est 0, alors la case est vide avec une gomme (cas général par défaut)
                        squares[i][j] = new EmptySquare(g, i, j);
                        
                        gommeNumber += 1; // on ajoute une gomme au nombre de gommes
                        
                        // case vide donc ajoutée au graphe
                        nodeToSquare.put(countNode, squares[i][j]);
                        squareToNode.put(squares[i][j], countNode);
                        
                        // une case = un noeud dans le graphe
                        countNode ++;
                        break;
                }
            }
        }
        
        // on crée le graphe avec le nombre de noeuds qu'on a obtenu
        graph = new Graph(countNode);

        // on teste les cases qui sont voisines
        // si les deux cases sont vides, alors on crée une arête entre elles
        for(int i=0; i<NB_ROWS-1; i++) {
            for(int j=0; j<NB_COLS-1; j++) {
                
                if (squares[i][j].getSimpleType().equals("empty")) {
                    
                    if (squares[i+1][j].getSimpleType().equals("empty")) {
                        graph.addEdge(squareToNode.get(squares[i][j]), squareToNode.get(squares[i+1][j]));
                    }
                    
                    if (squares[i][j+1].getSimpleType().equals("empty")) {
                        graph.addEdge(squareToNode.get(squares[i][j]), squareToNode.get(squares[i][j+1]));
                    }               
                } 
            }
        }
        
        // on applique l'algorithme de Floyd-Warshall à notre graphe
        graph.floydWarshall();
    }
      
    @Override
    public String toString() {return "";}

    public Square[][] getSquares() {
        return squares;
    }
    
    public Graph getGraph() {
        return graph;
    }

    public java.util.Map<Integer, Square> getNodeToSquare() {
        return nodeToSquare;
    }

    public java.util.Map<Square, Integer> getSquareToNode() {
        return squareToNode;
    }

    public int getGommeNumber() {
        return gommeNumber;
    }

    public void setGommeNumber(int gommeNumber) {
        this.gommeNumber = gommeNumber;
    }
}
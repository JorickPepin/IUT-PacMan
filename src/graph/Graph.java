
package graph;

import java.util.LinkedList;
import java.util.List;

/**
 * Implementation de l'algorithme de Floyd-Warshall
 * pour récupérer le plus court chemin à l'aide d'un graphe
 * @author Jorick
 */
public class Graph {
    
    /**
     * Matrice d'adjacence. La case (i, j) vaut 1 s'il y a une arête
     * de i à j, 0 sinon
     */
    private final int[][] matrix;
    private final int n;
    
    /**
     * Distance infinie pour le calcul des destinations inatteignables
     * N'utilise pas Integer.MAX_VALUE pour éviter les débordements d'entiers
     */
    private final int MAX_DIST = 999999;
    
    /**
     * Matrice qui stocke la longueur du plus court chemin du sommet i au sommet j
     * Une case vaut MAX_DIST si ce chemin n'existe pas
     */
    private int[][] pcc;
    
    /**
     * Matrice qui stocke les prédecesseurs pour calculer le plus court chemin
     * La case (i, j) contient le prédecesseur de j dans le chemin de i à j
     */
    private int[][] preds;
    
    /**
     * Constructeur de la classe Graphe
     * @param n = le nombre de sommets dans le graphe
     */
    public Graph(int n) {
        this.n = n;
        this.matrix = new int[n][n];
        
        this.pcc = null;
        this.preds = null;
    }
    
    /**
     * Permet d'ajouter une arête (non orientée) entre i et j
     * @param i = premier sommet
     * @param j = deuxième sommet
     */
    public void addEdge(int i, int j) {
        matrix[i][j] = 1;
        matrix[j][i] = 1;
    }
    
    /**
     * Calcule les plus courts chemins entre toutes les paires de sommets
     * en temps O(n^3)
     * 
     * @return pcc, matrice des longueurs des plus courts chemins
     */
    public int[][] floydWarshall() {
        pcc = new int[n][n];
        preds = new int[n][n];
        
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                preds[i][j] = i;
                
                if (i == j) {
                    pcc[i][j] = 0;
                } else {
                    if (matrix[i][j] == 1) {
                        pcc[i][j] = 1;
                    } else {
                        pcc[i][j] = MAX_DIST;
                    }
                }
            }
        }

        for (int k = 0; k < n; ++k) {
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (pcc[i][k] + pcc[k][j] < pcc[i][j]) {
                        pcc[i][j] = pcc[i][k] + pcc[k][j];
                        preds[i][j] = preds[k][j];
                    }
                }
            }
        }
        
        return pcc;
    }
    
    // FONCTION DE DEBUG
//    private static void show(int[][] m) {
//        for (int i = 0; i < m.length; ++i) {
//            for (int j = 0; j < m[i].length; ++j) {
//                System.out.print(m[i][j] + "\t");
//            }
//            
//            System.out.println("");
//        }
//    }
    
    /**
     * Reconstruit le chemin de i à j à l'aide de la matrice des prédecesseurs
     * @param i = sommet source
     * @param j = sommet destination
     * @return  une liste d'entiers avec les numéros des sommets de i à j
     */
    public List<Integer> shortestPath(int i, int j) {
        if (pcc == null || preds == null)
            throw new RuntimeException("Erreur. Floyd-Warshall doit être calculé avant d'appeler cette méthode.");
        
        // show(preds);
        List<Integer> path = new LinkedList<>();
        int viaVertex = j;
        
        while (i != viaVertex) {
            path.add(0, viaVertex);
            viaVertex = preds[i][viaVertex];
        }
        
        path.add(0, i);
        return path;
    }
    
    /**
     * Renvoie la longueur du plus court chemin entre les sommets i et j
     * @param i
     * @param j
     * @return la longueur du plus court chemin entre les sommets i et j
     */
    public int shortestDistance(int i, int j) {
        if (pcc == null || preds == null)
            throw new RuntimeException("Erreur. Floyd-Warshall doit être calculé avant d'appeler cette méthode.");
        
        return pcc[i][j];
    }
}

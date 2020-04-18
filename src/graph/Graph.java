
package graph;

import java.util.LinkedList;
import java.util.List;

/**
 * Implementation de l'algorithme de Floyd-Warshall
 * pour récupérer le plus court chemin à l'aide d'un graphe
 * @author Jorick
 */
public class Graph {
    
    private final int[][] matrix;
    private final int n;
    
    // Store the result of the algorithm
    private final int MAX_DIST = 999999;
    private int[][] pcc;
    private int[][] preds;
    
    public Graph(int n) {
        this.n = n;
        this.matrix = new int[n][n];
        
        this.pcc = null;
        this.preds = null;
    }
    
    public void addEdge(int i, int j) {
        matrix[i][j] = 1;
        matrix[j][i] = 1;
    }
    
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
    
//    private static void show(int[][] m) {
//        for (int i = 0; i < m.length; ++i) {
//            for (int j = 0; j < m[i].length; ++j) {
//                System.out.print(m[i][j] + "\t");
//            }
//            
//            System.out.println("");
//        }
//    }
    
    public List<Integer> shortestPath(int i, int j) {
        if (pcc == null || preds == null)
            throw new RuntimeException("Please call Floyd-Warshall before this method.");
        
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
    
    public int shortestDistance(int i, int j) {
        if (pcc == null || preds == null)
            throw new RuntimeException("Please call Floyd-Warshall before this method.");
        
        return pcc[i][j];
    }
}

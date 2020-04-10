/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import iut.Game;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import square.Square;
import java.util.List;
import square.EmptySquare;
import square.FullSquare;

/**
 *
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
    private final int nbRows = 20;
    
    /**
     * La map est composée de 25 colonnes
     */ 
    private final int nbCols = 25;
    
    /**
     * Constructeur de la map
     * @param g
     * @throws IOException 
     */
    public Map(Game g) throws IOException {
        
        // initialisation du tableau avec le nombre de lignes et colonnes
        this.squares = new Square[nbRows][nbCols];
        
        // la map est "écrite" avec un fichier .map qu'on récupère ici
        List<String> lines = Files.readAllLines(Paths.get("ressources/map.map"));
        
        for(int i=0; i<nbRows; i++) {
            for(int j=0; j<nbCols; j++) {
                if (lines.get(i).charAt(j) == '1') {     // dans le .map, si le caractère est 1, alors la case est pleine
                   squares[i][j] = new FullSquare(g, i, j); 
                } else {                                 // dans le .map, si le caractère est 0, alors la case est vide
                    squares[i][j] = new EmptySquare(g, i, j); 
                }
                
            }
        }
    }

    /**
     * Permet de dessiner la map dans la console 
     * (voir Main)
     * E = empty / F = full
     * @return 
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < nbRows; ++i) {
            for (int j = 0; j < nbCols; ++j) {
                sb.append(squares[i][j]);
            }
            
            sb.append("\n");
        }
        
        return sb.toString();
    }

    public Square[][] getSquares() {
        return squares;
    }
    
    
}
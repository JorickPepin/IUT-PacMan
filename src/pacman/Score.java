
package pacman;

import iut.Game;
import iut.GameItem;
import static java.lang.String.valueOf;

/**
 *
 * @author Jorick
 */
public class Score extends GameItem {

    private final Game g;

    /**
     * Tableau de String contenant les noms des fichiers images représentant 
     * les chiffres afin d'afficher le score
     */
    private static String[] filesNames;
    
    /**
     * Tableau de Score contenant les différents items composant le score
     * 1 instance de type Score = 1 item
     * ex : 820 = 3 items donc 3 instances donc listScoreItems.lenght() = 3
     */
    private Score[] listScoreItems;
    
    public Score(Game g, String name, int x, int y) {
        super(g, name, x, y);
        this.g = g;
        
        // ce test permet de remplir le tableau avec le nom des fichiers qu'une
        // seule fois (quand on ajoute le label du score)
        if ("Score/labelScore".equals(name)) {
            fillFilesNames();
        }
        
        // initialisation de la liste
        this.listScoreItems = new Score[1];
    }
    
    /**
     * Méthode permettant de mettre à jour le score en haut à gauche
     * @param score = le score du joueur
     */
    public void setScore(int score) {
        // variable représentant l'abscisse des caractères 
        int x = 63;
        
        // variable représentant l'index des éléments du tableau listScoreItems
        int scoreItem = 0;
        
        // on transforme le score en chaîne de caractères pour pouvoir "balayer" dedans (= récupérer chaque chiffre)
        String s = valueOf(score);
        
        // on "nettoie" pour enlever le score présent afin de mettre le nouveau
        cleanScoreItemsList();       
        
        // la liste dépend de la taille du score
        // si le score est 20, la liste contient 2 éléments
        // si le score est 200, la liste contient 3 éléments
        listScoreItems = new Score[s.length()];
        
        // on "balaie" dans le score et récupère chaque chiffre
        for (char c : s.toCharArray()) {
            // dans notre liste, on ajoute le nouveau chiffre
            // c - '0' pour récupérer la valeur entière du caractère (ex : '9' - '0' = 9)
            // pour c=9, on ajoute le sprite filesNames[9] càd Score/9
            listScoreItems[scoreItem] = new Score(g, filesNames[c - '0'], x, 8);
            g.addItem(listScoreItems[scoreItem]);

            // on incrémente l'abscisse pour que le chiffre d'après soit décalé
            x += 8;
            
            scoreItem++;
        }
    }

    /**
     * Méthode permettant de remplir le tableau contenant le nom des fichiers
     * images représentant les chiffres
     */
    private static void fillFilesNames() {
        
        // notre tableau est un tableau de String de 10 valeurs (0 .. 9)
        filesNames = new String[10];

        // pour i allant de 0 à 9, on ajoute i au préfixe Score/ (Score/[i])
        for (int i = 0; i < 10; i++) {
            String fileName = "Score/";
            fileName += valueOf(i);

            // ex : filesNames[2] = "Score/2";
            filesNames[i] = fileName;
        }
    }

    /**
     * Méthode permettant d'enlever le score existant en haut à gauche pour
     * mettre le nouveau
     */
    private void cleanScoreItemsList() {
        
        for (Score ScoreItem : this.listScoreItems) {
            g.remove(ScoreItem);
        }
    }
    
    @Override public boolean isCollide(GameItem gi) {return false;}
    @Override public void collideEffect(GameItem gi) {}
    @Override public void evolve(long l) {}
    
    @Override
    public String getItemType() {
        return "score";
    }
}


package stats;

import iut.GameItem;
import static java.lang.String.valueOf;
import pacman.PacMan;

/**
 * Classe contenant la gestion du score du joueur
 * @author Jorick
 */
public class Score extends GameItem {

    private final PacMan game;

    /**
     * Compteur pour limiter la vitesse dans le evolve on utilise un byte car
     * codé sur 8 bits => modulo calculé sur des plus petites valeurs qu'avec un
     * int (complexité améliorée ?)
     */
    private static byte count = 0;
    
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
    
    /**
     * Ce tableau contient les éléments à afficher pour le delta (combien le 
     * joueur à marquer de points (ex : +10 pour un point normal)
     */
    private Score[] listDeltaItems;
    
    /**
     * Attribut représentant le score total du joueur
     */
    private int score;
    
    public Score(PacMan g, String name, int x, int y) {
        super(g, name, x, y);
        this.game = g;
        
        // ce test permet de remplir le tableau avec le nom des fichiers qu'une
        // seule fois (quand on ajoute le label du score)
        if ("images/Score/labelScore".equals(name)) {
            fillFilesNames();
        }
        
        // initialisation des listes
        this.listScoreItems = new Score[0];
        this.listDeltaItems = new Score[0];
    }
    
    /**
     * Méthode permettant de mettre à jour le score en haut à gauche
     * et d'appeler la méthode permettant d'afficher le nombre de points qu'a
     * gagnés le joueur à côté de lui (d'où les paramètres)
     * @param nbPoints = le nombre de points qu'a marqués le joueur
     * @param i = la position du joueur
     * @param j = la position du joueur
     * @param direction = la direction du joueur
     */
    public void addPoints(int nbPoints, int i, int j, String direction) {
         
        // on enlève le +[nbPoints] à côté du joueur
        cleanDeltaItemsList();
        
        // on "nettoie" pour enlever le score présent afin de mettre le nouveau
        cleanScoreItemsList(); 
        
        // le score vaut le score actuel + le nombre de points obtenus lors
        // de la dernière action
        this.score += nbPoints;
        
        // variable représentant l'abscisse des caractères 
        int x = 78;
        
        // variable représentant l'index des éléments du tableau listScoreItems
        int scoreItem = 0;
        
        // on transforme le score en chaîne de caractères pour pouvoir "balayer" dedans (= récupérer chaque chiffre)
        String s = valueOf(score);
        
        // la liste dépend de la taille du score
        // si le score est 20, la liste contient 2 éléments
        // si le score est 200, la liste contient 3 éléments
        listScoreItems = new Score[s.length()];
        
        // on "balaie" dans le score et récupère chaque chiffre
        for (char c : s.toCharArray()) {
            // dans notre liste, on ajoute le nouveau chiffre
            // c - '0' pour récupérer la valeur entière du caractère (ex : '9' - '0' = 9)
            // pour c=9, on ajoute le sprite filesNames[9] càd Score/9
            listScoreItems[scoreItem] = new Score(game, filesNames[c - '0'], x, 6);
            game.addItem(listScoreItems[scoreItem]);

            // on incrémente l'abscisse pour que le chiffre d'après soit décalé
            x += 8;
            
            // item suivant
            scoreItem++;
        }
        
        // on appelle la méthode permettant d'afficher combien de points
        // a obtenu le joueur avec sa dernière action effectuée
        if (nbPoints != 10)
            displayTheDelta(nbPoints, i, j, direction);
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
            String fileName = "images/Score/";
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
        
        for (Score scoreItem : this.listScoreItems) {
            game.remove(scoreItem);
        }
    }
    
    /**
     * Méthode permettant d'enlever le +[nbPoints] à côté du joueur
     */
    public void cleanDeltaItemsList() {
       
        for (Score scoreItem : this.listDeltaItems) {
            game.remove(scoreItem);
        }
    }
        
    /**
     * Méthode permettant d'afficher le delta, c'est à dire la différence entre 
     * le nouveau score et le score d'avant (combien il a marqué de points)
     * @param nbPoints = le nombre de points qu'il a gagnés (variant selon l'action effectuée)
     * @param i = la position du joueur
     * @param j = la position du joueur
     * @param direction = la direction du joueur
     */
    private void displayTheDelta(int nbPoints, int i, int j, String direction) {
        
        // ces variables vont permettre d'afficher le nombre de points gagnés 
        // en fonction de i et j
        int x = 0, y = 0;
        
        // selon la direction du joueur, on affiche pas le delta au même endroit
        switch(direction) {        
            case"left":
                x = (j+1) * 28; // le joueur va sur la gauche donc on l'affiche à sa droite (+1 en abscisse)
                y = i * 28;
                break;
            case"right":
                x = (j-1) * 28; // le joueur va sur la droite donc on l'affiche à sa gauche (-1 en abscisse)
                y = i * 28;
                break;
            case"up":
                x = j * 28; 
                y = (i+1) * 28; // le joueur va en haut donc on l'affiche en-dessous (+1 en ordonnée)
                break;
            case"down":
                x = j * 28;
                y = (i-1) * 28; // le joueur va en bas donc on l'affiche au-dessus (-1 en ordonnée)
                break;
        }
        
        // variable permettant de décaler les chiffres sur la droite en abscisse pour ne pas qu'ils se superposent
        int decale = 0;
        
        // même principe que pour le score, on transforme l'entier en String pour récupérer chaque caractère
        String s = valueOf(nbPoints);
        
        // liste avec les chiffres du delta à afficher avec un élément en plus (d'où le +1) qui est le signe "+"
        listDeltaItems = new Score[s.length()+1];
        
        // variable représentant l'index des éléments du tableau listDeltaItems
        int deltaItems = 0;
        
        // on affiche le signe "+"
        listDeltaItems[deltaItems] = new Score(game, "images/Score/+", x, y);
        game.addItem(listDeltaItems[deltaItems]);
        
        // on incrémente pour placer le prochaine élément à la suite dans le tableau et pas à la place
        deltaItems ++;
        
        // on décale le prochain élément de 8 pixels sur la droite pour éviter la superposition
        decale+=8;
        
        // pour chaque chiffre, on l'ajoute dans la liste des éléments et on l'affiche
        for(char c : s.toCharArray()) {
            listDeltaItems[deltaItems] = new Score(game, filesNames[c - '0'], x+decale, y);
            game.addItem(listDeltaItems[deltaItems]);

            // on incrémente l'abscisse pour que le chiffre d'après soit décalé
            decale += 8;
            
            // on décale le prochain élément de 8 pixels sur la droite pour éviter la superposition
            deltaItems++;
        }
    }
    
    /**
     * Méthode permettant d'afficher le score final du joueur
     */
    public void displayFinalScore() {
        // variable représentant l'abscisse des caractères 
        int x = 355;
        
        // variable représentant l'index des éléments du tableau listScoreItems
        int scoreItem = 0;
        
        // on transforme le score en chaîne de caractères pour pouvoir "balayer" dedans (= récupérer chaque chiffre)
        String s = valueOf(score);
        
        // la liste dépend de la taille du score
        // si le score est 20, la liste contient 2 éléments
        // si le score est 200, la liste contient 3 éléments
        listScoreItems = new Score[s.length()];
        
        // on "balaie" dans le score et récupère chaque chiffre
        for (char c : s.toCharArray()) {
            // dans notre liste, on ajoute le nouveau chiffre
            // c - '0' pour récupérer la valeur entière du caractère (ex : '9' - '0' = 9)
            // pour c=9, on ajoute le sprite filesNames[9] càd Score/9
            listScoreItems[scoreItem] = new Score(game, filesNames[c - '0'], x, 379);
            game.addItem(listScoreItems[scoreItem]);

            // on incrémente l'abscisse pour que le chiffre d'après soit décalé
            x += 8;
            
            // item suivant
            scoreItem++;
        }
    }
    
    @Override public boolean isCollide(GameItem gi) {return false;}
    @Override public void collideEffect(GameItem gi) {}
    @Override public void evolve(long l) {}
    
    @Override
    public String getItemType() {
        return "score";
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

/*
 * - Puissance 4 - Helleux / Meurice -
 */
package puissance4_helleux_meurice;

/**
 *
 * @author 33608
 */
public class Grille {
    Cellule[][] cellules;
    
    // On créé un tableau de 42 Cellules.
    public Grille(){
        cellules= new Cellule[6][7];
    }
    
    // On vérifie toutes les lignes de la colonne selectionnée (de bas en haut), si une cellule de cette colonne ne possède pas de jetons, on return true et le jeton sera attribué à cette cellule. Autrement, on return false quand la colonne est pleine.
    public boolean ajouterJetonDansColonne (Jeton jeton, int colonne){
        int i=0;
        while (cellules[i][colonne].jetonCourant!=null && i<6){
            i++;
        }
        // ici, i n'est plus forcément 0, il est la ligne la plus basse innocupée.
        return cellules[i][colonne].affecterJeton(jeton);
    }
    
    // verifie que chaque case de chaque ligne est vide
    public boolean etreRemplie(){
        for (int i=0;i<6;i++){
            for (int j=0;j<7;j++){
                if (cellules[i][j].jetonCourant==null){
                    return false;
                }
            }
        }
        return true;
    }
    
    // Parcours l'ensemble de la grille et réinitialise toutes les cellules pour quelles soient vides
    public void viderGrille(){
        for (int i=0;i<6;i++){
            for (int j=0;j<7;j++){
                cellules[i][j]=null;
            }
        }
    }
    
    //à retravailler
    public void afficherGrilleSurConsole(){
        for (int i=5;i>=0;i--){
            for (int j=0;j<=6;j++){
                if (cellules[i][j].jetonCourant!=null){
                    if ("rouge".equals(cellules[i][j].jetonCourant.couleur)){
                        System.out.print("R");
                    }
                    if ("jaune".equals(cellules[i][j].jetonCourant.couleur)){
                        System.out.print("J");
                    }
                }
                if (cellules[i][j].trouNoir==true && cellules[i][j].jetonCourant==null){
                    System.out.print("T");
                }
                if (cellules[i][j].desintegrateur==true && cellules[i][j].trouNoir==false && cellules[i][j].jetonCourant==null){
                    System.out.print("D");
                }
                if (cellules[i][j].desintegrateur==false && cellules[i][j].trouNoir==false&&cellules[i][j].jetonCourant==null) {
                    System.out.print("_");               
                }
            }
            System.out.print("\n");
        }
    }
    
    // renvoie vrai si la cellule de coordonnées données est occupée par un jeton.
    public boolean celluleOccupee(int ligne, int colonne){
        if (cellules[ligne][colonne].jetonCourant!= null){
            return true; 
        }
        else {
            return false;
        }
    }
    
    public String lireCouleurDuJeton( int i, int j){
        return (cellules[i][j].jetonCourant.couleur);
    }
    
    // Verifie si le joueur entré en paramètre est gagnant en sondant les cellules de la grille pour trouver une combinaison gagnante des jetons de sa couleur
    public boolean etreGagnantePourJoueur(joueur Michel){
        String couleurtest= Michel.couleur;
        for (int i=0;i<3;i++){
            // test dans le quart bas gauche de la grille car seul ici on peut avoir le premier jeton d'une combinaison gagnante
            for (int j=0;j<4;j++){
                //test des colonne
                if (cellules[i][j].jetonCourant!=null && cellules[i+1][j].jetonCourant!=null && cellules[i+2][j].jetonCourant!=null && cellules[i+3][j].jetonCourant!=null){
                    if (cellules[i][j].jetonCourant.couleur.equals(couleurtest) && cellules[i+1][j].jetonCourant.couleur.equals(couleurtest) && cellules[i+2][j].jetonCourant.couleur.equals(couleurtest) && cellules[i+3][j].jetonCourant.couleur.equals(couleurtest)){
                        return true;
                    }
                }
                
                //test des lignes
                if (cellules[i][j].jetonCourant!=null && cellules[i][j+1].jetonCourant!=null && cellules[i][j+2].jetonCourant!=null && cellules[i][j+3].jetonCourant!=null){
                    if (cellules[i][j].jetonCourant.couleur.equals(couleurtest) && cellules[i][j+1].jetonCourant.couleur.equals(couleurtest) && cellules[i][j+2].jetonCourant.couleur.equals(couleurtest) && cellules[i][j+3].jetonCourant.couleur.equals(couleurtest)){
                        return true;
                    }
                }
                //test des diagonales de bas gauche vers haut droit
                if (cellules[i][j].jetonCourant!=null && cellules[i+1][j+1].jetonCourant!=null && cellules[i+2][j+2].jetonCourant!=null && cellules[i+3][j+3].jetonCourant!=null){
                    if (cellules[i][j].jetonCourant.couleur.equals(couleurtest) && cellules[i+1][j+1].jetonCourant.couleur.equals(couleurtest) && cellules[i+2][j+2].jetonCourant.couleur.equals(couleurtest) && cellules[i+3][j+3].jetonCourant.couleur.equals(couleurtest)){
                        return true;
                    }
                }
            }
            // test dans le quart bas droite (seulement possibilité de colonne et de diagonales de bas droite vers haut gauche)
            for (int j=3;j<7;j++){
                
                    // test des diagonales qui vont de en bas a droite vers en haut a gauche
                    if (cellules[i][j].jetonCourant!=null && cellules[i+1][j-1].jetonCourant!=null && cellules[i+2][j-2].jetonCourant!=null && cellules[i+3][j-3].jetonCourant!=null){               
                        if (cellules[i][j].jetonCourant.couleur.equals(couleurtest) && cellules[i+1][j-1].jetonCourant.couleur.equals(couleurtest) && cellules[i+2][j-2].jetonCourant.couleur.equals(couleurtest) && cellules[i+3][j-3].jetonCourant.couleur.equals(couleurtest)){
                            return true;
                        }
                    }
                    
                    //test des colonnes
                    if (j>3){ // On évite de refaire la meme colonne 2 fois
                        if (cellules[i][j].jetonCourant!=null && cellules[i+1][j].jetonCourant!=null && cellules[i+2][j].jetonCourant!=null && cellules[i+3][j].jetonCourant!=null){
                            if (cellules[i][j].jetonCourant.couleur.equals(couleurtest) && cellules[i+1][j].jetonCourant.couleur.equals(couleurtest) && cellules[i+2][j].jetonCourant.couleur.equals(couleurtest) && cellules[i+3][j].jetonCourant.couleur.equals(couleurtest)){
                                return true;
                            }
                        }
                    }
            }
        }
        // à ce niveau toutes les colonnes sont vérifiées ainsi que toutes les diagonales
        // test du quart haut droit (seulement possibilité de ligne)
        for (int i=3;i<6;i++){
            for (int j=0;j<4;j++){
                if (cellules[i][j].jetonCourant!=null &&cellules[i][j+1].jetonCourant!=null && cellules[i][j+2].jetonCourant!=null && cellules[i][j+3].jetonCourant!=null){                
                    if (cellules[i][j].jetonCourant.couleur.equals(couleurtest) && cellules[i][j+1].jetonCourant.couleur.equals(couleurtest) && cellules[i][j+2].jetonCourant.couleur.equals(couleurtest) && cellules[i][j+3].jetonCourant.couleur.equals(couleurtest)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /*public void tasserGrille(int i){
        for (i;i<5;i++){
            cellules[]
        }
    }
    */
    
    public boolean colonneRemplie(int j){
        // on teste seulement la derniere cellule de la colonne car c'est elle qui détermine si la colonne est remplie
        return cellules[0][j]!= null;
    }
    
    //vérifie l'absence de trou noir puis en ajoute un si possible    
    public boolean placerTrouNoir(int i, int j){
        if (cellules[i][j].trouNoir==true){
            return false;
        }
        else {
            cellules[i][j].trouNoir=true;
            return true;
        }
    }
    
    //vérifie l'absence de desintegrateur puis en ajoute un si possible
    public boolean placerDesintegrateur(int i, int j){
        if (cellules[i][j].desintegrateur==true){
            return false;
        }
        else {
            cellules[i][j].desintegrateur=true;
            return true;
        }
    }
    
    //verifie la présence d'un jeton puis le supprime si possible
    public boolean supprimerJeton(int i, int j){
        if (cellules[i][j].jetonCourant==null){
            return false;
        }
        else {
            cellules[i][j].jetonCourant=null;
            return true;
        }
    }
    
    //Recupere l'adresse du jeton puis le supprime de la cellule et retourne cette adresse
    public Jeton recupererJeton(int i, int j){
        Jeton jeton =cellules[i][j].jetonCourant;
        supprimerJeton(i, j);
        return jeton;
    }
    
    // On fait un programme qui parcours les lignes de la colonne a tasser (de numéro j) dès qu'il trouve une cellule nulle dans la colonne,
    // il l'échange avec celle de la cellule du dessus. Ainsi elle finit par se retrouver en haut 
    public void tasserGrille(int j){
        int a=0; // a va permettre de savoir si on a besoin de refaire monter des valeures nulles (si il y en a 2 d'un coup par ex)
        while (a==0){
            for (int i=4; i<=0;i--){ // on va que jusqu'a la 4 eme ligne car si on doit tasser la colonne, la ligne 6 sera forcement nulle.
                if (cellules[i][j].jetonCourant==null){
                    cellules[i][j].jetonCourant=cellules[i+1][j].jetonCourant;
                    cellules[i+1][j].jetonCourant=null;
                }
            }
            a=1;
            for (int i=4; i<=0;i--){  // cas où on ai 2 valeures nulles dans la colonne.
                if (cellules[i][j].jetonCourant==null && cellules[i+1][j].jetonCourant!=null){
                    a=0;
                }
            }
        }
    }
}
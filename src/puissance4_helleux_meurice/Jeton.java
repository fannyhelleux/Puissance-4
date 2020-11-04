package puissance4_helleux_meurice;

/*
 * - Puissance 4 - Helleux / Meurice -
 */

/**
 *
 * @author 33608
 */
public class Jeton {
    String couleur;
    
    // Création du constructeur
    public Jeton(String jcouleur){
        couleur = jcouleur;
    }
    
    // Création de la fonction permettant de lire la couleur du jeton
    public String lireCouleur(){
        return couleur;
    }
}

package puissance4_helleux_meurice;

/*
 * - Puissance4 - Helleux - Meurice -
 */

/**
 *
 * @author 33608
 */
public class Cellule {
    Jeton jetonCourant;
    boolean trouNoir;
    boolean desintegrateur;
    
    // Création du constructeur
    public Cellule(){
        jetonCourant = null;
        trouNoir= false;
        desintegrateur=false;
    }
    //Fonction permettant l'affectation d'un jeton à une cellule retournant si l'ajout c'est bien passé
    public boolean affecterJeton(Jeton jetonAjoute){
        if (jetonCourant==null){
            jetonCourant=jetonAjoute;
            return true;
        }
        else {
            return false;
        }   
    }
    
    //Retourne l'objet jeton présent dans la case si il existe sinon "null"
    public Jeton recupererJeton(){
        return jetonCourant;
    }
    
    // Supprime le jeton si il existe et retourne vrai sinon retourne faux
    public boolean supprimerJeton(){
        if (jetonCourant==null){
            return false;
        }
        else {
            jetonCourant = null;
            return true;
        }    
    }
    
    //Fonction permettant l'affectation d'un trou noir à une cellule retournant vrai si l'ajout c'est bien passé
    public boolean placerTrouNoir(){
        if (trouNoir==false){
            trouNoir=true;
            return true;
        }
        else {
            return false;
        }   
    }
    
    //Fonction permettant l'affectation d'un desintegrateur à une cellule retournant vrai si l'ajout c'est bien passé
    public boolean placerDesintegrateur(){
        if (desintegrateur==false){
            desintegrateur=true;
            return true;
        }
        else {
            return false;
        }   
    }
    
    // Renvoi si oui ou non un trou noir est present dans la cellule
    public boolean presenceTrouNoir() {
        return trouNoir;
    }
    
    // Renvoi si oui ou non un desintegrateur est present dans la cellule
    public boolean presenceDesintegrateur() {
        return desintegrateur;
    }
    
    // Renvoi la couleur du jeton présent sur la case à l'aide de la méthode associée
    public String lireCouleurDuJeton(){
        return jetonCourant.lireCouleur();
    }
    
    public boolean recupererDesintegrateur  (){
        if (desintegrateur==true){
            desintegrateur=false;
            return true;
        }
        else {
            return false;
        }    
    }//eeeeeefefzd,sji,fijenrjfnsjf,jnrejnfsjnf,rksjrnf,sjnfrkjfnjnfkd,sfjre
    
    public boolean activerTrouNoir(){
        if (jetonCourant!= null && trouNoir==true){
            jetonCourant= null;
            trouNoir=false;
            return true;
        }
        else {
            return false;
        }
    }
   
}

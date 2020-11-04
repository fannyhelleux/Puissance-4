/*
 * - Puissance4 - Helleux - Meurice -
 */
package puissance4_helleux_meurice;

/**
 *- Puissance 4 - Helleux / Meurice -
 * @author 33608
 */
public class joueur {
    String nom;
    String couleur;
    Jeton [] listeJetons;
    int nbrDesintegrateur;
    int nbrJetonsRestants;
    
    // définis le nom du joueur, initialise une liste de jetons, le nbr de desintegrateur, et son nombre total de jetons
    public joueur(String jnom){
        nom=jnom;
        listeJetons = new Jeton [21];
        nbrDesintegrateur=0;
        nbrJetonsRestants=21;
    }
    
    // Affecte une couleur au joueur
    public void affecterCouleur(String jcouleur){
        couleur=jcouleur;
    }
    
    // Ajoute un jeton a la liste du joueur
    public void ajouterJeton(Jeton jjeton){
        int i=0;
        while (listeJetons[i]!=null && i<(listeJetons.length-1)){
            i=i+1;
        }
        listeJetons[i]=jjeton;
    }
    
    // incrémente le nbr de desintegrateur du joueur
    public void obtenirDesintegrateur(){
        nbrDesintegrateur++;
    }
    
    // Décrémente le nbr de desintegrateur (renvoie true si utilisation possible et false sinon) 
    public boolean utiliserDesintegrateur(){
        if(nbrDesintegrateur>0){
            nbrDesintegrateur--;
            return true;
        }
        else{
            return false;
        }
    }
}

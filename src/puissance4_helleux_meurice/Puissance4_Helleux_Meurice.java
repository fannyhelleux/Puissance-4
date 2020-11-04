/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puissance4_helleux_meurice;

import java.util.Arrays;

/**
 *
 * @author 33608
 */
public class Puissance4_Helleux_Meurice {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        /*
        // Test la classe jeton
        Jeton jetonTest = new Jeton ("rouge");
        System.out.println(jetonTest.lireCouleur());
        
        //Test la classe Cellule
        Cellule cellTest = new Cellule ();
        System.out.println("affecterJeton :"+ cellTest.affecterJeton(jetonTest));
        System.out.println("recupererJeton :" + cellTest.recupererJeton());
        System.out.println("lireCouleurDuJeton :"+ cellTest.lireCouleurDuJeton());
        System.out.println("placerTrouNoir :"+ cellTest.placerTrouNoir());
        System.out.println("presenceTrouNoir :"+cellTest.presenceTrouNoir());
        System.out.println("placerDesintegrateur :"+cellTest.placerDesintegrateur());
        System.out.println("presenceDesintegrateur :"+cellTest.presenceDesintegrateur());
        System.out.println("recupérerDesintegrateur :"+cellTest.recupererDesintegrateur());        
        System.out.println("supprimerJeton :"+cellTest.supprimerJeton());
        System.out.println("affecterJeton :"+cellTest.affecterJeton(jetonTest));
        System.out.println("activerTrouNoir :"+cellTest.activerTrouNoir());
        
        //Test la classe joueur
        joueur joueurTest = new joueur("fapaul");
        joueurTest.affecterCouleur("red");
        System.out.println("\naffecter Couleur : "+joueurTest.couleur);
        joueurTest.ajouterJeton(jetonTest);
        System.out.println("ajouter Jeton : "+Arrays.toString(joueurTest.listeJetons));
        joueurTest.obtenirDesintegrateur();
        System.out.println("obtenir Desinstegrateur : "+joueurTest.nbrDesintegrateur);
        joueurTest.utiliserDesintegrateur();
        System.out.println("utiliser Desinstegrateur : "+ joueurTest.nbrDesintegrateur);
        
        System.out.println(jetonTest.couleur);
        String cross = "\ud800\udc35";
        */
        Partie partie= new Partie ();
        partie.debuterPartie();
        
    }
    
}

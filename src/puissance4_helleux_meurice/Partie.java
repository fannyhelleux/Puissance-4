/*
 * - Puissance 4 - Helleux / Meurice -
 */
package puissance4_helleux_meurice;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author 33608
 */
public class Partie {
    joueur[] listeJoueurs;
    joueur joueurCourant;
    Grille grille;
    
    public Partie(){
        listeJoueurs = new joueur[2];
        grille  = new Grille(); 
    }
    
    //// On attribue une couleur au joueur : on crée un tableau contenant les string jaune et rouge pour les couleurs, on met en place un systeme de nombre aléatoire qui va décider la case du tableau qu'on attribuera au premier joueur, puis on effectue un test pour attribuer l'autre culeir à l'autre joueur
    public void attribuerCouleursAuxJoueurs(){
        String tab[] = new String [2];
        tab[0] = "jaune";
        tab[1] = "rouge";
        Random rand = new Random();
        int nombreAleatoire = rand.nextInt(2);
        listeJoueurs[0].affecterCouleur(tab[nombreAleatoire]);
        if (nombreAleatoire==0){
            listeJoueurs[1].affecterCouleur(tab[1]);
        }
        else{
            listeJoueurs[1].affecterCouleur(tab[0]);
        }
    }
 
    public void initialiserPartie(){
        //initialisation des joueurs
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez entrer le nom du premier joueur:");
        listeJoueurs[0]= new joueur(sc.nextLine());
        System.out.println("Veuillez entrer le nom du second joueur:");
        listeJoueurs[1]= new joueur(sc.nextLine());
        attribuerCouleursAuxJoueurs();
        
        // création et attributions des jetons aux joueurs
        int i=0;
        while (i<21){
            listeJoueurs[0].ajouterJeton(new Jeton(listeJoueurs[0].couleur));
            listeJoueurs[1].ajouterJeton(new Jeton(listeJoueurs[1].couleur));
            i++;
        }
        
        //initialisation de la grille 
        for (i=0;i<6;i++){
            for (int j=0;j<7;j++){
                grille.cellules[i][j]= new Cellule();
            }
        }
        
        
        //positionnement des cinq trou noir
        i=0;
        int tabCooTrouNoir[][]= new int[5][2];
        while(i<5){
            Random rand = new Random();
            int ligne= rand.nextInt(6);
            int colonne= rand.nextInt(7);
            if (grille.cellules[ligne][colonne].placerTrouNoir()==true){
                tabCooTrouNoir[i][0]=ligne;
                tabCooTrouNoir[i][1]=colonne;
                i=i+1;
            }
        }
        
        
        // positionnement des cinq désintégrateurs
        i=1;
        //positionnement des deux désintégrateurs sous les trous noirs
        while(i<2){
            Random rand = new Random();
            int num= rand.nextInt(5);
            if (grille.cellules[tabCooTrouNoir[num][0]][tabCooTrouNoir[num][1]].placerDesintegrateur()==true){
                i=i+1;
            }
        }  
        //positionnement des autres désintégrateurs (hors trous noirs)
        while(i<5){
            Random rand = new Random();
            int coo[]= new int[2];
            coo[0]=rand.nextInt(6);
            coo[1]= rand.nextInt(7);
            if (grille.cellules[coo[0]][coo[1]].placerDesintegrateur()==true && tabCooTrouNoir[0]!=coo && tabCooTrouNoir[1]!=coo && tabCooTrouNoir[2]!=coo &&tabCooTrouNoir[3]!=coo &&tabCooTrouNoir[4]!=coo){
                i=i+1;
            }
        }
    }
    
    @SuppressWarnings("empty-statement")
    public void debuterPartie(){
        initialiserPartie();
        Scanner sc = new Scanner(System.in);
        int nb= 0;
        int compteur=0;
        System.out.println(listeJoueurs[0].nom+ " est "+listeJoueurs[0].couleur);
        System.out.println(listeJoueurs[1].nom+ " est "+listeJoueurs[1].couleur);        
        while(grille.etreGagnantePourJoueur(listeJoueurs[0])==false||grille.etreGagnantePourJoueur(listeJoueurs[1])==false||grille.etreRemplie()==false){
            compteur = 0; // permet de controler le changement de joueur
            joueurCourant=listeJoueurs[nb%2];
            System.out.println("\n\nC'est à " + joueurCourant.nom + " de jouer!"); 
            System.out.println("Tu as "+joueurCourant.nbrDesintegrateur+" désintégrateur(s).\n");
            grille.afficherGrilleSurConsole();
            System.out.println("\nQue veux tu faire?\n");
            // permet d'enlever la proposition d'utilisation du désintégrateur ( on garde tout de meme la boucle de vérification plus tard en cas d'erreur du joueur)  
            if (joueurCourant.nbrDesintegrateur!=0){
                System.out.println("Jouer un desintegrateur (d)");
            }
            System.out.println("-> Placer un jeton (j)");
            System.out.println("-> Récuperer un jeton (r)\n");
            System.out.println("Quitter (q)");
 
            String action;
            action = sc.nextLine();
            
            //jeu d'un desintegrateur
            if ("d".equals(action)){
                // permet de décrémenter le nmbre de désintégrateur et évite au joueur d'utiliser des désintégrateur qu'il n'as pas 
                if (joueurCourant.utiliserDesintegrateur()==true){
                    System.out.println("Où veux tu placer ton désintégrateur?");
                    System.out.print("ligne : ");
                    int ligneD= sc.nextInt()-1;
                    System.out.print("colonne : ");
                    int colonneD= sc.nextInt()-1;
                    while (colonneD<0 || colonneD>6){
                        System.out.println("Cette colonne n'éxiste pas... (Elles vont de 1 à 7)");
                        colonneD= sc.nextInt()-1;
                    }
                    while (ligneD<0 || ligneD>5){
                        System.out.println("Cette colonne n'éxiste pas... (Elles vont de 1 à 7)");
                        ligneD= sc.nextInt()-1;
                    }
                    while ((joueurCourant.couleur).equals(grille.cellules[ligneD][colonneD].jetonCourant.couleur) || grille.cellules[ligneD][colonneD].supprimerJeton()== false){
                        System.out.println("Tu ne peux pas utiliser de désintégrateur sur cette case \nOù veux tu placer ton désintégrateur?");
                        System.out.print("ligne : ");
                        ligneD= sc.nextInt()-1;
                        System.out.print("colonne : ");
                        colonneD= sc.nextInt()-1;
                    }
                    grille.tasserGrille(colonneD);
                    compteur=1;
                }
                else {
                   System.out.println("Tu n'as pas de désintégrateur choisis une autre action!");
                }
            }
            
            // récuperation d'un jeton :
            if ("r".equals(action)){
                if (joueurCourant.listeJetons.length<20){
                    System.out.println("Quel jeton veux tu récuperer?");
                    System.out.print("ligne : ");
                    int ligneD= sc.nextInt()-1;
                    System.out.print("colonne : ");
                    int colonneD= sc.nextInt()-1;
                    while (colonneD<0 || colonneD>6){
                        System.out.println("Cette colonne n'éxiste pas... (Elles vont de 1 à 7)");
                        colonneD= sc.nextInt()-1;
                    }
                    while (ligneD<0 || ligneD>5){
                        System.out.println("Cette colonne n'éxiste pas... (Elles vont de 1 à 7)");
                        ligneD= sc.nextInt()-1;
                    }
                    while ((joueurCourant.couleur).equals(grille.cellules[ligneD][colonneD].jetonCourant.couleur)==false || grille.cellules[ligneD][colonneD].supprimerJeton()== false){
                        System.out.println("Tu ne peux pas récuperer de jeton ici \nQuel jeton veux tu récuperer?");
                        System.out.print("ligne : ");
                        ligneD= sc.nextInt()-1;
                        System.out.print("colonne : ");
                        colonneD= sc.nextInt()-1;
                        while (colonneD<0 || colonneD>6){
                            System.out.println("Cette colonne n'éxiste pas... (Elles vont de 1 à 7)");
                            colonneD= sc.nextInt()-1;
                        }
                        while (ligneD<0 || ligneD>5){
                            System.out.println("Cette colonne n'éxiste pas... (Elles vont de 1 à 7)");
                            ligneD= sc.nextInt()-1;
                        }
                    }
                    int i=0;
                    while (joueurCourant.listeJetons[i]!=null){
                        i++;
                    }
                    joueurCourant.listeJetons[i]=grille.cellules[ligneD][colonneD].recupererJeton();
                    grille.tasserGrille(colonneD);
                    compteur = 1;
                    
                }
                else{
                    System.out.println("Désolé mais tu n'as pas de jeton à récupérer...");
                }
            }
            
            //placement d'un jeton
             if ("j".equals(action)){
                if (joueurCourant.listeJetons.length>0){
                    System.out.println("Où veux tu placer ton jeton?");
                    System.out.print("colonne : ");
                    // String colonnej = sc.next(); // !!!!!!!!!!! faudrait remplacer l'entrée colonnej en String et pas prendre des int pcq sinon ça affiche une erreur qui fait quitter la partie
                    int colonnej= sc.nextInt()-1;
                    // Vérification de l'existence de la colonne
                    while (colonnej<0 || colonnej>6){ // mais ici je sais pas dire "colonnej n'appartient pas a ("1","2",...,"5")
                        System.out.println("Cette colonne n'éxiste pas... (Elles vont de 1 à 7)");
                        colonnej= sc.nextInt()-1;
                    }
                    // détermination du jeton à placer
                    int i=20;
                    while (joueurCourant.listeJetons[i]==null){
                        i--;
                    }
                    // vérification de l'ajout du jeton
                    while (grille.ajouterJetonDansColonne(joueurCourant.listeJetons[i], colonnej)==false){
                        System.out.println("Tu ne peux pas placer de jeton ici \nOù veux tu placer ton jeton?");
                        System.out.print("colonne : ");
                        colonnej= sc.nextInt()-1;
                        while (colonnej<0 || colonnej>6){
                            System.out.println("Cette colonne n'éxiste pas... (Elles vont de 1 à 7)");
                            colonnej= sc.nextInt()-1;
                        }
                    }
                    joueurCourant.listeJetons[i]=null;
                    // recherche de la case d'ajout du jeton
                    int lignej=0;
                    while (grille.cellules[lignej][colonnej]==null){
                        lignej++;
                    }
                    if (grille.cellules[lignej][colonnej].presenceDesintegrateur()){
                        joueurCourant.obtenirDesintegrateur();
                        grille.cellules[lignej][colonnej].recupererDesintegrateur();
                        System.out.println("Tu as réussi à obtenir un nouveau désintégrateur!");
                    }
                    if (grille.cellules[lignej][colonnej].presenceTrouNoir()){
                        grille.cellules[lignej][colonnej].activerTrouNoir();
                        grille.tasserGrille(colonnej);
                        System.out.println("Tu as activé un trou noir...");
                    }
                    compteur=1;
                }
                else{
                    System.out.println("Tu n'as plus de jetons... Choisis une autre action!");
                }
            }
            if ("q".equals(action)){
                break;
            }
            if (compteur==1){ // si une action a été effectuer alors le compteur sera à 1 et permettra le changement de joueur
                nb++;
            }
        }
    }
}

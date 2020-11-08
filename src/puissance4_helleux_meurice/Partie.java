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
        int compteur = 0; // permet de controler le changement de joueur.
        
        int dmaispasd = 0; // permet d'afficher un msg d'erreur si "d" mais pas de desintegrateur.
        int rmaispasdej=0; // meme chose si joueur veut jouer "r" mais ne peut pas récupérer de jetons.
        int pdj=0; //meme chose si "j" mais plus de jetons.
        
        int quitter=0;
        boolean quitterbool = false;
        System.out.println("\n"+listeJoueurs[0].nom+ " est "+listeJoueurs[0].couleur);
        System.out.println(listeJoueurs[1].nom+ " est "+listeJoueurs[1].couleur);        
        
        while((grille.etreGagnantePourJoueur(listeJoueurs[0])==false & grille.etreGagnantePourJoueur(listeJoueurs[1])==false & grille.etreRemplie()==false & quitterbool==false)==true){
            
            compteur = 0;
            joueurCourant=listeJoueurs[nb%2];
// affichage de la console et des propositions d'action :
            System.out.println("\n\nA toi de jouer : " + joueurCourant.nom+"\n");
            
            grille.afficherGrilleSurConsole();
            
            int nbj=21;
            while (joueurCourant.listeJetons[nbj-1]==null){
                nbj=nbj-1;
            }
            System.out.println("\n"+"nb Jeton: "+nbj);
            System.out.println("nb Desintegrateurs: "+joueurCourant.nbrDesintegrateur+"\n");
            
            System.out.println("-> Placer un jeton (j)");
            System.out.println("-> Récuperer un jeton (r)");
            if (joueurCourant.nbrDesintegrateur!=0){ // n'affiche que cette option si elle est possible.
                System.out.println("-> Jouer un desintegrateur (d)");
            }
            System.out.println("-> Quitter (q)\n");
            
// msg d'erreurs en cas d'erreurs:
            if (dmaispasd==1){
                System.out.println("Tu n'as aucun désintégrateurs, choisis une autre action !");
                dmaispasd=0;
            }
            if (rmaispasdej==1){
                System.out.println("Tu ne peut pas récupérer de jetons, choisis une autre action !");
                rmaispasdej=0;
            }
            if (pdj==1){
                System.out.println("Tu n'as plus de jetons, choisis une autre action!");
                pdj=0;
            }
// demande de l'action :
            System.out.println("Choix : ");
            String action = sc.nextLine(); // visiblement cette instruction se faisait sauter 1 fois sur deux
            while (action.equals("")){ // donc on a fait ça car de temps a autre action="".
                action = sc.nextLine();
            }

// "d" : jeu d'un desintegrateur
            if ("d".equals(action)){
                // permet de décrémenter le nmbre de désintégrateur et évite au joueur d'utiliser des désintégrateur qu'il n'as pas 
                if (joueurCourant.utiliserDesintegrateur()==true){
                    System.out.println("Où veux-tu donc placer ton désintégrateur ?");
                    
                    System.out.print("ligne : ");
                    int ligneD= sc.nextInt()-1;
                    while ((ligneD>=0 & ligneD<=5)==false){
                        System.out.println("Cette colonne n'existe pas... (Elles vont de 1 à 7)\nRessaisis là");
                        ligneD= sc.nextInt()-1;
                    }
                    
                    System.out.print("colonne : ");
                    int colonneD= sc.nextInt()-1;
                    while ((colonneD>=0 & colonneD<=6)==false){
                        System.out.println("Cette colonne n'existe point... (Elles vont de 1 à 7)\nRessaisis là");
                        colonneD= sc.nextInt()-1;
                    }
                    
                    while ((grille.celluleOccupee(ligneD,colonneD) && (!(joueurCourant.couleur).equals(grille.cellules[ligneD][colonneD].jetonCourant.couleur)) )== false){
                        System.out.println("Tu ne peux guère utiliser de désintégrateur sur cette case \nOù veux-tu placer ton désintégrateur ?");
                        
                        System.out.print("ligne : ");
                        ligneD= sc.nextInt()-1;
                        while ((ligneD>=0 & ligneD<=5)==false){
                            System.out.println("Cette colonne n'existe pas... (Elles vont de 1 à 7)\nRessaisis là");
                            ligneD= sc.nextInt()-1;
                        }
                        
                        System.out.print("colonne : ");
                        colonneD= sc.nextInt()-1;
                        while ((colonneD>=0 & colonneD<=6)==false){
                            System.out.println("Cette colonne n'existe point... (Elles vont de 1 à 7)\nRessaisis là");
                            colonneD= sc.nextInt()-1;
                        }
                    }
                    grille.cellules[ligneD][colonneD].supprimerJeton();
                    grille.tasserGrille(colonneD);
                    compteur=1;
                }
                else {
                   dmaispasd = 1;
                }
            }
            
// "r" : récuperation d'un jeton :
            if ("r".equals(action)){
                if (joueurCourant.listeJetons.length<20){
                    System.out.println("Quel jeton veux-tu récuperer?");
                    System.out.print("ligne : ");
                    int ligneD= sc.nextInt()-1;
                    System.out.print("colonne : ");
                    int colonneD= sc.nextInt()-1;
                    while (colonneD<0 || colonneD>6){
                        System.out.println("Cette colonne n'existe pas... (Elles vont de 1 à 7)");
                        colonneD= sc.nextInt()-1;
                    }
                    while (ligneD<0 || ligneD>5){
                        System.out.println("Cette ligne n'existe pas... (Elles vont de 1 à 7)");
                        ligneD= sc.nextInt()-1;
                    }
                    while ((joueurCourant.couleur).equals(grille.cellules[ligneD][colonneD].jetonCourant.couleur)==false || grille.cellules[ligneD][colonneD].supprimerJeton()== false){
                        System.out.println("Tu ne peux pas récuperer de jeton ici \nQuel jeton veux tu récuperer?");
                        System.out.print("ligne : ");
                        ligneD= sc.nextInt()-1;
                        System.out.print("colonne : ");
                        colonneD= sc.nextInt()-1;
                        while (colonneD<0 || colonneD>6){
                            System.out.println("Cette colonne n'existe pas... (Elles vont de 1 à 7)");
                            colonneD= sc.nextInt()-1;
                        }
                        while (ligneD<0 || ligneD>5){
                            System.out.println("Cette ligne n'existe pas... (Elles vont de 1 à 7)");
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
                    rmaispasdej=1;
                }
            }
            
// "j" : placement d'un jeton
            if ("j".equals(action)){
                if (joueurCourant.listeJetons.length>0){
                    System.out.println("Où veux-tu placer ton jeton ?");
                    System.out.print("colonne : ");
                    // String colonnej = sc.next(); // d'un pt de vue optimal faudrait remplacer l'entrée colonnej en String et pas prendre des int pcq sinon ça affiche une erreur qui fait quitter la partie
                    int colonnej= sc.nextInt()-1;
                    
                    //System.out.println(colonnej>=0);
                    //System.out.println(colonnej<=6);
                    //System.out.println(grille.cellules[5][colonnej].jetonCourant==null);
                    //System.out.println((colonnej>=0 && colonnej<=6 && grille.cellules[5][colonnej].jetonCourant==null));
                    // Vérification de l'existence de la colonne
                    while ((colonnej>=0 && colonnej<=6 && grille.cellules[5][colonnej].jetonCourant==null)==false){
                        System.out.println("Mauvaise saisie ! Recommencez :");
                        colonnej= sc.nextInt()-1;
                    }

                    // détermination du jeton à placer dans la liste de jeton 
                    int i=20; // 21 jetons mais pour le prgrm, le 0 en est aussi un donc i=20
                    while (joueurCourant.listeJetons[i]==null){
                        i=i-1;
                    }
                    
                // dans l'ordre : désintégrateur ?; ajout du jeton; trou noir ?
                    // Recherhce de la ligne la plus basse innocupée ?
                    int lignei=0;
                    while (grille.cellules[lignei][colonnej].jetonCourant!=null & lignei<6){
                        lignei=lignei+1;
                    }
                    // Désintégrateur ?
                    if (grille.cellules[lignei][colonnej].presenceDesintegrateur()==true){
                        joueurCourant.obtenirDesintegrateur();
                        grille.cellules[lignei][colonnej].recupererDesintegrateur();
                        System.out.println("\n\n\n\nBravo "+ joueurCourant.nom +"! Tu as réussi à obtenir un nouveau désintégrateur!\n            (message temporaire)\n\n\n\n");
                        // Pause de 4s = 4000 millisecondes 
                        int millis = 4000;
                        try {
                            Thread.sleep(millis);
                        } catch (InterruptedException ie) {
                            // ...
                        } 
                    }
                    
                    // Ajout du jeton :
                    while (grille.ajouterJetonDansColonne(joueurCourant.listeJetons[i],colonnej)==false){
                        System.out.println("Tu ne peux pas placer de jeton ici \nOù veux-tu placer ton jeton ?");
                        System.out.print("colonne : ");
                        colonnej= sc.nextInt()-1;
                        while (colonnej<0 || colonnej>6){
                            System.out.println("Cette colonne n'existe pas... (Elles vont de 1 à 7)");
                            colonnej= sc.nextInt()-1;
                        }
                    }
                    joueurCourant.listeJetons[i]=null; //le jeton est ajouté, on le supprime de la liste
                    
                    // Trou noir ?
                    if (grille.cellules[lignei][colonnej].presenceTrouNoir()==true){
                        grille.cellules[lignei][colonnej].activerTrouNoir();
                        grille.tasserGrille(colonnej);
                        System.out.println("\n\n\n\nDommage "+ joueurCourant.nom +"! Tu as activé un trou Noir...\n            (message temporaire)\n\n\n\n");
                        // Pause de 4s = 4000 millisecondes 
                        int millis = 4000;
                        try {
                            Thread.sleep(millis);
                        } catch (InterruptedException ie) {
                            // ...
                        } 
                    }
                    
                    compteur=1;
                }
                else{
                    pdj=1;
                }
            }
            if ("q".equals(action)){
                quitter=1;
            }
            if (compteur==1){ // si une action a été effectuer alors le compteur sera à 1 et permettra le changement de joueur
                nb++;
            }
            
            if (quitter==0){ //change le paramètre quitterbool pour qu'on quitte le while si un joueur le veut.
                quitterbool=false;
            }
            else{
                quitterbool=true;
            }
        }
        if (grille.etreGagnantePourJoueur(listeJoueurs[0])){
                    System.out.println("\n\n\n\nBravo!! "+listeJoueurs[0].nom+" as gagné!!!\n\n\n\n");
        }
        if (grille.etreGagnantePourJoueur(listeJoueurs[1])){
                    System.out.println("\n\n\n\nBravo!! "+listeJoueurs[1].nom+" as gagné!!!\n\n\n\n");
        }
        if (grille.etreRemplie()){
            System.out.println("Vous etes tous nuls vous avez perdus.");
        }
    }
}


// Finis :  probleme pour quitter (break pas ouf)
// Finis :  Alors si une colonne est pleine et qu'on met un jeton dessus y'a une erreur qui nous fais tej
// Finis :  On peut pas placer de desintegrateur sur la ligne 1... Ah si jcrois bref tester
// Finis :  Tasser grille tasse pas la grille
// Finis :  Ca print tjrs 2 fois le menu de jeu 
// Finis :  Ca ramasse plus les desintegrateurs, enfin j'ai l'impression que c'est pas le cas partout ex pas pour (i=3;j=7)
// Finis :  Faire une ptite pause qd on a cop un désintégrateur et qd on tombe sur un trou noir.
// Finis :  nbr de jeton immuables.
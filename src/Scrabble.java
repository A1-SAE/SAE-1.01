public class Scrabble {
    private Joueur[] joueurs;
    private int numJoueur;
    private Plateau plateau;
    private MEE sac;

    private static int[] nbPointsJeton = {1,3,3,2,1,4,2,4,1,8,10,1,2,1,1,3,8,1,1,1,1,4,10,10,10,10,0};

    /*
    * action : constructeur de Scrabble
    * */
    public Scrabble(String[] nomJoueurs){
        this.numJoueur = Ut.randomMinMax(0, nomJoueurs.length - 1);
        this.joueurs = new Joueur[nomJoueurs.length];
        for(int i = 0; i < nomJoueurs.length; i++){
            this.joueurs[i] = new Joueur(nomJoueurs[i]);
        }

        this.plateau = new Plateau();
        this.sac = new MEE(new int[] {9,2,2,3,15,2,2,2,8,1,1,5,3,6,6,2,1,6,6,6,6,2,1,1,1,1});
    }

    /*
    * résultat : joueur courant et plateau
    * */
    public String toString(){
        return "[" + this.joueurs[this.numJoueur] + "]\n\n" + this.plateau;
    }

    /*
    * action/resultat : orchestre la partie de scrabble et retourne le ou les gagannts
    * */
    public Joueur[] partie(){
        int tour = 0;
        for(int i = 0; i < this.joueurs.length; i++){
            this.joueurs[i].prendJetons(this.sac, 7);
        }

        int raisonFinPartie = 0;
        while(raisonFinPartie == 0){
            int toursPasses = 0;
            this.numJoueur = this.numJoueur % this.joueurs.length;
            /*
            * TODO:
            *   UTILISER LE NUMJOUEUR, DEGAGE LA BOUCLE FOR EN DESSOUS LA
            * */

            for(int i = 0; i < this.joueurs.length; i++){
                System.out.println(this);
                if(this.joueurs[i].getChevalet().getNbTotEx() == 0 && this.sac.getNbTotEx() == 0){
                    raisonFinPartie = 1;
                }else{
                    int choix;
                    do{
                        System.out.println("Tour du joueur : " + this.joueurs[i].getNom() + "\n" +
                                "[1] Placer un mot\n" +
                                "[2] Passer son tour\n" +
                                "[3] Défausser une ou des lettres");
                        choix = Ut.saisirEntier();
                    }while(!(choix >= 1 && choix <= 3));

                    switch(choix){
                        case 1:
                            /* placer un mot */

                            String mot = "";
                            int[] pos = new int[] {-1, -1};
                            char dir = ' ';
                            do{
                                System.out.println("Veuillez entrer le mot que vous souhaitez jouer : ");
                                mot = Ut.saisirChaine();
                                System.out.println("Veuillez entrer la ligne de départ du mot : ");
                                pos[0] = Ut.saisirEntier();
                                System.out.println("Veuillez entrer la colonne de départ du mot : ");
                                pos[1] = Ut.saisirEntier();
                                System.out.println("Veuillez choisir le sens de placement ('h' pour horizontal et 'v' pour vertical) : ");
                                dir = Ut.saisirCaractere();
                            }while(!((mot.length() >= 2) && (dir == 'y' || dir == 'h') && (pos[0] >= 0 && pos[0] <= 14) && (pos[1] >= 0 && pos[1] <= 14) && this.plateau.placementValide(mot, pos[0], pos[1], dir, this.joueurs[i].getChevalet())));

                            /* incrémente le score du joueur */
                            this.joueurs[i].ajouteScore(this.plateau.nbPointsPlacement(mot, pos[0], pos[1], dir, Scrabble.nbPointsJeton));

                            /* repioche des jetons jusqu'à 7 */
                            int jetonsEnleves = this.plateau.place(mot, pos[0], pos[1], dir, this.joueurs[i].getChevalet());
                            if(jetonsEnleves < this.sac.getNbTotEx()) jetonsEnleves = this.sac.getNbTotEx();
                            this.joueurs[i].prendJetons(this.sac, jetonsEnleves);

                            break;
                        case 2:
                            /* passer son tour */

                            toursPasses++;
                            break;
                        case 3:
                            /* Defausser une ou des lettres */

                            String letters;
                            do{
                                System.out.println("Quelles lettres souhaitez-vous défausser (entrez les lettres à la suite comme ceci : 'ABCD') : ");
                                letters = Ut.saisirChaine().toUpperCase();
                            }while(joueurs[i].estCorrectPourEchange(letters));

                            joueurs[i].echangeJetonsAux(this.sac, letters);
                            break;
                    }
                }
            }

            if(toursPasses == this.joueurs.length) raisonFinPartie = 2;
        }

        switch(raisonFinPartie){
            case 1:
                /* le joueur courant n'a plus de jetons et le sac est vide */

                int total = 0;
                for(int i = 0; i < this.joueurs.length; i++){
                    if(i != numJoueur){
                        total += this.joueurs[i].nbPointsChevalet(Scrabble.nbPointsJeton);
                        this.joueurs[i].ajouteScore(- this.joueurs[i].nbPointsChevalet(Scrabble.nbPointsJeton));
                    }
                }
                this.joueurs[this.numJoueur].ajouteScore(total);

                break;
            case 2:
                /* tous les joueurs ont passé leur tour */

                for(int i = 0; i < this.joueurs.length; i++){
                    this.joueurs[i].ajouteScore(- this.joueurs[i].nbPointsChevalet(Scrabble.nbPointsJeton));
                }

                break;
        }

        Joueur[] gagnants = new Joueur[1];
        int scoreGagnant = 0;
        for(int i = 0; i < this.joueurs.length; i++){
            if(this.joueurs[i].getScore() > scoreGagnant){
                gagnants = new Joueur[1];
                gagnants[0] = this.joueurs[i];
            }else if(this.joueurs[i].getScore() == scoreGagnant){
                Joueur[] temp = new Joueur[gagnants.length + 1];
                for(int j = 0; j < gagnants.length; j++){
                    temp[j] = gagnants[j];
                }
                temp[temp.length - 1] = this.joueurs[i];

                gagnants = temp;
            }
        }

        if(gagnants.length == 1){
            System.out.println("Le gagnant est : \n- " + gagnants[0]);
        }else{
            String msg = "Les gagnants sont : \n";
            for(int i = 0; i < gagnants.length; i++){
                msg += "- " + gagnants[i];
            }

            System.out.println(msg);
        }

        return gagnants;
    }
}

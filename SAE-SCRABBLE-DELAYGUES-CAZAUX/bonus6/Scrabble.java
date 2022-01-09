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
        for(int i = 0; i < this.joueurs.length; i++){
            this.joueurs[i].prendJetons(this.sac, 7);
        }

        int raisonFinPartie = 0;
        int toursPasses = 0;
        while(raisonFinPartie == 0){


            this.numJoueur = ((this.numJoueur + 1) % this.joueurs.length);

            System.out.println(this);
            if (joueurs[numJoueur].getChevalet().getNbTotEx() == 0 && this.sac.getNbTotEx() == 0) {
                raisonFinPartie = 1;
            } else {
                int choix = joueurs[numJoueur].joue(this.plateau, this.sac, Scrabble.nbPointsJeton);
                if (choix == 2){
                    toursPasses++;
                }else{
                    toursPasses = 0;
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
        int scoreGagnant = -10000000;
        for (Joueur joueur : this.joueurs) {
            if (joueur.getScore() > scoreGagnant) {
                gagnants = new Joueur[1];
                gagnants[0] = joueur;
                scoreGagnant = joueur.getScore();
            } else if (joueur.getScore() == scoreGagnant) {
                Joueur[] temp = new Joueur[gagnants.length + 1];
                for (int j = 0; j < gagnants.length; j++) {
                    temp[j] = gagnants[j];
                }
                temp[temp.length - 1] = joueur;

                gagnants = temp;
            }
        }

        if(gagnants.length == 1){
            System.out.println("Le gagnant est : \n- " + gagnants[0].getNom());
        }else{
            String msg = "Les gagnants sont : \n";
            for(int i = 0; i < gagnants.length; i++){
                msg += "- " + gagnants[i].getNom();
            }

            System.out.println(msg);
        }

        return gagnants;
    }
}

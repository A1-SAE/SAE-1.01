public class Scrabble {
    private Joueur[] joueurs;
    private int numJoueur;
    private Plateau plateau;
    private MEE sac;

    private static int[] nbPointsJeton = {1,3,3,2,1,4,2,4,1,8,10,1,2,1,1,3,8,1,1,1,1,4,10,10,10,10,0};

    public Scrabble(String[] nomJoueurs){
        this.numJoueur = Ut.randomMinMax(0, nomJoueurs.length - 1);
        this.joueurs = new Joueur[nomJoueurs.length];
        for(int i = 0; i < nomJoueurs.length; i++){
            this.joueurs[i] = new Joueur(nomJoueurs[i]);
        }

        this.plateau = new Plateau();
        this.sac = new MEE(new int[] {9,2,2,3,15,2,2,2,8,1,1,5,3,6,6,2,1,6,6,6,6,2,1,1,1,1});
    }

    public String toString(){
        String res = "[" + this.joueurs[this.numJoueur] + "]\n\n";

        res += this.plateau;

        return res;
    }

    public Joueur[] partie(){
        
    }


}

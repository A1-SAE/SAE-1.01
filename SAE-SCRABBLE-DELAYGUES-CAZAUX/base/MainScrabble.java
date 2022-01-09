public class MainScrabble {
    public static void main(String[] args){
        int nbJoueurs;
        do{
            System.out.println("Saisir le nombre de joueurs : ");
            nbJoueurs = Ut.saisirEntier();
        }while(!(nbJoueurs >= 1 && nbJoueurs <= 14));

        String[] joueurs = new String[nbJoueurs];
        for(int i = 0; i < joueurs.length; i++){
            System.out.println("Saisir le nom de joueur n°" + i + " : ");
            joueurs[i] = Ut.saisirChaine();
        }

        Scrabble partie = new Scrabble(joueurs);
        partie.partie();
    }

}

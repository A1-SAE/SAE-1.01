/*
* extensions : 3.1
* */

public class Branche {
    private Branche[] lettresSuivantes;
    private char lettre;
    boolean estUneFinDeMotPossible;
    int motsSurBranche;

    /*
    * prérequis : lettre est une majuscule
    * action : constructeur de Branche
    * */
    public Branche(char lettre, boolean estUneFinDeMotPossible){
        this.lettre = lettre;
        this.estUneFinDeMotPossible = estUneFinDeMotPossible;
        this.motsSurBranche = 1;
        lettresSuivantes = null;
    }

    /*
    * prérequis : lettreSuivante existe
    * action : ajoute une lettre dans les lettres qui suivent celle-ci
    * */
    public void ajouterLettreSuivante(Branche lettreSuivante){
        if(this.lettresSuivantes == null) this.lettresSuivantes = new Branche[0];

        Branche[] temp = new Branche[this.lettresSuivantes.length + 1];
        for(int i = 0; i < this.lettresSuivantes.length; i++){
            temp[i] = this.lettresSuivantes[i];
        }
        temp[temp.length - 1] = lettreSuivante;

        this.lettresSuivantes = temp;
    }

    /*
    * résultat : retourne les lettres qui suivent celle-ci
    * */
    public Branche[] getLettresSuivantes(){
        return this.lettresSuivantes;
    }

    /*
    * résultat : retourne la lettre de cette branche
    * */
    public char getLettre(){
        return this.lettre;
    }

    /*
    * résultat : retourne vrai ssi avec les lettres précédentes et celle-ci l'on peut former un mot valide du dictionnaire
    * */
    public void setFinDeMot(){
        this.estUneFinDeMotPossible = true;
    }

    /*
    * action : augmenter le nombre de mots qui peuvent être formés par ce chemin
    * */
    public void increaseWord(){
        this.motsSurBranche++;
    }

    /*
    * résultat : retourne le nombre de mots qui peuvent être formés par ce chemin
    * */
    public int getWords(){
        return this.motsSurBranche;
    }
}

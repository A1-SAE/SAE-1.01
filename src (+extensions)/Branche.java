public class Branche {
    private Branche[] lettresSuivantes;
    private char lettre;
    boolean estUneFinDeMotPossible;

    public Branche(char lettre, boolean estUneFinDeMotPossible){
        this.lettre = lettre;
        this.estUneFinDeMotPossible = estUneFinDeMotPossible;
        lettresSuivantes = null;
    }

    public void ajouterLettreSuivante(Branche lettreSuivante){
        if(this.lettresSuivantes == null) this.lettresSuivantes = new Branche[0];

        Branche[] temp = new Branche[this.lettresSuivantes.length + 1];
        for(int i = 0; i < this.lettresSuivantes.length; i++){
            temp[i] = this.lettresSuivantes[i];
        }
        temp[temp.length - 1] = lettreSuivante;

        this.lettresSuivantes = temp;
    }

    public Branche[] getLettresSuivantes(){
        return this.lettresSuivantes;
    }

    public char getLettre(){
        return this.lettre;
    }

    public void setFinDeMot(){
        this.estUneFinDeMotPossible = true;
    }
}

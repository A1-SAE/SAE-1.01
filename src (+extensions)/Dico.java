import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dico {
    Branche[] debuts;

    public Dico() throws FileNotFoundException {
        File doc =
                new File("dicoReference.txt");
        Scanner obj = new Scanner(doc);
        Scanner objCount = new Scanner(doc);
        int lines = 0;
        while(objCount.hasNextLine()){
            lines++;
            objCount.nextLine();
        }

        int line = 0;
        String loadingbar = "";
        while (obj.hasNextLine()){
            line++;
            String mot = obj.nextLine();

            System.out.print("\033[H\033[2J");
            System.out.flush();

            if(line % (lines / 10) == 0) loadingbar += "\u2588";
            System.out.println(mot + "\n" + ((line*100)/lines) + "% " + loadingbar);


            char[] lettres = mot.toCharArray();
            Branche currentBranche = null;

            for(int i = 0; i < lettres.length; i++){
                char lettre = lettres[i];

                if(i == 0){
                    /* première lettre */
                    if(!(this.debuts == null)){
                        for(int j = 0; j < this.debuts.length; j++){
                            if(this.debuts[j].getLettre() == lettre){
                                currentBranche = debuts[j];
                                break;
                            }
                        }
                    }

                    if(currentBranche == null){
                        if(this.debuts == null){
                            this.debuts = new Branche[0];
                        }
                        Branche[] temp = new Branche[this.debuts.length + 1];
                        for(int j = 0; j < this.debuts.length; j++){
                            temp[j] = this.debuts[j];
                        }
                        temp[temp.length - 1] = new Branche(lettre, false);
                        this.debuts = temp;
                        currentBranche = this.debuts[this.debuts.length - 1];
                    }
                }else{
                    Branche[] lettresSuivantes = currentBranche.getLettresSuivantes();
                    boolean lettreSuivanteExiste = false;

                    if(lettresSuivantes != null){
                        for(int j = 0; j < lettresSuivantes.length; j++){
                            if(lettresSuivantes[j].getLettre() == lettre){
                                currentBranche = currentBranche.getLettresSuivantes()[j];
                                lettreSuivanteExiste = true;

                                if(i == lettres.length - 1) currentBranche.setFinDeMot();
                                break;
                            }
                        }
                    }

                    if(!lettreSuivanteExiste){
                        currentBranche.ajouterLettreSuivante(new Branche(lettre, false));
                        currentBranche = currentBranche.getLettresSuivantes()[currentBranche.getLettresSuivantes().length - 1];
                        if(i == lettres.length - 1) currentBranche.setFinDeMot();
                    }
                }
            }
        }
    }

    public boolean existe(String mot){
        char[] lettres = mot.toCharArray();
        Branche currentBranche = null;

        for(int i = 0; i < lettres.length; i++){
            if(i == 0){
                boolean letterFound = false;
                for(int j = 0; j < this.debuts.length; j++){
                    if(this.debuts[j].getLettre() == lettres[i]){
                        currentBranche = this.debuts[j];
                        letterFound = true;
                        break;
                    }
                }

                if(!letterFound) return false;

            }else{
                boolean letterFound = false;
                Branche[] lettresSuivantes = currentBranche.getLettresSuivantes();
                if(lettresSuivantes == null) return false;

                for(int j = 0; j < lettresSuivantes.length; j++){
                    if(lettresSuivantes[j].getLettre() == lettres[i]){
                        currentBranche = lettresSuivantes[j];
                        letterFound = true;
                        break;
                    }
                }

                if(!letterFound) return false;

                if(i == lettres.length - 1) return currentBranche.estUneFinDeMotPossible;
            }
        }

        return false;
    }

    /*public static void main(String[] args) throws FileNotFoundException {
        Dico test = new Dico();

        System.out.println(test.existe("PAPIERR"));
        System.out.println(test.existe("BALADE"));
        System.out.println(test.existe("SYSTEME"));
        System.out.println(test.existe("DICTCIONNAIRE"));
    }*/

}

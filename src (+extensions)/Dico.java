import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Dico {
    private Branche[] debuts;
    private int nombreDeMots;

    public Dico() throws FileNotFoundException {
        File doc =
                new File("dicoReference.txt");
        Scanner obj = new Scanner(doc);
        Scanner objCount = new Scanner(doc);
        this.nombreDeMots = 0;
        while(objCount.hasNextLine()){
            this.nombreDeMots++;
            objCount.nextLine();
        }

        int line = 0;
        String loadingbar = "";
        while (obj.hasNextLine()){
            line++;
            String mot = obj.nextLine();

            System.out.print("\033[H\033[2J");
            System.out.flush();

            if(line % (this.nombreDeMots / 10) == 0) loadingbar += "\u2588";
            System.out.println(mot + "\n" + ((line*100)/this.nombreDeMots) + "% " + loadingbar);


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
                                debuts[j].increaseWord();
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
                                currentBranche.increaseWord();
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

    public String[] motsPossibles(double pourcentageRecherches){ /* rajouter MEE du joueur ? */
        int motsRecherches = (int) ((pourcentageRecherches * this.nombreDeMots) / 100);
        if(motsRecherches > this.nombreDeMots) motsRecherches = this.nombreDeMots;

        return this.recuperesXMots("",this.debuts, motsRecherches);
    }

    public String[] recuperesXMots(String lettrePrecedente, Branche[] branchesActuelles, int nombreDeMotsSouhaites){

        /* calcul du nombre de mots possibles avec les branches qui suivent */
        int motsSurLesBranches = 0;
        for(Branche b: branchesActuelles){
            motsSurLesBranches+= b.getWords();
        }


            /* calcul aléatoire de la répartition des recherches de mots sur les prochaines branches */
            /*int[]posMots = new int[nombreDeMotsSouhaites];
            for(int i = 0; i < posMots.length; i++){
                 int randomValue = Ut.randomMinMax(1, motsSurLesBranches);

                randomValue = posMots[i];
            }

            *//* lie une branche avec le nombre de mots à chercher dedans *//*
            int motsPrev = 0;
            int mots = 0;
            Map<Branche, Integer> motsParBranche = new HashMap<Branche, Integer>();
            for(int i = 0; i < branchesActuelles.length; i++){
                mots += branchesActuelles[i].getWords();

                for(int pos: posMots){
                    if(pos > motsPrev && pos <= mots){
                        if(motsParBranche.containsKey(branchesActuelles[i])){
                                motsParBranche.replace(branchesActuelles[i], motsParBranche.get(branchesActuelles[i]) + 1);
                        }else{
                            motsParBranche.put(branchesActuelles[i], 1);
                        }
                    }
                }
                motsPrev = mots;
            }*/


            Map<Branche, Integer> motsParBranche = new HashMap<Branche, Integer>();
            Branche[] branchesAImplementer = new Branche[branchesActuelles.length];
            for(int i = 0; i < branchesAImplementer.length; i++){
                branchesAImplementer[i] = branchesActuelles[i];
            }

            for(int i = 0; i < nombreDeMotsSouhaites; i++){
                boolean error = false;
                do{
                    int randomValue = Ut.randomMinMax(1, motsSurLesBranches);
                    int motsPrev = 0;
                    int mots = 0;

                    for(int j = 0; j < branchesAImplementer.length; j++){
                        mots += branchesAImplementer[j].getWords();

                        if(randomValue > motsPrev && randomValue <= mots){
                            if(motsParBranche.containsKey(branchesAImplementer[j])){
                                if(motsParBranche.get(branchesAImplementer[j]) == branchesAImplementer[j].getWords()){
                                    error = true;

                                    Branche[] temp = new Branche[branchesAImplementer.length];
                                    int l = 0;
                                    for(int k = 0; k < branchesAImplementer.length; k++){
                                        if(k != j){
                                            temp[l] = branchesAImplementer[k];
                                            l++;
                                        }
                                    }
                                    branchesActuelles = temp;
                                    break;
                                }else{
                                    motsParBranche.replace(branchesAImplementer[j], motsParBranche.get(branchesAImplementer[j]) + 1);
                                }
                            }else{
                                motsParBranche.put(branchesAImplementer[j], 1);
                            }
                        }

                        motsPrev = mots;
                    }

                }while(!error);



            }


        /* envisage une fin de mot */
        String[] res = new String[nombreDeMotsSouhaites];
        for(Branche b: branchesActuelles){
            if(motsParBranche.containsKey(b) && b.estUneFinDeMotPossible){
                if(Ut.randomMinMax(1, b.getWords()) == 1){
                    motsParBranche.replace(b, motsParBranche.get(b) - 1);
                    boolean estAjoute = false;
                    for(int i = 0; i < res.length; i++){
                        if(res[i] == null && !estAjoute){
                            res[i] = lettrePrecedente + b.getLettre();
                            if(motsParBranche.get(b) == 0) motsParBranche.remove(b);
                            estAjoute = true;
                        }
                    }
                }
            }
        }

        /* récupères la suite des mots cherchés dans les branches suivantes */
        for(Branche b: branchesActuelles){
            if(motsParBranche.containsKey(b)){
                String[] values = this.recuperesXMots(String.valueOf(b.getLettre()), b.getLettresSuivantes(), motsParBranche.get(b));
                int valuesIndex = 0;
                for(int i = 0; i < res.length; i++){
                    if(res[i] == null){
                        res[i] = lettrePrecedente + values[valuesIndex];
                        valuesIndex++;
                    }

                    if(valuesIndex == values.length) break;
                }
            }
        }

        return res;
    }



    public static void main(String[] args) throws FileNotFoundException {
        Dico test = new Dico();

        String[] recherches = test.motsPossibles(0.50);

        for(String val: recherches){
            System.out.println(val);
        }
    }

}

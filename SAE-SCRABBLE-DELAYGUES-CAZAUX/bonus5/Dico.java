/*
* extensions : 3.1
*
* (3.1)
* Le dictionnaire se crée en même temps que le Plateau. Celui-ci utilise un arbre
* lexicographique.
* Sa structure est faite de manière à pouvoir connaitre la répartition des mots le
* long de l'arbre, ce qui nous est utile par la suite pour l'IA.
* */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Dico {
    private Branche[] debuts;
    private int nombreDeMots;


    /*
    * prérequis : le fichier "dicoReference.txt" existe et est à la racine do dossier
    * action : constructeur de la classe Dico
    * */
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

    /*
    * prérequis : mot est un mot formé de lettres en majuscule
    * résultat : vrai ssi mot est un mot appartenant au dictionnaire
    * */
    public boolean existe(String mot){
        if(mot != mot.toUpperCase()) return false;
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

    /*
    * prérequis : 0 < pourcentageRecherches <= 100
    * résultat : retourne un pourcentage de mots issus du dictionnaire
    * */
    public String[] motsPossibles(double pourcentageRecherches){
        int motsRecherches = (int) ((pourcentageRecherches * this.nombreDeMots) / 100);
        if(motsRecherches > this.nombreDeMots) motsRecherches = this.nombreDeMots;

        return this.recuperesXMots("",this.debuts, motsRecherches);
    }

    /*
    * prérequis : 0 <= lettrePrecedente.length() < 2, branchesActuelles est formé des lettres qui suivent dans le mot, 0 < nombreDeMotsSouhaites <= this.nombreDeMots
    * résultat : retourne une liste de mots complets ou incomplets (en fonction du stade de la recherche) présents dans le dictionnaire
    * */
    public String[] recuperesXMots(String lettrePrecedente, Branche[] branchesActuelles, int nombreDeMotsSouhaites){

            /* calcul aléatoire de la répartition des recherches de mots sur les prochaines branches */
            int motsSurLesBranches;
            Map<Branche, Integer> motsParBranche = new HashMap<Branche, Integer>(); /* mots attribués sur une branche */
            Map<Branche, Integer> motsChoisissables = new HashMap<Branche, Integer>(); /* espaces disponibles sur une branche */
            for(Branche b: branchesActuelles){
                motsChoisissables.put(b, b.getWords());
            }

            for(int i = 0; i < nombreDeMotsSouhaites; i++){
                Branche[] branchesChoisissables = new Branche[branchesActuelles.length];
                motsSurLesBranches = 0;
                for(int j = 0; j < branchesActuelles.length; j++){
                    if(motsChoisissables.get(branchesActuelles[j]) > 0){
                        branchesChoisissables[j] = branchesActuelles[j];
                        motsSurLesBranches+= motsChoisissables.get(branchesChoisissables[j]);
                    }
                }

                int randomValue = Ut.randomMinMax(1, motsSurLesBranches);
                int motsPrev = 0;
                int mots = 0;

                /* pour chaque branche que l'on peut encore choisir, on regarde à laquelle correcpond la valeur de l'aléatoire */
                for(int j = 0; j < branchesChoisissables.length; j++){
                    if(branchesChoisissables[j] != null){
                        mots += motsChoisissables.get(branchesChoisissables[j]);

                        /* affectation des lettres sur une branche */
                        if(randomValue > motsPrev && randomValue <= mots){
                            if(motsParBranche.containsKey(branchesChoisissables[j])){
                                motsParBranche.replace(branchesChoisissables[j], motsParBranche.get(branchesChoisissables[j]) + 1);
                            }else{
                                motsParBranche.put(branchesChoisissables[j], 1);
                            }

                            motsChoisissables.replace(branchesChoisissables[j], motsChoisissables.get(branchesChoisissables[j]) - 1);
                        }

                        motsPrev = mots;
                    }

                }
            }


        /* envisage une fin de mot */
        String[] res = new String[nombreDeMotsSouhaites];
        for(Branche b: branchesActuelles){
            if(motsParBranche.containsKey(b) && b.estUneFinDeMotPossible){
                if(Ut.randomMinMax(1, b.getWords()) == 1 || motsParBranche.get(b) == b.getWords()){
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
}

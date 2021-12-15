public class Plateau {
    private Case[][] g; // g pour grille
    private static int[][] casesSpeciales = new int[][] {
            {0,0,5},
            {1,1,4},
            {2,2,4},
            {3,3,4},
            {4,4,4},
            {5,5,3},
            {6,6,2},
            {7,7,4},
            {0,3,2},
            {0,7,5},
            {1,5,3},
            {2,6,2},
            {3,7,2}
    };

    public Plateau(){
        this.g = new Case[15][15];
        for(int i = 0; i < g.length; i++){
            for(int j = 0; j < g[0].length; j++){
                g[i][j] = new Case(1);
            }
        }

        //R = 0°
        fillPlateau(g, casesSpeciales);

        //R = 45°
        for(int i=0; i < casesSpeciales.length; i++){
            casesSpeciales[i][1] = 14 - casesSpeciales[i][1];
        }
        fillPlateau(g, casesSpeciales);

        //R = 90°
        for(int i=0; i < casesSpeciales.length; i++){
            casesSpeciales[i][0] = 14 - casesSpeciales[i][0];
            casesSpeciales[i][1] = 14 - casesSpeciales[i][1];
            int temp = casesSpeciales[i][0];
            casesSpeciales[i][0] = casesSpeciales[i][1];
            casesSpeciales[i][1] = temp;
        }
        fillPlateau(g, casesSpeciales);

        //R = 135°
        for(int i=0; i < casesSpeciales.length; i++){
            casesSpeciales[i][0] = 14 - casesSpeciales[i][0];
        }
        fillPlateau(g, casesSpeciales);

        //R = 180°
        for(int i=0; i < casesSpeciales.length; i++){
            int temp = casesSpeciales[i][0];
            casesSpeciales[i][0] = casesSpeciales[i][1];
            casesSpeciales[i][1] = temp;
        }
        fillPlateau(g, casesSpeciales);

        //R = 225°
        for(int i=0; i < casesSpeciales.length; i++){
            casesSpeciales[i][1] = 14 - casesSpeciales[i][1];
        }
        fillPlateau(g, casesSpeciales);

        //R = 270°
        for(int i=0; i < casesSpeciales.length; i++){
            casesSpeciales[i][0] = 14 - casesSpeciales[i][0];
            casesSpeciales[i][1] = 14 - casesSpeciales[i][1];
            int temp = casesSpeciales[i][0];
            casesSpeciales[i][0] = casesSpeciales[i][1];
            casesSpeciales[i][1] = temp;
        }
        fillPlateau(g, casesSpeciales);

        //R = 315°
        for(int i=0; i < casesSpeciales.length; i++){
            casesSpeciales[i][0] = 14 - casesSpeciales[i][0];
        }
        fillPlateau(g, casesSpeciales);
    }

    public static Case[][] fillPlateau(Case[][] g, int[][] schema){
        for(int i = 0; i < Plateau.casesSpeciales.length; i++){
            if(g[Plateau.casesSpeciales[i][0]][Plateau.casesSpeciales[i][1]].getCouleur() == 1){
                g[Plateau.casesSpeciales[i][0]][Plateau.casesSpeciales[i][1]] = new Case(Plateau.casesSpeciales[i][2]);
            }
        }

        return g;
    }

    public String toString(){
        String res = "";
        for(int i = -1; i < this.g.length; i++){
            if(i == -1){
                res += "    |";
            }else if(i == this.g.length - 1){
                res += " " + i;
            }else{
                if(i < 10){
                    res += "  " + i + " |";
                }else{
                    res += " " + i + " |";
                }
            }
        }
        res+= "\n";

        for(int i = 0; i <= this.g.length; i++){
            if(i == this.g.length){
                res += "----";
            }else{
                res += "----|";
            }
        }
        res += "\n";

        for(int i = 0; i < this.g.length; i++){
            for(int j = -1; j < this.g[0].length; j++){
                if(j == -1){
                    if(i < 10){
                        res += "  " + i + " |";
                    }else{
                        res += " " + i + " |";
                    }
                }else{
                    if(this.g[i][j].estRecouverte()){
                        res += "  " + g[i][j].getLettre() + " ";
                        if(j != this.g[0].length - 1) res += "|";
                    }else{
                        res += "  " + g[i][j].getCouleur() + " ";
                        if(j != this.g[0].length - 1) res += "|";
                    }
                }
            }
            res += "\n";

            if(i < this.g.length - 1){
                for(int j = -1; j < this.g[0].length; j++){
                    if(j == this.g.length - 1){
                        res += "----";
                    }else{
                        res += "----|";
                    }
                }
                res += "\n";
            }
        }

        return res;
    }

    /**
     * pré-requis : mot est un mot accepté par CapeloDico,
     *     0 <= numLig <= 14, 0 <= numCol <= 14, sens est un élément
     *     de {’h’,’v’} et l’entier maximum prévu pour e est au moins 25
     *  résultat : retourne vrai ssi le placement de mot sur this à partir
     *     de la case (numLig, numCol) dans le sens donné par sens à l’aide
     *     des jetons de e est valide.
     */
    public boolean placementValide(String mot, int numLig, int numCol, char sens, MEE e) {
        char[] letters = mot.toCharArray();
        MEE eCopy = new MEE(e);

        /*
        * vérifier que mot rentre
        * - soit la case n'existe pas (trop court) return false
        * - soit la case est vide ou contient la bonne lettre return true à la fin
        * - soit la case contient qqchose et ce n'est pas la bonne lettre return false
        * */
        int col = numCol;
        int lig = numLig;
        for(int i = 0; i < letters.length; i++){
            if(sens == 'v') lig = numLig + i;
            if(sens == 'h') col = numCol + i;

            if(lig >= 15 || col >= 15) return false;

            if(this.g[col][lig].estRecouverte() && this.g[col][lig].getLettre() != letters[i]) return false;
            /*on va créer une list ede cases pour les lettres déjà placées qu'on ne va pas compter dans le chevalet*/
        }

        /*
         * vérifier que e contient les lettres du mot
         * - dupliquer e
         * - enlever chaque lettre une à une, si erreur alors false
         */
        for(int i = 0; i < letters.length; i++){
            if(!eCopy.retireLettre(letters[i])) return false;
        }

        return true;
    }

    public static void main(String[] args){
        MEE tests = new MEE(new int[] {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
        Plateau p = new Plateau();
        System.out.println(p.placementValide("AABC", 0, 0, 'v', tests));
    }

}
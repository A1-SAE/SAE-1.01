public class Plateau {
    private Case[][] g; // g pour grille
    private static int[][] plateau = {
            {5,1,1,2,1,1,1,5,1,1,1,2,1,1,5},
            {1,4,1,1,1,3,1,1,1,3,1,1,1,4,1},
            {1,1,4,1,1,1,2,1,2,1,1,1,4,1,1},
            {2,1,1,4,1,1,1,2,1,1,1,4,1,1,2},
            {1,1,1,1,4,1,1,1,1,1,4,1,1,1,1},
            {1,3,1,1,1,3,1,1,1,3,1,1,1,3,1},
            {1,1,2,1,1,1,2,1,2,1,1,1,2,1,1},
            {5,1,1,2,1,1,1,4,1,1,1,2,1,1,5},
            {1,1,2,1,1,1,2,1,2,1,1,1,2,1,1},
            {1,3,1,1,1,3,1,1,1,3,1,1,1,3,1},
            {1,1,1,1,4,1,1,1,1,1,4,1,1,1,1},
            {2,1,1,4,1,1,1,2,1,1,1,4,1,1,2},
            {1,1,4,1,1,1,2,1,2,1,1,1,4,1,1},
            {1,4,1,1,1,3,1,1,1,3,1,1,1,4,1},
            {5,1,1,2,1,1,1,5,1,1,1,2,1,1,5}};

    public Plateau(){
        this.g = new Case[15][15];
        for(int i = 0; i < this.g.length; i++){
            for(int j = 0; j < this.g[0].length; j++){
                this.g[i][j] = new Case(Plateau.plateau[i][j]);
            }
        }
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
        char[] lettres = mot.toCharArray();
        MEE eCopy = new MEE(e);
        int col = numCol;
        int lig = numLig;
        if(sens == 'v') lig = numLig + lettres.length - 1;
        if(sens == 'h') col = numCol + lettres.length - 1;

        /* vérifie si le plateau est vide */
        boolean estVide = true;
        for(int i = 0; i < this.g.length; i++){
            for(int j = 0; j < this.g[0].length; j++){
                if(this.g[i][j].estRecouverte()) estVide = false;
            }
        }

        if(estVide){
            /*
            * Dans le cas où le plateau est vide, la méthode placementValide doit vérifier que :
            * — le mot proposé a au moins 2 lettres,
            * — la zone de placement du mot (suite de cases consécutives - de la longueur du mot - à
            * partir de la case de sa première lettre, dans le sens de placement du mot) contient la
            * case centrale du plateau,
            * — l’ensemble e, correspondant au chevalet du joueur proposant le mot, contient les jetons
            * permettant de former le mot.
            * */

            /* vérifie que mot >= 2 lettres */
            if(lettres.length < 2) return false;

            /* vérifie que le placement du mot contient la case centrale */
            if(!(numLig <= 7 && 7 <= lig && numCol <= 7 && 7 <= col)) return false;

            /* vérifie que chevalet contient jetons */
            for(int i = 0; i < lettres.length; i++){
                if(!eCopy.retireLettre(lettres[i])) return false;
            }
        }else{
            /*
            * Dans le cas où le plateau n’est pas vide, la méthode placementValide vérifie que :
            * — la zone de placement du mot ne dépasse pas de la grille,
            * — cette zone n’est ni précédée ni suivie d’une case recouverte par un jeton; autrement
            * dit, elle est précédée (respectivement suivie) du bord de la grille ou d’une case non
            * recouverte,
            * — cette zone contient au moins une case non recouverte par un jeton,
            * — cette zone contient au moins une case recouverte par un jeton,
            * — pour chaque case recouverte par un jeton de la zone de placement du mot, la lettre du
            * jeton est la même que celle du mot à placer dans cette case,
            * — l’ensemble e, correspondant au chevalet du joueur proposant le mot, contient les jetons
            * (non déjà présents sur le plateau) permettant de former le mot.
            * */

            /* vérifie que le mot ne dépasse pas */
            if(lig > 14 || col > 14) return false;

            /* vérification non précédée ou suivie d'une case recouverte */
            if((sens == 'v' && (numCol - 1 < 0 || this.g[numLig][numCol - 1].estRecouverte())) || (sens == 'h' && (numLig - 1 < 0 || this.g[numLig - 1][numCol].estRecouverte()))) return false;

            /*
            * vérifications :
            * - contient au moins une non recouverte
            * - une recouverte
            * - les lettres recouvertes correspondent
            * - le chevalet contient les lettres
            *  */
            int lig2 = numLig;
            int col2 = numCol;
            boolean contientRecouverte = false;
            boolean contientNonRecouverte = false;

            for(int i = 0; i < lettres.length; i++){
                if(sens == 'v') lig2 = numLig + i;
                if(sens == 'h') col2 = numCol + i;

                if(this.g[lig2][col2].estRecouverte()){
                    contientRecouverte = true;
                    if(this.g[lig2][col2].getLettre() != lettres[i]) return false;
                }else{
                    contientNonRecouverte = true;
                    if(!eCopy.retireLettre(lettres[i])) return false;
                }
            }
            if(!(contientNonRecouverte && contientRecouverte)) return false;
        }

        return true;
    }

    public static void main(String[] args){
        MEE tests = new MEE(new int[] {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
        Plateau p = new Plateau();
        System.out.println(p.placementValide("ABC", 7, 5, 'h', tests));
    }

}
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

    /*
    * action : constructeur de Plateau avec une grille vierge
    * */
    public Plateau(){
        this.g = new Case[15][15];
        for(int i = 0; i < this.g.length; i++){
            for(int j = 0; j < this.g[0].length; j++){
                this.g[i][j] = new Case(Plateau.plateau[i][j]);
            }
        }
    }

    /*
    * action : constructeur de Plateau avec une grille donnée
    * */
    public Plateau(Case[][] plateau){
        this.g = plateau;
    }

    /*
    * résultat : chaîne décrivant ce plateau
    * */
    public String toString(){
        String res = "";
        String[] tabAlphabet = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"};
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
                        res += "  " + tabAlphabet[i] + " |";
                    }else{
                        res += "  " + tabAlphabet[i] + " |";
                    }
                }else{
                    res += "  " + g[i][j] + " ";
                    if(j != this.g[0].length - 1) res += "|";
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
        /* CapeloDico */
        char estValide = ' ';
        do {
            System.out.println("[CapeloDico] Le mot suivant est-t-il valide et en majuscules ? (oui : o, non : n) : " + mot);
            estValide = Ut.saisirCaractere();
        }while(!(estValide == 'o' || estValide == 'n'));
        if(estValide == 'n') return false;

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
            if(sens == 'h'){
                if(numCol > 0 && this.g[numLig][numCol - 1].estRecouverte()) return false;
                if(numCol + mot.length() <= 14 && this.g[numLig][numCol + mot.length()].estRecouverte()) return false;
            }else{
                if(numCol > 0 && this.g[numLig - 1][numCol].estRecouverte()) return false;
                if(numLig + mot.length() <= 14 && this.g[numLig + mot.length()][numCol].estRecouverte()) return false;
            }


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

    /**
     * pré-requis : le placement de mot sur this à partir de la case
     *  (numLig, numCol) dans le sens donné par sens est valide
     * résultat : retourne le nombre de points rapportés par ce placement, le
     *  nombre de points de chaque jeton étant donné par le tableau nbPointsJet.
     */
    public int nbPointsPlacement(String mot, int numLig, int numCol, char sens, int[] nbPointsJet) {
        char[] lettres = mot.toCharArray();
        char[] convert = new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        int res = 0;
        int multiplyWord = 1;
        int faitScrabble = 0;

        int col = numCol;
        int lig = numLig;
        for(int i = 0; i < lettres.length; i++){
            if(sens == 'h') col = numCol + i;
            if(sens == 'v') lig = numLig + i;

            int letterPos = 0;
            for(int j = 0; j < convert.length; j++){
                if(lettres[i] == convert[j]) letterPos = j;
            }

            int caseColor = 1;
            if(!this.g[lig][col].estRecouverte()) caseColor = this.g[lig][col].getCouleur();
            int multiplyLetter = 1;
            if(caseColor <= 3){
                multiplyLetter = caseColor;
            }else{
                if(caseColor == 4) multiplyWord *= 2;
                if(caseColor == 5) multiplyWord *= 3;
            }

            res += (nbPointsJet[letterPos] * multiplyLetter);

            if(!this.g[lig][col].estRecouverte()) faitScrabble++;
        }

        res *= multiplyWord;
        if(faitScrabble == 7) res += 50;
        return res;
    }

    /**
     * pré-requis : le placement de mot sur this à partir de la case
     *    (numLig, numCol) dans le sens donné par sens à l’aide des
     *    jetons de e est valide.
     *  action/résultat : effectue ce placement et retourne le
     *    nombre de jetons retirés de e.
     */
    public int place(String mot, int numLig, int numCol, char sens, MEE e){
        char[] lettres = mot.toCharArray();
        int col = numCol;
        int lig = numLig;
        int res = 0;

        for(int i = 0; i < lettres.length; i++){
            if(sens == 'h') col = numCol + i;
            if(sens == 'v') lig = numLig + i;

            if(!this.g[lig][col].estRecouverte()){
                this.g[lig][col].setLettre(lettres[i]);
                e.retireLettre(lettres[i]);
                res++;
            }

        }

        return res;
    }
}
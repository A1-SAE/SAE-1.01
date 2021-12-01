public class MEE {
    private int[] tabFreq; // tabFreq[i] est le nombre d’exemplaires
                           // (fréquence) de l’élément i
    private int nbTotEx; // nombre total d’exemplaires

    /**
     *  pré-requis : max >= 0
     *  action : crée un multi-ensemble vide dont les éléments seront
     *          inférieurs à max
     */
    public MEE (int max){
        this.tabFreq = new int[max];
        nbTotEx = 0;
    }


    /**
     *  pré-requis : les éléments de tab sont positifs ou nuls
     *  action : crée un multi-ensemble dont le tableau de fréquences est
     *.          une copie de tab
     */
    public MEE (int[] tab){
        this.tabFreq = new int[tab.length - 1];

        for(int i = 0; i < tab.length; i++){
            this.tabFreq[i] = tab[i];
            this.nbTotEx += tab[i];
        }
    }

    /**
     * constructeur par copie
     */
    public MEE (MEE e){
        this.nbTotEx = e.nbTotEx;
        this.tabFreq = new int[e.tabFreq.length];

        for(int i = 0; i < this.tabFreq.length; i++){
            this.tabFreq[i] = e.tabFreq[i];
        }
    }

    /**
     *  résultat : vrai ssi cet ensemble est vide
     */
    public boolean estVide (){
        return this.nbTotEx == 0;
    }

    /**
     * pré-requis : 0 <= i < tabFreq.length
     * action : ajoute un exemplaire de i à this
     */
    public void ajoute (int i) {
        this.tabFreq[i] = this.tabFreq[i] + 1;
        this.nbTotEx++;
    }

    /**
     * pré-requis : 0 <= i < tabFreq.length
     * action/résultat : retire un exemplaire de i de this s’il en existe,
     *    et retourne vrai ssi cette action a pu être effectuée
     */
    public boolean retire (int i) {
        if(this.tabFreq[i] > 0){
            this.tabFreq[i] = this.tabFreq[i] - 1;
            this.nbTotEx--;
            return true;
        }

        return false;
    }

    /**
     * pré-requis : this est non vide
     * action/résultat : retire de this un exemplaire choisi aléatoirement
     *                   et le retourne
     */
    public int retireAleat () {
        int nbAleatoire = (int) (Math.random() * (this.tabFreq.length));

        this.retire(nbAleatoire);
        return nbAleatoire;
    }

    /**
     * pré-requis : 0 <= i < tabFreq.length
     * action/résultat : transfère un exemplaire de i de this vers e s’il
     *     en existe, et retourne vrai ssi cette action a pu être effectuée
     */
    public boolean transfere (MEE e, int i) {
        if(this.tabFreq[i] > 0){
            this.retire(i);
            e.ajoute(i);
            return true;
        }

        return false;
    }

    /** pré-requis : k >= 0
     *  action : tranfère k exemplaires choisis aléatoirement de this vers e
     *           dans la limite du contenu de this
     */
    public void transfereAleat (MEE e, int k) {
        for(int i = 0; i < k; i++){
            int nbAleatoire = (int) (Math.random() * (this.tabFreq.length));
            if(!this.transfere(e, nbAleatoire)) i--;
        }
    }

    /**
     * pré-requis : tabFreq.length <= v.length
     * résultat : retourne la somme des valeurs des exemplaires des
     *     éléments de this, la valeur d’un exemplaire d’un élément i
     *     de this étant égale à v[i]
     */
    public int sommeValeurs (int[] v){
        int res = 0;
        for(int i = 0; i < v.length; i++){
            res += this.tabFreq[v[i]];
        }

        return res;
    }





        public int getNbTotEx(){
        return this.nbTotEx;
    }
}

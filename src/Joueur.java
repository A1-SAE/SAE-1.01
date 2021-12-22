public class Joueur{
	private String nom;
	private MEE chevalet;
	private int score;

	public Joueur(String unNom){
		this.nom = unNom;
	}

	public String toString(){
		return this.nom + " " + this.chevalet + " " + this.score;
	}

	public int getScore(){
		return this.score;
	}

	public int ajouteScore(int nb){
		return this.score + nb;
	}

	/**
	 * pré-requis : nbPointsJet indique le nombre de points rapportés par
	 * 				chaque jeton/lettre
	 * résultat : le nombre de points total sur le chevalet de ce joueur
	 * suggestion : bien relire la classe MEE !
	 */
	public int nbPointsChevalet(MEE e, int[] nbPointJet){
		int res = 0;

		// heu nan c'est la merde enfaite
	}

	/**
	 * pré-requis : les éléments de s sont inférieurs à 26
	 * action : simule la prise de nbJetons jetons par this dans le sac s,
	 * 			dans la limite de son contenu.
	 */
	public void prendJetons (MEE s, int nbJetons) {
		s.retire(nbJetons);
	}

	/**
	 * pré-requis : les éléments de s sont inférieurs à 26
	 * 				et nbPointsJet.length >= 26
	 * action : simule le coup de this :  this choisit de passer son tour,
	 * 			d’échanger des jetons ou de placer un mot
	 * résultat : -1 si this a passé son tour, 1 si son chevalet est vide,
	 * 			  et 0 sinon
	 */
	public int joue(Plateau p, MEE s, int[] nbPointJet){
		int res=0;

		// Manque Plateau

		return res;
	}

	/** pré-requis : les éléments de s sont inférieurs à 26
	* et nbPointsJet.length >= 26
	* action : simule le placement d’un mot de this :
	* a) le mot, sa position sur le plateau et sa direction, sont saisis
	* au clavier
	* b) vérifie si le mot est valide
	* c) si le coup est valide, le mot est placé sur le plateau
	* résultat : vrai ssi ce coup est valide, c’est-à-dire accepté par
	* CapeloDico et satisfaisant les règles détaillées plus haut
	* stratégie : utilise la méthode joueMotAux
	*/
	public boolean joueMot(Plateau p, MEE s, int[] nbPointsJet) {
		boolean res = false;

		// Manque Plateau

		return res;
	}

	/** pré-requis : cf. joueMot et le placement de mot à partir de la case
	* (numLig, numCol) dans le sens donné par sens est valide
	* action : simule le placement d’un mot de this
	*/
	public void joueMotAux(Plateau p, MEE s, int[] nbPointsJet, String mot, int numLig, int numCol, char sens) {
		// Manque Plateau
	}


	/**
	* pré-requis : sac peut contenir des entiers de 0 à 25
	* action : simule l’échange de jetons de ce joueur :
	* - saisie de la suite de lettres du chevalet à échanger
	* en vérifiant que la suite soit correcte
	* - échange de jetons entre le chevalet du joueur et le sac
	* stratégie : appelle les méthodes estCorrectPourEchange et echangeJetonsAux
	*/
	public void echangeJetons(MEE sac) {
		if(estCorrectPourEchange("A")){
			echangeJetonsAux(sac, "A");
		}
	}

	/** résultat : vrai ssi les caractères de mot correspondent tous à des
	* lettres majuscules et l’ensemble de ces caractères est un
	* sous-ensemble des jetons du chevalet de this
	*/
	public boolean estCorrectPourEchange (String mot) {
		boolean res = false;
		
		if(mot.isUpperCase() /*&& contient mot dans son chevalet*/){
			res = true;
		}

		return res;
	}

	/** pré-requis : sac peut contenir des entiers de 0 à 25 et ensJetons
	* est un ensemble de jetons correct pour l’échange
	* action : simule l’échange de jetons de ensJetons avec des
	* jetons du sac tirés aléatoirement.
	*/
	public void echangeJetonsAux(MEE sac, String ensJetons) {
		this.chevalet.retire(/*ensJetons <-- C'est un String et nous on veut un INT dans retire()...*/);
		sac.transfereAleat(this.chevalet, /*nbJetons de ensJetons*/);
	}



	public static void main(String[] args) {
		int[] nbPointJet = new int[]{1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 10, 1, 2, 1, 1, 3, 8, 1, 1, 1, 1, 4, 10, 10, 10, 10};
		//                           A  B  C  D  E  F  G  H  I  J   K  L  M  N  O  P  Q  R  S  T  U  V   W   X   Y   Z
	}
}
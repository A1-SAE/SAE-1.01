public class Joueur{
	private String nom;
	private MEE chevalet;
	private int score;

	public Joueur(String unNom){
		this.nom = unNom;
		this.chevalet = new MEE(26);
		this.score = 0;
	}

	public String toString(){
		return this.nom + " | " + this.chevalet + " | " + this.score;
	}

	public int getScore(){
		return this.score;
	}

	public void ajouteScore(int nb){
		this.score = this.score + nb;
	}

	/**
	 * pré-requis : nbPointsJet indique le nombre de points rapportés par
	 * 				chaque jeton/lettre
	 * résultat : le nombre de points total sur le chevalet de ce joueur
	 * suggestion : bien relire la classe MEE !
	 */
	public int nbPointsChevalet(int[] nbPointJet){
		int res = 0;
		int tabFreq[] = this.chevalet.getTabFreq();

		for(int i = 0; i < tabFreq.length; i++){
			res += tabFreq[i] * nbPointJet[i];
		}

		return res;
	}

	/**
	 * pré-requis : les éléments de s sont inférieurs à 26
	 * action : simule la prise de nbJetons jetons par this dans le sac s,
	 * 			dans la limite de son contenu.
	 */
	public void prendJetons (MEE s, int nbJetons) {
		s.transfereAleat(this.chevalet, nbJetons);
	}

	/**
	 * pré-requis : les éléments de s sont inférieurs à 26
	 * 				et nbPointsJet.length >= 26
	 * action : simule le coup de this : this choisit de passer son tour,
	 * 			d’échanger des jetons ou de placer un mot
	 * résultat : -1 si this a passé son tour, 1 si son chevalet est vide,
	 * 			  et 0 sinon
	 */
	public int joue(Plateau p, MEE s, int[] nbPointJet){
		int choix;
		do{
			System.out.println("\nTour du joueur : " + this.getNom() + "\n" +
					"[1] Placer un mot\n" +
					"[2] Passer son tour\n" +
					"[3] Défausser une ou des lettres");
			choix = Ut.saisirEntier();
		}while(!(choix >= 1 && choix <= 3));

		switch(choix){
			case 1:
				/* placer un mot */
				boolean placementReussi = false;
				while(!placementReussi){
					placementReussi = this.joueMot(p, s, nbPointJet);
				}

				break;
			case 2:
				/* passer son tour */

				break;
			case 3:
				/* Defausser une ou des lettres */
				this.echangeJetons(s);

				break;
		}

		return choix;
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
		String mot = "";
		int[] pos = new int[] {-1, -1};
		char dir = ' ';
		System.out.println("Veuillez entrer le mot que vous souhaitez jouer : ");
		mot = Ut.saisirChaine();
		System.out.println("Veuillez entrer la ligne de départ du mot : ");
		pos[0] = Ut.saisirEntier();
		System.out.println("Veuillez entrer la colonne de départ du mot : ");
		pos[1] = Ut.saisirEntier();
		System.out.println("Veuillez choisir le sens de placement ('h' pour horizontal et 'v' pour vertical) : ");
		dir = Ut.saisirCaractere();

		if(!((mot.length() >= 2) && (dir == 'v' || dir == 'h') && (pos[0] >= 0 && pos[0] <= 14) && (pos[1] >= 0 && pos[1] <= 14) && p.placementValide(mot, pos[0], pos[1], dir, this.getChevalet()))) return false;

		this.joueMotAux(p, s, nbPointsJet, mot, pos[0], pos[1], dir);
		return true;
	}

	/** pré-requis : cf. joueMot et le placement de mot à partir de la case
	* (numLig, numCol) dans le sens donné par sens est valide
	* action : simule le placement d’un mot de this
	*/
	public void joueMotAux(Plateau p, MEE s, int[] nbPointsJet, String mot, int numLig, int numCol, char sens) {

		/* incrémente le score du joueur */
		this.ajouteScore(p.nbPointsPlacement(mot, numLig, numCol, sens, nbPointsJet));

		/* repioche des jetons jusqu'à 7 */
		int jetonsEnleves = p.place(mot, numLig, numCol, sens, this.getChevalet());
		if(jetonsEnleves > s.getNbTotEx()) jetonsEnleves = s.getNbTotEx();
		this.prendJetons(s, jetonsEnleves);
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
		String letters;
		do{
			System.out.println("Quelles lettres souhaitez-vous défausser (entrez les lettres à la suite comme ceci : 'ABCD') : ");
			letters = Ut.saisirChaine();
		}while(!this.estCorrectPourEchange(letters));

		this.echangeJetonsAux(sac, letters);
	}

	/** résultat : vrai ssi les caractères de mot correspondent tous à des
	* lettres majuscules et l’ensemble de ces caractères est un
	* sous-ensemble des jetons du chevalet de this
	*/
	public boolean estCorrectPourEchange (String mot) {
		if(mot.length() == 0 || !mot.equals(mot.toUpperCase())) return false;

		char[] lettres = mot.toCharArray();
		
		MEE copieChevalet = new MEE(this.chevalet);

		for (char lettre : lettres) {
			if (!copieChevalet.retireLettre(lettre)) return false;
		}

		return true;
	}

	/** pré-requis : sac peut contenir des entiers de 0 à 25 et ensJetons
	* est un ensemble de jetons correct pour l’échange
	* action : simule l’échange de jetons de ensJetons avec des
	* jetons du sac tirés aléatoirement.
	*/
	public void echangeJetonsAux(MEE sac, String ensJetons) {
		char[] jetons = ensJetons.toCharArray();

		for(int i = 0; i < jetons.length; i++){
			if(sac.estVide()) return;

			this.chevalet.retireLettre(jetons[i]);
			sac.transfereAleat(this.chevalet, 1);
		}
	}

	public String getNom(){ return this.nom; }

	public MEE getChevalet(){ return this.chevalet; }
}
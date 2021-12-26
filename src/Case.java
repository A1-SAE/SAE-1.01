public class Case{
	private int coul;
	private char lettre; /* ' ' correspond à l'absence de lettre */

	/*
	* prérequis : uneCouleur est un entier entre 1 et 5
	* action : constructeur de Case
	* */
	public Case (int uneCouleur){
		this.coul = uneCouleur;
		this.lettre = ' ';
	}

	/*
	* résultat : la couleur de this, un nombre compris entre 1 et 5
	* */
	public int getCouleur(){
		return this.coul;
	}

	/*
	* pré-requis : cette case est recouverte
	* résultat : retourne la lettre sur une case
	* */
	public char getLettre(){
		return this.lettre;
	}

	/*
	* pré-requis : let est une lettre majuscule
	* action : place une lettre sur la case
	* */
	public void setLettre(char let){
		this.lettre = let;
	}

	/*
	* résultat : vrai ssi la case est recouverte par une lettre
	* */
	public boolean estRecouverte(){
		return !(this.lettre == ' ');
	}

	/*
	* résultat : retourne la couleur de la case ou si elle est recouverte sa lettre
	* */
	public String toString(){
		if(this.estRecouverte()) return String.valueOf(this.lettre);

		return String.valueOf(this.coul);
	}
}

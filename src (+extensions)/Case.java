public class Case{
	private int coul;
	private char lettre;

	public Case (int uneCouleur){
		if(uneCouleur >= 1 && uneCouleur <= 5){
			this.coul = uneCouleur;
			this.lettre = ' ';
		}
	}

	public int getCouleur(){
		return this.coul;
	}

	public char getLettre(){
		return this.lettre;
	}

	public void setLettre(char let){			// PEUT ETRE A MODIFIER
		this.lettre = let;
	}

	public boolean estRecouverte(){
		return this.lettre != ' ';
	}

	public String toString(){
		if(this.estRecouverte()) return String.valueOf(this.lettre);

		return String.valueOf(this.coul);
	}
}

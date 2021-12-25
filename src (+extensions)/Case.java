public class Case{
	private int coul;
	private char lettre;
	boolean estJoker;

	public Case (int uneCouleur){
		if(uneCouleur >= 1 && uneCouleur <= 5){
			this.coul = uneCouleur;
			this.lettre = ' ';
			this.estJoker = false;
		}
	}

	public int getCouleur(){
		return this.coul;
	}

	public char getLettre(){
		return this.lettre;
	}

	public void setLettre(char let, boolean estJoker){
		this.lettre = let;
		this.estJoker = estJoker;
	}

	public boolean estRecouverte(){
		return this.lettre != ' ';
	}

	public String toString(){
		if(this.estRecouverte()){
			if(this.estJoker) return "*" + this.lettre;

			return " " + this.lettre;
		}

		return " " + this.coul;
	}
}

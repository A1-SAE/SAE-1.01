public class Case{
	private int coul;
	private char lettre;

	public Case (int uneCouleur){
		if(uneCouleur >= 1 && uneCouleur <= 5){
			this.coul = uneCouleur;
		}
	}

	public int getCouleur(){
		return this.coul;
	}

	public char getLettre(){
		if(this.estRecouverte()){
			return this.lettre;
		}
	}

	public void setLettre(char let){
		if(!(let == Ut.indexToAlpha(let))){
			let = Ut.indexToAlpha(let);
		}
	}

	public boolean estRecouverte(){
		boolean res = false;
		if(/* Condition : Lettre sur la case*/){
			res = true;
		}
		return res;
	}

	public String toString(){
		return this.coul + " " + this.lettre;
	}
}
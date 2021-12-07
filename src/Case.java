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

		return 0; //gestion des erreurs (doit forcément retourner un truc)
	}

	public void setLettre(char let){
		if(!(let == Ut.indexToAlpha(let))){
			this.lettre = Ut.indexToAlpha(let);
		}
	}

	public boolean estRecouverte(){
		return !(this.lettre == ' ');
	}

	public String toString(){
		return this.coul + " " + this.lettre;
	}
}
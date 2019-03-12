package model;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class CaseVide extends Case{

	/**
	 * Constructeur de CaseVide
	 * @param c : les coordonnees de la case
	 */
	public CaseVide(Coordonnees c) {
		super(c);
	}

	/**
	 * Methode qui permet de dire que la case est accessible
	 */
	@Override
	public boolean etreAccessible() {
		return true;
	}

	/**
	 * Methode qui permet d'afficher la case
	 */
	@Override
	public String toString() {
		return "CaseVide " + super.toString();
	}
}

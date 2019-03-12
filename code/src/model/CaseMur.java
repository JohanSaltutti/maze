package model;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class CaseMur extends Case{

	/**
	 * Constructeur de CaseMur
	 * @param c : les coordonnees de la case
	 */
	public CaseMur(Coordonnees c) {
		super(c);
	}

	/**
	 * Methode qui permet de dire que la case n'est pas accessible
	 */
	@Override
	public boolean etreAccessible() {
		return false;
	}

	/**
	 * Methode qui permet d'afficher la case
	 */
	@Override
	public String toString() {
		return "CaseMur " + super.toString();
	}
}

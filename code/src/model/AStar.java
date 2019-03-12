package model;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class AStar extends AlgorithmeCheminementGlobal {

	/**
	 * Constructeur de AStar
	 * @param g : le gestionnaire
	 */
	public AStar(Gestionnaire g) {
		super(g);
	}

	/**
	 * Methode permettant d'initialiser le cout H de l'algorithme
	 */
	protected int coutH(Case a) {
		Case sortie = gestionnaire.getLaby().getSortie();

		int dx = Math.abs(a.getCoord().x - sortie.getCoord().x);
		int dy = Math.abs(a.getCoord().y - sortie.getCoord().y);

		int coutDeplacement = 1;

		return coutDeplacement * (dx + dy);
	}
}
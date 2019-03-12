package model;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class Dijkstra extends AlgorithmeCheminementGlobal {
	
	/**
	 * Constructeur de Dijkstra
	 * @param g : le gestionnaire
	 */
	public Dijkstra(Gestionnaire g) {
		super(g);
	}
	
	/**
	 * Methode permettant de set le cout H a 0
	 */
	protected int coutH(Case a) {
		return 0;
	}
}
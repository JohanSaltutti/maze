package model;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public abstract class Algorithme {

	/**
	 * Attribut representant les cases adjacentes
	 */
	protected final int[][] tabAdj = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
	
	/**
	 * Attribut permettant de gerer les differents elements du model
	 */
	protected Gestionnaire gestionnaire;
	
	/**
	 * Constructeur de Algorithme
	 * @param g : le gestonnaire
	 */
	protected Algorithme(Gestionnaire g){
		gestionnaire = g;
		initialiserAlgorithme();
	}
	
	/**
	 * Methode abstraite qui permet de creer un algorithme selon le modele
	 */
	public abstract void initialiserAlgorithme();
	
	/**
	 * Methode abstraite qui permet d'executer un algorithme selon le modele 
	 */
	public abstract void executerAlgorithme();

}

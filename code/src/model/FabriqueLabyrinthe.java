package model;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public abstract class FabriqueLabyrinthe {
	
	/**
	 * Attribut permettant de gerer les differents elements du model
	 */
	protected Gestionnaire gestionnaire;
	
	/**
	 * Constructeur de FabriqueLabyrinthe
	 * @param g : le gestionnaire
	 */
	public FabriqueLabyrinthe(Gestionnaire g) {
		gestionnaire = g;
	}
	
	/**
	 * Methode abstraite qui permet de creer un labyrinthe selon le modele choisi
	 * @return
	 */
	public abstract Labyrinthe fabriquerLabyrinthe();
}

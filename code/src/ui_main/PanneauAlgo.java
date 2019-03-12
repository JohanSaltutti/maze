package ui_main;

import model.Algorithme;
import model.Gestionnaire;
import view.Player;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public abstract class PanneauAlgo extends Panneau {

	/**
	 * Attribut representant le player des algorithmes
	 */
	protected Player player;
	
	/**
	 * Contructeur de PanneauAlgo
	 * @param g : le gestionnaire
	 * @param a : l'algorithme
	 */
	protected PanneauAlgo(Gestionnaire g, Algorithme a) {
		super(g, a);
	}

	/**
	 * Getter de l'attribut player
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}
}

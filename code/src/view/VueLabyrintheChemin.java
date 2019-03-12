package view;

import java.awt.Graphics;

import model.Gestionnaire;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class VueLabyrintheChemin extends VueLabyrinthe{

	/**
	 * Constructeur de VueLabyrintheChemin
	 * @param g : le gestionnaire
	 */
	public VueLabyrintheChemin(Gestionnaire g) {
		super(g);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}
}

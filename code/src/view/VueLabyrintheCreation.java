package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import model.Gestionnaire;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class VueLabyrintheCreation extends VueLabyrinthe{

	/**
	 * Constructeur de VueLabyrintheCreation
	 * @param g : le gestionnaire
	 */
	public VueLabyrintheCreation(Gestionnaire g) {
		super(g);
	}

	/**
	 * Methode permettant de dessiner les differents elements du labyrinthe
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		Graphics2D gg = (Graphics2D) g;
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		dess.dessinerEntree(g, TAILLE_CASE);
		dess.dessinerSortie(g, TAILLE_CASE);
		dess.dessinerRobot(g, TAILLE_CASE, false);
	}
}

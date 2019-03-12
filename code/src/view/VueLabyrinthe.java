package view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;

import model.Gestionnaire;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public abstract class VueLabyrinthe extends Vue{
	
	/**
	 * Attributs representant le nombre de lignes et de colonnes du labyrinthe 
	 */
	protected int LIGNES, COLONNES;
	
	/**
	 * Constructeur de VueLabyrinthe
	 * @param g : le gestionnaire
	 */
	public VueLabyrinthe(Gestionnaire g) {
		super(g);
		MARGIN_HORIZ = 0;
		MARGIN_VERT = 0;
		this.setMaximumSize(new Dimension(350, 350));
		this.setMinimumSize(new Dimension(350, 350));
	}

	@Override
	protected void resize() {
		Container parent = getParent();
		int parentHeight = parent.getHeight();
		int parentWidth = parent.getWidth();
		
		LIGNES = gestionnaire.getLaby().getLignes();
		COLONNES = gestionnaire.getLaby().getColonnes();
		
		double heightRatio = (parentHeight - 2.0 * MARGIN_VERT) / LIGNES;
		double widthRatio = (parentWidth - 2.0 * MARGIN_HORIZ) / COLONNES;
		
		TAILLE_CASE = (heightRatio > widthRatio)? (int)widthRatio : (int)heightRatio;
		
		WIDTH = TAILLE_CASE * COLONNES;
		HEIGHT = TAILLE_CASE * LIGNES;
		
		this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		
		revalidate();
	}

	/**
	 * Methode permettant de dessiner les differents elements du labyrinthe
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		resize();
		
		dess.dessinerLabyrinthe(g, TAILLE_CASE, LIGNES, COLONNES);
	}
}
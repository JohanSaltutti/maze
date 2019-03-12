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
public class VueAdjacents extends Vue {

	/**
	 * Attribut representant ce que voit le robot
	 */
	private int visionRobot;

	/**
	 * Constructeur de VueAdjacents
	 * @param g : le gestionnaire
	 */
	public VueAdjacents(Gestionnaire g) {
		super(g);
	}

	/**
	 * Methode permettant de redefinir la taille de l'interface graphique
	 */
	protected void resize() {
		Container parent = getParent();
		int parentHeight = parent.getHeight();
		int parentWidth = parent.getWidth();
		
		MARGIN_HORIZ = parentWidth / 3;
		MARGIN_VERT = MARGIN_HORIZ / 2;

		double heightRatio = (parentHeight - 2 * MARGIN_VERT) / 2.0;
		double widthRatio = (parentWidth - 2 * MARGIN_HORIZ) / 3.0;
		
		TAILLE_CASE = (heightRatio > widthRatio)? (int)widthRatio : (int)heightRatio;
		
		WIDTH = TAILLE_CASE * 3;
		HEIGHT = TAILLE_CASE * 2;
		
		this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		
		this.revalidate();
	}

	/**
	 * Methode permettant de dessiner les differents elements du labyrinthe
	 */
	public void paintComponent(Graphics g){

		super.paintComponent(g);

		resize();
		
		visionRobot = gestionnaire.calculerVisionRobot();
		
		dess.dessinerAdjacents(g, TAILLE_CASE, traduireVisionRobot());
	}

	//true = vide, false = mur
	private boolean[] traduireVisionRobot(){
		int vr = visionRobot;
		boolean [] res = new boolean [3];
		for(int i = 0 ; i < 3 ; i++){
			res[2-i] = vr % 2 == 0;
			vr >>= 1;
		}

		return res;
	}
}

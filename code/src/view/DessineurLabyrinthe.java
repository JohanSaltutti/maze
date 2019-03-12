package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import model.Case;
import model.Coordonnees;
import model.Gestionnaire;
import model.Position;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class DessineurLabyrinthe {

	/**
	 * Attribut representant le gestionnaire
	 */
	private Gestionnaire gestionnaire;

	/**
	 * Constantes representant les couleurs des cases du labyrinthe
	 */
	public static final Color COULEUR_ENTREE = Color.YELLOW, COULEUR_SORTIE = Color.GREEN, COULEUR_ROBOT = Color.RED, COULEUR_ROBOT_TRANSPARENT = new Color(255, 0, 0, 160);
	
	/**
	 * Constantes representant les couleurs des cases lors de l'execution des algorithmes
	 */
	public static final Color COULEUR_CASES_PARCOURUES = Color.orange, COULEUR_CHEMIN_FINAL = Color.PINK;
	
	/**
	 * Constantes representant les couleurs des cases courantes
	 */
	public static final Color COULEUR_ADJACENTS = Color.magenta, COULEUR_CASE_COURANTE = Color.cyan, COULEUR_VALUATIONS = Color.black;
	
	/**
	 * Constantes representant les couleurs des positions et du mur ou l'on s'appuie sur main droite
	 */
	public static final Color COULEUR_POSITION = Color.BLUE, COULEUR_MUR_MAIN_DROITE = Color.gray;
	
	/**
	 * Constantes representant les couleurs des cailloux de tremaux
	 */
	public static final Color COULEUR_CAILLOU_SIMPLE = Color.black, COULEUR_CAILLOU_DOUBLE = Color.RED;

	/**
	 * Constructeur de DessineurLabyrinthe
	 * @param g : le gestionnaire
	 */
	public DessineurLabyrinthe(Gestionnaire g) {
		gestionnaire = g;
	}

	/**
	 * Methode permettant de dessiner une case
	 * @param g : le graphics
	 * @param tailleCase : la taille d'une case
	 * @param c : la case
	 * @param coul : la couleur
	 */
	public void dessinerCase(Graphics g, int tailleCase, Case c, Color coul) {
		Graphics2D gg = antialiasing(g);
		gg.setColor(coul);
		Coordonnees coord = c.getCoord();
		gg.fillRect(coord.x * tailleCase + 1, coord.y * tailleCase + 1, tailleCase - 1, tailleCase - 1);
	}
	
	/**
	 * Mehode permettant de dessiner un labyrinthe
	 * @param g : le graphics
	 * @param tailleCase : la taille de la case
	 * @param lignes : le nombre de lignes
	 * @param colonnes : le nombre de colonnes
	 */
	public void dessinerLabyrinthe(Graphics g, int tailleCase, int lignes, int colonnes) {
		Graphics2D gg = antialiasing(g);

		for (int i = 0; i < colonnes; i++) {
			for (int j = 0; j < lignes; j++) {
				if (gestionnaire.getLaby().getCarte()[i][j].etreAccessible()) {
					gg.drawRect(i * tailleCase, j * tailleCase, tailleCase, tailleCase);
				} else {
					gg.fillRect(i * tailleCase, j * tailleCase, tailleCase, tailleCase);
				}
			}
		}
	}

	/**
	 * Methode permettant de dessiner un robot
	 * @param g : le graphics
	 * @param tailleCase : la taille de la case
	 * @param transparent : la transparence
	 */
	public void dessinerRobot(Graphics g, int tailleCase, boolean transparent) {
		Graphics2D gg = antialiasing(g);

		//Robot
		if(transparent) {
			gg.setColor(COULEUR_ROBOT_TRANSPARENT);
		}
		else {
			gg.setColor(COULEUR_ROBOT);	
		}
		Coordonnees caseRobot = gestionnaire.getRobot().getPosition().getCase().getCoord();//Robot
		gg.fillOval(caseRobot.x * tailleCase +1, caseRobot.y * tailleCase+1 ,tailleCase-1, tailleCase-1);

		//Direction robot
		int xFinal = caseRobot.x * tailleCase, yFinal = caseRobot.y * tailleCase;

		gg.setColor(Color.BLACK);
		switch(gestionnaire.getRobot().getPosition().getDirection()) {
		case Position.NORD:
			xFinal +=  tailleCase/2;
			break;
		case Position.EST:
			xFinal += tailleCase;
			yFinal += tailleCase/2;
			break;
		case Position.SUD:
			xFinal += tailleCase/2;
			yFinal += tailleCase;			
			break;
		case Position.OUEST:
			yFinal += tailleCase/2;			
			break;
		}

		gg.drawLine(caseRobot.x * tailleCase + tailleCase/2, caseRobot.y * tailleCase + tailleCase/2, xFinal, yFinal);
	}

	/**
	 * Methode permettant de dessiner la sortie 
	 * @param g : le graphics
	 * @param tailleCase : la taille de la case
	 */
	public void dessinerSortie(Graphics g, int tailleCase) {
		dessinerCase(g,tailleCase, gestionnaire.getLaby().getSortie(), COULEUR_SORTIE);
	}

	/**
	 * Methode permettant de dessiner l'entree 
	 * @param g : le graphics
	 * @param tailleCase : la taille de la case
	 */
	public void dessinerEntree(Graphics g, int tailleCase) {
		dessinerCase(g, tailleCase, gestionnaire.getLaby().getEntree(), COULEUR_ENTREE);
	}

	/**
	 * Methode permettant de dessiner les cases adjacentes 
	 * @param g : le graphics
	 * @param tailleCase : la taille de la case
	 * @param casesAdj : les cases adjacentes
	 */
	public void dessinerAdjacents(Graphics g, int tailleCase, boolean[] casesAdj) {

		Graphics2D gg = antialiasing(g);
		
		//a gauche
		if(casesAdj[0]){
			for(int i = 0 ; i < 5 ; i++){
				gg.drawRect(i, tailleCase + i, tailleCase - 2 * i, tailleCase - 2 * i);
			}
		}
		else{
			gg.fillRect(0, tailleCase, tailleCase, tailleCase);
		}

		//devant
		if(casesAdj[1]){
			for(int i = 0 ; i < 5 ; i++){
				gg.drawRect(tailleCase + i, i, tailleCase - 2 * i, tailleCase - 2 * i);
			}
		}
		else{
			gg.fillRect(tailleCase, 0, tailleCase, tailleCase);			
		}

		//a droite
		if(casesAdj[2]){
			for(int i = 0 ; i < 5 ; i++){
				gg.drawRect(2 * tailleCase + i, tailleCase + i, tailleCase - 2 * i, tailleCase - 2 * i);
			}
		}
		else{
			gg.fillRect(2 * tailleCase, tailleCase, tailleCase, tailleCase);
		}
	}

	/**
	 * Methode permettant de trouver la taille de police pour afficher une string dans un rectangle de reference
	 * @param s String que l'on veut afficher
	 * @param parentHeight Hauteur du rectangle par rapport auquel on veut afficher la string
	 * @param parentWidth Largeur du rectangle par rapport auquel on veut afficher la string
	 * @param percentageWidth Pourcentage de hauteur que doit prendre la string dans le rectangle de reference
	 * @param percentageHeight Pourcentage de largeur que doit prendre la string dans le rectangle de reference
	 * @return La police adaptee a la string et au rectangle de reference
	 */
	protected Font creerPolice(String s, double percentageWidth, double percentageHeight, int tailleCase) {
		
		//courrier new : pour un caractere, quelle que soit la taille de police, la largeur du caractere est 40% la hauteur du caractere
		// de plus, la hauteur d'un caractere est environ egale a la size de la font
		
		int size;

		//si la largeur de la string est plus grande que la hauteur
		if(s.length() > 2) {
			size = (int) ((tailleCase * percentageWidth) / (s.length() * 0.4));
		}
		else {
			size = (int) (tailleCase * percentageHeight);
		}
		
		return new Font("courrier new", Font.PLAIN, size);
	}
	
	private Graphics2D antialiasing(Graphics g) {
		Graphics2D gg = (Graphics2D) g;
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		return gg;
	}
}

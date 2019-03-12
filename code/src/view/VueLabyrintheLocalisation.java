package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashMap;

import model.AlgorithmeLocalisation;
import model.Gestionnaire;
import model.Position;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class VueLabyrintheLocalisation extends VueLabyrinthe {
	
	/**
	 * Constructeur de VueLabyrintheLocalisation
	 * @param g : le gestionnaire
	 */
	public VueLabyrintheLocalisation(Gestionnaire g) {
		super(g);
	}

	/**
	 * Methode permettant de dessiner les differents elements du labyrinthe
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		Graphics2D gg = (Graphics2D) g;
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		AlgorithmeLocalisation al = ((AlgorithmeLocalisation) gestionnaire.getAlgoCourant());
		
		
		dess.dessinerEntree(g, TAILLE_CASE);
		dess.dessinerSortie(g, TAILLE_CASE);
		
		//si on est en mode affichage du robot
		if(al.isAfficherRobot()){
			dess.dessinerRobot(g, TAILLE_CASE, false);
		}
		else{
			//si on est en mode affichage des positions possibles du robot
			HashMap<Integer, ArrayList<Position>> posPossibles = al.getPositionsPossibles();

			for (int i = 0; i < COLONNES; i++) {
				for (int j = 0; j < LIGNES; j++) {
					if (gestionnaire.getLaby().getCarte()[i][j].etreAccessible()) {
						dessinerPositionsCase(gg, posPossibles, i, j);
					}
				}
			}
		}
	}
	
	/**
	 * Methode permettant de dessiner les positions possibles dans le labrinthe
	 * @param gg : le graphics
	 * @param posPossibles : les positions possibles
	 * @param i
	 * @param j
	 */
	public void dessinerPositionsCase(Graphics gg, HashMap<Integer, ArrayList<Position>> posPossibles, int i, int j) {
		int x = i * TAILLE_CASE;
		int y = j * TAILLE_CASE;

		Point hautGauche = new Point(x, y);
		Point hautDroite = new Point(x + TAILLE_CASE, y);
		Point basDroite = new Point(x + TAILLE_CASE, y + TAILLE_CASE);
		Point basGauche = new Point(x, y + TAILLE_CASE);
		Point centre = new Point(x + TAILLE_CASE / 2, y + TAILLE_CASE / 2);

		gg.setColor(DessineurLabyrinthe.COULEUR_POSITION);

		int pos = (j + LIGNES * i);

		ArrayList<Position> setPos = posPossibles.get(pos);
		if(setPos != null){

			for(Position p : setPos){

				switch(p.getDirection()){
				case Position.NORD:{
					gg.fillPolygon(new int[]{hautGauche.x, hautDroite.x, centre.x}, new int[]{hautGauche.y, hautDroite.y, centre.y}, 3);
					break;
				}
				case Position.EST:{
					gg.fillPolygon(new int[]{hautDroite.x, centre.x, basDroite.x}, new int[]{hautDroite.y, centre.y, basDroite.y}, 3);
					break;
				}
				case Position.SUD:{
					gg.fillPolygon(new int[]{centre.x, basDroite.x, basGauche.x}, new int[]{centre.y, basDroite.y, basGauche.y}, 3);
					break;
				}
				case Position.OUEST:{
					gg.fillPolygon(new int[]{basGauche.x, hautGauche.x, centre.x}, new int[]{basGauche.y, hautGauche.y, centre.y}, 3);
					break;
				}
				}

			}
		}

		gg.setColor(Color.BLACK);
		gg.drawLine(hautDroite.x, hautDroite.y, basGauche.x, basGauche.y);
		gg.drawLine(hautGauche.x, hautGauche.y, basDroite.x, basDroite.y);
	}
}

package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.util.ArrayList;

import model.Case;
import model.Coordonnees;
import model.Gestionnaire;
import model.Pledge;
import model.Position;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class VuePledge extends VueLabyrintheChemin {
	
	/**
	 * Constructeur de VuePledge
	 * @param g : le gestionnaire
	 */
	public VuePledge(Gestionnaire g) {
		super(g);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D gg = (Graphics2D)g;
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Pledge algo = (Pledge) gestionnaire.getAlgoCourant();
		int etat = algo.getEtatCourant();

		if(etat != 0) {
			ArrayList<Position> pos = algo.getPositionsRobot();
			

			for(int i = 0 ; i < etat - 1 ; i++) {
				dess.dessinerCase(g, TAILLE_CASE, pos.get(i).getCase(), DessineurLabyrinthe.COULEUR_CASES_PARCOURUES);
			}
			
			//affichage du mur sur lequel on s'appuie
			if(algo.getMode() == Pledge.MAIN_DROITE) {
				Case caseADroite = gestionnaire.caseADroite();
				if(!caseADroite.etreAccessible()) {
					dess.dessinerCase(g, TAILLE_CASE, caseADroite, Color.GRAY);
				}
			}
			
			dess.dessinerEntree(gg, TAILLE_CASE);
			dess.dessinerSortie(gg, TAILLE_CASE);
			dess.dessinerRobot(gg, TAILLE_CASE, false);
			
			//affichage du compteur
			if(algo.getCompteurs().size() > 0) {
				gg.setColor(Color.BLUE);
				String v = "" + algo.getCompteurs().get(etat - 1);
				
				gg.setFont(dess.creerPolice(v, 0.4, 0.4, TAILLE_CASE));
				
				FontRenderContext frc = gg.getFontRenderContext();
				GlyphVector gv = gg.getFont().createGlyphVector(frc, v);
				Rectangle r = gv.getPixelBounds(null, 0, 0);
				
				Coordonnees caseRobot = gestionnaire.getRobot().getPosition().getCase().getCoord();
				
				int x = caseRobot.x * TAILLE_CASE +1, y = caseRobot.y * TAILLE_CASE+1;

				gg.drawString(v, (int) (x + (TAILLE_CASE - r.getWidth()) / 2.0), (int) (y + (TAILLE_CASE - r.getHeight()) / 2.0));
			}
		}
		else {
			dess.dessinerEntree(gg, TAILLE_CASE);
			dess.dessinerSortie(gg, TAILLE_CASE);
			dess.dessinerRobot(gg, TAILLE_CASE, false);
		}
	}
}
package ui_main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import model.Algorithme;
import model.Gestionnaire;
import view.DessineurLabyrinthe;
import view.VueLabyrinthe;
import view.VueTremaux;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class PanneauTremaux extends PanneauAlgoLocal {

	/**
	 * Constructeur de PanneauTremaux
	 * @param g : le gestionnaire
	 * @param a : l'algorithme
	 */
	public PanneauTremaux(Gestionnaire g, Algorithme a) {
		super(g, a);
	}

	@Override
	public VueLabyrinthe vue() {
		return new VueTremaux(gestionnaire);	
	}

	@Override
	public JPanel legend() {
		JPanel p = new JPanel(new GridLayout(2, 3));

		JPanel p1 = creerLegende(DessineurLabyrinthe.COULEUR_ENTREE, "Entree");
		JPanel p2 = creerLegende(DessineurLabyrinthe.COULEUR_SORTIE, "Sortie");
		JPanel p3 = creerLegende(DessineurLabyrinthe.COULEUR_CASES_PARCOURUES, "Case Parcourue");
		
		JPanel p4 = creerLegende(DessineurLabyrinthe.COULEUR_CHEMIN_FINAL, "Chemin Final");
		JPanel p5 = creerLegendeCailloux(DessineurLabyrinthe.COULEUR_CAILLOU_SIMPLE, "Caillou Simple");
		JPanel p6 = creerLegendeCailloux(DessineurLabyrinthe.COULEUR_CAILLOU_DOUBLE, "Caillou Double");
		
		p.add(p1);
		p.add(p2);
		p.add(p3); 
		p.add(p4);
		p.add(p5);
		p.add(p6);

		return p;
	}

	/**
	 * Methode permettant de creer la legende des cailloux
	 * @param c : la couleur
	 * @param desc : la description
	 * @return
	 */
	private JPanel creerLegendeCailloux(Color c, String desc) {
		return new JPanel(){
			public void paintComponent(Graphics g) {
				Graphics2D gg = (Graphics2D)g;
				gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				g.drawString(desc, 50, 25);
				g.setColor(c);
				g.fillOval(20, 10, 20, 20);
			}
		};
	}
}

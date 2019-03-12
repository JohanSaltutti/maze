package ui_main;

import java.awt.GridLayout;

import javax.swing.JPanel;

import model.Algorithme;
import model.Gestionnaire;
import view.DessineurLabyrinthe;
import view.VueAlgoCheminementGlobal;
import view.VueLabyrinthe;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class PanneauDijkstra extends PanneauAlgoGlobal {

	/**
	 * Constructeur de PanneauDijkstra
	 * @param g : le gestionnaire
	 * @param a : l'algorithme
	 */
	public PanneauDijkstra(Gestionnaire g, Algorithme a) {
		super(g, a);
	}

	@Override
	public VueLabyrinthe vue() {
		return new VueAlgoCheminementGlobal(gestionnaire);	
	}

	@Override
	public JPanel legende() {
		 JPanel p = new JPanel(new GridLayout(2, 3));
		 
		 JPanel p1 = creerLegende(DessineurLabyrinthe.COULEUR_ENTREE, "Entree");
		 JPanel p2 = creerLegende(DessineurLabyrinthe.COULEUR_SORTIE, "Sortie");
		 JPanel p3 = creerLegende(DessineurLabyrinthe.COULEUR_CASE_COURANTE, "Case Courante");
		 JPanel p4 = creerLegende(DessineurLabyrinthe.COULEUR_ADJACENTS, "Liste Ouverte");
		 JPanel p5 = creerLegende(DessineurLabyrinthe.COULEUR_CASES_PARCOURUES, "Liste Fermee");
		 JPanel p6 = creerLegende(DessineurLabyrinthe.COULEUR_CHEMIN_FINAL, "Chemin Minimal");
		 
		 p.add(p1);
		 p.add(p2);
		 p.add(p3); 
		 p.add(p4);
		 p.add(p5);
		 p.add(p6);
		 
		 return p;
	}
}

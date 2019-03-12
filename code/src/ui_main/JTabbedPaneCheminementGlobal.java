package ui_main;

import java.util.ArrayList;

import javax.swing.JTabbedPane;

import controller.ControleurChangementPanneau;
import model.AStar;
import model.Dijkstra;
import model.Gestionnaire;


/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class JTabbedPaneCheminementGlobal extends JTabbedPane {

	/**
	 * Constructeur de JTabbedPaneCheminementGlobal
	 * @param g : le gestionnaire
	 */
	public JTabbedPaneCheminementGlobal(Gestionnaire g) {

		Panneau aStar = new PanneauAStar(g, new AStar(g));
		Panneau dijkstra = new PanneauDijkstra(g, new Dijkstra(g));
		
		addTab("Dijkstra", null, dijkstra, "Dijktsra");
		addTab("A*",null, aStar, "A*");
		
		ArrayList<ArrayList<Panneau>> listePanneaux = new ArrayList<ArrayList<Panneau>>();
		ArrayList<Panneau> al1 = new ArrayList<Panneau>();
		al1.add(dijkstra);
		
		ArrayList<Panneau> al2 = new ArrayList<Panneau>();
		al2.add(aStar);
		
		listePanneaux.add(al1);
		listePanneaux.add(al2);
		
		ControleurChangementPanneau ccp = new ControleurChangementPanneau(g, listePanneaux);
		
		addChangeListener(ccp);
	}

}
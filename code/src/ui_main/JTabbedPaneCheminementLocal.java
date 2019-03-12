package ui_main;

import java.util.ArrayList;

import javax.swing.JTabbedPane;

import controller.ControleurChangementPanneau;
import model.Gestionnaire;
import model.MainDroite;
import model.Pledge;
import model.Tremaux;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class JTabbedPaneCheminementLocal extends JTabbedPane {

	/**
	 * Constructeur de JTabbedPaneCheminementLocal
	 * @param g : le gestionnaire
	 */
	public JTabbedPaneCheminementLocal(Gestionnaire g) {
		Panneau mainDroite = new PanneauMainDroite(g, new MainDroite(g));
		Panneau pledge = new PanneauPledge(g, new Pledge(g));
		Panneau tremaux = new PanneauTremaux(g, new Tremaux(g));
		
		addTab("Main Droite", null, mainDroite, "Main Droite");
		addTab("Pledge", null, pledge, "Pledge");
		addTab("Tremaux", null, tremaux, "Tremaux");
		
		ArrayList<ArrayList<Panneau>> listePanneaux = new ArrayList<ArrayList<Panneau>>();
		ArrayList<Panneau> al1 = new ArrayList<Panneau>();
		al1.add(mainDroite);
		
		ArrayList<Panneau> al2 = new ArrayList<Panneau>();
		al2.add(pledge);
		
		ArrayList<Panneau> al3 = new ArrayList<Panneau>();
		al3.add(tremaux);
		
		listePanneaux.add(al1);
		listePanneaux.add(al2);
		listePanneaux.add(al3);
		
		ControleurChangementPanneau ccp = new ControleurChangementPanneau(g, listePanneaux);
		
		addChangeListener(ccp);
	}

}
package ui_main;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.ControleurChangementPanneau;
import model.Gestionnaire;
import model.Labyrinthe;
import model.Robot;
import view.DessineurLabyrinthe;
import view.Vue;
import view.Vue3D;
import view.VueAdjacents;
import view.VueLabyrinthe;
import view.VueLabyrintheCreation;
import view.VueLabyrintheLocalisation;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class Fenetre extends JFrame{

	/**
	 * Contructeur permettant de construire l'interface graphique
	 */
	public Fenetre(){

		super("Maze");
		setMinimumSize(new Dimension(1200, 700));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		//MODELE
		Gestionnaire gestionnaire = new Gestionnaire();
		Robot robot = new Robot();
		Labyrinthe laby = new Labyrinthe();
		gestionnaire.setLabyrinthe(laby);
		gestionnaire.setRobot(robot);

		//VUES
		DessineurLabyrinthe dl = new DessineurLabyrinthe(gestionnaire);
		Vue.dess = dl;
		
		VueLabyrinthe vueLabyCreat = new VueLabyrintheCreation(gestionnaire);	
		VueLabyrintheLocalisation vueLabyLoc = new VueLabyrintheLocalisation(gestionnaire);
		VueAdjacents vueAdj = new VueAdjacents(gestionnaire);
		Vue3D v3 = new Vue3D(gestionnaire);

		PanneauCreation panneauCreation = new PanneauCreation(gestionnaire, vueLabyCreat);
		PanneauLocalisation panneauLocalisation = new PanneauLocalisation(gestionnaire, vueLabyLoc, vueAdj, v3);
		
		JTabbedPane tabbedPaneAlgoGlobal = new JTabbedPaneCheminementGlobal(gestionnaire);
		JTabbedPane tabbedPaneAlgoLocal = new JTabbedPaneCheminementLocal(gestionnaire);

		//liste des panneaux permettant l'aiguillage des observeurs sur les bons panneaux et algorithmes quand on change de panneau
		ArrayList<ArrayList<Panneau>> listePanneauxGlobale = new ArrayList<ArrayList<Panneau>>();
		ArrayList<Panneau> al1 = new ArrayList<Panneau>();
		al1.add(panneauCreation);

		ArrayList<Panneau> al2 = new ArrayList<Panneau>();
		al2.add(panneauLocalisation);

		ArrayList<Panneau> al3 = new ArrayList<Panneau>();

		for(Component c : tabbedPaneAlgoGlobal.getComponents()) {
			al3.add((Panneau)c);
		}

		ArrayList<Panneau> al4 = new ArrayList<Panneau>();

		for(Component c : tabbedPaneAlgoLocal.getComponents()) {
			al4.add((Panneau)c);
		}

		listePanneauxGlobale.add(al1);
		listePanneauxGlobale.add(al2);
		listePanneauxGlobale.add(al3);
		listePanneauxGlobale.add(al4);

		JTabbedPane panneauOnglets = new JTabbedPane();
		panneauOnglets.addTab("Creation", new ImageIcon(getClass().getClassLoader().getResource("icons/pencil.png")), panneauCreation, "Panneau de creation");
		panneauOnglets.addTab("Localisation", new ImageIcon(getClass().getClassLoader().getResource("icons/location.png")), panneauLocalisation, "Panneau de localisation");
		panneauOnglets.addTab("Algorithmes globaux", new ImageIcon(getClass().getClassLoader().getResource("icons/global.png")), tabbedPaneAlgoGlobal, "Panneau d'algorithme global");
		panneauOnglets.addTab("Algorithmes locaux", new ImageIcon(getClass().getClassLoader().getResource("icons/local.png")), tabbedPaneAlgoLocal, "Panneau d'algorithme local");

		//CONTROLEURS
		ControleurChangementPanneau ccp = new ControleurChangementPanneau(gestionnaire, listePanneauxGlobale);
		
		panneauOnglets.addChangeListener(ccp);
		gestionnaire.addObserver(vueLabyCreat);	
		
		setContentPane(panneauOnglets);
		setJMenuBar(new Menu(gestionnaire));
		setVisible(true);
	}
	
	public static void main(String args[]) {
		new Fenetre();
	}
}

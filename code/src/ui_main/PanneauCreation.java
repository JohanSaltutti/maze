package ui_main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import controller.ControleurClic;
import controller.ControleurDeplacementLaby;
import controller.ControleurDeplacementRobot;
import controller.ControleurGeneration;
import controller.ControleurMode;
import controller.ControleurTaille;
import model.Gestionnaire;
import model.Labyrinthe;
import view.VueLabyrinthe;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class PanneauCreation extends Panneau{
	
	/**
	 * Attribut permettant de gerer la taille du labyrinthe (HxL)
	 */
	private ControleurTaille controlTaille;
	
	/**
	 * Attribut permettant d'avoir le panel du haut 
	 */
	private JPanel nord;
	
	/**
	 * Attribut permettant d'avoir le panel du bas
	 */
	private JPanel sud;

	/**
	 * Constructeur qui permet d'associer le gestionnaire et la vue du labyrinthe
	 * @param g : le gestionnaire
	 * @param vue : la vue du labyrinthe
	 */
	public PanneauCreation(Gestionnaire g, VueLabyrinthe vue) {
		super(g, null);
		vues.add(vue);
		nord = nord(vue);
		sud = sud();
		initialiserPanneau();
	}

	/**
	 * Methode qui permet d'initialiser le panneau
	 */
	protected void initialiserPanneau() {
		this.setLayout(new BorderLayout());
		this.add(nord,BorderLayout.CENTER);
		this.add(sud,BorderLayout.SOUTH);
	}

	/**
	 * Methode qui permet de creer le panel du haut
	 * @return : JPanel
	 */
	private JPanel nord(VueLabyrinthe vueLaby) {
		
		JPanel nord = new JPanel();
		nord.setLayout(new BoxLayout(nord, BoxLayout.PAGE_AXIS));
		
		ControleurClic cc = new ControleurClic(gestionnaire, vueLaby);
		vueLaby.addMouseMotionListener(cc);
		vueLaby.addMouseListener(cc);
		vueLaby.setAlignmentX(CENTER_ALIGNMENT);
		
		nord.add(vueLaby);
		
		return nord;
	}

	/**
	 * Methode qui permet de creer le panel du bas
	 * @return : JPanel
	 */
	private JPanel sud() {
		//Label et Spinner
		JPanel size = new JPanel();
		size.setLayout(new GridLayout(2, 2));
		{
			JLabel lignes = new JLabel("Lignes : ");
			JSpinner jslignes = new JSpinner(new SpinnerNumberModel(10, Labyrinthe.MIN_LIGNES, Labyrinthe.MAX_LIGNES, 1));
			
			JLabel colonnes = new JLabel("Colonnes : ");
			JSpinner jscolonnes = new JSpinner(new SpinnerNumberModel(10, Labyrinthe.MIN_COLONNES, Labyrinthe.MAX_COLONNES, 1));
			
			controlTaille = new ControleurTaille(gestionnaire, jslignes, jscolonnes);
			
			size.add(lignes);
			size.add(jslignes);
			size.add(colonnes);
			size.add(jscolonnes);
		}
		
		//Modification taille laby
		JPanel validSize = new JPanel();
		{
			JButton creer = new JButton("Creer");

			creer.addActionListener(controlTaille);
			validSize.add(size);
			validSize.add(creer);
		}
		
		JPanel modificationTailleLaby = new JPanel();
		{
			TitledBorder creerCaseTitle = new TitledBorder(new LineBorder(Color.GRAY), "Modifier taille");
			creerCaseTitle.setTitleJustification(TitledBorder.LEFT);
			creerCaseTitle.setTitlePosition(TitledBorder.TOP);
			modificationTailleLaby.setBorder(creerCaseTitle);
			modificationTailleLaby.add(validSize);
		}
		
		JPanel modificationOrientation = new JPanel();
		{
			TitledBorder orRobotTitle = new TitledBorder(new LineBorder(Color.GRAY), "Changer orientation");
			orRobotTitle.setTitleJustification(TitledBorder.LEFT);
			orRobotTitle.setTitlePosition(TitledBorder.TOP);
			modificationOrientation.setBorder(orRobotTitle);
			
			JPanel modificationOrientationContent = new JPanel();
			{
				JButton modifOrientation = new JButton("Orientation robot");
				modifOrientation.setName("Orientation");
				modifOrientation.addActionListener(new ControleurDeplacementRobot(gestionnaire));
				modificationOrientationContent.add(modifOrientation);
				modificationOrientationContent.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
			}

			modificationOrientation.add(modificationOrientationContent);
	
		}
		
		JPanel modifLabyOrien = new JPanel();
		{
			modifLabyOrien.setLayout(new GridLayout(1, 2));
			modifLabyOrien.add(modificationTailleLaby);
			modifLabyOrien.add(modificationOrientation);
		}
		
		
		//Case a creer (vide, mur, ...)
		JPanel caseLaby = new JPanel();
		{
			JButton vide = new JButton("Vide");
			JButton mur = new JButton("Mur");
			JButton entree = new JButton("Entree");
			entree.setBackground(Color.YELLOW);
			JButton sortie = new JButton("Sortie");
			sortie.setBackground(Color.GREEN);
			
			vide.setFocusPainted(false);
			mur.setFocusPainted(false);
			entree.setFocusPainted(false);
			sortie.setFocusPainted(false);
			
			ControleurMode contMode = new ControleurMode(gestionnaire);
			vide.addActionListener(contMode);
			mur.addActionListener(contMode);
			entree.addActionListener(contMode);
			sortie.addActionListener(contMode);
			
			caseLaby.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
			
			caseLaby.add(vide);
			caseLaby.add(mur);
			caseLaby.add(entree);
			caseLaby.add(sortie);
		}
		
		//Case + Titre
		JPanel creationCase = new JPanel();
		{
			TitledBorder creerCaseTitle = new TitledBorder(new LineBorder(Color.GRAY),"Creer case");
			creerCaseTitle.setTitleJustification(TitledBorder.LEFT);
			creerCaseTitle.setTitlePosition(TitledBorder.TOP);
			creationCase.setBorder(creerCaseTitle);
			creationCase.add(caseLaby);
		}
		
		//Partie Sud gauche
		JPanel gauche = new JPanel();
		{
			gauche.setLayout(new GridLayout(2,1));
			gauche.add(modifLabyOrien);
			gauche.add(creationCase);
		}
		
		//Droite bouton
		JPanel boutonsOutils = new JPanel();
		{
			JButton remplir = new JButton("Remplir");
			JButton ilot = new JButton("Ilot");
			JButton vider = new JButton("Vider");
			JButton parfait = new JButton("Parfait");
			JButton imparfait = new JButton("Imparfait");
			
			ControleurGeneration controlOutils = new ControleurGeneration(gestionnaire);
			remplir.addActionListener(controlOutils);
			ilot.addActionListener(controlOutils);
			vider.addActionListener(controlOutils);
			parfait.addActionListener(controlOutils);
			imparfait.addActionListener(controlOutils);
			
			boutonsOutils.setLayout(new GridLayout(5,1));
			boutonsOutils.add(remplir);
			boutonsOutils.add(vider);
			boutonsOutils.add(ilot);
			boutonsOutils.add(parfait);
			boutonsOutils.add(imparfait);
		}
		
		JPanel boutonsOutilsGeneration = new JPanel();
		{
			TitledBorder outilsGenerationTitle = new TitledBorder(new LineBorder(Color.GRAY),"Generation");
			outilsGenerationTitle.setTitleJustification(TitledBorder.LEFT);
			outilsGenerationTitle.setTitlePosition(TitledBorder.TOP);
			boutonsOutilsGeneration.setBorder(outilsGenerationTitle);
			boutonsOutilsGeneration.add(boutonsOutils);
		}
		
		JPanel boutonsDeplacement = new JPanel();
		{
			Dimension d = new Dimension(50, 50);
			boutonsDeplacement.setLayout(new GridLayout(3,3));
			JButton hautBtn = new JButton(creerFleche(2));
			hautBtn.setName("Haut");
			hautBtn.setPreferredSize(d);
			hautBtn.setMinimumSize(d);
			JButton gaucheBtn = new JButton(creerFleche(1));
			gaucheBtn.setName("Gauche");
			gaucheBtn.setPreferredSize(d);
			gaucheBtn.setMinimumSize(d);
			JButton basBtn = new JButton(creerFleche(4));
			basBtn.setName("Bas");
			basBtn.setPreferredSize(d);
			basBtn.setMinimumSize(d);
			JButton droiteBtn = new JButton(creerFleche(3));
			droiteBtn.setName("Droite");
			droiteBtn.setPreferredSize(d);
			droiteBtn.setMinimumSize(d);
			
			
			ControleurDeplacementLaby contrdepla= new ControleurDeplacementLaby(gestionnaire);
			hautBtn.addActionListener(contrdepla);
			basBtn.addActionListener(contrdepla);
			gaucheBtn.addActionListener(contrdepla);
			droiteBtn.addActionListener(contrdepla);
			JButton vide1 = new JButton();
			vide1.setVisible(false);
			JButton vide2 = new JButton();
			vide2.setVisible(false);
			JButton vide3 = new JButton();
			vide3.setVisible(false);
			JButton vide4 = new JButton();
			vide4.setVisible(false);
			JButton vide5 = new JButton();
			vide5.setVisible(false);
			
			//boutonsDeplacement.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
		
			boutonsDeplacement.add(vide1);
			boutonsDeplacement.add(hautBtn);
			boutonsDeplacement.add(vide2);
			boutonsDeplacement.add(gaucheBtn);
			boutonsDeplacement.add(vide3);
			boutonsDeplacement.add(droiteBtn);
			boutonsDeplacement.add(vide4);
			boutonsDeplacement.add(basBtn);
			boutonsDeplacement.add(vide5);
		}
		
		JPanel boutonsDeplacementBorder = new JPanel();
		{
			TitledBorder outilsDeplacementTitle = new TitledBorder(new LineBorder(Color.GRAY),"Deplacement labyrinthe");
			outilsDeplacementTitle.setTitleJustification(TitledBorder.LEFT);
			outilsDeplacementTitle.setTitlePosition(TitledBorder.TOP);
			boutonsDeplacementBorder.setBorder(outilsDeplacementTitle);
			boutonsDeplacementBorder.add(boutonsDeplacement);
		}
		
		//Boutons outils + Titre
		JPanel droite = new JPanel();
		{
			TitledBorder outilsTitle = new TitledBorder(new LineBorder(Color.GRAY),"Outils");
			outilsTitle.setTitleJustification(TitledBorder.LEFT);
			outilsTitle.setTitlePosition(TitledBorder.TOP);
			droite.setBorder(outilsTitle);
			droite.setLayout(new GridLayout(1,2));
			droite.add(boutonsOutilsGeneration);
			droite.add(boutonsDeplacementBorder);
		}
		
		JPanel sud = new JPanel();
		{
			sud.setLayout(new GridLayout(1,2));
			sud.add(gauche);
			sud.add(droite);
		}
		
		return sud;
	}
}
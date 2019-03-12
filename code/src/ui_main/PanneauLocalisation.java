package ui_main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import controller.ControleurAffichageRobot;
import controller.ControleurDeplacementRobot;
import controller.ControleurSwitchVues;
import controller.ControleurVue3D;
import model.AlgorithmeLocalisation;
import model.Gestionnaire;
import view.Vue3D;
import view.VueAdjacents;
import view.VueLabyrintheLocalisation;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class PanneauLocalisation extends Panneau {

	/**
	 * Attribut permettant le deplacement du robot dans le labyrinthe
	 */
	private ControleurDeplacementRobot contDepl;
	
	private ControleurVue3D contVue3D;
	
	/**
	 * Attribut permettant d'avoir les panels de droite et de gauche
	 */
	private JPanel east, west;

	/**
	 * Contructeur de PanneauLocalisation
	 * @param g : le gestionnaire
	 * @param vll : la vue de localisation du labyrinthe
	 * @param va : la vue adjacente
	 */
	public PanneauLocalisation(Gestionnaire g, VueLabyrintheLocalisation vll, VueAdjacents va, Vue3D v3) {
		super(g, new AlgorithmeLocalisation(g));
		g.setAlgoCourant(algoCourant);
		vues.add(va);
		
		contDepl = new ControleurDeplacementRobot(g);
		contVue3D = new ControleurVue3D(v3);
		
		vues.add(vll);
		vll.addKeyListener(contDepl);
		vll.requestFocusInWindow();
		
		vues.add(v3);
		
		east = east(va, v3);
		west = west(vll);
		initialiserPanneau();
	}

	/**
	 * Methode qui permet d'initialiser le panneau
	 */
	@Override
	protected void initialiserPanneau() {

		this.setLayout(new GridLayout(1, 2));
		this.add(west, BorderLayout.WEST);
		this.add(east, BorderLayout.CENTER);
	}

	/**
	 * Methode qui permet de creer le panel de gauche
	 * @return : JPanel
	 */
	private JPanel west(VueLabyrintheLocalisation vueLabyLoc){
		JPanel west = new JPanel(new BorderLayout());
		{
			JPanel insideWest = new JPanel(new BorderLayout());
			insideWest.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); 
			{
				JPanel westCenter = new JPanel();
				{
					westCenter.setLayout(new BoxLayout(westCenter, BoxLayout.PAGE_AXIS));
					westCenter.add(Box.createRigidArea(new Dimension(1, 5)));

					vueLabyLoc.setMinimumSize(new Dimension(300, 300));
					vueLabyLoc.setMaximumSize(new Dimension(300, 300));
					westCenter.add(vueLabyLoc);

				}
				
				JPanel westSouth = new JPanel(new GridLayout(1, 2));
				{
					TitledBorder contGlob = new TitledBorder(new LineBorder(Color.GRAY),"Controles Vue Globale");
					contGlob.setTitleJustification(TitledBorder.LEFT);
					contGlob.setTitlePosition(TitledBorder.TOP);
					westSouth.setBorder(contGlob);

					JPanel switchVues = new JPanel();
					{
						TitledBorder afficherLoc = new TitledBorder(new LineBorder(Color.GRAY),"Afficher");
						afficherLoc.setTitleJustification(TitledBorder.LEFT);
						afficherLoc.setTitlePosition(TitledBorder.TOP);
						switchVues.setBorder(afficherLoc);
						switchVues.setLayout(new BoxLayout(switchVues, BoxLayout.PAGE_AXIS));

						JRadioButton robot = new JRadioButton("Robot");
						robot.setAlignmentX(CENTER_ALIGNMENT);
						JRadioButton posPoss = new JRadioButton("Positions possibles");
						posPoss.setAlignmentX(CENTER_ALIGNMENT);
						posPoss.setSelected(true);
						
						ControleurAffichageRobot car = new ControleurAffichageRobot(gestionnaire);
						robot.addActionListener(car);
						posPoss.addActionListener(car);
						
						ButtonGroup group = new ButtonGroup();
						group.add(robot);
						group.add(posPoss);
						
						switchVues.add(Box.createRigidArea(new Dimension(1, 45)));
						switchVues.add(robot);
						switchVues.add(posPoss);
						switchVues.add(Box.createRigidArea(new Dimension(1, 45)));
					}

					JPanel controlButtons = new JPanel();
					{
						controlButtons.setLayout(new BoxLayout(controlButtons, BoxLayout.PAGE_AXIS));

						JButton focus = new JButton("Focus");
						focus.setAlignmentX(CENTER_ALIGNMENT);
						focus.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent arg0) {
								vueLabyLoc.requestFocusInWindow();
							}
						});
						
						JButton replRobot = new JButton("Placer le robot sur l'entree");
						replRobot.setAlignmentX(CENTER_ALIGNMENT);
						replRobot.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent arg0) {
								gestionnaire.getAlgoCourant().initialiserAlgorithme();
								gestionnaire.placerRobot();
								gestionnaire.miseAJour();
							}
						});
						
						JButton aleaRobot =  new JButton("Placer aleatoirement le robot");
						aleaRobot.setAlignmentX(CENTER_ALIGNMENT);
						aleaRobot.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								gestionnaire.placerAleatoireRobot();
								gestionnaire.getAlgoCourant().initialiserAlgorithme();
							}
						});
						
						controlButtons.add(Box.createRigidArea(new Dimension(1, 30)));
						controlButtons.add(focus);
						controlButtons.add(Box.createRigidArea(new Dimension(1, 10)));
						controlButtons.add(replRobot);
						controlButtons.add(Box.createRigidArea(new Dimension(1, 10)));
						controlButtons.add(aleaRobot);
					}
					
					westSouth.add(switchVues);
					westSouth.add(controlButtons);
				}
					

				insideWest.add(westSouth, BorderLayout.SOUTH);
				insideWest.add(westCenter, BorderLayout.CENTER);
			}

			JSeparator sepVer = new JSeparator(JSeparator.VERTICAL);

			west.add(insideWest, BorderLayout.CENTER);
			west.add(sepVer, BorderLayout.LINE_END);
		}

		return west;
	}

	/**
	 * Methode qui permet de creer le panel de droite
	 * @param v3 
	 * @return : JPanel
	 */
	private JPanel east(VueAdjacents vueAdj, Vue3D v3){
		JPanel east = new JPanel(new BorderLayout());
		east.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); 

		JPanel eastCenter = new JPanel();
		{

			eastCenter.setLayout(new BoxLayout(eastCenter, BoxLayout.PAGE_AXIS));
			eastCenter.add(Box.createRigidArea(new Dimension(1, 5)));

			eastCenter.add(v3);
		}

		JPanel eastSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 40));
		{
			TitledBorder contLoc = new TitledBorder(new LineBorder(Color.GRAY),"Controles Vue Locale");
			contLoc.setTitleJustification(TitledBorder.LEFT);
			contLoc.setTitlePosition(TitledBorder.TOP);
			eastSouth.setBorder(contLoc);

			JPanel switchVues = new JPanel();
			{
				switchVues.setLayout(new BoxLayout(switchVues, BoxLayout.PAGE_AXIS));

				JRadioButton radioAdj = new JRadioButton("Vue cases adjacentes");
				JRadioButton radio3D = new JRadioButton("Vue 3D");
				radio3D.setSelected(true);

				ButtonGroup group = new ButtonGroup();
				group.add(radioAdj);
				group.add(radio3D);
				
				switchVues.add(radioAdj);
				switchVues.add(Box.createRigidArea(new Dimension(1, 10)));
				switchVues.add(radio3D);
				
				ControleurSwitchVues csv = new ControleurSwitchVues(eastCenter, vueAdj, v3);
				radioAdj.addActionListener(csv);
				radio3D.addActionListener(csv);
			}

			JPanel deplacement = new JPanel(new GridLayout(2, 3));
			{
				JButton tournerDroite = new JButton(creerFleche(3));
				tournerDroite.setFocusPainted(false);
				tournerDroite.setName("Droite");
				tournerDroite.addActionListener(contDepl);

				JButton avancer = new JButton(creerFleche(2));
				avancer.setFocusPainted(false);
				avancer.setName("Avancer");
				avancer.addActionListener(contDepl);

				JButton tournerGauche = new JButton(creerFleche(1));
				tournerGauche.setFocusPainted(false);
				tournerGauche.setName("Gauche");
				tournerGauche.addActionListener(contDepl);

				JButton vide1 = new JButton();
				vide1.setVisible(false);

				JButton vide2 = new JButton();
				vide2.setVisible(false);

				JButton vide3 = new JButton();
				vide3.setVisible(false);

				deplacement.add(vide1);
				deplacement.add(avancer);
				deplacement.add(vide2);
				deplacement.add(tournerGauche);
				deplacement.add(vide3);
				deplacement.add(tournerDroite);
			}
			
			JPanel vue3D = new JPanel(new GridLayout(1, 4));
			{
				
				JSlider camDistance = new JSlider(JSlider.VERTICAL, 5, (int)(Vue3D.RENDERING_DISTANCE - 0.5) * 10, 5);
				camDistance.setName("cam");		
				camDistance.addChangeListener(contVue3D);
				camDistance.setPreferredSize(new Dimension(20, 70));
				
				JSlider screenWidth = new JSlider(JSlider.VERTICAL, 1, 10, 1);
				screenWidth.setName("width");
				screenWidth.addChangeListener(contVue3D);
				screenWidth.setPreferredSize(new Dimension(20, 70));
				
				vue3D.add(camDistance);
				vue3D.add(new JLabel("Camera"));
				vue3D.add(screenWidth);
				vue3D.add(new JLabel("Ecran"));
				
			}

			eastSouth.add(switchVues);
			eastSouth.add(deplacement);
			eastSouth.add(vue3D);
		}

		east.add(eastCenter, BorderLayout.CENTER);
		east.add(eastSouth, BorderLayout.SOUTH);

		return east;
	}
}
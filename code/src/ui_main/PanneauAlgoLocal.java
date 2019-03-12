package ui_main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import controller.ControleurSwitchVues;
import controller.ControleurVue3D;
import model.Algorithme;
import model.Gestionnaire;
import view.Player;
import view.Vue3D;
import view.VueAdjacents;
import view.VueLabyrinthe;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public abstract class PanneauAlgoLocal extends PanneauAlgo {

	/**
	 * Attributs representant les panels du nord, sud et est
	 */
	private JPanel north, south, east;
	
	/**
	 * Attribut representant la vue du labyrinthe
	 */
	private VueLabyrinthe vueLaby;
	
	/**
	 * Attribut representant la vue des cases adjacentes
	 */
	private VueAdjacents vueAdj;
	
	/**
	 * Attribut representant la vue 3D
	 */
	private Vue3D v3D;
	
	private ControleurVue3D contVue3D;
	
	/**
	 * Constructeur de PanneauAlgoLocal
	 * @param g : le gestionnaire
	 * @param a : l'algorithme
	 */
	public PanneauAlgoLocal(Gestionnaire g, Algorithme a) {
		super(g, a);
		vueLaby = vue();
		north = north();
		south = south(legend());
		initialiserPanneau();
		vues.add(vueLaby);
		vues.add(vueAdj);
		vues.add(v3D);
	}

	public abstract VueLabyrinthe vue();
	public abstract JPanel legend();

	@Override
	protected void initialiserPanneau() {
		this.setLayout(new BorderLayout());
		this.add(north, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);
	}

	/**
	 * Methode permettant de creer le panel du nord
	 * @return
	 */
	private JPanel north() {		

		JPanel west = new JPanel();
		west.setLayout(new BoxLayout(west, BoxLayout.PAGE_AXIS));
		vueLaby.setAlignmentX(CENTER_ALIGNMENT);
		west.add(vueLaby);
		
		v3D = new Vue3D(gestionnaire);
		
		east = new JPanel(new BorderLayout());
		{
			east.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
			east.setLayout(new BoxLayout(east, BoxLayout.PAGE_AXIS));
			east.add(Box.createRigidArea(new Dimension(1, 5)));
	
			east.add(v3D);
			
		}
		
		contVue3D = new ControleurVue3D(v3D);
		
		JPanel nord = new JPanel(new GridLayout(1,2));
		nord.add(west);
		nord.add(east);

		return nord;
	}

	/**
	 * Methode permettant de creer le panel du sud
	 * @param legend : la legende
	 * @return
	 */
	private JPanel south(JPanel legend) {
		
		legend.setSize(new Dimension(20,360));
		TitledBorder creerCaseTitle = new TitledBorder(new LineBorder(Color.GRAY), "Legende");
		creerCaseTitle.setTitleJustification(TitledBorder.LEFT);
		creerCaseTitle.setTitlePosition(TitledBorder.TOP);
		legend.setBorder(creerCaseTitle);
		legend.setPreferredSize(new Dimension(30, 100));

		JPanel changementVues = new JPanel(new GridLayout(1,2));
		{
			TitledBorder contLoc = new TitledBorder(new LineBorder(Color.GRAY),"Controles Vue Locale");
			contLoc.setTitleJustification(TitledBorder.LEFT);
			contLoc.setTitlePosition(TitledBorder.TOP);
			changementVues.setBorder(contLoc);

			JPanel switchVues = new JPanel();
			{
				switchVues.setLayout(new FlowLayout());

				JRadioButton radioAdj = new JRadioButton("Vue cases adjacentes");
				JRadioButton radio3D = new JRadioButton("Vue 3D");
				radio3D.setSelected(true);
				
				ButtonGroup group = new ButtonGroup();
				group.add(radioAdj);
				group.add(radio3D);

				switchVues.add(radioAdj);
				switchVues.add(radio3D);
				
				vueAdj = new VueAdjacents(gestionnaire);
				
				ControleurSwitchVues csv = new ControleurSwitchVues(east, vueAdj, v3D);
				radioAdj.addActionListener(csv);
				radio3D.addActionListener(csv);
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

			changementVues.add(switchVues);
			changementVues.add(vue3D);
		}		

		player = new Player(gestionnaire);

		JPanel sudEast = new JPanel();
		{
			sudEast.setLayout(new GridLayout(2,1));
			sudEast.add(changementVues);
			sudEast.add(player);
		}

		JPanel sud = new JPanel();
		{
			sud.setLayout(new GridLayout(1,2));
			sud.add(legend);
			sud.add(sudEast);
		}

		return sud;
	}
}

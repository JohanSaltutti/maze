package ui_main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import controller.ControleurAfficherMasquer;
import model.Algorithme;
import model.Gestionnaire;
import view.Player;
import view.VueLabyrinthe;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public abstract class PanneauAlgoGlobal extends PanneauAlgo {

	/**
	 * Attributs representant les panels du nord et du sud
	 */
	private JPanel north, south;
	
	public abstract VueLabyrinthe vue();
	public abstract JPanel legende();

	/**
	 * Constructeur de PanneauAlgoGlobal 
	 * @param g : le gestionnaire
	 * @param a : l'algorithme
	 */
	public PanneauAlgoGlobal(Gestionnaire g, Algorithme a) {
		super(g, a);
		north = north();
		south = south(legende());
		initialiserPanneau();
	}

	@Override
	protected void initialiserPanneau() {
		this.setLayout(new BorderLayout());
		this.add(north,BorderLayout.CENTER);
		this.add(south,BorderLayout.SOUTH);
	}

	/**
	 * Methode permettant de creer le panel du nord
	 * @return
	 */
	private JPanel north() {
		
		VueLabyrinthe vueLaby = vue();
		vues.add(vueLaby);
		
		JPanel nord = new JPanel();
		nord.setLayout(new BoxLayout(nord, BoxLayout.PAGE_AXIS));
		vueLaby.setAlignmentX(CENTER_ALIGNMENT);
		nord.add(vueLaby);

		return nord;
	}

	/**
	 * Methode permettant de creer le panel du sud
	 * @param legend : la legende
	 * @return
	 */
	private JPanel south(JPanel legend) {

		{
			legend.setSize(new Dimension(20,360));
			TitledBorder creerCaseTitle = new TitledBorder(new LineBorder(Color.GRAY), "Legende");
			creerCaseTitle.setTitleJustification(TitledBorder.LEFT);
			creerCaseTitle.setTitlePosition(TitledBorder.TOP);
			legend.setBorder(creerCaseTitle);
			legend.setPreferredSize(new Dimension(30, 100));
		}
		
		JPanel affichage = new JPanel();
		{
			affichage.setLayout(new GridLayout(3, 1));
			
			ControleurAfficherMasquer cam = new ControleurAfficherMasquer(gestionnaire);
			
			JCheckBox valuation = new JCheckBox("Valuations");
			valuation.addActionListener(cam);
			JCheckBox parent = new JCheckBox("Parents");
			parent.addActionListener(cam);
			
			affichage.add(valuation);
			affichage.add(parent);
			
			TitledBorder masquerTitle = new TitledBorder(new LineBorder(Color.GRAY), "Masquer");
			masquerTitle.setTitleJustification(TitledBorder.LEFT);
			masquerTitle.setTitlePosition(TitledBorder.TOP);
			affichage.setBorder(masquerTitle);
		}
		
		JPanel left = new JPanel();
		{
			left.setLayout(new BorderLayout());
			left.add(legend, BorderLayout.CENTER);
			left.add(affichage, BorderLayout.EAST);
		}

		player = new Player(gestionnaire);
		
		
		JPanel sud = new JPanel();
		{
			sud.setLayout(new GridLayout(1,2));
			sud.add(left);
			sud.add(player);
		}

		return sud;
	}
}

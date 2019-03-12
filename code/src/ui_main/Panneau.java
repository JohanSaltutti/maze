package ui_main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.Algorithme;
import model.Gestionnaire;
import view.Vue;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public abstract class Panneau extends JPanel{
	
	/**
	 * Attribut permettant de gerer les différents éléments du model
	 */
	protected Gestionnaire gestionnaire;
	protected Algorithme algoCourant;
	protected ArrayList<Vue> vues;
	
	/**
	 * Contructeur qui associe un gestionnaire
	 * @param g
	 */
	protected Panneau(Gestionnaire g, Algorithme a){
		gestionnaire = g;
		vues = new ArrayList<Vue>();
		algoCourant = a;
		if(a != null) {
			gestionnaire.addAlgo(a);
		}
	}
	
	/**
	 * Methode abstraite qui permet d'initialiser un panneau
	 */
	protected abstract void initialiserPanneau();
	
	/**
	 * Getter de l'attribut vues
	 * @return
	 */
	public ArrayList<Vue> getVues(){
		return vues;
	}
	
	/**
	 * Getter de l'attribut algoCourant
	 * @return
	 */
	public Algorithme getAlgoCourant() {
		return algoCourant;
	}
	
	/**
	 * Methode permettant de creer les fleches des boutons de deplacements
	 * @param dir
	 * @return
	 */
	protected static Icon creerFleche(int dir){

		BufferedImage img = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.black);

		switch(dir){

		case 1:{
			g.drawLine(6, 15, 24, 15);
			g.drawLine(6, 15, 12, 9);
			g.drawLine(6, 15, 12, 21);
			break;
		}
		case 2:{
			g.drawLine(15, 24, 15, 6);
			g.drawLine(9, 12, 15, 6);
			g.drawLine(21, 12, 15, 6);
			break;
		}
		case 3:{
			g.drawLine(6, 15, 24, 15);
			g.drawLine(24, 15, 18, 9);
			g.drawLine(24, 15, 18, 21);
			break;
		}
		case 4:{
			g.drawLine(15, 24, 15, 6);
			g.drawLine(15, 24, 9, 18);
			g.drawLine(15, 24, 21, 18);
		}
		}

		return new ImageIcon(img);
	}
	
	/**
	 * Methode permettant de creer une legende
	 * @param c : la couleur
	 * @param desc : la description
	 * @return
	 */
	protected JPanel creerLegende(Color c, String desc) {
		return new JPanel(){
			public void paintComponent(Graphics g) {
				Graphics2D gg = (Graphics2D)g;
				gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				g.drawString(desc, 50, 25);
				g.setColor(c);
				g.fillRect(20, 10, 20, 20);
			}
		};
	}
}
	
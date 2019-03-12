package view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.Gestionnaire;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public abstract class Vue extends JPanel implements Observer {

	/**
	 * Attributs correspondant a la largeur, la hauteur, la taille d'une case et les marges verticale et horizontale
	 */
	protected int WIDTH, HEIGHT, TAILLE_CASE, MARGIN_VERT, MARGIN_HORIZ;
	
	/**
	 * Attribut permettant de gerer les differents elements du model
	 */
	protected Gestionnaire gestionnaire;
	
	/**
	 * Attribut permettant de dessiner le labyrinthe
	 */
	public static DessineurLabyrinthe dess;
	
	/**
	 * Constructeur de Vue
	 * @param g : le gestionnaire
	 */
	protected Vue(Gestionnaire g){
		gestionnaire = g;
	}
	
	/**
	 * Getter de l'attribut TAILLE_CASE 
	 * @return : entier représentant la taille d'une case
	 */
	public int getTailleCase(){
		return TAILLE_CASE;
	}
	
	/**
	 * Methode permettant de redefinir la taille de l'interface graphique
	 */
	protected abstract void resize();
	
	public void update(Observable o, Object arg) {
		repaint();	
	}
}

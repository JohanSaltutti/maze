package view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

import model.AlgorithmeCheminement;
import model.Gestionnaire;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class SliderEtats extends JSlider implements Observer {
	
	/**
	 * Attribut representant le gestionnaire
	 */
	private Gestionnaire gest;
	
	/**
	 * Constructeur de SliderEtats
	 * @param g : le gestionnaire
	 * @param min : le minimum d'etats
	 * @param max : le maximum d'etats
	 * @param value : la valeur de l'etat
	 */
	public SliderEtats(Gestionnaire g, int min, int max, int value) {
		super(min, max, value);
		this.setName("states");
		gest = g;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		AlgorithmeCheminement a = (AlgorithmeCheminement) gest.getAlgoCourant();
		
		int n = a.getNbEtats();
		int curr = a.getEtatCourant();
		
		ChangeListener cl = getChangeListeners()[0];
		removeChangeListener(cl);
		
		if(curr != 0 && curr == n) {
			setMaximum(n);
		}
		setValue(curr);
		
		addChangeListener(cl);
	}

}
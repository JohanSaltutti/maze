package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import view.Vue3D;
import view.VueAdjacents;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class ControleurSwitchVues implements ActionListener{

	/**
	 * Attribut permettant d'avoir la vue des cases adjacentes
	 */
	private VueAdjacents va;
	
	/**
	 * Attribut permettant d'avoir la vue 3D
	 */
	private Vue3D v3D;
	
	/**
	 * Attribut permettant d'avoir de récupérer le panneau parent 
	 */
	private JPanel parent;

	/**
	 * Constructeur de ControleurSwitchVues
	 * @param g : le gestionnaire
	 */
	public ControleurSwitchVues(JPanel p, VueAdjacents va, Vue3D v3) {
		this.va = va;
		v3D = v3; 
		parent = p;
	}
	
	/**
	 * Methode permettant de definir l'action a effectuer lors de l’interaction avec le controleur
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JRadioButton b = ((JRadioButton)(e.getSource()));

		switch(b.getText()) {
		case "Vue cases adjacentes":
			parent.remove(v3D);
			parent.add(va);
			parent.repaint();
			va.repaint();
			parent.validate();
			va.validate();
			break;
			
		case "Vue 3D":
			parent.remove(va);
			parent.add(v3D);
			parent.repaint();
			v3D.repaint();
			parent.validate();
			v3D.validate();
			
			break;
		}
	}
}

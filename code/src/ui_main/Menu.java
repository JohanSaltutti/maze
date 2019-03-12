package ui_main;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import controller.ControleurMenu;
import model.Gestionnaire;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class Menu extends JMenuBar {
	
	/**
	 * Attribut permettant de gerer les différents elements du model
	 */
	private Gestionnaire gestionnaire;
	
	/**
	 * Constructeur du menu
	 * @param g : le gestionnaire
	 */
	public Menu(Gestionnaire g) {
		super();
		gestionnaire = g;
		initialiserSousMenus();
	}

	/**
	 * Methode permettant d'initialiser le menu en ajoutant les onglets dessus
	 */
	public void initialiserSousMenus(){

		//Menu fichier
		JMenu fichier = new JMenu("Fichier"); 
		
		JMenuItem ouvrir = new JMenuItem("Ouvrir", new ImageIcon(getClass().getClassLoader().getResource("icons/open.png")));
		ouvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));	
		ouvrir.addActionListener(new ControleurMenu(gestionnaire));
		fichier.add(ouvrir);
			
		JMenuItem sauvegarder = new JMenuItem("Sauvegarder", new ImageIcon(getClass().getClassLoader().getResource("icons/save.png")));
		sauvegarder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));	
		sauvegarder.addActionListener(new ControleurMenu(gestionnaire));
		fichier.add(sauvegarder);
		
		JMenuItem export = new JMenuItem("Exporter PNG", new ImageIcon(getClass().getClassLoader().getResource("icons/png.png")));
		export.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_MASK));	
		export.addActionListener(new ControleurMenu(gestionnaire));
		fichier.add(export);
		
		JSeparator js = new JSeparator(JSeparator.HORIZONTAL);
		fichier.add(js);
		
		JMenuItem quitter = new JMenuItem("Quitter", new ImageIcon(getClass().getClassLoader().getResource("icons/exit.png")));
		quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));	
		quitter.addActionListener(new ControleurMenu(gestionnaire));
		fichier.add(quitter);
		
		JMenu aide = new JMenu("Aide");
		
		JMenuItem aPropos = new JMenuItem("A Propos", new ImageIcon(getClass().getClassLoader().getResource("icons/about_us.png")));
		aPropos.addActionListener(new ControleurMenu(gestionnaire));
		aide.add(aPropos);
		
		add(fichier);
		add(aide);
	}
}

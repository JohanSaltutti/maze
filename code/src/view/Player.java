package view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import controller.ControleurPlayer;
import model.Gestionnaire;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class Player extends JPanel{

	/**
	 * Attribut representant la liste des etats
	 */
	private SliderEtats states;
	
	/**
	 * Constructeur de Player
	 * @param g : le gestionnaire
	 */
	public Player(Gestionnaire g) {
		
		ControleurPlayer cp = new ControleurPlayer(g);
		
		JPanel startAlgo = new JPanel();

		states = new SliderEtats(g, 0, 0, 0);
		states.addChangeListener(cp);
		
		JSlider speed = new JSlider(0, 1999, 1500);
		speed.addChangeListener(cp);
		speed.setName("speed");

		startAlgo.add(new JLabel("Etats"));
		startAlgo.add(states);
		startAlgo.add(new JLabel("Vitesse"));
		startAlgo.add(speed);

		JPanel playerContent = new JPanel();
		
		JButton debut = new JButton(new ImageIcon(getClass().getClassLoader().getResource("icons_player/debut.png")));
		debut.setName("debutPlayer");
		debut.addActionListener(cp);

		JButton retour = new JButton(new ImageIcon(getClass().getClassLoader().getResource("icons_player/retour.png")));
		retour.setName("retourPlayer");
		retour.addActionListener(cp);

		JButton pause = new JButton(new ImageIcon(getClass().getClassLoader().getResource("icons_player/pause.png")));
		pause.setName("pausePlayer");
		pause.addActionListener(cp);

		JButton play = new JButton(new ImageIcon(getClass().getClassLoader().getResource("icons_player/play.png")));
		play.setName("demarrerPlayer");
		play.addActionListener(cp);
		
		JButton stop = new JButton(new ImageIcon(getClass().getClassLoader().getResource("icons_player/stop.png")));
		stop.setName("reinitAlgo");
		stop.addActionListener(cp);

		JButton avant = new JButton(new ImageIcon(getClass().getClassLoader().getResource("icons_player/avant.png")));
		avant.setName("avantPlayer");
		avant.addActionListener(cp);

		playerContent.setLayout(new GridLayout(1,7));
		playerContent.add(debut);
		playerContent.add(retour);
		playerContent.add(play);
		playerContent.add(pause);
		playerContent.add(stop);
		playerContent.add(avant);

		TitledBorder playerTitle = new TitledBorder(new LineBorder(Color.GRAY),"Player");
		playerTitle.setTitleJustification(TitledBorder.LEFT);
		playerTitle.setTitlePosition(TitledBorder.TOP);
		setBorder(playerTitle);
		setLayout(new GridLayout(2,1));
		add(startAlgo);
		add(playerContent);

	}
	
	/**
	 * Getter de l'attribut states
	 * @return
	 */
	public SliderEtats getSliderEtats() {
		return states;
	}
}
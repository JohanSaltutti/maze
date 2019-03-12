package model;

import java.util.ArrayList;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public abstract class AlgorithmeCheminementLocal extends AlgorithmeCheminement{

	/**
	 * Attribut representant les positions du robots
	 */
	protected ArrayList<Position> positionsRobot;
	
	/**
	 * Attribut representant la position initiale du robot
	 */
	protected static Position entreeRobot;
	
	/**
	 * Constructeur de AlgorithmeCheminementLocal
	 * @param g : le gestionnaire
	 */
	public AlgorithmeCheminementLocal(Gestionnaire g) {
		super(g);
	}

	/**
	 * Getter de l'attribut positionsRobot
	 * @return
	 */
	public ArrayList<Position> getPositionsRobot() {
		return positionsRobot;
	}

	@Override
	public void initialiserAlgorithme() {
		super.initialiserAlgorithme();
		positionsRobot = new ArrayList<Position>();
	}

	/**
	 * Getter de l'attribut entreeRobot
	 * @return
	 */
	public static Position getEntreeRobot() {
		return entreeRobot;
	}

	/**
	 * Setter de l'attribut etreeRobot
	 * @param entreeRobot
	 */
	public static void setEntreeRobot(Position entreeRobot) {
		AlgorithmeCheminementLocal.entreeRobot = entreeRobot;
	}
}

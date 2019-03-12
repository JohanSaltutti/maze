package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class AlgorithmeLocalisation extends Algorithme {

	/**
	 * Attributs representant les orientations
	 */
	public static final int TOURNE_GAUCHE = 0, AVANCE = 1, TOURNE_DROITE = 2;
	
	/**
	 * Attribut permettant de savoir le type de deplacement
	 */
	private int typeDeplacement;
	
	/**
	 * Attribut permettant d'afficher ou non le robot
	 */
	private boolean afficherRobot;
	
	/**
	 * Attributs representant les positions possibles et non possibles
	 */
	private HashMap<Integer, ArrayList<Position>> positionsPossibles, positionsMurees;
	
	/**
	 * Constructeur de AlgorithmeLocalisation
	 * @param g : le gestionnaire
	 */
	public AlgorithmeLocalisation(Gestionnaire g){
		super(g);
		afficherRobot = false;
	}

	/**
	 * Methode qui permet d'initialiser l'algorithme de localisation
	 */
	@Override
	public void initialiserAlgorithme() {
		positionsMurees = new HashMap<Integer, ArrayList<Position>>();
		positionsPossibles = new HashMap<Integer, ArrayList<Position>>();
		
		Case[][] carte = gestionnaire.getLaby().getCarte();
		
		int colonnes = gestionnaire.getLaby().getColonnes();
		int lignes = gestionnaire.getLaby().getLignes();
		
		for(int i = 0 ; i < colonnes ; i++){
			for(int j = 0 ; j < lignes ; j++){
				if(carte[i][j].etreAccessible()){
					int x = carte[i][j].getCoord().x;
					int y = carte[i][j].getCoord().y;
					int pos = (y + lignes * x);
					
					ArrayList<Position> setPos = new ArrayList<Position>();
					setPos.add(new Position(new CaseVide(new Coordonnees(x, y)), Position.NORD));
					setPos.add(new Position(new CaseVide(new Coordonnees(x, y)), Position.EST));
					setPos.add(new Position(new CaseVide(new Coordonnees(x, y)), Position.SUD));
					setPos.add(new Position(new CaseVide(new Coordonnees(x, y)), Position.OUEST));
					
					positionsPossibles.put(pos, setPos);
				}
			}
		}
	}
	
	/**
	 * Methode qui permet d'executer l'algorithme de localisation
	 * @param typeDeplacement : le type de deplacement
	 */
	public void executerAlgorithme(){
		
		positionsMurees = new HashMap<Integer, ArrayList<Position>>();

		//capture de ce que le robot voit
		int visionRobot = gestionnaire.calculerVisionRobot();
		
		Set<Integer> s = positionsPossibles.keySet();
		
		ArrayList<Integer> l = new ArrayList<Integer>();
		for(Integer i : s){
			l.add(i);
		}

		int LIGNES = gestionnaire.getLaby().getLignes();
		
		switch(typeDeplacement){
		case TOURNE_GAUCHE:{
			
			for(int k = 0 ; k < l.size() ; k++){
				
				ArrayList<Position> posSet = positionsPossibles.get(l.get(k));
				
				int i = 0;
				
				while(i < posSet.size()){
					
					Position pos = posSet.get(i);
					
					tournerGauche(pos);
					
					int visionCourante = gestionnaire.calculerVision(pos);
					
					if(visionCourante != visionRobot){
						posSet.remove(i);
					}
					else{
						i++;
					}
				}
				
				if(posSet.isEmpty()){
					positionsPossibles.remove(l.get(k));
				}
			}
			
			break;
		}
	
		case AVANCE:{
			
			HashMap<Integer, ArrayList<Position>> newOne = new HashMap<Integer, ArrayList<Position>>();
			
			for(int k = 0 ; k < l.size() ; k++){
				
				ArrayList<Position> posSet = positionsPossibles.get(l.get(k));
				
				int i = 0;
				int x = 0, y = 0;
				
				while(i < posSet.size()){
					
					Position pos = posSet.get(i);
					
					avancer(pos);
					
					x = pos.getCase().getCoord().x;
					y = pos.getCase().getCoord().y;
					
					int p = y + LIGNES * x;
					
					int visionCourante = gestionnaire.calculerVision(pos);
					
					if(visionCourante == visionRobot){
						ArrayList<Position> pl = newOne.get(p);
						if(pl == null){
							pl = new ArrayList<Position>();
							newOne.put(p, pl);
						}
						pl.add(pos);
					}
					i++;
				}
				
			}
			
			positionsPossibles = newOne;
			break;
		}
			
		case TOURNE_DROITE:{

			for(int k = 0 ; k < l.size() ; k++){
				
				ArrayList<Position> posSet = positionsPossibles.get(l.get(k));
				
				int i = 0;
				
				while(i < posSet.size()){
					
					Position pos = posSet.get(i);
					
					tournerDroite(pos);
					
					int visionCourante = gestionnaire.calculerVision(pos);
					
					if(visionCourante != visionRobot){
						posSet.remove(i);
					}
					else{
						i++;
					}
				}
				
				if(posSet.isEmpty()){
					positionsPossibles.remove(l.get(k));
				}
			}
			
			break;
		}
			
		}
	}
	
	/**
	 * Methode qui permet de supprimer une case où le robot peut se situer
	 * @param c : la case a supprimer
	 */
	public void supprimerCasePossible(Case c){
		//(appel quand on cree Mur pour supprimer les positions sur lesquelles on a positionne un mur)
		//--> si on veut LEGEREMENT(ajout/suppression de murs) changer le labyrinthe sans perdre les positions possibles

		int LIGNES = gestionnaire.getLaby().getLignes();
		int cx = c.getCoord().x;
		int cy = c.getCoord().y;
		int posI = (cy + LIGNES * cx);
		
		ArrayList<Position> setPos = positionsPossibles.get(posI);

		if(setPos != null){
			positionsMurees.put(posI, setPos);
			positionsPossibles.remove(posI, setPos);
		}
	}

	/**
	 * Methode qui permet de remettre la case où peut se situer le robot
	 * @param c : la case a remettre
	 */
	public void remettreCase(Case c){
		//(appel quand on cree Vide pour remettre les positions sur lesquelles on a positionne un mur que l'on a detruit juste apres)
		
		int LIGNES = gestionnaire.getLaby().getLignes();
		int cx = c.getCoord().x;
		int cy = c.getCoord().y;
		int posI = (cy + LIGNES * cx);
		
		ArrayList<Position> setPos = positionsMurees.get(posI);

		if(setPos != null){
			positionsPossibles.put(posI, setPos);
			positionsMurees.remove(posI, setPos);
		}
	}
	
	/**
	 * Methode qui permet de simuler l'avance du robot
	 * @param pos : la direction
	 */
	private void avancer(Position pos){
		if(gestionnaire.getLaby().etreMouvementValide(pos)){
			switch(pos.getDirection()){

			case Position.NORD:{
				pos.getCase().getCoord().y --;
				break;
			}

			case Position.EST:{
				pos.getCase().getCoord().x ++;
				break;
			}

			case Position.SUD:{
				pos.getCase().getCoord().y ++;
				break;
			}

			case Position.OUEST:{
				pos.getCase().getCoord().x --;
				break;
			}
			}
		}
	}
	
	/**
	 * Methode qui permet de simuler la rotation a 90° a droite du robot
	 * @param pos : la direction
	 */
	private void tournerDroite(Position pos){
		switch(pos.getDirection()){
		
		case Position.NORD:{
			pos.setDirection(Position.EST);
			break;
		}
		
		case Position.EST:{
			pos.setDirection(Position.SUD);
			break;
		}
		
		case Position.SUD:{
			pos.setDirection(Position.OUEST);
			break;
		}
		
		case Position.OUEST:{
			pos.setDirection(Position.NORD);
			break;
		}
		}
	}
	
	/**
	 * Methode qui permet de simuler la rotation a 90° a gauche du robot
	 * @param pos : la direction
	 */
	private void tournerGauche(Position pos){
		switch(pos.getDirection()){
		
		case Position.NORD:{
			pos.setDirection(Position.OUEST);
			break;
		}
		
		case Position.EST:{
			pos.setDirection(Position.NORD);
			break;
		}
		
		case Position.SUD:{
			pos.setDirection(Position.EST);
			break;
		}
		
		case Position.OUEST:{
			pos.setDirection(Position.SUD);
			break;
		}
		}
	}
	
	/**
	 * Getter de positionsPossibles
	 * @return : les positions possibles
	 */
	public HashMap<Integer, ArrayList<Position>> getPositionsPossibles() {
		return positionsPossibles;
	}

	public void setTypeDeplacement(int typeDeplacement) {
		this.typeDeplacement = typeDeplacement;
	}

	public void afficherRobot(boolean b) {
		afficherRobot = b;
	}

	public boolean isAfficherRobot() {
		return afficherRobot;
	}
}
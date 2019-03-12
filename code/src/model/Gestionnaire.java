package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class Gestionnaire extends Observable {

	/**
	 * Attribut representant le labyrinthe actuel
	 */
	private Labyrinthe laby;

	/**
	 * Attribut representant le robot
	 */
	private Robot robot;

	/**
	 * Attribut representant le type de case actuel
	 */
	private String choixCreationCase = "Mur";

	/**
	 * Attribut representant l'algorithme de localisation
	 */
	private Algorithme algoCourant;

	private ArrayList<Algorithme> algorithmes = new ArrayList<Algorithme>();

	//modification du labyrinthe

	/**
	 * Methode qui permet de changer la taille du labyrinthe
	 * @param lignes : le nombre de lignes
	 * @param colonnes : le nombre de colonnes
	 */
	public void changerTailleLaby(int lignes, int colonnes) {
		if(lignes>0 && colonnes>0) {
			laby.modifierTaille(lignes, colonnes);
			placerRobot();
			
			initialiserAlgorithmes();
			miseAJour();
		}
	}

	/**

	 * Methode qui permet de déplacer le labyrinthe s'il y a de nouvelles cases
	 * @param direction : la nouvelle direction
	 */
	public void deplacerLaby(int direction) {
		laby.deplacerLabyrinthe(direction);
		placerRobot();

		initialiserAlgorithmes();
		miseAJour();
	}

	/**
	 * Methode qui permet de creer un nouveau labyrinthe
	 * @param fl : la creation de labyrinthe
	 */
	public void fabriquerNouveauLabyrinthe(FabriqueLabyrinthe fl){
		Labyrinthe nouveauLabyrinthe = fl.fabriquerLabyrinthe();
		laby = nouveauLabyrinthe;
		placerRobot();

		initialiserAlgorithmes();
		miseAJour();
	}

	/**
	 * Methode qui permet de creer une case vide
	 * @param c : la case vide a creer
	 */
	public void creerCaseVide(Case c) {
		laby.viderCase(c);

		for(Algorithme a : algorithmes) {
			if(!(a instanceof AlgorithmeLocalisation)) {
				a.initialiserAlgorithme();
			}
		}

		miseAJour();
	}

	/**
	 * Methode qui permet de creer un mur
	 * @param c : le mur a creer
	 */
	public void creerCaseMur(Case c) {

		if(!robot.etreSurCase(c) && !c.equals(laby.getEntree()) && !c.equals(laby.getSortie())){
			laby.remplirCase(c);

			for(Algorithme a : algorithmes) {
				if(!(a instanceof AlgorithmeLocalisation)) {
					a.initialiserAlgorithme();
				}
			}

			miseAJour();
		}
	}

	/**
	 * Methode qui permet de creer une case de sortie
	 * @param c : la case de sortie a creer
	 */
	public void creerCaseSortie(Case c) {

		if(!c.equals(laby.getEntree())) {
			laby.positionnerSortie(c);

			for(Algorithme a : algorithmes) {
				if(!(a instanceof AlgorithmeLocalisation)) {
					a.initialiserAlgorithme();
				}
			}

			miseAJour();
		}
	}

	/**
	 * Methode qui permet de creer une case d'entree
	 * @param c : la case d'entree a creer
	 */
	public void creerCaseEntree(Case c) {
		if(!c.equals(laby.getSortie())) {
			laby.positionnerEntree(c);
			placerRobot();

			initialiserAlgorithmes();
			miseAJour();
		}
	}

	/**
	 * Mzthode qui permet de sauvegarder le labyrinthe dans un fichier
	 * @param file : le fichier de sauvegarde
	 * @throws IOException : erreur d'ouverture ou de sauvegarde
	 */
	public void sauvegarderLabyrinthe(File file) throws IOException {
		laby.sauvegarder(file);
	}

	/**
	 * Methode permettant d'exporter le labyrinthe dans une image
	 * @param file : le fichier de sauvegarde
	 * @throws IOException : erreur d'ouverture ou de sauvegarde
	 */
	public void exporterImage(String file) throws IOException {
		laby.exporterImage(file);
	}

	/**

	 * Methode qui permet d'ouvrir le fichier de sauvegarde (avec l'extension .maze) du labyrinthe
	 * @param file : le fichier à ouvrir
	 * @throws IOException : erreur d'ouverture ou de sauvegarde
	 * @throws LectureFichierException : exception générée selon l'erreur 
	 */
	public void ouvrirLabyrinthe(File file) throws IOException, LectureFichierException {
		laby.ouvrir(file);
		placerRobot();

		initialiserAlgorithmes();
		miseAJour();
	}

	/**
	 * Methode qui permet d'ouvrir le fichier de sauvegarde (avec l'extension .png) du labyrinthe
	 * @param file : le fichier à ouvrir
	 * @throws IOException : erreur d'ouverture ou de sauvegarde
	 * @throws LectureFichierException : exception générée selon l'erreur 
	 */
	public void ouvrirLabyrintheImage(File file) throws IOException, LectureFichierException {
		laby.ouvrir(file);
		placerRobot();

		initialiserAlgorithmes();
		miseAJour();
	}

	/**
	 * Methode retournant la case a droite du robot
	 * (utilisee pour simplifier l'ecriture)
	 * @return La case a droite du robot 
	 */
	public Case caseADroite(){
		Position posRobot = robot.getPosition();
		int x = posRobot.getCase().getCoord().x;
		int y = posRobot.getCase().getCoord().y;
		int direction = posRobot.getDirection();
		Case[][] cases = laby.getCarte();
		Case res = null;

		switch(direction){
		case(Position.NORD):
			res = cases[x+1][y];
		break;
		case(Position.SUD):
			res = cases[x-1][y];
		break;
		case(Position.OUEST):
			res = cases[x][y-1];
		break;
		case(Position.EST):
			res = cases[x][y+1];
		break;	
		}

		return res;
	}

	/**
	 * Methode renvoyant la case devant le robot 
	 * (utilisee pour simplifier l'ecriture)
	 * @return La case devant le robot
	 */
	public Case caseDevant(){

		Position posRobot = robot.getPosition();
		int x = posRobot.getCase().getCoord().x;
		int y = posRobot.getCase().getCoord().y;
		int direction = posRobot.getDirection();

		Case[][] cases = laby.getCarte();
		Case res = null;

		switch(direction){
		case(Position.NORD):
			res = cases[x][y-1];
		break;
		case(Position.SUD):
			res = cases[x][y+1];
		break;
		case(Position.OUEST):
			res = cases[x-1][y];
		break;
		case(Position.EST):
			res = cases[x+1][y];
		break;	
		}
		return res;
	}

	/**
	 * Methode renvoyant le mur en diagonale a droite
	 * @return le mur en diagonale a droite
	 */
	public Case murADroiteAvant() {

		Position posRobot = robot.getPosition();
		int x = posRobot.getCase().getCoord().x;
		int y = posRobot.getCase().getCoord().y;
		int direction = posRobot.getDirection();

		Case[][] cases = laby.getCarte();
		Case res = null;

		switch(direction){
		case(Position.NORD):
			res = cases[x+1][y+1];
		break;
		case(Position.SUD):
			res = cases[x-1][y-1];
		break;
		case(Position.OUEST):
			res = cases[x+1][y-1];
		break;
		case(Position.EST):
			res = cases[x-1][y+1];
		break;	
		}
		return res;
	}

	//actions sur le robot

	/**
	 * Methode qui permet de placer un robot dans le labyrinthe
	 */
	public void placerRobot() {
		Case c = laby.getEntree();
		int entreeX = c.getCoord().x, entreeY = c.getCoord().y;
		robot.changerPosition(new Position(new CaseVide(new Coordonnees(entreeX, entreeY)), robot.getPosition().getDirection()));
		AlgorithmeCheminementLocal.setEntreeRobot(new Position(new CaseVide(new Coordonnees(entreeX, entreeY)), robot.getPosition().getDirection()));
	}

	public void changerPositionRobot(Position p) {
		robot.changerPosition(AlgorithmeCheminement.copierPosition(p));
	}

	/**
	 * Methode qui permet de faire avancer le robot
	 */
	public void avancerRobot() {
		Position positionRobot = robot.getPosition();
		if (laby.etreMouvementValide(positionRobot)) {
			robot.avancer();
		}

		if(algoCourant instanceof AlgorithmeLocalisation){
			((AlgorithmeLocalisation) algoCourant).setTypeDeplacement(AlgorithmeLocalisation.AVANCE);
			((AlgorithmeLocalisation) algoCourant).executerAlgorithme();
		}

		miseAJour();
	}

	/**
	 * Methode qui permet de faire une rotation de 90° a droite au robot
	 */
	public void tournerADroiteRobot() {
		robot.tournerADroite();

		if(algoCourant instanceof AlgorithmeLocalisation){
			((AlgorithmeLocalisation) algoCourant).setTypeDeplacement(AlgorithmeLocalisation.TOURNE_DROITE);
			((AlgorithmeLocalisation) algoCourant).executerAlgorithme();
		}

		miseAJour();
	}

	/**
	 * Methode qui permet de faire une rotation de 90° a gauche au robot
	 */
	public void tournerAGaucheRobot() {	
		robot.tournerAGauche();

		if(algoCourant instanceof AlgorithmeLocalisation){
			((AlgorithmeLocalisation) algoCourant).setTypeDeplacement(AlgorithmeLocalisation.TOURNE_GAUCHE);
			((AlgorithmeLocalisation) algoCourant).executerAlgorithme();
		}

		miseAJour();
	}

	/**
	 * Methode qui permet de calculer la vision du robot
	 * @return : la vision du robot
	 */
	public int calculerVisionRobot(){
		return calculerVision(robot.getPosition());
	}

	/**
	 * Methode qui permet de calculer la vision
	 * @param p : la position a partir de laquelle calculer
	 * @return : la vision
	 */
	public int calculerVision(Position p){
		int xPos = p.getCase().getCoord().x;
		int yPos = p.getCase().getCoord().y;

		Case gauche, devant, droite;
		gauche = devant = droite = null;

		int res = 0;

		switch(p.getDirection()){

		case Position.NORD:{
			gauche = laby.getCarte()[xPos-1][yPos];
			devant = laby.getCarte()[xPos][yPos-1];
			droite = laby.getCarte()[xPos+1][yPos];
			break;
		}

		case Position.EST:{
			gauche = laby.getCarte()[xPos][yPos-1];
			devant = laby.getCarte()[xPos+1][yPos];
			droite = laby.getCarte()[xPos][yPos+1];
			break;
		}

		case Position.SUD:{
			gauche = laby.getCarte()[xPos+1][yPos];
			devant = laby.getCarte()[xPos][yPos+1];
			droite = laby.getCarte()[xPos-1][yPos];
			break;
		}

		case Position.OUEST:{
			gauche = laby.getCarte()[xPos][yPos+1];
			devant = laby.getCarte()[xPos-1][yPos];
			droite = laby.getCarte()[xPos][yPos-1];
			break;
		}
		}

		if(! gauche.etreAccessible()){
			res = 1;
		}
		res <<= 1;

		if(! devant.etreAccessible()){
			res += 1;
		}

		res <<= 1;

		if(! droite.etreAccessible()){
			res += 1;
		}

		return res;
	}

	/**
	 * Setter de afficherRobot
	 * @param afficherRobot : le changement d'affichage du robot
	 */
	public void afficherRobot(boolean a) {
		if(algoCourant instanceof AlgorithmeLocalisation) {
			((AlgorithmeLocalisation) algoCourant).afficherRobot(a);
			miseAJour();
		}
	}

	/**
	 * Methode qui permet de positionner aléatoirement le robot dans le labyrinthe
	 */
	public void placerAleatoireRobot() {
		int xAlea, yAlea;

		do {
			xAlea = (int)(Math.random()*laby.getColonnes());
			yAlea = (int)(Math.random()*laby.getLignes());
		}
		while(!laby.getCarte()[xAlea][yAlea].etreAccessible());

		robot.changerPosition(new Position(new CaseVide(new Coordonnees(xAlea, yAlea)), robot.getPosition().getDirection()));
		miseAJour();
	}

	//algorithmes

	/**
	 * Methode permettant d'ajouter un algorithme
	 * @param a : l'algorithme a ajouter
	 */
	public void addAlgo(Algorithme a) {
		algorithmes.add(a);
	}

	/**
	 * Methode permettant d'initialiser un algorithme
	 */
	public void initialiserAlgorithmes() {
		for(Algorithme a : algorithmes) {
			a.initialiserAlgorithme();
		}
	}

	/**
	 * Methode permettant de tout mettre a jour
	 */
	public void miseAJour() {
		setChanged();
		notifyObservers();
	}

	//getters

	/**
	 * Setter de Labyrinthe
	 * @param l : le nouveau labyrinthe
	 */
	public void setLabyrinthe(Labyrinthe l) {
		laby = l;
	}

	/**
	 * Setter de Robot
	 * @param r : le nouveau robot
	 */
	public void setRobot(Robot r) {
		robot = r;
		placerRobot();
	}

	/**
	 * Getter de Robot
	 * @return : le robot actuel
	 */
	public Robot getRobot() {
		return robot;
	}

	/**
	 * Getter de Labyrinthe
	 * @return : le labyrinthe actuel
	 */
	public Labyrinthe getLaby() {
		return laby;
	}

	/**
	 * Getter de choixCreationCase
	 * @return : le choix actuel du type de case a creer
	 */
	public String getChoixCreationCase() {
		return choixCreationCase;
	}

	/**
	 * Setter de choixCreationCase
	 * @param s : le nouveau choix
	 */
	public void setChoixCreationCase(String s) {
		choixCreationCase = s;
	}

	/**
	 * Setter de AlgorithmeLocalisation
	 * @param al : le nouvel algorithme de localisation
	 */
	public void setAlgoCourant(Algorithme a){
		algoCourant = a;
	}

	/**
	 * Getter de AlgorithmeLocalisation
	 * @return : l'agorithme de localisation actuel
	 */
	public Algorithme getAlgoCourant(){
		return algoCourant;
	}
}
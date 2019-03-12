package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;


import model.AlgorithmeLocalisation;
import model.Case;
import model.CaseMur;
import model.CaseVide;
import model.Coordonnees;
import model.FabriqueLabyrintheParfait;
import model.Gestionnaire;
import model.Labyrinthe;
import model.LectureFichierException;
import model.Robot;

public class TestGestionnaire {

	private Gestionnaire gestionnaire;
	private Labyrinthe laby;
	private Robot robot;
	private AlgorithmeLocalisation algorithmeLocalisation;
	
	@Before
	public void init() {
		gestionnaire = new Gestionnaire();
		laby = new Labyrinthe();
		robot = new Robot();
		gestionnaire.setLabyrinthe(laby);
		gestionnaire.setRobot(robot);
		algorithmeLocalisation = new AlgorithmeLocalisation(gestionnaire);
		gestionnaire.setAlgoCourant(algorithmeLocalisation);
	}
	
	@Test
	public void testPositionnementInitialRobotX() {
		int positionRobotTestee = -1;
		int positionRobotVoulue = 1;
		
		positionRobotTestee = robot.getPosition().getCase().getCoord().x;
		
		assertEquals("Le robot devrait avoir pour 1 en x", positionRobotVoulue, positionRobotTestee);
	}
	
	@Test
	public void testPositionnementInitialRobotY() {
		int positionRobotTestee = -1;
		int positionRobotVoulue = 1;	
		
		positionRobotTestee = robot.getPosition().getCase().getCoord().y;
		
		assertEquals("Le robot devrait avoir pour 1 en y", positionRobotVoulue, positionRobotTestee);
	}
	
	@Test
	public void testChangerTailleLabyX() {
		int x = 12;
		int y = 15;
		int tailleLabyTestee = -1;
		int tailleLabyVoulue = 12;
		
		gestionnaire.changerTailleLaby(x, y);
		
		tailleLabyTestee = laby.getLignes();
		
		assertEquals("Le laby devrait avoir pour 12 en x", tailleLabyVoulue, tailleLabyTestee);
	}
	
	@Test
	public void testChangerTailleLabyY() {
		int x = 12;
		int y = 15;
		int tailleLabyTestee = -1;
		int tailleLabyVoulue = 15;
		
		gestionnaire.changerTailleLaby(x, y);
		
		tailleLabyTestee = laby.getColonnes();
		
		assertEquals("Le laby devrait avoir pour 15 en y", tailleLabyVoulue, tailleLabyTestee);
	}
	
	@Test
	public void testCreerCaseMur() {
		int positionX = 2;
		int positionY = 2;
		boolean caseAccessible = true;
		CaseMur caseMur = null;
		Coordonnees c = null;
		
		c = new Coordonnees(positionX, positionY);
		caseMur = new CaseMur(c);
		
		caseAccessible = caseMur.etreAccessible();
		
		assertFalse(caseAccessible);
	}
	
	@Test
	public void testCreerCaseVide() {
		int positionX = 2;
		int positionY = 2;
		boolean caseAccessible = true;
		CaseVide caseVide = null;
		Coordonnees c = null;
		
		c = new Coordonnees(positionX, positionY);
		caseVide = new CaseVide(c);
		
		caseAccessible = caseVide.etreAccessible();
		
		assertTrue(caseAccessible);
	}
	
	@Test
	public void testSuperpositionCases() {
		int positionX = 2;
		int positionY = 2;
		boolean caseAccessible = true;
		CaseMur caseMur = null;
		Case caseSortie = null;
		Case caseEntree = null;
		Case[][] caseC;
		Coordonnees c = null;
		
		
		c = new Coordonnees(positionX, positionY);
		caseMur = new CaseMur(c);
		
		caseC = laby.getCarte();
		caseSortie = caseC[positionX][positionY];
		caseEntree = caseC[positionX][positionY];		
		gestionnaire.creerCaseSortie(caseSortie);
		gestionnaire.creerCaseEntree(caseEntree);
		//System.out.println(laby.toString());
		
		caseAccessible = caseMur.etreAccessible();
		
		assertFalse(caseAccessible);
	}
	
	@Test
	public void testAvancerRobotMur() {
		int positionX = 2;
		int positionY = 1;
		boolean test = false;
		CaseMur caseMur = null;
		Coordonnees c = null;
		Case caseTestee = null;
		Case[][] caseC;
		
		c = new Coordonnees(positionX, positionY);
		caseMur = new CaseMur(c);
		gestionnaire.creerCaseMur(caseMur);
		gestionnaire.avancerRobot();
		caseC = laby.getCarte();
		caseTestee = caseC[1][1];
		test = gestionnaire.getRobot().etreSurCase(caseTestee);
		
		assertTrue(test);
	}
	
	@Test
	public void testAvancerRobotPlus1() {
		int positionX = 3;
		int positionY = 1;
		boolean test = false;
		CaseMur caseMur = null;
		Coordonnees c = null;
		Case caseTestee = null;
		Case[][] caseC;
		
		c = new Coordonnees(positionX, positionY);
		caseMur = new CaseMur(c);
		gestionnaire.creerCaseMur(caseMur);
		gestionnaire.avancerRobot();
		caseC = laby.getCarte();
		caseTestee = caseC[1][1];
		test = gestionnaire.getRobot().etreSurCase(caseTestee);
		
		assertFalse(test);
	}
	
	@Test
	public void testMurExterieur() {
		int positionX = 14;
		int positionY = 14;
		CaseMur caseMur = null;
		Coordonnees c = null;
		
		c = new Coordonnees(positionX, positionY);
		caseMur = new CaseMur(c);
		boolean res = laby.etreMurExterieur(caseMur);
		
		assertTrue(res);
	}
	
	@Test
	public void testDirection() {
		int direcTestee = -1;
		int direcVoulue = 2;
		
		direcTestee = gestionnaire.getRobot().getPosition().getDirection();
		
		assertEquals("La direction du robot devrait être 2 (EST)", direcVoulue, direcTestee);
	}
	
	@Test
	public void testDirections() {
		int direcTestee = -1;
		int direcVoulue = 4;
		
		gestionnaire.tournerADroiteRobot();
		gestionnaire.tournerADroiteRobot();
		direcTestee = gestionnaire.getRobot().getPosition().getDirection();
		
		assertEquals("La direction du robot devrait être 4 (OUEST)", direcVoulue, direcTestee);
	}
	
	@Test
	public void testDirectionTournerDroite() {
		int direcTestee = -1;
		int direcVoulue = 3;
		
		gestionnaire.tournerADroiteRobot();
		direcTestee = gestionnaire.getRobot().getPosition().getDirection();
		
		assertEquals("La direction du robot devrait être 3 (SUD)", direcVoulue, direcTestee);
	}
	
	@Test
	public void testDirectionTournerGauche() {
		int direcTestee = -1;
		int direcVoulue = 1;
		
		gestionnaire.tournerAGaucheRobot();
		direcTestee = gestionnaire.getRobot().getPosition().getDirection();
		
		assertEquals("La direction du robot devrait être 1 (NORD)", direcVoulue, direcTestee);
	}
	
	@Test
	public void testVision() {
		int visionTestee = -1;
		int visionVoulue = 4;
		
		visionTestee = gestionnaire.calculerVision(gestionnaire.getRobot().getPosition());
		
		assertEquals("La vision du robot devrait être 4", visionVoulue, visionTestee);
	}
	
	@Test
	public void testVisionApresTourner() {
		int visionTestee = -1;
		int visionVoulue = 6;
		
		gestionnaire.tournerAGaucheRobot();
		visionTestee = gestionnaire.calculerVision(gestionnaire.getRobot().getPosition());
		
		assertEquals("La vision du robot devrait être 4", visionVoulue, visionTestee);
	}
	
	@Test
	public void testSauvegardeOuvertureMaze() throws IOException, LectureFichierException {
		FabriqueLabyrintheParfait flp = null;
		File f = null;
		Case[][] caseLaby = {};
		Case[][] caseLabyTest = {};
		Labyrinthe labyTest = null;
		boolean res = false;
		
		flp = new FabriqueLabyrintheParfait(gestionnaire);
		gestionnaire.fabriquerNouveauLabyrinthe(flp);
		f = new File("./test.maze");
		gestionnaire.sauvegarderLabyrinthe(f);
		caseLaby = gestionnaire.getLaby().getCarte();
		labyTest = new Labyrinthe();
		gestionnaire.setLabyrinthe(labyTest);
		gestionnaire.ouvrirLabyrinthe(f);
		caseLabyTest = gestionnaire.getLaby().getCarte();
		
		for(int y = 0 ; y < caseLaby.length ; y++) {
			for(int x = 0 ; x < caseLaby[y].length ; x++) {
			    if(caseLaby[y][x].equals(caseLabyTest[x][y])) {
			        res = true;
			    }
			}
		}
		
		assertTrue(res);
		
		f.delete();
	}
	
	@Test
	public void testSauvegardeOuvertureImage() throws IOException, LectureFichierException {
		FabriqueLabyrintheParfait flp = null;
		File f = null;
		Case[][] caseLaby = {};
		Case[][] caseLabyTest = {};
		Labyrinthe labyTest = null;
		boolean res = false;
		
		flp = new FabriqueLabyrintheParfait(gestionnaire);
		gestionnaire.fabriquerNouveauLabyrinthe(flp);
		f = new File("./test.png");
		gestionnaire.exporterImage("./" + f.getName());
		caseLaby = gestionnaire.getLaby().getCarte();
		labyTest = new Labyrinthe();
		gestionnaire.setLabyrinthe(labyTest);
		gestionnaire.ouvrirLabyrinthe(f);
		caseLabyTest = gestionnaire.getLaby().getCarte();
		
		for(int y = 0 ; y < caseLaby.length ; y++) {
			for(int x = 0 ; x < caseLaby[y].length ; x++) {
			    if(caseLaby[y][x].equals(caseLabyTest[x][y])) {
			        res = true;
			    }
			}
		}
		
		assertTrue(res);
		
		f.delete();
	}
	
}

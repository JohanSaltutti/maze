package controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Gestionnaire;
import model.LectureFichierException;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class ControleurMenu implements ActionListener {

	/**
	 * Attribut permettant de gerer les differents elements du model
	 */
	private Gestionnaire gestionnaire;
	
	/**
	 * Attribut permettant d'avoir une interface graphique pour choisir des fichiers
	 */
	private JFileChooser jfc;
	
	/**
	 * Attribut permettant de filtrer les extensions des fichiers
	 */
	private FileNameExtensionFilter filtreOuverture;
	private FileNameExtensionFilter filtreSauvegarde;
	private FileNameExtensionFilter filtreExport;
	
	/**
	 * Attribut permettant de definir un fichier
	 */
	private File file;

	/**
	 * Constructeur de ControleurMenu
	 * @param g : le gestionnaire
	 */
	public ControleurMenu(Gestionnaire g) {
		gestionnaire = g;
		filtreOuverture = new FileNameExtensionFilter("Maze or PNG files", "maze", "png");
		filtreSauvegarde = new FileNameExtensionFilter("Maze files", "maze");
		filtreExport = new FileNameExtensionFilter("PNG files", "png");
		jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
	}

	/**
	 * Methode permettant de definir l'action a effectuer lors de l’interaction avec le controleur
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		String text = (((JMenuItem)(e.getSource())).getText());
		try {
			switch(text) {
			case "Ouvrir":
				jfc.setFileFilter(filtreOuverture);
				int res = jfc.showOpenDialog(null);
				if(res == JFileChooser.APPROVE_OPTION) {
					file = jfc.getSelectedFile();
					if(!(file.getName().toLowerCase().endsWith(".maze") || file.getName().toLowerCase().endsWith(".png")))
						JOptionPane.showMessageDialog(null, "Fichier au format incorrect", "Erreur", JOptionPane.INFORMATION_MESSAGE);
					else
						gestionnaire.ouvrirLabyrinthe(file);
				}
				break;

			
			case "Sauvegarder":
				jfc.setFileFilter(filtreSauvegarde);
				res = jfc.showSaveDialog(null);
				if(res == JFileChooser.APPROVE_OPTION) {
					file = jfc.getSelectedFile();
					String fileName = file.toString();
					
					if (!fileName.endsWith(".maze")) {
						fileName += ".maze";
						file = new File(fileName);
					}
					gestionnaire.sauvegarderLabyrinthe(file);
				}			
				break;
				
			case "Exporter PNG":
				jfc.setFileFilter(filtreExport);
				res = jfc.showSaveDialog(null);
				if(res == JFileChooser.APPROVE_OPTION) {
					file = jfc.getSelectedFile();
					String fileName = file.toString();
					
					if (!fileName.endsWith(".png")) {
						fileName += ".png";
						file = new File(fileName);
					}
					gestionnaire.exporterImage(fileName);
				}			
				break;
				
			case "Quitter":
				System.exit(0);
				break;
				
			case "A Propos":
				String aProposContent = "<html><u>Projet Tutore n°18 :</html></u> Localisation d'un robot dans un environnement discret" + "\n" + "\n";
				aProposContent += "Projet realise par :" + "\n";
				aProposContent += "\t" +"\t" + "- Valentin BERARD" + "\n";
				aProposContent += "\t" +"\t" + "- Lucas OBERHAUSSER" + "\n";
				aProposContent += "\t" +"\t" + "- Johan SALTUTTI" + "\n";
				aProposContent += "\t" +"\t" + "- Mathhieu SOMMER" + "\n" + "\n";
				aProposContent += "Ce projet a pour but d'expliquer simplement "+ "\n" 
								+ "les problematiques de localisation et de cheminement " + "\n"
								+ "dans un environnement discret (ici, un labyrinthe)" + "\n"
								 +" a des etudiants debutants lors de salons ou conferences.";
				JOptionPane.showMessageDialog(null, aProposContent, "A Propos", JOptionPane.INFORMATION_MESSAGE);
				break;

			}
			
		} catch (LectureFichierException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Erreur", JOptionPane.INFORMATION_MESSAGE);
		}catch (IOException e1) {
			e1.printStackTrace();
		} 
	}
}

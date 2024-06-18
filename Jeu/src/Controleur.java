package Jeu;

import Jeu.metier.Joueur;
import Jeu.metier.JetonRessource;
import Jeu.metier.IRessource;
import Jeu.metier.Jeton;
import Jeu.metier.PlateauJ;
import Jeu.metier.Pioche;
import Jeu.metier.Plateau;
import Jeu.metier.Ville;
import Jeu.metier.Route;

import Jeu.ihm.FramePlateauJ;
import Jeu.ihm.FramePlateau;
import Jeu.ihm.PanelPlateau;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

public class Controleur
{
	// metier
	private Joueur joueur1 = new Joueur("Charles");
	private Joueur joueur2 = new Joueur("Solène");
	private Plateau plateau;
	private PlateauJ plateauJoueur1;
	private PlateauJ plateauJoueur2;
	private Pioche pioche = new Pioche();
	private static List<Ville> villes = new ArrayList<>();
	private static List<Route> routes = new ArrayList<>();

	// ihm
	private int[] abscissesRessources = { 44, 81, 118, 155, 192, 229, 266, 303 };
	private int[] ordonneesRessources = { 151, 113, 75, 37 };
	private FramePlateau  framePlateau;
	private PanelPlateau  panelPlateau;
	private FramePlateauJ framePlateauJ1;
	private FramePlateauJ framePlateauJ2;
	private Dimension tailleEcran;


	public Controleur()
	{
		this.importerDonnees();

		// metier
		this.plateau        = new Plateau(joueur1, joueur2);
		this.plateauJoueur1 = new PlateauJ(this.joueur1);
		this.plateauJoueur2 = new PlateauJ(this.joueur2);

		this.remplirPlateau(this.plateau);

		// ihm
		this.tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
		
		this.panelPlateau   = new PanelPlateau(this, this.tailleEcran);
		this.framePlateau   = new FramePlateau(this, this.tailleEcran, this.panelPlateau);

		this.framePlateauJ1 = new FramePlateauJ(joueur1.getNumero(), this.tailleEcran);

		this.framePlateauJ2 = new FramePlateauJ(joueur2.getNumero(), this.tailleEcran);

		this.affichagePlateauJ(this.plateauJoueur1, this.framePlateauJ1);
		this.affichagePlateauJ(this.plateauJoueur2, this.framePlateauJ2);
	}

	public void remplirPlateau(Plateau plateau)
	{
		for (int i = 1; i < 31; i++)
		{
			Jeton j = this.pioche.tirerJeton();
			IRessource r = j.getType();
			if (r instanceof JetonRessource)
			{
				Ville v = Ville.trouverVilleParNum(i);
				v.setRessource((JetonRessource) r);
			}
		}
	}

	public void affichagePlateauJ(PlateauJ plateau, FramePlateauJ framePlateau)
	{
		int abscisse = 0;

		for (List<JetonRessource> colonne : plateau.getPlateau())
		{
			for (int ordonnee = 0; ordonnee < colonne.size(); ordonnee++)
			{
				JetonRessource jeton = colonne.get(ordonnee);
				framePlateau.creationLabelJeton(new Jeton(jeton).toString(), this.abscissesRessources[abscisse], this.ordonneesRessources[ordonnee]);
			}
			abscisse += 1;
		}

		for (int i = 0; i < plateau.getNbMonnaie(); i++)
		{
			framePlateau.creationLabelJeton(new Jeton(JetonRessource.MONNAIE).toString(), 44 + i * 37, 225);
		}
	}

	public void dessinerVillesEtRoutes( Graphics g, Graphics2D g2)
	{
		this.villes = Ville.getVilles();
		this.routes = Route.getRoutes();

		for (Ville ville : villes)
		{
			int numVille = ville.getNumero();
			int x = ville.getAbscisse();
			int y = ville.getOrdonnee();
			JetonRessource r = ville.getRessource();
			String nom = ville.getNom();


			if(numVille == 0)
			{
				ville.setImage("NvlRome");
				this.panelPlateau.dessinerVille(g2, g, "NvlRome", x, y, null);
			}
			if (numVille > 0 && numVille <= 5)
			{

				ville.setImage("Mine_Bleu");
				this.panelPlateau.dessinerVille(g2, g, "Mine_Bleu", x, y, nom);
			} 
			else if (numVille > 5 && numVille <= 10)
			{

				ville.setImage("Mine_Gris");
				this.panelPlateau.dessinerVille(g2, g, "Mine_Gris", x, y, nom);
			} 
			else if (numVille > 10 && numVille <= 15)
			{

				ville.setImage("Mine_Jaune");
				this.panelPlateau.dessinerVille(g2, g, "Mine_Jaune", x, y, nom);
			} 
			else if (numVille > 15 && numVille <= 20)
			{

				ville.setImage("Mine_Marron");
				this.panelPlateau.dessinerVille(g2, g, "Mine_Marron", x, y, nom);
			} 
			else if (numVille > 20 && numVille <= 25)
			{

				ville.setImage("Mine_Rouge");
				this.panelPlateau.dessinerVille(g2, g, "Mine_Rouge", x, y, nom);
			} 
			else if (numVille > 25 && numVille <= 30)
			{

				ville.setImage("Mine_Vert");
				this.panelPlateau.dessinerVille(g2, g, "Mine_Vert", x, y, nom);
			}
			if (r != null)
			{
				String nomImage = r.toString().toLowerCase();
				this.panelPlateau.dessinerRessources(g2, nomImage, x, y);
			}
        }

		for ( Route route : routes)
		{
			int epaisseur = (int) (route.getVilleDep().getLargeur() + route.getVilleArr().getLargeur() / 4);
		
			int departx = route.getAbsDep();
			int departy = route.getOrdDep();
			int arriveex = route.getAbsArr();
			int arriveey = route.getOrdArr();
		
			int pasx = (arriveex - departx) / route.getNbTroncons();
			int pasy = (arriveey - departy) / route.getNbTroncons();

			int nbTroncons = route.getNbTroncons();

			this.panelPlateau.dessinerRoute(g, epaisseur, departx, departy, arriveex, arriveey, pasx, pasy, nbTroncons);
		}
	}

	private void importerDonnees()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Importer");
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/../carte/sauvegarde"));
		int userSelection = fileChooser.showOpenDialog(this.framePlateau);

		if (userSelection == JFileChooser.APPROVE_OPTION)
		{
			File selectedFile = fileChooser.getSelectedFile();
			try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile)))
			{
				String line;
				while ((line = reader.readLine()) != null)
				{
					if (line.startsWith("Ville"))
					{
						String[] villeData = line.split(":");
						if (villeData.length >= 3)
						{
							String nom = villeData[1];
							String[] coords = villeData[2].trim().split(",");
							if (coords.length == 2)
							{
								int x = Integer.parseInt(coords[0].trim());
								int y = Integer.parseInt(coords[1].trim());

								Ville.creerVille(nom, x, y);
							}
						}
					}
					else if (line.startsWith("Route"))
					{
						String[] routeData = line.split(":");
						if (routeData.length >= 3) 
						{
							int villeDepNum = Integer.parseInt(routeData[1]);
							int villeArrNum = Integer.parseInt(routeData[2]);
							Ville villeDep = Ville.trouverVilleParNum(villeDepNum);
							Ville villeArr = Ville.trouverVilleParNum(villeArrNum);

							if (villeDep != null && villeArr != null)
							{
								int nbTroncons = Integer.parseInt(routeData[3].trim());
			
								Route.ajouterRoute(nbTroncons, villeDep, villeArr);
							}
						
						}
					}
				}
				JOptionPane.showMessageDialog(this.framePlateau , "Données importées avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
			} 
			catch (IOException e)
			{
				JOptionPane.showMessageDialog(this. framePlateau, "Erreur lors de l'importation des données !", "Erreur", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(this.framePlateau, "Erreur de format dans les données !", "Erreur", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args)
	{
		new Controleur();
	}
}

package Jeu;

import Jeu.metier.Joueur;
import Jeu.metier.JetonRessource;
import Jeu.metier.IRessource;
import Jeu.metier.Jeton;
import Jeu.metier.PlateauJ;
import Jeu.metier.Pioche;
import Jeu.metier.Plateau;
import Jeu.metier.Ville;
import Jeu.metier.ZoneCliquable;
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
	private int hauteurPlateau;
	private int largeurPlateau;

	private Joueur joueur1 = new Joueur("Charles");
	private Joueur joueur2 = new Joueur("Solène");
	private Joueur nvlRome = new Joueur("Romain");
	private Joueur joueurActif;
	private Plateau plateau;
	private PlateauJ plateauJoueur1;
	private PlateauJ plateauJoueur2;
	private Ville ville1, ville2;
	private Pioche pioche = new Pioche();
	private static List<Ville> villes = new ArrayList<>();
	private static List<Route> routes = new ArrayList<>();
	private List<ZoneCliquable> zonesCliquables = new ArrayList<>();

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

		this.tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();

		// ihm
		this.panelPlateau   = new PanelPlateau(this, this.tailleEcran);
		this.framePlateau   = new FramePlateau(this, this.tailleEcran, this.panelPlateau);

		// metier
		this.plateau        = new Plateau(joueur1, joueur2);
		this.plateauJoueur1 = new PlateauJ(this.joueur1);
		this.plateauJoueur2 = new PlateauJ(this.joueur2);

		this.remplirPlateau(this.plateau);

		this.framePlateauJ1 = new FramePlateauJ(joueur1.getNumero(), this.tailleEcran);

		this.framePlateauJ2 = new FramePlateauJ(joueur2.getNumero(), this.tailleEcran);

		Ville.trouverVilleParNum(0).possession(this.nvlRome);
		this.joueurActif = this.joueur1;
	}

	public void setDimension(int hauteur, int largeur)
	{
		this.hauteurPlateau = hauteur;
		this.largeurPlateau = largeur;
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
			
		this.villes = Ville.getVilles();
		this.routes = Route.getRoutes();

		this.remplirVille();

		for ( Route route : routes)
		{
			route.setDep(route.getVilleDep().getAbsCentre(), route.getVilleDep().getOrdCentre());
			route.setArr(route.getVilleArr().getAbsCentre(), route.getVilleArr().getOrdCentre());
		}
	}

	public void remplirVille()
	{
		for (Ville ville : villes)
		{
			int numVille = ville.getNumero();

			if(numVille == 0)
			{
				ville.setImage("NvlRome", this.tailleEcran);
			}
			if (numVille > 0 && numVille <= 5)
			{

				ville.setImage("Mine_Bleu", this.tailleEcran);
			} 
			else if (numVille > 5 && numVille <= 10)
			{

				ville.setImage("Mine_Gris", this.tailleEcran);
			} 
			else if (numVille > 10 && numVille <= 15)
			{

				ville.setImage("Mine_Jaune", this.tailleEcran);
			} 
			else if (numVille > 15 && numVille <= 20)
			{

				ville.setImage("Mine_Marron", this.tailleEcran);
			} 
			else if (numVille > 20 && numVille <= 25)
			{

				ville.setImage("Mine_Rouge", this.tailleEcran);
			} 
			else if (numVille > 25 && numVille <= 30)
			{

				ville.setImage("Mine_Vert", this.tailleEcran);
			}


			int lPanel = this.panelPlateau.getWidth();
            int hPanel = this.panelPlateau.getHeight();

            int x = ville.getAbscisse() * lPanel / 1000;
            int y = ville.getOrdonnee() * hPanel / 800;

            int lImage = (int) (lPanel * 0.04);
            int hImage = lImage;

			this.zonesCliquables.add(new ZoneCliquable(x, y, lImage, hImage, ville));

        }
	}

	public void dessinerVillesEtRoutes( Graphics g, Graphics2D g2)
	{
		this.villes = Ville.getVilles();
		this.routes = Route.getRoutes();

		for ( Route route : routes)
		{
			int epaisseur = (int) ((route.getVilleDep().getLargeur() + route.getVilleArr().getLargeur()) / 4);
		
			int departx = route.getAbsDep();
			int departy = route.getOrdDep();
			int arriveex = route.getAbsArr();
			int arriveey = route.getOrdArr();
			int nbTroncons = route.getNbTroncons();

			int numJoueur = 0;
			if (route.getJoueur() != null)
			{
				numJoueur = route.getJoueur().getNumero();
			}

			this.panelPlateau.dessinerRoute(g, epaisseur, departx, departy, arriveex, arriveey, nbTroncons, numJoueur);
		}

		for (Ville ville : villes)
		{
			int numVille = ville.getNumero();
			String nom = ville.getNom();
			int x = ville.getAbscisse();
			int y = ville.getOrdonnee();
			JetonRessource r = ville.getRessource();
			Image image = ville.getImage();

			if (ville.getNumero() != 0 && r != null)
			{
				String nomImage = r.toString().toLowerCase();
				this.panelPlateau.dessinerVille(g2, g, image, x, y, nom, nomImage);
			}
			else
			{
				this.panelPlateau.dessinerVille(g2, g, image, x, y, null, null);
			}
        }
	}

	public boolean selectionVilles(int x, int y)
	{
		for (ZoneCliquable zone : zonesCliquables)
		{
			if (zone.contains(x, y))
			{
				if (this.ville1 == null)
				{
					this.ville1 = zone.getVilleAssociee();
					return false;
				}
				else
				{
					this.ville2 = zone.getVilleAssociee();
					return true;
				}
			}
		}
		return false;
	}

	public void deplacement()
	{
		if (this.ville1.estAdjacente(this.ville2) && (this.ville1.getJoueur() != null ^ this.ville2.getJoueur() != null) && this.ville1 != this.ville2)
		{
			Route.getRouteAvecVilles(this.ville1, this.ville2).possession(this.joueurActif);
			if (this.ville1.getJoueur() == null)
			{
				this.ville1.setJoueur(this.joueurActif);
				this.joueurActif.setRessource(this.ville1.getRessource());
				this.ville1.enleverRessource();
			}
			else
			{
				this.ville2.setJoueur(this.joueurActif);
				this.joueurActif.setRessource(this.ville2.getRessource());
				this.ville2.enleverRessource();
			}

			this.remplirVille();
			this.changeJoueur();
			this.ville1 = null;
			this.ville2 = null;
		}
	}

	public void changeJoueur()
	{
		if (this.joueurActif == this.joueur1)
		{
			this.joueurActif = this.joueur2;
		}
		else
		{
			this.joueurActif = this.joueur1;
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
			
								Route r = Route.ajouterRoute(nbTroncons, villeDep, villeArr);
								villeDep.ajouterRoute(r);
								villeArr.ajouterRoute(r);
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

package Jeu;

import Jeu.ihm.*;
import Jeu.metier.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.spec.OAEPParameterSpec;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Controleur
{
	// metier

	private int hauteurPlateau;
	private int largeurPlateau;
	private int etape = 0;
	private int tour;
	private String nomJoueur1, nomJoueur2;
	private String scenario;

	private Joueur joueur1 = new Joueur("Charles", 1);
	private Joueur joueur2 = new Joueur("Solène", 2);
	private Joueur nvlRome = new Joueur("Romain", 3);
	private Joueur joueurActif;
	private Plateau plateau;
	private PlateauJ plateauJoueur1;
	private PlateauJ plateauJoueur2;

	private Ville ville1, ville2;
	private Pioche pioche;
	private static List<Ville> villes = new ArrayList<>();
	private static List<Route> routes = new ArrayList<>();
	private List<ZoneCliquable> zonesCliquables = new ArrayList<>();

	// ihm
	private final double[] abscissesRessources = { 0.11, 0.21, 0.31, 0.40, 0.50, 0.59, 0.69, 0.79 };
	private final double[] ordonneesRessources = { 0.54, 0.40, 0.26, 0.12 };

	private FramePlateau framePlateau;
	private PanelPlateau panelPlateau;
	private FrameConnexion frameConnexion;

	private FramePlateauJ framePlateauJ1;
	private FramePlateauJ framePlateauJ2;
	private PanelPlateauJ panelPlateauJ1;
	private PanelPlateauJ panelPlateauJ2;

	private Dimension tailleEcran;

	public Controleur(String scenario)
	{
		this.tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
        this.frameConnexion = new FrameConnexion(this, this.tailleEcran);
        this.scenario = scenario;
	}

	public void demarrerJeu()
    {
        this.importerDonnees();

        this.panelPlateau = new PanelPlateau(this, this.tailleEcran);
        this.framePlateau = new FramePlateau(this, this.tailleEcran, this.panelPlateau);

        this.panelPlateauJ1 = new PanelPlateauJ(this, this.tailleEcran, this.joueur1.getNumero());
        this.panelPlateauJ2 = new PanelPlateauJ(this, this.tailleEcran, this.joueur2.getNumero());
        this.framePlateauJ1 = new FramePlateauJ(this, this.joueur1.getNumero(), this.tailleEcran, this.panelPlateauJ1);
        this.framePlateauJ2 = new FramePlateauJ(this, this.joueur2.getNumero(), this.tailleEcran, this.panelPlateauJ2);

        this.plateau = new Plateau(this.joueur1, this.joueur2);
        this.plateauJoueur1 = new PlateauJ(this.joueur1);
        this.plateauJoueur2 = new PlateauJ(this.joueur2);
        this.pioche = new Pioche();
        this.remplirPlateau(this.plateau);
        this.jouer();
    }

	public void jouerPrecedent()
	{
		this.joueur1 = new Joueur(this.nomJoueur1, 1);
		this.joueur2 = new Joueur(this.nomJoueur2, 2);
		this.plateau = new Plateau(this.joueur1, this.joueur2);
		this.plateauJoueur1.viderPlateauJ();
		this.plateauJoueur2.viderPlateauJ();
		this.panelPlateauJ1.repaint();
		this.panelPlateauJ2.repaint();
		this.vider();
		this.pioche = new Pioche();
		this.remplirPlateau(this.plateau);
		this.jouer();
	}

	public void vider()
	{
		for (Ville ville : this.villes)
		{
			ville.setJoueur(null);
		}
		for (Route route : this.routes)
		{
			route.possession(null);
		}
	}

	public void jouer()
	{
		Ville.trouverVilleParNum(0).possession(this.nvlRome);
		this.importerEtapes();
		this.panelPlateau.repaint();
	}

	public void setDimension(int hauteur, int largeur)
	{
		this.hauteurPlateau = hauteur;
		this.largeurPlateau = largeur;
	}

	public String getJoueurActif()
    {
        if (this.joueurActif != null)
        {
            return this.joueurActif.getNom();
        }
        return this.joueur1.getNom();
    }

	public int getNbPionJ(int numeroJoueur)
	{
		if (numeroJoueur == 1)
		{
			return this.joueur1.getNbJetPoss();
		} else {
			return this.joueur2.getNbJetPoss();
		}
	}
	
	public void viderPlateau(Plateau plateau)
	{

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

		for (Route route : routes)
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

			if (numVille == 0)
			{
				ville.setImage("NvlRome", this.tailleEcran);
			}
			if (numVille > 0 && numVille <= 5)
			{
				ville.setRegion("Bleu");
				ville.setImage("Mine_Bleu", this.tailleEcran);
			} else if (numVille > 5 && numVille <= 10)
			{
				ville.setRegion("Gris");
				ville.setImage("Mine_Gris", this.tailleEcran);
			} else if (numVille > 10 && numVille <= 15)
			{
				ville.setRegion("Jaune");
				ville.setImage("Mine_Jaune", this.tailleEcran);
			} else if (numVille > 15 && numVille <= 20)
			{
				ville.setRegion("Marron");
				ville.setImage("Mine_Marron", this.tailleEcran);
			} else if (numVille > 20 && numVille <= 25)
			{
				ville.setRegion("Rouge");
				ville.setImage("Mine_Rouge", this.tailleEcran);
			} else if (numVille > 25 && numVille <= 30)
			{
				ville.setRegion("Vert");
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

	public void dessinerVillesEtRoutes(Graphics g, Graphics2D g2)
	{
		this.villes = Ville.getVilles();
		this.routes = Route.getRoutes();

		for (Route route : routes)
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
			} else {
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
				if (this.ville1 == null || this.ville2 != null)
				{
					this.ville2 = null;
					this.ville1 = zone.getVilleAssociee();
					return false;
				} else {
					this.ville2 = zone.getVilleAssociee();
					return true;
				}
			}
		}
		return false;
	}

	public void dessinerPlateauJ(Graphics g, int num)
	{
		if (num == 1)
		{
			this.affichagePlateauJ(g, this.plateauJoueur1, this.panelPlateauJ1);
		} else {
			this.affichagePlateauJ(g, this.plateauJoueur2, this.panelPlateauJ2);
		}
	}

	public void affichagePlateauJ(Graphics g, PlateauJ plateau, PanelPlateauJ panel)
	{
		int abscisse = 0;

		for (List<JetonRessource> colonne : plateau.getPlateau())
		{
			for (int ordonnee = 0; ordonnee < colonne.size(); ordonnee++)
			{
				JetonRessource jeton = colonne.get(ordonnee);
				panel.dessinerPlateauJ(g, new Jeton(jeton).toString().toLowerCase(), this.abscissesRessources[abscisse],
						this.ordonneesRessources[ordonnee]);
			}
			abscisse += 1;
		}

		for (int i = 0; i < plateau.getNbMonnaie(); i++)
		{
			panel.dessinerPlateauJ(g, new Jeton(JetonRessource.MONNAIE).toString().toLowerCase(), 0.11 + 0.1 * i, 0.82);
		}

		abscisse = 0;
		for (List<Ville> colonne : plateau.getVilles())
		{

			for (int ordonnee = 0; ordonnee < colonne.size(); ordonnee++)
			{

				Ville v = colonne.get(ordonnee);
				int lPanel = panel.getWidth();
				int hPanel = panel.getHeight();

				panel.dessinerVilles(g, v.getNom(), v.getRegion(), lPanel * 0.67 + 0.053 * lPanel * abscisse, 0.1 * hPanel + 0.1 * hPanel * ordonnee);

			}
			abscisse += 1;
		}
	}

	public void deplacement()
	{
		if (this.ville1.estAdjacente(this.ville2) && (this.ville1.getJoueur() != null ^ this.ville2.getJoueur() != null)
				&& this.ville1 != this.ville2)
		{
			Route.getRouteAvecVilles(this.ville1, this.ville2).possession(this.joueurActif);
			if (this.joueurActif == this.joueur1)
			{
				if (this.ville1.getJoueur() == null)
				{
					this.ville1.setJoueur(this.joueur1);
					this.plateauJoueur1.ajouterJeton(this.ville1.getRessource());
					this.plateauJoueur1.ajouterVille(this.ville1);
					this.ville1.enleverRessource();
				} else {
					this.ville2.setJoueur(this.joueur1);
					this.plateauJoueur1.ajouterJeton(this.ville2.getRessource());
					this.plateauJoueur1.ajouterVille(this.ville2);
					this.ville2.enleverRessource();
				}
				this.panelPlateauJ1.repaint();

			} else {
				if (this.ville1.getJoueur() == null)
				{
					this.ville1.setJoueur(this.joueur2);
					this.plateauJoueur2.ajouterJeton(this.ville1.getRessource());
					this.plateauJoueur2.ajouterVille(this.ville1);
					this.ville1.enleverRessource();
				} else {
					this.ville2.setJoueur(this.joueur2);
					this.plateauJoueur2.ajouterJeton(this.ville2.getRessource());
					this.plateauJoueur2.ajouterVille(this.ville2);
					this.ville2.enleverRessource();
				}
				this.panelPlateauJ2.repaint();
			}

			this.remplirVille();
			this.changeJoueur();
			this.ville1 = null;
			this.ville2 = null;
		}
	}

	public void setNomJoueur(int numeroJoueur, String nomJoueur)
	{
		if(numeroJoueur == 1)
		{
			this.joueur1.setNom(nomJoueur);
			this.nomJoueur1 = nomJoueur;
		}	
		else
		{
			this.joueur2.setNom(nomJoueur);
			this.nomJoueur2 = nomJoueur;
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

	public void importerDonnees()
	{
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filtre = new FileNameExtensionFilter("Carte", "run");
		fileChooser.setDialogTitle("Importer");
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/../carte/sauvegarde"));
		fileChooser.setFileFilter(filtre);
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
				JOptionPane.showMessageDialog(this.framePlateau, "Données importées avec succès !", "Succès",
						JOptionPane.INFORMATION_MESSAGE);
			}
			catch (IOException e)
			{
				JOptionPane.showMessageDialog(this.framePlateau, "Erreur lors de l'importation des données !", "Erreur",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(this.framePlateau, "Erreur de format dans les données !", "Erreur",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	public int getEtape()
	{
		return this.etape;
	}

	public void setEtape(int etape)
	{
		if (etape < 0)
		{
			etape = 0;
		}

		if (etape > 30)
		{
			etape = 30;
		}

		if (etape < this.etape)
		{
			this.etape = etape;
			this.jouerPrecedent();
		}
		else
		{
			this.etape = etape;
			this.jouer();
		}
	}

	public void importerEtapes()
	{
		if (this.scenario  == "")
		{
			this.joueurActif = this.joueur1;
			return;
		}
		try (BufferedReader reader = new BufferedReader(new FileReader("../carte/sauvegarde/scenario/" + this.scenario)))
		{
			String line;
			while ((line = reader.readLine()) != null)
			{
				if (line.startsWith("Etape"))
				{
					String[] etapeData = line.split(":");
					if (etapeData.length >= 3)
					{
						int numEtape = Integer.parseInt(etapeData[1]);
						if (numEtape < this.etape)
						{
							if ((numEtape % 2) == 0)
							{
								this.joueurActif = this.joueur1;
							}
							else
							{
								this.joueurActif = this.joueur2;
							}
							int villeDepNum = Integer.parseInt(etapeData[2]);
							int villeArrNum = Integer.parseInt(etapeData[3]);
							this.ville1 = Ville.trouverVilleParNum(villeDepNum);
							this.ville2 = Ville.trouverVilleParNum(villeArrNum);
							if (this.ville1 != null && this.ville2 != null)
							{
								this.deplacement();
							}
						}
					}
				}
			}
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(this.framePlateau, "Erreur lors de l'importation des données !", "Erreur",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this.framePlateau, "Erreur de format dans les données !", "Erreur",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args)
	{
		if (args.length == 0)
		{
			new Controleur("");
		}
		else
		{
			new Controleur(args[0]);
		}
	}
}

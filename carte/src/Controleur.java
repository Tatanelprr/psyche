package carte;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

import carte.ihm.FrameReseau;
import carte.metier.Ville;
import carte.metier.Route;

public class Controleur
{
	private List<Ville> tabVilles;
	private List<Route> tabRoutes;
	private FrameReseau frame;
	private Ville       selectedVille = null;
	private int         offsetX, offsetY;

	public Controleur()
	{
		this.frame     = new FrameReseau(this);
		this.tabVilles = Ville.getVilles();
		this.tabRoutes = Route.getRoutes();
	}

	public List<String> creerListe()
	{
		List<String> liste = new ArrayList<String>();

		for (Ville ville : Ville.getVilles()) 
		{
			String val = ville.getNumero() + ";" + ville.getNom();
			liste.add(val);
		}
		return liste;
	}

	public Object[][] creerDataRoute()
	{
		Object[][] data;
		data = new Object[this.tabRoutes.size()][3];
		for (int i = 0; i < this.tabRoutes.size(); i++)
		{
			Route route = this.tabRoutes.get(i);
			data[i][0] = route.getVilleDep().getNom();
			data[i][1] = route.getVilleArr().getNom();
			data[i][2] = route.getNbTroncons();
		}
		return data;
	}

	public Object[][] creerDataVille()
	{
		Object[][] data;
		data = new Object[this.tabVilles.size()][4];
		for (int i = 0; i < this.tabVilles.size(); i++)
		{
			Ville ville = this.tabVilles.get(i);
			data[i][0] = ville.getNumero();
			data[i][1] = ville.getNom();
			data[i][2] = ville.getAbscisse();
			data[i][3] = ville.getOrdonnee();
		}
		return data;
	}

	public boolean ajouterRoute(int nbTroncons, String villeDep, String villeArr)
	{
		boolean bool = false;

		String[] villeDepInfo = villeDep.split(";");
		String[] villeArrInfo = villeArr.split(";");

		Ville depart = Ville.trouverVilleParNum(Integer.parseInt(villeDepInfo[0]));
		Ville arrivee = Ville.trouverVilleParNum(Integer.parseInt(villeArrInfo[0]));
		Route route = Route.ajouterRoute(nbTroncons, depart, arrivee);
		if ( route != null)
		{
			bool = true;
			this.frame.rafraichirCarte();
		}
		return bool;
	}

	public void ajouterRoute(int nbTroncons, int villeDep, int villeArr)
	{

		Ville depart = Ville.trouverVilleParNum(villeDep);
		Ville arrivee = Ville.trouverVilleParNum(villeArr);

		Route.ajouterRoute(nbTroncons, depart, arrivee);
		this.frame.rafraichirCarte();
	}

	public boolean creerVille(String nom, int x, int y)
	{
		boolean bool = false;

		Ville ville = Ville.creerVille(nom, x, y);
		if ( ville != null)
		{
			bool = true;
			this.frame.rafraichirCarte();
		}
		return bool;
	}

	public void modifierVille(int ligneSelec, String nom, int x, int y)
	{
		Ville ville = tabVilles.get(ligneSelec);
		if (x >= 0 && x <= 1000 && y >= 0 && y <= 800)
		{
			ville.setNom(nom);
			ville.setAbscisse(x);
			ville.setOrdonnee(y);
		}
	}

	public String donneesVille(MouseEvent e)
	{
		String infoText = "";

		for (Ville ville : Ville.getVilles()) 
			{
				int x = ville.getAbscisse();
				int y = ville.getOrdonnee();

				if (e.getX() >= x && e.getX() <= x + 50 && e.getY() >= y && e.getY() <= y + 50)
				{
					selectedVille = ville;
					this.offsetX = e.getX() - x;
					this.offsetY = e.getY() - y;
					
					infoText = "Nom: " + ville.getNom() + ", X: " + ville.getAbscisse() + ", Y: " + ville.getOrdonnee();
				}
			}
		return infoText;
	}

	public void relacherVille()
	{
		this.selectedVille = null;
	}

	public boolean getSelectedVille()
	{
		boolean bool = false;

		if ( this.selectedVille != null)
		{
			bool = true;
		}
		return bool;
	}

	public void deplacerVille(MouseEvent e)
	{
		if ((e.getX() - this.offsetX) >= 0 && (e.getX() - this.offsetX) <= 1000 && (e.getY() - this.offsetX) >= 0 && (e.getY() - this.offsetX) <= 800)
		{
			selectedVille.setAbscisse(e.getX() - this.offsetX);
    		selectedVille.setOrdonnee(e.getY() - this.offsetY);
		}
	}

	public void dessinerVillesEtRoutes(Graphics g)
	{
		for (Route route : Route.getRoutes())
		{
			int departx = route.getVilleDep().getAbscisse() + 25;
			int departy = route.getVilleDep().getOrdonnee() + 25;
			int pasx = (route.getVilleArr().getAbscisse() - route.getVilleDep().getAbscisse()) / route.getNbTroncons();
			int pasy = (route.getVilleArr().getOrdonnee() - route.getVilleDep().getOrdonnee())  / route.getNbTroncons();
			int nbTroncons = route.getNbTroncons();

			this.frame.dessinerRoute(g, departx, departy, pasx, pasy, nbTroncons);
		}
		
		for (Ville ville : Ville.getVilles())
		{
			int x = ville.getAbscisse();
			int y = ville.getOrdonnee();
			String nom = ville.getNom();

			this.frame.dessinerVille(g, x, y, nom);
		}
	}

	public void ecrireFichier(BufferedWriter writer)
	{
		for (Ville ville : Ville.getVilles())
			{
				String villeString = ville.toString();
				this.frame.ecrireVille(writer, villeString);
            }
            
            for (Route route : Route.getRoutes())
			{
				String routeString = route.toString();
				this.frame.ecrireVille(writer, routeString);
            }
	}

	public void rafraichirCarte()
	{
		this.frame.repaint();
	}

	public static void main(String[] args) 
	{
		new Controleur();
	}
}
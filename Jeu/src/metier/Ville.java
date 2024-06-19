package Jeu.metier;

import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Ville {

    private static int nbVilles = 0;
    private static List<Ville> villes = new ArrayList<>();

    private int absCentre, ordCentre;
    private int numero;
    private String nom;
    private int x, y;
    private int largeur, hauteur;
    private String region;
    private Image image;
    private Joueur joueur = null;

    private JetonRessource ressource;

    private List<Route> routesAdj;

    public static Ville creerVille(String nom, int x, int y)
	{
        if (x >= 0 && x <= 1000 && y >= 0 && y <= 800)
		{
            Ville ville = new Ville(nom, x, y);
            villes.add(ville);
            return ville;
        } else {
            return null;
        }

    }

    private Ville(String nom, int x, int y)
	{
        this.numero = Ville.nbVilles;
        Ville.nbVilles++;
        this.nom = nom;
        this.x = x;
        this.y = y;
        this.routesAdj = new ArrayList<>();
        this.ressource = null;
    }

    public String toString()
	{
        return "Ville " + this.numero + " :" + this.nom + ":" + this.x + "," + this.y;
    }

    public void ajouterRoute(Route route)
	{
        this.routesAdj.add(route);
    }

    public int getAbsCentre()
	{
        return this.absCentre;
    }

    public int getOrdCentre()
	{
        return this.ordCentre;
    }

    public int getAbscisse()
	{
        return this.x;
    }

    public int getOrdonnee()
	{
        return this.y;
    }

    public String getNom()
	{
        return this.nom;
    }

    public String getRegion()
	{
        return this.region;
    }

    public int getLargeur()
	{
        return this.largeur;
    }

    public JetonRessource getRessource()
	{
        return this.ressource;
    }

    public int getHauteur()
	{
        return this.hauteur;
    }

    public Joueur getJoueur()
	{
        return this.joueur;
    }

    public int getNumero()
	{
        return this.numero;
    }

    public Image getImage()
	{
        return this.image;
    }

    public static List<Ville> getVilles()
	{
        return villes;
    }

    public void setAbscisse(int x)
	{
        this.x = x;
    }

    public void setOrdonnee(int y)
	{
        this.y = y;
    }

    public void setRessource(JetonRessource ressource)
	{
        this.ressource = ressource;
    }

    public void setJoueur(Joueur j)
	{
        this.joueur = j;
    }

    public void setRegion(String s)
	{
        this.region = s;
    }

    public void setNom(String nom)
	{
        this.nom = nom;
    }

    public void setImage(String nom, Dimension tailleEcran)
	{
        ImageIcon imageIcon;
        if (this.joueur != null)
		{
            imageIcon = new ImageIcon("images/transparent/" + nom + ".png");
        } else {
            imageIcon = new ImageIcon("images/opaque/" + nom + ".png");
        }

        int hEcran = tailleEcran.height;
        int lEcran = tailleEcran.width;

        Image image = imageIcon.getImage();

        this.largeur = (int) (lEcran * 0.5 * 0.04);
        this.hauteur = this.largeur * image.getHeight(null) / image.getWidth(null);

        this.absCentre = this.x + this.largeur / 2;
        this.ordCentre = this.y + this.hauteur / 2;

        this.image = image;
    }

    public static Ville trouverVilleParNum(int num)
	{
        for (Ville ville : villes)
		{
            if (ville.getNumero() == num)
			{
                return ville;
            }
        }
        return null;
    }

    public void possession(Joueur j)
	{
        if (this.joueur == null)
		{
            this.joueur = j;

        }
    }

    public void enleverRessource()
	{
        this.ressource = null;
    }

    public boolean estAdjacente(Ville autreVille)
	{
        for (Route route : this.routesAdj)
		{
            if ((route.getVilleDep().equals(this) && route.getVilleArr().equals(autreVille))
                    || (route.getVilleDep().equals(autreVille) && route.getVilleArr().equals(this)))
					{
                return true;
            }
        }
        return false;
    }

}

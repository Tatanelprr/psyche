package Jeu.metier;

import java.util.ArrayList;
import java.util.List;

public class Route
{
    private static List<Route> routes = new ArrayList<>();

	private int    nbTroncons;
	private int absDep;
	private int ordDep;
	private int absArr;
	private int ordArr;
	private Ville villeDep  ;
	private Ville villeArr  ;

	public static Route ajouterRoute(int nbTroncons, Ville villeDep,  Ville villeArr)
	{
		if (nbTroncons >= 0 && nbTroncons <= 10)
		{
			Route route = new Route(nbTroncons, villeDep, villeArr);
			routes.add(route);
			return route;
		}
		else
		{
			return null;
		}

	}

	private Route ( int nbTroncons, Ville villeDep,  Ville villeArr)
	{
		this.nbTroncons = nbTroncons;
        this.villeDep = villeDep;
        this.villeArr = villeArr;

        double angle = Math.atan2(ordArr - ordDep, absArr - absDep);
        int distance = 90;

        int nvDepartx = (int) (absDep + distance * Math.cos(angle));
        int nvDeparty = (int) (ordDep + distance * Math.sin(angle));
        int nvArriveex = (int) (absArr - distance * Math.cos(angle));
        int nvArriveey = (int) (ordArr - distance * Math.sin(angle));

        this.absDep = nvDepartx;
        this.ordDep = nvDeparty;
        this.absArr = nvArriveex;
        this.ordArr = nvArriveey;

	}

	public Ville getVilleDep() { return this.villeDep; }

	public Ville getVilleArr() { return this.villeArr; }

	public int getNbTroncons() { return this.nbTroncons; }

	public void setDep(int absDep, int ordDep)
	{
		this.absDep = absDep;
		this.ordDep = ordDep;
	}

	public void setArr(int absArr, int ordArr)
	{
		this.absArr = absArr;
		this.ordArr = ordArr;
	}

	public int getAbsDep(){ return this.absDep; }

	public int getOrdDep(){ return this.ordDep; }

	public int getAbsArr(){ return this.absArr; }

	public int getOrdArr(){ return this.ordArr; }

	public void setAbsDep(int entier){ this.absDep = entier; }

	public void setOrdDep(int entier){ this.ordDep = entier; }

	public void setAbsArr(int entier){ this.absArr = entier; }

	public void setOrdArr(int entier){ this.ordArr = entier; }
	

	public String toString()
	{
		return "Route :" + this.villeDep.getNumero() + ":" + this.villeArr.getNumero() + ":" + this.nbTroncons;
	}
	

	public static List<Route> getRoutes() { return routes; }
}

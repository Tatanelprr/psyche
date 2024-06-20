package Jeu.metier;

import java.util.List;
import java.util.ArrayList;

public class Joueur {

    private int numero;
    private String nom;
    private int nbJetPoss;

    public Joueur(String nom, int num)
	{
        this.numero = num;
        this.nom = nom;
		this.nbJetPoss = 25;
    }

    public int getNumero()
	{
        return this.numero;
    }

    public String getNom()
	{
        return this.nom;
    }

    public int getNbJetPoss()
	{
        return this.nbJetPoss;
    }

    public void setNbJetPoss(int nb)
	{
        this.nbJetPoss -= nb;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

}

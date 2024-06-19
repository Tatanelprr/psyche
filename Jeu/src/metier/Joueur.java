package Jeu.metier;

import java.util.List;
import java.util.ArrayList;

public class Joueur
{
    private static int increment = 1;
    private int numero;
    private String nom;
    private int nbJetPoss = 25;
    private List<JetonRessource> ressources = new ArrayList<>();
    private PlateauJ plateau;

    public Joueur(String nom) {
        this.numero = increment++;
        this.nom = nom;
        this.plateau = new PlateauJ(this);
    }

    public int getNumero() {
        return this.numero;
    }

    public String getNom() {
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

    public void setRessource(JetonRessource r)
    {
        this.plateau.ajouterJeton(r);
    }

    public PlateauJ getPlateau()
    {
        return this.plateau;
    }
}
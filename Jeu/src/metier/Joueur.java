package Jeu.metier;

import java.util.List;
import java.util.ArrayList;

public class Joueur {

    private static int increment = 1;
    private int numero;
    private String nom;
    private int nbJetPoss = 25;

    public Joueur(String nom) {
        this.numero = increment++;
        this.nom = nom;
    }

    public int getNumero() {
        return this.numero;
    }

    public String getNom() {
        return this.nom;
    }

    public int getNbJetPoss() {
        return this.nbJetPoss;
    }

    public void setNbJetPoss(int nb) {
        this.nbJetPoss -= nb;
    }

}

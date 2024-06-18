package Jeu.metier;

public class Joueur
{
    private static int increment = 1;
    private int numero;
    private String nom;

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
}

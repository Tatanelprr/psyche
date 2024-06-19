package Jeu.ihm;

import javax.swing.*;

import Jeu.Controleur;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelPlateauJ extends JPanel {

    private int hEcran, lEcran;
    private int numJoueur;
    private Controleur ctrl;
    private int lImage, hImage;

    public PanelPlateauJ(Controleur ctrl, Dimension tailleEcran, int numeroJoueur)
	{
        this.ctrl = ctrl;
        this.hEcran = tailleEcran.height;
        this.lEcran = tailleEcran.width;
        this.numJoueur = numeroJoueur;
    }

    public void paintComponent(Graphics g)
	{
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Image fond = getToolkit().getImage("images/plateau_joueur_" + this.numJoueur + ".png");

        int largeurOriginale = fond.getWidth(this);
        int hauteurOriginale = fond.getHeight(this);

        this.lImage = getWidth() * 2 / 3;
        this.hImage = (int) ((double) hauteurOriginale / largeurOriginale * lImage);

        g2.drawImage(fond, 0, 0, lImage, hImage, this);

        Image pion = getToolkit().getImage("images/pion_joueur_" + this.numJoueur + ".png");
        int tImage = getHeight() - hImage;

        g2.drawImage(pion, 0, hImage, tImage, tImage, this);

        int nbPion = this.ctrl.getNbPionJ(this.numJoueur);
        String pions = ("X " + nbPion);

        g.drawString(pions, tImage + 5, hImage + tImage / 2);

        this.ctrl.dessinerPlateauJ(g, this.numJoueur);
    }

    public void dessinerPlateauJ(Graphics g, String nom, double x, double y)
	{
        Graphics2D g2 = (Graphics2D) g;

        Image pion = getToolkit().getImage("images/ressources/" + nom + ".png");

        int tPion = (int) (this.lImage * 0.1);

        int x2 = (int) (x * this.lImage);
        int y2 = (int) (y * this.hImage);

        g2.drawImage(pion, x2, y2, tPion, tPion, this);
    }

    public void dessinerVilles(Graphics g, String nom, String region, double x, double y)
	{
        Graphics2D g2 = (Graphics2D) g;

        Image imgMine = getToolkit().getImage("images/opaque/Mine_" + region + ".png");

        int largeurOriginale = imgMine.getWidth(this);
        int hauteurOriginale = imgMine.getHeight(this);

        int lImgMine = (int) (getWidth() * 0.05);
        int hImgMine = (int) ((double) hauteurOriginale / largeurOriginale * lImgMine);

        int x2 = (int) (x);
        int y2 = (int) (y);

        int x3, y3;

        g2.drawImage(imgMine, x2, y2, lImgMine, hImgMine, this);
        if (nom != null)
		{
            x3 = (int) (x2 + lImgMine / 2.5);
            y3 = y2 + hImgMine / 3;
            g2.drawString(nom, x3, y3);
        }
    }
}

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

public class PanelPlateauJ extends JPanel
{
    private int hEcran, lEcran;
    private int numJoueur;
	private Controleur ctrl;

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

        int lPanel = getWidth() * 2 / 3;
		int hPanel = (int) ((double)hauteurOriginale / largeurOriginale * lPanel);

		g2.drawImage(fond, 0, 0, lPanel, hPanel, this);

        
		Image pion = getToolkit().getImage("images/pion_joueur_" + this.numJoueur + ".png");
        int tImage = getHeight() - hPanel;

		g2.drawImage(pion, 0, hPanel, tImage, tImage, this);

        int nbPion = this.ctrl.getNbPionJ(this.numJoueur);
        String pions = ("X " + nbPion);

        g.drawString(pions, tImage + 5, hPanel + tImage / 2);
    }
}
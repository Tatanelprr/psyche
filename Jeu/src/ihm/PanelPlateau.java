package Jeu.ihm;

import javax.swing.*;

import Jeu.Controleur;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

public class PanelPlateau extends JPanel
{
	private int hEcran, lEcran, hPanel, lPanel;
	private Controleur ctrl;

	public PanelPlateau(Controleur ctrl, Dimension tailleEcran)
	{

		this.ctrl = ctrl;
		this.hEcran = tailleEcran.height;
		this.lEcran = tailleEcran.width;

	}

	public void paintComponent(Graphics g)
	{

		Graphics2D g2 = (Graphics2D) g;
		Image fond = getToolkit().getImage("images/Plateau_vierge.png");
		this.lPanel = (int) (this.lEcran * 0.5);
		this.hPanel = lPanel * fond.getHeight(getFocusCycleRootAncestor()) / fond.getWidth(getFocusCycleRootAncestor());

		g2.drawImage(fond, 0, 0,lPanel, hPanel, this);

		this.ctrl.dessinerVillesEtRoutes(g, g2);
    }

    public void dessinerVille(Graphics2D g2, Graphics g, String nom, int x, int y, String num)
	{
		Image image = getToolkit().getImage("images/opaque/" + nom + ".png");

		int x2 = x * this.lPanel / 1000;
		int y2 = y * this.hPanel / 800;

		int lImage = (int) (this.lPanel * 0.04);
		int hImage = lImage * image.getHeight(getFocusCycleRootAncestor()) / image.getWidth(getFocusCycleRootAncestor());

		g2.drawImage(image, x2, y2, lImage, hImage, this);
		if (num != null)
		{
			g2.drawString(num, x2 + 15, y2 + 23);
		}
	}

	public void dessinerRessources(Graphics g2, String nom, int x, int y)
	{
		Image image = getToolkit().getImage("images/ressources/" + nom + ".png");
		
		int x2 = (x * this.lPanel / 1000) + 5;
		int y2 = (y * this.hPanel / 800) + 35;

		int lImage = (int) (this.lPanel * 0.03);
		int hImage = lImage * image.getHeight(getFocusCycleRootAncestor()) / image.getWidth(getFocusCycleRootAncestor());

		g2.drawImage(image, x2, y2, lImage, hImage, this);
	}

	public void dessinerRoute(Graphics g, int epaisseur, int departx, int departy, int arriveex, int arriveey, int pasx, int pasy, int nbTroncons) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.GRAY);
	
		g2d.setStroke(new BasicStroke((float) (epaisseur / 2.5)));
	
		g2d.drawLine(departx, departy, arriveex, arriveey);
		for (int i = 0; i < nbTroncons; i++) {
			g2d.fillOval(departx - (int) (epaisseur / 2), departy - (int) (epaisseur / 2), epaisseur, epaisseur);
			departx += pasx;
			departy += pasy;
		}
		g2d.fillOval(departx - (int) (epaisseur / 2), departy - (int) (epaisseur / 2), epaisseur, epaisseur);
	}
}

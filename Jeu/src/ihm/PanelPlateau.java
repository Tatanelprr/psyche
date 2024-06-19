package Jeu.ihm;

import javax.swing.*;

import Jeu.Controleur;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelPlateau extends JPanel
{
	private int hEcran, lEcran;
	private Controleur ctrl;

	public PanelPlateau(Controleur ctrl, Dimension tailleEcran)
	{
		this.ctrl = ctrl;
		this.hEcran = tailleEcran.height;
		this.lEcran = tailleEcran.width;

		this.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e)
			{
				int x = e.getX();
				int y = e.getY();

				boolean bool = PanelPlateau.this.ctrl.selectionVilles(x, y);
				
				if (bool)
				{
					PanelPlateau.this.ctrl.deplacement();
					PanelPlateau.this.repaint();
				}
			}
		});
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		Image fond = getToolkit().getImage("images/Plateau_vierge.png");

		int lPanel = getWidth();
		int hPanel = getHeight();

		g2.drawImage(fond, 0, 0, lPanel, hPanel, this);

		this.ctrl.dessinerVillesEtRoutes(g, g2);
    }

    public void dessinerVille(Graphics2D g2, Graphics g, Image image, int x, int y, String num, String nom)
	{
		int lPanel = getWidth();
        int hPanel = getHeight();

		int x2 = x * lPanel / 1000;
		int y2 = y * hPanel / 800;

		int x3, y3;

		int lImage = (int) (lPanel * 0.04);
		if (image != null)
		{
			int hImage = lImage * image.getHeight(null) / image.getWidth(null);

			g2.drawImage(image, x2, y2, lImage, hImage, this);
			if (num != null)
			{
				x3 = (int) (x2 + lImage / 2.5);
				y3 = y2 + hImage / 3;
				g2.setFont(new Font("Dialog", Font.PLAIN, 12));
				g2.setColor(Color.BLACK);
				g2.drawString(num, x3, y3);
			}
			if (nom != null)
			{
				Image imageR = getToolkit().getImage("images/ressources/" + nom + ".png");

				lImage = (int) (lPanel * 0.03);		

				g2.setColor(Color.BLACK);

				x3 = x2 + lImage / 4;
				y3 = y2 + hImage / 2;

				g2.drawImage(imageR, x3, y3, lImage, lImage, this);
			}
		}

		g2.setFont(new Font("Serif", Font.BOLD, 24));
		g2.setColor(Color.WHITE);
		g2.drawString("Tour du joueur " + this.ctrl.getJoueurActif(), getWidth()/50, getHeight()/15); 
	}

	public void dessinerRoute(Graphics g, int epaisseur, int departx, int departy, int arriveex, int arriveey, int nbTroncons, int numeroJoueur) 
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.DARK_GRAY);

		int lPanel = getWidth();
        int hPanel = getHeight();
	
		g2d.setStroke(new BasicStroke((float) (epaisseur / 2.5)));
	
		int departx2 = departx * lPanel / 1000;
		int departy2 = departy * hPanel / 800;

		int arriveex2 = arriveex * lPanel / 1000;
		int arriveey2 = arriveey * hPanel / 800;

		g2d.drawLine(departx2, departy2, arriveex2, arriveey2);
		if (nbTroncons == 2)
		{
			g2d.fillOval((departx2 + arriveex2) / 2 - (int) (epaisseur / 2), (departy2 + arriveey2) / 2 - (int) (epaisseur / 2), epaisseur, epaisseur);
		}

		if (numeroJoueur != 0)
        {
            Image image = getToolkit().getImage("images/pion_joueur_" + numeroJoueur + ".png");
            int lImage = (int) (lPanel * 0.04);
            int hImage = lImage * image.getHeight(null) / image.getWidth(null);
            if (nbTroncons == 2)
            {
                g2d.drawImage(image, (departx2 + ((departx2 + arriveex2) / 2 - (int) (epaisseur / 2))) / 2 , (departy2 + ((departy2 + arriveey2) / 2 - (int) (epaisseur / 2))) / 2, epaisseur, epaisseur, this);
                g2d.drawImage(image, (arriveex2 + ((departx2 + arriveex2) / 2 - (int) (epaisseur / 2))) / 2 , (arriveey2 + ((departy2 + arriveey2) / 2 - (int) (epaisseur / 2))) / 2, epaisseur, epaisseur, this);
            }
			else
			{
				g2d.drawImage(image, (departx2 + arriveex2) / 2 - (int) (epaisseur / 2), (departy2 + arriveey2) / 2 - (int) (epaisseur / 2), epaisseur, epaisseur, this);
			}
        }
	}
}

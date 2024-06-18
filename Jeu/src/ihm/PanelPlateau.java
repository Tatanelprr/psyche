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
import java.awt.event.MouseListener;

public class PanelPlateau extends JPanel
{
	private int hEcran, lEcran, hPanel, lPanel;
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

				PanelPlateau.this.ctrl.deplacement(x, y);
			}
		});
	}

	public void paintComponent(Graphics g)
	{

		Graphics2D g2 = (Graphics2D) g;
		Image fond = getToolkit().getImage("images/Plateau_vierge.png");
		this.lPanel = (int) (this.lEcran * 0.5);
		this.hPanel = lPanel * fond.getHeight(getFocusCycleRootAncestor()) / fond.getWidth(getFocusCycleRootAncestor());

		//this.ctrl.setDimension(this.hPanel, this.lPanel);

		g2.drawImage(fond, 0, 0,lPanel, hPanel, this);

		this.ctrl.dessinerVillesEtRoutes(g, g2);
    }

    public void dessinerVille(Graphics2D g2, Graphics g, Image image, int x, int y, String num)
	{

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


		g2.setColor(Color.BLACK);

		g2.drawImage(image, x2, y2, lImage, hImage, this);
	}

	public void dessinerRoute(Graphics g, int epaisseur, int departx, int departy, int arriveex, int arriveey, int nbTroncons, int numeroJoueur) 
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.DARK_GRAY);
	
		g2d.setStroke(new BasicStroke((float) (epaisseur / 2.5)));
	
		int departx2 = departx * this.lPanel / 1000;
		int departy2 = departy * this.hPanel / 800;

		int arriveex2 = arriveex * this.lPanel / 1000;
		int arriveey2 = arriveey * this.hPanel / 800;

		g2d.drawLine(departx2, departy2, arriveex2, arriveey2);
		if (nbTroncons == 2)
		{
			g2d.fillOval((departx2 + arriveex2) / 2 - (int) (epaisseur / 2), (departy2 + arriveey2) / 2 - (int) (epaisseur / 2), epaisseur, epaisseur);
		}

		if (numeroJoueur != 0)
        {
            Image image = getToolkit().getImage("images/pion_joueur_" + numeroJoueur);
            int lImage = (int) (this.lPanel * 0.04);
            int hImage = lImage * image.getHeight(getFocusCycleRootAncestor()) / image.getWidth(getFocusCycleRootAncestor());
            if (nbTroncons == 2)
            {
                g2d.drawImage(image, (departx2 + ((departx2 + arriveex2) / 2 - (int) (epaisseur / 2))) / 2 , (departy2 + ((departy2 + arriveey2) / 2 - (int) (epaisseur / 2))) / 2, lImage, hImage, this);
                g2d.drawImage(image, (arriveex2 + ((departx2 + arriveex2) / 2 - (int) (epaisseur / 2))) / 2 , (arriveey2 + ((departy2 + arriveey2) / 2 - (int) (epaisseur / 2))) / 2, lImage, hImage, this);
            }
			else
			{
				g2d.drawImage(image, (departx2 + arriveex2) / 2 - (int) (epaisseur / 2), (departy2 + arriveey2) / 2 - (int) (epaisseur / 2), epaisseur, epaisseur, this);
			}
        }
	}
}

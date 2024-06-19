package Jeu.ihm;

import javax.swing.*;

import Jeu.Controleur;
import Jeu.ihm.PanelPlateau;

import java.awt.*;

public class FramePlateau extends JFrame
{
	private ImageIcon imagePlateau;
	private int lEcran, hEcran;
	private Controleur ctrl;

	public FramePlateau(Controleur ctrl, Dimension tailleEcran, PanelPlateau panel)
	{
		this.lEcran = tailleEcran.width;
		this.hEcran = tailleEcran.height;
		this.ctrl = ctrl;

		this.setTitle("Plateau");
		this.setSize((int) (this.lEcran * 0.5), this.hEcran);
		this.setLocation((int) (this.lEcran * 0.5), 0);

		this.add(panel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}

package Jeu.ihm;

import Jeu.Controleur;

import javax.swing.*;
import java.awt.*;

public class FramePlateauJ extends JFrame
{
	private ImageIcon imagePlateau;
	private int hEcran, lEcran;
	private Controleur ctrl;

	public FramePlateauJ(Controleur ctrl, int numeroJoueur, Dimension tailleEcran, PanelPlateauJ panel)
	{
		this.lEcran = tailleEcran.width;
		this.hEcran = tailleEcran.height;
		this.ctrl = ctrl;

		this.setTitle("Plateau Joueur " + numeroJoueur);
		
		if ( numeroJoueur == 1)
		{
			this.setLocation(0, 0);
		}
		else
		{
			this.setLocation(0, this.hEcran / 2);
		}

		this.setSize((int) (this.lEcran * 0.45), (int) (this.hEcran * 0.45));
		this.add(panel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}

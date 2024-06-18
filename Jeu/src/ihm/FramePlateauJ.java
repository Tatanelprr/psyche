package Jeu.ihm;

import Jeu.Controleur;

import javax.swing.*;
import java.awt.*;

public class FramePlateauJ extends JFrame
{
	private ImageIcon imagePlateau;
	private JLabel labelPlateau;
	private JLayeredPane layeredPane;
	private int hEcran, lEcran;

	public FramePlateauJ(int numeroJoueur, Dimension tailleEcran)
	{
		this.hEcran = tailleEcran.height;
		this.lEcran = tailleEcran.width;

		int largeurPanel = (int) (this.lEcran * 0.20);

		ImageIcon image = new ImageIcon("images/plateau_joueur_" + numeroJoueur + ".png");

		int hauteurPanel = image.getIconHeight() * largeurPanel / image.getIconWidth();

		Image imageRedimensionnee = image.getImage().getScaledInstance(largeurPanel, hauteurPanel, Image.SCALE_SMOOTH);
		this.imagePlateau = new ImageIcon(imageRedimensionnee);

		this.labelPlateau = new JLabel(this.imagePlateau);
		labelPlateau.setBounds(0, 0, this.imagePlateau.getIconWidth(), this.imagePlateau.getIconHeight());

		this.layeredPane = new JLayeredPane();
		this.layeredPane.setPreferredSize(new Dimension(this.imagePlateau.getIconWidth(), 1000));
		this.layeredPane.add(this.labelPlateau, JLayeredPane.DEFAULT_LAYER);

		this.setTitle("Plateau Joueur " + numeroJoueur);
		
		if ( numeroJoueur == 1)
		{
			this.setLocation(0, 0);
		}
		else
		{
			this.setLocation((int) (this.lEcran * 0.80), 0);
		}

		this.setSize(largeurPanel, this.hEcran);
		this.getContentPane().add(this.layeredPane);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	public void creationLabelJeton(String nomJeton, int absJeton, int ordJeton)
	{
		String cheminImage = "images/ressources/" + nomJeton.toLowerCase() + ".png";
		ImageIcon imageJeton = new ImageIcon(cheminImage);

		Image imageRedimensionnee = imageJeton.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		ImageIcon imageJetonRedimensionnee = new ImageIcon(imageRedimensionnee);

		JLabel labelJeton = new JLabel(imageJetonRedimensionnee);
		labelJeton.setBounds(absJeton, ordJeton, 35, 35);
		
		this.layeredPane.add(labelJeton, JLayeredPane.PALETTE_LAYER);
	}
}

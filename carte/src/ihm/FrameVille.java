package carte.ihm;
import javax.swing.*;

import carte.Controleur;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameVille extends JFrame implements ActionListener
{
	private JPanel     pnlAjout, pnlTableau, pnlDroit;
	private JLabel     lblNom, lblAbscisse, lblOrdonnee;
	private JTextField txtNom, txtAbscisse, txtOrdonnee;
	private JButton    btConfirmer, btModifier;
	private JTable     tblVilles;

	private Controleur c;

	public FrameVille(Controleur c)
	{
		this.c = c;

		this.setSize(900, 300);
		this.setLocation(50, 400);
		this.setTitle("Nouvelle Ville");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// Définition des composants
		this.pnlDroit   = new JPanel(new BorderLayout());
		this.pnlTableau = new JPanel(new BorderLayout());
		this.pnlAjout   = new JPanel(new GridLayout(3, 2));

		this.lblNom      = new JLabel("nom : ");
		this.lblAbscisse = new JLabel("x : "  );
		this.lblOrdonnee = new JLabel("y : "  );

		this.txtNom      = new JTextField(50);
		this.txtAbscisse = new JTextField(5 );
		this.txtOrdonnee = new JTextField(5 );

		this.btConfirmer = new JButton("Confirmer");
		this.btConfirmer.addActionListener(this);

		this.btModifier = new JButton("Modifier");
		this.btModifier.addActionListener(this);

		// Ajout des composants

		this.pnlAjout.add(this.lblNom);
		this.pnlAjout.add(this.txtNom);

		this.pnlAjout.add(this.lblAbscisse);
		this.pnlAjout.add(this.txtAbscisse);

		this.pnlAjout.add(this.lblOrdonnee);
		this.pnlAjout.add(this.txtOrdonnee);

		this.updateTable();

		this.pnlTableau.add(new JScrollPane(tblVilles), BorderLayout.CENTER);
		this.pnlTableau.add(this.btModifier, BorderLayout.SOUTH);

		this.add(this.pnlTableau, BorderLayout.WEST);

		this.pnlDroit.add(this.pnlAjout, BorderLayout.CENTER);
		this.pnlDroit.add(this.btConfirmer, BorderLayout.SOUTH);

		this.add(this.pnlDroit, BorderLayout.CENTER);
		
		this.setVisible(true);
	}

	private void updateTable()
	{

		String[]   nomCol = {"Numéro", "Nom", "x", "y"};
		Object[][] data;

		data = this.c.creerDataVille();
		tblVilles = new JTable(data, nomCol);
		if (this.pnlTableau != null)
		{
			this.pnlTableau.removeAll();
			this.pnlTableau.add(new JScrollPane(this.tblVilles), BorderLayout.CENTER);
			this.pnlTableau.add(this.btModifier, BorderLayout.SOUTH);
			this.pnlTableau.revalidate();
			this.pnlTableau.repaint();
		}
	}

	public void actionPerformed(ActionEvent e)
	{
        String action = e.getActionCommand();
		
        if (action.equals("Confirmer"))
		{
            this.Confirmer();
        }
		else if (action.equals("Modifier"))
		{
            this.Modifier();
        }
    }

    private void Confirmer()
	{
        String nom = this.txtNom.getText();
        String abscisseText = this.txtAbscisse.getText();
        String ordonneeText = this.txtOrdonnee.getText();

        if (nom.isEmpty() || abscisseText.isEmpty() || ordonneeText.isEmpty())
		{
            JOptionPane.showMessageDialog(this, "Tous les champs doivent être complets", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try 
		{
            int x = Integer.parseInt(abscisseText);
            int y = Integer.parseInt(ordonneeText);

            boolean bool = this.c.creerVille(nom, x, y);
	        if (bool == true)
	        {
	            updateTable();
	            this.c.rafraichirCarte();
	            dispose();
	        }
        }
		catch (NumberFormatException ex)
		{
            JOptionPane.showMessageDialog(this, "Les coordonnées doivent être des entiers", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

	private void Modifier()
	{
		int ligneSelec = tblVilles.getSelectedRow();
		if (ligneSelec >= 0)
		{
			String nom = (String) tblVilles.getValueAt(ligneSelec, 1);
			String abscisseText = tblVilles.getValueAt(ligneSelec, 2).toString();
			String ordonneeText = tblVilles.getValueAt(ligneSelec, 3).toString();
	
			if (nom.isEmpty() || abscisseText.isEmpty() || ordonneeText.isEmpty())
			{
				JOptionPane.showMessageDialog(this, "Tous les champs doivent être complets", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}
	
			try
			{
				int x = Integer.parseInt(abscisseText);
				int y = Integer.parseInt(ordonneeText);
	
				this.c.modifierVille(ligneSelec, nom, x, y);
				updateTable();
				this.c.rafraichirCarte();
			}
			catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(this, "Les coordonnées doivent être des entiers", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Sélectionnez une ville à modifier", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}	
}
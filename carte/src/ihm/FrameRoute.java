package carte.ihm;
import javax.swing.*;

import carte.Controleur;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameRoute extends JFrame implements ActionListener
{
	private JPanel			  pnlTableau, pnlAjout, pnlDroit;
	private JLabel 			  lblTroncon, lblVilleDepart, lblVilleArrive;
	private JTextField 		  txtTroncon;
	private JComboBox<String> ddlstVilleDepart, ddlstVilleArrive;
	private JButton 		  btConfirmer;
	private JTable			  tblRoutes;
	private String[]		  nomCol = {"Ville Dep", "Ville Arr", "nb Tronçon"};
	private Object[][]	 	  data;

	private Controleur c;

	public FrameRoute(Controleur c)
	{
		this.c = c;

		this.setSize(800,300);
		this.setLocation(50,50);
		this.setTitle("Nouvelle Route");
		this.setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		//ACTIVATION DES COMPOSANTS;

		this.pnlDroit   = new JPanel(new BorderLayout());
		this.pnlAjout   = new JPanel(new GridLayout(3,2));
		this.pnlTableau = new JPanel(new BorderLayout());

		this.lblTroncon     = new JLabel("Nombre de tronçon(s) :");
		this.lblVilleDepart = new JLabel("Ville de départ : "    );
		this.lblVilleArrive = new JLabel("Ville d'arrivée : "    );

		this.txtTroncon       = new JTextField(20);
		this.ddlstVilleDepart = new JComboBox<>(c.creerListe().toArray(new String[0]));
		this.ddlstVilleArrive = new JComboBox<>(c.creerListe().toArray(new String[0]));

		this.btConfirmer = new JButton("Confirmer");
		this.btConfirmer.addActionListener(this);

		//PLACEMENT DES COMPOSANTS
		this.pnlAjout.add(this.lblTroncon);
		this.pnlAjout.add(this.txtTroncon);

		this.pnlAjout.add(this.lblVilleDepart);
		this.pnlAjout.add(this.ddlstVilleDepart);

		this.pnlAjout.add(this.lblVilleArrive);
		this.pnlAjout.add(this.ddlstVilleArrive);

		this.updateTable();

		this.pnlTableau.add(new JScrollPane(this.tblRoutes), BorderLayout.CENTER);

		this.add(this.pnlTableau, BorderLayout.WEST);

		this.pnlDroit.add(this.btConfirmer, BorderLayout.SOUTH);
		this.pnlDroit.add(this.pnlAjout, BorderLayout.CENTER);

		this.add(pnlDroit, BorderLayout.CENTER);

		this.setVisible(true);

	}

	private void updateTable()
	{
		this.data = this.c.creerDataRoute();
		this.tblRoutes = new JTable(this.data, this.nomCol);
		if (this.pnlTableau != null)
		{
			this.pnlTableau.removeAll();
			this.pnlTableau.add(new JScrollPane(this.tblRoutes), BorderLayout.CENTER);
			this.pnlTableau.revalidate();
			this.pnlTableau.repaint();
		}
	}

	public void actionPerformed(ActionEvent e)
	{
	    String action = e.getActionCommand();
	    if (action.equals("Confirmer"))
	    {
	        String villeDep = (String) this.ddlstVilleDepart.getSelectedItem();
	        String villeArr = (String) this.ddlstVilleArrive.getSelectedItem();
	        String tronconText = this.txtTroncon.getText().trim();

	        if (tronconText.isEmpty())
	        {
	            JOptionPane.showMessageDialog(this, "Veuillez saisir le nombre de tronçons", "Erreur", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

			if (villeDep == null || villeArr == null)
	        {
	            JOptionPane.showMessageDialog(this, "Les Villes de départs et d'arrivées ne peuvent pas être nulles", "Erreur", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

			if (villeArr == villeDep)
	        {
	            JOptionPane.showMessageDialog(this, "La ville de départ et la ville d'arrivée doivent être différentes", "Erreur", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        try
	        {
	            int nbTroncon = Integer.parseInt(tronconText);
	            boolean bool = this.c.ajouterRoute(nbTroncon, villeDep, villeArr);
	            if (bool == true)
	            {
	                updateTable();
	                this.c.rafraichirCarte();
	                dispose();
	            }
	        }
	        catch (NumberFormatException ex)
	        {
	            JOptionPane.showMessageDialog(this, "Veuillez saisir un nombre valide pour le nombre de tronçons", "Erreur", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}
}	
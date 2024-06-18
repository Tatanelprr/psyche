package carte.ihm;

import javax.swing.*;
import javax.swing.filechooser.*;

import carte.Controleur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrameReseau extends JFrame
{
	private JPanel panelCarte;

	private JLabel villesRestLabel = new JLabel("Il reste 31 villes à créer");
	private int    villesRestantes = 31;

	private JLabel routesRestLabel = new JLabel("Il reste au moins 50 routes à créer");
	private int    routesRestantes = 50;

	private JLabel infosVille = new JLabel("");

	private Controleur c;

	public FrameReseau(Controleur c)
	{
		JFrame	frame	   	  = new JFrame("FrameReseau");
		Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();

		this.c = c;

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(tailleEcran.width, tailleEcran.height);
		
		frame.add(mainPanel());
		
		frame.setVisible(true);
	}

	private JPanel mainPanel()
	{
		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout());
		panel.add(panelCarte(), BorderLayout.CENTER);
		panel.add(panelMenu(), BorderLayout.EAST);

		return panel;
	}

	private JPanel panelMenu()
	{
		Dimension tailleBouton = new Dimension(150, 50);

		JPanel menu	= new JPanel();
		JPanel boutons = new JPanel();
		JPanel ville   = new JPanel();

		JButton ajtVille = new JButton("Nouvelle Ville");
		JButton ajtRoute = new JButton("Nouvelle Route");
		JButton exporter = new JButton("Exporter");
		JButton importer = new JButton("Importer");

		ajtRoute.setPreferredSize(tailleBouton);
		ajtRoute.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				new FrameRoute(c);
				routesRestantes--;
				compteAReboursRoute();
			}
		});

		ajtVille.setPreferredSize(tailleBouton);
		ajtVille.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if (villesRestantes > 0)
				{
					new FrameVille(c);
					villesRestantes--;
					compteAReboursVille();
				}
				else
				{
					JOptionPane.showMessageDialog(FrameReseau.this, "Vous avez déjà créé 31 villes!", "Info", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});


		exporter.setBackground(Color.RED);
		exporter.setForeground(Color.WHITE);
		exporter.setPreferredSize(tailleBouton);

		exporter.addMouseListener(new MouseAdapter()
		{
            public void mouseClicked(MouseEvent e)
			{
                exporterDonnees();
            }
        });

		importer.setBackground(Color.BLUE);
		importer.setForeground(Color.WHITE);
		importer.setPreferredSize(tailleBouton);

		importer.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				importerDonnees();
			}
		});

		boutons.setLayout(new GridLayout(7, 1));
		boutons.add(ajtVille);
		boutons.add(ajtRoute);
		boutons.add(importer);
		boutons.add(exporter);
		boutons.add(infosVille);
		boutons.add(villesRestLabel);
		boutons.add(routesRestLabel);

		menu.setLayout(new GridLayout(2, 1));
		menu.add(boutons);
		menu.add(ville);

		return menu;
	}

	private JPanel panelCarte()
	{
		panelCarte = new JPanel() 
		{
			protected void paintComponent(Graphics g) 
			{
				super.paintComponent(g);
				c.dessinerVillesEtRoutes(g);
			}
		};

		panelCarte.addMouseListener(new MouseAdapter()
		{
            public void mousePressed(MouseEvent e)
			{
				String infoText = c.donneesVille(e);
				infosVille.setText(infoText);
				infosVille.revalidate();
				infosVille.repaint();
			}
            public void mouseReleased(MouseEvent e)
			{
                c.relacherVille();
            }
        });

		panelCarte.addMouseMotionListener(new MouseAdapter()
		{
            public void mouseDragged(MouseEvent e)
			{
                if (c.getSelectedVille())
				{
                    c.deplacerVille(e);
                    rafraichirCarte();
                }
            }
        });

		return panelCarte;
	}
	
	public void dessinerVille(Graphics g, int x, int y, String nom)
	{
		g.setColor(Color.CYAN);
		g.fillOval(x, y, 50, 50);
		g.setColor(Color.BLACK);
		g.drawString(nom, x + 25 - (nom.length() / 2) * 7, y + 25);
	}

	public void dessinerRoute(Graphics g, int departx, int departy, int pasx, int pasy, int nbTroncons)
	{
		g.setColor(Color.BLACK);

		for (int i = 1; i < nbTroncons; i++)
		{
			g.drawLine(departx, departy, departx + pasx - pasx / 10, departy + pasy - pasy / 10);
			departx += pasx;
			departy += pasy;
		}
		g.drawLine(departx, departy, departx + pasx, departy + pasy);
	}

	public void rafraichirCarte()
	{
		this.panelCarte.repaint();
	}

	private void exporterDonnees()
	{	
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Exporter");
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/sauvegarde"));
        int userSelection = fileChooser.showSaveDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION)
		{
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile())))
			{

				this.c.ecrireFichier(writer);
                
                JOptionPane.showMessageDialog(this, "Données exportées avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            }
			catch (IOException e)
			{
                JOptionPane.showMessageDialog(this, "Erreur lors de l'exportation des données !", "Erreur", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

	private void importerDonnees()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Importer");
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/sauvegarde"));
		int userSelection = fileChooser.showOpenDialog(this);

		if (userSelection == JFileChooser.APPROVE_OPTION)
		{
			File selectedFile = fileChooser.getSelectedFile();
			try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile)))
			{
				String line;
				while ((line = reader.readLine()) != null)
				{
					if (line.startsWith("Ville"))
					{
						String[] villeData = line.split(":");
						if (villeData.length >= 3)
						{
							String nom = villeData[1];
							String[] coords = villeData[2].trim().split(",");
							if (coords.length == 2)
							{
								int x = Integer.parseInt(coords[0].trim());
								int y = Integer.parseInt(coords[1].trim());

								this.c.creerVille(nom, x, y);
							}
						}
					}
					else if (line.startsWith("Route"))
					{
						String[] routeData = line.split(":");
						if (routeData.length >= 3) 
						{
							int villeDepNum = Integer.parseInt(routeData[1]);
							int villeArrNum = Integer.parseInt(routeData[2]);
							if (villeDepNum != -1 && villeArrNum != -1)
							{
								int nbTroncons = Integer.parseInt(routeData[3].trim());
			
								this.c.ajouterRoute(nbTroncons, villeDepNum, villeArrNum);
							}
						
						}
					}
				}
				rafraichirCarte();
				JOptionPane.showMessageDialog(this, "Données importées avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
			} 
			catch (IOException e)
			{
				JOptionPane.showMessageDialog(this, "Erreur lors de l'importation des données !", "Erreur", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(this, "Erreur de format dans les données !", "Erreur", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	private void compteAReboursVille()
	{
		villesRestLabel.setText("Il reste " + villesRestantes + " villes à créer");
		villesRestLabel.revalidate();
		villesRestLabel.repaint();
	}

	private void compteAReboursRoute()
	{
		if (routesRestantes > 0)
		{
			routesRestLabel.setText("Il reste au moins " + routesRestantes + " routes à créer");
			routesRestLabel.revalidate();
			routesRestLabel.repaint();
		}
		else
		{
			routesRestLabel.setText("Vous avez crée suffisamment de routes  ");
			routesRestLabel.revalidate();
			routesRestLabel.repaint();
		}
	}

	public void ecrireVille(BufferedWriter writer, String ville)
	{
        try 
		{
			writer.write(ville);
			writer.newLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void ecrireRoute(BufferedWriter writer, String route)
	{
        try 
		{
			writer.write(route);
			writer.newLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}

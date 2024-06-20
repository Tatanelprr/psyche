package Jeu.ihm;

import javax.swing.*;
import java.awt.event.*;
import Jeu.Controleur;
import Jeu.ihm.PanelPlateau;
import java.awt.*;

public class FramePlateau extends JFrame implements ActionListener
{
    private int lEcran, hEcran;
    private Controleur ctrl;
    private JMenuItem menuiPrecedent, menuiSuivant;
    private JMenu menuLstAction;

    public FramePlateau(Controleur ctrl, Dimension tailleEcran, PanelPlateau panel)
    {
        this.lEcran = tailleEcran.width;
        this.hEcran = tailleEcran.height;
        this.ctrl = ctrl;

        this.setTitle("Plateau");
        this.setSize((int) (this.lEcran * 0.5), this.hEcran);
        this.setLocation((int) (this.lEcran * 0.5), 0);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuAction = new JMenu("Action");
        this.menuLstAction = new JMenu("Liste Action");

        this.menuiPrecedent = new JMenuItem("Précédent");
        this.menuiSuivant = new JMenuItem("Suivant");

        this.menuiPrecedent.addActionListener(this);
        this.menuiSuivant.addActionListener(this);

        for(int i = 0; i < 31; i++)
        {
            JMenuItem menuiAction = new JMenuItem("Étape n° " + i);
            menuiAction.putClientProperty("index", i);
            menuiAction.addActionListener(this);
            this.menuLstAction.add(menuiAction);
        }

        menuAction.add(this.menuiSuivant);
        menuAction.add(this.menuiPrecedent);
        menuBar.add(menuAction);
        menuBar.add(menuLstAction);
        this.setJMenuBar(menuBar);

        this.add(panel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();

        if (source == this.menuiPrecedent)
        {
            int etape = this.ctrl.getEtape() - 1;
            this.ctrl.setEtape(etape);
            this.ctrl.jouerPrecedent();
        }

        if (source == this.menuiSuivant)
        {
            int etape = this.ctrl.getEtape() + 1;
            this.ctrl.setEtape(etape);
        }

        for (int i = 0; i < menuLstAction.getItemCount(); i++)
        {
            JMenuItem item = menuLstAction.getItem(i);
            if (source == item) {
                this.ctrl.setEtape(i);
            }
        }
    }
}

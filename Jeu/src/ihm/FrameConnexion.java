package Jeu.ihm;

import Jeu.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FrameConnexion extends JFrame
{
    private int lEcran, hEcran;
    private Controleur ctrl;
    private JLabel lblNom1;
    private JLabel lblNom2;
    private JTextField txtInput1;
    private JTextField txtInput2;
    private JButton btnJouer;
    private JPanel pnlBtn;
    private JPanel pnlGrille;

    public FrameConnexion(Controleur ctrl, Dimension tailleEcran)
    {
        this.lEcran = tailleEcran.width;
        this.hEcran = tailleEcran.height;
        this.ctrl = ctrl;

        this.setLayout(new BorderLayout());

        this.pnlBtn = new JPanel();
        this.pnlGrille = new JPanel(new GridLayout(2,2));

        this.lblNom1 = new JLabel("Nom du joueur n° 1");
        this.lblNom2 = new JLabel("Nom du joueur n° 2");
        this.txtInput1 = new JTextField(15);
        this.txtInput2 = new JTextField(15);
        this.btnJouer = new JButton("Jouer");

        this.setTitle("Sélection des joueurs");
        this.setSize((int) (this.lEcran * 0.15), (int)(this.hEcran * 0.1));
        this.setLocation((int) (this.lEcran * 0.5), (int)(this.hEcran * 0.5));


        this.pnlGrille.add(this.lblNom1);
        this.pnlGrille.add(this.txtInput1);

        this.pnlGrille.add(this.lblNom2);
        this.pnlGrille.add(this.txtInput2);

        this.pnlBtn.add(this.btnJouer);
        this.add(this.pnlGrille, BorderLayout.CENTER);
        this.add(this.pnlBtn, BorderLayout.SOUTH);

        this.btnJouer.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) {
                demarrageJeu();
            }
        });

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private void demarrageJeu() {
        if (this.txtInput1.getText().length() > 0 && this.txtInput2.getText().length() > 0) {
            this.ctrl.setNomJoueur(1, this.txtInput1.getText());
            this.ctrl.setNomJoueur(2, this.txtInput2.getText());
            this.ctrl.demarrerJeu();
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez renseigner les noms des joueurs", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
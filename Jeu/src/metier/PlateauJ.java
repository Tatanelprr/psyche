package Jeu.metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class PlateauJ {

    private final int NB_MONNAIE_MAX = 8;
    private Joueur joueur;
    private int nbMonnaie;

    private final int NB_COL_MAX = 7;
    private final int NB_LIG_MAX = 4;
    private final List<List<JetonRessource>> plateau;
    private final List<List<Ville>> villes;

    private int score;
    private String detailScore;

    public PlateauJ(Joueur joueur) {
        this.plateau = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            this.plateau.add(new ArrayList<>());
        }
        this.villes = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            this.villes.add(new ArrayList<>());
        }
        this.nbMonnaie = 0;
        this.score = 0;
        this.detailScore = "";

    }

    public int getScore() {
        return this.score;
    }

    public JetonRessource getRessource(int i, int j) {
        return this.plateau.get(i).get(j);
    }

    public List<List<JetonRessource>> getPlateau() {
        return this.plateau;
    }

    public List<List<Ville>> getVilles() {
        return this.villes;
    }

    public int getNbMonnaie() {
        return this.nbMonnaie;
    }

    public Joueur getJoueur() {
        return this.joueur;
    }

    public String getDetailScore() {
        return this.detailScore;
    }

    public void ajouterVille(Ville v) {
        for (List<Ville> colonne : this.villes) {

            if (!colonne.isEmpty() && (colonne.get(0).getRegion().equals(v.getRegion()))) {

                if (colonne.size() < 4) {
                    colonne.add(v);
                    return;
                }
            }
        }

        for (List<Ville> colonne : this.villes) {
            if (colonne.isEmpty()) {
                colonne.add(v);
                break;
            }
        }

    }

    public boolean ajouterRessource(Jeton r) {
        IRessource type = r.getType();
        if (type instanceof JetonRessource) {
            return ajouterJeton((JetonRessource) type);
        }
        return false;
    }

    public boolean ajouterJeton(JetonRessource jeton) {
        for (List<JetonRessource> colonne : this.plateau) {

            if (jeton.toString().toLowerCase().equals("monnaie")) {
                if (NB_MONNAIE_MAX > this.nbMonnaie) {
                    this.nbMonnaie++;
                    return true;
                }
                return false;
            } else {
                if (!colonne.isEmpty() && colonne.get(0) == jeton) {

                    if (colonne.size() < 4) {
                        colonne.add(jeton);
                        this.triColonnes();
                        return true;
                    }
                }
            }
        }
        for (List<JetonRessource> colonne : plateau) {
            if (colonne.isEmpty()) {
                colonne.add(jeton);
                this.triColonnes();
                return true;
            }
        }
        return false;
    }

    public void calculerScore() {
        int i;
        int j = 0;

        this.detailScore = "Detail :\n	";
        this.detailScore += (String.format("%-15s", "Pieces ") + ": ");

        if (this.nbMonnaie > 1) // Regardes si il y a plus d'une monnaie car 1 monnaie = 0 score
        {
            this.score += this.nbMonnaie * this.nbMonnaie;
            this.detailScore += (this.nbMonnaie * this.nbMonnaie) + " pt\n	";
        }

        for (j = 0; j < NB_COL_MAX; j++) {
            int casesRemplies = 0;

            for (i = 0; i < NB_LIG_MAX; i++) {
                if (plateau.get(i).get(j) != null) {
                    casesRemplies++; // Compte le nombre de cases remplies par colonnes
                }
            }

            switch (casesRemplies) {
                case 0:
                    this.score += 0;
                    this.detailScore += (String.format("%-15s", "Colonne " + (j + 1)) + ": ") + 0 + "  pt\n	";
                    break;
                case 1:
                    this.score += 0;
                    this.detailScore += (String.format("%-15s", "Colonne " + (j + 1)) + ": ") + 0 + "  pt\n	";
                    break;
                case 2:
                    this.score += 2;
                    this.detailScore += (String.format("%-15s", "Colonne " + (j + 1)) + ": ") + 2 + "  pt\n	";
                    break;
                case 3:
                    this.score += 10;
                    this.detailScore += (String.format("%-15s", "Colonne " + (j + 1)) + ": ") + 10 + " pt\n	";
                    break;
                default:
                    this.score += 0;
                    this.detailScore += (String.format("%-15s", "Colonne " + (j + 1)) + ": ") + 0 + "  pt\n	";
                    break;
            }
        }

        for (i = 0; i < NB_LIG_MAX; i++) {
            int casesRemplies = 0;

            for (j = 0; j < NB_COL_MAX; j++) {
                if (plateau.get(i).get(j) != null) {
                    casesRemplies++; // Compte le nombre de cases remplies par lignes
                }
            }
            switch (casesRemplies) {
                case 0:
                    this.score += 0;
                    this.detailScore += (String.format("%-15s", "Ligne " + (i + 1)) + ": ") + 0 + "  pt\n	";
                    break;
                case 1:
                    this.score += 0;
                    this.detailScore += (String.format("%-15s", "Ligne " + (i + 1)) + ": ") + 0 + "  pt\n	";
                    break;
                case 2:
                    this.score += 2;
                    this.detailScore += (String.format("%-15s", "Ligne " + (i + 1)) + ": ") + 2 + "  pt\n	";
                    break;
                case 3:
                    this.score += 5;
                    this.detailScore += (String.format("%-15s", "Ligne " + (i + 1)) + ": ") + 5 + "  pt\n	";
                    break;
                case 4:
                    this.score += 9;
                    this.detailScore += (String.format("%-15s", "Ligne " + (i + 1)) + ": ") + 9 + "  pt\n	";
                    break;
                case 5:
                    this.score += 14;
                    this.detailScore += (String.format("%-15s", "Ligne " + (i + 1)) + ": ") + 14 + " pt\n	";
                    break;
                default:
                    this.score += 0;
                    this.detailScore += (String.format("%-15s", "Ligne " + (i + 1)) + ": ") + 0 + "  pt\n	";
                    break;
            }
        }
    }

    public String toString() {
        String sRet;
        String sep = "+-----";

        sRet = "";
        for (int i = 0; i < NB_LIG_MAX; i++) {
            for (int j = 0; j < NB_COL_MAX; j++) {
                sRet += sep;
            }
            sRet += "+ \n";
            for (int j = 0; j < NB_COL_MAX; j++) {
                sRet += "| ";
                if (plateau.get(i).get(j) == null) {
                    sRet += "    ";
                } else {
                    sRet += plateau.get(i).get(j).getLibCourt();
                    sRet += " ";
                }
            }
            sRet += "|\n";
        }

        for (int j = 0; j < NB_COL_MAX; j++) {
            sRet += sep;
        }
        sRet += "+";

        return sRet;
    }

    public void triColonnes() {
        Comparator<List<JetonRessource>> comparator = new Comparator<List<JetonRessource>>() {
            public int compare(List<JetonRessource> colonne1, List<JetonRessource> colonne2) {
                return Integer.compare(colonne2.size(), colonne1.size());
            }
        };
        this.plateau.sort(comparator);
    }

}

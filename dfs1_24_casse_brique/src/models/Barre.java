package models;

import application.Fenetre;

import java.awt.*;

public class Barre extends Rectangle {

    public Barre(int x, int y, int largeur, int hauteur, Color couleur) {
        super(x, y, largeur, hauteur, couleur);
    }

    public Barre() {
        super(Fenetre.LARGEUR / 2 - 75, Fenetre.HAUTEUR - 100, 150, 20, Color.BLUE);
    }
}

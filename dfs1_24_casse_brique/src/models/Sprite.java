package models;

import java.awt.*;

public abstract class Sprite {
    protected int x = 0;
    protected int y = 0;
    protected Color couleur = Color.RED;

    public Sprite(int x, int y, Color couleur) {
        this.x = x;
        this.y = y;
        this.couleur = couleur;
    }

    public abstract void dessiner(Graphics2D dessin);

    // Getters et setters pour x et y
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

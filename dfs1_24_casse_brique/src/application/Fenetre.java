package application;

import models.Balle;
import models.Barre;
import models.Brique;
import models.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Fenetre extends Canvas implements KeyListener {

    public static final int LARGEUR = 500;
    public static final int HAUTEUR = 700;

    protected boolean toucheEspace = false;
    protected boolean toucheGauche = false;
    protected boolean toucheDroite = false;
    private boolean jeuFini = false;

    ArrayList<Balle> listeBalles = new ArrayList<>();
    ArrayList<Sprite> listeSprites = new ArrayList<>();
    ArrayList<Brique> listeBriques = new ArrayList<>();
    Barre barre;

    Fenetre() throws InterruptedException {

        JFrame fenetre = new JFrame();

        this.setSize(LARGEUR, HAUTEUR);
        this.setBounds(0, 0, LARGEUR, HAUTEUR);
        this.setIgnoreRepaint(true);
        this.setFocusable(false);

        fenetre.pack();
        fenetre.setSize(LARGEUR, HAUTEUR);
        fenetre.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fenetre.setResizable(false);
        fenetre.requestFocus();
        fenetre.addKeyListener(this);

        Container panneau = fenetre.getContentPane();
        panneau.add(this);

        fenetre.setVisible(true);
        this.createBufferStrategy(2);

        this.demarrer();
    }

    public void demarrer() throws InterruptedException {

        barre = new Barre();
        listeSprites.add(barre);

        Balle balle = new Balle(100, 200, Color.GREEN, 30);
        listeBalles.add(balle);
        listeSprites.add(balle);

        // Création des briques
        int briqueLargeur = 50;
        int briqueHauteur = 20;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                Brique brique = new Brique(j * briqueLargeur, i * briqueHauteur, briqueLargeur, briqueHauteur, Color.RED);
                listeBriques.add(brique);
                listeSprites.add(brique);
            }
        }

        while (true) {
            Graphics2D dessin = (Graphics2D) this.getBufferStrategy().getDrawGraphics();
            dessin.setColor(Color.WHITE);
            dessin.fillRect(0, 0, LARGEUR, HAUTEUR);

            if (!jeuFini) {
                //----- app -----
                for (Balle b : listeBalles) {
                    b.deplacement();
                    // Collision avec la barre
                    if (b.getY() + b.getDiametre() >= barre.getY() && b.getX() + b.getDiametre() >= barre.getX() && b.getX() <= barre.getX() + barre.getLargeur()) {
                        b.setVitesseY(-b.getVitesseY());
                    }

                    // Collision avec les briques
                    for (Brique br : listeBriques) {
                        if (b.getY() <= br.getY() + br.getHauteur() && b.getY() + b.getDiametre() >= br.getY() && b.getX() + b.getDiametre() >= br.getX() && b.getX() <= br.getX() + br.getLargeur()) {
                            b.setVitesseY(-b.getVitesseY());
                            listeBriques.remove(br);
                            listeSprites.remove(br);
                            break;
                        }
                    }
                }

                // Déplacement de la barre avec les touches
                if (toucheGauche && barre.getX() > 0) {
                    barre.setX(barre.getX() - 5);
                }
                if (toucheDroite && barre.getX() + barre.getLargeur() < LARGEUR) {
                    barre.setX(barre.getX() + 5);
                }

                // Vérification de la condition de victoire
                if (listeBriques.isEmpty()) {
                    jeuFini = true;
                }
            }

            for (Sprite s : listeSprites) {
                s.dessiner(dessin);
            }

            if (jeuFini) {
                dessin.setColor(Color.BLACK);
                dessin.setFont(new Font("Arial", Font.BOLD, 30));
                dessin.drawString("Victoire!", LARGEUR / 2 - 75, HAUTEUR / 2);
            }

            dessin.dispose();
            this.getBufferStrategy().show();
            Thread.sleep(1000 / 60);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            toucheEspace = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            toucheGauche = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            toucheDroite = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            toucheEspace = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            toucheGauche = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            toucheDroite = false;
        }
    }
}

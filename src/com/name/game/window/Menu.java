package com.name.game.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Menu extends MouseAdapter {

    private Game game;
    private Handler handler;
    Random random = new Random();

    public Menu(Game game) {
        this.game = game;
    }

    public void mousePressed(MouseEvent e) {

        int mx = e.getX();
        int my = e.getY();

        // Main Menu
        if (game.gameState == Game.State.MENU) {
            // Play button
            if (mouseOver(mx, my, 300, 180, 170, 60)) {
                  game.gameState =  Game.State.GAME;
                  game.reset();
                  game.prepareGame();
            }
            // Quit button
            else if (mouseOver(mx, my, 300, 380, 170, 60)) {
                System.exit(1);
            }
            // Help button
            else if (mouseOver(mx, my, 300, 280, 170, 60)) {
                game.gameState = Game.State.HELP;
            }

            // Help menu
        } else if (game.gameState == Game.State.HELP) {
            // Back button
            if (mouseOver(mx, my, 300, 380, 170, 60)) {
                game.gameState = Game.State.MENU;
            }

            // Game over interface
        } else if (game.gameState == Game.State.END) {

            // Try again button
            if (mouseOver(mx, my, 322, 380, 170, 60)) {
                game.gameState = Game.State.GAME;
                game.reset();
                game.prepareGame();
            }
        }
    }

    public boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) {
                return true;
            }
        }
        return false;
    }

    public void tick() {

    }

    public void render(Graphics g) {

        // Main menu interface
        if (game.gameState == Game.State.MENU) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", 1, 40));
            g.drawString("Menu", 330, 100);

            g.setFont(new Font("arial", 1, 25));

            // Play button
            g.drawRect(300, 180, 170, 60);
            g.drawString("Play", 360, 220);

            // Help button
            g.drawRect(300, 280, 170, 60);
            g.drawString("Help", 360, 320);

            // Quit button
            g.drawRect(300, 380, 170, 60);
            g.drawString("Quit", 360, 420);

            // Help menu interface
        } else if (game.gameState == Game.State.HELP) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", 1, 40));
            g.drawString("Help", 330, 100);
            g.setFont(new Font("arial", 1, 18));
            g.drawString("Description line 1", 20, 200);
            g.drawString("Description line 2", 20, 230);
            g.drawString("Description line 3", 20, 260);
            g.drawString("Description line 4", 20, 290);
            g.drawString("Description line 5", 20, 320);

            // Back button
            g.drawRect(300, 380, 170, 60);
            g.drawString("Back", 362, 417);

            // Game Over interface
        } else if (game.gameState == Game.State.END) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", 10, 40));
            g.drawString("Game Over", 300, 100);
            g.setFont(new Font("arial", 1, 18));
            g.drawString("You has lost the game with a score of "  + game.getHUD().getScore(), 220, 150);
            // Try again button
            g.drawRect(322, 380, 170, 60);
            g.drawString("Try Again", 365, 417);
        }
    }
}

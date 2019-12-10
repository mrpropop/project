package com.name.game.framework;

import com.name.game.objects.Player;
import com.name.game.window.Game;
import com.name.game.window.Handler;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class KeyInput extends KeyAdapter {

    private Game game;

    private boolean keyDown[] = new boolean[4];

    public KeyInput(Game game) {

        this.game = game;

        for (int i = 0; i < keyDown.length; i++) {
            keyDown[i] = false;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < game.getHandler().getObjects().size(); i++) {
            GameObject object =  game.getHandler().getObjects().get(i);


            if (object.getIdentifier().equals(Identifier.PLAYER)) {
                int playerSpeed  = ((Player)object).getPlayerSpeed();
                if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP ) {
                    object.setVelY(-playerSpeed);
                    keyDown[0] = true;
                }
                if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
                    object.setVelY(+playerSpeed);
                    keyDown[1] = true;
                }
                if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
                    object.setVelX(playerSpeed);
                    keyDown[2] = true;
                }
                if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
                    object.setVelX(-playerSpeed);
                    keyDown[3] = true;
                }
            }
        }


        // Escape
        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }

        // Pause
        if (key == KeyEvent.VK_P) {
            if (game.gameState == Game.State.GAME) {
                game.setPaused(!game.isPaused());
            }
        }

    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i <  game.getHandler().getObjects().size(); i++) {
            GameObject object =  game.getHandler().getObjects().get(i);
            if (object.getIdentifier() == Identifier.PLAYER) {

                if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP)
                    keyDown[0] = false;
                if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN)
                    keyDown[1] = false;
                if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
                    keyDown[2] = false;
                if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
                    keyDown[3] = false;

                // vertical movement
                if (!keyDown[0] && !keyDown[1])
                    object.setVelY(0);

                // horizontal movement
                if (!keyDown[2] && !keyDown[3])
                    object.setVelX(0);

            }
        }
    }
}

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class KeyInput extends KeyAdapter {

    private Handler handler;

    private boolean keyDown[] = new boolean[4];

    private Game game;

    public KeyInput(Handler handler, Game game) {
        this.handler = handler;
        this.game = game;

        for (int i = 0; i < keyDown.length; i++) {
            keyDown[i] = false;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject object = handler.objects.get(i);


            if (object.getId().equals(ID.PLAYER)) {
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

    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject object = handler.objects.get(i);
            if (object.getId() == ID.PLAYER) {

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

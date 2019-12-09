

import com.sun.corba.se.spi.ior.ObjectId;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Player extends GameObject {

    int playerSpeed = 5;


    public Player(float x, float y, int width, int height, ID id, Handler handler) {
        super(x, y, width, height, id, handler);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
    }

    @Override
    public void render(Graphics g) {

        g.setColor(Color.WHITE);
        g.fillRect((int) x, (int) y, width, height);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);

        g2d.draw(getBoundsBottom());
        g2d.draw(getBoundsTop());
        g2d.draw(getBoundsRight());
        g2d.draw(getBoundsLeft());
        g2d.draw(getBounds());
    }

    public int getPlayerSpeed(){
        return playerSpeed;
    }

}
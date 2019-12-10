package com.name.game.objects;

import com.name.game.framework.GameObject;
import com.name.game.framework.Identifier;
import com.name.game.window.Handler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

//changed
public class Player extends GameObject {

    int playerSpeed = 5;


    public Player(float x, float y, int width, int height, Identifier identifier, Handler handler) {
        super(x, y, width, height, identifier, handler);
        mass = 1000000;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
       // handler.addObject(new com.name.game.objects.Trail(x, y, width, height,ID.TRAIL, Color.WHITE, 0.1F, handler));
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
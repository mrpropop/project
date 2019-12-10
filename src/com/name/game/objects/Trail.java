package com.name.game.objects;

import com.name.game.framework.GameObject;
import com.name.game.framework.Identifier;
import com.name.game.window.Handler;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * We are going to be creating this trailer every step or every tick on out
 * basic enemy. And this trail is not going to move or anything at all All it is
 * going to do is just take its alpha value of 1 and slowly subtract that so it
 * looks like it is fading out to the distance
 *
 * once it finally just fades out,then we just destroy it completely cause we
 * don't need it anymore
 *
 */
public class Trail extends GameObject {

    private float alpha = 1;
    private float life = 1;

    private Color color;

    public Trail(float x, float y, int width, int height, Identifier identifier, Color color, float life, Handler handler) {
        super(x, y, width, height, identifier, handler);
        this.color = color;
        this.life = life;
    }

    @Override
    public void tick() {
        alpha -= life;
        if (alpha <= 0) {
            handler.removeObject(this);
        }
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(makeTransparent(alpha));
        g2d.setColor(color);
        g2d.fillRect((int) x, (int) y, width, height);
        g2d.setComposite(makeTransparent(1));

    }

    /*
     * that's basically the method that is going to be able to render out these
     * transparencies and objects
     */
    private AlphaComposite makeTransparent(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return AlphaComposite.getInstance(type, alpha);
    }

}

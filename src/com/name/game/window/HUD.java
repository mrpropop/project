package com.name.game.window;

import com.name.game.Utilities;

import java.awt.*;
import java.awt.geom.Rectangle2D;


public class HUD {

    private int score = 0;
    private int currentHealth = 100;
    private int maximumHealth = 100;
    private int level = 0;
    private long start = System.currentTimeMillis();


    public void tick(){

        if(score %10 == 0) {
            if(currentHealth > 0) {
                currentHealth--;
            }
        }

        if(score++ %1000 == 0){
            level++;
        }

    }

    public long getTimer(){
        return start;
    }

    public void log(String s){
        System.out.println(s);
    }


    public void healthBar(Graphics g){

        int startX = 15, startY = 15;
        int healthBarHeight = 35;
        int healthBarWidth = maximumHealth * 2;
        String string = currentHealth + "%";

        //center the text coordinated to be centered vertically/horizontally in the health rectangle
        Graphics2D g2d = (Graphics2D) g;
        FontMetrics fontMetrics = g2d.getFontMetrics();
        Rectangle2D rectangle = fontMetrics.getStringBounds(string, g2d);
        int textX = (healthBarWidth - (int) rectangle.getWidth()) / 2;
        int textY = (healthBarHeight - (int) rectangle.getHeight()) / 2 + fontMetrics.getAscent();

        //get the current color for health
        int colorAmount = (int) Math.round(255.0D * (currentHealth / ((double) maximumHealth)));
        int red = 255 - colorAmount;
        int green = colorAmount;
        Color color = new Color(red, green, 0);

        //draw health information
        g.setColor(color);
        g.fillRect(startX,startY, currentHealth*2, healthBarHeight);
        g.setColor(Color.WHITE);
        g.drawString(string, startX + textX, startY + textY); //draw the current health
        g.drawRect(startX,startY,healthBarWidth, healthBarHeight);

    }


    public void render(Graphics g){
        healthBar(g);
        g.drawString("Time played: " + Utilities.formatSeconds((int) (System.currentTimeMillis() - start)/1000), 620, 30);
        g.drawString("Score: " + score, 620, 60);
        g.drawString("Level: " + level, 620, 90);
    }

    public int getCurrentHealth(){
        return currentHealth;
    }

    public int getScore(){
        return score;
    }


    public void reset() {
        score = 0;
        level = 0;
        currentHealth = 100;
        start = System.currentTimeMillis();
    }
}

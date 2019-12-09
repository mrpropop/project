import java.awt.*;

public class HUD {

    private int score = 0;
    private int currentHealth = 100;
    private int maximumHealth = 100;
    private int level = 0;
    private long start = System.currentTimeMillis();


    public void tick(){

        if(score %3 == 0) {
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
        int colorAmount = (int) Math.round(255.0D * (currentHealth /((double) maximumHealth)));
        int Red = 255 - colorAmount;
        int Green = colorAmount;
        int Blue = 0;
        Color color = new Color(Red,Green,Blue);
        g.setColor(color);
        g.fillRect(15,15, currentHealth * 2, 25);
        g.setColor(Color.WHITE);
        g.drawString(currentHealth + "%", currentHealth < 20 ? currentHealth + 20 : currentHealth, 33); //draw the currentHealth
        g.drawRect(15,15,200, 25);

    }


    public void render(Graphics g){
        healthBar(g);
        g.drawString("Time played: " + Utilities.FormatSeconds((int) (System.currentTimeMillis() - start)/1000), 620, 30);
        g.drawString("Score: " + score, 620, 60);
        g.drawString("Level: " + level, 620, 90);
    }



}

import java.awt.*;

public class HUD {

    private int score = 0;
    private int health = 100;
    private int level = 0;
    private long start = System.currentTimeMillis();


    public void tick(){

        if(score %3 == 0) {
            if(health > 0) {
                health--;
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
        if(health >=60){
            g.setColor(Color.green);
        }else if(health >=30 && health < 60){
            g.setColor(Color.orange);
        }else if(health < 30){
            g.setColor(Color.red);
        }

        g.fillRect(15,15, health * 2, 25);
        g.setColor(Color.white);
        g.drawString(health + "%", health < 20 ? health + 20 : health, 33); //draw the health
        g.drawRect(15,15,200, 25);

    }


    public void render(Graphics g){
        healthBar(g);
        g.drawString("Time played: " + (int) (System.currentTimeMillis() - start)/1000, 620, 30);
        g.drawString("Score: " + score, 620, 60);
        g.drawString("Level: " + level, 620, 90);
    }



}

import java.awt.*;
import java.util.Random;


//changed
public class Block extends GameObject{

    public Color original = Color.yellow;
    public Color shapeColor = Color.yellow;

    Random random = new Random();

    public Block(float x, float y, int width, int height, ID id, Handler handler) {
        super(x, y, width, height,  id, handler);

        double val1 = random.nextDouble() * 5;
        velX = (int)val1;
    }

    @Override
    public void tick() {
        double val1 = random.nextDouble();
        double val2 = random.nextDouble();


        x+= velX;

       // x = x > Game.WIDTH ? x - Game.WIDTH : x < 0 ? x + Game.WIDTH : x;



        if(x > Game.WIDTH - width){
            velX = -velX;
        }

        if(x < 0){
           velX = Math.abs(velX);
        }

        if(collision()){
            shapeColor = Color.red;
        }else{
            shapeColor = original;
        }



    }

    @Override
    public void render(Graphics g) {
        g.setColor(shapeColor);
        g.drawRect((int)x,(int)y,width, height);
    }
}

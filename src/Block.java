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

    public Block(float x, float y, int width, int height, ID id, Handler handler, float mass, byte CollisionMode) {
        super(x, y, width, height,  id, handler, mass, CollisionMode);
        GenerateRandomVelocities(5.0F);
    }

    public void GenerateRandomVelocities(double Max){
        velX = (float) ((Math.random() * 2.0D * Max) - Max);
        velY = (float) ((Math.random() * 2.0D * Max) - Max);
    }

    @Override
    public void tick() {
        x+= velX;
        y+= velY;
        Bounce();

        if(collision()){
            shapeColor = Color.red;
        }else{
            shapeColor = original;
        }

    }

    public void Bounce(){

        if(x > Game.WIDTH - width){
            x = Game.WIDTH - width;
            velX = -velX;
        }

        if(x < 0){
            x = 0;
            velX = Math.abs(velX);
        }

        if(y > Game.HEIGHT - 2*height){
            y = Game.HEIGHT - 2*height;
            velY = -velY;
        }

        if(y < 0){
            y = 0;
            velY = Math.abs(velY);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(shapeColor);
        g.drawRect((int)x,(int)y,width, height);
    }
}

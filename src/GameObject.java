import java.awt.*;

public abstract class GameObject {

    protected Handler handler;
    protected ID id;

    protected boolean falling = true;
    protected boolean jumping = false;

    protected float x,y;
    protected int width, height;
    protected float velX, velY;
    protected float accX, accY;


    // We need these functions in every class that extends GameObject
    public abstract void tick();
    public abstract void render(Graphics g);

    public GameObject(float x, float y, int width, int height, ID id, Handler handler){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.handler = handler;
    }

    /*
     * Basically, we are going to be using rectangles to handle all the collisions
     *
     * Because within the library, there is a method called intersects which handles
     * if two rectangles hit each other, it then will print true
     *
     */
    protected Rectangle getBounds(){
        return new Rectangle((int)Math.round(x), (int)Math.round(y), width, height);
    }





    public int getWidth(){
        return width;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public int getHeight(){
        return height;
    }

    public void setHeight(int height){
        this.height = height;
    }


    public float getVelX(){
        return velX;
    }

    public void setVelX(int velX){
        this.velX = velX;
    }

    public float getVelY(){
        return velY;
    }

    public void setVelY(int velY){
        this.velY = velY;
    }


    public ID getId(){
        return id;
    }




    public Rectangle getBoundsBottom() {
        return new Rectangle((int) x + width / 4, (int) y + height / 2, width / 2, height / 2);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle((int) x + width / 4, (int) y, width / 2, height / 2);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle((int) x + width - 5, (int) y + 5, 5, height - 10);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int) x, (int) y + 5, 5, height - 10);
    }



    public boolean collision() {
        boolean collided = false;
        for(int i = 0; i< handler.objects.size(); i++) {
            GameObject gameObject = handler.objects.get(i);
            if(gameObject != null && this != null && this != gameObject){
                if (this.getBounds().intersects(gameObject.getBounds())) {
                    collided = true;
                    System.out.println("An intersect");

                    // From the top
                    if (this.getBoundsTop().intersects(gameObject.getBounds())) {
                        velY = 0;
                        System.out.println("top");
                    }

                    // From the bottom
                    if (this.getBoundsBottom().intersects(gameObject.getBounds())) {
                        velY = 0;
                        System.out.println("bottom");
                    }

                    // From the right
                    if (this.getBoundsRight().intersects(gameObject.getBounds())) {
                        System.out.println("right");
                        velX = 0;
                    }

                    // From the left
                    if (this.getBoundsLeft().intersects(gameObject.getBounds())) {
                        velX = 0;
                        System.out.println("left");
                    }

                }
            }
        }
        return collided;
    }
}
